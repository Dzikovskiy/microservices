FROM adoptopenjdk:11-jre-hotspot
MAINTAINER dzikovskiy.com
COPY target/eurika-server-0.0.1-SNAPSHOT.jar eurika-server-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","/eurika-server-0.0.1-SNAPSHOT.jar"]