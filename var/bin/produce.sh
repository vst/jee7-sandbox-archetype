#!/bin/bash

curl --trace-ascii - --silent -X $1 -H "Authorization: Token ${AUTHC_TOKEN}" -H "Content-Type: application/json" --data "$3" -k https://localhost:8443/jee7-sandbox/api$2
