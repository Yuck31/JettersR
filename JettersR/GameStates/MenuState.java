package JettersR.GameStates;
/**
 * This is the GameState that manages the main menu.
 * It allows you to select maps, customize the colors of the game's Bombers, and even reorder controllers to different players.
 *
 * author: Luke Sullivan
 * Last Edit: 1/19/20
 */
import JettersR.*;
import JettersR.Audio.*;

public class MenuState extends GameState
{
    private Keyboard key;
    private BackGround bg;
    private ItemTipsBoard itemTips;

    //Menu Numbers
    private int menuNum = 0;
    private final int mainMenu = 0,
    battleMapsMenu = 1,
    testMapsMenu = 2,
    customPlayerMenu = 3,
    itemTipsBoard = 4,
    battleSettingsMenu = 5;

    //Item Tips Pages
    private int pageNum = 0;

    public int mos = 1;
    public boolean selected = true;

    private int mainChoice = 0;
    private String[] options = 
        {
            "BATTLE MAPS",
            "TEST MAPS",
            "BATTLE SETTINGS",
            "CUSTOM PLAYER",
            "ITEM HELP",
            "FPS",
            "QUIT"
        };

    private int battleChoice = 0;
    private String[] battleMaps = 
        {
            "BOMB FACTORY",
            "GREAT WALL",
            "POWER ZONE",
        };

    private int testChoice = 0;
    private String[] testMaps = 
        {
            "ROCK GARDEN",
            "PVE TEST",
            "MODE SEVEN TEST",
            "WIDE MAP TEST"
        };

    private int whichPlayer = 0;
    private boolean playerSelected = false;
    private String[] playerNumbers = 
        {
            "ONE",
            "TWO",
            "THREE",
            "FOUR",
            "FIVE",
            "SIX",
            "SEVEN",
            "EIGHT",
        };

    private int customChoice = 0;
    private int customMenu = 0;//Player Choose, RGB, Controls
    private final int faceMenu = 1,
    torsoMenu = 2,
    suitMenu = 3,
    handsMenu = 4,
    otherMenu = 5,
    controlsMenu = 6;
    private String[] customOptions = 
        {
            "FACE",
            "TORSO",
            "SUIT",
            "HANDS",
            "OTHER",
            "RESET TO DEFAULT",
            "CONTROLLER SETTINGS",
        };
    private int rgbChoice = 0;
    private String[] rgbOptions = 
        {
            "R",
            "G",
            "B"
        };
    private boolean rgbSelected = false;
    private int red = 0,
    green = 0,
    blue = 0;

    public boolean keyAssign = false;
    public byte assignSecond = 5;
    public byte assignFrames = 60;
    public boolean controllerAssign = false;
    public int controlChoice = 0;
    private String[] controlOptions =
        {
            "UP: ",
            "DOWN: ",
            "LEFT: ",
            "RIGHT: ",
            "BOMB: ",
            "PUNCH: ",
            "REMOTE: ",
            "SPECIAL: ",
            "SWAP-L: ",
            "SWAP-R: ",
            "PAUSE: ",
            "CONTROLLER NUMBER: ",
        };

    private int keyTime = 2;

    public int curBattleSetting = 0;
    public BattleOption[] battleSettings;

    public MenuState(GameStateManager gsm, Keyboard key)
    {
        itemTips = new ItemTipsBoard(key);
        this.gsm = gsm;
        this.key = key;
        bg = new BackGround(Sprite.backGround, 1);
        bg.setVector(-3, 0);

        BattleOption[] battleSettings0 = 
            {
                new BattleOption(0, 44, BattleOption.Type.PLAYERAMOUNT, this.gsm),
                new BattleOption(0, 68, BattleOption.Type.SETSTOWIN, this.gsm),
                new BattleOption(0, 92, BattleOption.Type.TIMELIMIT, this.gsm),
                new BattleOption(0, 116, BattleOption.Type.PRESSUREBLOCKS, this.gsm),
                new BattleOption(0, 140, BattleOption.Type.REVENGECARTS, this.gsm),
                new BattleOption(0, 164, BattleOption.Type.PLAYERSPAWNS, this.gsm),
                new BattleOption(0, 188, BattleOption.Type.SKULLAMOUNT, this.gsm),
            };

        battleSettings = battleSettings0;
        battleSettings[curBattleSetting].selected = true;
        init();
    }

