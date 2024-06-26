FROM amazoncorretto:17

ARG JAR_FILE=pophory-api/build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "/app.jar"]