# INFO

test & clean

> mvn clean test

run

I used mvn exec:java``to start the server, but it has several problems (no logging and injection of parameters does not work).
Therefore you have to use the following command to start jetty:

> mvn -X jetty:run