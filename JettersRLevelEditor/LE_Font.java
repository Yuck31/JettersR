package JettersRLevelEditor;
/**
 * Font for the level editor
 *
 * @author: Luke Sullivan
 * @11/31/19
 */
import JettersR.*;

public class LE_Font
{
    public static SpriteSheet LE_Font = new SpriteSheet("/JettersRLevelEditor/Fonts/LE_Font.png", 5);
    public static Sprite[] LE_FontCharacters = Sprite.split(LE_Font);
    private static String LE_FontCharIndex = 
        "ABCDEFGHIJKLM" +//
        "NOPQRSTUVWXYZ" +//
        "0123456789!@#" +//
        "$%^&*()-_+=,." +//
        "?/\\| ";

    public static void render(int x, int y, String text, Screen screen)
    {
        int ya = 0;
        int xa = 0;
        int ia = 0;
        for(int i = 0; i < text.length(); i++)
        {
            char currentChar = text.charAt(i);
            int index = LE_FontCharIndex.indexOf(currentChar);
            if(currentChar == ' '){xa += 2;}
            else if(currentChar == '`')
            {
                ya = ya + 6;
                xa = 0;
                ia = 0;
            }
            else{screen.renderSprite(x+xa+(ia*5),y+ya,LE_FontCharacters[index], false);ia++;}

            // switch(currentChar)
            // {
                // case 'M': 
                // case 'm':
                // case 'W':
                // case 'w': xa++;
                // break;

                // case 'I':
                // case 'i':
                // case 'l': xa -= 4;
                // break;
            // }
        }
    }
}
