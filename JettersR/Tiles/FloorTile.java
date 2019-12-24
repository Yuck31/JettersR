package JettersR.Tiles;

import JettersR.*;

public class FloorTile extends Tile
{
    public FloorTile(Sprite sprite, int yOffset)
    {
        super(sprite);
        this.yOffset = yOffset;
    }
    
    public boolean solid()
    {
        return false;
    }
}