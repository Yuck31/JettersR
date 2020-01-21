package JettersR.Audio;
/**
 * This is the class the manages audio clips.
 *
 * @author: Luke Sullivan
 * @1/19/20
 */
import java.util.List;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.*;

import JettersR.*;

public class AudioManager
{
    private static List<AudioPlayer> audioPlayers = new ArrayList<AudioPlayer>();
    private static OGGPlayer currentOggPlayer;
    private static boolean oggPlaying = false;

    /**
     * All of the contents that are meant to be put into an AudioPlayer
     * are loaded here in this format so that they can be easily used.
     */
    //UI Sounds
    public static AudioContents cancel = new AudioContents("/Sounds/UI/Cancel.wav", 10),
    select0 = new AudioContents("/Sounds/UI/Select0.wav", 10, 0.3f),
    confirm0 = new AudioContents("/Sounds/UI/Confirm0.wav", 10, 2f),
    confirm1 = new AudioContents("/Sounds/UI/Confirm1.wav", 10, 2f);
    //

    //In-Game Music
    public static OGGContents music_menu = new OGGContents("Music/Bomber5MENU.ogg", true),

    music_bombFactory = new OGGContents("Music/BomberBATTLE.ogg", true),
    music_bombFactorySD = new OGGContents("Music/SDBomberBATTLE.ogg", true),
    music_euroBomberBattle = new OGGContents("Music/EUROBomberBattle.ogg", true),
    music_euroBomberBattleSD = new OGGContents("Music/SDEUROBomberBattle.ogg", true),

    music_greatWall = new OGGContents("Music/GreatWall.ogg", 0.9f, true),
    music_greatWallSD = new OGGContents("Music/SDGreatWall.ogg", 0.9f, true),

    music_powerZone = new OGGContents("Music/SUPERBomber.ogg", true),
    music_powerZoneSD = new OGGContents("Music/SDSUPERBomber.ogg", true),

    music_battle64 = new OGGContents("Music/BomberBATTLE64.ogg", true),
    music_battle64SD = new OGGContents("Music/SDBomberBATTLE64.ogg", true),

    music_mujoesTheme = new OGGContents("Music/MujoesTheme.ogg", true),
    
    music_onlineLobby = new OGGContents("Music/OnlineLobby.ogg", true);
    //End of In-Game Music

    //In-Game Sounds

    //Players
    public static AudioContents sounds_bomberDeath = new AudioContents("/Sounds/Player/BomberDeath.wav", 10, 0.6f),
    sounds_bomberFall = new AudioContents("/Sounds/Player/BomberFall.wav", 10),
    sounds_bomberShoot1 = new AudioContents("/Sounds/Player/BomberShoot1.wav", 10, 0.7f),
    sounds_bomberShoot2 = new AudioContents("/Sounds/Player/BomberShoot2.wav", 10),
    sounds_bomberShoot3 = new AudioContents("/Sounds/Player/BomberShoot3.wav", 10),
    sounds_bombPlace = new AudioContents("/Sounds/Player/BombPlace.wav", 30),
    sounds_bombPump1 = new AudioContents("/Sounds/Player/BombPump1.wav", 10),
    sounds_bombPump2 = new AudioContents("/Sounds/Player/BombPump2.wav", 10),
    sounds_bombPump3 = new AudioContents("/Sounds/Player/BombPump3.wav", 10),
    sounds_bombPunch = new AudioContents("/Sounds/Player/BombPunch.wav", 20, 2f),
    sounds_bombKick = new AudioContents("/Sounds/Bombs/BombBounce.wav", 20),
    sounds_bombThrow = new AudioContents("/Sounds/Player/BombThrow.wav", 15),
    sounds_poof = new AudioContents("/Sounds/Player/Poof.wav", 6),
    sounds_bombPunchHit = new AudioContents("/Sounds/Player/PunchHit.wav", 6, 0.6f);
    //

