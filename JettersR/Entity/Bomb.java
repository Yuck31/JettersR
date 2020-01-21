package JettersR.Entity;

import JettersR.*;
import JettersR.Audio.*;
import JettersR.GameStates.*;
import JettersR.Entity.Mob.*;
import JettersR.Entity.Particle.*;

public class Bomb extends Projectile
{
    public Entity[] eCols;

    public boolean remote = false;
    public boolean activated = false;
    public int startBounceTime = 600;
    public byte bounceCool = 3;
    public int bounceTime = startBounceTime;

    public int anim = 0;//For animating frames
    public int time = 180;
    public boolean heightFlag = false;

    public int dirX;
    public int dirY;

    public boolean beingHeld = false;

    public boolean forced = false;
    public int forceMulti = 1;

    public Player player;
    public Player player2;

    public int fires;
    int i = 0;
    public int bx, by;

    public AnimatedSprite idleBomb = new AnimatedSprite(SpriteSheet.Bomb,40,40,4,10);
    public AnimatedSprite bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15,1);
    public AnimatedSprite rollingBombX = new AnimatedSprite(SpriteSheet.BombRollX, 40, 40, 8, 2);
    public AnimatedSprite rollingBombY = new AnimatedSprite(SpriteSheet.BombRollY, 40, 40, 4, 2);
    public AnimatedSprite bombBeep = new AnimatedSprite(SpriteSheet.RemoteBombBeep, 40, 40, 2, 2);
    public Bomb(int x, int y, int z, int fires, boolean remote, Player player)
    {
        super(x, y, z);
        this.x = x;
        this.y = y;
        this.bx = x;
        this.by = y;
        this.z = z;
        this.fires = fires;
        this.remote = remote;
        this.player = player;

        if(remote)
        {
            time = 40;
            idleBomb = new AnimatedSprite(SpriteSheet.RemoteBombAnim,40,40,4, 10);
            bombPunched = new AnimatedSprite(SpriteSheet.BombPunched,40,40,15, 1);
            rollingBombX = new AnimatedSprite(SpriteSheet.RemoteBombRollX, 40, 40, 8, 2);
            rollingBombY = new AnimatedSprite(SpriteSheet.RemoteBombRollY, 40, 40, 4, 2);
        }

        bounds.x = 4;
        bounds.y = 3;
        bounds.width = 31;
        bounds.height = 31;

        this.nx = 0;
        this.ny = 0;

        //damage = 20;
        sprite = Sprite.Bomb0;
    }

    public Bomb(int x, int y, int z,  int fires, boolean remote, Player player, int dirX, int dirY, int forceMulti, int hopTime)
    {
        this(x, y, z, fires, remote, player);
        this.dirX = dirX;
        this.dirY = dirY;
        this.forceMulti = forceMulti;
        this.hopTime = hopTime;

        //damage = 20;
        sprite = Sprite.Bomb0;
        hop(dirX, dirY, true, forceMulti);
    }

    public Bomb(int x, int y, int z, int fires, boolean remote,Player player, Player player2, int dirX, int dirY)//Used specificallby for Bomber Shoot
    {
        this(x, y, z, fires, remote, player);
        this.player2 = player2;
        this.dirX = dirX;
        this.dirY = dirY;

        if(dirX != 0)
        {
            this.nx = dirX*3 + ((Math.abs(dirX)/dirX) * 6);
            if(dirX > 0){dir = dir.RIGHT;}
            else if(dirX < 0){dir = dir.LEFT;}
        }
        else{this.nx = 0;}

        if(dirY != 0)
        {
            this.ny = dirY*3 + ((Math.abs(dirY)/dirY) * 6);
            if(dirY > 0){dir = dir.DOWN;}
            else if(dirY < 0){dir = dir.UP;}
        }
        else{this.ny = 0;}

        //sprite = Sprite.Bomb0;
        shot = true;
    }

    @Override
    public String getClassName(){return "Bomb";}

    public static String className(){return "Bomb";}

    public void updateVelocity(int xa, int ya)
    {
        this.nx = xa;
        this.ny = ya;
    }

    public void update()
    {
        eCols = entityCollisions(0, 0, z);
        anim++;
        x = bx;
        y = by;
        
        if(hopping)
        {
            bounceTime--;
            bounceCool = 3;
            if(bounceTime <= 0){Detonate();}
        }
        else if(bounceCool > 3)
        {
            bounceCool--;
            if(bounceCool <= 0){bounceTime = startBounceTime; bounceCool = 3;}
        }

        loop();

        idleBomb.update();

        if(remote && !shot)
        {
            if((!activated && !beingHeld && !hopping) && player.input.remote[player.playerNum])
            {
                activated = true;
                Game.am.add(AudioManager.sounds_bombBeep);
            }

            if(activated && (!hopping && !beingHeld)){time--; hopTime = 15; bombBeep.update();}
        }

        if(!hopping && !beingHeld)
        {
            if(!remote){time--;}
            hopTime = 15;
        }
        if(hopping){hopTime--; rolling = false;}

        if(time <= 0)
        {
            Detonate();
            remove();
        }

        if(hopTime <= 0){hopTime = 0;}

        if(hazardCollision(0, 0, z))
        {
            if(time > 4){time = 4;};//If this Bomb is hit by a Hazard (explosion), make it close to exploding.
            //This allows for Chain reactions with Bomb Explosions.
            if(remote){activated = true;}
        }
        if(player != null)
        {
            if(player.input.remote[player.playerNum] && rolling)
            {
                bx = (((bx+22)/32)*32)-4;
                by = (((by+26)/32)*32)-3;
                rolling = false;
            }
        }

        if(beingHeld && !activated){holdMove(player2);}
        else if(shot){shootMove();}
        else if(!activated || hopping){move();}

        if((!floorCollision(this, dir.NONE, 0, 0, 0, z).isFloor() && !floorCollision(this, dir.NONE, 0, 0, 0, z).isSlope()) && !hopping && !beingHeld && !shot)
        {
            if(z - 1 < 0)
            {
                Detonate();
            }
            else{z -= 1;}
            if(itemDetect(0,0,z) != null){itemDetect(0,0,z).Crush();}
            if(((!floorCollision(this, dir.NONE, 0, 0, 0, z).isFloor() && tileCollision(this, dir.NONE, 0, 0, 0, z-1).solid())
            || collisionType(eCols = entityCollisions(0, 0, z-1), CollisionType.SOLID)))
            {
                Game.am.add(AudioManager.sounds_bombBounce);
                hopTime = 15;
                hop(dirX, dirY, forced, forceMulti);
            }
        }
        x = bx;
        y = by;
    }

    public void loop()
    {
        if(bx+bounds.x < level.minX)
        {
            Projectile p = new Bomb(level.maxX-bounds.x,this.by,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
        else if(bx+bounds.x > level.maxX)
        {
            Projectile p = new Bomb(level.minX,this.by,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }

        if(by+bounds.y < level.minY)
        {
            Projectile p = new Bomb(this.bx,level.maxY-1,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
        else if(by+bounds.y > level.maxY)
        {
            Projectile p = new Bomb(this.bx,level.minY,(int)z-1, this.fires, this.remote, this.player, dirX, dirY, forceMulti, this.hopTime);
            level.add(p);
            remove();
        }
    }

    public void move()
    {
        if(collisionType(eCols, CollisionType.SOLID) && rolling)
        {
            rolling = false;
            bx = (((bx+22)/32)*32)-4;
            by = (((by+26)/32)*32)-3;
        }

        if(hopping && hopTime >= 0)
        {
            hop(dirX, dirY, forced, forceMulti);
        }

        if(rolling)
        {
            roll(dirX, dirY);
        }

        if(hopTime <= 0)
        {
            bx = (((bx+22)/32)*32)-4;
            by = (((by+26)/32)*32)-3;
            x = (((bx+22)/32)*32)-4;
            y = (((by+26)/32)*32)-3;
        }
        if(hopTime <= 0
        && ((collisionType(entityCollisions(0, 0, z-1), CollisionType.SOLID) || playerDetect(0,0,z-1) != null) || tileCollision(this, dir.NONE, 0, 0, 0, z-1).solid()))
        {
            if(playerDetect(0,0,z-1) != null){playerDetect(0,0,z-1).stun();}
            bx = (((bx+22)/32)*32)-4;
            by = (((by+26)/32)*32)-3;
            hopTime = 15;
            Game.am.add(AudioManager.sounds_bombBounce);
        }
        //else if((hopTime <= 0 && (entityCollision (0, 0,z-1) < 2 && !tileCollision (0, 0, z-1).solid()) && hopping) || ((forceMulti > 1 && (hopTime <= 10 && hopTime >= 8)) && getTileFloor((int)nbx, (int)nby, z-1) < z-1))
        else if(hopTime <= 0 && (!collisionType(entityCollisions(0, 0, z-1), CollisionType.SOLID) && !tileCollision (this, dir.NONE, 0, 0, 0, z-1).solid()) && hopping)
        {
            hopping = false;
            bx = (((bx+22)/32)*32)-4;
            by = (((by+26)/32)*32)-3;
            heightFlag = false;
            sprite = Sprite.Bomb0;
            if(itemDetect(0,0,z) != null){itemDetect(0,0,z).Crush();}
        }
    }

    public void holdMove(Player player2)
    {
        bx = (int)player2.getX();
        by = (int)player2.getY();
        z = player2.getZ();
    }

    public void Detonate()
    {
        bx = (((bx+22)/32)*32)-4;
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

        int firesA = fires;
        if(fires > 9)
        {
            firesA = 9;
        }
        level.add(new ParticleManager((int)(bx+bounds.x+(bounds.width/2)),(int)(by+bounds.y+(bounds.height)),z,(firesA*4),40,ParticleManager.particleType.GRAYCLOUD1));
        Projectile p = new BombExplosion(bx-28,by-28,this.z,0,fires,0);
        level.add(p);
        if(player != null)
        {
            player.deployableBombs--;
        }
        remove();
    }

    public void pickUp(Player player)
    {
        if(!hopping || hopTime < 3)
        {
            this.player2 = player;
            beingHeld = true;
            bounds.width = 0;
            bounds.height = 0;
        }
    }

    public void Throw(int dirX, int dirY, int throwMulti)
    {
        int bombX = (((bx+22)/32)*32)-4;
        int bombY = (((by+26)/32)*32)-3;

        Projectile p = new Bomb(bombX,bombY,z, fires, this.remote, this.player, dirX, dirY, throwMulti, 15);
        level.add(p);
        remove();
    }

    public void hop(int dirX, int dirY, boolean forced, int forceMulti)
    {
        hopping = true;
        this.dirX = dirX;
        this.dirY  = dirY;
        this.forced = forced;
        this.forceMulti = forceMulti;
        nx = dirX*2*forceMulti;
        ny = dirY*2*forceMulti;
        if(!heightFlag){z = z+1; heightFlag = true;}

        if(hopTime <= 14 && !tileCollision(this, dir.NONE, 0, 0, 0, z).solid())
        {
            bx += nx;
            by += ny;
        }
        else if(tileCollision(this, dir.NONE, 0, nx, ny, z).solid())
        {
            if(this.dirX != 0){this.dirX = Math.abs(dirX) * ((Math.abs(dirX)/dirX) * -1);}
            if(this.dirY != 0){this.dirY = Math.abs(dirY) * ((Math.abs(dirY)/dirY) * -1);}
        }

        if(hopTime <= 14){bombPunched.update();}
        if(forced && hopTime <= 0)
        {
            this.dirX = (this.dirX);
            this.dirY  = (this.dirY);
            this.forced = false;
            this.forceMulti = 1;
        }
    }

    public void roll(int dirX, int dirY)
    {
        rolling = true;
        float velocityOffsetX = 0;
        float velocityOffsetY = 0;
        if(dirX != 0){velocityOffsetX = (2.5f*(Math.abs(dirX)/dirX))+1;}
        if(dirY != 0){velocityOffsetY = (2.5f*(Math.abs(dirY)/dirY))+1;}
        this.dirX = dirX;
        this.dirY  = dirY;
        this.nx = dirX*5;
        this.ny = dirY*5;

        if(entityCollision(nx, ny, z).solid()
        || playerDetect(nx,ny,z) != null
        || tileCollision(this, dir.NONE, 0, nx, ny, z).solid())
        {
            if(playerDetect(nx,ny,z) != null){playerDetect((int)nx,(int)ny,z).stun();}
            nx = 0;
            ny = 0;
            rolling = false;
            bx = (((bx+22)/32)*32)-4;
            by = (((by+26)/32)*32)-3;
        }
        else if(!tileCollision(this, dir.NONE, 0, nx, ny, z).solid())
        {
            bx += nx;
            by += ny;
        }
        if(itemDetect(0,0,z) != null){itemDetect(0,0,z).Crush();}
    }

    public void shootMove()
    {
        if((entityCollision(nx, ny, z) != player2 && entityCollision(nx,ny,z).solid())
        || (playerDetect((int)nx,(int)ny,z) != null && playerDetect((int)nx,(int)ny,z) != player2)
        || (tileCollision(this, this.dir, 0, nx, ny, z).solid() || tileCollision(this, this.dir, 1, nx, ny, z).solid()))
        {
            bx = (((bx+22)/32)*32)-4;
            by = (((by+26)/32)*32)-3;
            Detonate();
        }
        else
        {
            bx += nx;
            by += ny;
            if(itemDetect((int)nx,(int)ny,z) != null){itemDetect((int)nx,(int)ny,z).Destroy();}
        }
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

    public Player getPlayer()
    {
        return player;
    }

    public void render(Screen screen)
    {
        if(hopping)
        {
            sprite = bombPunched.getSprite();
            screen.renderSprite((int)x,(int)(y - 6) - ((z) * 16), sprite, true);
        }
        else if(!hopping && !beingHeld && !activated)
        {
            sprite = idleBomb.getSprite();
            screen.renderSprite((int)x,(int)y - (z * 16), sprite, true);
        }
        else if(activated)
        {
            sprite = bombBeep.getSprite();
            screen.renderSprite((int)x,(int)y - (z * 16), sprite, true);
        }
        //screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFFFF0099, true);
    }

    public void renderScale(Screen screen, float scale)
    {
        if(hopping)
        {
            sprite = bombPunched.getSprite();
            screen.renderSprite((int)(x*scale),(int)(((y-6)-((z)*16))*scale), Sprite.scale(sprite,scale), true);
        }
        else if(!hopping && !beingHeld && !activated)
        {
            sprite = idleBomb.getSprite();
            screen.renderSprite((int)(x*scale),(int)((y-(z*16))*scale), Sprite.scale(sprite,scale), true);
        }
        else if(activated)
        {
            sprite = bombBeep.getSprite();
            screen.renderSprite((int)(x*scale),(int)((y-(z*16))*scale), Sprite.scale(sprite,scale), true);
        }
    }
}
