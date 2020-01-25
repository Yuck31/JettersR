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
public class PowerZoneState extends BattleMapState
{
    private BackGround bg;
    private int keyTime = 0;

    public PowerZoneState(GameStateManager gsm, Keyboard key, int timeMinute, int timeSecond, byte[] playerWins)
    {
        super(gsm, key, timeMinute, timeSecond, playerWins);
        state = gsm.POWERZONESTATE;

        if(gsm.playerAmount > 4)
        {
            maxSDX = 755;
            maxSDY = 429;
            minSDX = 147;
            minSDY = 45;

            SDCooldown = 14;

            minX = (5 * 32);//Left edge of bounds
            maxX = (23 * 32);//Right last pixel of bounds
            minY = (2 * 32);//Upper edge of bounds
            maxY = (14 * 32);//Lower last pixel of bounds
        }
        else
        {
            maxSDX = 723;
            maxSDY = 429;
            minSDX = 179;
            minSDY = 45;

            SDCooldown = 16;

            minX = (6 * 32);//Left edge of bounds
            maxX = (22 * 32);//Right last pixel of bounds
            minY = (2 * 32);//Upper edge of bounds
            maxY = (14 * 32);//Lower last pixel of bounds
        }
        SDX = minSDX;
        SDY = minSDY;

        bg = new BackGround(Sprite.powerZoneBackground, 1);
        bg.setPosition(16,0);

        music = AudioManager.music_powerZone;
        SDmusic = AudioManager.music_powerZoneSD;
        init();
    }

    public void init()
    {
        playerSpawn = new TileCoordinate(6,3,-5,-12);//Defualt 8-Player Spawns
        playerSpawn2 = new TileCoordinate(22,13,-5,-12);
        playerSpawn3 = new TileCoordinate(10,5,-5,-12);
        playerSpawn4 = new TileCoordinate(18,11,-5,-12);
        if(gsm.playerAmount > 4)
        {
            level = new Level("/Levels/BattleMaps/PowerZone8P/Floors/PowerZone8P_Floor0.png",
                              "/Levels/BattleMaps/PowerZone8P/Walls/PowerZone8P_Walls0.png");
        }
        else
        {
            level = new Level("/Levels/BattleMaps/PowerZone4P/Floors/PowerZone4P_Floor0.png",
                              "/Levels/BattleMaps/PowerZone4P/Walls/PowerZone4P_Walls0.png");
            playerSpawn = new TileCoordinate(7,3,-5,-12);
            playerSpawn2 = new TileCoordinate(21,13,-5,-12);
        }
        level.setLoopBounds(minX, maxX, minY, maxY);

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

        for(byte i = 0; i < gsm.playerAmount; i++)
        {
            if(players[i] != null){players[i].init(level);}     
        }
        for(byte i = 0; i < gsm.playerAmount; i++)
        {
            if(players[i] != null){level.add(players[i]);}
        }

        for(byte i = 0; i < gsm.playerAmount; i++)
        {
            if(players[i] != null)
            {
                players[i].setWins(playerWins[i]);
                players[i].bombs = players[i].maxBombs;
                players[i].fires = players[i].maxFires;
                players[i].speed = players[i].maxSpeed;
                players[i].bombKick = true;
                players[i].bombThrow = 2;
                players[i].bombPunch = true;
                players[i].collectedSpecialType[players[i].type_bomberShoot] = true;
                players[i].equipedSpecial = players[i].type_bomberShoot;
            }
        }
        setWins();
        super.init();
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
        if(!gsm.paused)
        {
            super.update();
            if(SDFrames <= 0){generateSuddenDeath(); SDFrames = SDCooldown;}
        }

        if(keyTime > 0){keyTime--;}
        if(gsm.paused && key.swapR[0] && keyTime == 0)
        {
            gsm.paused = false;
            super.update();
            gsm.paused = true;
            keyTime = 2;
        }
        else if(gsm.paused && key.swapR[0] && keyTime > 0)
        {
            keyTime = 2;
        }
    }

    public void render(Screen screen)
    {
        //double xScroll = player.x - screen.width / 2;
        //double yScroll = player.y - screen.height / 2;

        bg.render(screen, true);

        level.render(16,0,0,screen);
        super.render(screen);
    }
}