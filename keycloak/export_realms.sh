#!/bin/bash

docker exec -it app-idp /opt/jboss/keycloak/bin/standalone.sh \
-Djboss.socket.binding.port-offset=100 \
-Dkeycloak.migration.action=export \
-Dkeycloak.migration.provider=dir \
-Dkeycloak.migration.dir=/tmp/realms \
-Dkeycloak.migration.usersPerFile=1000 \
-Dkeycloak.migration.strategy=OVERWRITE_EXISTING