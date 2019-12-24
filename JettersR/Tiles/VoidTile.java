package JettersR.Tiles;

import JettersR.*;

public class VoidTile extends Tile
{
    public VoidTile()
    {
        super(null);
    }
    
    public boolean isFloor()//VoidTiles are considered "Empty Space" that the player can "fall" through
    {
        return false;
    }

    public void render(int x, int y, int z, Screen screen){}
    
    public void renderScale(int x, int y, int z, float scale, Screen screen){}
}
