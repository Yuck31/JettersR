package JettersRLevelEditor;
/**
 * GameStateManager for the Level Editor
 *
 * @author: Luke Sullivan
 * @1/19/20
 */
import JettersR.*;
import JettersR.GameStates.*;

public class LE_GameStateManager extends GameStateManager
{
    public LE_GameStateManager(Keyboard key)
    {
        super(key, -1);
        currentState = 0;
        gameStates[currentState] = new LevelEditor(Game.getUIManager(), key, Game.mouse);
    }
}
