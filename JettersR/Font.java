package JettersR;

/**
 * This Class uses SpriteSheets and arrys of Sprites to make fonts.
 * Other classes can then call this class's render method to take a string of text and render letters (sprites) from it.
 *
 * author: Luke Sullivan
 * Last Edit: 10/5/2019
 */

import java.awt.event.KeyEvent;
import com.studiohartman.jamepad.*;

public class Font
{
    public static SpriteSheet numbersUI = new SpriteSheet("/Fonts/NumbersUI.png", 16);
    public static Sprite[] numbersUIcharacters = Sprite.split(numbersUI);
    private static String numbersUIcharIndex = "0123456789:Ax ";

    public static SpriteSheet pinkFont = new SpriteSheet("/Fonts/FONT-Pink.png", 32);
    public static Sprite[] pinkFontcharacters = Sprite.split(pinkFont);
    private static String pinkFontcharIndex =
        "ABCDEFG" +//
        "HIJKLMN" +//
        "OPQRSTU" +//
        "VWXYZ01" +//
        "2345678" +//
        "9!?.-@a" +//
        "bcdefgh" +//
        "ijk ";

    public static SpriteSheet orangeFont = new SpriteSheet("/Fonts/FONT-Orange_Cap.png", 32);
    public static Sprite[] orangeFontcharacters = Sprite.split(orangeFont);
    private static String orangeFontcharIndex =
        "ABCDEFG" +//
        "HIJKLMN" +//
        "OPQRSTU" +//
        "VWXYZ01" +//
        "2345678" +//
        "9!?.-@a" +//
        "bcdefgh" +//
        "ijk ";

    public static SpriteSheet greenFont = new SpriteSheet("/Fonts/FONT-Green_Small_Cap.png", 16);
    public static Sprite[] greenFontcharacters = Sprite.split(greenFont);
    private static String greenFontcharIndex =
        "ABCDE" + //
        "FGHIJ" + //
        "KLMNO" + //
        "PQRST" + //
        "UVWXY" + //
        "Z ";

    public static SpriteSheet exoFont = new SpriteSheet("/Fonts/ExoFont.png", 10);
    public static Sprite[] exoFontcharacters = Sprite.split(exoFont);
    private static String exoFontcharIndex = 
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + //
        "abcdefghijklmnopqrstuvwxyz" + //
        "0123456789\"\'.,!?()&%+-*/\\=" + //
        ":;_[]{}` ";

    public static SpriteSheet thickExoFont = new SpriteSheet("/Fonts/ThickExoFont.png", 12);
    public static Sprite[] thickExoFontcharacters = Sprite.split(thickExoFont);
    private static String thickExoFontcharIndex = 
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + //
        "abcdefghijklmnopqrstuvwxyz" + //
        "0123456789\"\'.,!?()&%+-*/\\=" + //
        ":;_[]{}` ";

    public static SpriteSheet keyBoardKeys = new SpriteSheet("/Fonts/KeyboardKeys.png", 10, 22);
    public static Sprite[] keyBoardKeysCharacters = Sprite.split(keyBoardKeys);
    private static int[] keyBoardKeysIndex =
        {
            KeyEvent.VK_ESCAPE, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_BACK_QUOTE,//
            KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0, KeyEvent.VK_UNDERSCORE, KeyEvent.VK_EQUALS, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_INSERT,//
            KeyEvent.VK_HOME, KeyEvent.VK_PAGE_UP, KeyEvent.VK_DIVIDE, KeyEvent.VK_MULTIPLY, KeyEvent.VK_MINUS, KeyEvent.VK_TAB, KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_Y, KeyEvent.VK_U, KeyEvent.VK_I,//
            KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_OPEN_BRACKET, KeyEvent.VK_CLOSE_BRACKET, KeyEvent.VK_BACK_SLASH, KeyEvent.VK_DELETE, KeyEvent.VK_END, KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9, KeyEvent.VK_ADD, KeyEvent.VK_A, KeyEvent.VK_S,//
            KeyEvent.VK_D, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_SEMICOLON, KeyEvent.VK_QUOTE, KeyEvent.VK_ENTER, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6, KeyEvent.VK_SHIFT,//
            KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C, KeyEvent.VK_V, KeyEvent.VK_B, KeyEvent.VK_N, KeyEvent.VK_M, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH, KeyEvent.VK_UP, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3,//
            KeyEvent.VK_CONTROL, KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT, KeyEvent.VK_SPACE, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD0, KeyEvent.VK_DECIMAL, KeyEvent.VK_UNDEFINED, KeyEvent.VK_UNDEFINED, KeyEvent.VK_UNDEFINED, KeyEvent.VK_UNDEFINED, KeyEvent.VK_UNDEFINED,//
        };
        
