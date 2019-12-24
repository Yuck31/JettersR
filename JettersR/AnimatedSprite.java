package JettersR;
/**
 * This is the class that manages animations.
 * Animations in the game are simply arrays of sprites, flipped through to make in-game animations.
 * 
 * author: Luke Sullivan
 * Last Edit: 9/21/2019
 */
public class AnimatedSprite extends Sprite
{
    private int frame = 0;
    private Sprite sprite;
    private float rate;//Delay between frames (in game Frames). This is a float so that way, animations can be a precise as I WANT.
    //Ex: A rate of 2.5 wil do: One frame after 2 frames, and one frame 3 frames after that. So on and so forth.
    private float time = 0;
    private int length = -1;
    private boolean doesLoop = true;//Controls rather animations can loop back to the first frame after finishing the animation

    public AnimatedSprite(SpriteSheet sheet, int width, int height, int length, float rate)
    {
        super(sheet, width, height);
        this.sheet = sheet;
        this.length = length;
        this.rate = rate;
        sprite = sheet.getSprites()[0];
    }

    public AnimatedSprite(SpriteSheet sheet, int width, int height, int length, float rate, boolean doesLoop)
    {
        super(sheet, width, height);
        this.sheet = sheet;
        this.length = length;
        this.rate = rate;
        sprite = sheet.getSprites()[0];
        this.doesLoop = doesLoop;
    }

    public void update()
    {
        time++;
        if(time >= rate)
        {
            if(frame+1 > length-1)
            {
                if(doesLoop)
                {
                    frame = 0;
                    time -= rate;
                }
            }
            else
            {
                frame++;
                time -= rate;
            }
            sprite = sheet.getSprites()[frame];
        }
    }

    public void updateReverse()
    {
        time++;
        if(time >= rate)
        {
            if(frame <= 0)
            {
                if(doesLoop){frame = length-1;}
            }
            else {frame--; time -= rate;}
            sprite = sheet.getSprites()[frame];
        }
    }

    public void resetAnim()
    {
        frame = 0;
        time = 0;
        sprite = sheet.getSprites()[0];
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setRate(float rate)
    {
        this.rate = rate;
    }
}
