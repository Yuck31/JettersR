package JettersR;

import JettersR.*;
import JettersR.Mode7Entities.*;
import JettersR.Entity.Statics.*;
import JettersR.Entity.Mob.*;
import JettersR.Mode7Entities.Mobs.*;
import JettersR.Mode7Entities.Items.*;
import JettersR.Entity.Particle.*;
import JettersR.Tiles.*;
import JettersR.GameStates.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Mode7Level
{
    public Screen screen;
    protected int width, height;
    public int[] entitySpawns;

    public List<Mode7Entity> entities7 = new ArrayList<Mode7Entity>();;//We use an array pf entities for floor management
    public List<Mode7Projectile> projectiles7 = new ArrayList<Mode7Projectile>();//Hiarchy of projectiles
    public List<Mode7Item> items7 = new ArrayList<Mode7Item>();
    public List<Mode7PlayerJet> players7 = new ArrayList<Mode7PlayerJet>();

    public Comparator<Mode7Entity> yAxisSorter = new Comparator<Mode7Entity>()
        {
            @Override
            public int compare(Mode7Entity a, Mode7Entity b)
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

    public Comparator<Mode7Entity> zAxisSorter = new Comparator<Mode7Entity>()
        {
            public int compare(Mode7Entity a, Mode7Entity b)
            {
                int x = 0;//Defualt value in case Entity a's z-value and Entity b's z-value are EQUAL.
                if(a.getZ() > b.getZ())
                {
                    x = -1;//This is for if Entity a's z-value is LESS than Enity b's z-value.
                }
                else if(a.getZ() < b.getZ())
                {
                    x = 1;//This is for if Entity a's z-value is GREATER than Enity b's z-value.
                }
                return x;
            }
        };

    public Mode7Level ()
    {
        
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

            }
        }
    }

    //The following functions are simply meant for managing the level itself
    public void update()
    {
        for (int i = 0; i < entities7.size(); i++)
        {
            entities7.get(i).update();//Updates entities
        }
        try
        {
            entities7.sort(zAxisSorter);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        remove();
    }

    private void remove()
    {
        for (int i = 0; i < items7.size(); i++)
        {
            if(items7.get(i).isRemoved()){items7.remove(i);}
        }
        for (int i = 0; i < entities7.size(); i++)
        {
            if(entities7.get(i).isRemoved()){entities7.remove(i);}
        }
        for(int i = 0; i < projectiles7.size(); i++)
        {
            if(projectiles7.get(i).isRemoved()){projectiles7.remove(i);}
        }
        for(int i = 0; i < players7.size(); i++)
        {
            if(players7.get(i).isDead()){players7.remove(i);}
        }
    }

    public List<Mode7Projectile> getProjectiles()
    {
        return projectiles7;
    }

    public void render(Screen screen)//This ONLY renders tiles ON SCREEN
    {
        this.screen = screen;
        for (int i = 0; i < entities7.size(); i++)
        {
            entities7.get(i).render(screen);
        }
    }

    public void add(Mode7Entity e)
    {
        e.init(this);
        if(e instanceof Mode7PlayerJet)
        {
            players7.add((Mode7PlayerJet)e);
            entities7.add(e);
        }
        else if(e instanceof Mode7Projectile)
        {
            projectiles7.add((Mode7Projectile)e);
            entities7.add(e);
        }
        else if(e instanceof Mode7Item)
        {
            items7.add((Mode7Item)e);
            entities7.add(e);
        }
        else
        {
            entities7.add(e);
        }     
    }

    public List<Mode7PlayerJet> getPlayers()
    {
        return players7;
    }

    public int getPlayersSize()
    {
        return players7.size();
    }

    public List<Mode7Entity> getEntities(Mode7Entity e, int radius)
    {
        List<Mode7Entity> result = new ArrayList<Mode7Entity>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for(int i = 0;i<entities7.size();i++)
        {
            Mode7Entity entity = entities7.get(i);
            int x = (int)entity.getX();
            int y = (int)entity.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if(distance <= radius) {result.add(entity);}
        }
        return result;
    }

    public List<Mode7PlayerJet> getPlayers(Mode7Entity e, int radius)
    {
        List<Mode7PlayerJet> result = new ArrayList<Mode7PlayerJet>();
        int ex = (int)e.getX();
        int ey = (int)e.getY();
        for(int i = 0;i<players7.size();i++)
        {
            Mode7PlayerJet player = players7.get(i);
            int x = (int)player.getX();
            int y = (int)player.getY();
            int dx = Math.abs(x - ex);
            int dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx * dx) + (dy * dy));
            if(distance <= radius) {result.add(player);}
        }
        return result;
    }
}
