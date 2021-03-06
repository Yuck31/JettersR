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
        super(Sprite.floor1, 0);
    }

    @Override
    public void render(int x, int y, int z, Screen screen){}//Well, this is an invisable tile... so it shouldn't render anything!

    @Override
    public void render(int x, int y, Screen screen)
    {
        screen.renderTranslucentSprite(x, y-(yOffset/2), 50f, sprite, false);
    }

    @Override
    public void render(int x, int y, int z, boolean show, Screen screen)
    {
        if(show)
        {
            screen.renderTranslucentSprite((x << 5), ((y << 5)+yOffset)-(z*16), 50f, sprite, true);
        }
    }

    public void renderScale(int x, int y, int z, float scale, Screen screen){}

    public boolean solid()
    {
        return false;
    }
}
