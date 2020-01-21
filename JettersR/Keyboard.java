package JettersR;

/**
 * This is the Class that manages user input via keyboard or controller (special thanks to jamepad by the way).
 * A single one of this class is instantiated by the Game class and then shared by all Players in the game.
 *
 * Here's a list of all this possible keys that can be used...
 * 
VK_0
VK_1 
VK_2 
VK_3 
VK_4 
VK_5 
VK_6 
VK_7 
VK_8 
VK_9 

VK_A
VK_ACCEPT
"Accept" or "Commit" function key.
VK_ADD 
VK_AGAIN 
VK_ALT 
VK_ALT_GRAPH
AltGraph function key.
VK_AMPERSAND 
VK_ASTERISK 
VK_AT
"@" key.

VK_B 
VK_BACK_QUOTE 
VK_BACK_SLASH
"\" key.
VK_BACK_SPACE
VK_BACK_QUOTE
"`" key.
VK_BEGIN
Constant for the Begin key.
VK_BRACELEFT 
VK_BRACERIGHT 

VK_C 
VK_CANCEL 
VK_CAPS_LOCK 
VK_CIRCUMFLEX
"^" key.
VK_CLEAR 
VK_CLOSE_BRACKET
"]" key.
VK_CODE_INPUT
"Code Input" function key.
VK_COLON
":" key.
VK_COMMA
"," key.
VK_COMPOSE
Compose function key.
VK_CONTEXT_MENU
Microsoft Windows Context Menu key.
VK_CONTROL 
VK_CONVERT
Convert function key.
VK_COPY 
VK_CUT 

VK_D 
VK_DEAD_ABOVEDOT 
VK_DEAD_ABOVERING 
VK_DEAD_ACUTE 
VK_DEAD_BREVE 
VK_DEAD_CARON 
VK_DEAD_CEDILLA 
VK_DEAD_CIRCUMFLEX 
VK_DEAD_DIAERESIS 
VK_DEAD_DOUBLEACUTE 
VK_DEAD_GRAVE 
VK_DEAD_IOTA 
VK_DEAD_MACRON 
VK_DEAD_OGONEK 
VK_DEAD_SEMIVOICED_SOUND 
VK_DEAD_TILDE 
VK_DEAD_VOICED_SOUND 
VK_DECIMAL 
VK_DELETE 
VK_DIVIDE 
VK_DOLLAR
"$" key.
VK_DOWN
Down arrow key.

VK_E 
VK_END 
VK_ENTER 
VK_EQUALS
"=" key.
VK_ESCAPE 
VK_EURO_SIGN
Euro currency sign key.
VK_EXCLAMATION_MARK
"!" key.

VK_F 
VK_F1
VK_F10
VK_F11
VK_F12
VK_F13
VK_F14
VK_F15
VK_F16
VK_F17
VK_F18
VK_F19
VK_F2
VK_F20
VK_F21
VK_F22
VK_F23
VK_F24
VK_F3
VK_F4
VK_F5
VK_F6
VK_F7
VK_F8
VK_F9

VK_FINAL 
VK_FIND 
VK_FULL_WIDTH
Constant for the Full-Width Characters function key.

VK_G 
VK_GREATER 
VK_H 
VK_HALF_WIDTH
Constant for the Half-Width Characters function key.

VK_HELP 
VK_HOME 
VK_I 

VK_INSERT 

VK_J 

VK_K 

VK_KP_DOWN
Numeric keypad down arrow key.

VK_KP_LEFT
Numeric keypad left arrow key.

VK_KP_RIGHT
Numeric keypad right arrow key.

VK_KP_UP
Numeric keypad up arrow key.

VK_L 
VK_LEFT
Left arrow key.

VK_LEFT_PARENTHESIS
"(" key.

VK_LESS 

VK_M 
VK_META 
VK_MINUS
"-" key

VK_MULTIPLY 
VK_N 

VK_NUM_LOCK 
VK_NUMBER_SIGN
"#" key.

VK_NUMPAD0 
VK_NUMPAD1 
VK_NUMPAD2 
VK_NUMPAD3 
VK_NUMPAD4 
VK_NUMPAD5 
VK_NUMPAD6 
VK_NUMPAD7 
VK_NUMPAD8 
VK_NUMPAD9 
VK_O 
VK_OPEN_BRACKET
"[" key

VK_P 
VK_PAGE_DOWN 
VK_PAGE_UP 
VK_PASTE 
VK_PAUSE 
VK_PERIOD
"." key

VK_PLUS
"+" key.

VK_PRINTSCREEN 
VK_PROPS 
VK_Q 
VK_QUOTE 
VK_QUOTEDBL 
VK_R 
VK_RIGHT
Right arrow key.

VK_RIGHT_PARENTHESIS
")" key.

VK_ROMAN_CHARACTERS
Roman Characters function key.

VK_S 
VK_SEMICOLON
";" key.

VK_SEPARATOR
Constant for the Numpad Separator key.

VK_SHIFT 
VK_SLASH
Constant for the forward slash key, "/"

VK_SPACE 
VK_STOP 
VK_SUBTRACT 

VK_T 
VK_TAB 
VK_U 
VK_UNDEFINED
This value is used to indicate that the keyCode is unknown.

VK_UNDERSCORE
"_" key.

VK_UNDO 
VK_UP
Up arrow key.

VK_V 
VK_W 
VK_WINDOWS
Microsoft Windows "Windows" key.

VK_X 
VK_Y 
VK_Z 

 * author: Luke Sullivan
 * Last Edit: 11/7/19
 */

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import com.studiohartman.jamepad.*;//CONTROLLER SUPPORT!

