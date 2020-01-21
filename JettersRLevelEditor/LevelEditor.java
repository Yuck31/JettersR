package JettersRLevelEditor;
/**
 * The Level Editor is really just a GameState designed to create and edit bitmaps.
 * ...Supposed to be anyway.
 *
 * @author: Luke Sullivan
 * @12/30/19
 */
import JettersR.*;
import JettersR.UI.*;
import JettersR.Util.*;
import JettersR.Tiles.*;
import JettersR.GameStates.*;
import JettersR.Audio.*;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.WritableRaster;

public class LevelEditor extends GameState
{
    public String[] floors = new String[4];
    public String[] walls = new String[4];
    public boolean isBattleMap = false;
    public String levelName;
    public Level level = null;
    private Keyboard key;
    private Mouse mouse;

    private UIManager ui;
    private UIPanel panel = new UIPanel(new Vector2i(0,0), null, false);
    private UILabel playTest, tile0, tile1, swapTiles, tileKeyBar;

    private byte keyTime = 0;
    private int x = 0, y = 0, z = 0, r = 0;
    private int[] currentColor =
        {
            0xFF61993C,
            0x00000000,
        };

    private int[][] tileKey =
        {
            {0xFF000000, 0xFFFFFFFF},
            loadColors("/Levels/BattleMaps/GenericTiles/GenericTiles_TileKey.png"),
            loadColors("/Levels/BattleMaps/BombFactory4P/BombFactory_TileKey.png"),
            loadColors("/Levels/BattleMaps/GreatWall/GreatWall_TileKey.png"),
            loadColors("/Levels/BattleMaps/PowerZone4P/PowerZone_TileKey.png"),
            loadColors("/Levels/BattleMaps/RockGarden/RockGarden_TileKey.png"),
        };

    public LevelEditor(UIManager ui, Keyboard key, Mouse mouse)
    {
        this.key = key;
        this.mouse = mouse;
        this.ui = ui;
        //
        this.ui.addPanel(panel);
        //
        playTest = new UILabel(new Vector2d(3,35), LE_Sprite.testClip0, 64, 32, panel);
        tile0 = new UILabel(new Vector2i(3,383), LE_Sprite.heldTile0, panel);
        tile1 = new UILabel(new Vector2i(3,383), Sprite.none_Icon, panel);
        swapTiles = new UILabel(new Vector2d(3,435), LE_Sprite.swapTiles0, 28, 15, panel);
        tileKeyBar = new UILabel(new Vector2d(880, 0), LE_Sprite.tileKeyBar, 16, 454, panel);
        //
        // floors[0] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor0.png";
        // floors[1] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor1.png";
        // floors[2] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor2.png";
        // floors[3] = "/Levels/StoryMissions/TestLevel/Floors/TestLevel_Floor3.png";
        // walls[0] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls0.png";
        // walls[1] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls1.png";
        // walls[2] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls2.png";
        // walls[3] = "/Levels/StoryMissions/TestLevel/Walls/TestLevel_Walls3.png";
        // level = new Level(floors, walls, 4);
        level = new Level("/Levels/StoryMissions/PreTestLevel/Floors/PreTestLevel_Floor0.png",
            "/Levels/StoryMissions/PreTestLevel/Walls/PreTestLevel_Walls0.png");
        //
        currentColor[0] = tileKey[2][4];
        init();
    }

    public void init()
    {
        Level.showInvis = true;
        Game.am.setOGG(AudioManager.music_mujoesTheme);
        Game.am.playOGG();
    }

    public int[] loadColors(String path)
    {
        int[] pixels = new int[1];
        try
        {
            //Meant to get a file path to the image
            BufferedImage image = ImageIO.read(Sprite.class.getResource(path));
            int width = image.getWidth();
            int height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0,0,width,height,pixels,0,width);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return pixels;
    }

