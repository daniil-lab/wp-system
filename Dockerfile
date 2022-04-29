FROM gradle:7.3-jdk17 as build

RUN apt-get update

RUN apt-get install git

RUN git clone https://daniil-lab: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@github.com/daniil-lab/wp-system.git wp

WORKDIR wp

RUN gradle clean build

FROM openjdk:17-alpine

COPY --from=build /home/gradle/wp/build/libs/system-dev.jar .

COPY ./images ./images

RUN apk add chromium

EXPOSE 8080

ENTRYPOINT java -Xmx6144M -d64 -jar system-dev.jar