import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;

public class Keyboard implements KeyListener
{
    public static boolean[] keys = new boolean[224];//This is for all keys in general(key presses are returned as booleans)
    private final ControllerManager controllers;//This manages Controllers(controller inputs are also returned as booleans)

    //All keys are structured in a way where there is one action with 8 possible players to configure to.(8 Players only possible with controllers)

    //D-Pad
    public static boolean up[] = new boolean[8];//Moving Up
    public static boolean down[] = new boolean[8];//Moving Down
    public static boolean left[] = new boolean[8];//Moving Left
    public static boolean right[] = new boolean[8];//Moving Right
    //

    //Face Buttons
    public static boolean bomb[] = new boolean[8];//Placing Bombs
    public static boolean punch[] = new boolean[8];//Punching Bombs
    public static boolean remote[] = new boolean[8];//Detonating Remote Bombs or stopping Player's kicked Bomb
    public static boolean special[] = new boolean[8];//Activating special abilities(detonator, potentially Charaboms, etc.)
    //

    //Bumpers
    public static boolean swapL[] = new boolean[8];//In the case the Player is carrying more than one type of Bomb...
    public static boolean swapR[] = new boolean[8];//...They can press these buttons to switch between them.
    //

    //Misilanious Buttons
    public static boolean pause;//Pauses the Game
    //

    //D-Pad
    public static int upButton[] = new int[8];//Moving Up
    public static int downButton[] = new int[8];//Moving Down
    public static int leftButton[] = new int[8];//Moving Left
    public static int rightButton[] = new int[8];//Moving Right
    //

    //Face Buttons
    public static int bombButton[] = new int[8];//Placing Bombs
    public static int punchButton[] = new int[8];//Punching Bombs
    public static int remoteButton[] = new int[8];//Detonating Remote Bombs or stopping Player's kicked Bomb
    public static int specialButton[] = new int[8];//Activating special abilities(detonator, potentially Charaboms, etc.)
    //

    //Bumpers
    public static int swapLButton[] = new int[8];//In the case the Player is carrying more than one type of Bomb...
    public static int swapRButton[] = new int[8];//...They can press these buttons to switch between them.

    public static int pauseButton = KeyEvent.VK_ENTER;
    //Player Controller Assignments
    public static byte[] controllerNums = 
        {
            0,
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
        };

    public static int[][] playerKeys =
        {
            upButton,
            downButton,
            leftButton,
            rightButton,
            bombButton,
            punchButton,
            remoteButton,
            specialButton,
            swapLButton,
            swapRButton,
        };
    // public Keyboard()
    // {
    // try{
    // Scanner scanner = new Scanner (System.in);//Creates a new Scanner object
    // System.out.println("Input File Name to Read");
    // File file = new File(scanner.nextLine()); // Read file name from standard input
    // scanner = new Scanner(file);              // Overwrite scanner to read from file
    // FileWriter fw = new FileWriter("Controls.txt");
    // BufferedWriter bw = new BufferedWriter(fw);

