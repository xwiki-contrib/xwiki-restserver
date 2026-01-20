#!/bin/bash
PARAMETERS="-Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -DskipTests=true -Dxwiki.enforcer.enforce-java.skip=true"
mvn release:prepare -U ${PARAMETERS} -DautoVersionSubmodules=true -Pintegration-tests,quality -DskipTests=true -Darguments="${PARAMETERS}" && mvn release:perform -Pquality ${PARAMETERS} -Darguments="${PARAMETERS}"
