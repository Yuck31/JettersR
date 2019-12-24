package JettersR.GameStates;

import JettersR.*;
import JettersR.UI.*;

import java.util.Random;
public abstract class GameState
{
    protected GameStateManager gsm;
    public static UIManager uiManager;
    public int roundNum = 1;
    public Random random;
    
    public abstract void init();
    public void endGame(){}
    public void endBattle(){}
    public void quitGame(){}
    public abstract void update();
    public abstract void render(Screen screen);
    public void setRoundNum(int roundNum){this.roundNum = roundNum;}
}
