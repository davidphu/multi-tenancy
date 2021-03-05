Spring Keycloak Multi Tenant Project

## Prerequisites
* Add the following line into your /etc/hosts:
> 127.0.0.1 id.david.phu tenant1.davidphu.com tenant2.davidphu.com

## Instructions
* Create volumes for your docker containers

> mkdir ./keyclock-db
> 
> mkdir ./tenant1-db
> 
> mkdir ./tenant2-db
> 

* Start your keycloak servers in docker containers:
> ./run_dockers.sh

## References
https://www.czetsuyatech.com/2020/11/how-to-implement-multitenancy-with-spring-boot-and-keycloak.html
https://www.keycloak.org/docs/latest/securing_apps/index.html
https://www.keycloak.org/docs/latest/securing_apps/index.html#_multi_tenancy