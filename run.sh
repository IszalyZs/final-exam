#!/bin/bash

#docker build -t trex -f Dockerfile .
#docker network create trexnet
#docker run --name mysql-standalone -p 3333:3306 --network trexnet -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=trex -e MYSQL_USER=sa -e MYSQL_PASSWORD=password -d mysql:5.7.35
#docker run --name trex -t --rm -p 8080:8080 --network trexnet users-mysql java -jar trex.jar --spring.datasource.url=jdbc:mysql://mysql-standalone:3306/trex

#docker logs mysql-standalone
#docker logs users-mysql
#docker run -d --name mysqldb -p 3333:3306 --network trexnet -e MYSQL_ROOT_PASSWORD="password" mysql:latest
#docker run --name trex -t --rm -p 8080:8080 --network trexnet trex java -jar trex.jar --spring.datasource.url=jdbc:mysql://mysqldb:3306/trex
#sudo docker-compose up

#sudo docker run --name=mysqlcontainer -d mysql/mysql-server:latest

docker run -d -p 3333:3306 --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=trex mysql:latest

docker build -t trex -f Dockerfile .
docker network create trexnet
./waitForMySQL.sh
docker network connect trexnet mysqldb
#docker container inspect mysqldb
docker run -p 8888:8080 --name trex --net trexnet -e MYSQL_HOST=mysqldb -e MYSQL_USER=root -e MYSQL_PASSWORD=root -e MYSQL_PORT=3306 trex


