package JettersR.GameStates;
/**
 * BOIS...
 * WE GOT MODE 7!!!!!!!!!!
 *
 * author: Luke Sullivan
 * Last Edit: 5/23/2019
 */
import JettersR.*;
import JettersR.UI.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;
import JettersR.Mode7Entities.Mobs.*;

import java.util.Random;
public class TestState extends GameState
{
    public Mode7Level level;
    private Random random = new Random();

    private String[] musicPaths = new String[2];

    public int timeMinute = 2;//Minute + ":" + Second
    public int timeSecond = 30;
    public int timeFrames = 60;//Used for counting down seconds
    public int startTimeMinute = 2;//Minute + ":" + Second
    public int startTimeSecond = 30;
    public int SDFrames = 16;

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

    public int minX = 6;
    public int maxX = 22;
    public int minY = 2;
    public int maxY = 15;

    private Keyboard key;
    private int keyTime = 2;
    private Font font;
    private static UIManager uiManager;
    private Mode7PlayerJet player;
    public Player[] players = new Player[4];
    public byte[] playerWins;

    float x = 0f;
    float y = 0f;
    float a = 0f;
    float fNear = 0f;
    float vertOff = 0f;
    float fFar = 5f;
    float FoVoffset = 0;
    public int r = 0;

    public TestState(GameStateManager gsm, Keyboard key)
    {
        this.gsm = gsm;
        this.key = key;
        font = new Font();
        minX = (minX * 32) + 4;
        maxX = (maxX * 32) + 4;
        minY = (minY * 32) - 3;
        maxY = (maxY * 32);
        init();
    }

    public void init()
    {
        this.level = new Mode7Level();
        this.uiManager = new UIManager();
        player = new Mode7PlayerJet(0,0,0,key,uiManager);
        this.level.add(player);
    }

    public void update()
    {
        if(keyTime > 0){keyTime--;}
        if(!gsm.paused)
        {
            this.level.update();
            if(key.up[0])
            {
                x += Math.cos(a * (Math.PI/180)) * 10f;
                y += Math.sin(a * (Math.PI/180)) * 10f;
            }
            else if(key.down[0])
            {
                x -= Math.cos(a * (Math.PI/180)) * 10f;
                y -= Math.sin(a * (Math.PI/180)) * 10f;
            }
            if(key.swapR[0])
            {
                x += Math.cos((a * ((Math.PI/180))+90)) * 10f;
                y += Math.sin((a * ((Math.PI/180))+90)) * 10f;
            }
            else if(key.swapL[0])
            {
                x -= Math.cos((a * ((Math.PI/180))+90)) * 10f;
                y -= Math.sin((a * ((Math.PI/180))+90)) * 10f;
            }

            if(key.right[0])
            {
                a += 2.5f;
            }
            else if(key.left[0])
            {
                a -= 2.5f;
            }

            if(key.up[1]){vertOff+=5f;}
            else if(key.down[1]){vertOff-=5f;}

            if(key.right[1])
            {
                a += 5f;
            }
            else if(key.left[1])
            {
                a -= 5f;
            }

            if(key.up[2]){fFar += 10f;}
            else if(key.down[2]){fFar -= 10f;}

            if(key.right[2]){FoVoffset += 0.001f;}
            else if(key.left[2]){FoVoffset -= 0.001f;}
            
            if(key.up[3]){fNear+=0.01f;}
            else if(key.down[3]){fNear-=0.01f;}
            
            if(vertOff > 90){vertOff = 90;}
            else if(vertOff < -90){vertOff = -90;}
            
            System.out.println(x + ", " + y + ", " + a + ", " +  fNear + ", " + fFar + ", " + FoVoffset + ",  " + vertOff);
        }
    }

    public void quitGame()
    {
        uiManager.remove();
        gsm.paused = false;
        System.gc();
        gsm.setState(GameStateManager.MENUSTATE);
    }

    public void render(Screen screen)
    {
        float xScroll = x - screen.width / 2;
        float yScroll = y - screen.height / 2;
        screen.mode7Render(x,y,a,vertOff, fNear, fFar, FoVoffset, Sprite.battleWinnerGround);
        //screen.mode7Render(x,y,a,fNear, fFar, FoVoffset, vertOff, Sprite.fireUp);
        this.level.render(screen);
        uiManager.render(screen);
    }
}