package JettersR.UI;
/**
 * This is the class that represent UI Panels (sprites, words, buttons, etc.)
 * 
 * author: Luke Sullivan
 * 1/1/20
 */
import JettersR.*;
import JettersR.Util.*;

import java.awt.Color;
import java.awt.Rectangle;

public class UILabel extends UIComponent//This displays UI Text and Sprites
{
    public UILabel(Vector2i position, String text)
    {
        super(position);
        this.text = text;
        color = new Color(0x00000000);
    }

    public UILabel(Vector2i position, String text, UIPanel panel)
    {
        this(position, text);
        panel.addComponent(this);
    }

    public UILabel(Vector2i position, Sprite sprite)
    {
        super(position);
        this.sprite = sprite;
        color = new Color(0x00000000);
    }

    public UILabel(Vector2d positionD, Sprite sprite)
    {
        super(positionD);
        this.sprite = sprite;
        color = new Color(0x00000000);
    }

    public UILabel(Vector2i position, Sprite sprite, UIPanel panel)
    {
        this(position, sprite);
        panel.addComponent(this);
    }

    public UILabel(Vector2d positionD, Sprite sprite, UIPanel panel)
    {
        this(positionD, sprite);
        panel.addComponent(this);
    }

    public UILabel(Vector2d positionD, Sprite sprite, int width, int height, UIPanel panel)
    {
        this(positionD, sprite, panel);
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int)positionD.x, (int)positionD.y, width, height);
    }
    
    public void setBounds(Vector2d positionD, int width, int height)
    {
        bounds = new Rectangle((int)positionD.x, (int)positionD.y, width, height);
    }

    public void render(Screen screen)
    {
        if(font != null)
        {
            Font.render(position.x + offset.x, position.y + offset.y, text, font, screen);
        }
        else if(sprite != null)
        {
            screen.renderSprite(position.x + offset.x, position.y + offset.y, sprite, false);
        }
        if(positionD != null){screen.drawRect((int)positionD.x, (int)positionD.y, width, height, 0xFF00FFFF, false);}
    }
}