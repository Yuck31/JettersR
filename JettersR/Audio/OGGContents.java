package JettersR.Audio;
/**
 * This is simply for convience reasons when instantiating new Music
 *
 * @author: Luke Sullivan
 * @12/8/19
 */
public class OGGContents extends AudioContents
{
    OGGContents(String path)
    {
        super(path, 0);
        volume = -1;
    }
    
    OGGContents(String path, float volume)
    {
        super(path, 0, volume);
    }
    
    OGGContents(String path, boolean loop)
    {
        super(path, 0, loop);
        volume = -1;
    }
    
    OGGContents(String path, float volume, boolean loop)
    {
        super(path, 0, volume, loop);
    }
}
