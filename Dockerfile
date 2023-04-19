FROM maven:3.9-amazoncorretto-19
WORKDIR /usr/src/server
COPY . .
RUN mvn package
CMD java -jar ./target/DoodleBackend-0.0.1.jar
