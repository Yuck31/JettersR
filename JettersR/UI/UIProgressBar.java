package JettersR.UI;

import JettersR.*;
import JettersR.Util.*;

public class UIProgressBar extends UIComponent
{
    private Vector2i size;
    
    public UIProgressBar(Vector2i position, Vector2i size)
    {
        super(position);
        this.size = size;
    }
    
    public void update(){}
    
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
    
    public void render(Screen screen, Sprite sprite, int reduction)
    {
        screen.renderSprite(position.x, position.y, sprite, reduction, false);
    }
}