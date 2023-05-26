#!/usr/bin/sh
# 개발 BUILD_STAGE를 입력하기 위해 스크립트 분리
# Application Start
SERVICE_DIR=/java/sales-statistics/
JAR_NAME=stat
ACCOUNT_HOME=/home/ec2-user
SERVER_PORT=8080

# Heap size 4GB
HEAP_XMS=4096m
HEAP_XMX=4096m

cd ${ACCOUNT_HOME}${SERVICE_DIR}

# 인스턴스 실행
screen -S ${JAR_NAME} -dm -jar ${JAR_NAME}.jar

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