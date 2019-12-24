package JettersR.Entity.Mob;
/**
 * This is the class that contains all of the code for the regular, generic, Hige-Hige Bandit.
 * ...Well, it's supposed to anyway.
 *
 * Luke Sullivan
 * 11/27/19
 */
import JettersR.*;
public class HigeHigeBandit extends Mob
{
    public AnimatedSprite idle;// = new AnimatedSprite();
    public HigeHigeBandit(int x, int y, int z)
    {
        super(x,y,z);
        
        health = 1;
    }
    
    public void update()
    {
        
    }
    
    public void render(Screen screen)
    {
        screen.renderSprite((int)x, (int)y, sprite, true);
    }
}
