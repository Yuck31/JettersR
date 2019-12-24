package JettersR.GameStates.BattleMaps;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.UI.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;

import java.util.Random;
import java.util.HashMap;
public class RockGardenState extends BattleMapState
{
    public RockGardenState(GameStateManager gsm, Keyboard key, int timeMinute, int timeSecond, byte[] playerWins)
    {
        super(gsm, key, timeMinute, timeSecond, playerWins);
        state = gsm.ROCKGARDENSTATE;

        maxSDX = 723;
        maxSDY = 461;
        minSDX = 179;
        minSDY = 13;

        SDX = minSDX;
        SDY = minSDY;
        SDFrames = 14;
        SDCooldown = 14;

        minX = 0;//Left edge of bounds
        maxX = 895;//Right last pixel of bounds
        minY = 0;//Upper edge of bounds
        maxY = 503;//Lower last pixel of bounds

        music = AudioManager.music_battle64;
        SDmusic = AudioManager.music_battle64SD;

        init();
    }

    public void init()
    {
        level = new Level("/Levels/RockGarden/RockGardenFloor.png", "/Levels/RockGarden/RockGardenWalls.png");

        //generateBlocks();

        playerSpawn = new TileCoordinate(7,3,-5,-12);
        players[0] = new Player(playerSpawn.x(), playerSpawn.y(), 0, key, (byte)0, uiManager);
        playerSpawn2 = new TileCoordinate(21,13,-5,-12);
        players[1] = new Player(playerSpawn2.x(), playerSpawn2.y(),0, key, (byte)1, uiManager);       
        players[2] = new Player(playerSpawn2.x(), playerSpawn.y(),0, key, (byte)2, uiManager);
        players[3] = new Player(playerSpawn.x(), playerSpawn2.y(),0, key, (byte)3, uiManager);

        for(byte i = 0; i < players.length; i++)
        {
            if(players[i] != null){players[i].init(level);}
        }
        for(byte i = 0; i < players.length; i++)
        {
            if(players[i] != null){level.add(players[i]);}
        }

        for(byte i = 0; i < playerWins.length; i++)
        {
            if(players[i] != null)
            {
                players[i].bombs = 2;
                players[i].bombKick = true;
                players[i].bombThrow = 1;
            }
        }

        setWins();
        super.init();
    }

    public void generateBlocks()
    {
        for(int y = 3; y < 14; y++)
        {
            for(int x = 6; x <= 20; x++)
            {
                int chance = random.nextInt(20);
                if(chance < 17)
                {
                    if(((x > 7 && x < 19) || (y > 4 && y < 12)))
                    {
                        Entity e = new SoftBlock((x*32)+32,(y*32)-1,0);
                        if(y%2 == 1 || x%2 != 1){level.add(e);}
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
            if(SDFrames <= 0){generateSuddenDeath(); SDFrames = SDCooldown;}
        }
    }

    public void endGame()
    {
        super.endBattle(gsm.ROCKGARDENSTATE);
    }

    public void render(Screen screen)
    {
        level.render(16,0,0,screen);
        super.render(screen);
    }
}
