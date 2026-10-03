# Build
FROM maven:3.9-eclipse-temurin-25 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Run
FROM eclipse-temurin:25-jre
COPY --from=build /home/app/target/*-jar-with-dependencies.jar /usr/local/lib/min-http-server.jar
WORKDIR /home/app
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/min-http-server.jar"]