    public void init()
    {
        Game.am.setOGG(AudioManager.oggPlayer(AudioManager.music_mujoesTheme));
        Game.am.playOGG();
    }

    public void update()
    {
        bg.update();
        //System.out.println(keyAssign);
        if(keyTime == 0 && keyAssign)
        {
            if(!key.assignKey(whichPlayer, controlChoice) && (assignSecond > 0 || assignFrames > 1))
            {
                assignFrames--;
                if(assignFrames <= 0){assignFrames = 60; assignSecond--;}
                return;
            }
            else if(key.assignKey(whichPlayer, controlChoice) && (assignSecond < 5 || assignFrames <= 59))
            {
                keyAssign = false;
                keyTime = 2;
                assignFrames = 60;
                assignSecond = 5;
            }
            else if((assignSecond <= 0 && assignFrames <= 1))
            {
                keyAssign = false;
                keyTime = 2;
                assignFrames = 60;
                assignSecond = 5;
            }
            else{return;}
        }
        if(keyTime > 0 && (!key.up[0] && !key.down[0] && !key.left[0] && !key.right[0]&& !key.pause)){keyTime--;}
        if(key.swapL[0]){mos--;}
        if(key.swapR[0]){mos++;}

        for(int i = 0; i < battleSettings.length; i++)
        {
            battleSettings[i].update();
        }

        if((key.up[0] || key.down[0] || key.bomb[0]) && keyTime > 0)
        {
            keyTime = 2;
        }
        else if((key.up[0] || key.down[0] || key.left[0] || key.right[0]) && keyTime <= 0)
        {
            if(
            ((menuNum == mainMenu || menuNum == battleMapsMenu || menuNum == testMapsMenu) && !(key.left[0] || key.right[0]))
            || (menuNum == itemTipsBoard && !(key.up[0] || key.down[0]))
            || (customMenu > 0 && !(key.left[0] || key.right[0]))
            ||(menuNum == battleSettingsMenu || menuNum == customPlayerMenu && customMenu == 0)
            )
            {Game.am.add(AudioManager.select0);}
        }

        switch(menuNum)
        {
            case mainMenu:
            if(key.up[0] && keyTime == 0)
            {
                mainChoice--;
                keyTime = 2;
                if(mainChoice <= -1)
                {
                    mainChoice = options.length - 1;
                }
            }
            if(key.down[0] && keyTime == 0)
            {
                mainChoice++;
                keyTime = 2;
                if(mainChoice >= options.length)
                {
                    mainChoice = 0;
                }
            }

            if(key.pause && keyTime == 0)
            {
                select();
                keyTime = 2;
            }
            break;

            case battleMapsMenu:
            if(key.up[0] && keyTime == 0)
            {
                battleChoice--;
                keyTime = 2;
                if(battleChoice <= -1)
                {
                    battleChoice = battleMaps.length - 1;
                }
            }
            if(key.down[0] && keyTime == 0)
            {
                battleChoice++;
                keyTime = 2;
                if(battleChoice >= battleMaps.length)
                {
                    battleChoice = 0;
                }
            }

            if(key.pause && keyTime == 0)
            {
                select();
                keyTime = 2;
            }
            break;

            case testMapsMenu:
            if(key.up[0] && keyTime == 0)
            {
                testChoice--;
                keyTime = 2;
                if(testChoice <= -1)
                {
                    testChoice = testMaps.length - 1;
                }
            }
            if(key.down[0] && keyTime == 0)
            {
                testChoice++;
                keyTime = 2;
                if(testChoice >= testMaps.length)
                {
                    testChoice = 0;
                }
            }

            if(key.pause && keyTime == 0)
            {
                select();
                keyTime = 2;
            }
            break;
            //
            //
            case customPlayerMenu:
            if(customMenu == 0)
            {
                if(!playerSelected)
                {
                    if(key.left[0] && keyTime == 0)
                    {
                        whichPlayer--;
                        keyTime = 2;
                        if(whichPlayer < 0){whichPlayer = playerNumbers.length-1;}
                    }
                    if(key.right[0] && keyTime == 0)
                    {
                        whichPlayer++;
                        keyTime = 2;
                        if(whichPlayer >= playerNumbers.length){whichPlayer = 0;}
                    }
                    if(key.up[0] && keyTime == 0)
                    {
                        whichPlayer -= 4;
                        keyTime = 2;
                        if(whichPlayer < 0){whichPlayer = playerNumbers.length + whichPlayer;}
                    }
                    if(key.down[0] && keyTime == 0)
                    {
                        whichPlayer += 4;
                        keyTime = 2;
                        if(whichPlayer >= playerNumbers.length){whichPlayer = whichPlayer - playerNumbers.length;}
                    }

                    if(key.pause && keyTime == 0)
                    {
                        select();
                        keyTime = 2;
                    }
                }
                else if(playerSelected)
                {
                    if(key.up[0] && keyTime == 0)
                    {
                        customChoice--;
                        keyTime = 2;
                        if(customChoice < 0){customChoice = customOptions.length-1;}
                    }
                    if(key.down[0] && keyTime == 0)
                    {
                        customChoice++;
                        keyTime = 2;
                        if(customChoice >= customOptions.length){customChoice = 0;}
                    }
                    if(key.pause && keyTime == 0)
                    {
                        select();
                        keyTime = 2;
                    }
                }
            }
            else if(customMenu > 0 && customMenu < controlsMenu)
            {
                if(!rgbSelected)
                {
                    if(key.up[0] && keyTime == 0)
                    {
                        rgbChoice--;
                        keyTime = 2;
                        if(rgbChoice < 0){rgbChoice = rgbOptions.length-1;}
                    }
                    if(key.down[0] && keyTime == 0)
                    {
                        rgbChoice++;
                        keyTime = 2;
                        if(rgbChoice >= rgbOptions.length){rgbChoice = 0;}
                    }
                    if(key.pause && keyTime == 0)
                    {
                        select();
                        keyTime = 2;
                    }
                }

                else if(rgbSelected)
                {
                    switch(rgbChoice)
                    {
                        case 0://R
                        if(key.left[0])
                        {
                            if((Game.playerColors[whichPlayer][customMenu-1] & 0x00FF0000) - 0x00010000 >= 0x00000000)
                            {
                                Game.playerColors[whichPlayer][customMenu-1] -= 0x00010000;
                            }
                        }
                        if(key.right[0])
                        {
                            if((Game.playerColors[whichPlayer][customMenu-1] & 0x00FF0000) + 0x00010000 <= 0x00FF0000)
                            {
                                Game.playerColors[whichPlayer][customMenu-1] += 0x00010000;
                            }
                        }
                        break;

                        case 1://G
                        if(key.left[0])
                        {
                            if((Game.playerColors[whichPlayer][customMenu-1] & 0x0000FF00) - 0x00000100 >= 0x00000000)
                            {
                                Game.playerColors[whichPlayer][customMenu-1] -= 0x00000100;
                            }
                        }
                        if(key.right[0])
                        {
                            if((Game.playerColors[whichPlayer][customMenu-1] & 0x0000FF00) + 0x00000100 <= 0x0000FF00)
                            {
                                Game.playerColors[whichPlayer][customMenu-1] += 0x00000100;
                            }
                        }
                        break;

                        case 2://B
                        if(key.left[0])
                        {
                            if((Game.playerColors[whichPlayer][customMenu-1] & 0x000000FF) - 0x00000001 >= 0x00000000)
                            {
                                Game.playerColors[whichPlayer][customMenu-1] -= 0x00000001;
                            }
                        }
                        if(key.right[0])
                        {
                            if((Game.playerColors[whichPlayer][customMenu-1] & 0x000000FF) + 0x00000001 <= 0x000000FF)
                            {
                                Game.playerColors[whichPlayer][customMenu-1] += 0x00000001;
                            }
                        }
                        break;
                    }
                }
            }
            else if(customMenu == controlsMenu && !keyAssign && !controllerAssign)
            {
                if(key.up[0] && keyTime == 0)
                {
                    controlChoice--;
                    keyTime = 2;
                    if(controlChoice < 0){controlChoice = controlOptions.length - 1;}
                }
                if(key.down[0] && keyTime == 0)
                {
                    controlChoice++;
                    keyTime = 2;
                    if(controlChoice >= controlOptions.length){controlChoice = 0;}
                }
                if(key.pause && keyTime == 0)
                {
                    keyTime = 2;
                    select();
                }
            }

            if(controllerAssign)
            {
                if(key.left[0] && keyTime == 0)
                {
                    if(key.controllerNums[whichPlayer] > 0){key.controllerNums[whichPlayer]--;}
                    else{key.controllerNums[whichPlayer] = 7;}
                    keyTime = 2;
                }
                if(key.right[0] && keyTime == 0)
                {
                    if(key.controllerNums[whichPlayer] < 7){key.controllerNums[whichPlayer]++;}
                    else{key.controllerNums[whichPlayer] = 0;}
                    keyTime = 2;
                }
            }
            break;
            //
            //
            case itemTipsBoard:
            if((key.left[0] || key.right[0]) && (keyTime <= 0 || keyTime > 0))
            {
                keyTime = 2;
            }
            itemTips.update();
            break;
            //
            //
            case battleSettingsMenu:
            if(key.up[0] && keyTime == 0)
            {
                battleSettings[curBattleSetting].selected = false;
                curBattleSetting--;
                keyTime = 2;
                if(curBattleSetting <= -1)
                {
                    curBattleSetting = battleSettings.length - 1;
                }
                battleSettings[curBattleSetting].selected = true;
            }
            if(key.down[0] && keyTime == 0)
            {
                battleSettings[curBattleSetting].selected = false;
                curBattleSetting++;
                keyTime = 2;
                if(curBattleSetting >= battleSettings.length)
                {
                    curBattleSetting = 0;
                }
                battleSettings[curBattleSetting].selected = true;
            }

            if(key.left[0] && keyTime == 0){battleSettings[curBattleSetting].adjustSetting(false); keyTime = 2;}
            else if(key.left[0] && keyTime != 0){keyTime = 2;}

            if(key.right[0] && keyTime == 0){battleSettings[curBattleSetting].adjustSetting(true); keyTime = 2;}
            else if(key.right[0] && keyTime != 0){keyTime = 2;}
            break;
        }
        if(menuNum != mainMenu && (key.bomb[0] && keyTime == 0))
        {
            cancel();
            keyTime = 2;
        }
        if(menuNum != mainMenu && (key.bomb[0] && keyTime != 0))
        {
            keyTime = 2;
        }
    }

