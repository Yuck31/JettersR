package JettersR.Entity;

import JettersR.*;
import JettersR.Entity.Particle.*;

public class WizardProjectile extends Projectile
{
    public static final int FIRE_RATE = 15;//Delay between shots...

    public WizardProjectile(int x, int y, int z, double dir)
    {
        super(x, y, z, dir);
        range = 75;
        speed = 4;
        damage = 20;
        sprite = Sprite.rotate(Sprite.Bomb0, angle);

        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }

    public void update()
    {
        move();
    }

    public void move()
    {
        x += nx;
        y += ny;
        if (distance() > range) remove();//Formula for range
    }

    private double distance()
    {
        double dist = 0;
        dist = Math.sqrt(/**Math.abs*/(xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y));
        return dist;
    }

    public void render(Screen screen)
    {
        screen.renderProjectile((int)x - 16,(int)y - 16, this, angle);
    }
}