#!/usr/bin/sh
# Application Stop
JAR_NAME=harrods-client
SERVER_PORT=8080

screen -X -S ${JAR_NAME} kill

CONTINUE=1
while [ ${CONTINUE} -eq 1 ]
do
    sleep 1
    PORT_STATUS=`netstat -an | grep LISTEN | grep ":${SERVER_PORT}" | wc -l`
    if [ ${PORT_STATUS} -eq 0 ]
    then
        CONTINUE=0
    fi
done
