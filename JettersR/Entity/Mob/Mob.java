package JettersR.Entity.Mob;

import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;
import JettersR.Entity.Particle.*;

import java.util.ArrayList;
import java.util.List;
public abstract class Mob extends Entity//Mobs are a subType of Entities
{
    protected boolean moving = false;
    protected int health;
    
    public boolean softBlockPass = false;//Used exclusivly for the Soft Block Pass
    public boolean bombPass = false;//Used exclusivly for the Bomb Pass
    public boolean falling = false;//Used for falling to a lower floor
    
    public AnimatedSprite front, left, right, back, holdFront, holdLeft, holdRight, holdBack;
    public Sprite bounceFront, bounceLeft, bounceRight, bounceBack;
    public AnimatedSprite frontIdle, leftIdle, rightIdle, backIdle, holdFrontIdle, holdLeftIdle, holdRightIdle, holdBackIdle;
    public AnimatedSprite death;
    
    public Mob(int x, int y, int z)
    {
        super(x,y,z);
    }
    
    public void setDir(double xa, double ya)
    {
        //System.out.println("Size: " + level.getProjectiles().size());
        if(xa > 0)//Moving Right
        {
            dir = dir.RIGHT;
        }
        if(xa < 0)//Moving Left
        {
            dir = dir.LEFT;
        }
        if(ya > 0)//Moving Down
        {
            dir = dir.DOWN;
        }
        if(ya < 0)//Moving Up
        {
            dir = dir.UP;
        }
    }
    
