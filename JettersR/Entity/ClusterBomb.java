package JettersR.Entity;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Particle.*;

public class ClusterBomb extends Bomb
{
    private int anim = 0;//For animating frames
    private int time = 180;

    public int direction = 0;//Direction 0 = Up, 1 = Right, 2 = Down, 3 = Left

    public AnimatedSprite[] idleClusterBomb = 
        {
            new AnimatedSprite(SpriteSheet.ClusterBombAnimUp, 41, 41, 4, 10),
            new AnimatedSprite(SpriteSheet.ClusterBombAnimRight, 41, 41, 4, 10),
            new AnimatedSprite(SpriteSheet.ClusterBombAnimDown, 41, 41, 4, 10),
            new AnimatedSprite(SpriteSheet.ClusterBombAnimLeft, 41, 41, 4, 10),
        };

    public AnimatedSprite[] clusterBombBeep = 
        {
            new AnimatedSprite(SpriteSheet.remoteClusterBombUpBeep, 41, 41, 2, 2),
            new AnimatedSprite(SpriteSheet.remoteClusterBombRightBeep, 41, 41, 2, 2),
            new AnimatedSprite(SpriteSheet.remoteClusterBombDownBeep, 41, 41, 2, 2),
            new AnimatedSprite(SpriteSheet.remoteClusterBombLeftBeep, 41, 41, 2, 2),
        };

    public ClusterBomb(int x, int y, int z, int fires, boolean remote, Player player, int direction)
    {
        super(x,y,z, fires, remote, player);
        this.x = x;
        this.y = y;
        this.bx = x;
        this.by = y;
        this.z = z;
        this.fires = fires;
        this.player = player;
        this.direction = direction;

        bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);

