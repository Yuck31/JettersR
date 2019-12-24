package JettersR.Tiles;
/**
 * 
 */
import JettersR.*;
import java.awt.Rectangle;

public class SlopeTile extends Tile
{
    public Rectangle bounds = new Rectangle(0,0,(31),(31));
    public SlopeTile(Sprite sprite, int yOffset, Direction dir)
    {
        super(sprite);
        this.dir = dir;
        this.yOffset = yOffset;
        
        if(dir == Direction.LEFT || dir == Direction.RIGHT){horizontal = true;}
        else if(dir == Direction.UP || dir == Direction.DOWN){vertical = true;}
    }

    public boolean solid()
    {
        return false;
    }
    
    public boolean isSlope()
    {
        return true;
    }
    
    public boolean isFloor()
    {
        return true;
    }
}
