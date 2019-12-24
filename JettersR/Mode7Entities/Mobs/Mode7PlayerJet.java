package JettersR.Mode7Entities.Mobs;

/**
 * This is the Class that controls the Player using the Bomber Jet.
 * 
 * 
 *
 * author: Luke Sullivan
 * Last Edit: 5/23/2019
 */

import java.util.Random;
import java.util.HashMap;
import java.awt.Rectangle;
import JettersR.*;
import JettersR.Util.*;
import JettersR.UI.*;
import JettersR.Mode7Entities.*;
import JettersR.Mode7Entities.Items.*;

enum PlayerState
{
    NEUTRAL/**Normal State with no additional effects*/,
    THROWING/**Throwing a Bomb*/,
    STUNNED/**Stunned by an attack*/,   
    CHARGING/**Charging a Bomb with Bomber Shoot*/,
    SHOOTING/**Shooting a Bomb with Bomber Shoot*/,
    BOOSTING/**Boosting forward for extra speed*/
}

public class Mode7PlayerJet extends Mode7Mob
{
    public Keyboard input;//Player's KeyBoard object
    private Sprite sprite;//Player's sprite
    Random random = new Random();
    private Sprite flameSprite;
    //public boolean playerFlag = false;

    private int anim = 0;//For animating frames
    private int punchAnim = 0;//For animating punch frames
    private boolean moving = false;
    public boolean dead = false;
    public boolean imMobile = false;
    public int renderSize = 42;//Used for adjusting the RenderPlayer method in Screen
    public int animDead = 0;//For checking the duration of the Death animation
    public byte keyTime = 0;//For "single press" inputs
    public double angle = -90;

    public byte playerNum = 0;//For checking what number Player this instance is
    PlayerState playerState = PlayerState.NEUTRAL;

    //Animations for Player
    public AnimatedSprite bomberIdle = new AnimatedSprite(SpriteSheet.Bomber_JETidle,164,164,1,1);
    public AnimatedSprite bomberThrow = new AnimatedSprite(SpriteSheet.Bomber_JETthrow,164,164,4,3);
    public AnimatedSprite bomberBoost = new AnimatedSprite(SpriteSheet.Bomber_JETboost, 164, 164, 2, 2);
    public AnimatedSprite bomberUnBoost = new AnimatedSprite(SpriteSheet.Bomber_JETunBoost, 164, 164, 2, 2);

    public AnimatedSprite jetFlame = new AnimatedSprite(SpriteSheet.Bomber_JET_FLAMEanim,164,164,3,1);
    //
    private int stunTime = 90;
    private int bombTimer = 11;

    private int dirX = 0;
    private int dirY = 0;

    //Boost Variables
    private byte shakeX = 0;
    private byte shakeY = 0;
    private int boostDelay = 3;
    private int unBoostDelay = 0;

    //Power-Ups
    public int bombs = 1;//Maximum amount of Bombs the Player can hold
    public int deployableBombs = 0;//Number used to limit Bomb placement depending on the Bombs variable
    public int maxBombs = 8;

    public int fires = 2;//Determines the size of a Bomb's explosion(how many Explosion objects it will make)
    public int maxFires = 8;

    public boolean hasHeart = false;
    public int invinceFrames = 0;//Used for after the Player is hit with a Heart

    public int chargeTimer = 0;//Used exclusivly for the Bomber Shoot
    public boolean softBlockPass = false;//Used exclusivly for the Soft Block Pass
    public boolean bombPass = false;//Used exclusivly for the Bomb Pass

    //UI
    private UIManager ui;
    private UILabel bombs_Count;
    private UILabel fires_Count;

    private UILabel heartSlot;

