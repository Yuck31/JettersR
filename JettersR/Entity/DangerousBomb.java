package JettersR.Entity;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Particle.*;

public class DangerousBomb extends Bomb
{
    private boolean converted = false;

    public DangerousBomb(int x, int y, int z, int fires, boolean remote, Player player, boolean converted)
    {
        super(x,y, z, fires, remote, player);
        this.x = x;
        this.y = y;
        this.bx = x;
        this.by = y;
        this.z = z;
        this.fires = fires - 1;
        this.player = player;
        this.converted = converted;
        if(this.fires < 1){fires = 1;}
        else if(this.fires > 3){this.fires = 3;}
        if(this.converted && this.fires < 2){this.fires = 2;}

        idleBomb = new AnimatedSprite(SpriteSheet.DangerousBomb,40,40,4, 10);
        bombPunched = new AnimatedSprite(SpriteSheet.DangerousBombPunched,40,40,15, 1);

        if(remote)
        {
            idleBomb = new AnimatedSprite(SpriteSheet.remoteDangerousBombAnim,40,40, 4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.DangerousBombPunched,40,40,15, 1);
            bombBeep = new AnimatedSprite(SpriteSheet.remoteDangerousBombBeep, 40, 40, 2, 2);
        }

        bounds.x = 4;
        bounds.y = 3;
        bounds.width = 31;
        bounds.height = 31;

        this.nx = 0;
        this.ny = 0;

        damage = 20;
        sprite = Sprite.DangerousBomb0;
    }

    public DangerousBomb(int x, int y, int z, int fires, boolean remote, Player player, int dirX, int dirY, int forceMulti, boolean converted, int hopTime)
    {
        this(x, y, z, fires, remote, player, converted);
        this.dirX = dirX;
        this.dirY = dirY;
        this.forceMulti = forceMulti;
        this.converted = converted;
        this.hopTime = hopTime;

        damage = 20;
        sprite = Sprite.DangerousBomb0;
        hop(dirX, dirY, true, forceMulti);
    }

    public DangerousBomb(int x, int y, int z, int fires, boolean remote, Player player, Player player2, int dirX, int dirY)//Used specifically for Bomber Shoot
    {
        super(x,y, z, fires, remote, player, player2, dirX, dirY);

        if(this.converted && this.fires < 2){this.fires = 2;}

        if(remote)
        {
            idleBomb = new AnimatedSprite(SpriteSheet.remoteDangerousBomb,40,40,4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.DangerousBombPunched,40,40,15, 1);
            bombBeep = new AnimatedSprite(SpriteSheet.remoteDangerousBombBeep, 40, 40, 2, 2);
        }
        else
        {
            idleBomb = new AnimatedSprite(SpriteSheet.DangerousBomb,40,40,4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.DangerousBombPunched,40,40,15, 1);
        }

        shot = true;
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
            Projectile p = new DangerousBomb(level.maxX-bounds.x,this.by, z-1, this.fires+1, this.remote, this.player, dirX, dirY, forceMulti, converted, this.hopTime);
            level.add(p);
            remove();
        }
        else if(bx+bounds.x > level.maxX)
        {
            Projectile p = new DangerousBomb(level.minX,this.by, z-1, this.fires+1, this.remote, this.player, dirX, dirY, forceMulti, converted, this.hopTime);
            level.add(p);
            remove();
        }

        if(by+bounds.y < level.minY)
        {
            Projectile p = new DangerousBomb(this.bx,level.maxY-1, z-1, this.fires+1, this.remote, this.player, dirX, dirY, forceMulti, converted, this.hopTime);
            level.add(p);
            remove();
        }
        else if(by+bounds.y > level.maxY)
        {
            Projectile p = new DangerousBomb(this.bx,level.minY, z-1, this.fires+1, this.remote, this.player, dirX, dirY, forceMulti, converted, this.hopTime);
            level.add(p);
            remove();
        }
    }

    public void Detonate()
    {
        bx = (((bx+22)/32)*32)-4;
        by = (((by+26)/32)*32)-3;

        if(fires <= 1)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_dangerousExplosion1));
        }
        else if(fires <= 2)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_dangerousExplosion2));
        }
        else
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_dangerousExplosion3));
        }

        level.add(new ParticleManager((int)(bx+bounds.x+(bounds.width/2)),(int)(by+bounds.y+(bounds.height)),z,(fires*4),40,ParticleManager.particleType.GRAYCLOUD1));
        Projectile p = new DangerousBombExplosion(bx-28, by-28,this.z,0,fires,0);
        level.add(p);
        if(player != null)
        {
            player.deployableBombs--;
            if(!converted)player.haveBombType[player.type_dangerousBomb] = true;
        }
        remove();
    }

    public void Throw(int dirX, int dirY, int throwMulti)
    {
        int bombX = (((bx+22)/32)*32)-4;
        int bombY = (((by+26)/32)*32)-3;

        Projectile p = new DangerousBomb(bombX,bombY,this.z, fires + 1, this.remote, player, dirX, dirY, throwMulti, converted, 15);
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
            if(!converted)player.haveBombType[player.type_dangerousBomb] = true;
        }
        remove();
    }
}
