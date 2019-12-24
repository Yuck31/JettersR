package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class SkullItem extends Item
{
    public Disease disease = Disease.NONE;
    public SkullItem(int x, int y, int z, Disease disease)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.bombPassAnim, 32, 32, 12, 6);
        this.disease = disease;
    }
    
    public void collect(Player player)
    {
        player.disease = disease;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new SkullItem((int)x, (int)y, (int)z, disease);
    // }
}
