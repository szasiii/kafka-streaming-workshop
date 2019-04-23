#!/bin/bash

curl -H "Content-Type: application/json" -X PUT -d @$1 http://localhost:8083/connectors/${1%.*}/config
