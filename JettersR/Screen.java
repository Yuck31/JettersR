package JettersR;

/**
 * This is the Class that renders arrays of pixels (sprites) on screen.
 * It is called upon by the many entities in the game via their render functions.
 *
 * author: Luke Sullivan
 * Last Edit: 9/21/2019
 */

import JettersR.Entity.*;
import JettersR.Mode7Entities.*;
import JettersR.Tiles.*;
import java.util.Random;
import java.awt.Color;

public class Screen
{
    public int width, height;
    public int[] pixels;
    public final int MAP_SIZE = 2;
    public final int MAP_SIZE_MASK = MAP_SIZE -1;

    public int xOffset, yOffset;

    //public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
    private Random random = new Random();

    public Screen(int width, int height)//<-Takes Game's width and height
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];//Going beyond 50,400 will crash

        // for(int i = 0; i < MAP_SIZE*MAP_SIZE; i++)
        // {
        // tiles[i] = random.nextInt(0xffffff);
        // tiles[0] = 0;
        // }
    }

    public void clear()
    {
        for(int i = 0; i < pixels.length; i++)
        {
            pixels[i] = 0;
        }
    }
    
    public void resetOffsets()
    {
        xOffset = 0;
        yOffset = 0;
    }

    public void renderSheet (int xp, int yp, SpriteSheet sheet, boolean fixed)
    {
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sheet.SPRITE_HEIGHT; y++)
        {
            int ya  = y + yp;
            for (int x = 0; x < sheet.SPRITE_WIDTH; x++)
            {
                int xa  = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}
                if(sheet.pixels[x+y*sheet.SPRITE_WIDTH] != 0x00000000){pixels[xa + ya * width] = sheet.pixels[x+y*sheet.SPRITE_WIDTH];}
            }
        }
    }

    public void renderSheet (int xp, int yp, SpriteSheet sheet, int reduction, boolean fixed)
    {
        if(reduction < 0){reduction = 0;}
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sheet.SPRITE_HEIGHT; y++)
        {
            int ya  = y + yp;
            for (int x = 0; x < sheet.SPRITE_WIDTH-reduction; x++)
            {
                int xa  = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}
                pixels[xa + ya * width] = sheet.pixels[x+y*sheet.SPRITE_WIDTH];
            }
        }
    }

    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed)
    {
        if(sprite == null){return;}
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++)
        {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++)
            {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}
                int col = sprite.pixels[(x+y*sprite.getWidth())];
                if(col != 0x00000000){pixels[xa + ya * width] = col;}
            }
        }
    }

    public void renderSprite(int xp, int yp, Sprite sprite, float scaleX, float scaleY, boolean fixed)//This render method can rescale sprites by an x and y float value(stretching)
    {
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        float scaleCritY = scaleY;
        if(scaleY < 1f){scaleCritY = 1f;}
        float scaleCritX = scaleX;
        if(scaleX < 1f){scaleCritX*= 1.1;}
        for (int y = 0; y < sprite.getHeight(); y++)
        {
            for(int s = 0; s < scaleY; s++)//Without this, the sprite would have empty lines where certain y-pixels are supposed to go.
            {
                int ya = (int)((y*scaleY) +  (yp+s));
                for (int x = 0; x < sprite.getWidth()*scaleCritX; x++)
                {
                    int xa = x + xp;
                    if(xa < 0 || xa >= width || ya < 0 || ya >= height)// || yaa < 0 || yaa >= height)
                    {
                        continue;
                    }
                    //int col = sprite.pixels[(int)((x+y*sprite.getWidth())/scale)];
                    int col = 0x00000000;
                    if((int)(((x/scaleX)+(y)*(sprite.getWidth()))) < sprite.pixels.length)
                    {
                        col = sprite.pixels[(int)(((x/scaleX)+(y)*(sprite.getWidth())))];
                    }
                    if(col != 0x00000000){pixels[xa + ya * width] = col;}
                    //if(col != 0x00000000){pixels[xa + yaa * width] = col;}
                }
            }
        }
    }

    public void renderSprite(int xp, int yp, Sprite sprite, int colorChange, int reduction, boolean fixed)//Used for flashing and UI bars
    {
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++)
        {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth()-reduction; x++)
            {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}
                int col = 0x00000000;
                if(colorChange == 0x00000000){col = sprite.pixels[x+y*sprite.getWidth()];}
                else if(sprite.pixels[x+y*sprite.getWidth()] != 0x00000000){col = colorChange;}
                if(col != 0x00000000){pixels[xa + ya * width] = col;}
            }
        }
    }

    public void renderSprite(int xp, int yp, Sprite sprite, int reduction, boolean fixed)//Used for UI bars
    {
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++)
        {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth()-reduction; x++)
            {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}
                int col = 0x00000000;
                if(sprite.pixels[x+y*sprite.getWidth()] != 0x00000000){col = sprite.pixels[x+y*sprite.getWidth()];}
                if(col != 0x00000000){pixels[xa + ya * width] = col;}
            }
        }
    }

    public void renderTile(int xp, int yp, Tile tile)
    {
        xp -= xOffset;
        yp -= yOffset;
        for(int y = 0; y < tile.sprite.height; y++)
        {
            int ya = y + yp;
            for(int x = 0; x < tile.sprite.width; x++)
            {
                int xa = x + xp;
                if(xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) {xa = 0;}
                int col = tile.sprite.pixels[x+y*tile.sprite.SIZE];
                if (col != 0x00000000) {pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];}//Ensures null colors STAY null
            }
        }
    }

    public void renderProjectile(int xp, int yp, Projectile p, double angle) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < p.getSpriteSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < p.getSpriteSize(); x++) {
                int xa = x + xp;
                if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int col = p.getSprite().pixels[x+y*p.getSprite().SIZE];
                if (col != 0x00000000) pixels[xa + ya * width] = p.getSprite().pixels[x + y * p.getSprite().SIZE];//Rids of Null Colors
            }
        }
    }

    public void renderMosaic(int mosaicMulti)//This is meant to replacate the "Mosaic" effect from the SNES.
    {//What happens is that the game takes the screen into sections, takes the upper-left most pixel of each section, and fills each section with that color of pixel
        if(mosaicMulti <= 0){return;}//If mosaicMulti is less than or equal to zero, cancel the function
        int passed = 1;//Variable used for consistancy
        for(int i = 0; i < pixels.length; i+=mosaicMulti)
        {
            if(i >= width*passed)//If i loops over the screen
            {
                i = ((width*passed)+(width*mosaicMulti))-width;//Refresh it to the next row(according to the mosaicMulti)
                passed+=mosaicMulti;//Adjust passed accordingly.
            }
            for(int y = 0; y < mosaicMulti; y++)
            {
                for(int x = 0; x < mosaicMulti; x++)
                {
                    if((i+x) + y * width >= pixels.length || (i+x) >= width*passed){continue;}//If the pixel the function is
                    //trying to access either over the screen on the current passed value or out of bounds of the screens's
                    //array of pixels, just loop to the next pixel.
                    pixels[(i+x) + y * width] = pixels[i];//Every time this loops, it makes each pixel in the section equal the initial pixel
                }
            }
        }
    }

    //Folks... THIS \/ is what you shold REALLY see... MODE 7!
    public void mode7Render(float fWorldX, float fWorldY, float hDegrees, float vDegrees, float fNear, float fFar, float FoVoffset, Sprite sprite)
    {
        //All right, let me make something clear...
        //Mode 7 is a graphical mode on the SNES that could render sprites like a flat texture and
        //even rotate and stretch sprites(only on background sprites however)

        //Essentially ALL of this function's code is taken (and converted to java) from OLC
        //(One Lone Coder), so special thanks to him and his youtube channel!
        
        float hWorldA = (float)(hDegrees * (Math.PI/180));//Convert Degrees to Radians
        float vWorldA = (float)(vDegrees * (Math.PI/180));
        float fFoVHalf = ((3.14159f / 4.0f) + FoVoffset);//This controls the field of view

        //The full area the "camera" can see is called the Furstum. This Furstum is created by making points to "connect" to.
        //Changing the "angle" value changes which direction the "camera" is turned towards.
        float fFarX1 = (fWorldX + (float)Math.cos(hWorldA - fFoVHalf) * fFar);//*vertOff;
        float fFarY1 = (fWorldY + (float)Math.sin(hWorldA - fFoVHalf) * fFar);//+vertOff;

        float fNearX1 = (fWorldX + (float)Math.cos(hWorldA - fFoVHalf) * fNear);//*vertOff;
        float fNearY1 = (fWorldY + (float)Math.sin(hWorldA - fFoVHalf) * fNear);//+vertOff;

        float fFarX2 = (fWorldX + (float)Math.cos(hWorldA + fFoVHalf) * fFar);//*vertOff;
        float fFarY2 = (fWorldY + (float)Math.sin(hWorldA + fFoVHalf) * fFar);//+vertOff;

        float fNearX2 = (fWorldX + (float)Math.cos(hWorldA + fFoVHalf) * fNear);//*vertOff;
        float fNearY2 = (fWorldY + (float)Math.sin(hWorldA + fFoVHalf) * fNear);//+vertOff;

        int y = 0;
        if(vDegrees < 0){y-=vDegrees;}
        for (y = y+1; y < height-(int)(vDegrees); y++)//From the farthest "Scanline" to the closest
        {
            //float fSampleHeight = ((float)y / (float)height);
            // The loop takes a single point
            float fSampleDepth = (float)y / ((float)(height));

            // It then uses the point to determine start and end points for "Scanlines" across the screen
            float fStartX = ((fFarX1 - fNearX1) / (fSampleDepth) + fNearX1);
            float fStartY = ((fFarY1 - fNearY1) / (fSampleDepth) + fNearY1);
            float fEndX = ((fFarX2 - fNearX2) / (fSampleDepth) + fNearX2);
            float fEndY = ((fFarY2 - fNearY2) / (fSampleDepth) + fNearY2);

            //And this loop goes through each Pixel in the "Scanline"
            for (int x = 0; x < width; x++)
            {
                float fSampleWidth = ((float)x / (float)width);

                float fSampleX = (fEndX - fStartX) * fSampleWidth + fStartX;
                float fSampleY = (fEndY - fStartY) * fSampleWidth + fStartY;

                int col = 0x00000000;

                //It creates integers that will corespond to colors in sprite
                int sx = (int)(fSampleX);// * (float)sprite.width);
                int sy = (int)(fSampleY);// * (float)sprite.height);

                if(sy*sprite.width+sx < 0 || sy*sprite.width+sx >= sprite.pixels.length)//If the pixel it's trying to get is out of bounds
                {
                    sx -= (int)((sx)/(sprite.width)) * (sprite.width);//Loop back around
                    sy -= (int)((sy)/(sprite.height)) * (sprite.height);
                }
                //if(fSampleX < 0 || fSampleX >= sprite.width){continue;}

                if(sy*sprite.width+sx >= 0 && sy*sprite.width+sx <= sprite.pixels.length)//If the pixel the varibles try to access is within bounds of the sprite's pixel array
                {
                    col = sprite.pixels[(sy*sprite.width+sx)];//Make col equal the sprite's color on this slot value
                }

                if(col != 0x00000000 && (((x)+(y+(int)vDegrees)*width) >= 0 && ((x)+(y+(int)vDegrees)*width) < pixels.length))
                {
                    pixels[((x)+(y+(int)vDegrees)*width)] = col;//And FINALLY, add the pixel to the screen if it isn't "no color"
                }
            }
        }
    }

    public void renderLighting(int xStart, int xWidth, int yStart, int yHeight, float brightness, boolean fixed)
    {//This function can make a lighting effect out of a rectangular shape of any size
        if(fixed)//Checks if fixed is true
        {
            xStart -= xOffset;
            yStart -= yOffset;
        }
        for (int y = 0; y < yHeight; y++)//For loop from yStart to it's max height
        {
            int ya = y + yStart;
            for (int x = 0; x < xWidth; x++)//For loop from xStart to it's max width
            {
                int xa = x + xStart;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}//If the pixel is off-screen, skip it.

                int alpha = pixels[xa + ya * width] & 0xFF000000;//Get's pixels alpha
                int red = (int)((pixels[xa + ya * width] & 0x00FF0000) + (brightness*0x00010000));//Get's and configures pixels reds
                int green = (int)((pixels[xa + ya * width] & 0x0000FF00) + (brightness*0x00000100));//Get's and configures pixels greens
                int blue = (int)((pixels[xa + ya * width] & 0x000000FF) + (brightness*0x00000001));//Get's and configures pixels blues

                if(red > 0x00FF0000){red = 0x00FF0000;}//If red is higher than max red, make it equal max red.
                else if(red < 0x00010000){red = 0x00000000;}//Else if red is lower than one(black), make it black.
                //Then the same thing for green and blue...

                if(green > 0x0000FF00){green = 0x0000FF00;}
                else if(green < 0x00000100){green = 0x00000000;}
                //else{green = green & 0x0000FF00;}

                if(blue > 0x000000FF){blue = 0x000000FF;}
                else if(blue < 0x00000001){blue = 0x00000000;}

                int col = alpha | red | green | blue;//It combines all of the colors back together...
                pixels[xa + ya * width] = col;//And pastes it onto the screen accordingly.
            }
        }
    }

    public void renderLighting(int xStart, int yStart, float brightness, Sprite sprite, boolean fixed)
    {//This function can take a sprite and make a lighting effect out of it
        if(fixed)//Checks if fixed is true
        {
            xStart -= xOffset;
            yStart -= yOffset;
        }
        for (int y = 0; y < sprite.height; y++)//For loop from yStart to it's max height
        {
            int ya = y + yStart;
            for (int x = 0; x < sprite.width; x++)//For loop from xStart to it's max width
            {
                int xa = x + xStart;
                if((xa < 0 || xa >= width || ya < 0 || ya >= height) || sprite.pixels[x + y * sprite.width] == 0x00000000){continue;}//If the pixel is off-screen or if there isn't a color on this pixel, skip it.

                int alpha = pixels[xa + ya * width] & 0xFF000000;//Get's pixels alpha
                int red = (int)((pixels[xa + ya * width] & 0x00FF0000) + (brightness*0x00010000));//Get's and configures pixels reds
                int green = (int)((pixels[xa + ya * width] & 0x0000FF00) + (brightness*0x00000100));//Get's and configures pixels greens
                int blue = (int)((pixels[xa + ya * width] & 0x000000FF) + (brightness*0x00000001));//Get's and configures pixels blues

                if(red > 0x00FF0000){red = 0x00FF0000;}//If red is higher than max red, make it equal max red.
                else if(red < 0x00010000){red = 0x00000000;}//Else if red is lower than one(black), make it black.
                //Then the same thing for green and blue...

                if(green > 0x0000FF00){green = 0x0000FF00;}
                else if(green < 0x00000100){green = 0x00000000;}
                //else{green = green & 0x0000FF00;}

                if(blue > 0x000000FF){blue = 0x000000FF;}
                else if(blue < 0x00000001){blue = 0x00000000;}

                int col = red | green | blue;//It combines all of the colors back together...
                pixels[xa + ya * width] = col;//And pastes it onto the screen accordingly.
            }
        }
    }

    public void shadeSprite(int xp, int yp, Sprite sprite, float brightness, boolean fixed)
    {
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++)
        {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++)
            {
                int xa = x + xp;
                if((xa < 0 || xa >= width || ya < 0 || ya >= height) || sprite.pixels[(x+y*sprite.getWidth())] == 0x00000000){continue;}

                int alpha = sprite.pixels[(x+y*sprite.getWidth())] & 0xFF000000;//Get's pixels alpha
                int red = (int)((sprite.pixels[(x+y*sprite.getWidth())] & 0x00FF0000) * brightness);//Get's and configures pixels reds
                int green = (int)((sprite.pixels[(x+y*sprite.getWidth())] & 0x0000FF00) * brightness);//Get's and configures pixels greens
                int blue = (int)((sprite.pixels[(x+y*sprite.getWidth())] & 0x000000FF) * brightness);//Get's and configures pixels blues

                if(red > 0x00FF0000){red = 0x00FF0000;}//If red is greater than the maximum red, make it equal the maximum red
                else{red = red & 0x00FF0000;}//Otherwise, extract the pure red components of the color
                //Then it proceeds to do the same thing for the other colors
                if(green > 0x0000FF00){green = 0x0000FF00;}
                else{green = green & 0x0000FF00;}

                if(blue > 0x000000FF){blue = 0x000000FF;}
                else{blue = blue & 0x000000FF;}

                if(brightness > 1.0f)//This reconfigures any colors in case a colors red, green, or blue is maxed(only if the sprite is being made brighter).
                {
                    red += (int)((0x00FF0000 - red)*(brightness-1)) & 0x00FF0000;
                    green += (int)((0x0000FF00 - green)*(brightness-1)) & 0x0000FF00;
                    blue += (int)((0x000000FF - blue)*(brightness-1)) & 0x000000FF;
                }

                int col = red | green | blue;//It combines all of the colors back together...
                pixels[xa + ya * width] = col;//And pastes it onto the screen accordingly.
            }
        }
    }

    public void renderTranslucentSprite(int xStart, int yStart, float opacity, Sprite sprite, boolean fixed)
    {
        if(fixed)//Checks if fixed is true
        {
            xStart -= xOffset;
            yStart -= yOffset;
        }
        for (int y = 0; y < sprite.height; y++)//For loop from yStart to it's max height
        {
            int ya = y + yStart;
            for (int x = 0; x < sprite.width; x++)//For loop from xStart to it's max width
            {
                int xa = x + xStart;
                if((xa < 0 || xa >= width || ya < 0 || ya >= height) || sprite.pixels[x + y * sprite.width] == 0x00000000){continue;}//If the pixel is off-screen, skip it.

                int alpha = sprite.pixels[(x+y*sprite.getWidth())] & 0xFF000000;//Get's pixels alpha
                int red = (int)((pixels[xa + ya * width] & (sprite.pixels[(x+y*sprite.getWidth())] & 0x00FF0000)) + (opacity*0x00010000));
                int green = (int)((pixels[xa + ya * width] & (sprite.pixels[(x+y*sprite.getWidth())] & 0x0000FF00)) + (opacity*0x00000100));
                int blue = (int)((pixels[xa + ya * width] & (sprite.pixels[(x+y*sprite.getWidth())] & 0x000000FF)) + opacity);

                if(red > 0x00FF0000){red = 0x00FF0000;}//If red is greater than the maximum red, make it equal the maximum red
                else{red = red & 0x00FF0000;}//Otherwise, extract the pure red components of the color
                //Then it proceeds to do the same thing for the other colors
                if(green > 0x0000FF00){green = 0x0000FF00;}
                else{green = green & 0x0000FF00;}

                if(blue > 0x000000FF){blue = 0x000000FF;}
                else{blue = blue & 0x000000FF;}

                int col = alpha | red | green | blue;
                pixels[xa + ya * width] = col;//And pastes it onto the screen accordingly.
            }
        }
    }

    public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed)
    {
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for(int x = xp; x < xp + width; x++)
        {
            if(x < 0 || x >= this.width || yp >= this.height){continue;}
            if(yp > 0){pixels[x + yp * this.width] = color;}
            if(yp + height >= this.height){continue;}
            if(yp + height > 0 )pixels[x + (yp + height) * this.width] = color;
        }
        for(int y = yp; y <=  yp + height; y++)
        {
            if(yp >= this.width || y < 0 || y >= this.height){continue;}
            if(xp > 0)pixels[xp + y * this.width] = color;
            if(xp + width >= this.width){continue;}
            if(xp + width > 0)pixels[(xp + width) + y * this.width] = color;
        }
    }

    public void fillRect(int xp, int yp, int width, int height, int color, float opacity, boolean fixed)
    {
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for(int y = yp; y <=  yp + height; y++)
        {
            for(int x = xp; x <= xp + width; x++)
            {
                if(x >= this.width || x < 0 || y >= this.height || y < 0){continue;}

                int alpha = color & 0xFF000000;//Get's pixels alpha
                int red = (int)((pixels[x + y * this.width] & (color & 0x00FF0000)) + (opacity*0x00010000));
                int green = (int)((pixels[x + y * this.width] & (color & 0x0000FF00)) + (opacity*0x00000100));
                int blue = (int)((pixels[x + y * this.width] & (color & 0x000000FF)) + opacity);

                if(red > 0x00FF0000){red = 0x00FF0000;}//If red is greater than the maximum red, make it equal the maximum red
                else{red = red & 0x00FF0000;}//Otherwise, extract the pure red components of the color
                //Then it proceeds to do the same thing for the other colors
                if(green > 0x0000FF00){green = 0x0000FF00;}
                else{green = green & 0x0000FF00;}

                if(blue > 0x000000FF){blue = 0x000000FF;}
                else{blue = blue & 0x000000FF;}

                int col = red | green | blue;
                pixels[x + y * this.width] = col;//And pastes it onto the screen accordingly.
            }
        }
    }

    public void renderCustomPlayer(int xp, int yp, Sprite sprite, int[] swapColors, boolean fixed)
    {
        int face2 = shadedColor(swapColors[0], -5),
        face3 = shadedColor(face2, -5),

        darkHeadColor = shadedColor(swapColors[2], -35),

        hand2 = shadedColor(swapColors[3], -28),
        hand3 = shadedColor(hand2, -30),

        headBall2 = shadedColor(swapColors[4], -28),
        headBall3 = shadedColor(headBall2, -30),
        headBall4 = shadedColor(headBall3, -42),
        headBall5 = shadedColor(headBall4, -28),
        headBall6 = shadedColor(headBall5, -28);

        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++)
        {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++)
            {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}
                if(sprite.pixels[x+(y*sprite.width)] != 0x00000000)
                {
                    switch(sprite.pixels[x+(y*sprite.width)])
                    {
                        //Face
                        case 0xFFFFD9B0: pixels[xa + ya * width] = swapColors[0]; break;
                        case 0xFFFFCB92: pixels[xa + ya * width] = face2; break;
                        case 0xFFFFBD74: pixels[xa + ya * width] = face3; break;

                        //Torso
                        case 0xFF2B2B2B: pixels[xa + ya * width] = swapColors[1]; break;

                        //Head, arms, & legs
                        case 0xFF1A1A1A: pixels[xa + ya * width] = swapColors[2]; break;
                        case 0xFF000001: pixels[xa + ya * width] = darkHeadColor; break;

                        //Hands:
                        case 0xFFCECECE: pixels[xa + ya * width] = swapColors[3]; break;
                        case 0xFFB8B8B8: pixels[xa + ya * width] = hand2; break;
                        case 0xFFA0A0A0: pixels[xa + ya * width] = hand3; break;

                        //Feet, Headball, & Hands
                        case 0xFF7E7E7E: pixels[xa + ya * width] = swapColors[4]; break;
                        case 0xFF727272: pixels[xa + ya * width] = headBall2; break;
                        case 0xFF616161: pixels[xa + ya * width] = headBall3; break;
                        case 0xFF545454: pixels[xa + ya * width] = headBall4; break;
                        case 0xFF3F3F3F: pixels[xa + ya * width] = headBall5; break;
                        case 0xFF101010: pixels[xa + ya * width] = headBall6; break;

                        default: pixels[xa + ya * width] = sprite.pixels[x+(y*sprite.width)];
                    }
                }
            }
        }
    }

    public void RGB(int xp, int yp, Sprite sprite, int rgb, boolean fixed)//rgb: 0 = blue, 1 = green, 2 = red.
    {
        if(sprite == null){return;}
        int hex = 0;
        int hexInc = 1;
        if(rgb == 1){hexInc = 0x00000100;}
        else if(rgb == 2){hexInc = 0x00010000;}
        if(fixed)
        {
            xp -= xOffset;
            yp -= yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++)
        {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++)
            {
                int xa = x + xp;
                if(xa < 0 || xa >= width || ya < 0 || ya >= height){continue;}
                int col = sprite.pixels[(x+y*sprite.getWidth())];
                if(col != 0x00000000){pixels[xa + ya * width] = col + hex;}
                if(x % 2 != 0){hex += hexInc;}
            }
        }
    }

    public int shadedColor(int color, int brightness)
    {
        int alpha = color & 0xFF000000;
        int red = (color & 0x00FF0000) + (brightness*0x00010000);
        int green = (color & 0x0000FF00) + (brightness*0x00000100);
        int blue = (color & 0x000000FF) + (brightness*0x00000001);

        if(red > 0x00FF0000){red = 0x00FF0000;}//If red is higher than max red, make it equal max red.
        else if(red < 0x00010000){red = 0x00000000;}//Else if red is lower than one(black), make it black.

        if(green > 0x0000FF00){green = 0x0000FF00;}
        else if(green < 0x00000100){green = 0x00000000;}

        if(blue > 0x000000FF){blue = 0x000000FF;}
        else if(blue < 0x00000001){blue = 0x00000000;}

        return alpha | red | green | blue;
    }

    public void setOffset(int xOffset, int yOffset)
    {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
