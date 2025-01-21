# Use the official Java image from the Docker hub
FROM openjdk:17-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Install Maven manually
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://archive.apache.org/dist/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.tar.gz && \
    tar -xvzf apache-maven-3.8.6-bin.tar.gz && \
    mv apache-maven-3.8.6 /opt/maven && \
    ln -s /opt/maven/bin/mvn /usr/bin/mvn && \
    rm -rf apache-maven-3.8.6-bin.tar.gz
# Copy the Maven or Gradle project file and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the entire project and compile it
COPY src ./src
RUN mvn clean package -DskipTests

# Create a minimal runtime image using OpenJDK
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/smc-core-0.0.1-SNAPSHOT.jar /app/txn-smc-core.jar

# Expose port 8080 for the Spring Boot app
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "/app/txn-smc-core.jar"]
