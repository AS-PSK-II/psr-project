FROM gradle:jdk22 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:22-jdk AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/edge-1.0.0.jar /app/edge-1.0.0.jar
EXPOSE 12345

ENTRYPOINT ["java", "-jar", "edge-1.0.0.jar"]
