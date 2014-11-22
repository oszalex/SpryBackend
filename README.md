SpryBackend
===

compile it:

> ./gradlew build

run it:

> java -jar build/libs/ase-0.1.0.jar




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

> sudo apt-get install tomcat7

> sudo touch /etc/authbind/byport/80
> sudo chmod 500 /etc/authbind/byport/80
> sudo chown tomcat7 /etc/authbind/byport/80

in `/etc/default/tomcat7` uncommend `Autobind=yes`and change port 8080 in `/etc/tomcat7/server.xml` to 80. Restart tomcat
by calling `service tomcat7 restart`.

You can test it with `netstat -a -n`.


Deploy to Tomcat
---

Upload war to `/var/lib/tomcat7/webapps/` (e.g. `scp v1.war root@api.gospry.com:/var/lib/tomcat7/webapps/`).

Interesting Tomcat files/locations:

> /var/log/tomcat7/catalina.2014-11-19.log
> /var/lib/tomcat7/logs
