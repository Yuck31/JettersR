package JettersR.Audio;
/**
 * This will be the class the manages currently playing audio clips.
 *
 * @author: Luke Sullivan
 * @12/7/19
 */
import java.util.List;
import java.util.ArrayList;
import JettersR.*;
public class AudioManager
{
    private static List<AudioPlayer> audioPlayers = new ArrayList<AudioPlayer>();
    private static OGGPlayer currentOggPlayer;
    private static boolean oggPlaying = false;

    /**
     * All of the contents that are meant to be put into an AudioPlayer
     * Are loaded here in this format so that they can be easily used.
     */
    //UI Sounds
    public static AudioContents cancel = new AudioContents("/Sounds/UI/Cancel.wav"),
    select0 = new AudioContents("/Sounds/UI/Select0.wav", 0.3f),
    confirm0 = new AudioContents("/Sounds/UI/Confirm0.wav", 2f),
    confirm1 = new AudioContents("/Sounds/UI/Confirm1.wav", 2f);
    //

    //In-Game Music
    public static OGGContents music_menu = new OGGContents("Music/Bomber5MENU.ogg", true),

    music_bombFactory = new OGGContents("Music/BomberBATTLE.ogg", true),
    music_bombFactorySD = new OGGContents("Music/SDBomberBATTLE.ogg", true),
    music_euroBomberBattle = new OGGContents("Music/EUROBomberBattle.ogg", true),

    music_greatWall = new OGGContents("Music/GreatWall.ogg", 0.9f, true),
    music_greatWallSD = new OGGContents("Music/SDGreatWall.ogg", 0.9f, true),

    music_powerZone = new OGGContents("Music/SUPERBomber.ogg", true),
    music_powerZoneSD = new OGGContents("Music/SDSUPERBomber.ogg", true),

    music_battle64 = new OGGContents("Music/BomberBATTLE64.ogg", true),
    music_battle64SD = new OGGContents("Music/SDBomberBATTLE64.ogg", true),

    music_mujoesTheme = new OGGContents("Music/MujoesTheme.ogg", true);
    //End of In-Game Music

    //In-Game Sounds

    //Players
    public static AudioContents sounds_bomberDeath = new AudioContents("/Sounds/Player/BomberDeath.wav", 0.6f),
    sounds_bomberFall = new AudioContents("/Sounds/Player/BomberFall.wav"),
    sounds_bomberShoot1 = new AudioContents("/Sounds/Player/BomberShoot1.wav", 0.7f),
    sounds_bomberShoot2 = new AudioContents("/Sounds/Player/BomberShoot2.wav"),
    sounds_bomberShoot3 = new AudioContents("/Sounds/Player/BomberShoot3.wav"),
    sounds_bombPlace = new AudioContents("/Sounds/Player/BombPlace.wav"),
    sounds_bombPump1 = new AudioContents("/Sounds/Player/BombPump1.wav"),
    sounds_bombPump2 = new AudioContents("/Sounds/Player/BombPump2.wav"),
    sounds_bombPump3 = new AudioContents("/Sounds/Player/BombPump3.wav"),
    sounds_bombPunch = new AudioContents("/Sounds/Player/BombPunch.wav", 2f),
    sounds_bombKick = new AudioContents("/Sounds/Bombs/BombBounce.wav"),
    sounds_bombThrow = new AudioContents("/Sounds/Player/BombThrow.wav"),
    sounds_poof = new AudioContents("/Sounds/Player/Poof.wav"),
    sounds_bombPunchHit = new AudioContents("/Sounds/Player/PunchHit.wav", 0.6f);
    //

    //Bombs
    public static AudioContents sounds_bombBounce = new AudioContents("/Sounds/Bombs/BombBounce.wav"),
    sounds_bombBeep = new AudioContents("/Sounds/Bombs/BombBeep.wav"),
    sounds_explosion1 = new AudioContents("/Sounds/Bombs/Explosion1.wav"),
    sounds_explosion2 = new AudioContents("/Sounds/Bombs/Explosion2.wav"),
    sounds_explosion3 = new AudioContents("/Sounds/Bombs/Explosion3.wav"),
    sounds_pierceExplosion1 = new AudioContents("/Sounds/Bombs/PierceExplosion1.wav"),
    sounds_pierceExplosion2 = new AudioContents("/Sounds/Bombs/PierceExplosion2.wav"),
    sounds_pierceExplosion3 = new AudioContents("/Sounds/Bombs/PierceExplosion3.wav"),
    sounds_dangerousExplosion1 = new AudioContents("/Sounds/Bombs/DangerousExplosion0.wav", 1f),
    sounds_dangerousExplosion2 = new AudioContents("/Sounds/Bombs/DangerousExplosion1.wav", 0.7f),
    sounds_dangerousExplosion3 = new AudioContents("/Sounds/Bombs/DangerousExplosion2.wav", 0.7f);
    //

    //Misc.
    public static AudioContents sounds_itemCollect = new AudioContents("/Sounds/Misc/ItemCollect.wav", 0.65f),
    skullCollect = new AudioContents("/Sounds/Misc/SkullCollect.wav");
    //

    //End of In-Game Sounds

    public AudioManager()
    {

    }

    public void update()
    {
        if(oggPlaying && currentOggPlayer != null)
        {
            if(!currentOggPlayer.getPlaying() && currentOggPlayer.loop)
            {
                currentOggPlayer.play();
            }
        }
        remove();
    }

    public void add(AudioPlayer a){audioPlayers.add(a);}

    public void add(AudioContents a){audioPlayers.add(audioPlayer(a));}

    public void remove()
    {
        for(int i = 0; i < audioPlayers.size(); i++)
        {
            if(!audioPlayers.get(i).getPlaying())
            {
                if(!audioPlayers.get(i).getPlayed())
                {audioPlayers.get(i).play();}
                else
                {
                    audioPlayers.get(i).close();
                    audioPlayers.remove(i);
                }
            }
        }
    }

    public void setOGG(OGGPlayer ogg)
    {
        if(currentOggPlayer != null){currentOggPlayer.close();}
        currentOggPlayer = ogg;
    }

    public void playOGG()
    {
        oggPlaying = true;
        currentOggPlayer.play();
    }

    public void stopOGG()
    {
        oggPlaying = false;
        currentOggPlayer.stop();
    }

    public static AudioPlayer audioPlayer(AudioContents a){return new AudioPlayer(a.getPath(), a.getVolume());}

    public static OGGPlayer oggPlayer(OGGContents o){return new OGGPlayer(o.getPath(), o.getVolume(), o.getLoop());}
}
