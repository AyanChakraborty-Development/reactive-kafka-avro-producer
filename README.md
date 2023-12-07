# reactive-kafka-avro-producer
Reactive kafka producer usign avro schema.

Bring up the docker compose file using the command below

docker-compose up -d

And in Azure create a container and in that create a folder with name json_data and put some block blobs in it.

then bring the service up and it will read from the json_data path, and read the contents and upload it to the kafka cluster that you got up usign docker compose file.

