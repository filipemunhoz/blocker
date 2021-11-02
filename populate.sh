#!/bin/bash

for i in $(seq  1 255); do
	for j in $(seq  1 255); do
		#echo $i - $j
    	curl -X POST http://localhost:8080/ -H 'Content-Type: application/json' -d '{"address": "192.167.'$i'.'$j'", "origin":"bash"}';
	done
done
