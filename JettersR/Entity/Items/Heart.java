package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class Heart extends Item
{
    public AnimatedSprite heart = new AnimatedSprite(SpriteSheet.heartAnim,32,32,12, 6);
    public Heart(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.heartAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.hasHeart = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new Heart((int)x, (int)y, (int)z);
    // }
}
