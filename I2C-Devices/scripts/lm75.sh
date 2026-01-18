#! /usr/bin/bash

#REM
#REM LM_75 start file
#REM

#set -x

INSTALL_PATH=/site/test

export JAVA_HOME=/usr/bin/java
export PATH=$PATH:$INSTALL_PATH

owd=`pwd`

cd $INSTALL_PATH

CLASS_PATH=.:./I2CDeviceTest.jar

#rem
#rem   L I B R A R Y    s e c t i o n
#rem
CLASS_PATH=$CLASS_PATH:./bin/LM_75-Test-1.1.1.jar

CLASS_PATH=$CLASS_PATH:./lib/LM_75-1.1.1.jar
CLASS_PATH=$CLASS_PATH:./lib/I2C-Device-1.1.1.jar

CLASS_PATH=$CLASS_PATH:./lib/args4j-2.37.jar

CLASS_PATH=$CLASS_PATH:./lib/log4j-api-2.25.3.jar
CLASS_PATH=$CLASS_PATH:./lib/log4j-core-2.25.3.jar
CLASS_PATH=$CLASS_PATH:./lib/log4j-slf4j2-impl-2.25.3.jar

CLASS_PATH=$CLASS_PATH:./lib/pi4j-core-2.8.0.jar
CLASS_PATH=$CLASS_PATH:./lib/pi4j-library-gpiod-2.8.0.jar
CLASS_PATH=$CLASS_PATH:./lib/pi4j-library-linuxfs-2.8.0.jar
CLASS_PATH=$CLASS_PATH:./lib/pi4j-plugin-gpiod-2.8.0.jar
CLASS_PATH=$CLASS_PATH:./lib/pi4j-plugin-linuxfs-2.8.0.jar

CLASS_PATH=$CLASS_PATH:./lib/slf4j-api-2.0.17.jar

#export CLASS_PATH

echo

echo "OWD       : $owd"
echo "CWD       : $cd"
echo "PATH      : $PATH"
echo "CLASS_PATH: $CLASS_PATH"

echo

echo "Launching Application ..."
/usr/bin/java -cp $CLASS_PATH lm75.LM_75_Test $@

cd $owd
