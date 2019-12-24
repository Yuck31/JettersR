package JettersR.Mode7Entities.Mobs;

import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Mode7Entities.*;
import JettersR.Mode7Entities.Mobs.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

public abstract class Mode7Mob extends Mode7Entity//Mobs are a subType of Entities
{
    protected Sprite sprite;
    protected boolean moving = false;

    protected int health;
    //private List<Entity> entities = new ArrayList<Entity>();//Hiarchy of entities

    //public Rectangle bounds = new Rectangle(x,y,32,32);

    public Mode7Mob(int x, int y, int z)
    {
        super(x,y,z);
    }
    public void move(double xa, double ya)//Lets mob move
    {
        //System.out.println("Size: " + level.getProjectiles().size());
        if(xa > 0)//Moving Right
        {
            dir = dir.RIGHT;
        }
        if(xa < 0)//Moving Left
        {
            dir = dir.LEFT;
        }
        if(ya > 0)//Moving Down
        {
            dir = dir.DOWN;
        }
        if(ya < 0)//Moving Up
        {
            dir = dir.UP;
        }
    }
    
    public void fall()
    {
        
    }

    public abstract void update();//Template update method for all Mobs

    public abstract void render(Screen screen);//Template render method for all Mobs
}