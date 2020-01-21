package JettersRLevelEditor;
/**
 * Sprites for the Level Editor
 *
 * @author: Luke Sullivan
 * @1/4/20
 */
import JettersR.*;

public class LE_Sprite
{
    public static Sprite tileBar0 = new Sprite(896, 4, 0, 0, LE_SpriteSheet.tileBar);
    public static Sprite tileBar1 = new Sprite(896, 50, 0, 1, LE_SpriteSheet.tileBar);
    public static Sprite tileKeyBar = new Sprite("/JettersRLevelEditor/Images/TileKeyBar.png", 208, 454);
    //
    public static Sprite testClip0 = new Sprite(76, 32, 1, 1, LE_SpriteSheet.testClip);
    public static Sprite testClip1 = new Sprite(76, 32, 2, 1, LE_SpriteSheet.testClip);
    public static Sprite testClip2 = new Sprite(76, 32, 3, 1, LE_SpriteSheet.testClip);
    public static Sprite testClipBar0 = new Sprite(76, 32, 1, 0, LE_SpriteSheet.testClip);
    public static Sprite testClipBar1 = new Sprite(76, 32, 2, 0, LE_SpriteSheet.testClip);
    public static Sprite testClipBar2 = new Sprite(76, 32, 3, 0, LE_SpriteSheet.testClip);
    //
    public static Sprite swapTiles0 = new Sprite(28, 15, 0, 0, LE_SpriteSheet.heldTileSprites);
    public static Sprite swapTiles1 = new Sprite(28, 15, 0, 1, LE_SpriteSheet.heldTileSprites);
    public static Sprite heldTile0 = new Sprite(36, 52, 1, 0, LE_SpriteSheet.heldTileSprites);
    public static Sprite heldTile1 = new Sprite(36, 52, 2, 0, LE_SpriteSheet.heldTileSprites);
    //
}
