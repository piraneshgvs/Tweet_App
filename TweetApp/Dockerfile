# define base docker image
FROM openjdk:8
LABEL maintainer="piraneshs"
ADD target/TweetApp-0.0.1-SNAPSHOT.jar tweet-app.jar
ENTRYPOINT ["java", "-jar", "tweet-app.jar"]