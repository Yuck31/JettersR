package JettersR.Tiles;
/**
 * Write a description of class InvisableSolidTile here.
 *
 * Luke Sullivan
 * 7/12/19
 */
import JettersR.*;

public class InvisableSolidTile extends SolidTile
{
    public InvisableSolidTile(int boundsW, int boundsH)
    {
        super(null,0,boundsW,boundsH);
    }

    public void render(int x, int y, Screen screen){}//Well, this is an invisable tile... so it shouldn't render anything!

    public void renderScale(int x, int y, int z, float scale, Screen screen){}
    
    public boolean solid()
    {
        return true;
    }
    
    public boolean breakable()
    {
        return false;
    }
}
