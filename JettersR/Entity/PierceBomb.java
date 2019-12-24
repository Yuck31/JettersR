package JettersR.Entity;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Particle.*;

public class PierceBomb extends Bomb
{
    public PierceBomb(int x, int y, int z, int fires, boolean remote, Player player)
    {
        super(x, y, z, fires, remote, player);
        this.x = x;
        this.y = y;
        this.bx = x;
        this.by = y;
        this.z = z;
        this.fires = fires;
        this.player = player;

        idleBomb = new AnimatedSprite(SpriteSheet.PierceBombAnim,40,40,4, 10);
        bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);

        if(remote)
        {
            idleBomb = new AnimatedSprite(SpriteSheet.remotePierceBombAnim,40,40,4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
            bombBeep = new AnimatedSprite(SpriteSheet.remotePierceBombBeep, 40, 40, 2, 2);
        }

        bounds.x = 4;
        bounds.y = 3;
        bounds.width = 31;
        bounds.height = 31;

        this.nx = 0;
        this.ny = 0;

        //damage = 20;
    }

    public PierceBomb(int x, int y, int z,  int fires, boolean remote, Player player, int dirX, int dirY, int forceMulti, int hopTime)
    {
        this(x, y, z, fires, remote, player);
        this.dirX = dirX;
        this.dirY = dirY;
        this.forceMulti = forceMulti;
        this.hopTime = hopTime;

        //damage = 20;
        hop(dirX, dirY, true, forceMulti);
    }

    public PierceBomb(int x, int y, int z, int fires, boolean remote, Player player, Player player2, int dirX, int dirY)//Used specifically for Bomber Shoot
    {
        super(x,y, z, fires, remote, player, player2, dirX, dirY);

        idleBomb = new AnimatedSprite(SpriteSheet.PierceBombAnim,40,40,4, 10);
        bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);

        if(remote)
        {
            idleBomb = new AnimatedSprite(SpriteSheet.remotePierceBombAnim,40,40,4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
            bombBeep = new AnimatedSprite(SpriteSheet.remotePierceBombBeep, 40, 40, 2, 2);
        }
    }

    public void updateVelocity(int xa, int ya)
    {
        this.nx = xa;
        this.ny = ya;
    }

    public void update()
    {
        super.update();
    }

    @Override
    public void loop()
    {
        if(bx+bounds.x < level.minX)
        {
            Projectile p = new PierceBomb(level.maxX-bounds.x,this.by,(int)z-1, this.fires, remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
        else if(bx+bounds.x > level.maxX)
        {
            Projectile p = new PierceBomb(level.minX,this.by,(int)z-1, this.fires, remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }

        if(by+bounds.y < level.minY)
        {
            Projectile p = new PierceBomb(this.bx,level.maxY-1,this.z-1, this.fires, remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
        else if(by+bounds.y > level.maxY)
        {
            Projectile p = new PierceBomb(this.bx,level.minY,this.z-1, this.fires, remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
    }

    // @Override
    // public void move()
    // {
        // if(entityCollision(nx,ny,z) >= 2 && rolling)
        // {
            // rolling = false;
            // bx = (((bx+22)/32)*32)-4;
            // by = (((by+26)/32)*32)-3;
        // }

        // if(hopping && hopTime >= 0)
        // {
            // hop(dirX, dirY, forced, forceMulti);
        // }

        // if(rolling)
        // {
            // roll(dirX, dirY);
        // }

        // if(hopTime <= 0)
        // {
            // bx = (((bx+22)/32)*32)-4;
            // by = (((by+26)/32)*32)-3;
            // x = (((bx+22)/32)*32)-4;
            // y = (((by+26)/32)*32)-3;
        // }
        // if((hopTime <= 0)
        // && (((entityCompare(0, 0, z-1) != null && entityCompare(0, 0,z-1).solid())
                // || playerDetect(0,0,z-1) != null)
            // || tileCollision(this, dir.NONE, 0, 0, 0, z-1).solid()))
        // {
            // if(playerDetect(0,0,z-1) != null){playerDetect(0,0,z-1).stun();}
            // bx = (((bx+22)/32)*32)-4;
            // by = (((by+26)/32)*32)-3;
            // hopTime = 15;
            // sounds[sounds_bombBounce].play();
        // }
        // else if(hopTime <= 0 && ((entityCompare(0, 0, z-1) == null || !entityCompare(0, 0, z-1).solid()) && !tileCollision (this, dir.NONE, 0, 0, 0, z-1).solid()) && hopping)
        // {
            // hopping = false;
            // bx = (((bx+22)/32)*32)-4;
            // by = (((by+26)/32)*32)-3;
            // sprite = Sprite.Bomb0;
            // if(itemDetect(0,0, z) != null){itemDetect(0,0, z).Crush();}
        // }
    // }

    public void Detonate()
    {
        bx = (((bx+22)/32)*32)-4;
        by = (((by+26)/32)*32)-3;

        if(fires <= 3)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_pierceExplosion1));
        }
        else if(fires <= 6)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_pierceExplosion2));
        }
        else// if(fires <= 10)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_pierceExplosion2));
        }

        int firesA = fires;
        if(fires > 9)
        {
            firesA = 9;
        }
        level.add(new ParticleManager((int)(bx+bounds.x+(bounds.width/2)),(int)(by+bounds.y+(bounds.height)),z,(firesA*4),40,ParticleManager.particleType.GRAYCLOUD1));
        Projectile p = new PierceBombExplosion(bx-28,by-28,this.z,0,fires,0);
        level.add(p);
        if(player != null)
        {
            player.deployableBombs--;
        }
        remove();
    }

    public void Throw(int dirX, int dirY, int throwMulti)
    {
        int bombX = (((bx+22)/32)*32)-4;
        int bombY = (((by+26)/32)*32)-3;

        Projectile p = new PierceBomb(bombX,bombY,z, fires, this.remote, this.player, dirX, dirY, throwMulti, 15);
        level.add(p);
        remove();
    }

    public boolean solid()
    {
        if(hopping && hopTime > 0){return false;}
        else{return true;}
    }

    public void Destroy()
    {
        if(player != null)
        {
            player.deployableBombs--;
        }
        remove();
    }
}
