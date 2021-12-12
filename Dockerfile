FROM gradle:7.3-jdk17 as build

RUN apt-get update

RUN apt-get install git

RUN git clone https://daniil-lab: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@github.com/daniil-lab/wp-system.git wp

WORKDIR wp

RUN gradle clean build

FROM openjdk:17-alpine

RUN apk add nginx

COPY --from=build /home/gradle/wp/nginx/nginx.conf /etc/nginx/nginx.conf

COPY --from=build /home/gradle/wp/build/libs/system-dev.jar .

RUN rm -rf /usr/share/nginx/html/*

EXPOSE 8080 80

ENTRYPOINT java -jar system-dev.jar

