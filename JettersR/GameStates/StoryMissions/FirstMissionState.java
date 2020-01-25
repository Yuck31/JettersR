package JettersR.GameStates.StoryMissions;

import JettersR.*;
import JettersR.UI.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;
import JettersR.GameStates.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import java.util.Random;
import java.util.HashMap;
public class FirstMissionState extends GameState
{
    public Level level;
    private Random random = new Random();
    private float scale = 1f;

    private String[] musicPaths = new String[2];
    public String[] floors = new String[4];
    public String[] walls = new String[4];

    public int timeMinute = 0;//Minute + ":" + Second
    public int timeSecond = 0;
    public int timeFrames = 60;//Used for counting down seconds

    public int endTime = 180;
    public boolean endFlag = false;

    public int width;
    public int height;
    public int[] entitySpawns;

    public int maxSDX = 723;
    public int maxSDY = 429;
    public int minSDX = 179;
    public int minSDY = 45;

    public int SDX = minSDX;
    public int SDY = minSDY;

    public boolean loopSet = false;
    public int minX = 0;
    public int maxX = 320;
    public int minY = 0;
    public int maxY = 320;

    private Keyboard key;
    private Player player;
    private Player player2;
    private Player player3;
    private Player player4;
    public Player[] players = new Player[4];
    public byte[] playerWins;

    public FirstMissionState(GameStateManager gsm, Keyboard key)
    {
        //musicPaths[0] = "/Music/BomberBATTLE__2.75_95.wav";
        //musicPaths[1] = "/Music/SDBomberBATTLE.wav";
        this.gsm = gsm;
        this.key = key;
        minX = (minX * 32) + 4;
        maxX = (maxX * 32) + 4;
        minY = (minY * 32) - 3;
        maxY = (maxY * 32);
        init();
    }

    public void init()
    {
        floors[0] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor0.png";
        floors[1] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor1.png";
        floors[2] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor2.png";
        floors[3] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor3.png";
        walls[0] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls0.png";
        walls[1] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls1.png";
        walls[2] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls2.png";
        walls[3] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls3.png";
        level = new Level(floors, walls, 4);
        //level.generateEntities("/Levels/StoryMissions/FirstMission/FirstMissionEntities.png");
        level.setLoopBounds(minX, maxX, minY, maxY);

        //generateBlocks();

        uiManager = Game.getUIManager();
        TileCoordinate playerSpawn = new TileCoordinate(125,216,-5,-12);
        player = new Player(playerSpawn.x(), playerSpawn.y(), 1, key, (byte)0, uiManager);
        TileCoordinate playerSpawn2 = new TileCoordinate(125,217,-5,-12);
        player.init(level);
        //player2.init(level);
        //player3.init(level);
        //player4.init(level);

        level.add(player);
        // level.add(player2);
        // level.add(player3);
        // level.add(player4);

        players[0] = player;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;

        player.bombKick = true;
        player.bombThrow = 1;
        player.collectedSpecialType[player.type_bomberShoot] = true;
        player.equipedSpecial = player.type_bomberShoot;
        //if(gsm.audioPlayer == null){gsm.audioPlayer = new AudioPlayer(musicPaths);}
        //else{gsm.audioPlayer.reassignSounds(musicPaths);}
        //gsm.audioPlayer.play(0);
    }

    public void update()
    {
        if(!gsm.paused)
        {
            level.update();//Updates level(which renders entities and Players too)
            uiManager.update();//Updates the UI

            if(!endFlag)
            {
                if(timeFrames >= 0){timeFrames--;}
                if(timeFrames < 0){timeSecond++; timeFrames = 60;}
                if(timeSecond > 59){timeSecond = 0; timeMinute++;}
            }

            if(level.getPlayersSize() <= 0)
            {
                endFlag = true;
                endTime--;
                if(endTime >= 177)
                {
                    for(int i = 0; i < level.players.size(); i++)
                    {
                        level.players.get(i).ImMobilize(2);//Immobilize players and make them face down
                    }
                }

                if(endTime <= 0)
                {
                    endGame();
                }
            }
        }
    }

    public void quitGame()
    {
        Game.resetUI();
        Game.screen.resetOffsets();
        gsm.paused = false;
        System.gc();
        gsm.setState(GameStateManager.MENUSTATE);
    }

    public void endGame()
    {
        Game.resetUI();
        Game.screen.resetOffsets();
        System.gc();
        gsm.setState(GameStateManager.MENUSTATE);
    }

    public void render(Screen screen)
    {
        double xScroll = (player.x*scale) - screen.width / 2;
        double yScroll = ((player.y-(player.getZ()*16)-player.getZOffset())*scale) - screen.height / 2;

        level.render((int)(xScroll),(int)(yScroll), (player.getZ() * 16), screen);
        //level.renderScale((int)(xScroll),(int)(yScroll), player.getZ(), scale, screen);

        if(gsm.renderUI)
        {
            uiManager.render(screen);
            if(timeSecond < 10)
            {
                Font.render(416, 482, ("A " + Integer.toString(timeMinute) + ":" + "0" + Integer.toString(timeSecond)), Font.numbersUI, screen);
            }
            else
            {
                Font.render(416, 482, ("A " + Integer.toString(timeMinute) + ":" + Integer.toString(timeSecond)), Font.numbersUI, screen);
            }
        }
    }
}