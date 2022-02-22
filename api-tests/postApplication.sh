#!/bin/bash

BASE_URL=http://localhost:8080/api/v1/application
REQUEST_BODY=$(cat <<EOF
{
  "applicationStatus": "NEW",
  "projectName": "Teset3434",
  "categoryId": 1,
  "subcategoryId": 1,
  "shortDescription": "projectEndDate",
  "targetBudget": 1111,
  "projectEndDate": 12312335443533
}
EOF)

curl -v $BASE_URL \
  -H 'Content-type:application/json' \
  -d "$REQUEST_BODY"

