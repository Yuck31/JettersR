package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class ClusterBombPerk extends Item
{
    public ClusterBombPerk(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.clusterBombItemAnim,32,32,12, 6);
    }

    public void collect(Player player)
    {
        if(!player.collectedBombType[player.type_clusterBomb]){player.equipedBombType = player.type_clusterBomb;}
        player.collectedBombType[player.type_clusterBomb] = true;
        player.haveBombType[player.type_clusterBomb] = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new ClusterBombPerk((int)x, (int)y, (int)z);
    // }
}