    // while (scanner.hasNextLine()){
    // String line = scanner.nextLine();
    // String delimiter = "[? ,!.;\t]+"; 
    // String word[] = line.split(delimiter);
    // if (Integer.parseInt(word[1]) % 2 == 0){
    // bw.write(word[0] + "\t" + "Even");
    // bw.newLine();
    // }
    // else{
    // bw.write(word[0] + "\t" + "Odd");
    // bw.newLine();
    // }
    // }
    // System.out.println ("The output has been written to Controls.txt");
    // scanner.close();
    // bw.close();
    // }
    // catch(IOException e){
    // }
    // }
    public Keyboard()
    {
        controllers = new ControllerManager();
        controllers.initSDLGamepad();

        //Player 1
        upButton[0] = KeyEvent.VK_W;
        downButton[0]  = KeyEvent.VK_S;
        leftButton[0]  = KeyEvent.VK_A;
        rightButton[0]  = KeyEvent.VK_D;

        bombButton[0] = KeyEvent.VK_F;
        punchButton[0] = KeyEvent.VK_G;
        remoteButton[0] = KeyEvent.VK_E;
        specialButton[0] = KeyEvent.VK_Q;

        swapLButton[0] = KeyEvent.VK_R;
        swapRButton[0] = KeyEvent.VK_T;

        //Player 2
        upButton[1] = KeyEvent.VK_I;
        downButton[1] = KeyEvent.VK_K;
        leftButton[1] = KeyEvent.VK_J;
        rightButton[1] = KeyEvent.VK_L;

        bombButton[1] = KeyEvent.VK_SEMICOLON;
        punchButton[1] = KeyEvent.VK_QUOTE;
        remoteButton[1] = KeyEvent.VK_O;
        specialButton[1] = KeyEvent.VK_U;

        swapLButton[1] = KeyEvent.VK_P;
        swapRButton[1] = KeyEvent.VK_OPEN_BRACKET;

        //Player 3
        upButton[2] = KeyEvent.VK_UP;
        downButton[2] = KeyEvent.VK_DOWN;
        leftButton[2] = KeyEvent.VK_LEFT;
        rightButton[2] = KeyEvent.VK_RIGHT;

        bombButton[2] = KeyEvent.VK_NUMPAD0;
        punchButton[2] = KeyEvent.VK_DECIMAL;
        remoteButton[2] = KeyEvent.VK_NUMPAD1;
        specialButton[2] = KeyEvent.VK_PAGE_DOWN;

        swapLButton[2] = KeyEvent.VK_NUMPAD2;
        swapRButton[2] = KeyEvent.VK_NUMPAD3;

        //Player 4
        upButton[3] = KeyEvent.VK_NUMPAD8;
        downButton[3] = KeyEvent.VK_NUMPAD5;
        leftButton[3] = KeyEvent.VK_NUMPAD4;
        rightButton[3] = KeyEvent.VK_NUMPAD6;

        bombButton[3] = KeyEvent.VK_ADD;
        punchButton[3] = KeyEvent.VK_SUBTRACT;
        remoteButton[3] = KeyEvent.VK_NUMPAD9;
        specialButton[3] = KeyEvent.VK_NUMPAD7;

        swapLButton[3] = KeyEvent.VK_DIVIDE;
        swapRButton[3] = KeyEvent.VK_MULTIPLY;
        //
        //
        //Player 5
        upButton[4] = KeyEvent.VK_I;
        downButton[4] = KeyEvent.VK_K;
        leftButton[4] = KeyEvent.VK_J;
        rightButton[4] = KeyEvent.VK_L;

        bombButton[4] = KeyEvent.VK_SEMICOLON;
        punchButton[4] = KeyEvent.VK_QUOTE;
        remoteButton[4] = KeyEvent.VK_O;
        specialButton[4] = KeyEvent.VK_U;

        swapLButton[4] = KeyEvent.VK_P;
        swapRButton[4] = KeyEvent.VK_OPEN_BRACKET;

        //Player 6
        upButton[5] = KeyEvent.VK_UP;
        downButton[5] = KeyEvent.VK_DOWN;
        leftButton[5] = KeyEvent.VK_LEFT;
        rightButton[5] = KeyEvent.VK_RIGHT;

        bombButton[5] = KeyEvent.VK_NUMPAD0;
        punchButton[5] = KeyEvent.VK_DECIMAL;
        remoteButton[5] = KeyEvent.VK_NUMPAD1;
        specialButton[5] = KeyEvent.VK_PAGE_DOWN;

        swapLButton[5] = KeyEvent.VK_NUMPAD2;
        swapRButton[5] = KeyEvent.VK_NUMPAD3;

        //Player 7
        upButton[6] = KeyEvent.VK_NUMPAD8;
        downButton[6] = KeyEvent.VK_NUMPAD5;
        leftButton[6] = KeyEvent.VK_NUMPAD4;
        rightButton[6] = KeyEvent.VK_NUMPAD6;

        bombButton[6] = KeyEvent.VK_ADD;
        punchButton[6] = KeyEvent.VK_SUBTRACT;
        remoteButton[6] = KeyEvent.VK_NUMPAD9;
        specialButton[6] = KeyEvent.VK_NUMPAD7;

        swapLButton[6] = KeyEvent.VK_DIVIDE;
        swapRButton[6] = KeyEvent.VK_MULTIPLY;

        //Player 8
        upButton[7] = KeyEvent.VK_W;
        downButton[7]  = KeyEvent.VK_S;
        leftButton[7]  = KeyEvent.VK_A;
        rightButton[7]  = KeyEvent.VK_D;

        bombButton[7] = KeyEvent.VK_F;
        punchButton[7] = KeyEvent.VK_G;
        remoteButton[7] = KeyEvent.VK_E;
        specialButton[7] = KeyEvent.VK_Q;

        swapLButton[7] = KeyEvent.VK_R;
        swapRButton[7] = KeyEvent.VK_T;
    }

