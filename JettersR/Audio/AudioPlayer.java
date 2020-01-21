package JettersR.Audio;
/**
 * This is the Class that plays WAV files.
 * 
 *
 * author: Luke Sullivan
 * Last Edit: 1/19/2020
 */

import javax.sound.sampled.*;
import java.io.*;

public class AudioPlayer implements AutoCloseable
{
    AudioInputStream ais;
    AudioFormat decodeFormat;
    AudioInputStream dais;

    //These variables are meant for recycling the sound effect if possible
    public AudioContents contents;
    public int timeToLive = 0;//In seconds
    public final int startTimeToLive;
    public byte ttlFrames = 0;//In frames
    //

    protected Clip clip;
    public boolean played = false;

    public AudioPlayer(String path, AudioContents contents, int timeToLive)
    {
        this.contents = contents;
        this.timeToLive = timeToLive;
        startTimeToLive = timeToLive;
        try
        {
            ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
            AudioFormat baseFormat = ais.getFormat();

            decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(),false);

            dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

            clip = AudioSystem.getClip();
            clip.open(dais);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public AudioPlayer(String path, float volume, AudioContents contents, int timeToLive)
    {
        this(path, contents, timeToLive);
        setVolume(volume);
    }

    public AudioPlayer(Clip clip, AudioInputStream dais)
    {
        this.clip = clip;
        startTimeToLive = 0;
    }

    public AudioPlayer(Clip clip, AudioInputStream dais, float volume)
    {
        this(clip, dais);
        setVolume(volume);
    }

    public float getVolume()
    {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) 
    {
        try
        {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume, int soundNum) 
    {
        try
        {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        //System.out.println(clips[0].getFramePosition());
        if(clip == null){return;}
        else if(clip.isRunning()){stop();}
        clip.setFramePosition(0);
        clip.start();
        played = true;
    }

    public void resume()
    {
        if(clip == null){ return;}
        else if(clip.isRunning()){stop();}
        clip.start();
    }

    public void stop()
    {
        if(clip != null && clip.isRunning())
        {
            clip.stop();
        }
    }

    public boolean getPlaying()
    {
        if(clip != null)
        {
            return clip.isRunning();
        }
        else{return false;}
    }

    public void close()
    {
        try
        {
            clip.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        clip = null;
    }

    public boolean getPlayed()
    {
        return played;
    }

    public AudioPlayer getInstance()
    {
        return new AudioPlayer(getClip(), dais);
    }

    public Clip getClip()
    {
        return clip;
    }
}
