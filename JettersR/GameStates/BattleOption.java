package JettersR.GameStates;
/**
 * This class represents the Settings within te Battle Settings menu.
 * They are used to adjust various variables for matches.
 *
 * @author: Luke Sullivan
 * @version: 10/5/19
 */
import JettersR.*;

public class BattleOption
{
    public enum Type
    {
        PLAYERAMOUNT,
        SETSTOWIN,
        TIMELIMIT,
        PRESSUREBLOCKS,
        REVENGECARTS,
        PLAYERSPAWNS,
        SKULLAMOUNT,
    }
    public Type type;
    public Sprite sprite;
    public GameStateManager gsm;

    public String text;
    public String value = "Off";

    public boolean selected = false;

    public int x = 0;
    public int y = 0;
    public int xa = 0;

    public BattleOption(int x, int y, Type type, GameStateManager gsm)
    {
        this.x = x;
        this.y = y;
        this.text = text;
        this.type = type;
        this.gsm = gsm;

        switch(this.type)
        {
            case PLAYERAMOUNT:
            text = "Player Amount:   ";
            value = Integer.toString(gsm.playerAmount);
            break;
            //
            //
            case SETSTOWIN:
            text = "Sets to Win:    ";
            value = Integer.toString(gsm.setsToWin);
            break;
            //
            //
            case TIMELIMIT:
            text = "Time Limit:    ";
            value = Integer.toString(gsm.timeMinute) + ":" + Integer.toString(gsm.timeSecond);
            break;
            //            
            //
            case PRESSUREBLOCKS: text = "Pressure Blocks:    ";
            if(gsm.pressureBlocks == true)
            {
                value = "On";
            }
            else
            {
                value = "Off";
            }
            break;
            //
            //
            case REVENGECARTS: text = "Revenge Carts:    ";
            value = "NOT IMPLEMENTED";//"Off";
            break;
            //
            //
            case PLAYERSPAWNS: text = "Player Spawns:    ";
            if(gsm.randomPlayerSpawns == true)
            {
                value = "Random";
            }
            else
            {
                value = "Default";
            }
            break;
            
            case SKULLAMOUNT: text = "Skull Amount:    ";
            value = "NOT IMPLEMENTED";//Integer.toString(gsm.skullAmount);
            break;
        }
    }

    public void update()
    {
        if(selected && xa < 0)
        {
            xa += 8;
            if(xa > 0){xa = 0;}
        }
        else if(!selected && xa > -64)
        {
            xa -= 8;
            if(xa < -64){xa = -64;}
        }        
    }

    public void adjustSetting(boolean dir)//false = left; true = right
    {
        switch(type)
        {
            case PLAYERAMOUNT:
            if(dir == false)
            {
                if(gsm.playerAmount - 1 > 1){gsm.playerAmount--;}
                else{gsm.playerAmount = 8;}
            }
            if(dir == true)
            {
                if(gsm.playerAmount + 1 < 9){gsm.playerAmount++;}
                else{gsm.playerAmount = 2;}
            }
            value = Integer.toString(gsm.playerAmount);
            break;
            //
            //
            case SETSTOWIN:
            if(dir == false)
            {
                if(gsm.setsToWin - 1 > 0){gsm.setsToWin--;}
                else{gsm.setsToWin = 5;}
            }
            if(dir == true)
            {
                if(gsm.setsToWin + 1 < 6){gsm.setsToWin++;}
                else{gsm.setsToWin = 1;}
            }
            value = Integer.toString(gsm.setsToWin);
            break;
            //
            //
            case TIMELIMIT:
            if(dir == false)
            {
                gsm.timeSecond = gsm.timeSecond - 30;
                if(gsm.timeSecond < 0)
                {
                    gsm.timeMinute--;
                    gsm.timeSecond = 30;
                }
            }
            if(dir == true)
            {
                gsm.timeSecond = gsm.timeSecond + 30;
                if(gsm.timeSecond >= 60)
                {
                    gsm.timeMinute++;
                    gsm.timeSecond = 0;
                }
            }

            if(gsm.timeMinute <= 0 && gsm.timeSecond <= 30)
            {
                gsm.timeMinute = 10;
                gsm.timeSecond = 0;
            }
            if(gsm.timeMinute >= 10 && gsm.timeSecond > 0)
            {
                gsm.timeMinute = 1;
                gsm.timeSecond = 0;
            }
            if(gsm.timeSecond < 10){value = Integer.toString(gsm.timeMinute) + ":0" + Integer.toString(gsm.timeSecond);}
            else{value = Integer.toString(gsm.timeMinute) + ":" + Integer.toString(gsm.timeSecond);}
            break;
            //
            //
            case PRESSUREBLOCKS:
            if(gsm.pressureBlocks == true)
            {
                gsm.pressureBlocks = false;
                value = "Off";
            }
            else
            {
                gsm.pressureBlocks = true;
                value = "On";
            }
            break;
            //
            //
            case REVENGECARTS: value = "NOT IMPLEMENTED";
            //if(value == "On"){value = "Off";}
            //else if(value == "Off"){value = "On";}
            break;
            //
            //
            case PLAYERSPAWNS:
            if(gsm.randomPlayerSpawns == true)
            {
                gsm.randomPlayerSpawns = false;
                value = "Default";
            }
            else
            {
                gsm.randomPlayerSpawns = true;
                value = "Random";
            }
            break;
            
            case SKULLAMOUNT: value = "NOT IMPLEMENTED";
            // if(dir == false)
            // {
                // if(gsm.skullAmount - 1 >= 0){gsm.skullAmount--;}
                // else{gsm.skullAmount = 3;}
            // }
            // if(dir == true)
            // {
                // if(gsm.skullAmount + 1 <= 3){gsm.skullAmount++;}
                // else{gsm.skullAmount = 0;}
            // }
            // value = Integer.toString(gsm.skullAmount);
            break;
        }
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void render(Screen screen)
    {
        if(selected)
        {
            sprite = Sprite.battleOptionSelected;
        }
        else
        {
            sprite = Sprite.battleOptionUnselected;
        }

        screen.renderSprite(x+xa,y,sprite,false);
        Font.render(x+xa+74,y+4,text+value,Font.thickExoFont,screen);
    }
}
