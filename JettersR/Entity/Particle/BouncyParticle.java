package JettersR.Entity.Particle;

import JettersR.*;
import JettersR.Entity.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class BouncyParticle extends Entity
{
    protected Random random = new Random();
    
    public float life;
    public float startLife;
    
    public float zz;
    public float za;
    
    public BouncyParticle(int x, int y, int z, int life)//Single Particles
    {
        super(x,y,z);
        this.x = x;
        this.y = y;
        this.z = z;
        this.life = life + (random.nextInt(20)-10);
        this.startLife = this.life;
        sprite = Sprite.grayCloudParticle1;
        this.nx = random.nextGaussian();//Number between -1 and 1
        this.ny = random.nextGaussian();
        this.zz = random.nextFloat() + 2.0f;
    }
    
    public void update()
    {
        life--;
        if(life <= 0) remove();
        za -= 0.1;
        
        if(zz < 0)
        {
            zz = 0;
            za *= -1;
        }
        this.x += nx;
        this.y += ny;
        this.zz += za;
    }
    
    public void render(Screen screen)
    {
        screen.renderSprite((int)x, (int)((y - zz)-(z*16)), Sprite.scale(sprite, (life/startLife)), true);
    }
    
    public void renderScale(Screen screen, float scale)
    {
        screen.renderSprite((int)x, (int)((y - zz)-(z*16)), Sprite.scale(sprite, (life/startLife)*scale), true);
    }
}