        // public static SpriteSheet xboxControllerButtons = new SpriteSheet("/Fonts/XboxControllerButtons.png", 10, 12);
        // public static Sprite[] xboxControllerButtonsCharacters = Sprite.split(xboxControllerButtons);
        // private static ControllerButton[] xboxControllerButtonIndex =
        // {
            // ControllerButton.A, ControllerButton.B, ControllerButton.X, ControllerButton.Y,//
            // ControllerButton.START, ControllerButton.BACK, ControllerButton.BACK, ControllerButton.BACK,//
            // ControllerButton.LEFTBUMPER, ControllerButton.RIGHTBUMPER, 
        // };

    public Font()
    {

    }

    public static void render(int x, int y, String text, SpriteSheet font, Screen screen)
    {
        int originX = x;
        int xOffset = 0;
        int yOffset = 0;

        if(font == numbersUI)
        {
            for(int i = 0; i < text.length(); i++)
            {
                char currentChar = text.charAt(i);
                int index = numbersUIcharIndex.indexOf(currentChar);
                if(index == ' '){screen.renderSprite(x+(i*6),y,numbersUIcharacters[index],false);}
                else{screen.renderSprite(x+(i*10),y,numbersUIcharacters[index],false);}
            }
        }

        else if(font == pinkFont)
        {
            for(int i = 0; i < text.length(); i++)
            {
                char currentChar = text.charAt(i);
                int index = pinkFontcharIndex.indexOf(currentChar);
                if(index == ' '){screen.renderSprite(x+(i*6),y,pinkFontcharacters[index],false);}
                else{screen.renderSprite(x+(i*32),y,pinkFontcharacters[index], false);}
            }
        }

        else if(font == orangeFont)
        {
            for(int i = 0; i < text.length(); i++)
            {
                char currentChar = text.charAt(i);
                int index = orangeFontcharIndex.indexOf(currentChar);
                if(index == ' '){screen.renderSprite(x+(i*6),y,orangeFontcharacters[index],false);}
                else{screen.renderSprite(x+(i*32),y,orangeFontcharacters[index],false);}
            }
        }

        else if(font == greenFont)
        {
            for(int i = 0; i < text.length(); i++)
            {
                char currentChar = text.charAt(i);
                int index = greenFontcharIndex.indexOf(currentChar);
                if(index == ' '){screen.renderSprite(x+(i*3),y,greenFontcharacters[index],false);}
                else{screen.renderSprite(x+(i*16),y,greenFontcharacters[index],false);}
            }
        }

        else if(font == exoFont)
        {
            int ya = 0;
            int xa = 0;
            int ia = 0;
            for(int i = 0; i < text.length(); i++)
            {
                char currentChar = text.charAt(i);
                int index = exoFontcharIndex.indexOf(currentChar);
                if(currentChar == ' '){xa += 8;}
                else if(currentChar == '`')
                {
                    ya = ya + 14;
                    xa = 0;
                    ia = 0;
                }
                else{screen.renderSprite(x+xa+(ia*10),y+ya,exoFontcharacters[index], false);ia++;}

                switch(currentChar)
                {
                    case 'M': 
                    case 'm':
                    case 'W':
                    case 'w': xa++;
                    break;

                    case 'I':
                    case 'i':
                    case 'l': xa -= 4;
                    break;
                }
            }
        }

        else if(font == thickExoFont)
        {
            int ya = 0;
            int xa = 0;
            int ia = 0;
            for(int i = 0; i < text.length(); i++)
            {
                char currentChar = text.charAt(i);
                int index = thickExoFontcharIndex.indexOf(currentChar);
                if(currentChar == ' '){xa += 8;}
                else if(currentChar == '`')
                {
                    ya = ya + 14;
                    xa = 0;
                    ia = 0;
                }
                else{screen.renderSprite(x+xa+(ia*10),y+ya,thickExoFontcharacters[index], false);ia++;}

                switch(currentChar)
                {
                    case 'M': 
                    case 'm':
                    case 'W':
                    case 'w': xa++;
                    break;

                    case 'I':
                    case 'i':
                    case 'l': xa -= 4;
                    break;
                }
            }
        }
    }

    public static void render(int x, int y, int key, boolean pressed, Screen screen)
    {
        int index = keyBoardKeysIndex.length - 1;
        for(int i = 0; i < keyBoardKeysIndex.length; i++)
        {
            if(keyBoardKeysIndex[i] == key)
            {
                index = i;
                break;
            }
        }
        if(!pressed){screen.renderSprite(x, y, keyBoardKeysCharacters[index], false);}
        else{screen.renderSprite(x, y, keyBoardKeysCharacters[index + keyBoardKeysIndex.length], false);}
    }
}
