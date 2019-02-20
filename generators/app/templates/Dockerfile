FROM openjdk:11-jre-stretch

ENV JAVA_OPTS=""

ADD ./build/libs/*.jar /app.jar

EXPOSE 8080
CMD java ${JAVA_OPTS} -jar /app.jar
