package JettersR.Audio;
/**
 * This is simply for convience reasons when instantiating new SFX
 *
 * @author: Luke Sullivan
 * @12/8/19
 */
public class AudioContents
{
    protected String path;
    protected float volume = 1;
    protected boolean loop = false;
    protected int timeToLive = 0;
    
    AudioContents(String path, int timeToLive)
    {
        this.path = path;
        this.timeToLive = timeToLive;
    }
    
    AudioContents(String path, int timeToLive, float volume)
    {
        this.path = path;
        this.timeToLive = timeToLive;
        this.volume = volume;
    }
    
    AudioContents(String path, int timeToLive, boolean loop)
    {
        this.path = path;
        this.timeToLive = timeToLive;
        this.loop = loop;
    }
    
    AudioContents(String path, int timeToLive, float volume, boolean loop)
    {
        this.path = path;
        this.timeToLive = timeToLive;
        this.volume = volume;
        this.loop = loop;
    }
    
    public String getPath(){return path;}
    public int getTimeToLive(){return timeToLive;}
    public float getVolume(){return volume;}
    public boolean getLoop(){return loop;}
}
