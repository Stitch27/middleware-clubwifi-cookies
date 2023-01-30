FROM openjdk:8-jre-slim
VOLUME /tmp
EXPOSE 8164
ARG JAR_FILE=target/clubwifi-cookies-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} clubwifi-cookies.jar
ENTRYPOINT ["java","-jar","clubwifi-cookies.jar"]