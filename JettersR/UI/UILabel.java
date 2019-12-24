package JettersR.UI;

import JettersR.*;
import JettersR.Util.*;

import java.awt.Color;

public class UILabel extends UIComponent//This displays UI Text and Sprites
{
    public UILabel(Vector2i position, String text)
    {
        super(position);
        this.text = text;
        color = new Color(0x00000000);
    }

    public UILabel(Vector2i position, Sprite sprite)
    {
        super(position);
        this.sprite = sprite;
        color = new Color(0x00000000);
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
    }
}