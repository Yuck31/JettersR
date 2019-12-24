package JettersR.Entity.Mob;

import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Entity.*;
import JettersR.Entity.Particle.*;
import java.util.ArrayList;
import java.util.List;
abstract class Mob extends Entity//Mobs are a subType of Entities
{
    protected boolean moving = false;

    protected int health;

    public Mob(int x, int y, int z)
    {
        super(x,y,z);
    }
    
    public void setDir(double xa, double ya)
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
    
    public void fall(){}

    public abstract void update();//Template update method for all Mobs

    public abstract void render(Screen screen);//Template render method for all Mobs
}