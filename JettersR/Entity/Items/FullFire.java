package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class FullFire extends Item
{
    public AnimatedSprite fullFire = new AnimatedSprite(SpriteSheet.fullFireAnim,32,32,12, 6);
    public FullFire(int x, int y, int z)
    {
        super(x,y,z);
        
        itemAnim = new AnimatedSprite(SpriteSheet.fullFireAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.fires = player.maxFires;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new FullFire((int)x, (int)y, (int)z);
    // }
}
