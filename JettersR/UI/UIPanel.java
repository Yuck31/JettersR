package JettersR.UI;
/**
 * 
 */
import JettersR.*;
import JettersR.Util.*;

import java.util.ArrayList;
import java.util.List;

public class UIPanel//This is essentially an object that is meant to store UI Components
{
    private List<UIComponent>components = new ArrayList<UIComponent>();
    private Vector2i position;
    public boolean translucent = false;

    private Sprite sprite;

    public UIPanel(Vector2i position, Sprite sprite, boolean translucent)
    {
        this.position = position;
        this.sprite = sprite;
        this.translucent = translucent;
    }

    public void addComponent(UIComponent component)
    {
        components.add(component);
    }

    public void update()
    {
        for(UIComponent component : components)
        {
            component.setOffset(position);
            component.update();
        }
    }

    public boolean intersects(Mouse mouse)
    {
        for(UIComponent component : components)
        {
            if(component.intersects(mouse))
            {
                return true;
            }
        }
        return false;
    }

    public void render(Screen screen)
    {
        if(sprite != null)
        {
            if(translucent){screen.renderTranslucentSprite(position.x, position.y, 20f, sprite, false);}
            else{screen.renderSprite(position.x, position.y, sprite, false);}
        }
        for(UIComponent component : components)
        {
            component.render(screen);
        }
    }
}