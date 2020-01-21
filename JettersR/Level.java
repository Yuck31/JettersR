package JettersR;
/**
 * This is the class that manages the game's levels.
 * Almost every State in the game will have it's own level in which it calls functions to.
 * The Level class manages the updating and rendering of lists of Tiles and Entities.
 * 
 * author: Luke Sullivan
 * Last Edit: 1/20/2020
 */
import JettersR.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Items.*;
import JettersR.Entity.Particle.*;
import JettersR.Tiles.*;
import JettersR.GameStates.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Level
{
    public Screen screen;
    Random rand = new Random();
    public int[] screenPixels;
    public boolean updating = false;
    public static boolean showInvis = false;
    public int startShakeX, startShakeY, shakeX, shakeY;//For screen shake
    public int shakeTime = 0;
    public byte shakeFrames = 0;

    public int width, height;
    public static int minX, maxX, minY, maxY;
    //protected int[] tilesInt;
    public int[][] floorTiles,//[Floor Number] [Tile Array]
    wallTiles;
    public int[][] itemSpaces;
    public boolean[][] ISused;//Item-Space used
    private int[] ISoffset;//Item-Space offset
    public boolean[][] floorRendered;
    public boolean[][] wallRendered;
    protected int tile_size;
    public int[] entitySpawns;

    public List<Entity>[] entities;//We use an array of entities for floor management
    public List<Projectile>[] projectiles;//Hiarchies of projectiles
    public List<Particle> particles = new ArrayList<Particle>();
    public List<Item>[] items;
    public List<Player> players = new ArrayList<Player>();

    public Comparator<Entity> yAxisSorter = new Comparator<Entity>()
        {
            //And now, an explaination of Comparators...

            //A Comparator is a method in Java that is used to compare multiple objects with each other.
            //Comparators will return either a negitive integer, Zero, or a positive integer(less than, equal to, or greater than).
            //Comparators have a Transitivity "Contract" stating something along the lines of: (Object a < Object b) must equal (Object b > Object a)
            //The Comparators here have checks that take into account whether the Y-Position or Z-value of two Entities a is less than, equal to, OR greater than with no confusion of telling them apart.
            //If the Comparator performs a comparison that dosn't follow the contract, an execption is thrown.
            @Override
            public int compare(Entity a, Entity b)
            {
                int x = 0;//Defualt value in case Entity a and Entity b are EQUAL
                //ab = a;
                //ba = b;
                if((a.getY() + a.getHeight() < b.getY() + b.getHeight()))
                {
                    x =  -1;//This is for if Entity a's y-value is LESS than Enity b's y-value.
                }
                else if((a.getY() + a.getHeight() > b.getY() + b.getHeight()))
                {
                    x = 1;//This is for if Entity a's y-value is GREATER than Enity b's y-value.
                }
                //xa = x;
                return x;
            }
        };

    public Comparator<Entity> zAxisSorter = new Comparator<Entity>()
        {
            public int compare(Entity a, Entity b)
            {
                int x = 0;//Defualt value in case Entity a's z-value and Entity b's z-value are EQUAL.
                if(a.getZ() < b.getZ())
                {
                    x = -1;//This is for if Entity a's z-value is LESS than Enity b's z-value.
                }
                else if(a.getZ() > b.getZ())
                {
                    x = 1;//This is for if Entity a's z-value is GREATER than Enity b's z-value.
                }
                return x;
            }
        };

    public Level (String floorPath, String wallPath)
    {
        entities = new List[2];//One floor for the floor, another floor for everything above the floor (SD Blocks)
        entities[0] = new ArrayList<Entity>();
        entities[1] = new ArrayList<Entity>();

        projectiles = new List[2];
        projectiles[0] = new ArrayList<Projectile>();
        projectiles[1] = new ArrayList<Projectile>();

        items = new List[2];//One floor for the floor, another floor for everything above the floor (SD Blocks)
        items[0] = new ArrayList<Item>();
        items[1] = new ArrayList<Item>();
        setSize(floorPath, 1);
        loadFloor(floorPath);
        loadWall(wallPath);
        generateLevel();

        //add(new Spawner(32, 32, Spawner.Type.PARTICLE, 50, this));
    }

    public Level (String[] floorPaths, String[] wallPaths, int floors)
    {
        entities = new List[floors+1];//All the floors for the actual ground, another floor for everything above the floor (SD Blocks)
        projectiles = new List[floors+1];
        items = new List[floors+1];
        for(int i = 0; i < entities.length; i++)
        {
            entities[i] = new ArrayList<Entity>();//We create Entity lists in here.
            projectiles[i] = new ArrayList<Projectile>();
            items[i] = new ArrayList<Item>();
        }
        setSize(floorPaths[0], floors);
        loadFloors(floorPaths);
        loadWalls(wallPaths);
        generateLevel();

        //add(new Spawner(32, 32, Spawner.Type.PARTICLE, 50, this));
    }

    protected void setSize(String path, int floors)//This sets the size of the Tile array for multiple floors of tiles
    {
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));//Gets image
            width = image.getWidth();//Gets image width
            height = image.getHeight();//Gets image height
            floorTiles = new int[floors][width * height];//Set the tile array to the number of floors and number of tiles per floor
            wallTiles = new int[floors][width * height];
            floorRendered = new boolean[floors+1][width*height];
            wallRendered = new boolean[floors+1][width*height];
        } catch (Exception e) {
            System.out.println("Size seting issue");
            e.printStackTrace();
        }
    }

    protected void loadFloor(String path)
    {
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));
            image.getRGB(0, 0, width, height, floorTiles[0], 0, width);
        } catch (Exception e) {
            System.out.println("Loading floor issue");
            e.printStackTrace();
        }
    }

    protected void loadWall(String path)
    {
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));
            image.getRGB(0, 0, width, height, wallTiles[0], 0, width);
        } catch (Exception e) {
            System.out.println("Loading wall issue");
            e.printStackTrace();
        }
    }

    protected void loadFloors(String[] paths)//This takes a pictrue of pixels and extracts the colors into each "floor" of the "array of tile arrays"
    {
        for(int i = 0; i < paths.length; i++)
        {
            try {
                BufferedImage image = ImageIO.read(Level.class.getResource(paths[i]));
                image.getRGB(0, 0, width, height, floorTiles[i], 0, width);
            } catch (Exception e) {
                System.out.println("Loading floor issue");
                e.printStackTrace();
            }
        }
    }

    protected void loadWalls(String[] paths)//This takes a pictrue of pixels and extracts the colors into each "floor" of the "array of tile arrays"
    {
        for(int i = 0; i < paths.length; i++)
        {
            try {
                BufferedImage image = ImageIO.read(Level.class.getResource(paths[i]));
                image.getRGB(0, 0, width, height, wallTiles[i], 0, width);
            } catch (Exception e) {
                System.out.println("Loading wall issue");
                e.printStackTrace();
            }
        }
    }

    protected void generateLevel()
    {
        for(int z = 0; z < floorTiles.length; z++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    getTile(x, y, z, floorTiles);
                }
            }
        }
        for(int z = 0; z < wallTiles.length; z++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    getTile(x, y, z, wallTiles);
                }
            }
        }
        tile_size = 32;
    }

    public void generateEntities(String path)
    {
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));
            entitySpawns = new int[width * height];
            image.getRGB(0, 0, width, height, entitySpawns, 0, width);
        } catch (Exception e) {
            System.out.println("Loading Entities issue");
            e.printStackTrace();
        }
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                switch(entitySpawns[x+y*width])
                {
                    case 0xFF926252: add(new SoftBlock((x*32),(y*32)-1,0)); break;
                }
            }
        }
    }

    public void setItemSpaces(String path)
    {
        ISoffset = new int[1];
        itemSpaces = new int[1][width * height];//Set the tile array to the number of floors and number of tiles per floor
        ISused = new boolean[1][width * height];
        try {
            BufferedImage image = ImageIO.read(Level.class.getResource(path));//Gets image
            image.getRGB(0, 0, width, height, itemSpaces[0], 0, width);
        } catch (Exception e) {
            System.out.println("Item-Space issue");
            e.printStackTrace();
        }
        for(int i = 0; i < itemSpaces[0].length; i++)
        {
            if(itemSpaces[0][i] != 0x00000000){break;}
            ISoffset[0]++;
        }
    }

    public void setItemSpaces(String[] paths)
    {
        ISoffset = new int[paths.length];
        itemSpaces = new int[paths.length][width * height];//Set the tile array to the number of floors and number of tiles per floor
        ISused = new boolean[paths.length][width * height];
        for(int i = 0; i < paths.length; i++)
        {
            if(paths[i] == null){continue;}
            try {
                BufferedImage image = ImageIO.read(Level.class.getResource(paths[i]));//Gets image
                image.getRGB(0, 0, width, height, itemSpaces[i], 0, width);
            } catch (Exception e) {
                System.out.println("Item-Space issue");
                e.printStackTrace();
            }
        }
        for(int f = 0; f < paths.length; f++)
        {

            for(int i = 0; i < itemSpaces[0].length; i++)
            {
                if(itemSpaces[0][i] != 0x00000000){break;}
                ISoffset[f]++;
            }

        }
    }

    public void setLoopBounds(int minX, int maxX, int minY, int maxY)
    {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void dropItems(List<Item> items)
    {
        //if(items.isEmpty() || itemSpaces == null){return;}
        for(int i = 0; i < items.size(); i++)
        {
            add(items.get(i));
            items.get(i).spawn();
        }
        // for(int f0 = 0; f0 < itemSpaces.length; f0++)
        // {
        // for(int f1 = ISoffset[f0]; f1 < itemSpaces[f0].length; f1++)
        // {
        // if(itemSpaces[f0][f1] != 0x00000000)
        // {
        // add(items.get(i));
        // i++;
        // if(i >= items.size()){return;}
        // }
        // }
        // }
    }

    //The following functions are simply meant for managing the level itself
    public void update()
    {
        updating = true;
        if(shakeTime > 0)
        {
            shake();
            shakeTime--;
            if(shakeTime <= 15 && shakeFrames <= 0)
            {
                startShakeX--; startShakeY--;
                shakeFrames = 10;
            }
            if(shakeFrames > 0){shakeFrames--;}
        }
        else{startShakeX = 0; startShakeY = 0; shakeX = 0; shakeY = 0;}
        for (int i = 0; i < entities.length; i++)
        {
            for (int j = 0; j < entities[i].size(); j++)
            {
                entities[i].get(j).update();//Updates entities
            }
        }
        manageFloorLists();
        remove();
        updating = false;
    }

    private void manageFloorLists()
    {
        for (int i = 0; i < entities.length; i++)
        {
            for (int j = 0; j < entities[i].size(); j++)
            {
                if(entities[i].get(j).getZ() != i)//If the Z value of the Entity does not equal the array slot number
                {
                    entities[entities[i].get(j).getZ()].add(entities[i].get(j));//Add it to the correct floor...
                    entities[i].remove(j);//...and remove it from this floor.
                }
            }
        }

        for (int i = 0; i < projectiles.length; i++)
        {
            for (int j = 0; j < projectiles[i].size(); j++)
            {
                if(projectiles[i].get(j).getZ() != i)
                {
                    projectiles[projectiles[i].get(j).getZ()].add(projectiles[i].get(j));
                    projectiles[i].remove(j);
                }
            }
        }

        for (int i = 0; i < items.length; i++)
        {
            for (int j = 0; j < items[i].size(); j++)
            {
                if(items[i].get(j).getZ() != i)
                {
                    items[items[i].get(j).getZ()].add(items[i].get(j));
                    items[i].remove(j);
                }
            }
        }
    }

    private void remove()
    {
        for(int i = 0; i < particles.size(); i++)
        {
            if(particles.get(i).isRemoved()){particles.remove(i);}
        }

        for (int i = 0; i < items.length; i++)
        {
            for (int j = 0; j < items[i].size(); j++)
            {
                if(items[i].get(j).isRemoved()){items[i].remove(j);}
            }
        }

        for (int i = 0; i < entities.length; i++)
        {
            for (int j = 0; j < entities[i].size(); j++)
            {
                if(entities[i].get(j).isRemoved()){entities[i].remove(j);}
            }
            try
            {
                entities[i].sort(yAxisSorter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < projectiles.length; i++)
        {
            for (int j = 0; j < projectiles[i].size(); j++)
            {
                if(projectiles[i].get(j).isRemoved()){projectiles[i].remove(j);}
            }
        }

        for(int i = 0; i < players.size(); i++)
        {
            if(players.get(i).isDead()){players.remove(i);}
        }
    }

    public List<Projectile> getProjectiles()
    {
        return projectiles[0];
    }
    
    public void shake(int shakeX, int shakeY, int shakeTime)
    {
        this.shakeTime = shakeTime;
        startShakeX = shakeX;
        startShakeY = shakeY;
    }
    
    public void shake()
    {
        if(startShakeX <= 0 || startShakeY <= 0){return;}
        shakeX = rand.nextInt((startShakeX*2)+1) - (startShakeX+1);
        shakeY = rand.nextInt((startShakeY*2)+1) - (startShakeY+1);
    }

    public void render(int xScrool, int yScrool, int zScrool, Screen screen)//This ONLY renders tiles and entities ON SCREEN
    {
        if(updating)
        {
            byte t = 15;
            while(updating)
            {
                if(!updating || t <= 0){break;}
                t--;
            }
        }
        this.screen = screen;
        screen.setOffset(xScrool+shakeX, yScrool+shakeY);
        int x0 = (xScrool) >> 5;
        int x1 = (xScrool + screen.width + 32) >> 5;
        int y0 = (yScrool-zScrool) >> 5;
        int y1 = ((yScrool+zScrool) + screen.height + 64) >> 5;

        for(int z = 0; z < floorTiles.length+1; z++)//Length + 1 so that any Entities higher than the map's max z value can render over the entire map
        {
            for(int y = y0; y <  y1; y++)
            {
                for(int x = x0; x < x1; x++)
                {
                    if(getTile(x,y,z,floorTiles) != Tile.voidTile /**&& !getTile(x,y,z,floorTiles).isSlope()*/ && !getFloorRendered(x,y,z))
                    {
                        if(getTile(x,y,z,floorTiles).isSlope() && getTile(x,y-1,z, wallTiles) != Tile.voidTile)
                        {
                            getTile(x,y-1,z, wallTiles).render(x,y,z,showInvis,screen);
                            setWallRendered(x,y-1,z,true);
                        }
                        if(!getTile(x,y,z,floorTiles).isSlope())
                        {
                            getTile(x,y,z,floorTiles).render(x,y,z,showInvis,screen);//Renders floor Tiles
                            setFloorRendered(x,y,z,true);
                        }
                    }
                }
            }

            for(int y = y0; y <  y1; y++)
            {
                for(int x = x0; x < x1; x++)
                {
                    for(int z0 = z; z0 < floorTiles.length; z0++)
                    {
                        if(getTile(x,y,z0, floorTiles) != Tile.voidTile || (getTile(x,y,z0, wallTiles) != Tile.voidTile))
                        {
                            if(getTile(x,y-1,z0+1, floorTiles) == Tile.voidTile
                            || getTile(x,y+1,z0+1, floorTiles) == Tile.voidTile)
                            {
                                if(!getWallRendered(x,y,z0))
                                {
                                    if(getTile(x,y,z0, wallTiles) != Tile.voidTile)
                                    {
                                        if(getTile(x,y-1,z0, floorTiles) != Tile.voidTile && z0 != z && !getFloorRendered(x,y,z0))
                                        {
                                            getTile(x,y-1,z0, floorTiles).render(x,y-1,z0,showInvis,screen);
                                            setFloorRendered(x,y-1,z0,true);
                                        }
                                        getTile(x,y,z0, wallTiles).render(x,y,z0,showInvis,screen);
                                        //if(!getTile(x,y+1,z0, floorTiles).isFloor())
                                        //{
                                        setWallRendered(x,y,z0,true);
                                        //}
                                    }
                                    else if(getTile(x,y,z0, floorTiles).isSlope() && !getFloorRendered(x,y,z0))
                                    {
                                        getTile(x,y,z0, floorTiles).render(x,y,z0,showInvis,screen);
                                        setFloorRendered(x,y,z0,true);
                                    }
                                }
                            }
                            else{break;}
                        }
                        else{break;}
                    }
                }
                for (int i = 0; i < entities[z].size(); i++)
                {
                    if((entities[z].get(i).getY()+32 >= (y*32) && entities[z].get(i).getY() <= (y*32) + 31))
                    {
                        //if(!entities[z].get(i).rendered)
                        //{
                        entities[z].get(i).render(screen);
                        //entities[z].get(i).rendered = true;
                        //}
                        //The main reason why I divided the Tiles into two arrays (floors and walls) was simply for rendering purposes
                    }
                }
            }
        }
        for(int z = 0; z < floorTiles.length; z++)
        {
            for(int y = y0; y <  y1; y++)
            {
                for(int x = x0; x < x1; x++)
                {
                    setFloorRendered(x,y,z,false);
                    setWallRendered(x,y,z,false);
                }       
            }
            // for (int i = 0; i < entities[z].size(); i++)
            // {
            // entities[z].get(i).rendered = false;
            // }
        }
        //screen.drawRect(minX, minY, maxX-minX, maxY-minY, 0x00FFFFFF, true);
    }

    public void add(Entity e)
    {
        e.init(this);
        if(e instanceof Particle)
        {
            particles.add((Particle)e);
            entities[e.getZ()].add(e);
        }
        else if(e instanceof Projectile)
        {
            projectiles[e.getZ()].add((Projectile)e);
            entities[e.getZ()].add(e);
        }
        else if(e instanceof Item)
        {
            items[e.getZ()].add((Item)e);
            entities[e.getZ()].add(e);
        }
        else if(e instanceof Player)
        {
            players.add((Player)e);
            entities[e.getZ()].add(e);
        }
        else
        {
            entities[e.getZ()].add(e);
        }     
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public int getPlayersSize()
    {
        return players.size();
    }

    // public Player getPlayerAt(int index)
    // {
    // return players.get(index);
    // }

    // public Player getClientPlayer()
    // {
    // return players.get(0);
    // }

    public List<Entity> getEntities(Entity e, int radius)
    {
        List<Entity> result = new ArrayList<Entity>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for(int i = 0;i<entities.length;i++)
        {
            for(int j = 0;j<entities[i].size();i++)
            {
                Entity entity = entities[i].get(j);
                int x = (int)entity.getX();
                int y = (int)entity.getY();
                int dx = Math.abs(x - ex);
                int dy = Math.abs(y - ey);
                double distance = Math.sqrt((dx * dx) + (dy * dy));
                if(distance <= radius) {result.add(entity);}
            }
        }
        return result;
    }

    public List<Player> getPlayers(Entity e, int radius)
    {
        List<Player> result = new ArrayList<Player>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for(int i = 0;i<players.size();i++)
        {
            Player player = players.get(i);
            int x = (int)player.getX();
            int y = (int)player.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if(distance <= radius) {result.add(player);}
        }
        return result;
    }

    public void enableScaling(float scale)
    {
        screenPixels = new int[(int)((width*scale)*(height*scale))];
    }

    public void renderScale(int xScrool, int yScrool, int zScrool, float scale, Screen screen)//This ONLY renders tiles ON SCREEN
    {
        this.screen = screen;
        screen.setOffset((int)(xScrool), (int)(yScrool));
        int x0 = (int)(((xScrool) >> 5)/scale);
        int x1 = (int)(((xScrool + screen.width + 32) >> 5)/scale);
        int y0 = (int)(((yScrool) >> 5)/scale);
        int y1 = (int)(((yScrool + screen.height + 32 + (32*(zScrool+1))) >> 5)/scale);

        for(int i = 0; i < entities.length; i++)
        {
            try
            {
                entities[i].sort(yAxisSorter);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        for(int z = 0; z < floorTiles.length+1; z++)//Length + 1 so that any Entities higher than the map's max z value can render over the entire map
        {
            for(int y = y0; y <  y1; y++)
            {
                for(int x = x0; x < x1; x++)
                {
                    if(getTile(x,y,z,floorTiles) != Tile.voidTile && !getTile(x,y,z,floorTiles).isSlope())
                    {
                        getTile(x,y,z,floorTiles).renderScale(x,y,z,scale,screen);//Renders floor Tiles
                    }
                }
            }
            for(int y = y0; y <  y1; y++)
            {
                for(int x = x0; x < x1; x++)
                {
                    if(getTile(x,y,z, wallTiles) != Tile.voidTile){getTile(x,y,z, wallTiles).renderScale(x,y,z,scale,screen);}//Then it renders wall Tiles
                    else if(getTile(x,y,z, floorTiles).isSlope()){getTile(x,y,z, floorTiles).renderScale(x,y,z,scale,screen);}//..Or Slope Tiles
                    //if((getTile(x,y,z, wallTiles) instanceof solidTile))
                    //{
                    for (int i = 0; i < entities[z].size(); i++)
                    {
                        if((entities[z].get(i).getX()+32 >= (x*32) && entities[z].get(i).getX() <= (x*32)+32) && ((entities[z].get(i).getY()+32 >= (y*32) && entities[z].get(i).getY() <= (y*32) + 31)))
                        {
                            entities[z].get(i).renderScale(screen, scale);//Renders entities based on their positions in relation to tiles(this is probably inefficient)
                        }
                    }
                }
            }
        }
        // screen.drawRect(
        // (int)(GameStateManager.minX*scale),
        // (int)(GameStateManager.minY*scale),
        // (int)((GameStateManager.maxX-GameStateManager.minX)*scale),
        // (int)((GameStateManager.maxY-GameStateManager.minY)*scale),
        // 0x00FFFFFF, true);
    }

    public boolean getFloorRendered(int x, int y, int z)
    {
        if(x < 0 || x >= width || y < 0 || y >= height || z < 0 || z > floorTiles.length){return true;}
        else{return floorRendered[z][x+y*width];}
    }

    public void setFloorRendered(int x, int y, int z, boolean booLean)
    {
        if(x < 0 || x >= width || y < 0 || y >= height || z < 0 || z > floorTiles.length){return;}
        else{floorRendered[z][x+y*width] = booLean;}
    }

    public boolean getWallRendered(int x, int y, int z)
    {
        if(x < 0 || x >= width || y < 0 || y >= height || z < 0 || z > floorTiles.length){return true;}
        else{return wallRendered[z][x+y*width];}
    }

    public void setWallRendered(int x, int y, int z, boolean booLean)
    {
        if(x < 0 || x >= width || y < 0 || y >= height || z < 0 || z > floorTiles.length){return;}
        else{wallRendered[z][x+y*width] = booLean;}
    }

    public Tile getTile(int x, int y, int z, int[][] tiles)//This takes a slot of either of the two tile arrays and returns what tile it should be based off of the color value of the array slot
    {
        if ((x < 0 || y < 0 || x >= width || y >= height || z >= tiles.length || z < 0)
        || ((z < tiles.length && z >= 0) && tiles[z][x+y*width] == 0))
        {return Tile.voidTile;}//To prevent Out of Bounds crash
        else{return getTile(tiles[z][x+y*width]);}
    }

    public Tile getTile(int color)
    {
        switch(color)
        {
            //Regular Map Tiles
            case 0xFF10811D: return Tile.green1;
            case 0xFF10671A: return Tile.green2;
            case 0xFF1ED1D9: return Tile.hardBlock;
            case 0xFF182C22: return Tile.vertWall;
            case 0xFF395648: return Tile.horiWall0;
            case 0xFF4B6C5D: return Tile.horiWall1;
            case 0xFF678A7C: return Tile.horiWall2;
            case 0xFF1E3628: return Tile.horiWall3;
            case 0xFFC0A50B: return Tile.upLeftCorner;
            case 0xFFB7510A: return Tile.upRightCorner;
            case 0xFF984106: return Tile.downLeftCorner;
            case 0xFFBE7316: return Tile.downRightCorner;

            case 0xFF967F4D: return Tile.backGroundTile1;
            case 0xFF7F6537: return Tile.backGroundTile2;
            case 0xFFB95925: return Tile.backGroundTile3;
            //

            //Great Wall Tiles
            case 0xFF4B463D: return Tile.obsidianFloor;
            case 0xFF2F2A23: return Tile.obsidianBlock;
            case 0xFF585147: return Tile.obsidianWall;
            case 0xFF2A251F: return Tile.obsidianSlopeLeft;
            case 0xFF221D17: return Tile.obsidianSlopeRight;
            case 0xFFA09B8D: return Tile.rockFloor;
            case 0xFF5A5849: return Tile.rockBlock;
            case 0xFFEE7707: return Tile.hotWall;

            case 0xFF2A2721: return Tile.gwBackGroundTile;
            //

            //Power Zone Tiles
            case 0xFF313131: return Tile.powerZoneWall;
            case 0xFFF02721: return Tile.powerZoneHeightenedWall;
            case 0xFFF02722: return Tile.powerZoneHardBlock;

            case 0xFF417C85: return Tile.powerZoneFloorUL;
            case 0xFF417C84: return Tile.powerZoneFloorU;
            case 0xFF417C83: return Tile.powerZoneFloorUR;
            case 0xFF417C78: return Tile.powerZoneFloorL;
            case 0xFFE5AB9D: return Tile.powerZoneFloor;
            case 0xFF417C82: return Tile.powerZoneFloorR;
            case 0xFF417C79: return Tile.powerZoneFloorDL;
            case 0xFF417C80: return Tile.powerZoneFloorD;
            case 0xFF417C81: return Tile.powerZoneFloorDR;
            //

            //Rock Garden Tiles
            case 0xFF61993C: return Tile.rockGardenGrass;
            case 0xFFCEC6BD: return Tile.rockGardenMarbleTile;

            case 0xFFC3BEAE: return Tile.rockGardenWallCube;
            case 0xFFC9B296: return Tile.rockGardenWallH1;
            case 0xFFC9B297: return Tile.rockGardenWallH2;
            case 0xFFC9B298: return Tile.rockGardenWallH3;
            case 0xFFA09083: return Tile.rockGardenWallV1;
            case 0xFFA09084: return Tile.rockGardenWallV2;
            case 0xFFA09085: return Tile.rockGardenWallV3;
            case 0xFFCFBE9A: return Tile.rockGardenPole0;
            case 0xFFCFBE9B: return Tile.rockGardenPole1;
            case 0xFFE2D19F: return Tile.rockGardenTallPole0;
            case 0xFFE2D1A0: return Tile.rockGardenTallPole1;
            case 0xFFE2D1A1: return Tile.rockGardenTallPole2;
            //

            //All maps
            case 0xFFABABAB: return Tile.floor0;
            case 0xFF8F8F8F: return Tile.floorBlock0;
            case 0xFF6A6A6A: return Tile.floorR0;
            case 0xFF606060: return Tile.floorBlockR0;
            case 0xFF5A5A5A: return Tile.floorL0;
            case 0xFF505050: return Tile.floorBlockL0;
            case 0xFFABABFF: return Tile.slantedFloorBlockDL0;
            case 0xFFABABE9: return Tile.slantedFloorBlockDR0;
            case 0xFFABFFAB: return Tile.slantedFloorUL0;
            case 0xFFABE9AB: return Tile.slantedFloorBlockUL0;
            case 0xFFABE0AB: return Tile.slantedFloorUR0;
            case 0xFFABD8AB: return Tile.slantedFloorBlockUR0;
            case 0xFFFFabab: return Tile.leftSlope0;
            case 0xFFE9abab: return Tile.rightSlope0;
            case 0xFFE0abab: return Tile.downSlope0;
            case 0xFFD8abab: return Tile.upSlope0;

            case 0xFFdedede: return Tile.floor1;
            case 0xFFbcbcbc: return Tile.floorBlock1;
            case 0xFFd0d0d0: return Tile.floorR1;
            case 0xFFcacaca: return Tile.floorBlockR1;
            case 0xFFb7b7b7: return Tile.floorL1;
            case 0xFFb2b2b2: return Tile.floorBlockL1;
            case 0xFFdedeFF: return Tile.slantedFloorBlockDL1;
            case 0xFFdedeE9: return Tile.slantedFloorBlockDR1;
            case 0xFFdeFFde: return Tile.slantedFloorUL1;
            case 0xFFdeE9de: return Tile.slantedFloorBlockUL1;
            case 0xFFdeE0de: return Tile.slantedFloorUR1;
            case 0xFFdeD8de: return Tile.slantedFloorBlockUR1;
            case 0xFFFFdede: return Tile.leftSlope1;
            case 0xFFE9dede: return Tile.rightSlope1;
            case 0xFFE0dede: return Tile.downSlope1;
            case 0xFFD8dede: return Tile.upSlope1;

            case 0xFF484848: return Tile.solidBlock;
            case 0xFF4848FF: return Tile.slantedWallDL;
            case 0xFF4848E9: return Tile.slantedWallDR;
            case 0xFF48FF48: return Tile.slantedWallUL;
            case 0xFF48E948: return Tile.slantedWallUR;
            case 0xFF5b5b5b: return Tile.backgroundTile;

            case 0xFF000001: return Tile.pressureBlockTile;
            case 0xFFFFFFFF: return Tile.invisableWall;
            case 0xFF000000: return Tile.invisableFloor;
            //

            default:
            return Tile.voidTile;
        }
    }

    public void setTile(int x, int y, int z, int[][] tiles, int color)
    {
        if(x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= floorTiles.length){return;}
        else{tiles[z][x+y*width] = color;}
    }

    public void setTile(int x, int y, int z, int color)
    {
        if(x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= floorTiles.length){return;}
        else
        {
            if(color == 0x00000000)
            {
                floorTiles[z][x+y*width] = color;
                wallTiles[z][x+y*width] = color;
            }
            else if(getTile(color).solid())
            {
                wallTiles[z][x+y*width] = color;
            }
            else{floorTiles[z][x+y*width] = color;}
        }
    }

    public void destroyTile(int x, int y, int z, int[][] tiles)
    {
        tiles[z][x+y*width] = 0x00000000;
    }
}