    public void move(double xa, double ya)
    {
        Entity[] eCols = entityCollisions(0, 0, z);
        Entity[] eColsX = entityCollisions(xa, 0, z);
        Entity[] eColsY = entityCollisions(0, ya, z);
        Entity e = entityCollision(0, 0, z);
        Entity eX = entityCollision(xa, 0, z);
        Entity eY = entityCollision(0, ya, z);
        boolean eS = collisionType(eCols, CollisionType.SOLID) & !falling;
        boolean eXS = collisionType(eColsX, CollisionType.SOLID) & !falling;
        boolean eYS = collisionType(eColsY, CollisionType.SOLID) & !falling;

        Direction hDir = Direction.LEFT;
        Direction vDir = Direction.UP;

        if(ya != 0)//Vertical
        {
            if(ya < 0)
            {
                vDir = Direction.UP;
            }
            else if(ya > 0)
            {
                vDir = Direction.DOWN;
            }
            if(!falling || slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope())
            {
                back.update();
                holdBack.update();
                front.update();
                holdFront.update();
            }

            if((!tileCollision(this, vDir, 0, 0, ya, z).solid() && !tileCollision(this, vDir, 1, 0, ya, z).solid())
            && (((!slopeCollision(this, vDir, 0, 0, ya, z).isSlope() && !slopeCollision(this, vDir, 1, 0, ya, z).isSlope())
            || ((slopeCollision(this, vDir, 0, 0, ya, z).dir == Tile.Direction.UP && slopeCollision(this, vDir, 1, 0, ya, z).dir == Tile.Direction.UP)
            || (slopeCollision(this, vDir, 0, 0, ya, z).dir == Tile.Direction.DOWN && slopeCollision(this, vDir, 1, 0, ya, z).dir == Tile.Direction.DOWN)))
            || zOffset > 0))
            {
                if(!eS && !eYS)//if(player isn't standing on or about to move into a solid entity){move up}
                {
                    y += ya;
                }
                else if(e.solid() || eY.solid())//otherwise, if(player is standing on or about to walk into an entity)
                {
                    if((softBlockPass && (isolatedCollision(eCols, SoftBlock.className()) || isolatedCollision(eColsY, SoftBlock.className())))//if player is in a soft-block and has soft-block pass
                    || (bombPass && (isolatedCollision(eCols, Bomb.className()) || isolatedCollision(eColsY, Bomb.className())))//or the player is in a bomb and has bomb pass
                    || e == eY || !eY.solid())
                    {
                        y += ya;//MOVE
                    }
                }
            }
            else if(!falling && !bouncing)
            {
                if(tileCollision(this, dir.UP, 0, 0, ya, z).solid() && tileCollision(this, dir.UP, 1, 0, ya, z).solid())
                {y = ((int)((y + bounds.y)/32) * 32) - bounds.y;}
                else if(tileCollision(this, dir.DOWN, 0, 0, ya, z).solid() && tileCollision(this, dir.DOWN, 1, 0, ya, z).solid())
                {y = ((int)(((y + bounds.y + bounds.height) / 32) - 1) * 32) + (bounds.width+2);}
            }
            if(xa == 0)
            {
                if(((!tileCollision(this, vDir, 0, 0, ya, z).solid() && tileCollision(this, vDir, 1, 0, ya, z).solid())
                    || (!slopeCollision(this, vDir, 0, 0, ya, z).horizontal && slopeCollision(this, vDir, 1, 0, ya, z).horizontal))
                || (slopeCollision(this, vDir, 0, 0, ya, z).vertical && !slopeCollision(this, vDir, 1, 0, ya, z).vertical))
                {
                    if(((!entityCollision(0, 0, z).solid() && !entityCollision(Math.abs(ya)*-1, 0, z).solid())
                        || (entityCollision(0, 0, z) == entityCollision(Math.abs(ya)*-1, 0, z)))
                    || ((isolatedCollision(entityCollisions(0, 0, z), SoftBlock.className()) || isolatedCollision(entityCollisions(Math.abs(ya)*-1, 0, z), SoftBlock.className()))
                        && softBlockPass))
                    {
                        xa = Math.abs(ya)*-1;
                        //x -= Math.abs(ya);
                    }
                }
                else if(((tileCollision(this, vDir, 0, 0, ya, z).solid() && !tileCollision(this, vDir, 1, 0, ya, z).solid())
                    || (slopeCollision(this, vDir, 0, 0, ya, z).horizontal && !slopeCollision(this, vDir, 1, 0, ya, z).horizontal))
                || (!slopeCollision(this, vDir, 0, 0, ya, z).vertical && slopeCollision(this, vDir, 1, 0, ya, z).vertical))
                {
                    if((!entityCollision(Math.abs(ya), 0, z).solid() || (entityCollision(0, 0, z) == entityCollision(Math.abs(ya), 0, z)))
                    || (isolatedCollision(entityCollisions(Math.abs(ya), 0, z), SoftBlock.className()) && softBlockPass))
                    {
                        xa = Math.abs(ya);
                        //x += Math.abs(ya);
                    }
                }
            }
        }

        if(xa != 0)//Horizontal
        {
            if(xa < 0)
            {
                hDir = Direction.LEFT;
            }
            else if(xa > 0)
            {
                hDir = Direction.RIGHT;
            }
            if(!falling || slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope())
            {
                left.update();
                holdLeft.update();
                right.update();
                holdRight.update();
            }

            if((!tileCollision(this, hDir, 0, xa, 0, z).solid() && !tileCollision(this, hDir, 1, xa, 0, z).solid())
            && (((!slopeCollision(this, hDir, 0, xa, 0, z).isSlope() && !slopeCollision(this, hDir, 1, xa, 0, z).isSlope())
            || ((slopeCollision(this, hDir, 0, xa, 0, z).dir == Tile.Direction.LEFT && slopeCollision(this, hDir, 1, xa, 0, z).dir == Tile.Direction.LEFT)
            || (slopeCollision(this, hDir, 0, xa, 0, z).dir == Tile.Direction.RIGHT && slopeCollision(this, hDir, 1, xa, 0, z).dir == Tile.Direction.RIGHT)))
            || zOffset > 0))
            {
                if(!eS && !eXS)//if(player isn't standing on or about to move into a solid entity){move up}
                {
                    x += xa;
                }
                else if(e.solid() || eX.solid())//otherwise, if(player is standing on or about to walk into an entity)
                {
                    if((softBlockPass && (isolatedCollision(eCols, SoftBlock.className()) || isolatedCollision(eColsX, SoftBlock.className())))//if player is in a soft-block and has soft-block pass
                    || (bombPass && (isolatedCollision(eCols, Bomb.className()) || isolatedCollision(eColsX, Bomb.className())))//or the player is in a bomb and has bomb pass
                    || e == eX || !eX.solid())
                    {
                        x += xa;//MOVE
                    }
                }
            }
            else if(!falling && !bouncing)
            {
                if(tileCollision(this, dir.LEFT, 0, xa, 0, z).solid() && tileCollision(this, dir.LEFT, 1, xa, 0, z).solid())
                {x = ((int)((x + bounds.x) / 32) * 32) - bounds.x;}
                else if(tileCollision(this, dir.RIGHT, 0, xa, 0, z).solid() && tileCollision(this, dir.RIGHT, 1, xa, 0, z).solid())
                {x = ((int)((x + bounds.x + bounds.width) / 32) * 32);}
            }
            if(ya == 0)
            {
                if(((!tileCollision(this, hDir, 0, xa, 0, z).solid() && tileCollision(this, hDir, 1, xa, 0, z).solid())
                    || (!slopeCollision(this, hDir, 0, xa, 0, z).vertical && slopeCollision(this, hDir, 1, xa, 0, z).vertical))
                || (slopeCollision(this, hDir, 0, xa, 0, z).horizontal && !slopeCollision(this, hDir, 1, xa, 0, z).horizontal))
                {
                    if(((!entityCollision(0, 0, z).solid() && !entityCollision(0, Math.abs(xa)*-1, z).solid())
                        || (entityCollision(0, 0, z) == entityCollision(0, Math.abs(xa)*-1, z)))
                    || ((isolatedCollision(entityCollisions(0, 0, z), SoftBlock.className()) || isolatedCollision(entityCollisions(0, Math.abs(xa)*-1, z), SoftBlock.className()))
                        && softBlockPass))
                    {
                        ya = Math.abs(xa)*-1;
                        y -= Math.abs(xa);
                    }
                }
                else if(((tileCollision(this, hDir, 0, xa, 0, z).solid() && !tileCollision(this, hDir, 1, xa, 0, z).solid())
                    || (slopeCollision(this, hDir, 0, xa, 0, z).vertical && !slopeCollision(this, hDir, 1, xa, 0, z).vertical))
                || (!slopeCollision(this, hDir, 0, xa, 0, z).horizontal && slopeCollision(this, hDir, 1, xa, 0, z).horizontal))
                {
                    if((!entityCollision(0, Math.abs(xa), z).solid() || (entityCollision(0, 0, z) == entityCollision(0, Math.abs(xa), z)))
                    || (isolatedCollision(entityCollisions(0, Math.abs(xa), z), SoftBlock.className()) && softBlockPass))
                    {
                        ya = Math.abs(xa);
                        y += Math.abs(xa);
                    }
                }
            }
        }

        if(collisionType(eColsX, CollisionType.SOLID))
        {
            if(isolatedCollision(eColsX, Bomb.className()) == isolatedCollision(eColsX, SoftBlock.className()) && !softBlockPass)
            {
                if(isolatedEntity(eColsX, SoftBlock.className()) != isolatedEntity(eCols, SoftBlock.className()))
                {
                    if(xa < 0)
                    {
                        x = isolatedEntity(eColsX, SoftBlock.className()).getX()+isolatedEntity(eColsX, SoftBlock.className()).getWidth()-isolatedEntity(eColsX, SoftBlock.className()).bounds.x-bounds.x;
                    }
                    if(xa > 0)
                    {
                        x = isolatedEntity(eColsX, SoftBlock.className()).getX()+isolatedEntity(eColsX, SoftBlock.className()).bounds.x-bounds.width-bounds.x;
                    }
                }
            }
        }
        if(collisionType(eColsY, CollisionType.SOLID))
        {
            if(isolatedCollision(eColsY, Bomb.className()) == isolatedCollision(eColsY, SoftBlock.className()) && !softBlockPass)
            {
                if(isolatedEntity(eColsY, SoftBlock.className()) != isolatedEntity(eCols, SoftBlock.className()) || ya < 0)
                {
                    if(ya < 0)
                    {
                        y = isolatedEntity(eColsY, SoftBlock.className()).getY()+isolatedEntity(eColsY, SoftBlock.className()).getHeight()-isolatedEntity(eColsY, SoftBlock.className()).bounds.y-bounds.y;
                    }
                    if(ya > 0)
                    {
                        y = isolatedEntity(eColsY, SoftBlock.className()).getY()+isolatedEntity(eColsY, SoftBlock.className()).bounds.y-bounds.height-bounds.y;
                    }
                }
            }
        }
    }
    
    public void fall(){}

    public abstract void update();//Template update method for all Mobs

    public abstract void render(Screen screen);//Template render method for all Mobs
}