FROM openjdk:11
ARG JAR_FILE=target/springboot-k8s-demo-1.0.0-RELEASE.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]