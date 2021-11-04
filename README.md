# blocker

## ipV4
curl -X POST http://localhost:8080/ -H 'Content-Type: application/json' -d '{"address": "200.167.99.200", "origin":"bash"}' 

## IpV6
curl -X POST http://localhost:8080/ -H 'Content-Type: application/json' -d '{"address": "fe80::a8c6:6cff:fe96:7fd1", "origin":"bash"}'


## Find on Cache
curl localhost:8080/200.167.99.200
curl localhost:8080/fe80a8c66cfffe967fd1
