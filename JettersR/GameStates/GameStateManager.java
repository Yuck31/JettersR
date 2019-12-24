package JettersR.GameStates;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.Entity.Mob.*;
import java.util.ArrayList;
import JettersR.GameStates.StoryMissions.*;
import JettersR.GameStates.BattleMaps.*;

public class GameStateManager
{
    private GameState[] gameStates;

    public ItemTipsBoard itemTips;
    public Font font;
    public int reduction = 0;

    private int currentState = 0;
    private int previousState = 0;
    //public static int minX, maxX, minY, maxY;

    public static final int NUMGAMESTATES = 9;
    public static final int MENUSTATE = 0,
    VICTORYSCREENSTATE = 1,
    BOMBFACTORYSTATE = 2,
    GREATWALLSTATE = 3,
    POWERZONESTATE = 4,
    FIRSTMISSIONSTATE = 5,
    ROCKGARDENSTATE = 6,
    WIDEMAPSTATE = 7,
    TESTSTATE = 8;

    public Keyboard key;
    public boolean paused = false;
    private int menuNum = 0;
    public int timeMinute = 2;
    public int timeSecond = 30;
    private int keyTime = 6;
    public Player[] players;
    public int roundNum = 1;

    public int playerAmount = 4;
    public byte setsToWin = 3;
    public boolean pressureBlocks = true;
    public boolean revengeCarts = false;
    public boolean randomPlayerSpawns = false;
    public byte skullAmount = 0;
    public byte[] playerWins = new byte[8];

    //Pause Menu Variables
    private int currentOption = 0;
    private String[] options = {
            "RESUME",
            "ITEM HELP",
            "BRIGHTNESS",
            "RENDER UI",
            "FPS",
            "QUIT MAP"
        };
    //
    public boolean renderUI = true;

    public GameStateManager(Keyboard key)
    {
        this.key = key;
        itemTips = new ItemTipsBoard(key);
        font = new Font();
        for(byte i = 0; i < playerWins.length; i++)
        {
            playerWins[i] = 0;
        }
        gameStates = new GameState[NUMGAMESTATES];

        currentState = MENUSTATE;
        loadState(currentState);
    }

    private void loadState(int state)
    {
        switch(state)
        {
            case MENUSTATE: gameStates[state] = new MenuState(this, key); break;
            case VICTORYSCREENSTATE: gameStates[state] = new VictoryScreenState(this, key, players, playerWins, timeMinute, timeSecond, previousState); break;
            case BOMBFACTORYSTATE: gameStates[state] = new BombFactoryState(this, key, timeMinute, timeSecond, playerWins); break;
            case GREATWALLSTATE: gameStates[state] = new GreatWallState(this, key, timeMinute, timeSecond, playerWins); break;
            case POWERZONESTATE: gameStates[state] = new PowerZoneState(this, key, timeMinute, timeSecond, playerWins); break;
            case FIRSTMISSIONSTATE: gameStates[state] = new FirstMissionState(this, key); break;
            case ROCKGARDENSTATE: gameStates[state] = new RockGardenState(this, key, timeMinute, timeSecond, playerWins); break;
            case WIDEMAPSTATE: gameStates[state] = new WideMapState(this, key, timeMinute, timeSecond, playerWins); break;
            case TESTSTATE: gameStates[state] = new TestState(this, key); break;
        }
        this.currentState = state;
    }

    public void setPreviousState(int state)
    {
        this.previousState = state;
    }

    public void setPlayersArray(Player[] players)
    {
        this.players = players;
    }

    public void setWinsArray(byte[] playerWins)
    {
        this.playerWins = playerWins;
    }

    public void resetWinsArray()
    {
        this.playerWins = new byte[8];
        for(byte i = 0; i > this.playerWins.length; i++)
        {
            playerWins[i] = 0;
        }
    }

    public void setTime(int timeMinute, int timeSecond)
    {
        this.timeMinute = timeMinute;
        this.timeSecond = timeSecond;
    }

    private void unloadState(int state)
    {
        gameStates[state] = null;
    }

    public void setState(int state)
    {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }
    
    public void setRoundNum(int roundNum)
    {
        this.roundNum = roundNum;
    }

