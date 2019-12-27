package JettersR;

/**
 * This is the Class that starts and loops the game while it is running.
 * It also creates the window frame that the game is played on.
 *
 * author: Luke Sullivan
 * Last Edit: 11/7/19
 */

import JettersR.*;
import JettersR.Audio.*;
import JettersR.GameStates.*;
import JettersR.UI.*;
import JettersR.Entity.Mob.*;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.DataBufferInt;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import com.studiohartman.jamepad.*;//CONTROLLER SUPPORT!

public class Game extends Canvas implements Runnable
{
    private static final long serialVersionUID = 1L;
    public static int TARGET_FPS = 60;
    public static int TARGET_DELTA = 1000/TARGET_FPS;

    public static int width = 896;//Screen width (height changes accordingly)
    public static int height = (width / 16) * 9;//16:9 ratio
    public static float scale = 1f;//Adjusts scaling of on screen sprites(affects what "one pixel" will look like on screen)
    public static int fps = 0;
    public static float brightness = 1.0f;
    public static boolean screenStretch = false;
    public static String TITLE = "Bomberman(2019)";

    private static Thread thread;//For Single-Thread
    public static UpdateThread updateThread;//For Multi-Thread
    public static RenderThread renderThread;//For Multi-Thread
    public static boolean updating = false;
    public static boolean rendering = false;

    public static JFrame frame;
    public static Keyboard key;
    private static boolean running = false;

    public static UIManager uiManager;

    public static Screen screen;
    private static Font font;

    public static GameStateManager gsm;
    public static AudioManager am;

