package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class DetonatorPerk extends Item
{
    public DetonatorPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.detonatorAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        //player.bombPass = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new DetonatorPerk((int)x, (int)y, (int)z);
    // }
}
