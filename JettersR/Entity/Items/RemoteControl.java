package JettersR.Entity.Items;

import JettersR.*;
import JettersR.Entity.Mob.*;
public class RemoteControl extends Item
{
    public RemoteControl(int x, int y, int z)
    {
        super(x,y,z);

        itemAnim = new AnimatedSprite(SpriteSheet.remoteControlAnim,32,32,12, 6);
    }
    
    public void collect(Player player)
    {
        player.remoteControl = true;
        collect();
    }
    
    // @Override
    // public Item getInstance()
    // {
        // return new RemoteControl((int)x, (int)y, (int)z);
    // }
}
