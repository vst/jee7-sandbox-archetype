#!/bin/bash

export AUTHC_TOKEN=`curl --silent -X GET -k https://admin:admin@localhost:8443/jee7-sandbox/api/login | cut -f "2" -d ":" | tr -d '"}'`