    public Mode7PlayerJet(int x, int y, int z, Keyboard input, UIManager ui)//The consturctor for each instance of Player
    {
        super(x,y,z);
        this.input = input;
        //sprite = bomberIdle.getSprite();
        //flameSprite = jetFlame.getSprite();
        //Setting Player UI
        this.ui = ui;
        UIPanel panel = new UIPanel(new Vector2i((0*74)-1,0), Sprite.player_UI, true);
        ui.addPanel(panel);

        UILabel bombsLabel = new UILabel(new Vector2i(2,0), Sprite.bombs_Icon);
        panel.addComponent(bombsLabel);  
        UILabel firesLabel = new UILabel(new Vector2i(40,0), Sprite.fires_Icon);
        panel.addComponent(firesLabel);

        bombs_Count = new UILabel(new Vector2i(19,1), "0" + Integer.toString(bombs));
        panel.addComponent(bombs_Count); 
        fires_Count = new UILabel(new Vector2i(55,1), "0" + Integer.toString(fires));
        panel.addComponent(fires_Count);

        heartSlot = new UILabel(new Vector2i(2,14), Sprite.heart_off);
        panel.addComponent(heartSlot);
    }

    public void update()//Manages moving, animation, and general status
    {
        jetFlame.update();
        //flameSprite = jetFlame.getSprite();
        //System.out.println(angle);
        if(playerState == playerState.NEUTRAL){bomberIdle.update();}
        if(playerState == playerState.STUNNED){stunTime--;}
        if(playerState == playerState.THROWING && bombTimer > 0)
        {
            bomberThrow.update();
            bombTimer--;
        }
        else if(playerState == playerState.THROWING && bombTimer <= 0)
        {
            bomberThrow.resetAnim();
            playerState = playerState.NEUTRAL;
            bombTimer = 11;
        }

        if(playerState == playerState.BOOSTING && boostDelay > 0)
        {
            bomberBoost.update();
            boostDelay--;
        }
        else if(playerState == playerState.BOOSTING && boostDelay <= 0)
        {
            shakeX = (byte)(random.nextInt(3)-1);
            shakeY = (byte)(random.nextInt(3)-1);
        }
        else
        {
            shakeX = 0;
            shakeY = 0;
        }
        if(playerState == playerState.BOOSTING && !input.punch[0])
        {
            bomberBoost.resetAnim();
            playerState = playerState.NEUTRAL;
            boostDelay = 3;
            unBoostDelay = 3;
        }
        if(unBoostDelay > 0)
        {
            bomberUnBoost.update();
            unBoostDelay--;
            if(unBoostDelay == 0){bomberUnBoost.resetAnim();}
        }
        if(stunTime <= 0){playerState = playerState.NEUTRAL; stunTime = 90;}
        if(invinceFrames > 0){invinceFrames--;}
        if(keyTime > 0){keyTime--;}
        double xa = 0, ya = 0;
        if(anim < 7500) {anim++;}
        else {anim = 0;}
        if((!input.left[playerNum] || (input.left[playerNum] && input.right[playerNum])) && angle < -90)
        {
            if(angle < -110){angle+=3;}
            else if(angle < -95){angle+=1.5;}
            else{angle+=0.5;}

            if(angle > -90){angle = -90;}
        }
        if((!input.right[playerNum] || (input.left[playerNum] && input.right[playerNum])) && angle > -90)
        {
            if(angle > -70){angle-=3;}
            else if(angle > -85){angle-=1.5;}
            else{angle-=0.5;}

            if(angle < -90){angle = -90;}
        }

        if(!dead && !imMobile)//All of the following can happen while the Player is alive
        {
            if(playerState != playerState.STUNNED)
            {
                if(input.up[playerNum]){ya = ((ya-8));/**Movement multiplied by speed value*/}
                if(input.down[playerNum]){ya = ((ya+8));}
                if(input.up[playerNum] && input.down[playerNum]){ya = 0;};//Without these, the player would be able to trick the Entity Collision system by using a Negitive Velocity
                if(input.left[playerNum]){xa = ((xa-8));}
                if(input.right[playerNum]){xa = ((xa+8));}
                if(input.left[playerNum] && input.right[playerNum]){xa = 0;};
            }
            if(playerState != playerState.STUNNED)
            {
                if(ya < 0)//UP
                {
                    y += ya;
                }
                if(ya > 0)//DOWN
                {
                    y += ya;
                }
                if(xa < 0)//LEFT
                {
                    x += xa;
                    if(angle > -135){angle -= 4.5;}
                }
                if(xa > 0)//RIGHT
                {
                    x += xa;
                    if(angle < -45){angle += 4.5;}
                }
                if(x < 5){x = 5;}
                else if(x+164 > Game.width-5){x = (Game.width-5)-164;}
                if(y < 5){y = 5;}
                else if(y+164 > Game.height-5){y = (Game.height-5)-164;}
            }

            if(deployableBombs < 0){deployableBombs = 0;}
            if(deployableBombs > bombs){deployableBombs = bombs;}

            if(fires > maxFires){fires = maxFires;}
        }
        else if(dead)//Only this happens when the Player dies
        {
            die();
        }
        //clear();

        //These are used for updating the UI Values and Sprites
        if(bombs < 10)
        {
            bombs_Count.setText("0" + Integer.toString(bombs));
        }
        else{bombs_Count.setText(Integer.toString(bombs));}//One digit has a leading zero whereas more than one digit dosn't have a leading zero

        if(fires < 10)
        {
            fires_Count.setText("0" + Integer.toString(fires));
        }
        else{fires_Count.setText(Integer.toString(fires));}

        if(hasHeart){heartSlot.setSprite(Sprite.heart_on);}
        else{heartSlot.setSprite(Sprite.heart_off);}
        updateActions();
        //angle += 0.01;
    }

