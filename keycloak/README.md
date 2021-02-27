Spring Keycloak Multi Tenant Project

## Prerequisites
* Add the following line into your /etc/hosts:
> 127.0.0.1 external.auth.com my.auth.com

## Instructions
* Start your keycloak servers in docker containers:
> docker-compose stop
> 
> docker-compose rm -f
> 
> docker-compose up -d

* Setup TenantX Auth Server as Identity Provider
> go to id.tenant1.com:8280/auth
> 
> click Admin Console
> 
> log in using admin/admin (these are specified in the docker-compose.yaml)
> 
> create a realm with name "**tenant1**"
> 
> 


## References
https://www.czetsuyatech.com/2020/11/how-to-implement-multitenancy-with-spring-boot-and-keycloak.html
https://www.keycloak.org/docs/latest/securing_apps/index.html
https://www.keycloak.org/docs/latest/securing_apps/index.html#_multi_tenancy