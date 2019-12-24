package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class PowerBombPerk extends Item
{
    public PowerBombPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.powerBombItemAnim,32,32,12, 6);
    }

    public void collect(Player player)
    {
        if(!player.collectedBombType[player.type_powerBomb]){player.equipedBombType = player.type_powerBomb;}
        player.collectedBombType[player.type_powerBomb] = true;
        player.haveBombType[player.type_powerBomb] = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new PowerBombPerk((int)x, (int)y, (int)z);
    // }
}
