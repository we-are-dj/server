#!/bin/bash

REPOSITORY=/home/ec2-user/app
START_REPOSITORY=/home/ec2-user/start

echo "> 구동 할 애플리케이션을 복사 합닏나."

cp $REPOSITORY/build/libs/*.jar $START_REPOSITORY

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(ls START_REPOSITORY/ | grep '.jar' | tail -n 1)

echo "현재 구동중인 어플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -9 $CURRENT_PID"
    sudo kill -9 $CURRENT_PID
    sleep 5
fi

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr START_REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

sudo chmod +x $JAR_NAME

sleep 2

echo "> $JAR_NAME 실행"


sudo nohup java -jar \
    -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-db.yml \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
#    $JAR_NAME 2>&1> $REPOSITORY/nohup.log &