    private void select()
    {
        switch(menuNum)
        {
            case mainMenu:
            Game.am.add(AudioManager.confirm0);
            switch(mainChoice)
            {
                case 0://BATTLE MAPS
                menuNum = battleMapsMenu;
                break;

                case 1://TEST MAPS
                menuNum = testMapsMenu;
                break;

                case 2://BATTLE SETTINGS
                menuNum = battleSettingsMenu;
                break;

                case 3://CUSTOM PLAYER
                customMenu = 0;
                menuNum = customPlayerMenu;
                break;

                case 4://ITEM HELP
                menuNum = itemTipsBoard;
                break;

                case 5://FPS
                if(Game.TARGET_FPS == 60)
                {
                    Game.TARGET_FPS = 30;
                    Game.TARGET_DELTA = 1000/Game.TARGET_FPS;
                }
                else
                {
                    Game.TARGET_FPS = 60;
                    Game.TARGET_DELTA = 1000/Game.TARGET_FPS;
                }
                break;

                case 6://QUIT
                System.exit(0);
                break;
            }
            break;

            case battleMapsMenu:
            Game.am.add(AudioManager.confirm1);
            Game.am.stopOGG();
            switch(battleChoice)
            {
                case 0://BOMB FACTORY
                gsm.setTime(gsm.timeMinute, gsm.timeSecond);
                gsm.setState(GameStateManager.BOMBFACTORYSTATE);
                break;

                case 1://GREAT WALL
                gsm.setTime(gsm.timeMinute, gsm.timeSecond);
                gsm.setState(GameStateManager.GREATWALLSTATE);
                break;

                case 2://POWER ZONE
                gsm.setTime(gsm.timeMinute, gsm.timeSecond);
                gsm.setState(GameStateManager.POWERZONESTATE);
                break;
            }
            break;

            case testMapsMenu:
            Game.am.stopOGG();
            switch(testChoice)
            {
                case 0://ROCK GARDEN
                gsm.setTime(gsm.timeMinute, gsm.timeSecond);
                gsm.setState(GameStateManager.ROCKGARDENSTATE);
                break;

                case 1://PVE TEST
                gsm.setState(GameStateManager.FIRSTMISSIONSTATE);
                break;

                case 2://MODE SEVEN TEST
                gsm.setState(GameStateManager.TESTSTATE);
                break;

                case 3://WIDE MAP TEST
                gsm.setTime(gsm.timeMinute, gsm.timeSecond);
                gsm.setState(GameStateManager.WIDEMAPSTATE);
                break;
            }
            break;

            case customPlayerMenu:
            if(!playerSelected)
            {
                Game.am.add(AudioManager.confirm0);
                playerSelected = true;
            }
            else
            {
                switch(customMenu)
                {
                    case 0://Main
                    Game.am.add(AudioManager.confirm0);
                    switch(customChoice)
                    {
                        case 0://FACE
                        customMenu = faceMenu;
                        break;

                        case 1://TORSO
                        customMenu = torsoMenu;
                        break;

                        case 2://SUIT
                        customMenu = suitMenu;
                        break;

                        case 3://HANDS
                        customMenu = handsMenu;
                        break;

                        case 4://OTHER
                        customMenu = otherMenu;
                        break;

                        case 5://RESET TO DEFAULT
                        Game.playerColors[whichPlayer] = Game.defaultPlayerColors()[whichPlayer];
                        break;

                        case 6://CONTROLLER NUMBER
                        customMenu = controlsMenu;
                        break;
                    }
                    break;

                    case faceMenu:
                    case torsoMenu:
                    case suitMenu:
                    case handsMenu:
                    case otherMenu:
                    switch(rgbChoice)
                    {
                        case 0://R
                        case 1://G
                        case 2://B
                        Game.am.add(AudioManager.confirm0);
                        if(!rgbSelected){rgbSelected = true;}
                        else{rgbSelected = false;}
                        break;
                    }
                    break;

                    case controlsMenu:
                    if(controlChoice < controlOptions.length - 1)
                    {
                        Game.am.add(AudioManager.confirm0);
                        keyAssign = true;
                    }
                    else
                    {
                        Game.am.add(AudioManager.confirm0);
                        controllerAssign = true;
                    }
                    break;
                }
            }
            break;
        }
    }

