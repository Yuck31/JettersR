package JettersR.Tiles;
/**
 * Write a description of class InvisableSolidTile here.
 *
 * Luke Sullivan
 * 8/25/19
 */
import JettersR.*;

public class InvisableFloorTile extends FloorTile
{
    public InvisableFloorTile()
    {
        super(null, 0);
    }

    public void render(int x, int y, int z, Screen screen){}//Well, this is an invisable tile... so it shouldn't render anything!
    
    public void renderScale(int x, int y, int z, float scale, Screen screen){}
    
    public boolean solid()
    {
        return false;
    }
}
