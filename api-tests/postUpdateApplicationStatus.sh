#!/bin/bash

BASE_URL=http://localhost:8080/api/v1/application/1
REQUEST_BODY=$(cat <<EOF
{
  "status": "APPROVED"
}
EOF)

curl -v $BASE_URL \
  -H 'Content-type:application/json' \
  -d "$REQUEST_BODY"

