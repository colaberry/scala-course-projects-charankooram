# MicroService for Data Enhancement

This service performs modification of the ingested data before storing it into the Elastic Search.

Source is created from a Kafka Consumer from a particular topic. And the Sink was created from a 
Kafka Producer to another topic.

## Build Instructions

Navigate into the project folder and use the command
sbt run
