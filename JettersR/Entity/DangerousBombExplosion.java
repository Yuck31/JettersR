package JettersR.Entity;

import java.awt.*;
import JettersR.*;
import JettersR.Entity.Statics.*;
import JettersR.Entity.Items.*;
import JettersR.Entity.Mob.*;

public class DangerousBombExplosion extends BombExplosion
{
    public DangerousBombExplosion(int x, int y, int z, int dirNum, int fires, int delay)
    {
        super(x, y, z, dirNum, fires, delay);
        if(this.fires > 3){this.fires = 3;}

        center = new AnimatedSprite(SpriteSheet.Fire_CENTER,96,96,9, 3,false);
        up = new AnimatedSprite(SpriteSheet.Fire_UP,96,96,9, 3,false);
        right = new AnimatedSprite(SpriteSheet.Fire_RIGHT,96,96,9, 3,false);
        down = new AnimatedSprite(SpriteSheet.Fire_DOWN,96,96,9, 3,false);
        left = new AnimatedSprite(SpriteSheet.Fire_LEFT,96,96,9, 3,false);
        upEND = new AnimatedSprite(SpriteSheet.Fire_UPend,96,96,9, 3,false);
        rightEND = new AnimatedSprite(SpriteSheet.Fire_RIGHTend,96,96,9, 3,false);
        downEND = new AnimatedSprite(SpriteSheet.Fire_DOWNend,96,96,9, 3,false);
        leftEND = new AnimatedSprite(SpriteSheet.Fire_LEFTend,96,96,9, 3,false);
    }

    public void createFire()
    {
        if(flag == true){return;}
        //
        if(dirNum == 0)//Center Fire
        {
            for(byte i = 1; i <= fires; i++)//Right Fires
            {
                Projectile p = new DangerousBombExplosion((int)(x)+(32*i),(int)(y),this.z,1, fires,i);
                if(i == fires){p = new DangerousBombExplosion((int)(x)+(32*i),(int)(y),this.z,5, fires,i);}
                if(p.x + p.bounds.x < level.maxX){level.add(p);}
                else{break;}
            }
            for(byte i = 1; i <= fires; i++)//Down Fires
            {
                Projectile p = new DangerousBombExplosion((int)(x),(int)(y)+(32*i),this.z,2, fires,i);
                if(i == fires){p = new DangerousBombExplosion((int)(x),(int)(y)+(32*i),this.z,6,fires,i);}
                if(p.y + p.bounds.y < level.maxY){level.add(p);}
                else{break;}
            }
            for(byte i = 1; i <= fires; i++)//Left Fires
            {
                Projectile p = new DangerousBombExplosion((int)x-(32*i),(int)y,this.z,3, fires,i);
                if(i == fires){p = new DangerousBombExplosion((int)(x)-(32*i),(int)(y),this.z,7, fires,i);}
                if(p.x + p.bounds.x >= level.minX+32){level.add(p);}
                else{break;}
            }
            for(byte i = 1; i <= fires; i++)//Up Fires
            {
                Projectile p = new DangerousBombExplosion((int)x,(int)y-(32*i),this.z,4, fires,i);
                if(i == fires){p = new DangerousBombExplosion((int)(x),(int)(y)-(32*i),this.z,8, fires,i);}
                if(p.y + p.bounds.y >= level.minY+32){level.add(p);}
                else{break;}
            }
        }
        if(dirNum == 1 || dirNum == 5)//Right Fires
        {
            for(byte i = 1; i <= fires; i++)//Up fires from Right
            {
                Projectile pro = new DangerousBombExplosion((int)x,(int)y-(32*i),this.z,4, fires,i);
                if(pro.x + pro.bounds.x >= level.maxX || pro.y + pro.bounds.y < level.minY + 32){break;}
                if(i == fires){pro =  new DangerousBombExplosion((int)(x),(int)(y)-(32*i),this.z,8, fires,i);}
                level.add(pro);
            }
            for(byte i = 1; i <= fires; i++)//Down fires from Right
            {
                Projectile pro = new DangerousBombExplosion((int)(x),(int)(y)+(32*i),this.z,2, fires,i);
                if(pro.x + pro.bounds.x >= level.maxX || pro.y + pro.bounds.y >= level.maxY){break;}
                if(i == fires){pro =  new DangerousBombExplosion((int)(x),(int)(y)+(32*i),this.z,6,fires,i);}
                level.add(pro);
            }
        }
        if(dirNum == 3 || dirNum == 7)//Left Fires
        {
            for(byte i = 1; i <= fires; i++)//Up fires from Left
            {
                Projectile pro = new DangerousBombExplosion((int)x,(int)y-(32*i),this.z,4, fires,i);
                if(pro.x + pro.bounds.x < level.minX + 32 || pro.y + pro.bounds.y < level.minY + 32){break;}
                if(i == fires){pro =  new DangerousBombExplosion((int)(x),(int)(y)-(32*i),this.z,8, fires,i);}
                level.add(pro);
            }
            for(byte i = 1; i <= fires; i++)//Down fires from Left
            {
                Projectile pro = new DangerousBombExplosion((int)(x),(int)(y)+(32*i),this.z,2, fires,i);
                if(pro.x + pro.bounds.x < level.minX + 32 || pro.y + pro.bounds.y >= level.maxY){break;}
                if(i == fires){pro =  new DangerousBombExplosion((int)(x),(int)(y)+(32*i),this.z,6,fires,i);}
                level.add(pro);
            }
        }
        flag = true;
    }
}