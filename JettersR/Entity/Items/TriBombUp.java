package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class TriBombUp extends Item
{
    public TriBombUp(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.triBombUpAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.bombs += 3;//Adds one to the maximum amount of Bombs the Player can hold
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new TriBombUp((int)x, (int)y, (int)z);
    // }
}
