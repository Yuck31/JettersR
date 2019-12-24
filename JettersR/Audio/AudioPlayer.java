package JettersR.Audio;

/**
 * This is the Class that plays WAV files.
 * 
 *
 * author: Luke Sullivan
 * Last Edit: 9/21/2019
 */

import javax.sound.sampled.*;
import java.io.*;

public class AudioPlayer implements AutoCloseable
{
    private Clip[] clips;
    AudioInputStream ais;
    public boolean played = false;

    public AudioPlayer(String path, int soundCount)
    {
        FileInputStream stream;
        try
        {
            stream = new FileInputStream(path);
        }
        catch(Exception e){}

        clips = new Clip[soundCount];

        for(int i = 0; i < soundCount; i++)
        {
            try
            {
                ais = 
                AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
                AudioFormat baseFormat = ais.getFormat();

                AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(),false);

                AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

                clips[i] = AudioSystem.getClip();
                clips[i].open(dais);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public AudioPlayer(String path)
    {
        this(path, 1);
    }

    public AudioPlayer(String path, float volume)
    {
        this(path, 1);
        setVolume(volume);
    }

    public AudioPlayer(String path, int soundCount, float volume)
    {
        this(path, soundCount);
        for(int i = 0; i < soundCount; i++)
        {
            setVolume(volume, i);
        }
    }

    public float getVolume()
    {
        FloatControl gainControl = (FloatControl) clips[0].getControl(FloatControl.Type.MASTER_GAIN);        
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) 
    {
        try
        {
            FloatControl gainControl = (FloatControl) clips[0].getControl(FloatControl.Type.MASTER_GAIN);        
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
            FloatControl gainControl = (FloatControl) clips[soundNum].getControl(FloatControl.Type.MASTER_GAIN);        
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void play(int soundNum)
    {
        if(clips[soundNum] == null){return;}
        else if(clips[soundNum].isRunning()){stop(soundNum);}
        clips[soundNum].setFramePosition(0);
        clips[soundNum].start();
    }

    public void play()
    {
        if(clips[0] == null){return;}
        else if(clips[0].isRunning()){stop(0);}
        clips[0].setFramePosition(0);
        clips[0].start();
        played = true;
    }

    public void playOver()
    {
        for(int i = 0; i < clips.length; i++)
        {
            if(!getPlaying(i))
            {
                play(i);
                return;
            }
        }
        play(0);
    }

    public void resume(int soundNum)
    {
        if(clips[soundNum] == null){return;}
        else if(clips[soundNum].isRunning()){stop(soundNum);}
        clips[soundNum].start();
    }

    public void resume()
    {
        if(clips[0] == null){ return;}
        else if(clips[0].isRunning()){stop(0);}
        clips[0].start();
    }

    public void stop(int soundNum)
    {
        if(clips[soundNum] != null && clips[soundNum].isRunning())
        {
            clips[soundNum].stop();
        }
    }

    public void stop()
    {
        if(clips[0] != null && clips[0].isRunning())
        {
            clips[0].stop();
        }
    }

    public boolean getPlaying(int soundNum)
    {
        if(clips[soundNum] != null)
        {
            played = true;
            return clips[soundNum].isRunning();
        }
        else{return false;}
    }

    public boolean getPlaying()
    {
        if(clips[0] != null)
        {
            //played = true;
            //System.out.println(clips[0].isRunning());
            return clips[0].isRunning();
        }
        else{return false;}
    }

    public void close(int soundNum)
    {
        try
        {
            stop(soundNum);
            clips[soundNum].close();
            clips[soundNum] = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        clips[soundNum] = null;
    }

    public void close()
    {
        try
        {
            //stop();
            clips[0].close();
            //finalize();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //clips[0] = null;
    }

    public void closeAll()
    {
        for(int i = 0; i < clips.length; i++)
        {
            try
            {
                stop(i);
                clips[i].close();
                clips[i] = null;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            clips[i] = null;
        }
    }

    public boolean getPlayed()
    {
        return played;
    }
}
