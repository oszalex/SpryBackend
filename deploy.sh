#!/bin/bash
echo -e "build"
gradle -Dskip.tests -Dserver.port=80 build 2> /dev/null

echo -e "upload file"
scp build/libs/spry-0.1.0.jar root@api.gospry.com:spry.jar
ssh -n -f root@api.gospry.com "sh -c 'killall java; nohup java -jar spry.jar > /dev/null 2>&1 &'"
