FROM gradle:jdk22 AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test

FROM openjdk:22-jdk AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/svc-historical-data-1.0.0.jar /app/svc-historical-data-1.0.0.jar

ENTRYPOINT ["java", "-jar", "svc-historical-data-1.0.0.jar"]