        if(remote)
        {
            idleClusterBomb[0] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimUp, 41, 41, 4, 10);
            idleClusterBomb[1] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimRight, 41, 41, 4, 10);
            idleClusterBomb[2] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimDown, 41, 41, 4, 10);
            idleClusterBomb[3] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimLeft, 41, 41, 4, 10);
        }

        bounds.x = 4;
        bounds.y = 3;
        bounds.width = 31;
        bounds.height = 31;

        this.nx = 0;
        this.ny = 0;

        damage = 20;
        sprite = Sprite.Bomb0;
    }

    public ClusterBomb(int x, int y, int z, int fires, boolean remote, Player player, int dirX, int dirY, int forceMulti, int direction, int hopTime)
    {
        this(x, y, z, fires, remote, player, direction);
        this.dirX = dirX;
        this.dirY = dirY;
        this.forceMulti = forceMulti;
        this.hopTime = hopTime;

        bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
        bounds.x = 4;
        bounds.y = 3;
        bounds.width = 31;
        bounds.height = 31;

        this.nx = 0;
        this.ny = 0;

        damage = 20;
        sprite = Sprite.Bomb0;
        hop(dirX, dirY, true, forceMulti);
    }

    public ClusterBomb(int x, int y, int z, int fires, boolean remote, Player player, Player player2, int dirX, int dirY, int direction)//Used specifically for Bomber Shoot
    {
        super(x,y, z, fires, remote, player, player2, dirX, dirY);
        this.direction = direction;

        bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);

        if(remote)
        {
            idleClusterBomb[0] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimUp, 41, 41, 4, 10);
            idleClusterBomb[1] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimRight, 41, 41, 4, 10);
            idleClusterBomb[2] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimDown, 41, 41, 4, 10);
            idleClusterBomb[3] = new AnimatedSprite(SpriteSheet.remoteClusterBombAnimLeft, 41, 41, 4, 10);
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
        if(activated){clusterBombBeep[direction].update();}
        else{idleClusterBomb[direction].update();}
    }

    @Override
    public void loop()
    {
        if(bx+bounds.x < level.minX)
        {
            Projectile p = new ClusterBomb(level.maxX-bounds.x,this.by,z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.direction, this.hopTime);
            level.add(p);
            remove();
        }
        else if(bx+bounds.x > level.maxX)
        {
            Projectile p = new ClusterBomb(level.minX,this.by,z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.direction, this.hopTime);
            level.add(p);
            remove();
        }

        if(by+bounds.y < level.minY)
        {
            Projectile p = new ClusterBomb(this.bx,level.maxY-1,z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.direction, this.hopTime);
            level.add(p);
            remove();
        }
        else if(by+bounds.y > level.maxY)
        {
            Projectile p = new ClusterBomb(this.bx,level.minY,z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.direction, this.hopTime);
            level.add(p);
            remove();
        }
    }

    public void Detonate()
    {
        bx = (((bx+22)/32)*32)-4;//Centers position for proper explosions
        by = (((by+26)/32)*32)-3;

        if(fires <= 3)
        {
            Game.am.add(AudioManager.sounds_explosion1);
        }
        else if(fires <= 6)
        {
            Game.am.add(AudioManager.sounds_explosion2);
            level.shake(1, 1, 30);
        }
        else// if(fires <= 10)
        {
            Game.am.add(AudioManager.sounds_explosion3);
            level.shake(2, 2, 30);
        }

        level.add(new ParticleManager((int)(bx+bounds.x+(bounds.width/2)),(int)(by+bounds.y+(bounds.height)),z,(fires*4),40,ParticleManager.particleType.GRAYCLOUD1));
        Projectile p = new BombExplosion(bx-28, by-28,this.z,0,fires,0);
        level.add(p);
        if(player != null)
        {
            player.deployableBombs--;
            player.haveBombType[player.type_clusterBomb] = true;
        }

        Projectile bomb = new Bomb(this.bx, this.by,this.z, fires, false, null, 0, 0, 2, 15);
        if(direction != 0)//If direction is not Up
        {
            bomb = new Bomb(this.bx, this.by,this.z,fires, false, null, 0, 1, 2, 15);//Then we can instantiate a down Bomb
            level.add(bomb);
        }
        if(direction != 1)//If direction is not Right
        {
            bomb = new Bomb(this.bx, this.by,this.z,fires, false, null, -1, 0, 2, 15);//Then we can instantiate a left Bomb
            level.add(bomb);
        }

        if(direction != 2)//If direction is not Down
        {
            bomb = new Bomb(this.bx, this.by,this.z,fires, false, null, 0, -1, 2, 15);//Then we can instantiate an up Bomb
            level.add(bomb);
        }

        if(direction != 3)//If direction is not Left
        {
            bomb = new Bomb(this.bx, this.by,this.z,fires, false, null, 1, 0, 2, 15);//Then we can instantiate a right Bomb
            level.add(bomb);
        }
        remove();
    }

    public void Throw(int dirX, int dirY, int throwMulti)
    {
        int bombX = (((bx+22)/32)*32)-4;
        int bombY = (((by+26)/32)*32)-3;

        Projectile p = new ClusterBomb(bombX,bombY,this.z, fires, this.remote, this.player, dirX, dirY, throwMulti, player2.dirNum, 15);
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
            player.haveBombType[player.type_clusterBomb] = true;
        }
        remove();
    }

    public void render(Screen screen)
    {
        if(hopping)
        {
            sprite = bombPunched.getSprite();
            screen.renderSprite((int)x,(int)(y - 6) - ((z) * 16), sprite, true);
        }
        else if(activated)
        {
            sprite = clusterBombBeep[direction].getSprite();
            screen.renderSprite((int)x,(int)y - (z * 16), sprite, true);
        }
        else if(!hopping && !beingHeld)
        {
            sprite = idleClusterBomb[direction].getSprite();
            screen.renderSprite((int)x,(int)y - (z * 16), sprite, true);
        }
    }

    public void renderScale(Screen screen, float scale)
    {
        if(hopping)
        {
            sprite = bombPunched.getSprite();
            screen.renderSprite((int)(x*scale),(int)(((y-6)-((z)*16))*scale), Sprite.scale(sprite,scale), true);
        }
        else if(activated)
        {
            sprite = clusterBombBeep[direction].getSprite();
            screen.renderSprite((int)(x*scale),(int)((y-(z*16))*scale), Sprite.scale(sprite,scale), true);
        }
        else if(!hopping && !beingHeld)
        {
            sprite = idleClusterBomb[direction].getSprite();
            screen.renderSprite((int)(x*scale),(int)((y-(z*16))*scale), Sprite.scale(sprite,scale), true);
        }
    }
}
