FROM amd64/amazoncorretto:21

ARG JAR_FILE=pophory-api/build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=test", "/app.jar"]