package JettersR.Entity.Particle;

import JettersR.*;
import JettersR.Entity.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

public class Particle extends Entity
{
    protected Random random = new Random();

    public float life;
    public float startLife;

    public double startNx, startNy;
    
    public float zz;
    public float za;

    public Particle(int x, int y, int z, int life)//Single Particles
    {
        super(x,y,z);
        this.x = x + (ThreadLocalRandom.current().nextDouble(-2, 2));
        if(this.x <= x)
        {
            this.nx = ThreadLocalRandom.current().nextDouble(-3, -1) * 2;
        }
        else if(this.x >= x)
        {
            this.nx = ThreadLocalRandom.current().nextDouble(1, 3) * 2;
        }
        
        this.y = y + (ThreadLocalRandom.current().nextDouble(-2, 2));
        if(this.y <= y)
        {
            this.ny = ThreadLocalRandom.current().nextDouble(-3, -1) * 2;
        }
        else if(this.y >= y)
        {
            this.ny = ThreadLocalRandom.current().nextDouble(1, 3) * 2;
        }
        
        this.startNx = this.nx;
        this.startNy = this.ny;
        this.z = z;
        this.life = life + (random.nextInt(20)-10);
        this.startLife = 20;
        sprite = Sprite.grayCloudParticle1;
        this.zz = random.nextFloat() + 2.0f;
    }

    public void update()
    {
        life--;
        if(life <= 0) remove();
        
        this.x += nx;
        this.y += ny;
        
        if(startNx > 0)
        {
            if(nx > 0){nx -= 0.2f;}
            else{nx = 0;}
        }
        else if(startNx < 0)
        {
            if(nx < 0){nx += 0.2f;}
            else{nx = 0;}
        }
        
        if(startNy > 0)
        {
            if(ny > 0){ny -= 0.2f;}
            else{ny = 0;}
        }
        else if(startNy < 0)
        {
            if(ny < 0){ny += 0.2f;}
            else{ny = 0;}
        }
    }

    // public void render(Screen screen)
    // {
        // screen.renderSprite((int)(x-(15*(life/startLife))), (int)((y)-(z*16)-(15*(life/startLife))), Sprite.scale(sprite, (life/startLife)), true);
    // }

    // public void renderScale(Screen screen, float scale)
    // {
        // screen.renderSprite((int)((x-(15*(life/startLife)))*scale), (int)(((y)-(z*16)-(15*(life/startLife)))*scale), Sprite.scale(sprite, ((life/startLife))*scale), true);
    // }
    
    public void render(Screen screen)
    {
        //x = ((x-(z*16))-((sprite.height/2)*((life/startLife)*2)));//Formula for centering scaled sprites
        screen.renderSprite((int)(x-((sprite.width/2)*((life/startLife)))), (int)((y-(z*16))-((sprite.height/2)*((life/startLife)*2))), Sprite.scale(sprite, (life/startLife)), true);
    }

    public void renderScale(Screen screen, float scale)
    {
        screen.renderSprite((int)((x-((sprite.width/2)*((life/startLife))))*scale), (int)(((y-(z*16))-((sprite.height/2)*((life/startLife)*2)))*scale), Sprite.scale(sprite, ((life/startLife))*scale), true);
    }
}
