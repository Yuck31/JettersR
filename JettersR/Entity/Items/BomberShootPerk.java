package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class BomberShootPerk extends Item
{
    public BomberShootPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.bomberShootAnim,32,32,12, 6);
    }

    public void collect(Player player)
    {
        if(!player.collectedSpecialType[player.type_bomberShoot]){player.equipedSpecial = player.type_bomberShoot;}
        player.collectedSpecialType[player.type_bomberShoot] = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new BomberShootPerk((int)x, (int)y, (int)z);
    // }
}
