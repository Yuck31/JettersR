package JettersR.Entity.Mob;
/**
 * This is the Class that represents the Player that a player can control.
 * It has a LOT of code that represents the Player's functionallity.
 * It controls functions such as the Player's ability to: Move, Deploy Bombs, Punch Bombs, Pick up and Throw Bombs, Die, etc.
 *
 * author: Luke Sullivan
 * Last Edit: 11/29/2019
 */

import java.util.Random;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import JettersR.*;
import JettersR.Tiles.*;
import JettersR.Util.*;
import JettersR.UI.*;
import JettersR.Audio.*;
import JettersR.GameStates.*;
import JettersR.Entity.*;
import JettersR.Entity.Statics.*;
import JettersR.Entity.Items.*;
import JettersR.Entity.Particle.*;

enum PlayerState//Enums are essentially Booleans, but they can have MORE than two values
{
    NEUTRAL/**Normal State with no additional effects*/, STUNNED/**Stunned by an attack, such as a bomb bouncing on the Player's head*/,
    HOLDING/**Holding a Bomb with Bomb Throw or Super Power Glove*/, KICKING/**Kicking a Bomb with Bomb Kick*/,
    PUNCHING/**Punching a Bomb with Bomb Punch*/, CHARGING/**Charging a Bomb with Bomber Shoot*/,
    SHOOTING/**Shooting a Bomb with Bomber Shoot*/, RIDING/**Riding a Bomb with Detonator*/;
}

public class Player extends Mob
{
    public Keyboard input;//Player's KeyBoard object
    public Disease disease = Disease.NONE;
    public List<Item> collectedItems = new ArrayList<Item>();

    public int[] playerColors;

    float testX = 2f;
    float testY = 2f;

    private int punchAnim = 0;//For animating punch frames
    public int[] deathFrames =
        {
            3, 7, 11, 15, 19, 24, 28, 32, 35, 38, 40, 42, 43, 45, 46, 48, 49, 50, 52, 53, 55, 56, 57, 59, 60, 61, 62, 63, 63, 64, //Rising
            64, 63, 63, 62, 61, 60, 59, 57, 56, 55, 53, 52, 50, 49, 48, 46, 45, 43, 42, 40, 38, 35, 32, 28, 24, 19, 15, 11, 7, 3, 0//Falling
        };
    public int deathSlot = 0;
    private boolean walking = false;
    public boolean dead = false;
    public boolean inRevengeCart = false;
    public boolean cartShot = false;
    public int cartCool = 210;
    public boolean imMobile = false;
    public int animDead = 0;//For checking the duration of the Death animation
    public byte keyTime = 0;//For "single press" inputs
    public double angle = 0;

    public byte playerNum = 0;//For checking what number Player this instance is.
    PlayerState playerState = PlayerState.NEUTRAL;
    public byte wins = 0;

    //Animations for each Player
    public Sprite playerUI = Sprite.player_UI;
    public Sprite[] playerUIBackground = new Sprite[8];

    public AnimatedSprite front = new AnimatedSprite(SpriteSheet.Bomber_FRONT,42,42,16, 2);
    public AnimatedSprite left = new AnimatedSprite(SpriteSheet.Bomber_LEFT,42,42,16,2);
    public AnimatedSprite right = new AnimatedSprite(SpriteSheet.Bomber_RIGHT,42,42,16,2);
    public AnimatedSprite back = new AnimatedSprite(SpriteSheet.Bomber_BACK,42,42,16,2);
    public AnimatedSprite holdFront = new AnimatedSprite(SpriteSheet.BomberHold_FRONT,42,42,16, 2);
    public AnimatedSprite holdLeft = new AnimatedSprite(SpriteSheet.BomberHold_LEFT,42,42,16,2);
    public AnimatedSprite holdRight = new AnimatedSprite(SpriteSheet.BomberHold_RIGHT,42,42,16,2);
    public AnimatedSprite holdBack = new AnimatedSprite(SpriteSheet.BomberHold_BACK,42,42,16,2);
    public Sprite bounceFront = Sprite.playerBounce_front;
    public Sprite bounceLeft = Sprite.playerBounce_left;
    public Sprite bounceRight = Sprite.playerBounce_right;
    public Sprite bounceBack = Sprite.playerBounce_back;

    public AnimatedSprite frontIdle = new AnimatedSprite(SpriteSheet.Bomber_FRONTidle,42,42,6, 2);
    public AnimatedSprite leftIdle = new AnimatedSprite(SpriteSheet.Bomber_LEFTidle,42,42,6,2);
    public AnimatedSprite rightIdle = new AnimatedSprite(SpriteSheet.Bomber_RIGHTidle,42,42,6,2);
    public AnimatedSprite backIdle = new AnimatedSprite(SpriteSheet.Bomber_BACKidle,42,42,1,2);
    public AnimatedSprite holdFrontIdle = new AnimatedSprite(SpriteSheet.BomberHold_FRONTidle,42,42,6, 2);
    public AnimatedSprite holdLeftIdle = new AnimatedSprite(SpriteSheet.BomberHold_LEFTidle,42,42,6,2);
    public AnimatedSprite holdRightIdle = new AnimatedSprite(SpriteSheet.BomberHold_RIGHTidle,42,42,6,2);
    public AnimatedSprite holdBackIdle = new AnimatedSprite(SpriteSheet.BomberHold_BACKidle,42,42,1,2);

    public AnimatedSprite death = new AnimatedSprite(SpriteSheet.Bomber_DEAD,42,42,31,6, false);

    public AnimatedSprite rcShootFront = new AnimatedSprite(SpriteSheet.rcShootFront, 90, 70, 5, 2);
    public AnimatedSprite rcJetL0 = new AnimatedSprite(SpriteSheet.rcJetL0, 45, 35, 4, 2);
    public AnimatedSprite rcJetR0 = new AnimatedSprite(SpriteSheet.rcJetR0, 45, 35, 4, 2);
    public AnimatedSprite rcJetL1 = new AnimatedSprite(SpriteSheet.rcJetL1, 45, 35, 4, 2);
    public AnimatedSprite rcJetR1 = new AnimatedSprite(SpriteSheet.rcJetR1, 45, 35, 4, 2);
    public AnimatedSprite rcJetL2 = new AnimatedSprite(SpriteSheet.rcJetL2, 45, 35, 4, 2);
    public AnimatedSprite rcJetR2 = new AnimatedSprite(SpriteSheet.rcJetR2, 45, 35, 4, 2);

    public AnimatedSprite stunnedEffect = new AnimatedSprite(SpriteSheet.stunnedEffectAnim, 42, 20, 16, 3.5f);
    //
    public int dirNum = 0;

    private int stunTime = 90;
    private int idleTime = 180;
    private int idleAnim = 12;
    public boolean falling = false;//Used for falling to a lower floor

    private int dirX = 0;
    private int dirY = 0;

    //Power-Ups
    public int startBombs = 1;
    public int bombs = startBombs;//Maximum amount of Bombs the Player can hold
    public int deployableBombs = 0;//Number used to limit Bomb placement depending on the Bombs variable
    public int maxBombs = 8;

    public int startFires = 2;
    public int fires = startFires;//Determines the size of a Bomb's explosion(how many Explosion objects it will make)
    public int maxFires = 8;

