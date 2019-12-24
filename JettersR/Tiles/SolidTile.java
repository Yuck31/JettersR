package JettersR.Tiles;

import JettersR.*;
import java.awt.Rectangle;

public class SolidTile extends Tile
{
    public Rectangle bounds = new Rectangle(0,0,(31),(31));
    public SolidTile(Sprite sprite, int yOffset)
    {
        super(sprite);
        this.yOffset = yOffset;
    }

    public SolidTile(Sprite sprite, int yOffset, int boundsW, int boundsH)
    {
        super(sprite);
        bounds.width = boundsW;
        bounds.height = boundsH;
        this.yOffset = yOffset;
    }

    public boolean solid()
    {
        return true;
    }

    public boolean breakable()
    {
        return false;
    }
}