    //Used for updating most button actions
    public void updateActions()
    {
        if(playerState == playerState.NEUTRAL && bombTimer == 11 && (input.bomb[0] && keyTime == 0))
        {
            playerState = playerState.THROWING;
            throwBomb((int)x,(int)y);
            keyTime = 2;
        }
        else if(input.bomb[0] && keyTime > 0)
        {
            keyTime = 2;
        }

        if(playerState != playerState.STUNNED && (input.punch[0] && keyTime == 0))
        {
            playerState = playerState.BOOSTING;
        }
    }

    //Action Functions
    protected void throwBomb(int x, int y)//Used for simply deploying Bombs
    {
        Mode7Projectile p = new Mode7Bomb(x+55,y+83,0);
        mode7Level.add(p);//Add it to the level
        //deployableBombs++;//Take away a Bomb the Player can throw
    }

    public void ImMobilize()//This function immobilizes the Player for cutscenes and whatnot
    {
        this.imMobile = true;//Immobilize the Player
        moving = false;//Stop the Player from walking
    }

    public void Mobilize()//This function mobilizes the Player after cutscenes and whatnot
    {
        this.imMobile = false;//Mobilize the Player
    }

    public void die()//Self Explainatory(the name of the function I mean)
    {
        dead = true;//Make the Player dead
        if(animDead < 127)
        {
            //playerDeath[playerNum].update(); animDead++;//Update the Death Animation
        }
        else if(animDead >= 127)
        {
            remove();//Remove the Player from the levela
        }

        if(animDead <= 1)
        {
            
        }
    }

    public boolean isDead()
    {
        return dead;
    }

    public void render(Screen screen)
    {
        if(!dead)//If the Player is alive, then the game renders sprites for each of the Player's directions, holding and not holding a bomb
        {
            if(playerState == playerState.NEUTRAL)
            {
                if(unBoostDelay > 0){sprite = bomberUnBoost.getSprite();}
                else{sprite = bomberIdle.getSprite();}
            }
            if(playerState == playerState.THROWING){sprite = bomberThrow.getSprite();}
            if(playerState == playerState.BOOSTING){sprite = bomberBoost.getSprite();}
            flameSprite = jetFlame.getSprite();
            if(invinceFrames % 3 == 0)
            {
                screen.renderSprite((int)(x+shakeX), (int)(y+shakeY), sprite.rotate(sprite, angle), true);
            }
            if(chargeTimer > 10 && chargeTimer % 3 != 0)
            {
                //screen.renderSprite((int)(x+shakeX), (int)(y+shakeY), sprite.rotate(sprite, angle), 0xFFFFE800, true);
            }
            screen.renderSprite((int)(x+shakeX), (int)(y+shakeY), flameSprite.rotate(flameSprite, angle), true);
        }
        else
        {
            //sprite = playerDeath[playerNum].getSprite();
            //screen.renderSprite((int)x-28, (int)(y-50) - (z*12), sprite, true);
        }
        screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFFFF2626, true);
        //screen.drawRect((int)(x), (int)(y), 2, 2, 0xFFFF2621, true);
    }
}