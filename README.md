# INFO

test & clean

> mvn clean test

run

I used mvn exec:java``to start the server, but it has several problems (no logging and injection of parameters does not work).
Therefore you have to use the following command to start jetty:

> mvn -X jetty:run


# FAQ

## How to see dokku (server) logs?

1. login to server ssh root@gattr.com

2. run `docker ps` to list all apps

3. view logs of specific container `docker attach <container>

logs

## How to deploy with custom subdomain?

> git remote add server dokku@gattr.com:spryv3
> git push server

it will create the subdomain spryv3.gattr.com

## How to test it
> http://spryv3.gattr.com/info