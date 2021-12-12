FROM gradle:7.3.1-jdk17

RUN apt-get update

RUN apt install git

RUN git clone https://daniil-lab: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@github.com/daniil-lab/wp-system.git wp

WORKDIR wp

RUN gradle build