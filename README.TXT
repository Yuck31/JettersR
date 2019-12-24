------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:Bomberman(2019) By: Luke Sullivan
PURPOSE OF PROJECT: For fun. Not for Glory.
VERSION or DATE: 11/8/19
HOW TO START THIS PROJECT: Execute the "Main.Bat" batch file. Make sure you have at least Java 8 installed.
AUTHORS:Luke Sullivan

USER INSTRUCTIONS:

Thanks to Jamepad, this game now supports Controllers. Controllers are assigned to Players in this order:
Controllers 1-8 = Players 1-8
See "HOW TO CHANGE CONTROLLER ORDER" for details on how to change this order.

A = Bomb/Cancel, X = Punch, Y = Remote, B = Special, LB = SwapL, RB = SwapR, LeftStick/D-Pad = Move, Start = Pause/Select

Keyboard Controls:

Move:
P1: WASD;
P2: Up Down Left Right
P3: IJKL;
P4: Numpad8 Numpad4 Numpad5 Numpad6

Bomb:
P1: F
P2: Numpad0
P3: SEMICOLON
P4: Numpad+

Punch(when you have the Bomb Punch power-up)
P1: G
P2: Numpad.(decimal)
P3: '(APOSTROPHE)
P4: Numpad-

Remote(stops YOUR kicked Bombs and detonates remote-controlled Bomb)
P1: E
P2: Numpad1
P3: O
P4: Numpad9

Special(used for various special abilities such as Detonator, Fire-Shield, (potentially) Charaboms, etc.)
//NOT IMPLEMENTED
P1: Q
P2: PAGE DOWN
P3: U
P4: NumPad7

Swap(L and R respectivly)(when you have more than one type of Bomb, you can swap between them for your convieince)
P1: R,T
P2: Numpad2, Numpad3
P3: P,OPEN BRACKET
P4: Numpad/, Numpad*

Pause
P1: ENTER

MANUAL CONTROL ASSIGNMENTS WILL BE COMING SOON.

Here is a list of all the Power-Ups in the Game and what they do...

Bomb-Up: Increases Player Bomb capacity by 1. Players can deploy bombs with the Bomb button(F,SEMICOLON,NumPad0,NumPad+ for each Player respectivly).
Fire-Up: Increases Player's Bomb's Explosion size by 1 tile.
Speed-Up: Increases Player's Movement speed by 20%.
Tri-Bomb-Up: Increases Player Bomb capacity by 3.
Tri-Fire-Up: Increases Player's Bomb's Explosion size by 3 tiles.
Tri-Speed-Up: Increases Player's Movement speed by 60%.
Full Fire: Maxes the Player's Bomb's explosion size.
Heart: Allows the Player to take an extra hit. Grants 2 seconds of invincibility upon being damaged.
Remote Control: Allows the Player to deploy Remote-Detonation Bombs that can be activated with the remote Button(E,O,NumPad1,NumPad9).
Pierce Bomb: Allows the Player to deploy Pierce Bombs, which pierce through soft blocks.

Bomb Kick: Allows the Player to kick bombs when walking into them. The Player can press the Remote button to stop one of THEIR kicked bombs.
Bomb Throw: Allows the Player to pick up bombs underneath them by holding the Bomb Button
Bomb Punch: Allows the Player to Punch bombs in front of them with the Punch button(G,APOSTROPHE,NumPadDECIMAL,NumPad-).
Super Power Glove: A varient of the Bomb Throw that allows Players to hold bombs and turn them into Dangerous Bombs with a blast-size of 2.

Bomber Shoot: Allows the Player to charge up and shoot bombs by standing still and holding the Bomb button and then releasing it when the bomb they placed has been picked up.
Soft Block Pass: Allows the Player to pass through soft blocks.
Bomb Pass: Allows the Player to walk through Bombs(overides Bomb kick).

Merger: Allows the Player to merge with other players, increasing the Player's size
and combining the stats of merged Players. Lasts for 25 seconds, but can be ended
early if the Player merges with every living Player on the map or if the Player is
deafeated. Merged Players are released when effect ends.//NOT IMPLEMENTED

Detonator: Allows the Player to ride Bombs at high speeds by pressing the special button in front of a bomb. Multiple detonators can be held at once. This item is dropped after use.//NOT IMPLEMENTED

Power Bomb: Allows the Player to deploy Power Bombs, which explode at max blast size. Non-Stackable bombs can be swapped with the SwapL(R,P,NumPad2,Numpad/) and SwapR(T,[,NumPad3,NumPad*) buttons.
Cluster Bomb: Allows the Player to deploy Cluster Bombs, which create 3 bombs from it upon its explosion.
Dangerous Bomb: Allows the Player to deploy Dangerous Bombs, which explode in a square that pierces hard and soft blocks(blast size is one less than your fires; min:1, max:3).


==HOW TO CHANGE CONTROLLER ORDER==
Initialy, Player 1 uses Controller 1. But, if you navigate to "Custom Bomber" and select it, you
cn then select a player. After selecting one, navigate to "Controller Number" and select it. From
there, you can press left or right to change the controller number of that player.
NOTE: Controller reorder happens instantly, so don't worry if all of a sudden, your controller doesn't work.
To prevent "Controller Overlapping", go to the Player you wanted to swap controllers with and
reorder the controller number on that Player to the number of the previous player you were just at.
==================================