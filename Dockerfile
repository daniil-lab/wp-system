FROM maven:3.8.4-openjdk-17 as build

RUN apk add git

RUN git clone https://daniil-lab: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@github.com/daniil-lab/wp-system.git wp

WORKDIR wp

RUN mvn package -DskipTests

FROM adoptopenjdk/openjdk11

COPY --from=build wp/target/walletbox-dev.jar .

COPY ./images ./images

EXPOSE 8080

ENTRYPOINT java -jar walletbox-dev.jar

