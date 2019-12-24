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

public class UpdateThread implements Runnable
{
    public Thread thread;
    public static Game game;
    public boolean running = false;

    public UpdateThread(Game game)
    {
        this.game = game;
    }

    public synchronized void start()
    {
        running = true;
        //Because Game extends "Runable", this instance of Game is added to the thread's constructor.
        thread = new Thread(this, "Updating");//Creates the Game's updating thread
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
    {
        long startTime = System.currentTimeMillis();
        int frameCount = 0; 
        while(running)
        {
            long before = System.currentTimeMillis();//Takes start frame time.
 
            //game.updating = true;
            game.update();//Performs Game's update function.
            //game.updating = false;
            
            frameCount++;//Increments frameCount.
            long after = System.currentTimeMillis();//Takes the new time after update and render.

            int deltaTime = (int)(after-before); //The total time it took to update and render the frame.

            if(after - startTime >= 1000)
            {
                startTime += 1000;
                game.fps = frameCount;
                frameCount = 0;
            }

            //If update took less time than the delta we want for 60fps (around 16ms),
            //then we pause the thread to free the CPU.
            if (game.TARGET_DELTA > deltaTime) {
                try {
                    Thread.sleep(game.TARGET_DELTA - deltaTime);//This creates the delay between each Frame(each loop)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //}
        }
    }
}
