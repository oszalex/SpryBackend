SpryBackend
===

compile it:

> ./gradlew build

run it:

> ./gradlew bootRun


DOCKER
-----
Docker image is hosted in private docker repository (docker hub). Image name: inkrement/spry-backend

Try it local:
 docker run -p 8443:8443 -t inkrement/spry-backend
 https://192.168.59.103:8443/status (you get your local ip on MacOSX with "boot2docker ip")


TODO
----
 - MySQL
 - auto deployment


Docs
----

authentication


The flow of the username password authentication is then generally as follows:

A spring security filter (basic authentication/form/..) picks up the username and password, turns it into an UsernamePasswordAuthentication object and passes it on to the AuthenticationManager
The authentication manager looks for a candidate provider which can handle UsernamePasswordtokens, which in this case is the DaoAuthenticationProvider and passes the token along for authentication
The authentication provider invokes the method loadUserByUsername interface and throws either a UsernameNotFound exception if the user is not present or returns a UserDetails object, which contains a username, password and authorities.
The Authentication provider then compares the passwords of the provided UsernamePasswordToken and UserDetails object. (it can also handle password hashes via PasswordEncoders) If it doesn't match then the authentication fails. If it matches it registers the user details object and passes it on to the AccessDecisionManager, which performs the Authorization part.
So if the verification in the DaoAuthenticationProvider suits your needs. Then you'll only have to implement your own UserDetailsService and tweak the verification of the DaoAuthenticationProvider.


Install Tomcat
---

> sudo apt-get install tomcat8

> sudo touch /etc/authbind/byport/443
> sudo chmod 500 /etc/authbind/byport/443
> sudo chown tomcat7 /etc/authbind/byport/443

in `/etc/default/tomcat7` uncommend `Autobind=yes`and change port 8443 in `/etc/tomcat7/server.xml` to 443. Restart tomcat and turn ssl on.
by calling `service tomcat8 restart`.

You can test it with `netstat -a -n`.


Deploy to Tomcat
---

Upload war to `/var/lib/tomcat8/webapps/` (e.g. `scp v1.war root@api.gospry.com:/var/lib/tomcat7/webapps/`).

Interesting Tomcat files/locations:

> /var/log/tomcat8/logs/catalina.out  
> /var/log/tomcat8/logs/application_v2  
