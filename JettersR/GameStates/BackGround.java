package JettersR.GameStates;

import JettersR.*;

public class BackGround
{
    private double x;
    private double y;
    private double dx;
    private double dy;
    
    private Sprite sprite;
    private double moveScale;
    
    public BackGround(Sprite sprite, double moveScale)
    {
        this.sprite = sprite;
        this.moveScale = moveScale;
    }
    
    public void setWrapPosition(double x, double y)
    {
        this.x = (x * moveScale) % sprite.width;
        this.y = (y * moveScale) % sprite.height;
    }
    
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void setVector(double dx, double dy)
    {
        this.dx = dx;
        this.dy = dy;
    }
    
    public void update()
    {
        this.x += dx;
        this.y += dy;
    }
    
    public void render(Screen screen, boolean fixed)
    {
        screen.renderSprite((int)x, (int)y, sprite, fixed);
        if(dx < 0 && x < 0)
        {
            screen.renderSprite((int)x + sprite.width, (int)y, sprite, fixed);
            setWrapPosition(x,y);
        }
        else if(dx > 0  && x > 0)
        {
            screen.renderSprite((int)x - sprite.width, (int)y, sprite, fixed);
            setWrapPosition(x,y);
        }
    }
    
    public void renderLights(Screen screen, float opacity, int brightness, boolean fixed)
    {
        screen.renderLighting((int)x, (int)y, brightness, sprite, fixed);
        screen.renderTranslucentSprite((int)x, (int)y, opacity, sprite, fixed);
        if(dx < 0 && x < 0)
        {
            screen.renderLighting((int)x + sprite.width, (int)y, brightness, sprite, fixed);
            screen.renderTranslucentSprite((int)x + sprite.width, (int)y, opacity, sprite, fixed);
            setWrapPosition(x,y);
        }
        else if(dx > 0 && x > 0)
        {
            screen.renderLighting((int)x - sprite.width, (int)y, brightness, sprite, fixed);
            screen.renderTranslucentSprite((int)x - sprite.width, (int)y, opacity, sprite, fixed);
            setWrapPosition(x,y);
        }
    }
}