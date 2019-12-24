package JettersR.Entity;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Particle.*;

public class PowerBomb extends Bomb
{
    public PowerBomb(int x, int y, int z, int fires, boolean remote, Player player)
    {
        super(x,y, z, fires, remote, player);
        this.fires = 8;
        
        idleBomb = new AnimatedSprite(SpriteSheet.PowerBombAnim,40,40,4, 10);
        bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
        
        if(remote)
        {
            idleBomb = new AnimatedSprite(SpriteSheet.remotePowerBombAnim,40,40,4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
            bombBeep = new AnimatedSprite(SpriteSheet.remotePowerBombBeep, 40, 40, 2, 2);
        }

        sprite = idleBomb.getSprite();
        damage = 20;
    }

    public PowerBomb(int x, int y, int z, int fires, boolean remote, Player player, int dirX, int dirY, int forceMulti, int hopTime)
    {
        this(x, y, z, fires, remote, player);
        this.dirX = dirX;
        this.dirY = dirY;
        this.forceMulti = forceMulti;
        this.hopTime = hopTime;
        
        idleBomb = new AnimatedSprite(SpriteSheet.PowerBombAnim,40,40,4, 10);
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

    public PowerBomb(int x, int y, int z, int fires, boolean remote, Player player, Player player2, int dirX, int dirY)//Used specifically for Bomber Shoot
    {
        super(x,y, z, fires, remote, player, player2, dirX, dirY);
        
        idleBomb = new AnimatedSprite(SpriteSheet.PowerBombAnim,40,40,4, 10);
        bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
        
        if(remote)
        {
            idleBomb = new AnimatedSprite(SpriteSheet.remotePowerBombAnim,40,40,4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
            bombBeep = new AnimatedSprite(SpriteSheet.remotePowerBombBeep, 40, 40, 2, 2);
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
            Projectile p = new PowerBomb(level.maxX-bounds.x,this.by,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
        else if(bx+bounds.x > level.maxX)
        {
            Projectile p = new PowerBomb(level.minX,this.by,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
        
        if(by+bounds.y < level.minY)
        {
            Projectile p = new PowerBomb(this.bx,level.maxY-1,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
        else if(by+bounds.y > level.maxY)
        {
            Projectile p = new PowerBomb(this.bx,level.minY,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
    }

    public void Detonate()
    {
        bx = (((bx+22)/32)*32)-4;
        by = (((by+26)/32)*32)-3;

        Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_explosion3));

        level.add(new ParticleManager((int)(bx+bounds.x+(bounds.width/2)),(int)(by+bounds.y+(bounds.height)),z,(fires*4),40,ParticleManager.particleType.GRAYCLOUD1));
        Projectile p = new BombExplosion(bx-28,by-28,this.z,0,fires,0);
        level.add(p);
        if(player != null)
        {
            player.deployableBombs--;
            player.haveBombType[player.type_powerBomb] = true;
        }
        remove();
    }

    public void Throw(int dirX, int dirY, int throwMulti)
    {
        int bombX = (((bx+22)/32)*32)-4;
        int bombY = (((by+26)/32)*32)-3;

        Projectile p = new PowerBomb(bombX,bombY,this.z, fires, this.remote, this.player, dirX, dirY, throwMulti, 15);
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
            player.haveBombType[player.type_powerBomb] = true;
        }
        remove();
    }
}
