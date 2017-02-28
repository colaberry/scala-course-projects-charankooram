
# MicroServices Based Genomic Data Manipulation and Search Application.

The purpose of this application is to learn and get familiar with the nuances of 
Scala Programming in the context of a usecase very similar to what can be found in any
generic Enterprise. Performing some manipulations on a given data and provide a way to
query the data for any information. 

## Description
The final Capstone project is divided into 4 stages of micro-services. Each one is designed to 
act as a temporary back-up storage for the next level  and to act as an independent entity each
accomplishing a portion of the overall business goal. 

The first Micro-Service is designed to read content from a given file and create a new producer 
record from each line to publish onto a specific topic on a kafka instance.

The second Micro-Service is desinged to provide an ability to perform any data purification or 
enhancement or data manipulation desired before proceeding to the next step. Here the service consumes
the topic used in the previous stage, and manipulates the data before storing into another topic on the 
same kafka instance.

The third Micro-Service is designed to load the data into an instance of Elastic Search. This is 
accomplished in this service by consuming data from the topic containing the modified data and making 
HTTP requests using appropriate Elastic search api to load data into the search service.

The fourth Micro-Service accepts HTTP Get requests to accept queries on the indexes stored in the previous
step.

This kind of architecture makes sense in the context of big data in motion that is designed to under-go
several modifications in several stages.

## Build instructions

We have 4 project folders in our Final project directory. They are called MicroServiceOne-Kafka Producer;
MicroServiceTwo-KafkaConsumerOnTopics; MicroService3-Commit-To-Elastic-Search;
Micro-Service4-api-get-data.

Since we want our consumers to be already up and running before the producer starts
writing data in the first service. First start the second and third micro-services.
Then start the first micro-services project.

cd /MicroServiceTwo-KafkaConsumerOnTopics/ 
sbt run 

cd /MicroService3-Commit-To-Elastic-Search/ 
sbt run 

cd /MicroServiceOne-Kafka/
sbt run

Allow for the third micro-service to load all data into elastic search.

cd  /Micro-Service4-api-get-data/
sbt run

Now the data is ready to be queried on the address 127.0.0.1:8080/api/{id_number}

## Configurations specified

Add the following configurations to each application.conf file in every project.
These values can be changed at any time after deployment to run the project
without rebuilding the whole project again.

## Micro Service One
|~|~|
|---|---|
|ip-address of service|"localhost"|
|port number of service (kafka)|"9092"|
|file-name of the input data file (entire path)|"C:\\Users\\Colaberry2017\\Downloads\\1000genomes2.csv"|
|topic-name|"topicA"|

## Micro Service Two
|~|~|
|---|---|
|ip-address of service|"localhost"|
|port|"9092"|
|group|"group1"|
|consumer-topic (should be same as the topic name in the first service)|"topicA"|
|producer-topic (topic name containing new data)|"topic3"|

## Micro Service Three
|~|~|
|---|---|
|ip-address of elastic service|"localhost"|
|port number| 9200| 
|filepath (original data file path for internal reasons)|"C:\\Users\\Colaberry2017\\Downloads\\1000genomes2.csv"|
|bootstrap-servers (ip and port number of kafka instance)|"localhost:9092"| 
|group (consumer group name)|"group1"| 
|uri (url with index and object name in the elastic search instance)|"/capstone223510/genomes/"| 
 
## Micro Service Four
|~|~|
|---|---|
|interface (ip-address of the service)|"localhost"|
|port (http port)|80| 
|uri (elastic search query string)|"/capstone223510/genomes/"| 

## Results

The data consists of comma separated  valued file with about 62 columns and 3502 lines
Like hinted below

![~](readme-data-show.PNG)

When not using docker, first deploy kafka; Configure constants correctly in the config files and start
the micro-services two and three to start the Kafka Consumers.

![~](readme-final-start2and3.PNG)

Start MicroService One to start the Kafka Producer. We should start observing service-2
to print modified data into it's console and service-3 printing json body for each
elastic search load request

![~](readme-starting-service1.PNG)

Start fourth service to provide api to access this data from a web-browser or any related client

![~](readme-fourth-service-started.PNG)

Snapshot of all 62 columns retrieved for a particular id of the record

![~](readme-browser-test.PNG)


 


 


 


 


