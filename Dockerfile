FROM maven:3-openjdk-11-slim
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
COPY ./target/trexshelter-0.0.1-SNAPSHOT.jar /app/trex.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /app/wait
RUN chmod +x /app/wait
CMD /app/wait && "mvn" "test" && "java" "-jar" "trex.jar"

