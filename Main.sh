#!/bin/bash
#This is the Shell Script File that executes the Game for Linux and Mac users.
#Sets CLASSPATH
export CLASSPATH=.
echo ${CLASSPATH}
#Compiles the Game class with Jamepad and OGG extensions
##javac -classpath '.:Jamepad.jar:EasyOgg/easyogg.jar:EasyOgg/jogg-0.0.7.jar:EasyOgg/jorbis-0.0.15.jar' JettersR/Game.java
javac -classpath JettersR/Game.java
#Runs the Game class with Jamepad and OGG extensions
#java -classpath '.:Jamepad.jar:EasyOgg/easyogg.jar:EasyOgg/jogg-0.0.7.jar:EasyOgg/jorbis-0.0.15.jar' JettersR/Game
##java -classpath JettersR/Game
