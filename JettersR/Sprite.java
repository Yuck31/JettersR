package JettersR;

/**
 * This Class can use the SpriteSheet Class's SpriteSheets to make individual Sprites.
 * This is mostly used for objects that don't require updating.
 *
 * author: Luke Sullivan
 * Last Edit: 12/21/2019
 */
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Sprite
{
    public final int SIZE;//Size of Sprite
    private int x,y;
    public int width,height;
    public int[] pixels;
    protected SpriteSheet sheet;

    //Thanks to the fact this class exists, WE CAN CALL SPRITES
    //Sprite [name] = new Sprite(Size, column(x), row(y), sheet)

    //Misc.
    public static Sprite RGB_Bar = new Sprite(512, 32, 0, 0, SpriteSheet.RGB_Bar);
    public static Sprite RGB_Pointer = new Sprite(9, 9, 0, 0, SpriteSheet.RGB_Pointer);
    public static Sprite backGround = new Sprite(896, 0, 0, SpriteSheet.BackGround);
    public static Sprite controllerControls = new Sprite(260,0,0, SpriteSheet.controllerControls);
    public static Sprite keyBoardControls = new Sprite(260,0,0, SpriteSheet.keyboardControls);
    public static Sprite menuBackGround = new Sprite(1026, 0, 0, SpriteSheet.MenuBackGround);
    public static Sprite battleWinnerGround = new Sprite("/Images/BattleWinnerGround.png", 320, 32);
    public static Sprite itemTipsBoard = new Sprite(896, 504, 0, 0, SpriteSheet.itemTipsBoard);

    public static Sprite battleOptionUnselected = new Sprite(600, 22, 0, 0, SpriteSheet.battleMenuBars);
    public static Sprite battleOptionSelected = new Sprite(600, 22, 0, 1, SpriteSheet.battleMenuBars);

    public static Sprite discoLights = new Sprite(896, 504, 0, 0, SpriteSheet.discoLights);
    //End of Misc.

    //Tiles
    //All Maps
    //public static Sprite voidSprite = new Sprite(32, 0x1B87E0);
    public static Sprite floor0 = new Sprite(32, 32, 0, 0, SpriteSheet.tiles);
    public static Sprite floorBlock0 = new Sprite(32, 48, 1, 0, SpriteSheet.tiles);
    public static Sprite floorR0 = new Sprite(32, 32, 2, 0, SpriteSheet.tiles);
    public static Sprite floorBlockR0 = new Sprite(32, 48, 3, 0, SpriteSheet.tiles);
    public static Sprite floorL0 = new Sprite(32, 32, 4, 0, SpriteSheet.tiles);
    public static Sprite floorBlockL0 = new Sprite(32, 48, 5, 0, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockDL0 = new Sprite(32, 48, 6, 0, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockDR0 = new Sprite(32, 48, 7, 0, SpriteSheet.tiles);
    public static Sprite slantedFloorUL0 = new Sprite(32, 48, 0, 1, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockUL0 = new Sprite(32, 48, 1, 1, SpriteSheet.tiles);
    public static Sprite slantedFloorUR0 = new Sprite(32, 48, 2, 1, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockUR0 = new Sprite(32, 48, 3, 1, SpriteSheet.tiles);
    public static Sprite leftSlope0 = new Sprite(32, 48, 4, 1, SpriteSheet.tiles);
    public static Sprite rightSlope0 = new Sprite(32, 48, 5, 1, SpriteSheet.tiles);
    public static Sprite downSlope0 = new Sprite(32, 48, 6, 1, SpriteSheet.tiles);
    public static Sprite upSlope0 = new Sprite(32, 48, 7, 1, SpriteSheet.tiles);

    public static Sprite floor1 = new Sprite(32, 48, 0, 2, SpriteSheet.tiles);
    public static Sprite floorBlock1 = new Sprite(32, 48, 1, 2, SpriteSheet.tiles);
    public static Sprite floorR1 = new Sprite(32, 48, 2, 2, SpriteSheet.tiles);
    public static Sprite floorBlockR1 = new Sprite(32, 48, 3, 2, SpriteSheet.tiles);
    public static Sprite floorL1 = new Sprite(32, 48, 4, 2, SpriteSheet.tiles);
    public static Sprite floorBlockL1 = new Sprite(32, 48, 5, 2, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockDL1 = new Sprite(32, 48, 6, 2, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockDR1 = new Sprite(32, 48, 7, 2, SpriteSheet.tiles);
    public static Sprite slantedFloorUL1 = new Sprite(32, 48, 0, 3, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockUL1 = new Sprite(32, 48, 1, 3, SpriteSheet.tiles);
    public static Sprite slantedFloorUR1 = new Sprite(32, 48, 2, 3, SpriteSheet.tiles);
    public static Sprite slantedFloorBlockUR1 = new Sprite(32, 48, 3, 3, SpriteSheet.tiles);
    public static Sprite leftSlope1 = new Sprite(32, 48, 4, 3, SpriteSheet.tiles);
    public static Sprite rightSlope1 = new Sprite(32, 48, 5, 3, SpriteSheet.tiles);
    public static Sprite downSlope1 = new Sprite(32, 48, 6, 3, SpriteSheet.tiles);
    public static Sprite upSlope1 = new Sprite(32, 48, 7, 3, SpriteSheet.tiles);

    public static Sprite solidBlock = new Sprite(32, 48, 0, 4, SpriteSheet.tiles);
    public static Sprite slantedWallDL = new Sprite(32, 48, 1, 4, SpriteSheet.tiles);
    public static Sprite slantedWallDR = new Sprite(32, 48, 2, 4, SpriteSheet.tiles);
    public static Sprite slantedWallUL = new Sprite(32, 48, 3, 4, SpriteSheet.tiles);
    public static Sprite slantedWallUR = new Sprite(32, 48, 4, 4, SpriteSheet.tiles);
    public static Sprite backgroundTile = new Sprite(32, 48, 5, 4, SpriteSheet.tiles);
    //

    //Bomb Factory Tiles
    public static Sprite unPaintedTile = new Sprite(32, 0, 0, SpriteSheet.tiles0);
    public static Sprite paintedTile = new Sprite(32, 1, 0, SpriteSheet.tiles0);
    public static Sprite green1 = new Sprite(32, 2, 0, SpriteSheet.tiles0);
    public static Sprite green2 = new Sprite(32, 3, 0, SpriteSheet.tiles0);
    public static Sprite hardBlock = new Sprite(32, 48, 0, 1, SpriteSheet.tiles0);
    public static Sprite vertWall = new Sprite(32, 48, 2, 1, SpriteSheet.tiles0);
    public static Sprite horiWall0 = new Sprite(32, 48, 0, 2, SpriteSheet.tiles0);
    public static Sprite horiWall1 = new Sprite(32, 48, 1, 2, SpriteSheet.tiles0);
    public static Sprite horiWall2 = new Sprite(32, 48, 2, 2, SpriteSheet.tiles0);
    public static Sprite horiWall3 = new Sprite(32, 48, 3, 2, SpriteSheet.tiles0);
    public static Sprite upLeftCorner = new Sprite(32, 48, 0, 3, SpriteSheet.tiles0);
    public static Sprite upRightCorner = new Sprite(32, 48, 1, 3, SpriteSheet.tiles0);
    public static Sprite downLeftCorner = new Sprite(32, 48, 2, 3, SpriteSheet.tiles0);
    public static Sprite downRightCorner = new Sprite(32, 48, 3, 3, SpriteSheet.tiles0);

    public static Sprite backGroundTile1 = new Sprite(32, 48, 0, 4, SpriteSheet.tiles0);
    public static Sprite backGroundTile2 = new Sprite(32, 48, 1, 4, SpriteSheet.tiles0);
    public static Sprite backGroundTile3 = new Sprite(32, 48, 2, 4, SpriteSheet.tiles0);

    public static Sprite softBlock = new Sprite(32, 48, 1, 1, SpriteSheet.tiles0);
    //

    //Great Wall Tiles
    public static Sprite obsidianFloor = new Sprite(44, 48, 3, 0, SpriteSheet.greatWallTiles);
    public static Sprite obsidianBlock = new Sprite(44, 48, 3, 1, SpriteSheet.greatWallTiles);
    public static Sprite rockFloor = new Sprite(44, 48, 0, 2, SpriteSheet.greatWallTiles);
    public static Sprite rockBlock = new Sprite(44, 48, 1, 2, SpriteSheet.greatWallTiles);
    public static Sprite hotWall = new Sprite(44, 48, 1, 0, SpriteSheet.greatWallTiles);
    public static Sprite obsidianSlopeLeft = new Sprite(44, 48, 1, 1, SpriteSheet.greatWallTiles);
    public static Sprite obsidianSlopeRight = new Sprite(44, 48, 2, 1, SpriteSheet.greatWallTiles);
    public static Sprite obsidianWall = new Sprite(44, 48, 0, 1, SpriteSheet.greatWallTiles);

    public static Sprite gwBackGroundTile = new Sprite(44, 48, 2, 2, SpriteSheet.greatWallTiles);
    public static Sprite hardenedBody1 = new Sprite(44, 48, 3, 2, SpriteSheet.greatWallTiles);
    public static Sprite hardenedBody2 = new Sprite(44, 48, 0, 3, SpriteSheet.greatWallTiles);
    public static Sprite hardenedBody3 = new Sprite(44, 48, 1, 3, SpriteSheet.greatWallTiles);
    public static Sprite hardenedBody4 = new Sprite(44, 48, 2, 3, SpriteSheet.greatWallTiles);

    public static Sprite sandStoneBlock = new Sprite(44, 48, 2, 0, SpriteSheet.greatWallTiles);
    //

    //Power Zone Tiles
    public static Sprite powerZoneWall = new Sprite(32, 48, 0,0, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneHeightenedWall = new Sprite(32, 48, 1,0, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneHardBlock = new Sprite(32, 48, 2,0, SpriteSheet.powerZoneTiles);

    public static Sprite powerZoneFloorUL = new Sprite(32, 48, 0,1, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloorU = new Sprite(32, 48, 1,1, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloorUR = new Sprite(32, 48, 2,1, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloorL = new Sprite(32, 48, 0,2, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloor = new Sprite(32, 48, 1,2, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloorR = new Sprite(32, 48, 2,2, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloorDL = new Sprite(32, 48, 0,3, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloorD = new Sprite(32, 48, 1,3, SpriteSheet.powerZoneTiles);
    public static Sprite powerZoneFloorDR = new Sprite(32, 48, 2,3, SpriteSheet.powerZoneTiles);

    public static Sprite powerZoneBackground = new Sprite(896, 0, 0, SpriteSheet.powerZoneBackground);
    //

    //Rock Garden Tiles
    public static Sprite gardenGrass = new Sprite(32, 0, 0, SpriteSheet.rockGardenTiles);
    public static Sprite gardenMarbleTile = new Sprite(32, 1, 0, SpriteSheet.rockGardenTiles);
    public static Sprite gardenPole = new Sprite(32, 64, 2, 0, SpriteSheet.rockGardenTiles);
    public static Sprite gardenTallPole = new Sprite(32, 96, 3, 0, SpriteSheet.rockGardenTiles);
    public static Sprite gardenWallCube = new Sprite(32, 48, 0, 1, SpriteSheet.rockGardenTiles);

    public static Sprite gardenWallH1 = new Sprite(32, 48, 4, 0, SpriteSheet.rockGardenTiles);
    public static Sprite gardenWallH2 = new Sprite(32, 48, 0, 2, SpriteSheet.rockGardenTiles);
    public static Sprite gardenWallH3 = new Sprite(32, 48, 1, 2, SpriteSheet.rockGardenTiles);
    public static Sprite gardenWallV1 = new Sprite(32, 48, 2, 2, SpriteSheet.rockGardenTiles);
    public static Sprite gardenWallV2 = new Sprite(32, 48, 3, 2, SpriteSheet.rockGardenTiles);
    public static Sprite gardenWallV3 = new Sprite(32, 48, 4, 2, SpriteSheet.rockGardenTiles);
    //
    //End of Tiles

    //Players
    public static Sprite player_front = new Sprite(42, 0, 1, SpriteSheet.BomberFRONT);
    public static Sprite player_back = new Sprite(42, 0, 1, SpriteSheet.BomberBACK);
    public static Sprite player_left = new Sprite(42, 0, 1, SpriteSheet.BomberLEFT);
    public static Sprite player_right = new Sprite(42, 0, 1, SpriteSheet.BomberRIGHT);
    public static Sprite playerHold_front = new Sprite(42, 0, 3, SpriteSheet.BomberFRONT);
    public static Sprite playerHold_back = new Sprite(42, 0, 3, SpriteSheet.BomberBACK);
    public static Sprite playerHold_left = new Sprite(42, 0, 3, SpriteSheet.BomberLEFT);
    public static Sprite playerHold_right = new Sprite(42, 0, 3, SpriteSheet.BomberRIGHT);

    public static Sprite playerBounce_front = new Sprite(42, 6, 1, SpriteSheet.BomberFRONT);
    public static Sprite playerBounce_back = new Sprite(42, 1, 1, SpriteSheet.BomberBACK);
    public static Sprite playerBounce_left = new Sprite(42, 6, 1, SpriteSheet.BomberLEFT);
    public static Sprite playerBounce_right = new Sprite(42, 6, 1, SpriteSheet.BomberRIGHT);

    public static Sprite rcCooled = new Sprite(90, 70, 0, 0, SpriteSheet.BomberRevengeCart);
    public static Sprite rcCooling = new Sprite(90, 70, 5, 0, SpriteSheet.BomberRevengeCart);

    public static Sprite playerShadow = new Sprite(22, 0, 0, SpriteSheet.playerShadow);
    //End of Players

    //Particles
    public static Sprite grayCloudParticle0 = new Sprite(15, 0, 0, SpriteSheet.grayCloudParticles);
    public static Sprite grayCloudParticle1 = new Sprite(15, 1, 0, SpriteSheet.grayCloudParticles);
    //

    //UI
    //Misc.
    public static Sprite player_UI = new Sprite(55, 0, 0, SpriteSheet.BomberUI);

    public static Sprite white_UIBackground = new Sprite(74, 0, 0, SpriteSheet.UIBomberBackgrounds);
    public static Sprite black_UIBackground = new Sprite(74, 1, 0, SpriteSheet.UIBomberBackgrounds);
    public static Sprite red_UIBackground = new Sprite(74, 2, 0, SpriteSheet.UIBomberBackgrounds);
    public static Sprite blue_UIBackground = new Sprite(74, 3, 0, SpriteSheet.UIBomberBackgrounds);
    public static Sprite green_UIBackground = new Sprite(74, 4, 0, SpriteSheet.UIBomberBackgrounds);
    public static Sprite yellow_UIBackground = new Sprite(74, 5, 0, SpriteSheet.UIBomberBackgrounds);
    public static Sprite pink_UIBackground = new Sprite(74, 6, 0, SpriteSheet.UIBomberBackgrounds);
    public static Sprite lightBlue_UIBackground = new Sprite(74, 7, 0, SpriteSheet.UIBomberBackgrounds);

    public static Sprite bombs_Icon = new Sprite(22, 0, 0, SpriteSheet.UI_Icons);
    public static Sprite fires_Icon = new Sprite(22, 1, 0, SpriteSheet.UI_Icons);
    public static Sprite heart_off = new Sprite(22, 6, 0, SpriteSheet.UI_Icons);
    public static Sprite heart_on = new Sprite(22, 7, 0, SpriteSheet.UI_Icons);
    //

    //Bomb Types
    public static Sprite bomb_slot = new Sprite(22, 2, 0, SpriteSheet.UI_Icons);
    public static Sprite remoteControl_Icon = new Sprite(10, 0, 0, SpriteSheet.BombType_Icons);
    public static Sprite pierceBomb_Icon = new Sprite(10, 1, 0, SpriteSheet.BombType_Icons);
    public static Sprite bomb_Icon = new Sprite(22, 1, 0, SpriteSheet.BombType_Icons);
    public static Sprite powerBomb_Icon = new Sprite(22, 2, 0, SpriteSheet.BombType_Icons);
    public static Sprite clusterBomb_Icon = new Sprite(22, 3, 0, SpriteSheet.BombType_Icons);
    public static Sprite dangerousBomb_Icon = new Sprite(22, 4, 0, SpriteSheet.BombType_Icons);
    public static Sprite pierceBomb_Icon2 = new Sprite(22, 5, 0, SpriteSheet.BombType_Icons);
    //

    //Special Types
    public static Sprite special_slot = new Sprite(22, 5, 0, SpriteSheet.UI_Icons);
    public static Sprite bombPass_Icon2 = new Sprite(10, 0, 0, SpriteSheet.SpecialType_Icons);
    public static Sprite softBlockPass_Icon = new Sprite(10, 1, 0, SpriteSheet.SpecialType_Icons);
    public static Sprite none_Icon = new Sprite(1, 23, 0, SpriteSheet.SpecialType_Icons);
    public static Sprite bomberShoot_Icon = new Sprite(22, 2, 0, SpriteSheet.SpecialType_Icons);
    //

    //Bomb Transportation
    public static Sprite bombKickOff_Icon = new Sprite(22, 8, 0, SpriteSheet.UI_Icons);
    public static Sprite bombKickOn_Icon = new Sprite(22, 9, 0, SpriteSheet.UI_Icons);
    public static Sprite bombThrowOff_Icon = new Sprite(22, 10, 0, SpriteSheet.UI_Icons);
    public static Sprite bombThrowOn_Icon = new Sprite(22, 11, 0, SpriteSheet.UI_Icons);
    public static Sprite superPowerGlove_Icon = new Sprite(22, 12, 0, SpriteSheet.UI_Icons);
    public static Sprite bombPunchOff_Icon = new Sprite(22, 13, 0, SpriteSheet.UI_Icons);
    public static Sprite bombPunchOn_Icon = new Sprite(22, 14, 0, SpriteSheet.UI_Icons);

    public static Sprite bombPass_Icon = new Sprite(22, 15, 0, SpriteSheet.UI_Icons);
    //
    //End of UI

    //BOMBS
    public static Sprite Bomb0 = new Sprite(40, 0, 0, SpriteSheet.bomb);

    public static Sprite DangerousBomb0 = new Sprite(40, 0, 0, SpriteSheet.dangerousBomb);

    //FIRE
    //Center
    public static Sprite Fire_Center = new Sprite(96, 0, 0, SpriteSheet.bombFire);
    //Left
    public static Sprite Fire_Left = new Sprite(96, 0, 1, SpriteSheet.bombFire);
    //Up
    public static Sprite Fire_Up = new Sprite(96, 0, 3, SpriteSheet.bombFire);
    //Right
    public static Sprite Fire_Right = new Sprite(96, 0, 5, SpriteSheet.bombFire);
    //Down
    public static Sprite Fire_Down = new Sprite(96, 0, 7, SpriteSheet.bombFire);
    //End of FIRE

    //Items
    //"Ups and Maxes"
    public static Sprite bombUp = new Sprite(32, 0, 0, SpriteSheet.bombUp);
    public static Sprite fireUp = new Sprite(32, 0, 0, SpriteSheet.fireUp);
    public static Sprite speedUp = new Sprite(32, 0, 0, SpriteSheet.speedUp);
    public static Sprite triBombUp = new Sprite(32, 0, 0, SpriteSheet.triBombUp);
    public static Sprite triFireUp = new Sprite(32, 0, 0, SpriteSheet.triFireUp);
    public static Sprite triSpeedUp = new Sprite(32, 0, 0, SpriteSheet.triSpeedUp);
    public static Sprite fullFire = new Sprite(32, 0, 0, SpriteSheet.fullFire);
    public static Sprite heart = new Sprite(32, 0, 0, SpriteSheet.heart);
    //

    //Bomb Movement
    public static Sprite bombKick = new Sprite(32, 0, 0, SpriteSheet.bombKickItem);
    public static Sprite bombThrow = new Sprite(32, 0, 0, SpriteSheet.bombThrowItem);
    public static Sprite bombPunch = new Sprite(32, 0, 0, SpriteSheet.bombPunchItem);
    public static Sprite superPowerGlove = new Sprite(32, 0, 0, SpriteSheet.superPowerGloveItem);
    //

    //Bomb Types
    public static Sprite powerBomb = new Sprite(32, 0, 0, SpriteSheet.powerBombItem);
    public static Sprite remoteControl = new Sprite(32, 0, 0, SpriteSheet.remoteControlItem);
    public static Sprite pierceBomb = new Sprite(32, 0, 0, SpriteSheet.pierceBombItem);
    public static Sprite clusterBomb = new Sprite(32, 0, 0, SpriteSheet.clusterBombItem);
    public static Sprite dangerousBomb = new Sprite(32, 0, 0, SpriteSheet.dangerousBombItem);
    //

    //Special Types
    public static Sprite bomberShoot = new Sprite(32, 0, 0, SpriteSheet.bomberShootItem);
    public static Sprite detonator = new Sprite(32, 0, 0, SpriteSheet.detonatorItem);
    public static Sprite merger = new Sprite(32, 0, 0, SpriteSheet.mergerItem);
    //

    //Skull
    public static Sprite skullItem = new Sprite(32, 0, 0, SpriteSheet.skullItem);
    public static Sprite disease_longFuse =     new Sprite(10, 0, 0, SpriteSheet.diseaseParticle);
    public static Sprite disease_shortFuse =    new Sprite(10, 1, 0, SpriteSheet.diseaseParticle);
    public static Sprite disease_weakComb =     new Sprite(10, 2, 0, SpriteSheet.diseaseParticle);
    public static Sprite disease_uads =         new Sprite(10, 0, 1, SpriteSheet.diseaseParticle);
    public static Sprite disease_oads =         new Sprite(10, 1, 1, SpriteSheet.diseaseParticle);
    public static Sprite disease_reverse =      new Sprite(10, 2, 1, SpriteSheet.diseaseParticle);
    public static Sprite disease_foxtrot =      new Sprite(10, 0, 2, SpriteSheet.diseaseParticle);
    public static Sprite disease_molasses =     new Sprite(10, 1, 2, SpriteSheet.diseaseParticle);
    public static Sprite disease_ludSpeed =     new Sprite(10, 2, 2, SpriteSheet.diseaseParticle);
    public static Sprite diseaseParticle = new Sprite(32, 1, 0, SpriteSheet.diseaseParticle);
    //

    //Passive Items
    public static Sprite softBlockPass = new Sprite(32, 0, 0, SpriteSheet.softBlockPass);
    public static Sprite bombPass = new Sprite(32, 0, 0, SpriteSheet.bombPass);
    //
    //End of Items
    public static Sprite pressureBlockTile = new Sprite(32, 48, 0, 0, SpriteSheet.pressureBlock);
    public static Sprite pressureBlock0 = new Sprite(58, 3, 0, SpriteSheet.pressureBlock);
    public static Sprite pressureBlock1 = new Sprite(58, 7, 0, SpriteSheet.pressureBlock);

    //public static Sprite particle_normal = new Sprite(3, 0xFFFF7C00);

    protected Sprite (SpriteSheet sheet, int width, int height)
    {
        if(width == height) SIZE = width;
        else SIZE = -1;
        this.width = width;
        this.height = height;
        this.sheet = sheet;
    }

    public Sprite(int size,int x,int y, SpriteSheet sheet)//We need size of Sprite
    {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * size;//We can treat our sprites as a "Array" of sorts, so..
        this.y = y * size;//Same here
        this.sheet = sheet;
        load();
    }

    public Sprite(int width, int height ,int x,int y, SpriteSheet sheet)
    {
        SIZE = width;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        this.x = x * width;
        this.y = y * height;
        this.sheet = sheet;
        load();
    }

    public Sprite(int width, int height, int colour)
    {
        SIZE = -1;
        this.width = width;
        this.height = height;
        pixels = new int [width*height];
        setColour(colour);
    }

    public Sprite(int size, int colour)
    {
        SIZE = size;
        this.width = size;
        this.height = size;
        pixels = new int[SIZE*SIZE];
        setColour(colour);
    }

    public Sprite(int[] pixels, int width, int height)
    {
        if(width == height) SIZE = width;
        else SIZE = -1;
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        for(int i = 0; i < pixels.length; i++)
        {
            this.pixels[i] = pixels[i];
        }
    }
    
    public Sprite(String path, int width, int height)
    {
        SIZE = -1;
        this.width = width;
        this.height = height;
        pixels = new int[this.width * this.height];
        load(path);
    }

    public static Sprite scale(Sprite sprite, float scale)
    {
        if(scale == 1f){return sprite;}
        else
        {
            return new Sprite(scale(sprite.pixels, (int)(sprite.width), (int)(sprite.height), scale), (int)(sprite.width*scale), (int)(sprite.height*scale));
        }
    }

    public static int[] scale(int[] pixels, int width, int height, float scale)//This render method can rescale sprites by a float value
    {
        //if(scale <= 0.16f){return new int[width*height];}
        int[] result = new int[(int)((width*scale)*(height*scale))];
        float scaleCritY = scale;
        if(scale < 1f){scaleCritY = 1f;}
        float scaleCritX = scale;
        if(scale < 1f){scaleCritX *= 1.1;}
        for (int y = 0; y < height; y++)
        {
            for(int s = 0; s < scale; s++)//Without this, the sprite would have empty lines where certain y-pixels are supposed to go.
            {
                int ya = (int)((y*scale) +  (s));
                for (int x = 0; x < width*scaleCritX; x++)
                {
                    int xa = x;
                    if(xa < 0 || xa >= width*scale || ya < 0 || ya >= height*scale)// || yaa < 0 || yaa >= height)
                    {
                        continue;
                    }
                    //int col = sprite.pixels[(int)((x+y*sprite.getWidth())/scale)];
                    int col = 0x00000000;
                    if((int)(((x/scale)+(y)*(width))) >= 0 && (int)(((x/scale)+(y)*(width))) < pixels.length)
                    {
                        col = pixels[(int)(((x/scale)+(y)*(width)))];
                    }
                    if(col != 0x00000000 && xa + ya * (int)(width*scale) < result.length){result[xa + ya * (int)(width*scale)] = col;}
                }
            }
        }
        return result;
    }

    public static Sprite stretch(Sprite sprite, float scaleX, float scaleY)
    {
        if(scaleX == 1f && scaleY == 1f){return sprite;}
        else
        {
            return new Sprite(stretch(sprite.pixels, (int)(sprite.width), (int)(sprite.height), scaleX, scaleY), (int)(sprite.width*scaleX), (int)(sprite.height*scaleY));
        }
    }

    public static int[] stretch(int[] pixels, int width, int height, float scaleX, float scaleY)//This render method can rescale and stretch sprites with two float values
    {
        if(scaleX <= 0.16f || scaleY <= 0.16f){return new int[width*height];}
        int[] result = new int[(int)((width*scaleX)*(height*scaleY))];
        float scaleCritX = scaleX;
        if(scaleX < 1f){scaleCritX*= 1.1;}
        for (int y = 0; y < height; y++)
        {
            for(int s = 0; s < scaleY; s++)//Without this, the sprite would have empty lines where certain y-pixels are supposed to go.
            {
                int ya = (int)((y*scaleY) +  (s));
                for (int x = 0; x < width*scaleCritX; x++)
                {
                    int xa = x;
                    if(xa < 0 || xa >= width*scaleX || ya < 0 || ya >= height*scaleY)// || yaa < 0 || yaa >= height)
                    {
                        continue;
                    }
                    //int col = sprite.pixels[(int)((x+y*sprite.getWidth())/scale)];
                    int col = 0x00000000;
                    if((int)(((x/scaleX)+(y)*(width))) < pixels.length)
                    {
                        col = pixels[(int)(((x/scaleX)+(y)*(width)))];
                    }
                    if(col != 0x00000000){result[xa + ya * (int)(width*scaleX)] = col;}
                }
            }
        }
        return result;
    }

    public static Sprite rotate(Sprite sprite, double degrees)
    {
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, degrees), sprite.width, sprite.height);
    }

    public static int[] rotate(int[] pixels, int width, int height, double degrees)//ROTATES SPRITES!(or at least it should...)
    {
        double angle = degrees * (Math.PI/180);
        int[] result = new int[width * height];

        double nx_x = rot_x(-angle, 1.0, 0.0);
        double nx_y = rot_y(-angle, 1.0, 0.0);
        double ny_x = rot_x(-angle, 0.0, 1.0);
        double ny_y = rot_y(-angle, 0.0, 1.0);

        double x0 = rot_x(-angle, -width/2.0, -height/2.0)+width/2.0;
        double y0 = rot_y(-angle, -width/2.0, -height/2.0)+height/2.0;

        for(int y = 0; y<height; y++)
        {
            double x1 = x0;
            double y1 = y0;
            for(int x = 0; x < width; x++)
            {
                int xx = (int) x1;
                int yy = (int) y1;
                int col = 0;
                if(xx<0||xx>=width||yy<0||yy>=height){col = 0x00000000;}
                else {col = pixels[xx+yy*width];}
                result[x+y*width] = col;//Makes current slot in pixel array equal the new color
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }
        return result;//The way rotating sprites works is by "simply" moving all the pixels
        //in the Sprite to an angle's corresponding location(like moving from 3,4 to 4,3 or som'n).
    }

    private static double rot_x(double angle, double x, double y)
    {
        double cos = Math.cos(angle - Math.PI/2);
        double sin = Math.sin(angle - Math.PI/2);
        return x * cos + y * -sin;
    }

    private static double rot_y(double angle, double x, double y)
    {
        double cos = Math.cos(angle - Math.PI/2);
        double sin = Math.sin(angle - Math.PI/2);
        return x * sin + y * cos;
    }

    public static Sprite[] split(SpriteSheet sheet)//Method of extracting sprites for fonts
    {
        int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH*sheet.SPRITE_HEIGHT);
        Sprite[] sprites = new Sprite[amount];

        int current = 0;
        int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];

        for(int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++)//This loop get position of sprites
        {
            for(int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++)
            {
                for(int y = 0; y < sheet.SPRITE_HEIGHT; y++)//This looop creates sprite pixels
                {
                    for(int x = 0; x < sheet.SPRITE_WIDTH; x++)
                    {
                        int xo = x + xp * sheet.SPRITE_WIDTH;
                        int yo = y + yp * sheet.SPRITE_HEIGHT;
                        pixels[x+y*sheet.SPRITE_WIDTH] = sheet.getPixels()[(xo+yo*sheet.getWidth())];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
            }
        }
        return sprites;
    }

    private void setColour(int colour)
    {
        for(int i = 0; i < width * height; i++)
        {
            pixels[i] = colour;
        }
    }

    public static Sprite customPlayer(Sprite sprite, int[] swapColors)
    {
        return new Sprite(customPlayer(sprite, sprite.width, sprite.height, swapColors), sprite.width, sprite.height);
    }

    public static int[] customPlayer(Sprite sprite, int width, int height, int[] swapColors)
    {//SwapColors[0 = Face; 1 = Torso; 2 = Head; Arms, and Legs; 3 = Hands; 4 = HeadBall & Feet]
        int[] result = new int[width*height];

        int face2 = shadedColor(swapColors[0], -7),
        face3 = shadedColor(face2, -7),

        darkHeadColor = shadedColor(swapColors[2], -35),

        hand2 = shadedColor(swapColors[3], -28),
        hand3 = shadedColor(hand2, -30),

        headBall2 = shadedColor(swapColors[4], -28),
        headBall3 = shadedColor(headBall2, -30),
        headBall4 = shadedColor(headBall3, -42),
        headBall5 = shadedColor(headBall4, -28),
        headBall6 = shadedColor(headBall5, -28);
        for(int ya = 0; ya < sprite.height; ya++)
        {
            for(int xa = 0; xa < sprite.width; xa++)
            {
                switch(sprite.pixels[xa+(ya*sprite.width)])
                {
                    //Face
                    case 0xFFFFD9B0: result[xa+(ya*sprite.width)] = swapColors[0]; break;
                    case 0xFFFFCB92: result[xa+(ya*sprite.width)] = face2; break;
                    case 0xFFFFBD74: result[xa+(ya*sprite.width)] = face3; break;

                    //Torso
                    case 0xFF2B2B2B: result[xa+(ya*sprite.width)] = swapColors[1]; break;

                    //Head, arms, & legs
                    case 0xFF1A1A1A: result[xa+(ya*sprite.width)] = swapColors[2]; break;
                    case 0xFF000001: result[xa+(ya*sprite.width)] = darkHeadColor; break;

                    //Hands:
                    case 0xFFCECECE: result[xa+(ya*sprite.width)] = swapColors[3]; break;
                    case 0xFFB8B8B8: result[xa+(ya*sprite.width)] = hand2; break;
                    case 0xFFA0A0A0: result[xa+(ya*sprite.width)] = hand3; break;

                    //Feet & Headball, & Hands
                    case 0xFF7E7E7E: result[xa+(ya*sprite.width)] = swapColors[4]; break;
                    case 0xFF727272: result[xa+(ya*sprite.width)] = headBall2; break;
                    case 0xFF616161: result[xa+(ya*sprite.width)] = headBall3; break;
                    case 0xFF545454: result[xa+(ya*sprite.width)] = headBall4; break;
                    case 0xFF3F3F3F: result[xa+(ya*sprite.width)] = headBall5; break;
                    case 0xFF101010: result[xa+(ya*sprite.width)] = headBall6; break;

                    default: result[xa+(ya*sprite.width)] = sprite.pixels[xa+(ya*sprite.width)];
                }
            }
        }
        return result;
    }

    public static int shadedColor(int color, int brightness)
    {
        int alpha = color & 0xFF000000;
        int red = (color & 0x00FF0000) + (brightness*0x00010000);
        int green = (color & 0x0000FF00) + (brightness*0x00000100);
        int blue = (color & 0x000000FF) + (brightness*0x00000001);

        if(red > 0x00FF0000){red = 0x00FF0000;}//If red is higher than max red, make it equal max red.
        else if(red < 0x00010000){red = 0x00000000;}//Else if red is lower than one(black), make it black.

        if(green > 0x0000FF00){green = 0x0000FF00;}
        else if(green < 0x00000100){green = 0x00000000;}

        if(blue > 0x000000FF){blue = 0x000000FF;}
        else if(blue < 0x00000001){blue = 0x00000000;}

        return alpha | red | green | blue;
    }

    public static Sprite shadedSprite(Sprite sprite, int brightness)
    {
        int[] result = new int[sprite.width * sprite.height];
        for(int ya = 0; ya < sprite.height; ya++)
        {
            for(int xa = 0; xa < sprite.width; xa++)
            {
                result[xa + ya * sprite.width] = shadedColor(sprite.pixels[xa + ya + sprite.width], brightness);
            }
        }
        return new Sprite(result, sprite.width, sprite.height);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    private void load()
    {
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++)
            {
                pixels[x+y*SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
                //Scans EVERY PIXEL to a SINGLE SLOT IN THE ARRAY!
            }
        }
    }
    
    private void load(String path)
    {
        try
        {
            //Meant to get a file path to the image
            BufferedImage image = ImageIO.read(Sprite.class.getResource(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0,0,width,height,pixels,0,width);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
