FROM gradle:7.5.1-jdk11 AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./

RUN gradle build --no-daemon -x test

COPY src ./src

RUN gradle build --no-daemon -x test

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=build /app/build/libs/your-app.jar /app/your-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/your-app.jar"]
