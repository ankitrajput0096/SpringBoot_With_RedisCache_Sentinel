# Base Image ubuntu
FROM ubuntu:18.04

# Installing dependencies
RUN apt-get update
RUN apt-get install -y openjdk-8-jdk maven 

# coping the required application files
COPY . /SpringBoot_RedisSentinel_Client
WORKDIR /SpringBoot_RedisSentinel_Client/SpringBoot_redisCache_Sentinel

# building the spring boot app
RUN mvn clean install

# Changing the working directory
WORKDIR /SpringBoot_RedisSentinel_Client/SpringBoot_redisCache_Sentinel/target

# running the spring boot app
CMD ["java", "-jar", "redis_sentinel-0.0.1-SNAPSHOT.jar"]

