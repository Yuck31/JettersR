package JettersR.Entity;

import java.awt.*;
import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Entity.Statics.*;
import JettersR.Entity.Items.*;
import JettersR.Entity.Mob.*;

public class PierceBombExplosion extends BombExplosion
{
    public PierceBombExplosion(int x, int y, int z, int dirNum, int fires, int delay)
    {
        super(x, y, z, dirNum, fires, delay);

        //damage = 20;
        center = new AnimatedSprite(SpriteSheet.pierceFire_CENTER,96,96,9, 3,false);
        up = new AnimatedSprite(SpriteSheet.pierceFire_UP,96,96,9, 3,false);
        right = new AnimatedSprite(SpriteSheet.pierceFire_RIGHT,96,96,9, 3,false);
        down = new AnimatedSprite(SpriteSheet.pierceFire_DOWN,96,96,9, 3,false);
        left = new AnimatedSprite(SpriteSheet.pierceFire_LEFT,96,96,9, 3,false);
        upEND = new AnimatedSprite(SpriteSheet.pierceFire_UPend,96,96,9, 3,false);
        rightEND = new AnimatedSprite(SpriteSheet.pierceFire_RIGHTend,96,96,9, 3,false);
        downEND = new AnimatedSprite(SpriteSheet.pierceFire_DOWNend,96,96,9, 3,false);
        leftEND = new AnimatedSprite(SpriteSheet.pierceFire_LEFTend,96,96,9, 3,false);
    }

    public void createFire()
    {
        if(dirNum == 0 && flag == false)
        {
            for(byte i = 1; i <= fires; i++)//Right Fires
            {
                Entity[] eCols = entityCollisions((i*32), 0, z);
                if((!tileCollision(this, dir.RIGHT, 0, (i*32), 0, z).solid() && !tileCollision(this, dir.RIGHT, 1, (i*32), 0, z).solid())
                && (!floorCollision(this, dir.RIGHT, 0, (i*32), 0, z).isSlope()
                    || (floorCollision(this, dir.RIGHT, 0, (i*32), 0, z).getDir() == Tile.Direction.RIGHT || floorCollision(this, dir.RIGHT, 0, (i*32), 0, z).getDir() == Tile.Direction.LEFT)))
                {
                    if((collisionType(eCols, CollisionType.BREAKABLE) || !collisionType(eCols, CollisionType.SOLID))
                    || isolatedCollision(eCols, Bomb.className()))
                    {
                        Projectile p = new PierceBombExplosion((int)(x)+(32*i),(int)(y),this.z,1, fires,i);
                        if(i == fires){p = new PierceBombExplosion((int)(x)+(32*i),(int)(y),this.z,5, fires,i);level.add(p);break;}
                        level.add(p);
                    }
                    else
                    {break;}
                }
                else
                {break;}//End fire generation on tile collision
            }
            for(byte i = 1; i <= fires; i++)//Down Fires
            {
                Entity[] eCols = entityCollisions(0,(i*32),z);
                if((!tileCollision(this, dir.DOWN, 0, 0, (i*32), z).solid() && !tileCollision(this, dir.DOWN, 1, 0,  (i*32), z).solid())
                && (!floorCollision(this, dir.DOWN, 0, 0, (i*32), z).isSlope()
                    || (floorCollision(this, dir.DOWN, 0, 0, (i*32), z).getDir() == Tile.Direction.DOWN || floorCollision(this, dir.DOWN, 0, 0, (i*32), z).getDir() == Tile.Direction.UP)))
                {
                    if((collisionType(eCols, CollisionType.BREAKABLE) || !collisionType(eCols, CollisionType.SOLID))
                    || isolatedCollision(eCols, Bomb.className()))
                    {
                        Projectile p = new PierceBombExplosion((int)(x),(int)(y)+(32*i),this.z,2, fires,i);
                        if(i == fires){p = new PierceBombExplosion((int)(x),(int)(y)+(32*i),this.z,6,fires,i);level.add(p);break;}
                        level.add(p);
                    }
                    else
                    {break;}
                }
                else
                {break;}
            }
            for(byte i = 1; i <= fires; i++)//Left Fires
            {
                Entity[] eCols = entityCollisions(-(i*32), 0,z);
                if((!tileCollision(this, dir.LEFT, 0, -(i*32), 0, z).solid() && !tileCollision(this, dir.LEFT, 1, -(i*32), 0, z).solid())
                && (!floorCollision(this, dir.LEFT, 0, -(i*32), 0, z).isSlope()
                    || (floorCollision(this, dir.LEFT, 0, -(i*32), 0, z).getDir() == Tile.Direction.LEFT || floorCollision(this, dir.LEFT, 0, -(i*32), 0, z).getDir() == Tile.Direction.RIGHT)))
                {
                    if((collisionType(eCols, CollisionType.BREAKABLE) || !collisionType(eCols, CollisionType.SOLID))
                    || isolatedCollision(eCols, Bomb.className()))
                    {
                        Projectile p = new PierceBombExplosion((int)x-(32*i),(int)y,this.z,3, fires,i);
                        if(i == fires){p = new PierceBombExplosion((int)(x)-(32*i),(int)(y),this.z,7, fires,i);level.add(p);break;}
                        level.add(p);
                    }
                    else
                    {break;}
                }
                else
                {break;}
            }
            for(byte i = 1; i <= fires; i++)//Up Fires
            {
                Entity[] eCols = entityCollisions(0,-(i*32),z);
                if((!tileCollision(this, dir.UP, 0, 0, -(i*32), z).solid() && !tileCollision(this, dir.UP, 1, 0, -(i*32), z).solid())
                && (!floorCollision(this, dir.UP, 0, 0, -(i*32), z).isSlope()
                    || (floorCollision(this, dir.UP, 0, 0, -(i*32), z).getDir() == Tile.Direction.DOWN || floorCollision(this, dir.UP, 0, 0, -(i*32), z).getDir() == Tile.Direction.UP)))
                {
                    if((collisionType(eCols, CollisionType.BREAKABLE) || !collisionType(eCols, CollisionType.SOLID))
                    || isolatedCollision(eCols, Bomb.className()))
                    {
                        Projectile p = new PierceBombExplosion((int)x,(int)y-(32*i),this.z,4, fires,i);
                        if(i == fires){p = new PierceBombExplosion((int)(x),(int)(y)-(32*i),this.z,8, fires,i);level.add(p);break;}
                        level.add(p);
                    }
                    else
                    {break;}
                }
                else
                {break;}
            }
            flag = true;
        }
    }
}