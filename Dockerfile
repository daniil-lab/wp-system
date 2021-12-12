FROM gradle:7.3-jdk17 as build

RUN apt-get update

RUN apt-get install git

RUN git clone https://daniil-lab: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@github.com/daniil-lab/wp-system.git wp

WORKDIR wp

RUN gradle clean build

FROM nginx:alpine

COPY --from=build /wp/build/libs/system-dev.jar .

RUN apk add --no-cache java-cacerts

ENV JAVA_HOME=/opt/openjdk-17
ENV PATH=/opt/openjdk-17/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin

RUN java -jar system-dev.jar