    //Bombs
    public static AudioContents sounds_bombBounce = new AudioContents("/Sounds/Bombs/BombBounce.wav", 20),
    sounds_bombBeep = new AudioContents("/Sounds/Bombs/BombBeep.wav", 20),
    sounds_explosion1 = new AudioContents("/Sounds/Bombs/Explosion1.wav", 30),
    sounds_explosion2 = new AudioContents("/Sounds/Bombs/Explosion2.wav", 30),
    sounds_explosion3 = new AudioContents("/Sounds/Bombs/Explosion3.wav", 30),
    sounds_pierceExplosion1 = new AudioContents("/Sounds/Bombs/PierceExplosion1.wav", 30),
    sounds_pierceExplosion2 = new AudioContents("/Sounds/Bombs/PierceExplosion2.wav", 30),
    sounds_pierceExplosion3 = new AudioContents("/Sounds/Bombs/PierceExplosion3.wav", 30),
    sounds_dangerousExplosion1 = new AudioContents("/Sounds/Bombs/DangerousExplosion0.wav", 30, 1f),
    sounds_dangerousExplosion2 = new AudioContents("/Sounds/Bombs/DangerousExplosion1.wav", 30, 0.7f),
    sounds_dangerousExplosion3 = new AudioContents("/Sounds/Bombs/DangerousExplosion2.wav", 30, 0.7f);
    //

    //Misc.
    public static AudioContents sounds_itemCollect = new AudioContents("/Sounds/Misc/ItemCollect.wav", 30, 0.65f),
    sounds_skullCollect = new AudioContents("/Sounds/Misc/SkullCollect.wav", 25);
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

    public void add(AudioPlayer a)//This was only used for testing reasons.
    {
        audioPlayers.add(a);
    }

    //I hear there's this strange concept called "caching", but...
    //THIS is what I like to call "Data Recycling". But it's just caching with a different take...
    //The concept is simple, whenever a sound effect is added to the list of sound effects...
    //the game checks if there are any not-playing sound effects in the list that have the same...
    //AudioContents as the ones in the parameters. If so, it replays it and cancels the addition.
    //If not, then it adds the new sound effect.
    public void add(AudioContents a)
    {
        for(int i = 0; i < audioPlayers.size(); i++)
        {
            AudioPlayer ap = audioPlayers.get(i);
            //If there is already a not-playing sound effect in the list that has the same AudioContents...
            if(ap.contents == a && !ap.getPlaying())
            {
                ap.played = false;//Replay it
                ap.timeToLive = ap.startTimeToLive; ap.ttlFrames = 0;//Reset its TimeToLive
                return;//Cancel addition
            }
        }
        audioPlayers.add(audioPlayer(a));//Otherwise, add the new sound effect
    }
    //Instead of removing sound effects IMEDIATLY after being played, all sound effects now have a Time-To-Live value.
    //This value is reduced every frame. Whenever it runs out, THEN the sound effect is disposed.
    //This significantly reduces the number of garbage-disposals that the game has to do.
    public void remove()
    {
        for(int i = 0; i < audioPlayers.size(); i++)
        {
            AudioPlayer ap = audioPlayers.get(i);
            if(!ap.getPlaying())
            {
                if(!ap.getPlayed())
                {ap.play();}
                else if(ap.timeToLive >= 0)
                {
                    ap.ttlFrames--;//Reduce TimeToLive
                    if(ap.ttlFrames <= 0){ap.timeToLive--; ap.ttlFrames = 60;}
                }
                else//When the sound effect's TimeToLive runs out...
                {
                    audioPlayers.get(i).close();//Releases the memory used up by the sound effect.
                    audioPlayers.remove(i);//Remove the sound effect's reference, preparing it for garbage-disposal.
                }
            }
        }
    }
    //Without "Data Recycling", the game would FREQUENTLY perform Garbage Collection, freezing the game for SECONDS at a time.

    public void dump()//This should ONLY be used prior to loading screens (if there are any)
    {
        for(int i = 0; i < audioPlayers.size(); i++)
        {
            audioPlayers.get(i).close();
            audioPlayers.remove(i);
        }
        System.gc();
    }

    public void setOGG(OGGPlayer ogg)
    {
        if(currentOggPlayer != null){currentOggPlayer.close();}
        currentOggPlayer = ogg;
    }

    public void setOGG(OGGContents ogg)
    {
        if(currentOggPlayer != null){currentOggPlayer.close();}
        currentOggPlayer = oggPlayer(ogg);
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

    public static AudioPlayer audioPlayer(AudioContents a){return new AudioPlayer(a.getPath(), a.getVolume(), a, a.getTimeToLive());}

    public static OGGPlayer oggPlayer(OGGContents o){return new OGGPlayer(o.getPath(), o.getVolume(), o.getLoop());}
}
