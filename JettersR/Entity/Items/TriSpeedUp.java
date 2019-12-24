package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class TriSpeedUp extends Item
{
    public AnimatedSprite triSpeedUp = new AnimatedSprite(SpriteSheet.triSpeedUpAnim,32,32,12, 6);
    public TriSpeedUp(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.triSpeedUpAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.speed += 0.6f;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new TriSpeedUp((int)x, (int)y, (int)z);
    // }
}
