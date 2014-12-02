#!/bin/bash

curl --silent -X $1 -H "Authorization: Token ${AUTHC_TOKEN}" -k https://localhost:8443/jee7-sandbox/api$2
