package JettersR.Tiles;
/**
 * Invisable Solid Tile...
 *
 * Luke Sullivan
 * 1/3/20
 */
import JettersR.*;

public class InvisableSolidTile extends SolidTile
{
    public InvisableSolidTile(int boundsW, int boundsH)
    {
        super(Sprite.floorBlock1, -16, boundsW, boundsH);
    }

    @Override
    public void render(int x, int y, int z, Screen screen){}//Well, this is an invisable tile... so it shouldn't render anything!

    @Override
    public void render(int x, int y, Screen screen)
    {
        screen.renderTranslucentSprite(x, y-(yOffset/2), 50f, sprite, false);
    }

    @Override
    public void render(int x, int y, int z, boolean show, Screen screen)//For the Level Editor
    {
        if(show)
        {
            screen.renderTranslucentSprite((x << 5), ((y << 5)+yOffset)-(z*16), 50f, sprite, true);
        }
    }

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
