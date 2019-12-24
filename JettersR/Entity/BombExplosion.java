package JettersR.Entity;

import java.awt.*;
import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Entity.Statics.*;
import JettersR.Entity.Items.*;
import JettersR.Entity.Mob.*;

public class BombExplosion extends Projectile
{    
    public int anim = 0;//For animating frames
    public boolean flag = false;
    public boolean hazard = false;
    public boolean hazardFlag = false;
    public int fires;
    public int dirNum;//Direction 0 = Center, 1 = Right, 2 = Down, 3 = Left, 4 = Up

    public AnimatedSprite center = new AnimatedSprite(SpriteSheet.Fire_CENTER,96,96,9, 3,false);
    public AnimatedSprite up = new AnimatedSprite(SpriteSheet.Fire_UP,96,96,9, 3,false);
    public AnimatedSprite right = new AnimatedSprite(SpriteSheet.Fire_RIGHT,96,96,9, 3,false);
    public AnimatedSprite down = new AnimatedSprite(SpriteSheet.Fire_DOWN,96,96,9, 3,false);
    public AnimatedSprite left = new AnimatedSprite(SpriteSheet.Fire_LEFT,96,96,9, 3,false);
    public AnimatedSprite upEND = new AnimatedSprite(SpriteSheet.Fire_UPend,96,96,9, 3,false);
    public AnimatedSprite rightEND = new AnimatedSprite(SpriteSheet.Fire_RIGHTend,96,96,9, 3,false);
    public AnimatedSprite downEND = new AnimatedSprite(SpriteSheet.Fire_DOWNend,96,96,9, 3,false);
    public AnimatedSprite leftEND = new AnimatedSprite(SpriteSheet.Fire_LEFTend,96,96,9, 3,false);

    public BombExplosion(int x, int y, int z, int dirNum, int fires, int delay)
    {
        super(x, y, z);
        this.fires = fires;
        this.dirNum = dirNum;
        this.fires = fires;
        this.anim -= delay;//The delay value allows explosions to have a "Travel Time" feel. Allows players to narrowly escape incoming Explosions.

        bounds.x = 32;
        bounds.y = 31;
        bounds.width = 31;
        bounds.height = 31;

        //damage = 20;
    }
    
    @Override
    public String getClassName(){return "BombExplosion";}
    public static String className(){return "BombExplosion";}

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
                        Projectile p = new BombExplosion((int)(x)+(32*i),(int)(y),this.z,1, fires,i);//Starting Explosion projectile

                        if(i == fires || (collisionType(eCols, CollisionType.BREAKABLE) || isolatedCollision(eCols, Bomb.className())))
                        {p = new BombExplosion((int)(x)+(32*i),(int)(y),this.z,5, fires,i);level.add(p);break;}
                        level.add(p);//Makes it a tail explosion if it is on top of a breakable-solid entity or if the for-loop is on the last fire
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
                        Projectile p = new BombExplosion((int)(x),(int)(y)+(32*i),this.z,2, fires,i);

                        if(i == fires || (collisionType(eCols, CollisionType.BREAKABLE) || isolatedCollision(eCols, Bomb.className())))
                        {p = new BombExplosion((int)(x),(int)(y)+(32*i),this.z,6,fires,i);level.add(p);break;}
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
                        Projectile p = new BombExplosion((int)x-(32*i),(int)y,this.z,3, fires,i);

                        if(i == fires || (collisionType(eCols, CollisionType.BREAKABLE) || isolatedCollision(eCols, Bomb.className())))
                        {p = new BombExplosion((int)(x)-(32*i),(int)(y),this.z,7, fires,i);level.add(p);break;}
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
                        Projectile p = new BombExplosion((int)x,(int)y-(32*i),this.z,4, fires,i);

                        if(i == fires || (collisionType(eCols, CollisionType.BREAKABLE) || isolatedCollision(eCols, Bomb.className())))
                        {p = new BombExplosion((int)(x),(int)(y)-(32*i),this.z,8, fires,i);level.add(p); break;}
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

    public void update()
    {
        anim++;
        createFire();
        if(anim >= 0)
        {
            if(dirNum == 0){center.update();}
            else if(dirNum == 1){right.update();}
            else if(dirNum == 2){down.update();}
            else if(dirNum == 3){left.update();}
            else if(dirNum == 4){up.update();}
            else if(dirNum == 5){rightEND.update();}
            else if(dirNum == 6){downEND.update();}
            else if(dirNum == 7){leftEND.update();}
            else if(dirNum == 8){upEND.update();}
        }
        if (anim >= 4 && anim < 22)
        {
            checkSoftCollision();
        }

        if(!hazardFlag)
        {
            if(anim > 1 && anim < 22)
            {
                hazard = true;
            }
            else{hazard = false;}
        }
        else{hazard = false;}

        if (anim >= 26)
        {
            remove();
        }
    }

    public void checkSoftCollision()
    {
        Entity[] eCol = entityCollisions(0, 0, z);
        if(isolatedCollision(eCol, SoftBlock.className()))
        {
            isolatedEntity(eCol, SoftBlock.className()).Destroy();
            hazardFlag = true;
        }
        if(itemDetect(0,0,z) != null)
        {
            itemDetect(0,0,z).Destroy();//Allows explosions to destory items
        }
    }

    @Override
    public boolean hazard()
    {
        return hazard;
    }

    public void setSprite()
    {
        if(dirNum == 0){sprite = center.getSprite();}
        else if(dirNum == 1){sprite = right.getSprite();}
        else if(dirNum == 2){sprite = down.getSprite();}
        else if(dirNum == 3){sprite = left.getSprite();}
        else if(dirNum == 4){sprite = up.getSprite();}
        else if(dirNum == 5){sprite = rightEND.getSprite();}
        else if(dirNum == 6){sprite = downEND.getSprite();}
        else if(dirNum == 7){sprite = leftEND.getSprite();}
        else if(dirNum == 8){sprite = upEND.getSprite();}
    }

    public void render(Screen screen)
    {
        if(anim >= 0)
        {
            setSprite();
            screen.renderSprite((int)(x),(int)(y-(z*16)), sprite, true);
        }
        //if(hazard){screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFFFF009B, true);}
    }

    public void renderScale(Screen screen, float scale)
    {
        if(anim >= 0)
        {
            setSprite();
            screen.renderSprite((int)(x*scale),(int)((y-(z*16))*scale), Sprite.scale(sprite, scale), true);
        }
    }
}