    public float startSpeed = 1f;//Speed here is a multiplier of the Player's velocity (distance per frame)
    public float speed = startSpeed;//Speed here is a multiplier of the Player's velocity (distance per frame)
    public float maxSpeed = 2.6f;

    public boolean hasHeart = false;
    public int invinceFrames = 0;//Used for after the Player is hit with a Heart

    public boolean bombKick = false;
    public byte bombThrow = 0;//0 = none, 1 = Bomb Throw, 2 = Super Power Glove
    public boolean bombPunch = false;

    public boolean canMerge = false;
    public int mergeTimer = 0;
    public byte mergedNum = 0;
    public Player[] mergedPlayers = new Player[8];
    public boolean merged;
    public Player hostMerger;

    public boolean droppedItems = false;

    public Bomb heldBomb;//The Bomb the Player is holding

    //Swapping Variables
    public int equipedBombType = 0;//Current equiped Bomb
    public final int type_bomb = 0;//I specifically assign regular numbers to "type" variables for when I need to add more Bomb types to the game 
    public final int type_powerBomb = 1;
    public final int type_clusterBomb = 2;
    public final int type_dangerousBomb = 3;
    public final int maxBombTypes = 4;//Max amount of types of Bombs in the game(so far...)

    public Sprite[] bombTypes = new Sprite[maxBombTypes];//Bomb Type Sprite Array
    public boolean[] collectedBombType = new boolean[maxBombTypes];
    public boolean[] haveBombType = new boolean[maxBombTypes];

    public int equipedSpecial = 0;//Current equiped Special
    public int type_none = 0;
    public int type_bomberShoot = 1;
    public int maxSpecials = 2;//Max amount of Specials in the game(so far...)

    public Sprite[] specialTypes = new Sprite[maxSpecials];//Special Sprite Array
    public boolean[] collectedSpecialType = new boolean[maxSpecials];

    public boolean remoteControl = false;//Used exclusivly for the Remote Control
    public boolean type_pierceBomb = false;//Used exclusivly for the Pierce Bomb
    public int chargeTimer = 0;//Used exclusivly for the Bomber Shoot(Bead Bomb)
    public boolean softBlockPass = false;//Used exclusivly for the Soft Block Pass
    public boolean bombPass = false;//Used exclusivly for the Bomb Pass
    public int dangerTimer = 180;//Used exclusivly for the Super Power Glove

    //UI
    private UIManager ui;
    private UILabel BomberIcon;
    private UILabel bombs_Count;
    private UILabel fires_Count;

    private UILabel bombKickStatus;
    private UILabel bombThrowStatus;
    private UILabel bombPunchStatus;

    private UILabel heartSlot;

    private UILabel bombSlot;
    private UILabel bombType;
    private UILabel remoteControlIcon;

    private UILabel specialSlot;
    private UILabel specialType;
    private UILabel softBlockPassIcon;

    private UILabel winsCount;

    public Player(int x, int y, int z, Keyboard input, byte playerNum, UIManager ui)//The consturctor for each instance of Player
    {
        super(x,y,z);
        dir = dir.DOWN;
        this.input = input;
        this.playerNum = playerNum;

        this.terminalVelocity = 14;

        //Setting Player Color Pallete
        playerColors = Game.playerColors[playerNum];

        //Setting collision Bounds
        bounds.x = 10;
        bounds.y = 20;
        bounds.width = 21;
        bounds.height = 20;

        //Setting Player Bomb and Special Types (sprites and booleans)
        bombTypes[0] = Sprite.bomb_Icon;
        bombTypes[1] = Sprite.powerBomb_Icon;
        bombTypes[2] = Sprite.clusterBomb_Icon;
        bombTypes[3] = Sprite.dangerousBomb_Icon;

        collectedBombType[0] = true;
        haveBombType[0] = true;
        for(int i = 1; i < collectedBombType.length; i++)
        {
            collectedBombType[i] = false;
        }
        for(int i = 1; i < haveBombType.length; i++)
        {
            haveBombType[i] = false;
        }

        specialTypes[0] = Sprite.none_Icon;
        specialTypes[1] = Sprite.bomberShoot_Icon;
        for(int i = 1; i < collectedSpecialType.length; i++)
        {
            collectedSpecialType[i] = false;
        }

        //Presetting UI Background Sprite for each Player
        playerUIBackground[0] = Sprite.white_UIBackground;
        playerUIBackground[1] = Sprite.black_UIBackground;
        playerUIBackground[2] = Sprite.red_UIBackground;
        playerUIBackground[3] = Sprite.blue_UIBackground;

        playerUIBackground[4] = Sprite.green_UIBackground;
        playerUIBackground[5] = Sprite.yellow_UIBackground;
        playerUIBackground[6] = Sprite.pink_UIBackground;
        playerUIBackground[7] = Sprite.lightBlue_UIBackground;

        //Setting Player UI
        this.ui = ui;
        UIPanel panel = new UIPanel(new Vector2i((playerNum*74)+4,0), playerUIBackground[playerNum], true);
        ui.addPanel(panel);

        BomberIcon = new UILabel(new Vector2i(0,0), Sprite.customPlayer(playerUI, playerColors));
        panel.addComponent(BomberIcon); 

        UILabel bombsLabel = new UILabel(new Vector2i(-1,0), Sprite.bombs_Icon);
        panel.addComponent(bombsLabel);  
        UILabel firesLabel = new UILabel(new Vector2i(37,0), Sprite.fires_Icon);
        panel.addComponent(firesLabel);

        bombs_Count = new UILabel(new Vector2i(16,1), "0" + Integer.toString(bombs));
        bombs_Count.setFont(Font.numbersUI);
        panel.addComponent(bombs_Count); 

        fires_Count = new UILabel(new Vector2i(52,1), "0" + Integer.toString(fires));
        fires_Count.setFont(Font.numbersUI);
        panel.addComponent(fires_Count);

        bombKickStatus = new UILabel(new Vector2i(-3,44), Sprite.bombKickOff_Icon);
        panel.addComponent(bombKickStatus);

        bombThrowStatus = new UILabel(new Vector2i(13,44), Sprite.bombThrowOff_Icon);
        panel.addComponent(bombThrowStatus);

        bombPunchStatus = new UILabel(new Vector2i(29,44), Sprite.bombPunchOff_Icon);
        panel.addComponent(bombPunchStatus);

        bombSlot = new UILabel(new Vector2i(50,18), Sprite.bomb_slot);
        panel.addComponent(bombSlot);
        bombType = new UILabel(new Vector2i(50, 18), bombTypes[0]);
        panel.addComponent(bombType);

        specialSlot = new UILabel(new Vector2i(50,41), Sprite.special_slot);
        panel.addComponent(specialSlot);
        specialType = new UILabel(new Vector2i(50, 41), specialTypes[0]);
        panel.addComponent(specialType);

        remoteControlIcon = new UILabel(new Vector2i(45,13), Sprite.remoteControl_Icon);
        panel.addComponent(remoteControlIcon);

        softBlockPassIcon = new UILabel(new Vector2i(45,36), Sprite.softBlockPass_Icon);
        panel.addComponent(softBlockPassIcon);

        heartSlot = new UILabel(new Vector2i(-1,14), Sprite.heart_off);
        panel.addComponent(heartSlot);

        winsCount = new UILabel(new Vector2i(27,25), Integer.toString(wins));
        winsCount.setFont(Font.numbersUI);
        panel.addComponent(winsCount);
        //All of these ^ adapt with the Player Number (thanks to(playerNum*74))

        equipedBombType = 0;
    }

