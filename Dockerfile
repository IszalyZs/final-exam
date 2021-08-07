FROM openjdk:11-jre-slim
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait
COPY ./target/trexshelter-0.0.1-SNAPSHOT.jar /trex.jar
CMD /wait && "java" "-jar" "trex.jar"

