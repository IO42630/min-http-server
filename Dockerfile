# Build
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package assembly:single

# Run
FROM amazoncorretto:11
COPY --from=build /home/app/target/min-http-server-0.1-jar-with-dependencies.jar /usr/local/lib/min-http-server-0.1.jar
WORKDIR /home/app/target/
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/min-http-server-0.1.jar"]
