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
public class WideMapState extends BattleMapState
{
    private float scale = 0.70f;
    public WideMapState(GameStateManager gsm, Keyboard key, int timeMinute, int timeSecond, byte[] playerWins)
    {
        super(gsm, key, timeMinute, timeSecond, playerWins);
        state = gsm.WIDEMAPSTATE;

        maxSDX = (38*32)-13;
        maxSDY = (17*32)-13;
        minSDX = 0-18;
        minSDY = 0-18;

        SDCooldown = 4;

        minX = (0 * 32);//Left edge of bounds
        maxX = (38 * 32);//Right last pixel of bounds
        minY = (0 * 32);//Upper edge of bounds
        maxY = (18 * 32);//Lower last pixel of bounds

        SDX = minSDX;
        SDY = minSDY;

        music = AudioManager.music_bombFactory;
        SDmusic = AudioManager.music_bombFactorySD;
        init();
    }

    public void init()
    {
        TileCoordinate playerSpawn = new TileCoordinate(1,1,-5,-12);//Defualt 8-Player Spawns
        TileCoordinate playerSpawn2 = new TileCoordinate(37,17,-5,-12);
        TileCoordinate playerSpawn3 = new TileCoordinate(19,9,-5,-12);

        level = new Level("/Levels/BattleMaps/12PlayerMap/Floors/12PlayerMap_Floor0.png",
                          "/Levels/BattleMaps/12PlayerMap/Walls/12PlayerMap_Walls0.png");

        //generateBlocks();

        players[0] = new Player(playerSpawn.x(), playerSpawn.y(), 0, key, (byte)0, uiManager);
        players[1] = new Player(playerSpawn2.x(), playerSpawn2.y(),0, key, (byte)1, uiManager);       

        switch(gsm.playerAmount)
        {
            case 8:
            players[7] = new Player(playerSpawn2.x(), playerSpawn3.y(),0, key, (byte)7, uiManager);

            case 7:
            players[6] = new Player(playerSpawn.x(), playerSpawn3.y(),0, key, (byte)6, uiManager);

            case 6:
            players[5] = new Player(playerSpawn3.x(), playerSpawn2.y(),0, key, (byte)5, uiManager);

            case 5:
            players[4] = new Player(playerSpawn3.x(), playerSpawn.y(),0, key, (byte)4, uiManager);

            case 4:
            players[3] = new Player(playerSpawn.x(), playerSpawn2.y(),0, key, (byte)3, uiManager);

            case 3:
            players[2] = new Player(playerSpawn2.x(), playerSpawn.y(),0, key, (byte)2, uiManager);
            break;
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
        super.init();
    }

    public void generateBlocks()
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

        for(int y = 1; y < 18; y++)
        {
            for(int x = 1; x < 38; x++)
            {
                int chance = random.nextInt(20);
                if(chance < 17)
                {
                    if((x > 2 && x < 36) || (y > 2 && y < 16))
                    {
                        if(y%2 != 0 || x%2 != 0){level.add(new SoftBlock((x*32),(y*32)-1,0));}
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

    public void render(Screen screen)
    {
        //level.render(16,0,0,screen);
        if(key.swapL[0]){scale-=0.1;}
        if(key.swapR[0]){scale+=0.1;}
        level.renderScale(0,-76,0,scale,screen);
        super.render(screen);
    }
}
