package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class PierceBombPerk extends Item
{
    public PierceBombPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.pierceBombItemAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.type_pierceBomb = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new PierceBombPerk((int)x, (int)y, (int)z);
    // }
}
