package JettersR.UI;
/**
 * This is the class that represent UI Components
 * 
 * author: Luke Sullivan
 * 1/1/20
 */
import JettersR.*;
import JettersR.Util.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.awt.Rectangle;

public abstract class UIComponent
{
    public Vector2i position, offset;
    public Vector2d positionD, offsetD;
    protected Rectangle bounds = new Rectangle(0, 0, 0, 0);
    protected int width = 0, height = 0;
    public int backgroundColor;
    public Color color;
    public String text;
    public SpriteSheet font;
    public Sprite sprite;
    public UIComponent(Vector2i position)
    {
        this.position = position;
        offset = new Vector2i();
    }
    
    public UIComponent(Vector2d positionD)
    {
        this.positionD = positionD;
        this.position = new Vector2i((int)positionD.x, (int)positionD.y);
        offsetD = new Vector2d();
        this.offset = new Vector2i((int)offsetD.x, (int)offsetD.y);
    }
    
    public UIComponent setColor(int color)
    {
        this.color = new Color(color);
        return this;
    }
    
    public void update(){}
    
    public void render(Screen screen){}
    
    public void setOffset(Vector2i offset)
    {
        this.offset = offset;
    }
    
    public void setOffset(Vector2d offsetD)
    {
        this.offsetD = offsetD;
        this.offset = new Vector2i((int)offsetD.x, (int)offsetD.y);
    }
    
    public UIComponent setFont(SpriteSheet font)
    {
        this.font = font;
        return this;
    }

    public UIComponent setText(String text)
    {
        this.text = text;
        return this;
    }
    
    public UIComponent setSprite(Sprite sprite)
    {
        this.sprite = sprite;
        return this;
    }
    
    public boolean intersects(Mouse mouse)
    {
        if(bounds.intersects(mouse.getBounds()))
        {
            return true;
        }
        else{return false;}
    }
}