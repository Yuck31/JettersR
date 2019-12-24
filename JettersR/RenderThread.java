package JettersR;
/**
 * This is a test on Multi-Threading
 *
 * @author: Luke Sullivan
 * @Date: 11/8/19
 */

import JettersR.*;
import JettersR.GameStates.*;
import JettersR.UI.*;
import JettersR.Entity.Mob.*;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import com.studiohartman.jamepad.*;//CONTROLLER SUPPORT!

public class RenderThread implements Runnable
{
    public Thread thread;
    public static Game game;
    private JFrame frame;
    public boolean running = false;

    public RenderThread(Game game)
    {
        this.game = game;
    }

    public synchronized void start()
    {
        running = true;
        thread = new Thread(this, "Rendering");//Creates the Game's updating thread
        thread.start();//Starts the updating Thread which starts run()
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

    @Override
    public void run()
    {//This executes as soon as the Game's Thread is made and started
        //fps counting
        long startTime = System.currentTimeMillis();
        int frameCount = 0; 
        //int fps = 0;
        game.requestFocus();//Focuses computer to the Game
        while(running)
        {
            long before = System.currentTimeMillis();//Takes start frame time.
            
            //game.rendering = true;
            try{game.render();}//Performs Game's render function
            catch(Exception e)
            {e.printStackTrace();}//Catchs exception in case of desync with updating thread
            //game.rendering = false;
            
            frameCount++;//Increments frameCount.
            long after = System.currentTimeMillis();//Takes the new time after update and render.

            int deltaTime = (int)(after-before); //The total time it took to update and render the frame.

            if(after - startTime >= 1000)
            {
                startTime += 1000;
                //game.fps = frameCount;
                frameCount = 0;
            }
            game.frame.setTitle(game.TITLE + " | delta: "+ deltaTime + " fps:" + game.fps);//Updates the title of the JFrame as needed.
            //If render took less time than the delta we want for 60fps (around 16ms),
            //then we pause the thread to free the CPU.
            if (game.TARGET_DELTA > deltaTime) 
            {
                try {
                    Thread.sleep(game.TARGET_DELTA - deltaTime);//This creates the delay between each Frame(each loop)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
