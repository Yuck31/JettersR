package JettersR.GameStates.BattleMaps;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.UI.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;

import java.util.Random;

public class BombFactoryState extends BattleMapState
{
    private BackGround bg;
    public BombFactoryState(GameStateManager gsm, Keyboard key, int timeMinute, int timeSecond, byte[] playerWins)
    {
        super(gsm, key, timeMinute, timeSecond, playerWins);
        random = new Random();
        state = gsm.BOMBFACTORYSTATE;
        if(gsm.playerAmount > 4)
        {
            maxSDX = 755;
            maxSDY = 429;
            minSDX = 147;
            minSDY = 45;

            SDCooldown = 14;

            minX = (5 * 32);//Left edge of bounds
            maxX = (23 * 32);//Right first pixel of last wall
            minY = (2 * 32);//Upper edge of bounds
            maxY = (14 * 32);//Lower first pixel of last wall
        }
        else
        {
            maxSDX = 723;
            maxSDY = 429;
            minSDX = 179;
            minSDY = 45;

            SDCooldown = 16;

            minX = (6 * 32);//Left edge of bounds
            maxX = (22 * 32);//Right first pixel of last wall
            minY = (2 * 32);//Upper edge of bounds
            maxY = (14 * 32);//Lower first pixel of last wall
        }        
        SDX = minSDX;
        SDY = minSDY;

        bg = new BackGround(Sprite.discoLights, 1);
        bg.setVector(-2, 0);

        music = AudioManager.music_bombFactory;
        SDmusic = AudioManager.music_bombFactorySD;
        init();
    }

    public void init()
    {
        playerSpawn = new TileCoordinate(6,3,-5,-12);//Defualt 8-Player Spawns
        playerSpawn2 = new TileCoordinate(22,13,-5,-12);
        playerSpawn3 = new TileCoordinate(10,5,-5,-12);
        playerSpawn4 = new TileCoordinate(18,11,-5,-12);

        //Creates correct size level
        if(gsm.playerAmount > 4)
        {
            level = new Level("/Levels/BombFactory/BombFactoryFloor_8P.png", "/Levels/BombFactory/BombFactoryWalls_8P.png");
            level.setItemSpaces("/Levels/BombFactory/BombFactoryItemSpaces_8P.png");
        }
        else
        {
            level = new Level("/Levels/BombFactory/BombFactoryFloor_4P.png", "/Levels/BombFactory/BombFactoryWalls_4P.png");
            level.setItemSpaces("/Levels/BombFactory/BombFactoryItemSpaces_4P.png");
            playerSpawn = new TileCoordinate(7,3,-5,-12);//Changes initial spawns to the 4-Player Spawns
            playerSpawn2 = new TileCoordinate(21,13,-5,-12);
        }

        generateBlocks(gsm.playerAmount);

        if(gsm.randomPlayerSpawns)
        {
            randomizeSpawns();
        }
        else
        {
            players[0] = new Player(playerSpawn.x(), playerSpawn.y(), 0, key, (byte)0, uiManager);
            players[1] = new Player(playerSpawn2.x(), playerSpawn2.y(), 0, key, (byte)1, uiManager);       

            switch(gsm.playerAmount)
            {
                case 8:
                players[7] = new Player(playerSpawn3.x(), playerSpawn4.y(), 0, key, (byte)7, uiManager);

                case 7:
                players[6] = new Player(playerSpawn4.x(), playerSpawn3.y(), 0, key, (byte)6, uiManager);

                case 6:
                players[5] = new Player(playerSpawn4.x(), playerSpawn4.y(), 0, key, (byte)5, uiManager);

                case 5:
                players[4] = new Player(playerSpawn3.x(), playerSpawn3.y(), 0, key, (byte)4, uiManager);

                case 4:
                players[3] = new Player(playerSpawn.x(), playerSpawn2.y(), 0, key, (byte)3, uiManager);

                case 3:
                players[2] = new Player(playerSpawn2.x(), playerSpawn.y(), 0, key, (byte)2, uiManager);
                break;
            }
        }

        for(byte i = 0; i < gsm.playerAmount; i++)
        {
            if(players[i] != null){players[i].init(level);}   
        }
        for(byte i = 0; i < gsm.playerAmount; i++)
        {
            if(players[i] != null){level.add(players[i]);}
        }
        
        setWins();
        if(matchPoint() && !SDFlag){music = AudioManager.music_euroBomberBattle;}
        super.init();
    }

    public void generateBlocks(int playerAmount)
    {
        if(playerAmount > 4)//8 Player Block Generation
        {
            for(int y = 3; y < 14; y++)
            {
                for(int x = 6; x <= 22; x++)
                {
                    int chance = random.nextInt(20);
                    if(chance < 17)
                    {
                        if(((x > 7 && x < 21) || (y > 4 && y < 12)))
                        {
                            if(y%2 != 0 || x%2 == 0)
                            {
                                if(((x != 10 || (y != 5 && y != 6)) && (x != 11 || y != 5))//If this isn't Player 5's spawn,...
                                && ((x != 18 || (y != 11 && y != 10)) && (x != 17 || y != 11))//...Player 6's spawn,...
                                && ((x != 18 || (y != 5 && y != 6)) && (x != 17 || y != 5))//...Player 7's spawn,...
                                && ((x != 10 || (y != 11 && y != 10)) && (x != 11 || y != 11)))//...or Player 8's spawn.
                                {
                                    level.add(new SoftBlock((x*32),(y*32)-1,0));
                                }
                            }
                        }
                    }
                }
            }
        }
        else//4 Player Block Generation
        {
            for(int y = 3; y < 14; y++)
            {
                for(int x = 7; x <= 21; x++)
                {
                    int chance = random.nextInt(20);
                    if(chance < 17)
                    {
                        if(((x > 8 && x < 20) || (y > 4 && y < 12)))
                        {
                            if(y%2 != 0 || x%2 != 0){level.add(new SoftBlock((x*32),(y*32)-1,0));}
                        }
                    }
                }
            }
        }
    }

    public void generateSuddenDeath()
    {
        if(!gsm.pressureBlocks){return;}

        if(SDX < maxSDX && (!maxXFlag && !maxYFlag) && SDY <= minSDY)
        {
            level.add(new PressureBlock(SDX, SDY,1));
            if(SDX <= maxSDX){SDX = SDX + 32;}
        }
        else if(SDX >= maxSDX && !maxXFlag)
        {
            SDY = SDY + 32;
            maxXFlag = true;
        }

        if(SDY < maxSDY && (maxXFlag && !maxYFlag) && SDX >= maxSDX)
        {
            level.add(new PressureBlock(SDX-32, SDY,1));
            if(SDY <= maxSDY){SDY = SDY + 32;}
        }
        else if(SDY >= maxSDY && !maxYFlag)
        {
            SDX = SDX - 32;
            maxYFlag = true;
        }

        if(SDX >= minSDX && (maxXFlag && maxYFlag) && SDY >= maxSDY)
        {
            level.add(new PressureBlock(SDX, SDY,1));
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
            level.add(new PressureBlock(SDX+32, SDY,1));
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
            bg.update();
            if(SDFrames <= 0){generateSuddenDeath(); SDFrames = SDCooldown;}
        }
    }

    public void render(Screen screen)
    {
        level.render(16,0,0,screen);
        if(matchPoint)
        {
            screen.renderLighting(0, 896, 0, 504, -64, false);
            bg.renderLights(screen, 5f, 128, false);
        }
        super.render(screen);
    }
}
