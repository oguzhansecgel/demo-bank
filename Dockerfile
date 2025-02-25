FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
COPY --from=build /app/target/bank-demo-0.0.1-SNAPSHOT.jar /app/bank-demo.jar
CMD ["java", "-jar", "/app/bank-demo.jar"]