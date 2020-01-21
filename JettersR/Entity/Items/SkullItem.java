package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.Entity.Mob.*;

public class SkullItem extends Item
{
    public Disease disease = Disease.NONE;
    public SkullItem(int x, int y, int z, Disease disease)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.skullItem0, 32, 32, 12, 6);
        this.disease = disease;
        itemCollect = AudioManager.sounds_skullCollect;
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
