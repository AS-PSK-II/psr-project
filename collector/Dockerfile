FROM gradle:jdk22 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

FROM openjdk:22-jdk AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/collector-1.0.0.jar /app/collector-1.0.0.jar

ENTRYPOINT ["java", "-jar", "collector-1.0.0.jar"]