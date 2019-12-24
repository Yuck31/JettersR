package JettersR.Audio;
/**
 * This is simply for convience reasons when instantiating new SFX
 *
 * @author: Luke Sullivan
 * @12/8/19
 */
public class AudioContents
{
    String path;
    float volume = 1;
    boolean loop = false;
    
    AudioContents(String path)
    {
        this.path = path;
    }
    
    AudioContents(String path, float volume)
    {
        this.path = path;
        this.volume = volume;
    }
    
    AudioContents(String path, boolean loop)
    {
        this.path = path;
        this.loop = loop;
    }
    
    AudioContents(String path, float volume, boolean loop)
    {
        this.path = path;
        this.volume = volume;
        this.loop = loop;
    }
    
    public String getPath(){return path;}
    public float getVolume(){return volume;}
    public boolean getLoop(){return loop;}
}
