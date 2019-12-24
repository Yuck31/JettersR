package JettersR.Entity.Particle;
/**
 * Write a description of class ParticleManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import JettersR.*;
import JettersR.Entity.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class ParticleManager extends Entity
{
    public enum particleType
    {
        GRAYCLOUD0, GRAYCLOUD1
    }
    public List<Particle> particles = new ArrayList<>();//Stores particles

    public ParticleManager(int x, int y, int z, int life, int amount, particleType type)
    {
        super(x,y,z);
        this.x = x;
        this.y = y;
        this.z = z;
        for(int i  = 0; i<amount-1; i++)
        {
            switch(type)
            {
                case GRAYCLOUD0: particles.add(new PoofParticle(x,y,z,life));
                break;
                
                case GRAYCLOUD1: particles.add(new Particle(x,y,z,life));
                break;
            }
        }
    }

    public void update()
    {
        for(int i  = 0; i < particles.size(); i++)
        {
            particles.get(i).update();
            if(particles.get(i).isRemoved())
            {
                particles.remove(i);
            }
        }
        if(particles.size() <= 0){remove();}
    }

    public void render(Screen screen)
    {
        for(int i  = 0; i < particles.size(); i++)
        {
            particles.get(i).render(screen);
        }
    }

    public void renderScale(Screen screen, float scale)
    {
        for(int i  = 0; i < particles.size(); i++)
        {
            particles.get(i).renderScale(screen, scale);
        }
    }
}