    public void cancel()
    {
        if(keyAssign){return;}
        Game.am.add(AudioManager.cancel);
        if(rgbSelected){rgbSelected = false;}
        else if(controllerAssign)
        {
            key.assignController((byte)whichPlayer, key.controllerNums[whichPlayer]);
            controllerAssign = false;
        }
        else
        {
            rgbChoice = 0;
            if(customMenu > 0){customMenu = 0;}
            else if(playerSelected){playerSelected = false;}
            else{menuNum = mainMenu;}
        }
    }

    public void render(Screen screen)
    {
        bg.render(screen, false);
        Font.render(10, 10, "W,A,S,D = Change Choice", Font.exoFont, screen);
        Font.render(10, 25, "Enter = Select   F = Cancel", Font.exoFont, screen);

        switch(menuNum)
        {
            case mainMenu:
            Font.render(336,0,"BOMBERMAN", Font.orangeFont, screen);

            for(int i = 0; i < options.length; i++)
            {
                if(i == mainChoice)
                {
                    Font.render(305,(66 * (i + 1)),options[i], Font.orangeFont, screen);
                }
                else
                {
                    Font.render(305,(66 * (i + 1)),options[i], Font.pinkFont, screen);
                }
            }
            Font.render(410,412,Integer.toString(Game.TARGET_FPS), Font.numbersUI, screen);
            screen.renderSprite(35,300, Sprite.controllerControls, false);
            screen.renderSprite(624,300, Sprite.keyBoardControls, false);
            break;

            case battleMapsMenu:
            Font.render(336,0,"PICK A MAP", Font.orangeFont, screen);

            for(int i = 0; i < battleMaps.length; i++)
            {
                if(i == battleChoice)
                {
                    Font.render(305,(66 * (i + 1)),battleMaps[i], Font.orangeFont, screen);
                }
                else
                {
                    Font.render(305,(66 * (i + 1)),battleMaps[i], Font.pinkFont, screen);
                }
            }
            screen.renderSprite(35,300, Sprite.controllerControls, false);
            screen.renderSprite(624,300, Sprite.keyBoardControls, false);
            break;

            case testMapsMenu:
            Font.render(336,0,"PICK A MAP", Font.orangeFont, screen);

            for(int i = 0; i < testMaps.length; i++)
            {
                if(i == testChoice)
                {
                    Font.render(305,(66 * (i + 1)),testMaps[i], Font.orangeFont, screen);
                }
                else
                {
                    Font.render(305,(66 * (i + 1)),testMaps[i], Font.pinkFont, screen);
                }
            }
            break;

            case customPlayerMenu:
            if(!playerSelected)
            {
                Font.render(200,0,"PICK A BOMBER", Font.orangeFont, screen);
                for(int i = 0; i < Game.playerColors.length/2; i++)
                {
                    screen.renderCustomPlayer((150 * (i+1))-16, 200, Sprite.player_front, Game.playerColors[i], false);
                    if(whichPlayer == i){Font.render((150 * (i+1))-48, 250, playerNumbers[i], Font.orangeFont, screen);}
                    else{Font.render((150 * (i+1))-16, 250, playerNumbers[i], Font.greenFont, screen);}
                }
                for(int i = Game.playerColors.length/2; i < Game.playerColors.length; i++)
                {
                    screen.renderCustomPlayer((150 * ((i-4)+1))-16, 300, Sprite.player_front, Game.playerColors[i], false);
                    if(whichPlayer == i){Font.render((150 * ((i-4)+1))-48, 350, playerNumbers[i], Font.orangeFont, screen);}
                    else{Font.render((150 * ((i-4)+1))-16, 350, playerNumbers[i], Font.greenFont, screen);}
                }
            }
            if(playerSelected)
            {
                Font.render(200,0,"CUSTOMIZE BOMBER", Font.orangeFont, screen);
                screen.renderCustomPlayer(550, 86, Sprite.scale(Sprite.player_front, 7.0f), Game.playerColors[whichPlayer], false);
                if(customMenu == 0)
                {
                    for(int i = 0; i < customOptions.length; i++)
                    {
                        if(i == customChoice && !controllerAssign)
                        {
                            Font.render(6,(66 * (i + 1)),customOptions[i], Font.orangeFont, screen);
                        }
                        else
                        {
                            Font.render(6,(66 * (i + 1)),customOptions[i], Font.pinkFont, screen);
                        }
                    }
                }
                else if(customMenu > 0 && customMenu < controlsMenu)
                {
                    for(int i = 0; i < rgbOptions.length; i++)
                    {
                        if(i == rgbChoice && !rgbSelected)
                        {
                            Font.render(6,(130 + (i * 100)),rgbOptions[i], Font.orangeFont, screen);
                        }
                        else
                        {
                            Font.render(6,(130 + (i * 100)),rgbOptions[i], Font.pinkFont, screen);
                        }
                    }
                    screen.RGB(50, 130, Sprite.RGB_Bar, 2, false);
                    screen.renderSprite((((Game.playerColors[whichPlayer][customMenu-1] & 0x00FF0000)>>16)*2)+46, 122, Sprite.RGB_Pointer, false);
                    Font.render(16, 166, Integer.toString((Game.playerColors[whichPlayer][customMenu-1] & 0x00FF0000)>>16), Font.numbersUI, screen);

                    screen.RGB(50, 230, Sprite.RGB_Bar, 1, false);
                    screen.renderSprite((((Game.playerColors[whichPlayer][customMenu-1] & 0x0000FF00)>>8)*2)+46, 222, Sprite.RGB_Pointer, false);
                    Font.render(16, 266, Integer.toString((Game.playerColors[whichPlayer][customMenu-1] & 0x0000FF00)>>8), Font.numbersUI, screen);

                    screen.RGB(50, 330, Sprite.RGB_Bar, 0, false);
                    screen.renderSprite(((Game.playerColors[whichPlayer][customMenu-1] & 0x000000FF)*2)+46, 322, Sprite.RGB_Pointer, false);
                    Font.render(16, 366, Integer.toString(Game.playerColors[whichPlayer][customMenu-1] & 0x000000FF), Font.numbersUI, screen);
                }
                else if(customMenu == controlsMenu)
                {
                    for(int i = 0; i < controlOptions.length; i++)
                    {
                        Font.render(16, 70 + (24*i), controlOptions[i], Font.thickExoFont, screen);
                        if(i == controlChoice){screen.renderSheet(4, 70 + (24*i), SpriteSheet.menuPointer, false);}
                        if(i < Keyboard.playerKeys.length){Font.render(90, 70 + (24*i), Keyboard.playerKeys[i][whichPlayer], false, screen);}
                        else if(i == Keyboard.playerKeys.length){Font.render(90, 70 + (24*i), Keyboard.pauseButton, false, screen);}
                        else{Font.render(200, 70 + (24*i), Integer.toString(Keyboard.controllerNums[(byte)whichPlayer]+1), Font.thickExoFont, screen);}
                    }
                    if(keyAssign)
                    {
                        Font.render(200,200,
                            "Press a Key to change what key`" +//
                            "    performs this action.`" +//
                            "NOTE: Key change happens instantly.`" +//
                            "Any overriding keys will be swapped,`" +//
                            "      even on other players.``" +//
                            "              " + assignSecond,
                            Font.thickExoFont, screen);
                    }
                    else if(controllerAssign)
                    {
                        Font.render(145,200,
                            "Press Left or Right to change the controller order.`" +//
                            "    NOTE: Controller reorder happens instantly.``" +//
                            "              Press Bomb to finish.",
                            Font.thickExoFont, screen);
                    }
                }
            }
            break;

            case itemTipsBoard:
            itemTips.render(screen);
            break;

            case battleSettingsMenu:
            Font.render(200,0,"BATTLE SETTINGS", Font.orangeFont, screen);
            for(int i = 0; i < battleSettings.length; i++)
            {
                battleSettings[i].render(screen);
            }
            break;
        }
        screen.renderMosaic(mos);
    }
}