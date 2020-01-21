package JettersR.Tiles;

import JettersR.*;
import java.awt.Rectangle;

public class Tile
{
    public enum Direction//Which direction the bottom of the slope faces.
    {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    public boolean horizontal = false;
    public boolean vertical = false;
    public Direction dir = Direction.NONE;
    
    protected int yOffset;
    public Sprite sprite;
    public Rectangle bounds = new Rectangle(0,0,(31),(31));

    //Tiles
    //Regular Map
    public static Tile green1 = new FloorTile(Sprite.green1, 0);
    public static Tile green2 = new FloorTile(Sprite.green2, 0);

    public static Tile hardBlock = new SolidTile(Sprite.hardBlock, -3, 31, 31);
    public static Tile vertWall = new SolidTile(Sprite.vertWall, -16, 31, 31);
    public static Tile horiWall0 = new SolidTile(Sprite.horiWall0, -16, 31, 31);
    public static Tile horiWall1 = new SolidTile(Sprite.horiWall1, -16, 31, 31);
    public static Tile horiWall2 = new SolidTile(Sprite.horiWall2, -16, 31, 31);
    public static Tile horiWall3 = new SolidTile(Sprite.horiWall3, -16, 31, 31);
    public static Tile upLeftCorner = new SolidTile(Sprite.upLeftCorner, -16,  31, 31);
    public static Tile upRightCorner = new SolidTile(Sprite.upRightCorner, -16, 31, 31);
    public static Tile downLeftCorner = new SolidTile(Sprite.downLeftCorner, -16, 31, 31);
    public static Tile downRightCorner = new SolidTile(Sprite.downRightCorner, -16, 31, 31);

    public static Tile backGroundTile1 = new FloorTile(Sprite.backGroundTile1, 0);
    public static Tile backGroundTile2 = new FloorTile(Sprite.backGroundTile2, 0);
    public static Tile backGroundTile3 = new FloorTile(Sprite.backGroundTile3, 0);
    //

    //Great Wall
    public static Tile obsidianFloor = new FloorTile(Sprite.obsidianFloor, 0);
    public static Tile obsidianBlock = new SolidTile(Sprite.obsidianBlock, 0, 31, 31);
    public static Tile rockFloor = new FloorTile(Sprite.rockFloor, 0);
    public static Tile rockBlock = new SolidTile(Sprite.rockBlock, 0, 31, 31);
    public static Tile hotWall = new SolidTile(Sprite.hotWall, -16, 31, 31);
    public static Tile obsidianSlopeLeft = new SlopeTile(Sprite.obsidianSlopeLeft, -16, Direction.LEFT);
    public static Tile obsidianSlopeRight = new SlopeTile(Sprite.obsidianSlopeRight, -16,  Direction.RIGHT);
    public static Tile obsidianWall = new SolidTile(Sprite.obsidianWall, -16, 31, 31);

    public static Tile gwBackGroundTile = new FloorTile(Sprite.gwBackGroundTile, 0);
    //

    //Power Zone
    public static Tile powerZoneWall = new SolidTile(Sprite.powerZoneWall, -16, 31, 31);
    public static Tile powerZoneHeightenedWall = new FloorTile(Sprite.powerZoneHeightenedWall, 0);
    public static Tile powerZoneHardBlock = new SolidTile(Sprite.powerZoneHardBlock, 0, 31, 31);

    public static Tile powerZoneFloorUL = new FloorTile(Sprite.powerZoneFloorUL, 0);
    public static Tile powerZoneFloorU = new FloorTile(Sprite.powerZoneFloorU, 0);
    public static Tile powerZoneFloorUR = new FloorTile(Sprite.powerZoneFloorUR, 0);
    public static Tile powerZoneFloorL = new FloorTile(Sprite.powerZoneFloorL, 0);
    public static Tile powerZoneFloor = new FloorTile(Sprite.powerZoneFloor, 0);
    public static Tile powerZoneFloorR = new FloorTile(Sprite.powerZoneFloorR, 0);
    public static Tile powerZoneFloorDL = new FloorTile(Sprite.powerZoneFloorDL, 0);
    public static Tile powerZoneFloorD = new FloorTile(Sprite.powerZoneFloorD, 0);
    public static Tile powerZoneFloorDR = new FloorTile(Sprite.powerZoneFloorDR, 0);
    //

