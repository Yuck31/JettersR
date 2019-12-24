package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class SpeedUp extends Item
{
    public SpeedUp(int x, int y, int z)
    {
        super(x,y,z);
        
        itemAnim = new AnimatedSprite(SpriteSheet.speedUpAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.speed += 0.2f;//Adds one to the maximum amount of Bombs the Player can hold
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new SpeedUp((int)x, (int)y, (int)z);
    // }
}
