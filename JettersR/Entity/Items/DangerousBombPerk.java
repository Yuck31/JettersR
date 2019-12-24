package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class DangerousBombPerk extends Item
{
    public DangerousBombPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.dangerBombItemAnim,32,32,12, 6);
    }

    public void collect(Player player)
    {
        if(!player.collectedBombType[player.type_dangerousBomb]){player.equipedBombType = player.type_dangerousBomb;}
        player.collectedBombType[player.type_dangerousBomb] = true;
        player.haveBombType[player.type_dangerousBomb] = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new DangerousBombPerk((int)x, (int)y, (int)z);
    // }
}
