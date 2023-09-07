#!/usr/bin/sh

PROFILE=dev
JAR_NAME=harrods
SERVER_PORT=8080
SERVICE_DIR=/java/harrods/
ACCOUNT_HOME=/home/ec2-user
SERVICE_HOME_DIR=${ACCOUNT_HOME}${SERVICE_DIR}

# Datadog
SERVICE_NAME=harrods
VERSION=1.0
DATADOG_JAVA_AGENT=${ACCOUNT_HOME}/dd-java-agent.jar

# Heap size 4GB (dev)
HEAP_XMS=4096m
HEAP_XMX=4096m

# 디렉토리 생성
if [ ! -d "${SERVICE_HOME_DIR}" ]; then
    mkdir -p "${SERVICE_HOME_DIR}"
    echo "directory created: ${SERVICE_HOME_DIR}"
else
    echo "directory already exist: ${SERVICE_HOME_DIR}"
fi

# 디렉토리 이동
cd ${SERVICE_HOME_DIR}

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
