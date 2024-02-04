FROM openjdk:17-alpine

WORKDIR /usr/src/app
# WORKDIR working directory 경로
# 컨테이너의 루트 위치(절대경로)의 usr/src/app 폴더 하위에 저장

COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar ./build/libs/demo-0.0.1-SNAPSHOT.jar
# WORKDIR 위치(상대경로)의 build/libs 폴더에 demo-0.0.1-SNAPSHOT.jar 저장

CMD ["java", "-jar", "./build/libs/demo-0.0.1-SNAPSHOT.jar"]
# WORKDIR 위치(상대경로)의 build/libs 폴더에 demo-0.0.1-SNAPSHOT.jar 실행