    @Override
    public String getClassName(){return "Player";}

    public static String className(){return "Player";}

    public void update()//Manages moving, animation, and general status
    {
        stunnedEffect.update();

        if(x+bounds.x < level.minX)
        {
            x = level.maxX-bounds.x;
        }
        else if(x+bounds.x > level.maxX)
        {
            x = level.minX;
        }

        if(y+bounds.y < level.minY)
        {
            y = level.maxY-bounds.y;
        }
        else if(y+bounds.y > level.maxY)
        {
            y = level.minY;
        }

        if(keyTime > 0){keyTime--;}
        if(invinceFrames > 0){invinceFrames--;}
        if(!dead && !imMobile && !merged)//All of the following can happen while the Player is alive
        {
            switch(dir)//For the ClusterBomb code...
            {
                case UP: dirNum = 0;
                break;

                case RIGHT: dirNum = 1;
                break;

                case DOWN: dirNum = 2;
                break;

                case LEFT: dirNum = 3;
                break;
            }

            if(!walking && idleTime > 0){idleTime--;}
            else if(walking){idleTime = 180;}

            if(!walking && idleTime <= 0 && idleAnim > 0)
            {
                idleAnim--;
                if(playerState == playerState.HOLDING)
                {
                    if(dir == dir.DOWN)holdFrontIdle.update();
                    else if(dir == dir.LEFT)holdLeftIdle.update();
                    else if(dir == dir.UP)holdBackIdle.update();
                    else if(dir == dir.RIGHT)holdRightIdle.update();
                }
                else
                {
                    if(dir == dir.DOWN)frontIdle.update();
                    else if(dir == dir.LEFT)leftIdle.update();
                    else if(dir == dir.UP)backIdle.update();
                    else if(dir == dir.RIGHT)rightIdle.update();
                }
            }
            if(idleAnim <= 0 || walking)
            {
                idleTime = 180;
                idleAnim = 12;
                if(playerState == playerState.HOLDING)
                {
                    holdFrontIdle.resetAnim();
                    holdLeftIdle.resetAnim();
                    holdBackIdle.resetAnim();
                    holdRightIdle.resetAnim();
                }
                frontIdle.resetAnim();
                leftIdle.resetAnim();
                backIdle.resetAnim();
                rightIdle.resetAnim();
            }

            if(playerState == playerState.STUNNED){stunTime--;}
            if(stunTime <= 0){playerState = playerState.NEUTRAL; stunTime = 90; invinceFrames = 40;}
            if(playerState == playerState.PUNCHING || punchAnim >= 8){punchAnim++;}

            //For Merger
            if(canMerge && mergedNum >= 3)
            {
                releaseMerged();
            }

            if(canMerge && mergeTimer > 0)
            {
                merge();
                if(mergedNum > 0)
                {
                    scale = 1 + (mergedNum*1.5f);
                }
                mergeTimer--;
            }
            else if(canMerge && mergeTimer <= 0)
            {
                releaseMerged();
            }
            //

            //nx and ny will be velocity here

            if(punchAnim >= 8){playerState = playerState.NEUTRAL;}
            if(punchAnim >= 30){punchAnim = 0;}
            if((playerState == playerState.NEUTRAL || playerState == playerState.HOLDING || playerState == playerState.CHARGING)
            && !bouncing)
            {
                if(input.up[playerNum]){ny = ((ny-2)*speed);/**Movement multiplied by speed value*/}
                if(input.down[playerNum]){ny = ((ny+2)*speed);}
                if(input.up[playerNum] && input.down[playerNum]){ny = 0;};//Without these, the player would be able to trick the Entity Collision system by using a Negitive Velocity
                if(input.left[playerNum]){nx = ((nx-2)*speed);}
                if(input.right[playerNum]){nx = ((nx+2)*speed);}
                if(input.left[playerNum] && input.right[playerNum]){nx = 0;};
            }

            //if(punchAnim >= 8 || !punching)
            if(!falling)
            {
                if((playerState == playerState.NEUTRAL || playerState == playerState.HOLDING || playerState == playerState.CHARGING)
                && !bouncing)
                {
                    move(nx, ny);
                }
                else if(bouncing)
                {
                    switch(dir)
                    {
                        case UP: nx = 0; ny = -2;
                        break;

                        case DOWN: nx = 0; ny = 2;
                        break;

                        case LEFT: nx = -2; ny = 0;
                        break;

                        case RIGHT: nx = 2; ny = 0;
                        break;
                    }
                    bounce((int)nx, (int)ny);
                }
            }
            else if(falling && walking && !bouncing && nz < 5)
            {
                //if(slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope())
                //{
                    move(nx,ny);
                //}
                // else if(!tileCollision(this, dir.NONE, 0, nx, ny, z).solid() || zOffset > 8)
                // {
                    // switch(dir)
                    // {
                        // case UP: y -= 2;
                        // break;

                        // case DOWN: y += 2;
                        // break;

                        // case LEFT: x -= 2;
                        // break;

                        // case RIGHT: x += 2;
                        // break;
                    // }
                // }
            }

            if(nx != 0 || ny != 0) 
            {
                setDir(nx, ny);//Uses Mob class to set Direction
                walking = true;
                //System.out.println(xa + " " + ya);
            }
            else
            {
                walking = false;
            }

            //Damage Collision
            if(((hazardCollision(0,7, z) && hazardCollision(0,-7, z)) && (hazardCollision(7,0, z) && hazardCollision(-7,0, z))) && !imMobile)//If the player collides with a Hazard entity/Projectile...
            {
                if(hasHeart)//If the Player has a Heart
                {
                    hasHeart = false;//Remove the Heart
                    invinceFrames = 120;//Make the Player invincable
                }
                else if(!hasHeart && invinceFrames <= 0)//Otherwise if the Player isn't invincable...
                {
                    die();//The player "dies" (it plays a different animation and dosn't allow the player to do anything).
                }
            }

            //Slope Collision

            //if(the player isn't standing on ground & not dead){fall}
            if((!floorCollision(this, dir.NONE, 0, 0, 0, z).isFloor() && !slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope())
            && !dead && !bouncing)
            {
                if(!falling && !slopeCollision(this, dir.NONE, 0, 0, 0, z-1).isSlope())
                {Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bomberFall));}
                //
                // if(tileCollision(this, dir.LEFT, 0, xa, 0, z).solid() && tileCollision(this, dir.LEFT, 1, xa, 0, z).solid())
                // {x = ((int)((x + bounds.x) / 32) * 32) - bounds.x;}
                // else if(tileCollision(this, dir.RIGHT, 0, xa, 0, z).solid() && tileCollision(this, dir.RIGHT, 1, xa, 0, z).solid())
                // {x = ((int)((x + bounds.x + bounds.width) / 32) * 32);}
                //
                // if(tileCollision(this, dir.UP, 0, 0, ya, z).solid() && tileCollision(this, dir.UP, 1, 0, ya, z).solid())
                // {y = ((int)((y + bounds.y)/32) * 32) - bounds.y;}
                // else if(tileCollision(this, dir.DOWN, 0, 0, ya, z).solid() && tileCollision(this, dir.DOWN, 1, 0, ya, z).solid())
                // {y = ((int)(((y + bounds.y + bounds.height) / 32) - 1) * 32) + (bounds.width+2);}
                //
                falling = true;
            }

