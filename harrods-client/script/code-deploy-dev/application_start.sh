#!/usr/bin/sh
# Application Start
SERVICE_DIR=/java/harrods-client/
JAR_NAME=harrods-client
SERVICE_NAME=harrods-client
VERSION=1.0
ACCOUNT_HOME=/home/ec2-user
DATADOG_JAVA_AGENT=${ACCOUNT_HOME}/dd-java-agent.jar
SERVER_PORT=8080
PROFILE=dev

# Heap size 4GB (dev)
HEAP_XMS=4096m
HEAP_XMX=4096m

cd ${ACCOUNT_HOME}${SERVICE_DIR}

# 인스턴스 실행(dev)
screen -S ${JAR_NAME} -dm java -javaagent:${DATADOG_JAVA_AGENT} -Ddd.profiling.enabled=true -XX:FlightRecorderOptions=stackdepth=256 -Ddd.service=${SERVICE_NAME} -Ddd.env=${PROFILE} -Ddd.version=${VERSION} -Xms${HEAP_XMS} -Xmx${HEAP_XMX} -jar -Dspring.profiles.active=${PROFILE} ${JAR_NAME}.jar

CONTINUE=1
while [ ${CONTINUE} -eq 1 ]
do
    sleep 1
    PORT_STATUS=`netstat -an | grep LISTEN | grep ":${SERVER_PORT}" | wc -l`
    if [ ${PORT_STATUS} -eq 1 ]
    then
      CONTINUE=0
    fi
done
