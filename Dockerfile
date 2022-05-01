FROM gradle:7.3-jdk17 as build

RUN apt-get update

RUN apt-get install git

RUN git clone https://daniil-lab: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@github.com/daniil-lab/wp-system.git wp

WORKDIR wp

RUN gradle clean build

FROM ghcr.io/graalvm/jdk:java17-21.3.0

COPY --from=build /home/gradle/wp/build/libs/system-dev.jar .

 ENV JAVA_TOOL_OPTIONS "-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=4855 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=188.225.45.41"/

COPY ./images ./images

EXPOSE 8080

ENTRYPOINT java -Xmx12144M -jar system-dev.jar

