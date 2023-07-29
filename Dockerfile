FROM ubuntu:latest
FROM openjdk:20
LABEL authors="Eugene Landarenko"
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN java Main.java

ENTRYPOINT ["top", "-b"]