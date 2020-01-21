package JettersR.Entity;

import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Items.*;
import java.util.Random;
import java.awt.Rectangle;
public class Entity implements Cloneable//An Entity in this project is ANYTHING that isn't a tile
{
    public enum Direction
    {
        UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT, NONE;
    }
    public enum Disease
    {
        NONE,
        SHORTFUSE,//Bombs blow up in 1 second instead of three
        LONGFUSE,//Bombs blow up in five seconds instead of three
        WCOMB,//"Weak Combustion Disease", Bombs are deployed at one fire
        OADS,//"Over-Active Deployment Syndrome", causes the player to uncontrollably place bombs whenever he can
        UADS,//"Under-Active Deployment Syndrome", Player can only place ONE bomb
        REVERSE,//Reverses Player's movement controls
        FOXTROT,//Player "Foxtrots" between fast and slow speeds
        MOLASSES,//Player moves REALLY SLOW
        LUDSPEED;//Player moves at LUDICROUS SPEED
    }
    public enum CollisionType
    {
        SOLID, BREAKABLE, HAZARD;
    }
    //public boolean solid = false;
    //public boolean breakable = false;
    protected Level level;
    public Random random = new Random();
    public float scale = 1f;
    public boolean rendered = false;

    public double x, y;
    public int z;//Since this is a 2D Game, this is just for RENDERING reasons(and floor management)
    public int zOffset;
    public double nx = 0, ny = 0;
    public int nz = 0;
    public int za = 0;
    public int width = 0, height = 0;
    public Sprite sprite = Sprite.none_Icon;
    private boolean removed = false;

    public int tx = 0;
    public int ty = 0;
        
    public Direction dir = Direction.NONE;
    public int dirX;
    public int dirY;
    public boolean hopping = false;
    public boolean rolling = false;
    public int hopTime;
    public boolean forced = false;
    public int forceMulti;
    public int terminalVelocity = 0;//Used for falling to lower floors

    public boolean destroyFlag = false;
    public boolean crushFlag = false;
    public boolean collected = false;
    protected double speed, range, damage;

    protected boolean bouncing = false;
    protected int bounceNum = 0;
    protected int[] bounceHeight =//15 Numbers slotted 0-14
        {
            0,2,4,6,7,8,8,//Rising
            8,7,6,4,2,1,0,0//Falling
        };

    public Rectangle bounds;

    public Entity(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;

        bounds = new Rectangle(0, 0, width, height);//Default Rectangle bounds
    }

    public Rectangle getCollisionBounds(double xOffset, double yOffset)
    {
        return new Rectangle((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset), bounds.width, bounds.height);
    }
    
    public String getClassName(){return "Entity";}
    public static String className(){return "Entity";}

    public void update(){}

    public void render(Screen screen){}

    public void renderScale(Screen screen, float scale){}

    public void remove()//Remove entity from level
    {
        removed = true;//Entity list in Level class specifically checks for this boolean
    }

    public int getX()
    {
        return (int)x;
    }

    public int getY()
    {
        return (int)y;
    }

    public int getZ()
    {
        return z;
    }

    public int getZOffset()
    {
        return zOffset;
    }

    public int getBoundsX()
    {
        return bounds.x;
    }

