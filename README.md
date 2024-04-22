
# Deployment (1 variant)

Чтобы развернуть этот проект на вашем localhost:8080, убедитесь, что
----
* 1.1 У вас установлена Java на вашем компьютере. Если нет, установите ее отсюда [ссылка](https://www.java.com/en/download/).
* 1.2 Установите JDK версии 20. Если нет, загрузите ее отсюда [ссылка](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html).
* 1.3 Установите gradle версии 8. Если нет, загрузите ее отсюда [ссылка](https://gradle.org/install/).
* 1.4 Вы также должны уметь работать с репозиториями на [GitHub](https://help.github.com/articles/set-up-git).
* 1.5 Создайте директорию для удобства
 ```bash
mkdir project1
``` 
* 1.6 Затем, перейдите в нее
 ```bash
cd project1
``` 
* 1.7 Клонируйте удаленный репозиторий к себе на локальную.
```bash
git clone https://github.com/xzeexcz/fmdskmfsdklmfdskm.git
```
### Но, перед тем, как мы продолжим удостовертесь, что вы имеете установленные PostgreSQL 3.3v and Cassandra 7v+. 
----
Если нет, установите БД на локальную машину и создайте БД по этим данным: 
* PostgresSQL: 
    * Port: 2345 
    * Username: myuser
    * Password: qweqwe
* Apache Cassandra: 
    * Port: 9042
    * Database: adik
    * Username: cassandra
    * Password: cassandra
Также, БД можно запустить через  [Docker](https://www.docker.com/products/docker-desktop/). 
* Установие приложение, войдите в аккаунт а потом введите в командную строку:
 ```bash
docker run --name my-cs -p 9042:9042 -d cassandra:latest
``` 
* Затем, после того как запустился контейнер my-cs, введите эту команду: 
 ```bash
docker exec -it my-cs cqlsh
``` 
* Затем, когда вы зашли в CLI БД, введите: 
 ```bash
CREATE KEYSPACE IF NOT EXISTS adik WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
``` 
* После того, как запустился контейнер my-cs, нам следует поднять контейнер с БД postgres
```bash
docker run -d —name my-pg-db -e POSTGRES_PASSWORD=qweqwe -e POSTGRES_USER=myuser -p 2345:2345 postgres:13.3
``` 
* 1.8 После того как все контейнеры поднялись, запускаем эту команду в КОРНЕВОЙ папке проекта
```bash
gradle build 
``` 
* 1.9 Слудующая команда: 
```bash
gradle bootRun
``` 
* 1.9.1 После успешного выполнения, выполняем эту команду:
```bash
java -jar build/libs/YOUR_BUILD.jar
``` 

###  А затем вы можете скачать postman-file.json для выполнения тестирования всех эндопинтов. Все существующие эндпоинты расписаны.

# Deployment (2 variant)

--- 
Чтобы запустить проект через докер вам поднадобиться: 
* [Docker Desktop](https://www.docker.com/products/docker-desktop/) 
    * Docker account

А также:
* 2.1 Создайте пустую директорию для большего комфорта.
 ```bash
mkdir project1
``` 
* 2.2 Папка куда склоните удаленный репо на свой локальный.
 ```bash
cd project1
``` 
* 2.3 Клонируйте.
```bash
git clone https://github.com/xzeexcz/fmdskmfsdklmfdskm.git
```
* 2.4 После этого запустите эту команду чтобы поднять скрипты докера и необходимые контейнеры: 
```bash
docker compose up -d --build
```
### А затем вы можете скачать postman-file.json для выполнения тестирования всех эндопинтов. Все существующие эндпоинты расписаны.
