package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class FireUp extends Item
{
    public FireUp(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.fireUpAnim,32,32,12, 6);
    }

    public void collect(Player player)
    {
        player.fires++;//Adds one to the maximum amount of Bombs the Player can hold
        collect();
    }

    // @Override
    // public Item getInstance()
    // {
        // return new FireUp((int)x, (int)y, (int)z);
    // }
}
