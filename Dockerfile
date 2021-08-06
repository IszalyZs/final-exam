FROM maven:3-openjdk-11-slim
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
COPY ./target/trexshelter-0.0.1-SNAPSHOT.jar /app/trex.jar
CMD ["java","-jar","trex.jar"]