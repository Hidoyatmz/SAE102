#!/bin/bash
export SOURCES=src
export CLASSES=classes
export CLASSPATH=`find lib -name "*.jar" | tr '\n' ':'`

javac -encoding UTF-8 -cp ${CLASSPATH} -sourcepath ${SOURCES} -d ${CLASSES} $@ `find src -name "*.java"`
cp ressources/* classes/
