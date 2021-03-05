[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

**Help me build more open-source projects by becoming my sponsor.*

# Spring Keycloak Multi Tenant Project
This project demonstrates multi-tenancy in a spring boot application connecting to Keycloak as an IdP for managing tenant data as separate realms.
It uses a single Keycloak instance with multiple realms.

## Prerequisites
- You must have prior knowledge with Spring and Keycloak.

## Instructions
- Set up docker resources: read the README.md under keycloak folder
- Run keycloak and mysql servers: in keycloak folder, run ./run_dockers.sh
- Set up demo database for each tenant
    - connect each tenant's DB server
    - create a database called 'demo'
    - create a user with creds username = 'demo' and password = 'password' 
    - Grant this user proper access rights to this database
    - connect to this DB from a mysql client
    - Run the DDL migration script under resources/db/migration
    - Run the DML migration script under resources/db/migration/<tenant-X>
- Start application
    - tenant 1 service is available at 'tenant1.davidphu.com'. Log in with 'tenant1-user1' user
    - tenant 2 service is available at 'tenant2.davidphu.com'. Log in with 'tenant2-user1' user
    

## References

- https://www.czetsuyatech.com/2020/11/how-to-implement-multitenancy-with-spring-boot-and-keycloak.html
- https://www.keycloak.org/docs/latest/securing_apps/index.html
- https://www.keycloak.org/docs/latest/securing_apps/index.html#_multi_tenancy