    public void update()//Yeah, I finally had the time to "de-noobify" this code
    {
        ControllerState[] playerControl = new ControllerState[8];
        for(int i = 0; i < playerControl.length; i++)
        {
            playerControl[i] = controllers.getState(controllerNums[i]);
            if(playerControl[i].isConnected)//If the Player's controller is plugged in...
            {//Assign controller inputs
                ControllerState controller = playerControl[i];
                
                up[i] = controller.leftStickY > 0.5 | controller.dpadUp;//Up D-Pad
                down[i]  = controller.leftStickY < -0.5 | controller.dpadDown;//Down D-Pad
                left[i]  = controller.leftStickX < -0.5 | controller.dpadLeft;//Left D-Pad
                right[i]  = controller.leftStickX > 0.5 | controller.dpadRight;//Right D-Pad

                bomb[i] = controller.a;//A Button
                punch[i] = controller.x;//X Button
                remote[i] = controller.y;//Y Button
                special[i] = controller.b;//B Button

                swapL[i] = controller.lb;//Left Bumper
                swapR[i] = controller.rb;//Right bumper
            }
            else//Otherwise, assign Keyborad inputs
            {
                up[i] = keys[upButton[i]];
                down[i]  = keys[downButton[i]];
                left[i]  = keys[leftButton[i]];
                right[i]  = keys[rightButton[i]];

                bomb[i] = keys[bombButton[i]];
                punch[i] = keys[punchButton[i]];
                remote[i] = keys[remoteButton[i]];
                special[i] = keys[specialButton[i]];

                swapL[i] = keys[swapLButton[i]];
                swapR[i] = keys[swapRButton[i]];
            }
        }

        //Misc.
        if(playerControl[0].isConnected){pause = playerControl[0].start;}
        else{pause = keys[pauseButton];}

        // for(int i = 0; i < keys.length; i++)
        // {
        // if(keys[i])
        // {
        // System.out.println("KEY: " + i);
        // }
        // }
    }

    public static boolean assignKey(int playerNum, int action)
    {
        for(int i = 0; i < keys.length; i++)
        {
            if(keys[i])
            {
                if(action < playerKeys.length)
                {
                    for(int p0 = 0; p0 < playerKeys.length; p0++)
                    {
                        for(int p1 = 0; p1 < playerKeys[p0].length; p1++)
                        {
                            if(playerKeys[p0][p1] == i && (p0 != action || p1 != playerNum))
                            {
                                playerKeys[p0][p1] = playerKeys[action][playerNum];
                            }
                            else if(i == pauseButton)
                            {
                                pauseButton = playerKeys[action][playerNum];
                            }
                        }
                    }
                }
                else
                {
                    pauseButton = i;
                    return true;
                }
                //
                if(action < playerKeys.length)
                {
                    playerKeys[action][playerNum] = i;
                    return true;
                }
            }
        }    
        return false;
    }

    public static void assignController(byte controllerNum, byte newControllerNum)
    {        
        //WIP
    }

    public void keyPressed(KeyEvent e) 
    {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) 
    {
        keys[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e){}
}
