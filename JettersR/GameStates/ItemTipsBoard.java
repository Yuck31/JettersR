package JettersR.GameStates;

import JettersR.*;

public class ItemTipsBoard
{
    private Keyboard key;
    private Font font;
    private int pageNum = 0;
    private int keyTime = 0;

    private Sprite[][] itemSprites = new Sprite[][]
        {
            {
                Sprite.bombUp,
                Sprite.fireUp,
                Sprite.speedUp,
                Sprite.triBombUp,
                Sprite.triFireUp,
                Sprite.triSpeedUp,
            },

            {
                Sprite.fullFire,
                Sprite.heart,
                Sprite.softBlockPass,
                Sprite.bombPass,
            },

            {
                Sprite.bombKick,
                Sprite.bombThrow,
                Sprite.bombPunch,
                Sprite.superPowerGlove,
            },
            
            {
                Sprite.bomberShoot,
                Sprite.detonator,
                Sprite.merger,
            },

            {
                Sprite.remoteControl,
                Sprite.pierceBomb,
                Sprite.powerBomb,
                Sprite.clusterBomb,
                Sprite.dangerousBomb,
            },
            
            //Skulls and Diseases
            {
                Sprite.skullItem,
                Sprite.scale(Sprite.disease_longFuse, 2.0f),
                Sprite.scale(Sprite.disease_shortFuse, 2.0f),
                Sprite.scale(Sprite.disease_weakComb, 2.0f),
                Sprite.scale(Sprite.disease_uads, 2.0f),
                Sprite.scale(Sprite.disease_oads, 2.0f)
            },
            
            {
                Sprite.scale(Sprite.disease_reverse, 2.0f),
                Sprite.scale(Sprite.disease_foxtrot, 2.0f),
                Sprite.scale(Sprite.disease_molasses, 2.0f),
                Sprite.scale(Sprite.disease_ludSpeed, 2.0f),
            },
            //
        };

    private String[][] itemTips = new String[][]
        {
            {
                "BOMB UP: Increases Player Bomb capacity by 1.",
                "FIRE UP: Increases Player's Bomb's Explosion size by 1 tile.",
                "SPEED UP: Increases Player's Movement speed by 20%.",
                "TRI BOMB UP: Increases Player Bomb capacity by 3.",
                "TRI FIRE UP: Increases Player's Bomb's Explosion size by 3 tiles.",
                "TRI SPEED UP: Increases Player's Movement speed by 60%."
            },

            {
                "FULL FIRE: Maxes the Player's Bomb's explosion size.",
                "HEART: Allows the Player to take an extra hit. Grants 2 seconds of" +
                "`invincibility upon being damaged.",
                "SOFT BLOCK PASS: Allows the Player to pass through soft blocks.",
                "BOMB PASS: Allows the Player to walk through Bombs(overides Bomb kick)."
            },

            {
                "BOMB KICK: Allows the Player to kick bombs when walking into them." +
                "`The Player can press the Remote button to stop one`of THEIR kicked bombs.",
                "BOMB THROW: Allows the Player to pick up bombs underneath them by`holding the Bomb Button.",
                "BOMB PUNCH: Allows the Player to Punch bombs in front of them with`the Punch button.",
                "SUPER POWER GLOVE: A varient of the Bomb Throw that allows Players`to hold bombs and turn them into Dangerous Bombs`with a minimum blast-size of 2.",
            },
            
            {
                "BOMBER SHOOT: Allows the Player to charge up and shoot bombs by`standing still and holding the Bomb button and then releasing`it when the bomb they placed has been picked up.",
                "DETONATOR: Allows the Player to ride bombs at high speeds by`pressing the special button in front of a bomb. This item is`dropped after use.//NOT IMPLEMENTED",
                "MERGER: Allows the Player to merge with other players, increasing`the Player's size and combining the stats of merged Players. Lasts`for 25 seconds, but can be ended early if the Player merges with`every living Player on the map or if the Player is deafeated.`Merged Players are released when effect ends.//NOT IMPLEMENTED"
            },

            {
                "REMOTE CONTROL: Allows the Player to deploy Remote-Detonation Bombs that" +//
                "`can be activated with the remote Button.",//
                
                "PIERCE BOMB: Allows the Player to deploy Pierce Bombs,`which pierce through soft blocks.",//
                
                "POWER BOMB: Explodes at max blast size.",//
                
                "CLUSTER BOMB: Creates 3 bombs from it upon its explosion. Their" +//
                "`directions are based on which direction you placed the Cluster Bomb.",//
                
                "DANGEROUS BOMB: Explodes in a square that pierces hard and soft blocks" +//
                "`(blast size is one less than your fires; min:1, max:3)."//
            },
            
            {
                "SKULL: This \"Item\" gives the player one out of the nine following" +
                "`diseases: //NONE OF THESE DISEASES ARE IMPLEMENTED YET",
                
                "LONG FUSE DISEASE: Bombs take 5 seconds to explode instead of 3 seconds.",
                "SHORT FUSE DISEASE: Bombs explode in 1 second instead of 3 seconds.",
                "WEAK COMBUSTION SYNDROME: Bombs always explode with a firepower of 1.",
                "UNDER-ACTIVE DEPLOYMENT SYNDROME: Player can only deploy 1 bomb.",
                
                "OVER-ACTIVE DEPLOYMENT SYNDROME: Player uncontrollably places bombs" +
                "`whenever possible.",
            },
            
            {
                
                "REVERSE MOVEMENT DISEASE: Reverses the Player's movement.",
                "FOXTROT SYNDROME: Causes the Player to \"Foxtrot\" between" + 
                "`fast and slow speeds.",
                "MOLASSES SLUG SYNDROME: Makes the Player move very slowly.",
                "LUDICROUS SPEED SYNDROME: Makes the Player move ludicrously fast."
            },
        };

    public ItemTipsBoard(Keyboard key)
    {
        this.key = key;
        font = new Font();
    }

    public void update()
    {
        if(keyTime > 0){keyTime--;}
        if((key.left[0] || key.right[0]) && keyTime > 0){keyTime = 2;}
        if(key.left[0] && keyTime == 0)
        {
            pageNum--;
            keyTime = 2;
            if(pageNum < 0)
            {
                pageNum = itemTips.length-1;
            }
        }
        if(key.right[0] && keyTime == 0)
        {
            pageNum++;
            keyTime = 2;
            if(pageNum > itemTips.length-1)
            {
                pageNum = 0;
            }
        }
    }

    public void render(Screen screen)
    {
        screen.renderSprite(0,0, Sprite.itemTipsBoard, false);
        for(int i = 0; i < itemTips[pageNum].length; i++)
        {
            screen.renderSprite(100,(60 * (i + 1)), itemSprites[pageNum][i], false);
            font.render(140,((60 * (i + 1)) + 5),itemTips[pageNum][i], Font.exoFont, screen);
        }
        font.render(16,470,"F to cancel", Font.exoFont, screen);
        font.render(716,470,"A and D to scroll", Font.exoFont, screen);
    }
}
