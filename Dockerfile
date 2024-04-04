#
##build project:       mvn clean package
#docker build -t my-app .
#docker run -p 8585:8585 my-app
#
#FROM maven:3.8.4-openjdk-17-slim AS build
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the Maven project file
#COPY pom.xml .
#
## Download all Maven dependencies
#RUN mvn dependency:go-offline -B
#
## Copy the source code to the container
#COPY src ./src
#
## Package the application
#RUN mvn package -DskipTests
#
## Use AdoptOpenJDK as the base image for the final image
#FROM adoptopenjdk/openjdk17:alpine-jre
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the JAR file from the build stage to the current location
#COPY --from=build /app/target/Server-VTV-0.0.1-SNAPSHOT.jar ./app.jar
#
## Expose the port on which the Spring Boot application will run
#EXPOSE 8585
#
## Define the command to run the application when the container starts
#CMD ["java", "-jar", "app.jar"]
#







#build project:       mvn clean package

FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Download all Maven dependencies
RUN mvn dependency:go-offline -B

# Copy the source code to the container
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Use Eclipse Temurin as the base image for the final image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the current location
COPY --from=build /app/target/Server-VTV-0.0.1-SNAPSHOT.jar ./app.jar

# Expose the port on which the Spring Boot application will run
EXPOSE 8585

# Define the command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]