package JettersR;


public class TileCoordinate
{
    private int x,y;
    private final int TILE_SIZE = 32;
    
    public TileCoordinate(int x, int y, int xOffset, int yOffset)
    {
        this.x = (x * TILE_SIZE) + xOffset;
        this.y = (y * TILE_SIZE) + yOffset;
    }
    
    public int x()
    {
        return x;
    }
    
    public int y()
    {
        return y;
    }
    
    public int [] xy()
    {
        int [] r = new int[2];
        r[0] = x;
        r[1] = y;
        return r;
    }
}
