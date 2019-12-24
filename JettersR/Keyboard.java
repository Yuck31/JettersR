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

    public void update()
    {
        ControllerState player1Control = controllers.getState(controllerNums[0]);
        ControllerState player2Control = controllers.getState(controllerNums[1]);
        ControllerState player3Control = controllers.getState(controllerNums[2]);
        ControllerState player4Control = controllers.getState(controllerNums[3]);

        ControllerState player5Control = controllers.getState(controllerNums[4]);
        ControllerState player6Control = controllers.getState(controllerNums[5]);
        ControllerState player7Control = controllers.getState(controllerNums[6]);
        ControllerState player8Control = controllers.getState(controllerNums[7]);
        //Player 1
        if(player1Control.isConnected)//If the Player's controller is plugged in...
        {//Assign controller inputs
            up[0] = player1Control.leftStickY > 0.5 | player1Control.dpadUp;//Up D-Pad
            down[0]  = player1Control.leftStickY < -0.5 | player1Control.dpadDown;//Down D-Pad
            left[0]  = player1Control.leftStickX < -0.5 | player1Control.dpadLeft;//Left D-Pad
            right[0]  = player1Control.leftStickX > 0.5 | player1Control.dpadRight;//Right D-Pad

            bomb[0] = player1Control.a;//A Button
            punch[0] = player1Control.x;//X Button
            remote[0] = player1Control.y;//Y Button
            special[0] = player1Control.b;//B Button

            swapL[0] = player1Control.lb;//Left Bumper
            swapR[0] = player1Control.rb;//Right bumper
        }

        else//Otherwise, assign Keyborad inputs
        {
            up[0] = keys[upButton[0]];
            down[0]  = keys[downButton[0]];
            left[0]  = keys[leftButton[0]];
            right[0]  = keys[rightButton[0]];

            bomb[0] = keys[bombButton[0]];
            punch[0] = keys[punchButton[0]];
            remote[0] = keys[remoteButton[0]];
            special[0] = keys[specialButton[0]];

            swapL[0] = keys[swapLButton[0]];
            swapR[0] = keys[swapRButton[0]];
        }

        //Player 2
        if(player2Control.isConnected)
        {
            up[1] = player2Control.leftStickY > 0.5 | player2Control.dpadUp;//Up D-Pad
            down[1]  = player2Control.leftStickY < -0.5 | player2Control.dpadDown;//Down D-Pad
            left[1]  = player2Control.leftStickX < -0.5 | player2Control.dpadLeft;//Left D-Pad
            right[1]  = player2Control.leftStickX > 0.5 | player2Control.dpadRight;//Right D-Pad

            bomb[1] = player2Control.a;//A Button
            punch[1] = player2Control.x;//X Button
            remote[1] = player2Control.y;//Y Button
            special[1] = player2Control.b;//B Button

            swapL[1] = player2Control.lb;//Left Bumper
            swapR[1] = player2Control.rb;//Right bumper
        }
        else
        {
            up[1] = keys[upButton[1]];
            down[1]  = keys[downButton[1]];
            left[1]  = keys[leftButton[1]];
            right[1]  = keys[rightButton[1]];

            bomb[1] = keys[bombButton[1]];
            punch[1] = keys[punchButton[1]];
            remote[1] = keys[remoteButton[1]];
            special[1] = keys[specialButton[1]];

            swapL[1] = keys[swapLButton[1]];
            swapR[1] = keys[swapRButton[1]];
        }

        //Player 3
        if(player3Control.isConnected)
        {
            up[2] = player3Control.leftStickY > 0.5 | player3Control.dpadUp;//Up D-Pad
            down[2]  = player3Control.leftStickY < -0.5 | player3Control.dpadDown;//Down D-Pad
            left[2]  = player3Control.leftStickX < -0.5 | player3Control.dpadLeft;//Left D-Pad
            right[2]  = player3Control.leftStickX > 0.5 | player3Control.dpadRight;//Right D-Pad

            bomb[2] = player3Control.a;//A Button
            punch[2] = player3Control.x;//X Button
            remote[2] = player3Control.y;//Y Button
            special[2] = player3Control.b;//B Button

            swapL[2] = player3Control.lb;//Left Bumper
            swapR[2] = player3Control.rb;//Right bumper
        }
        else
        {
            up[2] = keys[upButton[2]];
            down[2]  = keys[downButton[2]];
            left[2]  = keys[leftButton[2]];
            right[2]  = keys[rightButton[2]];

            bomb[2] = keys[bombButton[2]];
            punch[2] = keys[punchButton[2]];
            remote[2] = keys[remoteButton[2]];
            special[2] = keys[specialButton[2]];

            swapL[2] = keys[swapLButton[2]];
            swapR[2] = keys[swapRButton[2]];
        }

        //Player 4
        if(player4Control.isConnected)
        {
            up[3] = player4Control.leftStickY > 0.5 | player4Control.dpadUp;//Up D-Pad
            down[3]  = player4Control.leftStickY < -0.5 | player4Control.dpadDown;//Down D-Pad
            left[3]  = player4Control.leftStickX < -0.5 | player4Control.dpadLeft;//Left D-Pad
            right[3]  = player4Control.leftStickX > 0.5 | player4Control.dpadRight;//Right D-Pad

            bomb[3] = player4Control.a;//A Button
            punch[3] = player4Control.x;//X Button
            remote[3] = player4Control.y;//Y Button
            special[3] = player4Control.b;//B Button

            swapL[3] = player4Control.lb;//Left Bumper
            swapR[3] = player4Control.rb;//Right bumper
        }
        else
        {
            up[3] = keys[upButton[3]];
            down[3]  = keys[downButton[3]];
            left[3]  = keys[leftButton[3]];
            right[3]  = keys[rightButton[3]];

            bomb[3] = keys[bombButton[3]];
            punch[3] = keys[punchButton[3]];
            remote[3] = keys[remoteButton[3]];
            special[3] = keys[specialButton[3]];

            swapL[3] = keys[swapLButton[3]];
            swapR[3] = keys[swapRButton[3]];
        }

        //Player 5
        if(player5Control.isConnected)
        {
            up[4] = player5Control.leftStickY > 0.5 | player5Control.dpadUp;//Up D-Pad
            down[4]  = player5Control.leftStickY < -0.5 | player5Control.dpadDown;//Down D-Pad
            left[4]  = player5Control.leftStickX < -0.5 | player5Control.dpadLeft;//Left D-Pad
            right[4]  = player5Control.leftStickX > 0.5 | player5Control.dpadRight;//Right D-Pad

            bomb[4] = player5Control.a;//A Button
            punch[4] = player5Control.x;//X Button
            remote[4] = player5Control.y;//Y Button
            special[4] = player5Control.b;//B Button

            swapL[4] = player5Control.lb;//Left Bumper
            swapR[4] = player5Control.rb;//Right bumper
        }
        else
        {
            up[4] = keys[upButton[4]];
            down[4]  = keys[downButton[4]];
            left[4]  = keys[leftButton[4]];
            right[4]  = keys[rightButton[4]];

            bomb[4] = keys[bombButton[4]];
            punch[4] = keys[punchButton[4]];
            remote[4] = keys[remoteButton[4]];
            special[4] = keys[specialButton[4]];

            swapL[4] = keys[swapLButton[4]];
            swapR[4] = keys[swapRButton[4]];
        }

        //Player 6
        if(player6Control.isConnected)
        {
            up[5] = player6Control.leftStickY > 0.5 | player6Control.dpadUp;//Up D-Pad
            down[5]  = player6Control.leftStickY < -0.5 | player6Control.dpadDown;//Down D-Pad
            left[5]  = player6Control.leftStickX < -0.5 | player6Control.dpadLeft;//Left D-Pad
            right[5]  = player6Control.leftStickX > 0.5 | player6Control.dpadRight;//Right D-Pad

            bomb[5] = player6Control.a;//A Button
            punch[5] = player6Control.x;//X Button
            remote[5] = player6Control.y;//Y Button
            special[5] = player6Control.b;//B Button

            swapL[5] = player6Control.lb;//Left Bumper
            swapR[5] = player6Control.rb;//Right bumper
        }
        else
        {
            up[5] = keys[upButton[5]];
            down[5]  = keys[downButton[5]];
            left[5]  = keys[leftButton[5]];
            right[5]  = keys[rightButton[5]];

            bomb[5] = keys[bombButton[5]];
            punch[5] = keys[punchButton[5]];
            remote[5] = keys[remoteButton[5]];
            special[5] = keys[specialButton[5]];

            swapL[5] = keys[swapLButton[5]];
            swapR[5] = keys[swapRButton[5]];
        }

        //Player 7
        if(player7Control.isConnected)
        {
            up[6] = player7Control.leftStickY > 0.5 | player7Control.dpadUp;//Up D-Pad
            down[6]  = player7Control.leftStickY < -0.5 | player7Control.dpadDown;//Down D-Pad
            left[6]  = player7Control.leftStickX < -0.5 | player7Control.dpadLeft;//Left D-Pad
            right[6]  = player7Control.leftStickX > 0.5 | player7Control.dpadRight;//Right D-Pad

            bomb[6] = player7Control.a;//A Button
            punch[6] = player7Control.x;//X Button
            remote[6] = player7Control.y;//Y Button
            special[6] = player7Control.b;//B Button

            swapL[6] = player7Control.lb;//Left Bumper
            swapR[6] = player7Control.rb;//Right Bumper
        }
        else
        {
            up[6] = keys[upButton[6]];
            down[6]  = keys[downButton[6]];
            left[6]  = keys[leftButton[6]];
            right[6]  = keys[rightButton[6]];

            bomb[6] = keys[bombButton[6]];
            punch[6] = keys[punchButton[6]];
            remote[6] = keys[remoteButton[6]];
            special[6] = keys[specialButton[6]];

            swapL[6] = keys[swapLButton[6]];
            swapR[6] = keys[swapRButton[6]];
        }

        //Player 8
        if(player8Control.isConnected)
        {
            up[7] = player8Control.leftStickY > 0.5 | player8Control.dpadUp;//Up D-Pad
            down[7]  = player8Control.leftStickY < -0.5 | player8Control.dpadDown;//Down D-Pad
            left[7]  = player8Control.leftStickX < -0.5 | player8Control.dpadLeft;//Left D-Pad
            right[7]  = player8Control.leftStickX > 0.5 | player8Control.dpadRight;//Right D-Pad

            bomb[7] = player8Control.a;//A Button
            punch[7] = player8Control.x;//X Button
            remote[7] = player8Control.y;//Y Button
            special[7] = player8Control.b;//B Button

            swapL[7] = player8Control.lb;//Left Bumper
            swapR[7] = player8Control.rb;//Right bumper
        }
        else
        {
            up[7] = keys[upButton[7]];
            down[7]  = keys[downButton[7]];
            left[7]  = keys[leftButton[7]];
            right[7]  = keys[rightButton[7]];

            bomb[7] = keys[bombButton[7]];
            punch[7] = keys[punchButton[7]];
            remote[7] = keys[remoteButton[7]];
            special[7] = keys[specialButton[7]];

            swapL[7] = keys[swapLButton[7]];
            swapR[7] = keys[swapRButton[7]];
        }

        //Misc.
        if(player1Control.isConnected){pause = player1Control.start;}
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
