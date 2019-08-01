#!/bin/bash

url="************.japaneast.aksapp.io/sample/hello"
for i in {1..5000}; do
    content=$(curl --silent -X GET $url)
    echo $i : $content
    sleep 1
done

