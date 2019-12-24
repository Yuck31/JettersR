package JettersR.Entity.Particle;

import JettersR.*;
import JettersR.Entity.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class PoofParticle extends Particle
{    
    public PoofParticle(int x, int y, int z, int life)//Single Particles
    {
        super(x,y,z,life);
        sprite = Sprite.grayCloudParticle0;
    }
}
