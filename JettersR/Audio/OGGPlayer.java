package JettersR.Audio;
/**
 * Thanks to EasyOGG, OGG sound files can be decoded and played.
 * OGG files are used usually for background music and are specifically
 * used to reduce the amount of space the whole game takes up.
 *
 * @author Luke Sullivan
 * @Last Edit 12/7/2019
 */
import org.newdawn.easyogg.*;//OGG SUPPORT!!
import java.io.File;
import java.io.*;

public class OGGPlayer implements AutoCloseable
{
    String path;
    public boolean loop = true;
    public OggClip clip = null;
    float volume = -1f;
    //OGG clips have the following public functions:

    //setDefaultGain
    //setGain
    //setBalance
    //pause
    //isPaused
    //resume
    //stopped(returns boolean)
    //play
    //loop
    //stop
    //close

    public OGGPlayer(String path)
    {
        this.path = path;
    }

    public OGGPlayer(String path, float volume)
    {
        this.path = path;
        this.volume = volume;
    }
    
    public OGGPlayer(String path, boolean loop)
    {
        this.path = path;
        this.loop = loop;
    }
    
    public OGGPlayer(String path, float volume, boolean loop)
    {
        this.path = path;
        this.volume = volume;
        this.loop = loop;
    }

    public void open()
    {
        try
        {
            FileInputStream stream = new FileInputStream(path);
            OggClip tempClip = new OggClip(stream);
            clip = tempClip;
        } catch (IOException e) {e.printStackTrace();}
        setVolume(volume);
    }
    //
    //
    public void play()
    {
        if(clip == null){open();}
        if(clip != null)
        {
            if(clip.isPaused()){resume();}
            clip.play();
        }
    }

    public void stop()
    {
        if(clip == null){return;}
        else
        {
            try
            {
                if(clip.isPaused()){resume();}
                clip.stop();
            }
            catch(NullPointerException e){e.printStackTrace();}
        }
    }

    public void pause()
    {
        //if(clip == null){return;}
        //else
        //{
            try
            {
                if(!clip.isPaused())
                {
                    clip.pause();
                }
            }
            catch(NullPointerException e){e.printStackTrace();}
        //}
    }

    public void resume()
    {
        if(clip == null){return;}
        else
        {
            try
            {
                clip.resume();
            }
            catch(NullPointerException e){e.printStackTrace();}
        }
    }

    public boolean getPlaying()
    {
        if(clip != null)
        {
            if(!clip.stopped()){return true;}
            //else if(!clip.isPaused()){return true;}
            else{return false;}
        }
        else{return false;}
    }

    public void setVolume(float volume) 
    {
        if(clip != null && volume != -1)
        {
            try
            {
                clip.setGain(volume);
            }
            catch(Exception e){e.printStackTrace();}
        }
    }

    public void close()
    {
        if(clip != null)
        {
            try
            {
                clip.pause();
                clip.close();
                clip = null;
            }
            catch(Exception e){e.printStackTrace();}
        }
        //System.gc();
    }
}
