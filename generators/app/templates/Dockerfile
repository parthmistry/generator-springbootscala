FROM openjdk:<%= (SCALA_VERSION === '2.11') ? '8-jre-alpine' : '10-jre-slim' %>

ENV JAVA_OPTS=""

ADD ./build/libs/*.jar /app.jar

EXPOSE 8080
CMD java ${JAVA_OPTS} -jar /app.jar