    public void update()
    {       
        if(gameStates[currentState] == null){return;}
        gameStates[currentState].update();

        if(keyTime > 0){keyTime--;}
        if((key.pause && keyTime <= 0 && currentState > VICTORYSCREENSTATE) && !paused)
        {
            pause();
        }        
        else if((key.bomb[0] && currentState > VICTORYSCREENSTATE && keyTime <= 0) && paused && menuNum == 0)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.cancel));
            unPause();
            menuNum = 0;
            currentOption = 0;
        }

        if((key.pause && keyTime > 0) || currentState <= VICTORYSCREENSTATE)
        {
            keyTime = 3;
        }

        if(paused)
        {
            if((key.up[0] || key.down[0] || key.bomb[0]) && keyTime > 0)
            {
                keyTime = 2;
            }
            else if((key.up[0] || key.down[0] || key.left[0] || key.right[0]) && keyTime <= 0)
            {
                if(menuNum != 1 || !(key.up[0] || key.down[0]))
                {Game.am.add(AudioManager.audioPlayer(AudioManager.select0));}
            }

            switch(menuNum)
            {
                case 0:
                if(key.up[0] && keyTime == 0)
                {
                    currentOption--;
                    keyTime = 2;
                    if(currentOption <= -1)
                    {
                        currentOption = options.length - 1;
                    }
                }
                if(key.down[0] && keyTime == 0)
                {
                    currentOption++;
                    keyTime = 2;
                    if(currentOption >= options.length)
                    {
                        currentOption = 0;
                    }
                }
                if(key.pause && keyTime == 0)
                {
                    select();
                    keyTime = 2;
                }

                if(key.left[0] && currentOption == 2 && keyTime == 0)
                {
                    if(Game.brightness >= -100f)Game.setBrightness(-5f);
                    if(Game.brightness < -100f){Game.brightness = -100f;}
                    keyTime = 2;
                }
                if(key.right[0] && currentOption == 2 && keyTime == 0)
                {
                    if(Game.brightness < 100f){Game.setBrightness(5f);}
                    if(Game.brightness > 100f){Game.brightness = 100f;}
                    keyTime = 2;
                }
                reduction = (int)(50 - (Game.brightness/2));
                break;

                case 1:
                if((key.left[0] || key.right[0]) && (keyTime <= 0 || keyTime > 0))
                {
                    keyTime = 2;
                }
                itemTips.update();
                if(key.bomb[0] && keyTime == 0)
                {
                    Game.am.add(AudioManager.audioPlayer(AudioManager.cancel));
                    menuNum = 0;
                    keyTime = 2;
                }
            }
        }
    }

    public void select()
    {
        Game.am.add(AudioManager.audioPlayer(AudioManager.confirm0));
        switch(currentOption)
        {
            case 0:
            unPause();
            currentOption = 0;
            break;

            case 1:
            menuNum = 1;
            break;

            case 2:
            Game.brightness = 1.0f;
            break;

            case 3:
            if(renderUI){renderUI = false;}
            else{renderUI = true;}
            break;

            case 4:
            if(Game.TARGET_FPS == 60)
            {
                Game.TARGET_FPS = 30;
                Game.TARGET_DELTA = 1000/Game.TARGET_FPS;
            }
            else
            {
                Game.TARGET_FPS = 60;
                Game.TARGET_DELTA = 1000/Game.TARGET_FPS;
            }
            break;

            case 5:
            gameStates[currentState].quitGame();
            currentOption = 0;
            break;
        }
    }

    public void pause()
    {
        keyTime = 2;
        paused = true;
    }

    public void unPause()
    {
        keyTime = 2;
        key.pause = false;
        paused = false;
    }

    public void render(Screen screen)
    {
        if(gameStates[currentState] == null){return;}
        gameStates[currentState].render(screen);
        if(paused)
        {
            switch(menuNum)
            {
                case 0:
                for(int i = 0; i < options.length; i++)
                {
                    if(i == currentOption)
                    {
                        font.render(305,(66 * (i + 1)),options[i], Font.orangeFont, screen);
                    }
                    else
                    {
                        font.render(305,(66 * (i + 1)),options[i], Font.pinkFont, screen);
                    }
                }
                screen.renderSheet(625,210,SpriteSheet.brightnessBar, reduction, false);
                font.render(410,344,Integer.toString(Game.TARGET_FPS), Font.numbersUI, screen);
                break;

                case 1:
                itemTips.render(screen);
            }
        }
    }
}