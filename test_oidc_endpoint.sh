#!/usr/bin/bash

echo "emulate react app call to keycloak"


export access_token=$(\

    curl -v  -X POST "http://localhost:7080/auth/realms/geektour/protocol/openid-connect/token" \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d "grant_type=password&username=alice&password=alice&client_id=app-react" | jq --raw-output '.access_token' \
)

echo "access_token: $access_token"



curl -v -X GET \
  http://localhost:8080/reports \
  -H "Authorization: Bearer "$access_token