    //Rock Garden
    public static Tile rockGardenGrass = new FloorTile(Sprite.gardenGrass, 0);
    public static Tile rockGardenMarbleTile = new FloorTile(Sprite.gardenMarbleTile, 0);

    public static Tile rockGardenWallCube = new SolidTile(Sprite.gardenWallCube, -16);
    public static Tile rockGardenWallH1 = new SolidTile(Sprite.gardenWallH1, -16);
    public static Tile rockGardenWallH2 = new SolidTile(Sprite.gardenWallH2, -16);
    public static Tile rockGardenWallH3 = new SolidTile(Sprite.gardenWallH3, -16);
    public static Tile rockGardenWallV1 = new SolidTile(Sprite.gardenWallV1, -16);
    public static Tile rockGardenWallV2 = new SolidTile(Sprite.gardenWallV2, -16);
    public static Tile rockGardenWallV3 = new SolidTile(Sprite.gardenWallV3, -16);
    //public static Tile rockGardenPole = new SolidTile(Sprite.gardenPole, -32);
    public static Tile rockGardenPole0 = new SolidTile(Sprite.gardenPole0, 0);
    public static Tile rockGardenPole1 = new SolidTile(Sprite.gardenPole1, -16);
    //public static Tile rockGardenTallPole = new SolidTile(Sprite.gardenTallPole, -64);
    public static Tile rockGardenTallPole0 = new SolidTile(Sprite.gardenTallPole0, 0);
    public static Tile rockGardenTallPole1 = new SolidTile(Sprite.gardenTallPole1, -16);
    public static Tile rockGardenTallPole2 = new SolidTile(Sprite.gardenTallPole2, -32);
    //

    //All Maps
    public static Tile floor0 =  new FloorTile(Sprite.floor0, 0);
    public static Tile floorBlock0 =  new SolidTile(Sprite.floorBlock0, -16);
    public static Tile floorR0 =  new FloorTile(Sprite.floorR0, 0);
    public static Tile floorBlockR0 =  new SolidTile(Sprite.floorBlockR0, -16);
    public static Tile floorL0 =  new FloorTile(Sprite.floorL0, 0);
    public static Tile floorBlockL0 = new SolidTile(Sprite.floorBlock0, -16);
    public static Tile slantedFloorBlockDL0 = new SolidTile(Sprite.slantedFloorBlockDL0, -16);
    public static Tile slantedFloorBlockDR0 = new SolidTile(Sprite.slantedFloorBlockDR0, -16);
    public static Tile slantedFloorUL0 = new FloorTile(Sprite.slantedFloorUL0, 0);
    public static Tile slantedFloorBlockUL0 =  new SolidTile(Sprite.slantedFloorBlockUL0 , -16);
    public static Tile slantedFloorUR0 = new FloorTile(Sprite.slantedFloorUR0, 0);
    public static Tile slantedFloorBlockUR0 = new SolidTile(Sprite.slantedFloorBlockUR0 , -16);
    public static Tile leftSlope0 =  new SlopeTile(Sprite.leftSlope0 , -16, Direction.LEFT);
    public static Tile rightSlope0 =  new SlopeTile(Sprite.rightSlope0, -16, Direction.RIGHT);
    public static Tile downSlope0 = new SlopeTile(Sprite.downSlope0, -16, Direction.DOWN);
    public static Tile upSlope0 =  new SlopeTile(Sprite.upSlope0, -16, Direction.UP);
    