    public int getBoundsY()
    {
        return bounds.y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public int tileX()
    {
        return (int)((x + 31)/32);
    }

    public int tileY()
    {
        return (int)((y + 31)/32);
    }

    public Entity entityCollision(double xa, double ya, int za)//This is used for returning EXACTLY what entity is being collided with. Used for comparing two entites of the same type (ex: two solid objects)
    {
        if(za < 0){return new Entity(0, 0, 0);}
        Entity entity = new Entity(0, 0, 0);
        for(Entity e : level.entities[za])
        {
            if(e.equals(this) || (!e.solid() && !e.breakable() && !e.hazard()))
            {
                continue;//Ensures an entity dosn't check with itself
            }
            if(e.getCollisionBounds(0,0).intersects(getCollisionBounds(xa, ya)) && e.getZ() == za)
            {
                entity = e;
                if(!e.hazard()){return entity;}
            }
        }
        // if(entity == null)
        // {
            // for(Projectile p : level.projectiles[za])
            // {
                // if(p.equals(this))
                // {
                    // continue;//Ensures an entity dosn't collide with itself
                // }
                // if(p.getCollisionBounds(0,0).intersects(getCollisionBounds(xa, ya)) && p.getZ() == za)
                // {
                    // entity = p;
                    // return entity;
                // }
            // }
        // }
        return entity;
    }

    //
    public Entity[] entityCollisions(double xa, double ya, int za)
    {
        if(za < 0){return new Entity[]{new Entity(0, 0, 0), new Entity(0, 0, 0), new Entity(0, 0, 0), new Entity(0, 0, 0), new Entity(0, 0, 0)};}
        Entity[] entities = new Entity[]{new Entity(0, 0, 0), new Entity(0, 0, 0), new Entity(0, 0, 0), new Entity(0, 0, 0), new Entity(0, 0, 0)};
        byte entCount = 0;
        for(Entity e : level.entities[za])
        {
            if(e.equals(this) || (!e.solid() && !e.breakable() && !e.hazard()))
            {
                continue;//Ensures an entity dosn't check with itself
            }
            if(e.getCollisionBounds(0,0).intersects(getCollisionBounds(xa, ya)) && e.getZ() == za)
            {
                entities[entCount] = e;
                entCount++;
                if(entCount >= entities.length){break;}
            }
        }
        return entities;
    }    

    public boolean isolatedCollision(Entity[] entities, String className)//This checks if an entity in an array is an instance of a class
    {
        for(byte i = 0; i < entities.length; i++)
        {
            if(entities[i].equals(this)){continue;}
            if(entities[i].getClassName() == className)
            {
                return true;
            }
        }
        return false;
    }

    public boolean collisionType(Entity[] entities, CollisionType colType)//This checks if an entity in an array is a perticular collision type
    {
        for(byte i = 0; i < entities.length; i++)
        {
            if(entities[i].equals(this)){continue;}
            switch(colType)
            {
                case SOLID:
                if(entities[i].solid()){return true;}
                break;

                case BREAKABLE:
                if(entities[i].breakable()){return true;}
                break;

                case HAZARD:
                if(entities[i].hazard()){return true;}
                break;
            }
        }
        return false;
    }
    
    public Entity isolatedEntity(Entity[] entities, String className)//This checks if an entity in an array is an instance of a class
    {
        for(byte i = 0; i < entities.length; i++)
        {
            if(entities[i].equals(this)){continue;}
            if(entities[i].getClassName() == className)
            {
                return entities[i];
            }
        }
        return new Entity(0, 0, 0);
    }
    
    public Entity isolatedEntity(Entity[] entities, CollisionType colType)
    {
        for(byte i = 0; i < entities.length; i++)
        {
            if(entities[i].equals(this)){continue;}
            switch(colType)
            {
                case SOLID:
                if(entities[i].solid())
                {
                    return entities[i];
                    //else{offset--;}
                }
                break;

                case BREAKABLE:
                if(entities[i].breakable())
                {
                    return entities[i];
                    //else{offset--;}
                }
                break;

                case HAZARD:
                if(entities[i].hazard())
                {
                    return entities[i];
                    //else{offset--;}
                }
                break;
            }
        }
        return new Entity(0, 0, 0);
    }
    //

    public Projectile projectileCollision(double xa, double ya, int za)
    {
        for(Projectile p : level.projectiles[za])
        {
            if(p.equals(this) || (!p.solid() && !p.breakable() && !p.hazard()))
            {
                continue;//Ensures an entity dosn't collide with itself
            }
            if(p.getCollisionBounds(0,0).intersects(getCollisionBounds(xa, ya)) && p.getZ() == za)
            {
                return p;
            }
        }
        return new Projectile(0, 0, 0);
    }

    public Item itemDetect(int xa, int ya, int za)//Detects ONLY Items
    {
        for(Item i : level.items[za])
        {
            if(i.equals(this))
            {
                continue;//Ensures an entity dosn't collide with itself
            }
            if(i.getCollisionBounds(0,0).intersects(getCollisionBounds(xa, ya)) && i.z == za)
            {
                return i;
            }
        }
        return null;
    }

    public Player playerDetect(double xa, double ya, int za)//Detects ONLY Players
    {
        for(Player i : level.players)
        {
            if(i.equals(this))
            {
                continue;//Ensures an entity dosn't collide with itself
            }
            if(i.getCollisionBounds(0,0).intersects(getCollisionBounds(xa, ya)) && i.z == za && !i.merged && !i.dead)
            {
                return i;
            }
        }
        return null;
    }

    public boolean hazardCollision(double xa, double ya, int za)//This is for if one wants to check for ONLY hazard collisions
    {
        if(za < 0){return false;}
        for(Entity e : level.entities[za])
        {
            if(e.equals(this))
            {
                continue;//Ensures an entity dosn't check with itself
            }
            if(e.getCollisionBounds(0,0).intersects(getCollisionBounds(xa, ya)) && e.getZ() == za && e.hazard())
            {
                return true;
            }
        }
        return false;
    }

    public Tile tileCollision(Entity e, Direction d, int side, double xa, double ya, int za)//side: 0[]1, 2 = Both
    {
        if(e == null || za < 0 || level == null || d == null){return Tile.voidTile;}
        //int tx = 0;
        //int ty = 0;
        boolean both = false;
        switch(d)
        {
            case UP:
            ty = (int)(e.y+ya+e.bounds.y)/32;
            switch(side)
            {
                case 2: both = true;
                case 0://LEFT
                tx = (int)(e.x+e.bounds.x)/32;
                if(level.getTile(tx, ty, za, level.wallTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.wallTiles);}
                if(!both){break;}

                case 1://RIGHT
                tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
                break;
            }
            break;

            case RIGHT:
            tx = (int)(e.x + xa + e.bounds.x + e.bounds.width) / 32;
            switch(side)
            {
                case 2: both = true;
                case 0://UP
                ty = (int)(e.y+e.bounds.y)/32;
                if(level.getTile(tx, ty, za, level.wallTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.wallTiles);}
                if(!both){break;}

                case 1://DOWN
                ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
                break;
            }
            break;

            case DOWN:
            ty = (int)(e.y+ya+e.bounds.y + e.bounds.height)/32;
            switch(side)
            {
                case 2: both = true;
                case 0://LEFT
                tx = (int)(e.x+e.bounds.x)/32;
                if(level.getTile(tx, ty, za, level.wallTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.wallTiles);}
                if(!both){break;}

                case 1://RIGHT
                tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
                break;
            }
            break;

            case LEFT:
            tx = (int)(e.x + xa + e.bounds.x) / 32;
            switch(side)
            {
                case 2: both = true;
                case 0://UP
                ty = (int)(e.y+e.bounds.y)/32;
                if(level.getTile(tx, ty, za, level.wallTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.wallTiles);}
                if(!both){break;}

                case 1://DOWN
                ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
                break;
            }
            break;

            case NONE:
            //UP
            ty = (int)(e.y+e.bounds.y)/32;
            tx = (int)(e.x+e.bounds.x)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }
            tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }

            //DOWN
            ty = (int)(e.y+e.bounds.y + e.bounds.height)/32;
            tx = (int)(e.x+e.bounds.x)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }
            tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }

            //LEFT
            tx = (int)(e.x + e.bounds.x) / 32;
            ty = (int)(e.y+e.bounds.y)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }
            ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }

            //RIGHT
            tx = (int)(e.x + e.bounds.x + e.bounds.width) / 32;
            ty = (int)(e.y+e.bounds.y)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }
            ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
            if(level.getTile(tx, ty, za, level.wallTiles).solid())
            {
                return level.getTile(tx, ty, za, level.wallTiles);
            }
            break;
        }
        return level.getTile(tx, ty, za, level.wallTiles);
    }

    public Tile floorCollision(Entity e, Direction d, int side, double xa, double ya, int za)//side: 0[]1, 2 = Both
    {
        if(e == null || za < 0 || level == null){return Tile.voidTile;}
        int tx = 0;
        int ty = 0;
        boolean both = false;
        switch(d)
        {
            case UP:
            ty = (int)(e.y+ya+e.bounds.y)/32;
            switch(side)
            {
                case 2: both = true;
                case 0://LEFT
                tx = (int)(e.x+e.bounds.x)/32;
                if(level.getTile(tx, ty, za, level.floorTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://RIGHT
                tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
                break;
            }
            break;

            case RIGHT:
            tx = (int)(e.x + xa + e.bounds.x + e.bounds.width) / 32;
            switch(side)
            {
                case 2: both = true;
                case 0://UP
                ty = (int)(e.y+e.bounds.y)/32;
                if(level.getTile(tx, ty, za, level.floorTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://DOWN
                ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
                break;
            }
            break;

            case DOWN:
            ty = (int)(e.y+ya+e.bounds.y + e.bounds.height)/32;
            switch(side)
            {
                case 2: both = true;
                case 0://LEFT
                tx = (int)(e.x+e.bounds.x)/32;
                if(level.getTile(tx, ty, za, level.floorTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://RIGHT
                tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
                break;
            }
            break;

            case LEFT:
            tx = (int)(e.x + xa + e.bounds.x) / 32;
            switch(side)
            {
                case 2: both = true;
                case 0://UP
                ty = (int)(e.y+e.bounds.y)/32;
                if(level.getTile(tx, ty, za, level.floorTiles) != Tile.voidTile)
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://DOWN
                ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
                break;
            }
            break;

            case NONE:
            //UP
            ty = (int)(e.y+e.bounds.y)/32;
            tx = (int)(e.x+e.bounds.x)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }

            //DOWN
            ty = (int)(e.y+e.bounds.y + e.bounds.height)/32;
            tx = (int)(e.x+e.bounds.x)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }

            //LEFT
            tx = (int)(e.x + e.bounds.x) / 32;
            ty = (int)(e.y+e.bounds.y)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }

            //RIGHT
            tx = (int)(e.x + e.bounds.x + e.bounds.width) / 32;
            ty = (int)(e.y+e.bounds.y)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isFloor())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            break;
        }
        return level.getTile(tx, ty, za, level.floorTiles);
    }

    public Tile slopeCollision(Entity e, Direction d, int side, double xa, double ya, int za)
    {
        if(e == null || za < 0 || level == null){return Tile.voidTile;}
        int tx = 0;
        int ty = 0;
        boolean both = false;
        switch(d)
        {
            case UP:
            ty = (int)(e.y+ya+e.bounds.y)/32;
            switch(side)
            {
                case 2: both = true;
                case 0://LEFT
                tx = (int)(e.x+e.bounds.x)/32;
                if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://RIGHT
                tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
                break;
            }
            break;

            case RIGHT:
            tx = (int)(e.x + xa + e.bounds.x + e.bounds.width) / 32;
            switch(side)
            {
                case 2: both = true;
                case 0://UP
                ty = (int)(e.y+e.bounds.y)/32;
                if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://DOWN
                ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
                break;
            }
            break;

            case DOWN:
            ty = (int)(e.y+ya+e.bounds.y + e.bounds.height)/32;
            switch(side)
            {
                case 2: both = true;
                case 0://LEFT
                tx = (int)(e.x+e.bounds.x)/32;
                if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://RIGHT
                tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
                break;
            }
            break;

            case LEFT:
            tx = (int)(e.x + xa + e.bounds.x) / 32;
            switch(side)
            {
                case 2: both = true;
                case 0://UP
                ty = (int)(e.y+e.bounds.y)/32;
                if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
                {return level.getTile(tx, ty, za, level.floorTiles);}
                if(!both){break;}

                case 1://DOWN
                ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
                break;
            }
            break;

            case NONE:
            //UP
            ty = (int)(e.y+e.bounds.y)/32;
            tx = (int)(e.x+e.bounds.x)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }

            //DOWN
            ty = (int)(e.y+e.bounds.y + e.bounds.height)/32;
            tx = (int)(e.x+e.bounds.x)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            tx = (int)(e.x+e.bounds.x+e.bounds.width)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }

            //LEFT
            tx = (int)(e.x + e.bounds.x) / 32;
            ty = (int)(e.y+e.bounds.y)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }

            //RIGHT
            tx = (int)(e.x + e.bounds.x + e.bounds.width) / 32;
            ty = (int)(e.y+e.bounds.y)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            ty = (int)(e.y+e.bounds.y+e.bounds.height)/32;
            if(level.getTile(tx, ty, za, level.floorTiles).isSlope())
            {
                return level.getTile(tx, ty, za, level.floorTiles);
            }
            break;
        }
        return level.getTile(tx, ty, za, level.floorTiles);
    }

    public boolean solid()
    {
        return false;
    }

    public boolean breakable()
    {
        return false;
    }

    public boolean hazard()
    {
        return false;
    }

    public void updateVelocity(int xa, int ya){}

    public void move(){};

    public void move(int xa, int ya){}

    public void hop(int dirX, int dirY, boolean forced, int forceMulti){}

    public void bounce(){}

    public void bounce(int dirX, int dirY, boolean forced, int forceMulti){}

    public void roll(int dirX, int dirY){}

    public void die(){}

    public void Destroy(){}

    public void collect(){}

    public void collect(Player player){}

    public void pickUp(){}

    public void pickUp(Player player){}

    public void Throw(int dirX, int dirY, int throwMulti){}

    public boolean isRemoved()
    {
        return removed;
    }

    public void init(Level level)
    {
        this.level = level;
    }
    
    public Entity getInstance() throws CloneNotSupportedException
    {
        return (Entity)this.clone();
    }
}