FROM openjdk:11-jre
COPY "/app-launcher/build/libs/app-launcher.jar" app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]