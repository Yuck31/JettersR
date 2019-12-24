package JettersR.UI;

import JettersR.*;
import JettersR.Util.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class UIComponent
{
    public Vector2i position, offset;
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
    
    public UIComponent setColor(int color)
    {
        this.color = new Color(color);
        return this;
    }
    
    public void update(){}
    
    public void render(Screen screen){}
    
    void setOffset(Vector2i offset)
    {
        this.offset = offset;
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
}