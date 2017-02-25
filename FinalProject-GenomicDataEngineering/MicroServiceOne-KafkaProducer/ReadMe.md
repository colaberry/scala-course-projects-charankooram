# MicroService for Kafka Producer

As mentioned in the description of the project, the first service is designed to read
data from a particular source ( a file in this case); and publish records into a kafka 
instance.

Programatically, this was acheived using Reactive Kafka api which is like a Akka streams
connector for Apache Kafka . The framework allows a lightweight composition of a flow, comprising of 
Source, and a Sink along with various stages within to perform business logic to the ingesting data.

The Source in this service is created from a Iterator of a file Object. The Sink is a Kafka Producer.

As mentioned in the description of the final project, filenames, topic-names, ip-address and port number
of Kafka connections are all configurable from the config file.

##Build instruction
Navigate to the project directory and run the project using the command
sbt run.

