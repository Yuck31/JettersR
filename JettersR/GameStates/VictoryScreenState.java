package JettersR.GameStates;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.Entity.Mob.*;
import java.awt.event.KeyEvent;

public class VictoryScreenState extends GameState
{
    private Keyboard key;
    private BackGround bg;
    private Player[] players;
    private byte[] playerWins;
    private int gameState = 0;

    float y = 5*1280f;

    private int keyTime = 0;
    public int timeMinute = 2;
    public int timeSecond = 30;
    private Font font;
    public VictoryScreenState(GameStateManager gsm, Keyboard key, Player[] players, byte[] playerWins, int timeMinute, int timeSecond, int gameState)
    {
        this.gsm = gsm;
        this.key = key;
        keyTime = 10;
        this.players = players;
        this.playerWins = playerWins;
        this.timeMinute = timeMinute;
        this.timeSecond = timeSecond;
        this.gameState = gameState;
        font = new Font();
        bg = new BackGround(Sprite.menuBackGround, 1);
        bg.setVector(3, 0);
        init();
    }

    public void init()
    {
        Game.am.setOGG(AudioManager.oggPlayer(AudioManager.music_menu));
        Game.am.playOGG();
    }

    public void update()
    {
        bg.update();
        // if(!gsm.music_menu.getPlaying())
        // {
            // gsm.music_menu.play();
        // }
        if(keyTime > 0 && (!key.up[0] && !key.down[0] && !key.left[0] && !key.right[0]&& !key.pause)){keyTime--;}

        if(timeMinute <= 0 && timeSecond <= 30)
        {
            timeMinute = 10;
            timeSecond = 0;
        }
        if(timeMinute >= 10 && timeSecond > 0)
        {
            timeMinute = 1;
            timeSecond = 0;
        }

        if(key.pause && keyTime == 0)
        {
            Game.am.stopOGG();
            for(int i = 0; i < playerWins.length; i++)
            {
                if(playerWins[i] == gsm.setsToWin)
                {
                    gsm.resetWinsArray();
                    System.gc();
                    gsm.setState(gsm.MENUSTATE);
                    return;
                }
            }
            gsm.setTime(timeMinute, timeSecond);
            gsm.setWinsArray(playerWins);
            System.gc();
            gsm.setState(gameState);
        }
        y -= 0.009f * 1026f;
    }

    public void render(Screen screen)
    {
        //bg.render(screen);

        screen.mode7Render(0.5f, y, -90f, 0, 0, 205.2f, 0, Sprite.menuBackGround);
        if(y < 4.67f * 1026f){y = 5 * 1026f;}

        font.render(336,0,"RESULTS", Font.orangeFont, screen);

        for(int i = 0; i < players.length; i++)
        {
            if(players[i] != null)
            {
                screen.renderSprite((100 * (i+1))-12, 300, Sprite.customPlayer(players[i].frontIdle.getSprite(), players[i].playerColors), false);
                font.render((100 * (i+1)), 350, Byte.toString(playerWins[i]),Font.numbersUI,screen);
            }
        }
    }
}