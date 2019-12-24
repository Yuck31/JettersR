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
public class BattleMapState extends GameState
{
    public Level level;
    public boolean matchPoint = false;
    public OGGContents music;
    public OGGContents SDmusic;
    public int state;
    protected Random random = new Random();

    public byte mos = 12;
    public short matchPointFrames = 165;

    public int timeMinute = 2;//Minute + ":" + Second
    public int timeSecond = 30;
    public int timeFrames = 60;//Used for counting down seconds
    public int startTimeMinute = 2;//Minute + ":" + Second
    public int startTimeSecond = 30;
    public int SDFrames = 16;
    public int SDCooldown = 16;
    public byte hurryUpTime = 120;

    public int endTime = 180;
    public boolean endFlag = false;

    public boolean SDFlag = false;
    public boolean maxXFlag = false;
    public boolean maxYFlag = false;
    public int maxSDX = 723;
    public int maxSDY = 429;
    public int minSDX = 179;
    public int minSDY = 45;

    public int SDX = minSDX;
    public int SDY = minSDY;

    public boolean loopSet = false;
    public int minX = 6;
    public int maxX = 22;
    public int minY = 2;
    public int maxY = 14;
    public Keyboard key;
    public Player[] players;
    public byte[] playerWins;

    public TileCoordinate playerSpawn;//Defualt 8-Player Spawns
    public TileCoordinate playerSpawn2;
    public TileCoordinate playerSpawn3;
    public TileCoordinate playerSpawn4;

    public BattleMapState(GameStateManager gsm, Keyboard key, int timeMinute, int timeSecond, byte[] playerWins)
    {
        this.gsm = gsm;
        this.key = key;
        uiManager = Game.getUIManager();
        players = new Player[8];
        this.timeMinute = timeMinute;
        this.timeSecond = timeSecond;
        this.startTimeMinute = timeMinute;
        this.startTimeSecond = timeSecond;
        this.playerWins = playerWins;
    }

    public void init()
    {
        level.setLoopBounds(minX, maxX, minY, maxY);

        if(matchPoint()){matchPoint = true;}

        if(timeMinute <= 1 && timeSecond <= 0)
        {
            SDFlag = true;
            Game.am.setOGG(AudioManager.oggPlayer(SDmusic));
        }
        else{Game.am.setOGG(AudioManager.oggPlayer(music));}
        Game.am.playOGG();
    }

    public void randomizeSpawns()
    {
        byte[] nums = {0, 1, 2, 3, 4, 5, 6, 7};
        int tileX = 0, tileY = 0, tileZ = 0;
        for(int i = gsm.playerAmount; i > 0; i--)
        {
            int rand = random.nextInt(i);
            switch(nums[rand])
            {
                case 7:
                tileX = playerSpawn3.x(); tileY = playerSpawn4.y();
                break;

                case 6:
                tileX = playerSpawn4.x(); tileY = playerSpawn3.y();
                break;

                case 5:
                tileX = playerSpawn4.x(); tileY = playerSpawn4.y();
                break;

                case 4:
                tileX = playerSpawn3.x(); tileY = playerSpawn3.y();
                break;

                case 3:
                tileX = playerSpawn.x(); tileY = playerSpawn2.y();
                break;

                case 2:
                tileX = playerSpawn2.x(); tileY = playerSpawn.y();
                break;

                case 1:
                tileX = playerSpawn2.x(); tileY = playerSpawn2.y();
                break;

                case 0:
                tileX = playerSpawn.x(); tileY = playerSpawn.y();
                break;
            }
            players[i-1] = new Player(tileX, tileY, tileZ, key, (byte)(i-1), uiManager);
            for(int ii = 0; rand+ii < gsm.playerAmount-1; ii++)
            {
                nums[rand+ii] = nums[rand+ii+1];
            }
        }
    }

    public void setWins()
    {
        for(byte i = 0; i < playerWins.length; i++)
        {
            if(players[i] != null)
            {
                players[i].setWins(playerWins[i]);
            }
        }
    }

    public boolean matchPoint()
    {
        for(byte i = 0; i < playerWins.length; i++)
        {
            if(players[i] != null)
            {
                //players[i].setWins(playerWins[i]);
                if(players[i].wins >= gsm.setsToWin-1){return true;}
            }
        }
        return false;
    }

