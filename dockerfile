FROM openjdk:8

RUN mkdir /transaction

WORKDIR /transaction

COPY . ./transaction
ADD . /transaction

EXPOSE 8089

CMD ["java", "-jar", "target/transaction-service-2.5.0-SNAPSHOT.jar"]