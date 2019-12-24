package JettersR.GameStates.BattleMaps;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.UI.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;

import java.util.Random;
public class GreatWallState extends BattleMapState
{
    private BackGround bg;

    public String[] floors = new String[3];
    public String[] walls = new String[3];
    public String[] itemSpaces = new String[3];

    public GreatWallState(GameStateManager gsm, Keyboard key, int timeMinute, int timeSecond, byte[] playerWins)
    {
        super(gsm, key, timeMinute, timeSecond, playerWins);
        state = gsm.GREATWALLSTATE;

        maxSDX = 755;
        maxSDY = 461;
        minSDX = 147;
        minSDY = 77;

        SDX = minSDX;
        SDY = minSDY;
        SDFrames = 14;
        SDCooldown = 14;

        minX = (5 * 32);//Left edge of bounds
        maxX = (23 * 32);//Right last pixel of bounds
        minY = (3 * 32);//Upper edge of bounds
        maxY = (15 * 32);//Lower last pixel of bounds

        bg = new BackGround(Sprite.backGround, 0);

        music = AudioManager.music_greatWall;
        SDmusic = AudioManager.music_greatWallSD;
        init();
    }

    public void init()
    {
        floors[0] = "/Levels/GreatWall/GreatWallFloor1.png";
        floors[1] = "/Levels/GreatWall/GreatWallFloor2.png";
        floors[2] = "/Levels/GreatWall/GreatWallFloor3.png";
        walls[0] = "/Levels/GreatWall/GreatWallWalls1.png";
        walls[1] = "/Levels/GreatWall/GreatWallWalls2.png";
        walls[2] = "/Levels/GreatWall/GreatWallWalls3.png";
        level = new Level(floors, walls, 3);

        itemSpaces[0] = "/Levels/GreatWall/GreatWallItemSpaces1.png";
        itemSpaces[2] = "/Levels/GreatWall/GreatWallItemSpaces3.png";
        level.setItemSpaces(itemSpaces);

        generateBlocks();

        playerSpawn = new TileCoordinate(6,4,-5,-12);
        playerSpawn2 = new TileCoordinate(22,14,-5,-12);
        playerSpawn3 = new TileCoordinate(10,6,-5,-12);
        playerSpawn4 = new TileCoordinate(18,12,-5,-12);

        if(gsm.randomPlayerSpawns)
        {
            randomizeSpawns();
        }
        else
        {
            players[0] = new Player(playerSpawn.x(), playerSpawn.y(), 0, key, (byte)0, uiManager);
            players[1] = new Player(playerSpawn2.x(), playerSpawn2.y(),0, key, (byte)1, uiManager);       

            switch(gsm.playerAmount)
            {
                case 8:
                players[7] = new Player(playerSpawn3.x(), playerSpawn4.y(),0, key, (byte)7, uiManager);

                case 7:
                players[6] = new Player(playerSpawn4.x(), playerSpawn3.y(),0, key, (byte)6, uiManager);

                case 6:
                players[5] = new Player(playerSpawn4.x(), playerSpawn4.y(),0, key, (byte)5, uiManager);

                case 5:
                players[4] = new Player(playerSpawn3.x(), playerSpawn3.y(),0, key, (byte)4, uiManager);

                case 4:
                players[3] = new Player(playerSpawn.x(), playerSpawn2.y(),0, key, (byte)3, uiManager);

                case 3:
                players[2] = new Player(playerSpawn2.x(), playerSpawn.y(),0, key, (byte)2, uiManager);
                break;
            }
        }

        for(byte i = 0; i < players.length; i++)
        {
            if(players[i] != null){players[i].init(level);}   
        }
        for(byte i = 0; i < players.length; i++)
        {
            if(players[i] != null){level.add(players[i]);}
        }

        setWins();
        super.init();
    }