    public void save()
    {
        String battleOrStory = "StoryMissions/";
        if(isBattleMap){battleOrStory = "BattleMaps/";}
        for(int i = 0; i < level.floorTiles.length; i++)
        {
            BufferedImage image = new BufferedImage(level.width, level.height, BufferedImage.TYPE_INT_ARGB);
            WritableRaster raster = (WritableRaster) image.getData();
            raster.setPixels(0,0,level.width,level.height,level.floorTiles[i]);
            String path = "/Levels/" + battleOrStory + levelName + "/Floors/" + levelName + "_Floor" + i;
            File outputfile = new File(path);
            try
            {
                ImageIO.write(image, "png", outputfile);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        for(int i = 0; i < level.wallTiles.length; i++)
        {
            BufferedImage image = new BufferedImage(level.width, level.height, BufferedImage.TYPE_INT_ARGB);
            WritableRaster raster = (WritableRaster) image.getData();
            raster.setPixels(0,0,level.width,level.height,level.wallTiles[i]);
            String path = "/Levels/" + battleOrStory + levelName + "/Walls/" + levelName + "_Walls" + i;
            File outputfile = new File(path);
            try
            {
                ImageIO.write(image, "png", outputfile);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void swapColors()
    {
        int color = currentColor[0];
        currentColor[0] = currentColor[1];
        currentColor[1] = color;
    }

    public void update()
    {
        if(keyTime > 0){keyTime--;}
        //System.out.println(playTest.intersects(mouse));
        //
        int xa = 16;
        int ya = 16;
        if(key.bomb[0]){xa = 32; ya = 32;}
        //
        if(key.up[0]){y -= ya;}
        if(key.down[0]){y += ya;}
        if(key.left[0]){x -= xa;}
        if(key.right[0]){x += xa;}
        //
        //if(mouse.getWheelRot() > 0){r++;}
        //else if(mouse.getWheelRot() < 0){r--;}

        if(Game.mouse.getY() < Game.getGameHeight() - 50 && !panel.intersects(mouse))
        {
            if(mouse.getButton() == mouse.lClick || mouse.getButton() == mouse.rClick)
            {
                int button = 0;
                if(mouse.getButton() == mouse.rClick){button = 1;}
                level.setTile((int)((Game.mouse.getX()+x)/32), (int)((Game.mouse.getY()+y)/32), z, currentColor[button]);
            }
            if(mouse.buttonPressed(mouse.mClick))
            {
                if(level.wallTiles[z][(int)((Game.mouse.getX()+x)/32) + ((int)((Game.mouse.getY()+y)/32) * level.width)] != 0x00000000)
                {
                    currentColor[0] = level.wallTiles[z][(int)((Game.mouse.getX()+x)/32) + ((int)((Game.mouse.getY()+y)/32) * level.width)];
                }
                else
                {
                    currentColor[0] = level.floorTiles[z][(int)((Game.mouse.getX()+x)/32) + ((int)((Game.mouse.getY()+y)/32) * level.width)];
                }
            }
        }
        //
        if(keyTime == 0)
        {
            if(key.swapL[0] && z > 0)
            {
                z--;
                keyTime = 2;
            }
            if(key.swapR[0] && z < level.floorTiles.length-1)
            {
                z++;
                keyTime = 2;
            }
        }
        else if(key.swapL[0] || key.swapR[0])
        {
            keyTime = 2;
        }
        //
        if(swapTiles.intersects(mouse) && mouse.buttonPressed(mouse.lClick))
        {
            swapColors();
        }
        if(tileKeyBar.intersects(mouse) && mouse.buttonPressed(mouse.lClick))
        {
            tileKeyBar.setOffset(new Vector2d(-192, 0));
        }
    }

    public void render(Screen screen)
    {
        if(level != null)
        {
            level.render((int)(x), (int)(y-(z*16)), z*16, screen);
        }
        //
        if(Game.mouse.getY() < screen.height - 50 && mouse.getButton() == -1 && !panel.intersects(mouse))
        {
            screen.fillRect(
                (int)((Game.mouse.getX()+x)/32)*32,
                ((int)((Game.mouse.getY()+y)/32)*32) - (z*16),
                31, 31, 0xFFF0F000, 50f, true);
        }

        screen.renderSprite(0, 454, LE_Sprite.tileBar0, false);
        screen.renderTranslucentSprite(0, 454, 40f, LE_Sprite.tileBar1, false);
        //
        screen.renderSprite(31, 399, LE_Sprite.heldTile1, false);
        level.getTile(currentColor[1]).render(33, 401, screen);
        ui.render(screen);
        level.getTile(currentColor[0]).render(5, 385, screen);

        LE_Font.render(screen.width-60, 0,
            "X = " + (int)(Game.mouse.getX()+x)
            + "`Y = " + (int)(Game.mouse.getY()+y)
            + "`Z = " + z, screen);

        for(int i = 0; i < tileKey[2].length; i++)
        {
            level.getTile(tileKey[2][i]).render(34*i + 8, 458, screen);
        }
    }
}