    private static BufferedImage image = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);//This is the WHOLE rendered "image" that is this Game
    private static int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    //Variables used for in-game mechanics
    public static final int[][] defaultPlayerColors()
    {
        return new int[][]
        {
            {//Player 1
                0xFFEEC395,
                0xFF3F3584,
                0xFFFEFEFE,
                0xFFEB86B5,
                0xFFEB86B5
            },

            {//Player 2
                0xFFEEC395,
                0xFF000000,
                0xFF292A27,
                0xFFEB86B5,
                0xFFEB86B5
            },

            {//Player 3
                0xFFEEC395,
                0xFF7C2016,
                0xFFEE2D16,
                0xFFEB86B5,
                0xFFEB86B5
            },

            {//Player 4
                0xFFEEC395,
                0xFF030C91,
                0xFF1D30FF,
                0xFFEB86B5,
                0xFFEB86B5
            },

            {//Player 5
                0xFFEEC395,
                0xFF37943E,
                0xFF4DD057,
                0xFFEB86B5,
                0xFFEB86B5
            },

            {//Player 6
                0xFFEEC395,
                0xFFB4AE15,
                0xFFFFF733,
                0xFFEB86B5,
                0xFFEB86B5
            },

            {//Player 7
                0xFFEEC395,
                0xFFC02F65,
                0xFFFF5E9A,
                0xFFEB86B5,
                0xFFEB86B5
            },

            {//Player 8
                0xFFEEC395,
                0xFF50ABBA,
                0xFF86EEFF,
                0xFFEB86B5,
                0xFFEB86B5
            },
        };
    };

    public static int[][] playerColors = 
        {
            defaultPlayerColors()[0],
            defaultPlayerColors()[1],
            defaultPlayerColors()[2],
            defaultPlayerColors()[3],
            defaultPlayerColors()[4],
            defaultPlayerColors()[5],
            defaultPlayerColors()[6],
            defaultPlayerColors()[7]
        };
    //

    public Game()
    {
        Dimension size = new Dimension((int)(width * scale), (int)(height * scale));
        setPreferredSize(size);//I made it so that the scale can take decimal numbers with no
        //problems. This allows the pixels to actually fit ANY screen.

        screen = new Screen((int)width, (int)height);//Creates a new screen.
        frame = new JFrame();//Creates a JFrame for the game window.

        key = new Keyboard();//Creates keyboard manager object.
        addKeyListener(key);

        Mouse mouse = new Mouse();//Creates mouse manager object.
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        font = new Font();//Makes a new font object.

        uiManager = new UIManager();//Makes a new UI object.

        am = new AudioManager();
        gsm = new GameStateManager(key);//Creates the Game's GameStateManager.

        updateThread = new UpdateThread(this);
        renderThread = new RenderThread(this);
    }

    public static int getWindowWidth()
    {
        return width * (int)scale;
    }

    public static int getWindowHeight()
    {
        return height * (int)scale;
    }

    public static int getGameWidth()
    {
        return (int)(width);
    }

    public static int getGameHeight()
    {
        return (int)(height);
    }

    public static UIManager getUIManager()
    {
        return uiManager;
    }

    public static void resetUI()
    {
        uiManager = new UIManager();
    }

    public static long getUsedMemory()
    {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public synchronized void start()
    {
        running = true;
        //Because Game extends "Runable", this instance of Game is added to the thread's constructor.
        thread = new Thread(this, "Thread");//Creates the Game's updating thread
        thread.start();//Starts the updating Thread which starts run()
    }

    public synchronized void multiStart()
    {
        updateThread.start();
        renderThread.start();
    }

    public synchronized void stop()
    {
        running = false;
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public synchronized void multiStop()
    {
        updateThread.stop();
        renderThread.stop();
    }

    @Override
    public void run()//This executes as soon as the Game's Thread is made and started (if multi-threaded
    {
        //fps counting
        long startTime = System.currentTimeMillis();
        int frameCount = 0; 
        //int fps = 0;
        requestFocus();//Focuses computer to the Game
        while(running) {
            //frame.requestFocus();
            long before = System.currentTimeMillis();//Takes start frame time.
            update();//Performs Game's update function.
            if(TARGET_FPS == 30){update();}//Updates twice if set to 30 FPS
            render();//Performs Game's render function
            frameCount++;//Increments frameCount.
            long after = System.currentTimeMillis();//Takes the new time after update and render.

            int deltaTime = (int)(after-before); //The total time it took to update and render the frame.

            if(after - startTime >= 1000)
            {
                startTime += 1000;
                fps = frameCount;
                frameCount = 0;
            }
            this.frame.setTitle(TITLE + " | delta: "+ deltaTime + " fps:" + fps);//Updates the title of the JFrame as needed.

            //If update + render took less time than the delta we want for 60fps (around 16ms),
            //then we pause the thread to free the CPU.
            if (TARGET_DELTA > deltaTime) {
                try {
                    Thread.sleep(TARGET_DELTA - deltaTime);//This creates the delay between each Frame(each loop)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void update()//Updates game (fps) times every second
    {
        //System.out.println(getUsedMemory() + " / " + Runtime.getRuntime().totalMemory());
        gsm.update();//Updates the Game State Manager which updates everything else
        key.update();//Updates keyboard
        am.update();
    }

    /**We use Buffer strategies to prepare images for future frames*/
    public synchronized void render()//This is the function that calls for everything else in the game to render Sprites and whatnot
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);//Stores TWO images for future frames
            return;//Gets out of render()
        }

        screen.clear();//Without this, pixels "out of bounds" would "overlap" each other and create a "trippy" effect.

        gsm.render(screen);//Renders the Game State Manager which renders everything else
        //screen.fillRect((int)(Mouse.getX()*scale),(int)(Mouse.getY()*scale), 16,16,0xFF000000,false);//Oh yeah, it also draws a rectangle over the mouse's position
        for(int i = 0; i < pixels.length; i++)
        {
            if(brightness != 0)
            {
                int alpha = screen.pixels[i] & 0xFF000000;
                int red = (int)((screen.pixels[i] & 0x00FF0000) + (brightness*0x00010000));//Extracts whatever values are in the red slot of the color.
                int green = (int)((screen.pixels[i] & 0x0000FF00) + (brightness*0x00000100));//Same for greens,
                int blue = (int)((screen.pixels[i] & 0x000000FF) + (brightness*0x00000001));//and blues.

                if(red > 0x00FF0000){red = 0x00FF0000;}//If red is higher than max red, make it equal max red.
                else if(red < 0x00010000){red = 0x00000000;}//Else if red is lower than one(black), make it black.
                //else{red = red & 0x00FF0000;}//Then the same thing for green and blue...

                if(green > 0x0000FF00){green = 0x0000FF00;}
                else if(green < 0x00000100){green = 0x00000000;}
                //else{green = green & 0x0000FF00;}

                if(blue > 0x000000FF){blue = 0x000000FF;}
                else if(blue < 0x00000001){blue = 0x00000000;}

                int color = alpha | red | green | blue;//Recombine alpha, red, green, and blue to the new color.
                pixels[i] = color;//Paste it in the pixel array.
            }
            else
            {
                pixels[i] = screen.pixels[i];//Paste it in the pixel array.
            }

            //White = 0x00FFFFFF
            //Black = 0x00000000
            //Red = 0x00FF0000           
            //Blue = 0x000000FF
            //Green = 0x0000FF00
            //Yellow = 0x00FFFF00
            //Magenta = 0x00FF00FF           
            //Cyan = 0x0000FFFF
            //Orange = 0x00FF7700
            //Grey = 0x00777777
            //Brown = 0x003F2813
            //Purple = 0x008800FF

            //Pink = 0x00FF77AA          
            //Yellow-Green = 0x0088FF00
            //Dark-Green = 0x00005500
            //Indigo = 0x00332980
        }
        Graphics g = bs.getDrawGraphics();//Creates Graphics object
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());//Paints the empty backdrop black(that way it looks like a void)
        if(!screenStretch)
        {
            if(getHeight() > getWidth())//Graphics then FINALLY draws the array of pixels as an image according to the size of the JFrame.
            {
                g.drawImage(image,0,(getHeight()-((getWidth()/16)*9))/2,getWidth(),(getWidth()/16)*9, null);
            }
            else// if(getWidth() >= getHeight())
            {
                if((getHeight()/9)*16 > getWidth())
                {
                    g.drawImage(image,0,(getHeight()-((getWidth()/16)*9))/2,getWidth(),(getWidth()/16)*9, null);
                }
                else
                {
                    g.drawImage(image,0,0,getWidth(),getHeight(), null);
                }
            }
        }
        else{g.drawImage(image,0,0,getWidth(),getHeight(), null);}
        //g.fillRect(Mouse.getX(),Mouse.getY(), 16,16);//Oh yeah, it also draws a rectangle on the mouse's position
        g.dispose();//Disposes current graphics
        //(graphics would break the game otherwise...)
        bs.show();//Displays everything that just happened.
    }

    public static void setBrightness(float amount)
    {
        brightness+=amount;//It takes amount and adds it to brightness... that's it.
    }

    public static void main(String[] args)//The function that starts the game (creates and starts an instance of the game)
    {
        Game game = new Game();//Creates an instance of Game
        //game.frame.setUndecorated(true);
        game.frame.setResizable(true);//Determines if the window can be stretched (pixels DO scale with the window)
        game.frame.setTitle(Game.TITLE);//Title
        game.frame.add(game);//Adds the instance of Game
        game.frame.pack();//Sets size of JFrame to our component
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Enables the Program to stop when closed
        try {
            game.frame.setIconImage(ImageIO.read(new File("ImageIcon.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        game.frame.setLocationRelativeTo(null);//Center window
        //game.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //game.frame.setUndecorated(true);
        game.frame.setVisible(true);//Make it show something

        game.start();
    }
}
