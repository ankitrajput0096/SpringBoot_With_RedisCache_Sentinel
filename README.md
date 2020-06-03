# SpringBoot With Redis Cache Sentinel Cluster

Dockerized Spring Boot application with Redis cache sentinel cluster configuration and PostgreSQL.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development purposes. See running for notes on how to run the project on a system.

### Prerequisites

1. Clone the project to your local environment:
    ```
    git clone https://github.com/ankitrajput0096/SpringBoot_With_RedisCache_Sentinel
    ```

2. You need Docker to be installed:

    #### Windows:
    https://download.docker.com/win/stable/Docker%20for%20Windows%20Installer.exe
    
    #### Mac:
    https://download.docker.com/mac/stable/Docker.dmg
    
    #### Ubuntu:
    https://docs.docker.com/install/linux/docker-ce/ubuntu/

## Redis Cluster Configurations Details

There are following services in the cluster,

* master: Master Redis Server
* slave:  Slave Redis Server
* sentinel: Sentinel Server


The sentinels are configured with a "mymaster" instance with the following properties -

```
sentinel monitor mymaster redis-master 6379 2
sentinel down-after-milliseconds mymaster 5000
sentinel parallel-syncs mymaster 1
sentinel failover-timeout mymaster 5000
```

The details could be found in sentinel/sentinel.conf

The default values of the environment variables for Sentinel are as following

* SENTINEL_QUORUM: 2
* SENTINEL_DOWN_AFTER: 5000
* SENTINEL_FAILOVER: 5000

### NOTE : In this repository, we are using redis Template at DAO(Data access object) layer to use custom implementation of redis cache.

## Installing and Running

Build services
```
docker-compose build
```
Running services
```
docker-compose up
```
Check the status of redis cluster
```
docker-compose ps
```
The result is 
```
                        Name                                      Command               State                 Ports               
----------------------------------------------------------------------------------------------------------------------------------
springboot_with_rediscache_sentinel_backend_1          java -jar redis_sentinel-0 ...   Up      0.0.0.0:8090->8090/tcp            
springboot_with_rediscache_sentinel_db_1               docker-entrypoint.sh postgres    Up      0.0.0.0:5432->5432/tcp            
springboot_with_rediscache_sentinel_master_1           docker-entrypoint.sh redis ...   Up      6379/tcp                          
springboot_with_rediscache_sentinel_sentinel_one_1     entrypoint.sh                    Up      0.0.0.0:10001->26379/tcp, 6379/tcp
springboot_with_rediscache_sentinel_sentinel_three_1   entrypoint.sh                    Up      0.0.0.0:10003->26379/tcp, 6379/tcp
springboot_with_rediscache_sentinel_sentinel_two_1     entrypoint.sh                    Up      0.0.0.0:10002->26379/tcp, 6379/tcp
springboot_with_rediscache_sentinel_slave_1            docker-entrypoint.sh redis ...   Up      6379/tcp   
```

#### NOTE : Refer the below configuration of cluster as SENTINEL_QUORUM value is 2.
Scale out the instance number of slaves/Replica redis servers in cluster

```
docker-compose up --scale slave=4
```

Check the status of redis cluster

```
docker-compose ps
```

The result is 

```
                        Name                                      Command               State                 Ports               
----------------------------------------------------------------------------------------------------------------------------------
springboot_with_rediscache_sentinel_backend_1          java -jar redis_sentinel-0 ...   Up      0.0.0.0:8090->8090/tcp            
springboot_with_rediscache_sentinel_db_1               docker-entrypoint.sh postgres    Up      0.0.0.0:5432->5432/tcp            
springboot_with_rediscache_sentinel_master_1           docker-entrypoint.sh redis ...   Up      6379/tcp                          
springboot_with_rediscache_sentinel_sentinel_one_1     entrypoint.sh                    Up      0.0.0.0:10001->26379/tcp, 6379/tcp
springboot_with_rediscache_sentinel_sentinel_three_1   entrypoint.sh                    Up      0.0.0.0:10003->26379/tcp, 6379/tcp
springboot_with_rediscache_sentinel_sentinel_two_1     entrypoint.sh                    Up      0.0.0.0:10002->26379/tcp, 6379/tcp
springboot_with_rediscache_sentinel_slave_1            docker-entrypoint.sh redis ...   Up      6379/tcp                          
springboot_with_rediscache_sentinel_slave_2            docker-entrypoint.sh redis ...   Up      6379/tcp                          
springboot_with_rediscache_sentinel_slave_3            docker-entrypoint.sh redis ...   Up      6379/tcp                          
springboot_with_rediscache_sentinel_slave_4            docker-entrypoint.sh redis ...   Up      6379/tcp 
```


## Get an access to all exposed API's with Postman

1. Install Postman (https://www.getpostman.com)
2. Import Postman collection from the `SpringBoot_RedisCache_Sentinel.postman_collection.json` file
3. Enjoy !!

#### NOTE : To see redis cache sentinel cluster in action, observe terminal logs of `docker-compose`. When trying to hit same endpoint multiple times. 

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Spring Boot 2
* [Maven](https://maven.apache.org/) - Dependency Management
* [Docker](https://www.docker.com/) - For containerization of application
* [Redis](https://redis.io/) - For Redis
* [Redis Sentinel](https://redis.io/topics/sentinel) - For Redis Sentinel
* [PostgreSQL](https://www.postgresql.org/) - Database

## Contributing

If you have any improvement suggestions please create a pull request and I'll review it.


## Authors

* **Ankit Rajput** - *Initial work* - [Github](https://github.com/ankitrajput0096)

## License

This project is licensed under the MIT License