    public static Tile floor1 = new FloorTile(Sprite.floor1, 0); 
    public static Tile floorBlock1 = new SolidTile(Sprite.floorBlock1, -16);
    public static Tile floorR1 = new FloorTile(Sprite.floorR1, 0); 
    public static Tile floorBlockR1 = new SolidTile(Sprite.floorBlockR1, -16);
    public static Tile floorL1 = new FloorTile(Sprite.floorL1, 0);
    public static Tile floorBlockL1 = new SolidTile(Sprite.floorBlockL1, -16);
    public static Tile slantedFloorBlockDL1 = new SolidTile(Sprite.slantedFloorBlockDL1, -16);
    public static Tile slantedFloorBlockDR1 = new SolidTile(Sprite.slantedFloorBlockDR1, -16);
    public static Tile slantedFloorUL1 = new FloorTile(Sprite.slantedFloorUL1, 0); 
    public static Tile slantedFloorBlockUL1 =  new SolidTile(Sprite.slantedFloorBlockUL1, -16);
    public static Tile slantedFloorUR1 = new FloorTile(Sprite.slantedFloorUR1, 0);
    public static Tile slantedFloorBlockUR1 = new SolidTile(Sprite.slantedFloorBlockUR1, -16);
    public static Tile leftSlope1 = new SlopeTile(Sprite.leftSlope1, -16, Direction.LEFT);
    public static Tile rightSlope1 = new SlopeTile(Sprite.rightSlope1, -16, Direction.RIGHT);
    public static Tile downSlope1 = new SlopeTile(Sprite.downSlope1, -16, Direction.DOWN);
    public static Tile upSlope1 = new SlopeTile(Sprite.upSlope1, -16, Direction.UP);
    
    public static Tile solidBlock = new SolidTile(Sprite.solidBlock, -16);
    public static Tile slantedWallDL =  new SolidTile(Sprite.slantedWallDL, -16);
    public static Tile slantedWallDR =  new SolidTile(Sprite.slantedWallDR, -16);
    public static Tile slantedWallUL =  new SolidTile(Sprite.slantedWallUL, -16);
    public static Tile slantedWallUR =  new SolidTile(Sprite.slantedWallUR, -16);
    public static Tile backgroundTile = new FloorTile(Sprite.backgroundTile, 0);
    
    public static Tile pressureBlockTile = new SolidTile(Sprite.pressureBlockTile, -16);
    
    public static Tile invisableFloor = new InvisableFloorTile();
    public static Tile invisableWall = new InvisableSolidTile(31, 31);
    public static Tile voidTile = new VoidTile();
    //
    //End of Tiles

    public Tile(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public Rectangle getCollisionBounds(double xOffset, double yOffset)
    {
        return new Rectangle((int)(bounds.x + xOffset), (int)(bounds.y + yOffset), bounds.width, bounds.height);
    }

    public void render(int x, int y, int z, Screen screen)
    {
        screen.renderSprite((x << 5), ((y << 5)+yOffset)-z*16, sprite, true);
    }
    
    public void render(int x, int y, int z, float trans, Screen screen)
    {
        screen.renderTranslucentSprite((x << 5), ((y << 5)+yOffset)-z*16, trans, sprite, true);
    }
    
    public void render(int x, int y, Screen screen)
    {
        screen.renderSprite(x, y, sprite, false);
    }
    
    public void render(int x, int y, int z, boolean show, Screen screen){render(x,y,z,screen);}//For the Level Editor
    
    public void renderScale(int x, int y, int z, float scale, Screen screen)
    {
        screen.renderSprite((int)((x << 5)*scale), (int)(((y << 5) + (((yOffset)-(z*16))))*scale), Sprite.scale(sprite, scale), true);
    }

    public boolean solid()
    {
        return false;
    }

    public Direction getDir()
    {
        return dir;
    }

    public boolean isSlope()
    {
        return false;
    }

    public boolean isFloor()
    {
        return true;
    }
}
