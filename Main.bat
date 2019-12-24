::This is the Batch File that executes the Game.

::Opens Terminal and dosn't display input
@echo off

::Sets CLASSPATH
set CLASSPATH=.

::Compiles the Game class with Jamepad and OGG extensions
javac -cp ".;jamepad.jar;EasyOgg/easyogg.jar;EasyOgg/jogg-0.0.7.jar;EasyOgg/jorbis-0.0.15.jar" JettersR/Game.java

::Runs the Game class with Jamepad and OGG extensions
java -cp ".;jamepad.jar;EasyOgg/easyogg.jar;EasyOgg/jogg-0.0.7.jar;EasyOgg/jorbis-0.0.15.jar" JettersR/Game

pause
