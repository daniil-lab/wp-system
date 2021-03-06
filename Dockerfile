FROM gradle:7.3-jdk17 as build

RUN apt-get update

RUN apt-get install git

RUN git clone https://daniil-lab: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@github.com/daniil-lab/wp-system.git wp

WORKDIR wp

RUN gradle clean build

FROM openjdk:17-oracle

COPY --from=build /home/gradle/wp/build/libs/system-dev.jar .

COPY ./images ./images

EXPOSE 8080

ENTRYPOINT java -jar system-dev.jar