    public void generateBlocks()
    {
        for(int y = 4; y < 15; y++)//Left Side
        {
            for(int x = 6; x <= 10; x++)
            {
                int chance = random.nextInt(20);
                if(chance < 16)
                {
                    //SoftBlocks will never spawn on:
                    //10, 6; 18, 6; 10, 12; and 18, 12.
                    if(((x > 7) || (y > 5 && y < 13)))
                    {
                        if((y%2 == 0 || x%2 == 0) && ((x != 8 || y != 9) && (x != 9 || y != 9) && (x != 10 || y != 9)))
                        {
                            if(((x != 9 || y != 8) && (x != 10 || (y < 6 || y > 8))) && ((x != 9 || y != 10) && (x != 10 || (y < 10 || y > 12))))
                            {
                                level.add(new SandStoneBlock((x*32),(y*32)-1,0));
                            }
                        }
                    }
                }
            }
        }
        for(int y = 4; y < 15; y++)//Middle(the Wall)
        {
            for(int x = 11; x <= 17; x++)
            {
                int chance = random.nextInt(20);
                if(chance < 16)
                {
                    if(((x > 11 && x < 17) || (y != 9)))
                    {
                        if(y%2 == 0 || x%2 != 0){level.add(new SandStoneBlock((x*32),(y*32)-1,2));}
                    }
                }
            }
        }
        for(int y = 4; y < 15; y++)//Right side
        {
            for(int x = 18; x <= 22; x++)
            {
                int chance = random.nextInt(20);
                if(chance < 16)
                {
                    if(((x < 21) || (y > 5 && y < 13)))
                    {
                        if((y%2 == 0 || x%2 == 0) && ((x != 18 || y != 9) && (x != 19 || y != 9) && (x != 20 || y != 9)))
                        {
                            if(((x != 19 || y != 8) && (x != 18 || (y < 6 || y > 8))) && ((x != 19 || y != 10) && (x != 18 || (y < 10 || y > 12))))
                            {
                                level.add(new SandStoneBlock((x*32),(y*32)-1,0));
                            }
                        }
                    }
                }
            }
        }
    }

    public void generateSuddenDeath()
    {
        if(!gsm.pressureBlocks){return;}

        int za = 1;
        if(SDX >= 339 && SDX <= 531){za = 3;}
        //else if(SDX >= 307 && SDX <= 563){za = 2;}
        if(SDX < maxSDX && (!maxXFlag && !maxYFlag) && SDY <= minSDY)
        {
            level.add(new PressureBlock(SDX, SDY,za));
            if(SDX <= maxSDX){SDX = SDX + 32;}
        }
        else if(SDX >= maxSDX && !maxXFlag)
        {
            SDY = SDY + 32;
            maxXFlag = true;
        }

        if(SDY < maxSDY && (maxXFlag && !maxYFlag) && SDX >= maxSDX)
        {
            level.add(new PressureBlock(SDX-32, SDY,za));
            if(SDY <= maxSDY){SDY = SDY + 32;}
        }
        else if(SDY >= maxSDY && !maxYFlag)
        {
            SDX = SDX - 32;
            maxYFlag = true;
        }

        if(SDX >= minSDX && (maxXFlag && maxYFlag) && SDY >= maxSDY)
        {
            level.add(new PressureBlock(SDX, SDY,za));
            if(SDX <= maxSDX){SDX = SDX - 32;}
        }
        else if(SDX <= minSDX && maxXFlag)
        {
            SDY = SDY - 32;
            minSDY = minSDY + 32;
            maxSDX = maxSDX - 32;
            maxXFlag = false;
        }

        if(SDY > minSDY && (!maxXFlag && maxYFlag) && SDX <= minSDX)
        {
            level.add(new PressureBlock(SDX+32, SDY,za));
            if(SDY <= maxSDY){SDY = SDY - 32;}
        }
        else if(SDY <= minSDY && maxYFlag)
        {
            SDX = SDX + 32;
            minSDX = minSDX + 32;
            maxSDY = maxSDY - 32;
            maxYFlag = false;
        }
    }

    public void update()
    {
        super.update();
        if(!gsm.paused)
        {
            if(SDFrames <= 0){generateSuddenDeath(); SDFrames = SDCooldown;}
        }
    }

    public void render(Screen screen)
    {
        //bg.render(screen);
        level.render(16,12,0,screen);
        //if(endTime <= 0)
        //{
        //level.render(0,0,screen);
        //}

        //Decoration Sprites
        screen.renderSprite(864, 160, Sprite.hardenedBody1, true);
        screen.renderSprite(812, 250, Sprite.hardenedBody2, true);
        screen.renderSprite(832, 320, Sprite.hardenedBody3, true);
        screen.renderSprite(851, 410, Sprite.hardenedBody4, true);

        //Use the Parent class to render UI over everything.
        super.render(screen);
    }
}