            if(falling && !bouncing && !slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope())
            {
                if(zOffset <= 0)
                {
                    if(floorCollision(this, dir.NONE, 0, 0, 0, z).isFloor()
                    || slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope())//If the player lands on a floor or slope...
                    {
                        falling = false;//Stop falling
                        nz = 0;//Reset z velocity
                        zOffset = 0;//Reset zOffset
                    }
                    else
                    {
                        if(z-1 < 0){die();}//If z - 1 is less than 0, die.
                        else//Otherwise...
                        {
                            z--;//...Fall to the next floor.
                            zOffset = 15;
                        }
                    }
                }
                else
                {
                    if(entityCollision(0,0,z).solid()
                    || (tileCollision(this, dir.NONE, 0, nx, nx, z-1).solid() && zOffset > 14))
                    {
                        Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bombKick));
                        z++;
                        zOffset += 15;
                        nz = 0;
                        bouncing = true;
                        falling = false;
                    }
                    if(!bouncing)
                    {
                        za++;
                        if(za > 1 && nz < terminalVelocity)
                        {
                            nz++;
                            za = 0;
                        }
                        zOffset -= nz;
                    }
                }
            }

            //if(the player is standing on a left-SlopeTile of the same floor and collides with a tile one floor above him with the right side of his hitbox){raise the player to that floor}
            if((floorCollision(this, dir.RIGHT, 0, 0, 0, z).getDir() == Tile.Direction.LEFT && floorCollision(this, dir.RIGHT, 1, 0, 0, z).getDir() == Tile.Direction.LEFT)
            && (floorCollision(this, dir.RIGHT, 0, nx, 0, z+1).isFloor() || floorCollision(this, dir.RIGHT, 1, nx, 0, z+1).isFloor()))
            {
                z++;
                zOffset = 0;
            }
            //else if(the player is standing on a rightSlope of the same floor and collides with a tile one floor above him with the left side of his hitbox){raise the player to that floor}
            else if((floorCollision(this, dir.LEFT, 0, 0, 0, z).getDir() == Tile.Direction.RIGHT && floorCollision(this, dir.LEFT, 1, 0, 0, z).getDir() == Tile.Direction.RIGHT)
            && (floorCollision(this, dir.LEFT, 0, nx, 0, z+1).isFloor() || floorCollision(this, dir.LEFT, 1, nx, 0, z+1).isFloor()))
            {
                z++;
                zOffset = 0;
            }

            {
                if((slopeCollision(this, dir.RIGHT, 0, 0, 0, z).getDir() == Tile.Direction.LEFT && slopeCollision(this, dir.RIGHT, 1, 0, 0, z).getDir() == Tile.Direction.LEFT)
                || (slopeCollision(this, dir.LEFT, 0, 0, 0, z).getDir() == Tile.Direction.LEFT && slopeCollision(this, dir.LEFT, 1, 0, 0, z).getDir() == Tile.Direction.LEFT))
                {
                    if((((int)(x+bounds.x+bounds.width) - ((int)((x+bounds.x+22)/32)*32))/2) != 0)
                    {zOffset = (((int)(x+bounds.x+bounds.width) - ((int)((x+bounds.x+22)/32)*32))/2);}
                }
                else if((slopeCollision(this, dir.LEFT, 0, 0, 0, z).getDir() == Tile.Direction.RIGHT && slopeCollision(this, dir.LEFT, 1, 0, 0, z).getDir() == Tile.Direction.RIGHT)
                || (slopeCollision(this, dir.RIGHT, 0, 0, 0, z).getDir() == Tile.Direction.RIGHT && slopeCollision(this, dir.RIGHT, 1, 0, 0, z).getDir() == Tile.Direction.RIGHT))
                {
                    if(((((int)((x+bounds.x)/32)*32) - ((int)(x+bounds.x+22)))/2)+26 != 0)
                    {zOffset = ((((int)((x+bounds.x)/32)*32) - ((int)(x+bounds.x+22)))/2)+26;}
                }

                else if((slopeCollision(this, dir.DOWN, 0, 0, 0, z).getDir() == Tile.Direction.UP && slopeCollision(this, dir.DOWN, 1, 0, 0, z).getDir() == Tile.Direction.UP)
                || (slopeCollision(this, dir.UP, 0, 0, 0, z).getDir() == Tile.Direction.UP && slopeCollision(this, dir.UP, 1, 0, 0, z).getDir() == Tile.Direction.UP))
                {
                    if((((int)(y+bounds.y+bounds.height) - ((int)((y+bounds.y+22)/32)*32))/2) != 0)
                    {zOffset = (((int)(y+bounds.y+bounds.height) - ((int)((y+bounds.y+22)/32)*32))/2);}
                }
                else if((slopeCollision(this, dir.UP, 0, 0, 0, z).getDir() == Tile.Direction.DOWN && slopeCollision(this, dir.UP, 1, 0, 0, z).getDir() == Tile.Direction.DOWN)
                || (slopeCollision(this, dir.DOWN, 0, 0, 0, z).getDir() == Tile.Direction.DOWN && slopeCollision(this, dir.DOWN, 1, 0, 0, z).getDir() == Tile.Direction.DOWN))
                {
                    if(((((int)((y+bounds.y)/32)*32) - ((int)(y+bounds.y+22)))/2)+26 != 0)
                    {zOffset = ((((int)((y+bounds.y)/32)*32) - ((int)(y+bounds.y+22)))/2)+26;}
                }

                else if((!slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope() && !slopeCollision(this, dir.NONE, 1, 0, 0, z).isSlope())
                &&(!slopeCollision(this, this.dir, 0, 0, 0, z-1).isSlope() && !slopeCollision(this, this.dir, 1, 0, 0, z-1).isSlope())
                && !falling)
                {
                    zOffset = 0;
                    //falling = true;
                }
            }

            //End of Slope Collision

            //Item Collisions
            Item item = itemDetect(0,0,z);
            if(item != null && !item.collected)//Player collects item...
            {
                try{collectedItems.add((Item)item.getInstance());}
                catch(CloneNotSupportedException e){e.printStackTrace();}
                item.collect(this);//Item does the rest, as it should...
            }

            //BombKick and BomberShoot
            if(bombKick && !bombPass && walking)
            {
                if((projectileCollision(nx,ny,z) instanceof Bomb && projectileCollision(0,0,z) != projectileCollision(nx,ny,z)
                    && !projectileCollision(nx,ny,z).shot) && !entityCollision(0,0,z).solid() && !falling)
                {
                    if(itemDetect(0, 0, z) == null){kick(projectileCollision(nx,ny,z));}
                }
            }

            Projectile p = projectileCollision(0,0,z);

            if(p != null && p instanceof Bomb)
            {
                if(bombThrow > 0 && playerState == playerState.NEUTRAL)
                {
                    if(input.bomb[playerNum] && keyTime == 0)
                    {
                        p.pickUp(this);
                        if((!p.hopping && !p.shot) || p.hopTime < 3)
                        {
                            heldBomb = (Bomb)p;
                            playerState = playerState.HOLDING;
                            keyTime = 2;
                        }
                    }
                }

                if(this.equipedSpecial == type_bomberShoot && playerState == playerState.NEUTRAL)
                {
                    if(input.bomb[playerNum] && keyTime > 0 && this.chargeTimer < 90)
                    {
                        if(chargeTimer < 91){chargeTimer++;}
                        if(this.chargeTimer >= 90)
                        {
                            heldBomb = (Bomb)p;
                            p.pickUp(this);
                        }
                    }
                    else if(!input.bomb[playerNum])
                    {
                        chargeTimer = 0;
                    }
                }
            }
            else if((p == null || !input.bomb[playerNum]) && heldBomb == null)
            {
                chargeTimer = 0;
            }
            //End of BombKick and BomberShoot

            if(deployableBombs < 0){deployableBombs = 0;}
            if(deployableBombs > bombs){deployableBombs = bombs;}

            if(!canMerge)
            {
                if(fires > maxFires){fires = maxFires;}
                if(speed > maxSpeed){speed = maxSpeed;}
            }
        }
        else if(merged && hostMerger != null)
        {
            this.x = hostMerger.getX();
            this.y = hostMerger.getY();
            this.z = hostMerger.getZ();
        }
        else if(dead && !inRevengeCart)//Only this happens when the Player dies
        {
            die();
        }
        clear();

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

        if(bombKick && !bombPass){bombKickStatus.setSprite(Sprite.bombKickOn_Icon);}
        else if(!bombKick && !bombPass){bombKickStatus.setSprite(Sprite.bombKickOff_Icon);}
        else if(bombPass){bombKickStatus.setSprite(Sprite.bombPass_Icon);}

        if(bombThrow == 1){bombThrowStatus.setSprite(Sprite.bombThrowOn_Icon);}
        else if(bombThrow == 2){bombThrowStatus.setSprite(Sprite.superPowerGlove_Icon);}
        else{bombThrowStatus.setSprite(Sprite.bombThrowOff_Icon);}

        if(bombPunch){bombPunchStatus.setSprite(Sprite.bombPunchOn_Icon);}
        else{bombPunchStatus.setSprite(Sprite.bombPunchOff_Icon);}

        if(hasHeart){heartSlot.setSprite(Sprite.heart_on);}
        else{heartSlot.setSprite(Sprite.heart_off);}

        if(remoteControl){remoteControlIcon.setSprite(Sprite.remoteControl_Icon);}
        else{remoteControlIcon.setSprite(Sprite.none_Icon);}

        if(softBlockPass){softBlockPassIcon.setSprite(Sprite.softBlockPass_Icon);}
        else{softBlockPassIcon.setSprite(Sprite.none_Icon);}

        bombType.setSprite(bombTypes[equipedBombType]);
        specialType.setSprite(specialTypes[equipedSpecial]);

        winsCount.setText(Integer.toString(wins));

        updateActions();
        nx = 0; ny = 0;
        //if(input.swapL[0]){testX-=0.01;}
        //if(input.swapR[0]){testX+=0.01;}
        //if(input.swapL[1]){testY-=0.01;}         <-This is all test code for sprite rescaling and stretching
        //if(input.swapR[1]){testY+=0.01;}
    }

    private void clear()
    {
        for(int i = 0; i < level.getProjectiles().size(); i++)
        {
            Projectile p = level.getProjectiles().get(i);
            if(p.isRemoved()) level.getProjectiles().remove(i);
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
        if(speed >= 2)//This sets the framerate of the animation higher if the Player's speed value is high enough
        {
            back.setRate(1);
            holdBack.setRate(1);
            front.setRate(1);
            holdFront.setRate(1);
            left.setRate(1);
            holdLeft.setRate(1);
            right.setRate(1);
            holdRight.setRate(1);
        }
        else if(speed >= 1.4)
        {
            back.setRate(1.5f);
            holdBack.setRate(1.5f);
            front.setRate(1.5f);
            holdFront.setRate(1.5f);
            left.setRate(1.5f);
            holdLeft.setRate(1.5f);
            right.setRate(1.5f);
            holdRight.setRate(1.5f);
        }
        else
        {
            back.setRate(2);
            holdBack.setRate(2);
            front.setRate(2);
            holdFront.setRate(2);
            left.setRate(2);
            holdLeft.setRate(2);
            right.setRate(2);
            holdRight.setRate(2);
        }

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

    public void updateActions()//This manages MOST actions the Player can do via Button inputs
    {
        // if(Mouse.getButton() == 1 && fireRate <= 0 && !dead)
        // {
        // double dx = Mouse.getX() - GamegetWindowWidth()/2;
        // double dy = Mouse.getY() - GamegetWindowHeight()/2;
        // double dir = Math.atan2(dy, dx);
        // shoot(x, y, dir);//Takes values to shoot function
        // fireRate = WizardProjectile.FIRE_RATE;
        // }
        if(!dead && !imMobile && !merged)
        {
            switch(dir)
            {
                case UP: dirX = 0; dirY = -1; break;//Up
                case RIGHT: dirX = 1; dirY = 0; break;//Right
                case DOWN: dirX = 0; dirY = 1; break;//Down
                case LEFT: dirX = -1; dirY = 0; break;//Left
            }

            if((input.bomb[playerNum] && keyTime == 0) && bombs != 0 && (playerState == playerState.NEUTRAL) && !dead)
            {        
                deployBomb((int)x, (int)y);//Takes values to Bomb function
                keyTime = 2;
            }
            else if(input.bomb[playerNum] && keyTime != 0)
            {
                keyTime = 2;//Used for making "Holding" inputs too.
            }

            if(input.bomb[playerNum] && playerState == playerState.HOLDING)
            {        
                playerState = playerState.HOLDING;
                if(bombThrow == 2 && !(heldBomb instanceof DangerousBomb) && dangerTimer > 0)
                {
                    dangerTimer--;//Reduce this timer if the Player is still holding the Bomb with the Super Power Glove
                }
                if(dangerTimer <= 0 && !(heldBomb instanceof DangerousBomb))
                {
                    if(heldBomb instanceof PowerBomb){heldBomb.getPlayer().haveBombType[heldBomb.getPlayer().type_powerBomb] = true;}//In case the Player holds another player's bomb
                    if(heldBomb instanceof ClusterBomb){heldBomb.getPlayer().haveBombType[heldBomb.getPlayer().type_clusterBomb] = true;}
                    Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_poof));
                    Bomb heldBomb2 = new DangerousBomb((int)x,(int)y,z,fires, remoteControl, heldBomb.player, true);
                    heldBomb.remove();
                    heldBomb = heldBomb2;
                    level.add(heldBomb);
                    heldBomb.pickUp(this);
                    level.add(new ParticleManager((int)(x+bounds.x+(bounds.width/2)),(int)(y+bounds.y+10),z,10,30,ParticleManager.particleType.GRAYCLOUD0));
                    //The now converted Dangerous Bomb still retains the original Player it was set by so the original Player can still get the bomb back when it explodes.
                }
            }
            if(!input.bomb[playerNum] && playerState == playerState.HOLDING && !dead)//If the Player is holding a Bomb and the Player lets go of the Bomb button.
            {
                Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bombThrow));
                heldBomb.Throw(dirX, dirY, 4);//"Throw" it
                playerState = playerState.NEUTRAL;//Set PlayerState to neutral
                dangerTimer = 180;//Reset Danger Timer
                heldBomb = null;//Get rid of the held bomb
            }

            if(input.punch[playerNum]
            && (entityCollision(0,0, z) == null || !entityCollision(0,0, z).solid())
            && heldBomb == null && keyTime == 0 && bombPunch == true)
            {
                punch();
                keyTime = 30;
                playerState = playerState.PUNCHING;
            }

            if(!input.bomb[playerNum] && heldBomb != null && chargeTimer == 90)
            {
                shootBomb((int)x, (int)y, heldBomb);
                chargeTimer = 0;
                heldBomb = null;
            }

            if(input.swapL[playerNum] && keyTime == 0)//Upon pressing SwapL
            {
                for(int i = equipedBombType-1; i >= type_bomb-1; i--)//This for loop runs through the boolean array of collected Bomb types
                {
                    if(i >= type_bomb && i < maxBombTypes)
                    {
                        if(collectedBombType[i])//If the type i goes on to was collected
                        {
                            equipedBombType = i;//Equip this Bomb
                            keyTime = 2;
                            break;//Stop the loop
                        }
                    }
                    else if(i < type_bomb){i = maxBombTypes;}//Wrap around if i is less than the regular Bomb type
                }
            }
            else if(input.swapL[playerNum] && keyTime != 0){keyTime = 2;}

            if(input.swapR[playerNum] && keyTime == 0)//...and Vice Versa(Pressing SwapR)
            {
                for(int i = equipedBombType+1; i <= maxBombTypes; i++)
                {
                    if(i >= type_bomb && i < maxBombTypes)
                    {
                        if(collectedBombType[i])
                        {
                            equipedBombType = i;
                            keyTime = 2;
                            break;
                        }
                    }
                    else if(i >= maxBombTypes){equipedBombType = type_bomb; keyTime = 2; break;}
                }
            }
            else if(input.swapR[playerNum] && keyTime != 0){keyTime = 2;}

            if(input.swapL[playerNum] && input.swapR[playerNum]){equipedBombType = 0;}

            if(equipedBombType < type_bomb){equipedBombType = maxBombTypes;}
            if(equipedBombType >= maxBombTypes){equipedBombType = type_bomb;}
        }
        else if(dead && inRevengeCart && !imMobile)
        {

        }
    }

    //Action Functions
    protected void deployBomb(int x, int y)//Used for simply deploying Bombs
    {
        if(slopeCollision(this, dir.NONE, 0, 0, 0, z).isSlope()){return;}
        int bombX = (((x+22)/32)*32)-4;//I take advantage of how ints work here to place Bombs LIKE a grid.
        int bombY = (((y+26)/32)*32)-3;
        if((entityCollision(0, 0, z) == null || !entityCollision(0, 0, z).solid()) && deployableBombs < bombs)//If the Player isn't in a Solid/Breakable-Solid entity(Bomb)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bombPlace));

            Bomb p = new Bomb(bombX,bombY,this.z, fires, remoteControl, this);//Make a new Bomb on BombX and BombY
            if(type_pierceBomb)
            {
                p = new PierceBomb(bombX,bombY,this.z, fires, remoteControl, this);//This places a Pierce Bomb instead
            }

            if((collectedBombType[type_dangerousBomb] && equipedBombType == type_dangerousBomb && haveBombType[type_dangerousBomb]) && deployableBombs < bombs)
            {
                haveBombType[type_dangerousBomb] = false;
                p = new DangerousBomb(bombX,bombY,this.z, fires, remoteControl, this, false);//This places a Dangerous Bomb instead
            }
            else if((collectedBombType[type_clusterBomb] && equipedBombType == type_clusterBomb && haveBombType[type_clusterBomb]) && deployableBombs < bombs)
            {
                haveBombType[type_clusterBomb] = false;
                p = new ClusterBomb(bombX,bombY,this.z, fires, remoteControl, this, dirNum);//This places a Cluster Bomb instead
            }
            else if((collectedBombType[type_powerBomb] && equipedBombType == type_powerBomb && haveBombType[type_powerBomb]) && deployableBombs < bombs)
            {
                haveBombType[type_powerBomb]  = false;
                p = new PowerBomb(bombX,bombY,this.z, fires, remoteControl, this);//This places a Power Bomb instead
            }
            level.add(p);//Add it to the level
            deployableBombs++;//Take away a Bomb the Player can place
        }

    }

    public void shootBomb(int x, int y, Bomb bomb)
    {
        int bombX = (((x+22)/32)*32)-4;
        int bombY = (((y+26)/32)*32)-3;
        int curFires = bomb.fires;
        boolean isRemote = bomb.remote;
        Player curPlayer = bomb.getPlayer();

        Bomb shotBomb = new Bomb(bombX, bombY, z, curFires, remoteControl, curPlayer, this, dirX, dirY);
        if(bomb instanceof DangerousBomb)
        {
            shotBomb = new DangerousBomb(bombX, bombY, z, curFires, isRemote, curPlayer, this, dirX, dirY);
        }
        else if(bomb instanceof ClusterBomb)
        {
            shotBomb = new ClusterBomb(bombX, bombY, z, curFires, isRemote, curPlayer, this, dirX, dirY, dirNum);
        }
        else if(bomb instanceof PowerBomb)
        {
            shotBomb = new PowerBomb(bombX, bombY, z, curFires, isRemote, curPlayer, this, dirX, dirY);
        }
        else if(bomb instanceof PierceBomb)
        {
            shotBomb = new PierceBomb(bombX, bombY, z, curFires, isRemote, curPlayer, this, dirX, dirY);
        }
        Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bomberShoot1));
        level.add(shotBomb);
    }

    public void stun()//Used for stunning the Player
    {
        if(invinceFrames > 0){return;}//Don't stun if the player is invincable
        if(playerState != playerState.STUNNED)
        {
            playerState = playerState.STUNNED;//Change PlayerState to Stunned
            stunTime = 90;//Start the StunTimer
        }
        if(heldBomb != null)//If the Player is holding a bomb
        {
            heldBomb.Throw(dirX, dirY, 1);//Let go of the bomb
            heldBomb = null;
        }
    }

    public void kick(Projectile p)//Whenever the Player walks and has BombKick, a hitbox is made in front of them to detect Bombs
    {
        int directionX = 0;
        int directionY = 0;
        switch(dir)
        {
            case UP: if(projectileCollision(0, ny, z) == p){directionY = -1;}
            else{return;}
            break;

            case DOWN: if(projectileCollision(0, ny, z) == p){directionY = 1;}
            else{return;}
            break;

            case LEFT: if(projectileCollision(nx, 0, z) == p){directionX = -1;}
            else{return;}
            break;

            case RIGHT: if(projectileCollision(nx, 0, z) == p){directionX = 1;}
            else{return;}
            break;
        }

        Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bombKick));
        p.roll(directionX, directionY);//"Kick" it
    }

    public void punch()//Whenever the Player presses Punch and has BombPunch, a hitbox is made in front of them to detect Bombs
    {
        Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bombPunch));
        int directionX = 0;
        int directionY = 0;

        if(dir == dir.UP)//Up
        {
            directionY = -1;
        }
        else if(dir == dir.RIGHT)//Right
        {
            directionX = 1;
        }
        else if(dir == dir.DOWN)//Down
        {
            directionY = 1;
        }      
        else if(dir == dir.LEFT)//Left
        {
            directionX = -1;
        }

        if(projectileCollision(directionX*20,directionY*20,z) != null && projectileCollision(directionX*20,directionY*20,z) instanceof Bomb)
        {
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bombPunchHit));
            projectileCollision(directionX*20,directionY*20,z).hop(directionX, directionY, true, 3);//"Punch" it
        }
    }

    public void bounce(int dirX, int dirY)
    {
        if(bounceNum+1 <= 14)
        {
            if(tileCollision(this,dir.NONE,0,nx,ny,z).solid())
            {
                dirX *= -1;
                dirY *= -1;

                if(tileCollision(this, dir.LEFT, 0, nx, 0, z).solid() && tileCollision(this, dir.LEFT, 1, nx, 0, z).solid())
                {x = ((int)((x + bounds.x) / 32) * 32) - bounds.x;}
                else if(tileCollision(this, dir.RIGHT, 0, nx, 0, z).solid() && tileCollision(this, dir.RIGHT, 1, nx, 0, z).solid())
                {x = ((int)((x + bounds.x + bounds.width) / 32) * 32);}

                if(tileCollision(this, dir.UP, 0, 0, ny, z).solid() && tileCollision(this, dir.UP, 1, 0, ny, z).solid())
                {y = ((int)((y + bounds.y)/32) * 32) - bounds.y;}
                else if(tileCollision(this, dir.DOWN, 0, 0, ny, z).solid() && tileCollision(this, dir.DOWN, 1, 0, ny, z).solid())
                {y = ((int)(((y + bounds.y + bounds.height) / 32) - 1) * 32) + (bounds.width+2);}

                switch(dir)
                {
                    case UP: dir = dir.DOWN;
                    break;

                    case RIGHT: dir = dir.LEFT;
                    break;

                    case DOWN: dir = dir.UP;
                    break;

                    case LEFT: dir = dir.RIGHT;
                    break;
                }
            }
            if(dirX != 0)
            {
                x += dirX;
            }
            if(dirY != 0)
            {
                y += dirY;
            }
            bounceNum++;
        }
        else
        {
            if((entityCollision(dirX,dirY,z-1) != null && entityCollision(dirX,dirY,z-1).solid())
            || (tileCollision(this,dir.NONE,0,nx,ny,z-1).solid() && !floorCollision(this,dir.NONE,0,nx,ny,z).isFloor()))
            {
                Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bombKick));
            }
            else{bouncing = false;}
            bounceNum = 0;
        }
    }

    public void merge()
    {
        Player player = playerDetect(0,0,z);
        if(player != null)
        {
            if(!player.dead && !player.merged && !player.canMerge)
            {
                player.beMerged(this);
                this.bombs += player.bombs;
                this.fires += player.fires;
                mergedPlayers[mergedNum] = player;
                mergedNum++;
            }
        }
    }

    public void beMerged(Player player)
    {
        merged = true;
        hostMerger = player;
    }

    public void releaseMerged()
    {
        for(int i = 0; i < mergedPlayers.length; i++)
        {
            if(mergedPlayers[i] != null)
            {
                this.bombs -= mergedPlayers[i].bombs;
                this.fires -= mergedPlayers[i].fires;

                mergedPlayers[i].merged = false;
                mergedPlayers[i].hostMerger = null;
                mergedPlayers[i].bouncing = true;
                switch(random.nextInt(4))
                {
                    case 0: mergedPlayers[i].bounce(0, -4);//UP
                    break;

                    case 1: mergedPlayers[i].bounce(0, 4);//DOWN
                    break;

                    case 2: mergedPlayers[i].bounce(-4, 0);//LEFT
                    break;

                    case 3: mergedPlayers[i].bounce(4,0);//RIGHT
                    break;
                }
                level.add(new ParticleManager((int)(x+bounds.x+(bounds.width/2)),(int)(y+bounds.y+15),z,10,30,ParticleManager.particleType.GRAYCLOUD0));
                mergedPlayers[i].invinceFrames = 60;
                mergedPlayers[i] = null;
            }
        }
        mergedNum = 0;
        mergeTimer = 0;
        scale = 1f;
        canMerge = false;
    }

    public void ImMobilize(int dir)//This function immobilizes the Player for cutscenes and whatnot
    {
        if(bouncing){return;}
        this.imMobile = true;//Immobilize the Player
        walking = false;//Stop the Player from walking
        if(dir != -1){this.dirNum = dir;}//-1 = current direction, all other numbers are the same as "dir"
        switch(dirNum)
        {
            case 0: this.dir = this.dir.UP;
            break;

            case 1: this.dir = this.dir.RIGHT;
            break;

            case 2: this.dir = this.dir.DOWN;
            break;

            case 3: this.dir = this.dir.LEFT;
            break;
        }
    }

    public void Mobilize(int dir)//This function mobilizes the Player after cutscenes and whatnot
    {
        this.imMobile = false;//Mobilize the Player
        if(dir != -1){this.dirNum = dir;}//-1 = current direction, all other numbers are the same as "dir"
    }

    public void setWins(byte wins)//This is for playing a match after the Results Screen
    {
        this.wins = wins;//It just sets the Player's wins to what it was the last match
    }

    public void die()//Self Explainatory(the name of the function, I mean)
    {
        if(!dead)
        {
            bombs = startBombs; fires = startFires; speed = startSpeed; bombKick = false; bombThrow = 0; bombPunch = false;
            hasHeart = false; remoteControl = false; softBlockPass = false; bombPass = false;
            equipedBombType = type_bomb;
            for(int i = 1; i < maxBombTypes; i++)
            {
                collectedBombType[i] = false;
                haveBombType[i] = false;
            }
            equipedSpecial = type_none;//Current equiped Special
            for(int i = 1; i < maxSpecials; i++)
            {
                collectedSpecialType[i] = false;
            }
            //level.dropItems(collectedItems);
            Game.am.add(AudioManager.audioPlayer(AudioManager.sounds_bomberDeath));//Plays Death sound effect
        }

        dead = true;//Make the Player dead
        bounds.width = 0;//Remove collisions
        bounds.height = 0;
        if(animDead >= 60 && !droppedItems)
        {
            level.dropItems(collectedItems);
            droppedItems = true;
        }
        if(animDead < 127)
        {
            if(deathSlot < deathFrames.length-1){deathSlot++;}
            if(animDead >= 60 && animDead <= 64){death.setRate(3);}
            death.update();
            animDead++;
        }
        else if(animDead >= 127)
        {
            if(Game.gsm.revengeCarts){inRevengeCart = true;}//if(revenge carts are enabled){Put the player in a revenge cart.}
            remove();//Otherwise, {Remove the Player from the level.}
        }

        if(heldBomb != null)//If the Player is holding a bomb
        {
            heldBomb.Throw(dirX, dirY, 1);//Let go of the bomb
            heldBomb = null;
        }

        if(canMerge){releaseMerged();}
    }

    public boolean isDead()
    {
        return dead;
    }

    public void setSprite()
    {
        if(!dead)
        {
            switch(dir)
            {
                case UP:
                if(bouncing)
                {
                    sprite = bounceBack;
                }
                else if(walking)
                {
                    if(playerState == playerState.NEUTRAL){sprite = back.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdBack.getSprite();}
                }
                else
                {
                    if(playerState == playerState.NEUTRAL){sprite = backIdle.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdBackIdle.getSprite();}
                }
                break;

                case RIGHT:
                if(bouncing)
                {
                    sprite = bounceRight;
                }
                else if(walking)
                {
                    if(playerState == playerState.NEUTRAL){sprite = right.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdRight.getSprite();}
                }
                else
                {
                    if(playerState == playerState.NEUTRAL){sprite = rightIdle.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdRightIdle.getSprite();}
                }
                break;

                case DOWN:
                if(bouncing)
                {
                    sprite = bounceFront;
                }
                else if(walking)
                {
                    if(playerState == playerState.NEUTRAL){sprite = front.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdFront.getSprite();}
                }
                else
                {
                    if(playerState == playerState.NEUTRAL){sprite = frontIdle.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdFrontIdle.getSprite();}
                }
                break;

                case LEFT:
                if(bouncing)
                {
                    sprite = bounceLeft;
                }
                else if(walking)
                {
                    if(playerState == playerState.NEUTRAL){sprite = left.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdLeft.getSprite();}
                }
                else
                {
                    if(playerState == playerState.NEUTRAL){sprite = leftIdle.getSprite();}
                    else if(playerState == playerState.HOLDING){sprite = holdLeftIdle.getSprite();}
                }
                break;
            }
        }
        else if(dead && !inRevengeCart)
        {
            sprite = death.getSprite();
        }
    }

    public void render(Screen screen)
    {
        setSprite();
        screen.renderLighting((int)(x+10),(int)((y+30)-(z*16)-zOffset),-20f, Sprite.playerShadow,true);//Renders the playerShadow sprite as... well, a shadow.
        if(!dead && !merged)//If the Player is alive, then the game renders sprites for each of the Player's directions, holding and not holding a bomb
        {
            if(invinceFrames % 3 == 0)
            {
                if((heldBomb != null && dir == dir.UP) && playerState == playerState.HOLDING)
                {
                    screen.renderSprite((int)x, (int)(y-6) - (z*16) - zOffset, heldBomb.sprite, true);
                }
                screen.renderSprite((int)(x-((sprite.width/2)*(scale-1))), (int)(((y-(z*16)-zOffset)-((sprite.height/2)*(scale-1)))-bounceHeight[bounceNum]), Sprite.scale(Sprite.customPlayer(sprite, playerColors), scale), true);
            }

            if(chargeTimer > 10 && chargeTimer % 3 != 0)
            {
                screen.renderSprite((int)x, (int)y - (z*16) - zOffset, sprite, 0xFFFFE800, 0, true);
            }
            if(canMerge && mergeTimer % 3 != 0)
            {
                screen.renderSprite((int)(x-((sprite.width/2)*(scale-1))), (int)(((y-(z*16))-((sprite.height/2)*(scale-1)))-bounceHeight[bounceNum]), Sprite.scale(sprite, scale), 0xFFBB0000, 0, true);
            }

            if((heldBomb != null && dir == dir.DOWN) && playerState == playerState.HOLDING)
            {
                screen.renderSprite((int)x, (int)y - (z*16) - zOffset, heldBomb.sprite, true);
            }
            else if((heldBomb != null && dir == dir.LEFT) && playerState == playerState.HOLDING)
            {
                screen.renderSprite((int)x-12, (int)(y-4) - (z*16) - zOffset, heldBomb.sprite, true);
            }
            else if((heldBomb != null && dir == dir.RIGHT) && playerState == playerState.HOLDING)
            {
                screen.renderSprite((int)x+12, (int)(y-4) - (z*16) - zOffset, heldBomb.sprite, true);
            }
        }
        else if(dead && !inRevengeCart)
        {
            screen.renderSprite((int)x, (int)((y-(z*16))-(deathFrames[deathSlot])+7), Sprite.customPlayer(sprite, playerColors), true);
        }

        if(!dead && stunTime < 90)
        {
            screen.renderSprite((int)(x-((sprite.width/2)*(scale-1))), (int)(((y-(z*16)-zOffset)-((sprite.height/2)*(scale-1)))-bounceHeight[bounceNum]), stunnedEffect.getSprite(), true);
        }
        //screen.drawRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFFFF1111, true);
        //screen.fillRect((int)(x+bounds.x), (int)(y+bounds.y), bounds.width, bounds.height, 0xFF00FF00, 20, true);
        //screen.drawRect((int)((x+bounds.x)/32)*32, (int)((y+bounds.x+12)/32)*32, 31, 31, 0xFFFF2621, true);
    }

    public void renderScale(Screen screen, float scale)
    {
        setSprite();
        screen.renderLighting((int)((x+10)*scale),(int)(((y+30)-(z*16)-zOffset)*scale),-20f, Sprite.scale(Sprite.playerShadow, scale),true);//Renders the playerShadow sprite as... well, a shadow.
        if(!dead)//If the Player is alive, then the game renders sprites for each of the Player's directions, holding and not holding a bomb
        {
            if(invinceFrames % 3 == 0)
            {
                if((heldBomb != null && dir == dir.UP) && playerState == playerState.HOLDING)
                {
                    screen.renderSprite((int)(x*scale), (int)(((y-6)-(z*16)-zOffset)*scale), Sprite.scale(heldBomb.sprite, scale), true);
                }
                screen.renderSprite((int)(x*scale), (int)(((y-(z*16)-zOffset)-bounceHeight[bounceNum])*scale), Sprite.customPlayer(Sprite.scale(sprite, scale), playerColors), true);
            }
            if(chargeTimer > 10 && chargeTimer % 3 != 0)
            {
                screen.renderSprite((int)(x*scale), (int)((y-(z*16)-zOffset)*scale), Sprite.scale(sprite, scale), 0xFFFFE800, 0, true);
            }
            if(canMerge && mergeTimer % 3 != 0)
            {
                screen.renderSprite((int)(x*scale), (int)((y-(z*16)-zOffset)*scale), Sprite.scale(sprite, scale), 0xFFBB0000, 0, true);
            }

            if((heldBomb != null && dir == dir.DOWN) && playerState == playerState.HOLDING)
            {
                screen.renderSprite((int)(x*scale), (int)((y-(z*16)-zOffset)*scale), Sprite.scale(heldBomb.sprite, scale), true);
            }
            else if((heldBomb != null && dir == dir.LEFT) && playerState == playerState.HOLDING)
            {
                screen.renderSprite((int)((x-12)*scale), (int)(((y-4)-(z*16)-zOffset)*scale), Sprite.scale(heldBomb.sprite, scale), true);
            }
            else if((heldBomb != null && dir == dir.RIGHT) && playerState == playerState.HOLDING)
            {
                screen.renderSprite((int)((x+12)*scale), (int)(((y-4)-(z*16)-zOffset)*scale), Sprite.scale(heldBomb.sprite, scale), true);
            }
        }
        else if(dead && !inRevengeCart)
        {
            screen.renderSprite((int)(x*scale), (int)(((y-(z*16))-(deathFrames[deathSlot])+7)*scale), Sprite.customPlayer(Sprite.scale(sprite, scale), playerColors), true);
        }
    }
}