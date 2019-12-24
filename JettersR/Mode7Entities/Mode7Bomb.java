package JettersR.Mode7Entities;

import JettersR.*;
public class Mode7Bomb extends Mode7Projectile
{
    public float distanceScale = 1f;
    public AnimatedSprite bombFly = new AnimatedSprite(SpriteSheet.jetBombAnim,54,54,4, 1);
    public Mode7Bomb(int x, int y, int z)
    {
        super(x,y,z);
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 54;
        bounds.height = 54;
        sprite = bombFly.getSprite();
    }
    
    public void update()
    {
        z++;
        distanceScale = 1/((z+10)*0.1f);
        bounds.width = (int)(54*distanceScale);
        bounds.height = (int)(54*distanceScale);
        x += distanceScale-0.05f;
        bombFly.update();
        if(z >= 200){remove();}
    }
    
    public void render(Screen screen)
    {
        sprite = bombFly.getSprite();
        screen.renderSprite((int)x,(int)y, Sprite.scale(sprite, distanceScale), true);
        screen.drawRect((int)(bounds.x+x), (int)(bounds.y+y), bounds.width, bounds.height, 0xFF8400B8, true);
    }
}
