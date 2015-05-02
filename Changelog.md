02.05.2015
    - Now you can build/push a new docker image with 
    ./gradlew pushDocker (it will be auto-deployed)
    - Java Version is updated to Java 8 (because it's faster and a 
    few libs require java8). There are no drawbacks because docker allows
    to run java 8 even on old machines
    - the basic security settings are moved from the WebSecurityConfig to 
    the application.yml