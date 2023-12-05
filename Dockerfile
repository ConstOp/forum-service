FROM eclipse-temurin:11-jre-ubi9-minimal

WORKDIR /app

COPY ./target/forum-service-0.0.1-SNAPSHOT.jar ./forum-service.jar

ENV MONGODB_URI=mongodb+srv://kosta:12345.com@clusterjava48.zh1mk1b.mongodb.net/java48db?retryWrites=true&w=majority

CMD [ "java", "-jar", "/app/forum-service.jar" ]