    public void generateBlocks(){}

    public void generateSuddenDeath(){}

    public void update()
    {
        if(mos > 1){mos--;}
        matchPointFrames--;
        if(SDFlag && hurryUpTime > 0){hurryUpTime--;}

        if(!gsm.paused)
        {
            level.update();//Updates level(which renders entities and Players too)
            uiManager.update();//Updates the UI

            if(!endFlag)
            {
                if(timeFrames >= 0){timeFrames--;}
                if(timeFrames < 0){timeSecond--; timeFrames = 60;}
                if(timeSecond < 0){timeSecond = 59; timeMinute--;}

                if((timeMinute == 1 && timeSecond == 0) && !SDFlag)
                {
                    SDFlag = true;
                    Game.am.setOGG(AudioManager.oggPlayer(SDmusic));
                }
            }

            if(SDFlag && !endFlag){SDFrames--;}
            if(SDFrames <= 0){generateSuddenDeath(); SDFrames = SDCooldown;}

            if(level.getPlayersSize() <= 1)
            {
                endFlag = true;
                endTime--;
                if(endTime >= 177)
                {
                    for(int i = 0; i < level.players.size(); i++)
                    {
                        level.players.get(i).ImMobilize(2);//Immobilize players and make them face down
                    }
                }
                else if(endTime >= 176)
                {
                    for(int i = 0; i < players.length; i++)
                    {
                        if(players[i] != null)
                        {
                            if(!players[i].dead){players[i].wins++;}
                        }
                    }
                }
            }
            else if(timeMinute <= 0 && timeSecond <= 0)
            {
                endFlag = true;
                endTime--;
                if(endTime >= 177)
                {
                    for(int i = 0; i < level.players.size(); i++)
                    {
                        level.players.get(i).ImMobilize(2);//Immobilize players and make them face down
                    }
                }
            }

            if(level.getPlayersSize() <= 1 || (timeMinute <= 0 && timeSecond <= 0))
            {
                if(endTime <= 0)
                {
                    endBattle(state);
                }
            }
        }
    }

    public void endBattle(int gameState)
    {
        Game.am.stopOGG();
        for(int i = 0; i < players.length; i++)
        {
            if(players[i] != null){playerWins[i] = players[i].wins;}
        }
        Game.resetUI();
        Game.screen.resetOffsets();
        gsm.setPreviousState(gameState);
        gsm.setTime(startTimeMinute, startTimeSecond);
        gsm.setPlayersArray(players);
        gsm.setWinsArray(playerWins);
        gsm.setRoundNum(roundNum++);
        System.gc();
        gsm.setState(GameStateManager.VICTORYSCREENSTATE);
    }

    public void quitGame()
    {
        Game.am.stopOGG();
        Game.resetUI();
        Game.screen.resetOffsets();
        gsm.paused = false;
        gsm.resetWinsArray();
        gsm.setRoundNum(1);
        System.gc();
        gsm.setState(GameStateManager.MENUSTATE);
    }

    public void render(Screen screen)
    {
        if(gsm.renderUI)
        {
            uiManager.render(screen);
            if(timeSecond < 10)
            {
                Font.render(416, 482, ("A " + Integer.toString(timeMinute) + ":" + "0" + Integer.toString(timeSecond)), Font.numbersUI, screen);
            }
            else
            {
                Font.render(416, 482, ("A " + Integer.toString(timeMinute) + ":" + Integer.toString(timeSecond)), Font.numbersUI, screen);
            }
        }
        if(matchPoint() && matchPointFrames > 0){Font.render((screen.width/2)-(64), (screen.height/2), "MATCH POINT", Font.thickExoFont, screen);}
        if(SDFlag
        && ((hurryUpTime <= 120 && hurryUpTime >= 90) || (hurryUpTime <= 60 && hurryUpTime >= 30)))
        {
            Font.render((screen.width/2)-(64), (screen.height/2) + 32, "HURRY UP!", Font.thickExoFont, screen);
        }
        if(mos > 1){screen.renderMosaic(mos);}
    }
}
