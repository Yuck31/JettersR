package JettersR.UI;

import JettersR.*;
import java.util.ArrayList;
import java.util.List;

public class UIManager//This manages the User Interface(UI)
{
    public List<UIPanel> panels = new ArrayList<UIPanel>();

    public UIManager()
    {

    }

    public void addPanel(UIPanel panel)
    {
        panels.add(panel);
    }

    public void update()
    {
        for(UIPanel panel : panels)
        {
            panel.update();
        }
    }

    public void render(Screen screen)
    {
        for(UIPanel panel : panels)
        {
            panel.render(screen);
        }
    }

    public void remove()
    {
        for (int i = 0; i < panels.size(); i++)
        {
            panels.remove(i);//Removes Panels from list of Panels
        }
        System.gc();//Calls garbage collection(not 100% effective)
    }
}