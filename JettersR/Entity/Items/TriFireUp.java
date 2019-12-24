package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class TriFireUp extends Item
{
    public TriFireUp(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.triFireUpAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.fires += 3;//Adds one to the maximum amount of Bombs the Player can hold
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new TriFireUp((int)x, (int)y, (int)z);
    // }
}
