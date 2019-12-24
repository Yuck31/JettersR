package JettersR;

/**
 * This Class takes in SpriteSheets that are in the "Images" folder in the Project files.
 * This class can then take those SpriteSheets and "slice" them into multiple sprites.
 *
 * author: Luke Sullivan
 * Last Edit: 5/23/2019
 */

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class SpriteSheet//Slices sprite SHEETS to save memory
{
    private String path;
    public final int SIZE;
    public final int SPRITE_WIDTH, SPRITE_HEIGHT;
    public int width, height;
    public int[] pixels;

    //SpriteSheets(Regular)

    //Misc.
    public static SpriteSheet RGB_Bar = new SpriteSheet("/Images/RGB_Bar.png", 512, 32);
    public static SpriteSheet RGB_Pointer = new SpriteSheet("/Images/RGB_Pointer.png", 9);
    public static SpriteSheet menuPointer = new SpriteSheet("/Images/Menu_Pointer.png", 9);
    public static SpriteSheet brightnessBar = new SpriteSheet("/Images/BrightnessBar.png", 100, 8);
    public static SpriteSheet BackGround = new SpriteSheet("/Images/BackGround.png", 896);
    public static SpriteSheet controllerControls = new SpriteSheet("/Images/ControllerCONTROLS.png", 260);
    public static SpriteSheet keyboardControls = new SpriteSheet("/Images/KeyBoardCONTROLS.png", 260);
    public static SpriteSheet MenuBackGround = new SpriteSheet("/Images/MenuBackGround.png", 1026);
    public static SpriteSheet itemTipsBoard = new SpriteSheet("/Images/ItemTipsBoard.png", 896, 504);
    public static SpriteSheet battleMenuBars = new SpriteSheet("/Images/BattleMenuBar.png", 600, 44);
    
    public static SpriteSheet discoLights = new SpriteSheet("/Images/DiscoLights.png", 896, 504);
    //

    //User Interface(HUD)
    public static SpriteSheet BomberUI = new SpriteSheet("/Images/UI/UI_Bomber.png", 55);
    public static SpriteSheet UIBomberBackgrounds = new SpriteSheet("/Images/UI/UIBomberBackgrounds.png", 592, 74);
    public static SpriteSheet UI_Icons = new SpriteSheet("/Images/UI/UI_Icons.png", 352, 22);
    public static SpriteSheet BombType_Icons = new SpriteSheet("/Images/UI/BombType_Icons.png", 132, 22);
    public static SpriteSheet SpecialType_Icons = new SpriteSheet("/Images/UI/SpecialType_Icons.png", 66, 22);
    //

    //Level Assets(Backgounds, Tiles, and Objects)

    //All Maps
    public static SpriteSheet tiles = new SpriteSheet("/Levels/GenericTiles/GenericTiles.png", 256, 240);
    public static SpriteSheet pressureBlock = new SpriteSheet("/Images/PressureBlock.png", 464, 58);
    //

    //Regular Map
    public static SpriteSheet tiles0 = new SpriteSheet("/Levels/BombFactory/BombFactoryTILES.png", 128, 240);
    public static SpriteSheet softBlockDestroy = new SpriteSheet("/Images/SoftBlockDESTROY.png",240);
    //

    //Great Wall
    public static SpriteSheet greatWallTiles = new SpriteSheet("/Levels/GreatWall/GreatWallTILES.png", 176, 192);
    public static SpriteSheet sandStoneBlockDestroy = new SpriteSheet("/Images/SandStoneBlockDESTROY.png",240);
    //

    //Power Zone
    public static SpriteSheet powerZoneBackground = new SpriteSheet("/Levels/PowerZone/PowerZoneBACKGROUND.png", 896);
    public static SpriteSheet powerZoneTiles = new SpriteSheet("/Levels/PowerZone/PowerZoneTILES.png", 96, 192);
    //
    
    //Rock Garden
    public static SpriteSheet rockGardenTiles = new SpriteSheet("/Levels/RockGarden/RockGardenTiles.png", 160, 192);
    
    //

    //Players
    public static SpriteSheet BomberFRONT = new SpriteSheet("/Images/BomberPlayer/BomberFRONT.png", 672);
    public static SpriteSheet BomberRIGHT = new SpriteSheet("/Images/BomberPlayer/BomberRIGHT.png", 672);
    public static SpriteSheet BomberLEFT = new SpriteSheet("/Images/BomberPlayer/BomberLEFT.png", 672);
    public static SpriteSheet BomberBACK = new SpriteSheet("/Images/BomberPlayer/BomberBACK.png", 672);
    public static SpriteSheet BomberDEAD = new SpriteSheet("/Images/BomberPlayer/BomberDEAD.png", 1302, 42);
    
    public static SpriteSheet BomberRevengeCart = new SpriteSheet("/Images/BomberPlayer/RevengeCart.png", 540, 140);
    
    public static SpriteSheet playerShadow = new SpriteSheet("/Images/BomberPlayer/PlayerShadow.png", 22);
    
    public static SpriteSheet stunnedEffect = new SpriteSheet("/Images/BomberPlayer/StunnedEffect.png", 672, 20);
    //

    //Bombs
    public static SpriteSheet bomb = new SpriteSheet("/Images/Bomb.png", 320, 200);
    public static SpriteSheet punchedBomb = new SpriteSheet("/Images/PunchedBomb.png", 600, 40);
    public static SpriteSheet bombFire = new SpriteSheet("/Images/BombEXPLOSION.png", 864);
    public static SpriteSheet pierceFire = new SpriteSheet("/Images/PierceBombEXPLOSION.png", 864);

    public static SpriteSheet remoteBomb = new SpriteSheet("/Images/RemoteBomb.png", 320, 200);
    public static SpriteSheet powerBomb = new SpriteSheet("/Images/PowerBomb.png", 320, 200);
    public static SpriteSheet remotePowerBomb = new SpriteSheet("/Images/RemotePowerBomb.png", 320, 200);
    public static SpriteSheet pierceBomb = new SpriteSheet("/Images/PierceBomb.png", 160);
    public static SpriteSheet remotePierceBomb = new SpriteSheet("/Images/RemotePierceBomb.png", 160, 80);
    public static SpriteSheet clusterBomb = new SpriteSheet("/Images/ClusterBomb.png", 328, 164);
    public static SpriteSheet remoteClusterBomb = new SpriteSheet("/Images/RemoteClusterBomb.png", 410, 164);

    public static SpriteSheet dangerousBomb = new SpriteSheet("/Images/Dangerous_Bomb.png", 160);
    public static SpriteSheet remoteDangerousBomb = new SpriteSheet("/Images/RemoteDangerousBomb.png", 160, 80);
    public static SpriteSheet punchedDangerousBomb = new SpriteSheet("/Images/PunchedDangerousBomb.png", 600, 40);
    //

    //Items
    public static SpriteSheet itemSpawn = new SpriteSheet("/Images/Items/ItemSpawn.png", 108, 36);
    
    public static SpriteSheet bombUp = new SpriteSheet("/Images/Items/Bomb_Up.png", 384);
    public static SpriteSheet fireUp = new SpriteSheet("/Images/Items/Fire_Up.png", 384);
    public static SpriteSheet fullFire = new SpriteSheet("/Images/Items/FullFIRE.png", 384);
    public static SpriteSheet speedUp = new SpriteSheet("/Images/Items/Speed_Up.png", 384);
    public static SpriteSheet heart = new SpriteSheet("/Images/Items/HEART.png", 384);
    public static SpriteSheet triBombUp = new SpriteSheet("/Images/Items/Tri_Bomb_Up.png", 384);
    public static SpriteSheet triFireUp = new SpriteSheet("/Images/Items/Tri_Fire_Up.png", 384);
    public static SpriteSheet triSpeedUp = new SpriteSheet("/Images/Items/Tri_Speed_Up.png", 384);

    public static SpriteSheet bombKickItem = new SpriteSheet("/Images/Items/BombKickPerk.png", 384);
    public static SpriteSheet bombThrowItem = new SpriteSheet("/Images/Items/BombThrowPerk.png", 384);
    public static SpriteSheet superPowerGloveItem = new SpriteSheet("/Images/Items/SuperPOWERglove.png", 384);
    public static SpriteSheet bombPunchItem = new SpriteSheet("/Images/Items/BombPunchPerk.png", 384);
    public static SpriteSheet bomberShootItem = new SpriteSheet("/Images/Items/BomberSHOOTperk.png", 384);
    public static SpriteSheet detonatorItem = new SpriteSheet("/Images/Items/Detonator_Item.png", 384);
    public static SpriteSheet softBlockPass = new SpriteSheet("/Images/Items/SoftBlockPass.png", 384);
    public static SpriteSheet bombPass = new SpriteSheet("/Images/Items/BombPass.png", 384);

    public static SpriteSheet powerBombItem = new SpriteSheet("/Images/Items/PowerBombPerk.png", 384);
    public static SpriteSheet remoteControlItem = new SpriteSheet("/Images/Items/RemoteCONTROLperk.png", 384);
    public static SpriteSheet clusterBombItem = new SpriteSheet("/Images/Items/ClusterBombPerk.png", 384);
    public static SpriteSheet pierceBombItem = new SpriteSheet("/Images/Items/PierceBombPerk.png", 384);
    public static SpriteSheet dangerousBombItem = new SpriteSheet("/Images/Items/DangorousBombPerk.png", 384);

    public static SpriteSheet mergerItem = new SpriteSheet("/Images/Items/Merger_Item.png", 384);
    
    public static SpriteSheet itemCollect = new SpriteSheet("/Images/Items/ItemCollect.png", 108);
    public static SpriteSheet itemBurn = new SpriteSheet("/Images/Items/ItemBurn.png", 240);
    public static SpriteSheet itemCrush = new SpriteSheet("/Images/Items/ItemCrush.png", 1216, 64);
    
    public static SpriteSheet skullItem = new SpriteSheet("/Images/Items/SkullItem.png", 512, 64);
    public static SpriteSheet diseaseParticle = new SpriteSheet("/Images/DiseaseParticle.png", 64, 32);
    //
    
    //Enemies
    public static SpriteSheet higeHigeBandit = new SpriteSheet("/Images/Enemies/HigeHigeBandit.png", 203, 29);
    //
    
    //Switches
    public static SpriteSheet switches = new SpriteSheet("/Images/Switches.png", 96,256);
    //

    //Particles
    public static SpriteSheet grayCloudParticles = new SpriteSheet("/Images/Particles/GrayCloudParticles.png", 30, 15);
    //
    
    //End of Sprite Sheets(Regular)
    
    //SpriteSheets(Mode7)
    
    //Player(Jet)
    public static SpriteSheet Bomber_JET = new SpriteSheet("/Images/Mode7/Player/BomberJet.png", 1312, 164);
    public static SpriteSheet Bomber_JETFLAME = new SpriteSheet("/Images/Mode7/Player/BomberJetFLAME.png", 492, 164);
    //
    
    //Bombs(Jet)
    public static SpriteSheet jetBomb = new SpriteSheet("/Images/Mode7/Player/Bombs/Jet_Bomb.png", 270, 54);
    //
    
    //End of SpriteSheets(Mode7)

    //SubSheets(for animation)(Regular)
    
    //Players
    public static SpriteSheet Bomber_FRONT = new SpriteSheet(BomberFRONT, 0, 0, 16, 1, 42);
    public static SpriteSheet Bomber_RIGHT = new SpriteSheet(BomberRIGHT, 0, 0, 16, 1, 42);
    public static SpriteSheet Bomber_LEFT = new SpriteSheet(BomberLEFT, 0, 0, 16, 1, 42);
    public static SpriteSheet Bomber_BACK = new SpriteSheet(BomberBACK, 0, 0, 16, 1, 42);
    public static SpriteSheet BomberHold_FRONT = new SpriteSheet(BomberFRONT, 0, 2, 16, 1, 42);
    public static SpriteSheet BomberHold_RIGHT = new SpriteSheet(BomberRIGHT, 0, 2, 16, 1, 42);
    public static SpriteSheet BomberHold_LEFT = new SpriteSheet(BomberLEFT, 0, 2, 16, 1, 42);
    public static SpriteSheet BomberHold_BACK = new SpriteSheet(BomberBACK, 0, 2, 16, 1, 42);

    public static SpriteSheet Bomber_FRONTidle = new SpriteSheet(BomberFRONT, 0, 1, 6, 1, 42);
    public static SpriteSheet Bomber_RIGHTidle = new SpriteSheet(BomberRIGHT, 0, 1, 6, 1, 42);
    public static SpriteSheet Bomber_LEFTidle = new SpriteSheet(BomberLEFT, 0, 1, 6, 1, 42);
    public static SpriteSheet Bomber_BACKidle = new SpriteSheet(BomberBACK, 0, 1, 1, 1, 42);
    public static SpriteSheet BomberHold_FRONTidle = new SpriteSheet(BomberFRONT, 0, 3, 6, 1, 42);
    public static SpriteSheet BomberHold_RIGHTidle = new SpriteSheet(BomberRIGHT, 0, 3, 6, 1, 42);
    public static SpriteSheet BomberHold_LEFTidle = new SpriteSheet(BomberLEFT, 0, 3, 6, 1, 42);
    public static SpriteSheet BomberHold_BACKidle = new SpriteSheet(BomberBACK, 0, 3, 1, 1, 42);

    public static SpriteSheet Bomber_DEAD = new SpriteSheet(BomberDEAD, 0, 0, 31, 1, 42);
    
    public static SpriteSheet rcShootFront = new SpriteSheet(BomberRevengeCart, 0, 1, 5, 1, 90, 70);
    
    public static SpriteSheet rcJetL0 = new SpriteSheet(BomberRevengeCart, 0, 2, 4, 1, 45, 35);
    public static SpriteSheet rcJetR0 = new SpriteSheet(BomberRevengeCart, 0, 3, 4, 1, 45, 35);
    public static SpriteSheet rcJetL1 = new SpriteSheet(BomberRevengeCart, 4, 2, 4, 1, 45, 35);
    public static SpriteSheet rcJetR1 = new SpriteSheet(BomberRevengeCart, 4, 3, 4, 1, 45, 35);
    public static SpriteSheet rcJetL2 = new SpriteSheet(BomberRevengeCart, 8, 2, 4, 1, 45, 35);
    public static SpriteSheet rcJetR2 = new SpriteSheet(BomberRevengeCart, 8, 3, 4, 1, 45, 35);
    
    public static SpriteSheet stunnedEffectAnim = new SpriteSheet(stunnedEffect, 0, 0, 16, 1, 42, 20);
    //

    //Bombs and Fires
    public static SpriteSheet Bomb = new SpriteSheet(bomb, 0, 0, 4, 1, 40);
    public static SpriteSheet BombPunched = new SpriteSheet(punchedBomb, 0, 0, 15, 1, 40);
    public static SpriteSheet BombRollX = new SpriteSheet(bomb, 0, 1, 8, 1, 40);
    public static SpriteSheet BombRollY = new SpriteSheet(bomb, 0, 3, 6, 1, 40);

    public static SpriteSheet PowerBombAnim = new SpriteSheet(powerBomb, 0, 0, 4, 1, 40);
    public static SpriteSheet powerBombPunched = new SpriteSheet(punchedBomb, 0, 0, 15, 1, 40);
    public static SpriteSheet powerBombRollX = new SpriteSheet(powerBomb, 0, 1, 8, 1, 40);
    public static SpriteSheet powerBombRollY = new SpriteSheet(powerBomb, 0, 3, 6, 1, 40);
    public static SpriteSheet remotePowerBombAnim = new SpriteSheet(remotePowerBomb, 0, 0, 4, 1, 40);
    public static SpriteSheet remotePowerBombPunched = new SpriteSheet(punchedBomb, 0, 0, 15, 1, 40);
    public static SpriteSheet remotePowerBombRollX = new SpriteSheet(remotePowerBomb, 0, 1, 8, 1, 40);
    public static SpriteSheet remotePowerBombRollY = new SpriteSheet(remotePowerBomb, 0, 2, 6, 1, 40);
    public static SpriteSheet remotePowerBombBeep = new SpriteSheet(remotePowerBomb, 0, 4, 2, 1, 40);

    public static SpriteSheet PierceBombAnim = new SpriteSheet(pierceBomb, 0, 0, 4, 1, 40);
    public static SpriteSheet pierceBombPunched = new SpriteSheet(punchedBomb, 0, 0, 15, 1, 40);
    public static SpriteSheet remotePierceBombAnim = new SpriteSheet(remotePierceBomb, 0, 0, 4, 1, 40);
    public static SpriteSheet remotePierceBombPunched = new SpriteSheet(punchedBomb, 0, 0, 15, 1, 40);
    public static SpriteSheet remotePierceBombBeep = new SpriteSheet(remotePierceBomb, 0, 1, 2, 1, 40);

    public static SpriteSheet RemoteBombAnim = new SpriteSheet(remoteBomb, 0, 0, 4, 1, 40);
    public static SpriteSheet remoteBombPunched = new SpriteSheet(punchedBomb, 0, 0, 15, 1, 40);
    public static SpriteSheet RemoteBombRollX = new SpriteSheet(remoteBomb, 0, 1, 8, 1, 40);
    public static SpriteSheet RemoteBombRollY = new SpriteSheet(remoteBomb, 0, 2, 4, 1, 40);
    public static SpriteSheet RemoteBombBeep = new SpriteSheet(remoteBomb, 0, 4, 2, 1, 40);

    public static SpriteSheet ClusterBombAnimDown = new SpriteSheet(clusterBomb, 0, 0, 4, 1, 41);
    public static SpriteSheet ClusterBombAnimLeft = new SpriteSheet(clusterBomb, 0, 1, 4, 1, 41);
    public static SpriteSheet ClusterBombAnimUp = new SpriteSheet(clusterBomb, 0, 2, 4, 1, 41);
    public static SpriteSheet ClusterBombAnimRight = new SpriteSheet(clusterBomb, 0, 3, 4, 1, 41);
    public static SpriteSheet remoteClusterBombAnimDown = new SpriteSheet(remoteClusterBomb, 0, 0, 4, 1, 41);
    public static SpriteSheet remoteClusterBombAnimLeft = new SpriteSheet(remoteClusterBomb, 0, 1, 4, 1, 41);
    public static SpriteSheet remoteClusterBombAnimUp = new SpriteSheet(remoteClusterBomb, 0, 2, 4, 1, 41);
    public static SpriteSheet remoteClusterBombAnimRight = new SpriteSheet(remoteClusterBomb, 0, 3, 4, 1, 41);
    public static SpriteSheet remoteClusterBombDownBeep = new SpriteSheet(remoteClusterBomb, 8, 0, 2, 1, 41);
    public static SpriteSheet remoteClusterBombLeftBeep = new SpriteSheet(remoteClusterBomb, 8, 1, 2, 1, 41);
    public static SpriteSheet remoteClusterBombUpBeep = new SpriteSheet(remoteClusterBomb, 8, 2, 2, 1, 41);
    public static SpriteSheet remoteClusterBombRightBeep = new SpriteSheet(remoteClusterBomb, 8, 3, 2, 1, 41);

    public static SpriteSheet DangerousBomb = new SpriteSheet(dangerousBomb, 0, 0, 4, 1, 40);
    public static SpriteSheet DangerousBombPunched = new SpriteSheet(punchedDangerousBomb, 0, 0, 15, 1, 40);
    public static SpriteSheet remoteDangerousBombAnim = new SpriteSheet(remoteDangerousBomb, 0, 0, 4, 1, 40);
    public static SpriteSheet remoteDangerousBombBeep = new SpriteSheet(remoteDangerousBomb, 0, 1, 2, 1, 40);

    public static SpriteSheet Fire_CENTER = new SpriteSheet(bombFire, 0, 0, 9, 1, 96);
    public static SpriteSheet Fire_LEFT = new SpriteSheet(bombFire, 0, 1, 9, 1, 96);
    public static SpriteSheet Fire_LEFTend = new SpriteSheet(bombFire, 0, 2, 9, 1, 96);
    public static SpriteSheet Fire_UP = new SpriteSheet(bombFire, 0, 3, 9, 1, 96);
    public static SpriteSheet Fire_UPend = new SpriteSheet(bombFire, 0, 4, 9, 1, 96);
    public static SpriteSheet Fire_RIGHT = new SpriteSheet(bombFire, 0, 5, 9, 1, 96);
    public static SpriteSheet Fire_RIGHTend = new SpriteSheet(bombFire, 0, 6, 9, 1, 96);
    public static SpriteSheet Fire_DOWN = new SpriteSheet(bombFire, 0, 7, 9, 1, 96);
    public static SpriteSheet Fire_DOWNend = new SpriteSheet(bombFire, 0, 8, 9, 1, 96);

    public static SpriteSheet pierceFire_CENTER = new SpriteSheet(pierceFire, 0, 0, 9, 1, 96);
    public static SpriteSheet pierceFire_LEFT = new SpriteSheet(pierceFire, 0, 1, 9, 1, 96);
    public static SpriteSheet pierceFire_LEFTend = new SpriteSheet(pierceFire, 0, 2, 9, 1, 96);
    public static SpriteSheet pierceFire_UP = new SpriteSheet(pierceFire, 0, 3, 9, 1, 96);
    public static SpriteSheet pierceFire_UPend = new SpriteSheet(pierceFire, 0, 4, 9, 1, 96);
    public static SpriteSheet pierceFire_RIGHT = new SpriteSheet(pierceFire, 0, 5, 9, 1, 96);
    public static SpriteSheet pierceFire_RIGHTend = new SpriteSheet(pierceFire, 0, 6, 9, 1, 96);
    public static SpriteSheet pierceFire_DOWN = new SpriteSheet(pierceFire, 0, 7, 9, 1, 96);
    public static SpriteSheet pierceFire_DOWNend = new SpriteSheet(pierceFire, 0, 8, 9, 1, 96);
    //

    //Items
    public static SpriteSheet itemSpawnAnim = new SpriteSheet(itemSpawn, 0, 0, 3, 1, 36);
    
    public static SpriteSheet bombUpAnim = new SpriteSheet(bombUp, 0,0, 12, 1, 32);
    public static SpriteSheet fireUpAnim = new SpriteSheet(fireUp, 0,0, 12, 1, 32);
    public static SpriteSheet fullFireAnim = new SpriteSheet(fullFire, 0,0, 12, 1, 32);
    public static SpriteSheet speedUpAnim = new SpriteSheet(speedUp, 0,0, 12, 1, 32);
    public static SpriteSheet heartAnim = new SpriteSheet(heart, 0,0, 12, 1, 32);
    public static SpriteSheet triBombUpAnim = new SpriteSheet(triBombUp, 0,0, 12, 1, 32);
    public static SpriteSheet triFireUpAnim = new SpriteSheet(triFireUp, 0,0, 12, 1, 32);
    public static SpriteSheet triSpeedUpAnim = new SpriteSheet(triSpeedUp, 0,0, 12, 1, 32);

    public static SpriteSheet bombKickAnim = new SpriteSheet(bombKickItem, 0,0, 12, 1, 32);
    public static SpriteSheet bombThrowAnim = new SpriteSheet(bombThrowItem, 0,0, 12, 1, 32);
    public static SpriteSheet superPowerGloveAnim = new SpriteSheet(superPowerGloveItem, 0,0, 12, 1, 32);
    public static SpriteSheet bombPunchAnim = new SpriteSheet(bombPunchItem, 0,0, 12, 1, 32);
    public static SpriteSheet bomberShootAnim = new SpriteSheet(bomberShootItem, 0, 0, 12, 1, 32);
    public static SpriteSheet detonatorAnim = new SpriteSheet(detonatorItem, 0, 0, 12, 1, 32);
    public static SpriteSheet softBlockPassAnim = new SpriteSheet(softBlockPass, 0,0, 12, 1, 32);
    public static SpriteSheet bombPassAnim = new SpriteSheet(bombPass, 0,0, 12, 1, 32);

    public static SpriteSheet mergerAnim = new SpriteSheet(mergerItem, 0,0, 12, 1, 32);
    
    public static SpriteSheet powerBombItemAnim = new SpriteSheet(powerBombItem, 0,0, 12, 1, 32);
    public static SpriteSheet remoteControlAnim = new SpriteSheet(remoteControlItem, 0,0, 12, 1, 32);
    public static SpriteSheet clusterBombItemAnim = new SpriteSheet(clusterBombItem, 0,0, 12, 1, 32);
    public static SpriteSheet pierceBombItemAnim = new SpriteSheet(pierceBombItem, 0,0, 12, 1, 32);
    public static SpriteSheet dangerBombItemAnim = new SpriteSheet(dangerousBombItem, 0,0, 12, 1, 32);

    public static SpriteSheet softBlockDestroyAnim = new SpriteSheet(softBlockDestroy, 0,0, 6, 1, 40);
    public static SpriteSheet sandStoneBlockDestroyAnim = new SpriteSheet(sandStoneBlockDestroy, 0,0, 6, 1, 40);
    public static SpriteSheet itemCollectAnim = new SpriteSheet(itemCollect, 0,0, 3, 1, 36);
    public static SpriteSheet itemBurnAnim = new SpriteSheet(itemBurn, 0,0, 6, 1, 40);
    public static SpriteSheet itemCrushAnim = new SpriteSheet(itemCrush, 0,0, 19, 1, 64);
    
    public static SpriteSheet skullItem0 = new SpriteSheet(skullItem, 0, 0, 12, 1, 32);
    public static SpriteSheet skullItem1 = new SpriteSheet(skullItem, 0, 1, 16, 1, 32);
    //

    //Sudden Death Blocks
    public static SpriteSheet SDBspawningAnim = new SpriteSheet(pressureBlock, 1, 0, 3, 1, 58);
    public static SpriteSheet SDBlandingAnim = new SpriteSheet(pressureBlock, 4, 0, 4, 1, 58);
    //
    
    //End of Subsheets(Regular)
    
    //Subsheets(Mode7)
    
    //Player
    public static SpriteSheet Bomber_JETidle = new SpriteSheet(Bomber_JET, 0, 0, 1, 1, 164);
    public static SpriteSheet Bomber_JETthrow = new SpriteSheet(Bomber_JET, 1, 0, 4, 1, 164);
    public static SpriteSheet Bomber_JETboost = new SpriteSheet(Bomber_JET, 5, 0, 2, 1, 164);
    public static SpriteSheet Bomber_JETunBoost = new SpriteSheet(Bomber_JET, 6, 0, 2, 1, 164);
    public static SpriteSheet Bomber_JET_FLAMEanim = new SpriteSheet(Bomber_JETFLAME, 0, 0, 3, 1, 164);
    //
    
    //Bombs
    public static SpriteSheet jetBombStart = new SpriteSheet(jetBomb, 0, 0, 1, 1, 54);
    public static SpriteSheet jetBombAnim = new SpriteSheet(jetBomb, 1, 0, 4, 1, 54);
    //
    
    //End of SubSheets(Mode7)
    

    //Sprite array (for animation)
    public Sprite[] sprites;

    public SpriteSheet (SpriteSheet sheet, int x, int y, int width, int height, int spriteSize)
    {//Subsheet creator
        int xx = x * spriteSize;//X and Y positions depend on sprites in the Sheet
        int yy = y * spriteSize;
        int w = width * spriteSize;
        int h = height * spriteSize;
        if (width == height) SIZE = width;
        else SIZE  = -1;
        SPRITE_WIDTH = w;
        SPRITE_HEIGHT = h;
        pixels = new int [w*h];
        for(int y0 = 0;y0<h;y0++)
        {
            int yp = yy + y0;
            for(int x0 = 0; x0 < w; x0++)
            {
                int xp = xx + x0;
                pixels[x0+y0*w] = sheet.pixels[xp+yp*sheet.SPRITE_WIDTH];
            }
        }

        int frame = 0;
        sprites = new Sprite[width * height];
        for(int ya = 0; ya < height; ya++){
            for(int xa = 0; xa < width; xa++){
                int[] spritePixels = new int[spriteSize * spriteSize];
                for(int y0 = 0; y0 < spriteSize; y0++){
                    for(int x0 = 0; x0 < spriteSize; x0++){
                        spritePixels[x0+y0*spriteSize] = pixels[(x0+xa*spriteSize)+(y0+ya*spriteSize)*SPRITE_WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }
    }
    
    public SpriteSheet (SpriteSheet sheet, int x, int y, int width, int height, int spriteWidth, int spriteHeight)
    {//Subsheet creator
        int xx = x * spriteWidth;//X and Y positions depend on sprites in the Sheet
        int yy = y * spriteHeight;
        int w = width * spriteWidth;
        int h = height * spriteHeight;
        if (width == height) SIZE = width;
        else SIZE  = -1;
        SPRITE_WIDTH = w;
        SPRITE_HEIGHT = h;
        pixels = new int [w*h];
        for(int y0 = 0;y0<h;y0++)
        {
            int yp = yy + y0;
            for(int x0 = 0; x0 < w; x0++)
            {
                int xp = xx + x0;
                pixels[x0+y0*w] = sheet.pixels[xp+yp*sheet.SPRITE_WIDTH];
            }
        }

        int frame = 0;
        sprites = new Sprite[width * height];
        for(int ya = 0; ya < height; ya++){
            for(int xa = 0; xa < width; xa++){
                int[] spritePixels = new int[spriteWidth * spriteHeight];
                for(int y0 = 0; y0 < spriteHeight; y0++){
                    for(int x0 = 0; x0 < spriteWidth; x0++){
                        spritePixels[x0+y0*spriteWidth] = pixels[(x0+xa*spriteWidth)+(y0+ya*spriteHeight)*SPRITE_WIDTH];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteWidth, spriteHeight);
                sprites[frame++] = sprite;
            }
        }
    }

    public SpriteSheet(String path, int size)
    {
        this.path = path;//Sprites are stored in folders(found via paths)
        SIZE = size;
        SPRITE_WIDTH = size;
        SPRITE_HEIGHT = size;
        pixels = new int[SIZE * SIZE];//Amount of Pixels equals Area of Sprite
        load();
    }

    public SpriteSheet(String path,int width, int height)
    {
        this.path = path;
        SIZE = -1;
        SPRITE_WIDTH = width;
        SPRITE_HEIGHT = height;
        pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
        load();
    }

    public Sprite[] getSprites()
    {
        return sprites;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int[] getPixels()
    {
        return pixels;
    }

    private void load()
    {
        try
        {
            //Meant to get a file path to the image
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
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
