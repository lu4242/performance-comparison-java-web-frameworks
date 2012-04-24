================================================================================

Performance comparison java web frameworks

================================================================================

The aim of this project is provide a way to compare different java web frameworks
and how they work from performance perspective. Remember there are other more 
important considerations at the time to choose a web framework, for example its 
pluggability, speed of development, documentation and many more. There is no 
perfect web framework, and each one of them makes a compromise between performance 
and other considerations.

In this site you can find:

/perfbench-code      : Booking application using different frameworks.
/comparison-2012-APR : 
/state-size-test     : Test to check how JSF Partial State Saving algorithm works,
                       just type mvn clean jetty:run to see how it works.

In /perfbench-code you can find:

/myfaces-jpa  : JSF 2.0 booking application using MyFaces (current version 2.1.7)
/mojarra-jpa  : JSF 2.0 booking application using Mojarra (current version 2.1.7)
/wicket-jpa   : Wicket 1.4 booking application.
/wicket15-jpa : Wicket 1.5 booking application.

Other unused/deprecated modules :

/jmeter-utils : (deprecated) Use Tomcat 7.0.26 or later is better.
/tapestry-jpa
/grails-gorm
/grocket-jpa  
/swicket-jpa
/seam-jpa
/lib

For now only the tests related to JSF 2.0 and Wicket works, but I hope extend the
comparison in the future.

================================================================================

INSTRUCTIONS

================================================================================

Requeriments:

The code here requires the following programs installed:

Ant 1.7.x 
Maven 3.0.x
Java 1.6.x

Installing tomcat:

1. Download Tomcat 7.0.26 and uncompress it in your computer.

2. Change tomcat config to minimize interference (set autoDeploy="false", 
   remove AccessLogValve, ... ) See /comparison-2012-APR/tomcat-config/README.txt

3. Use JAVA_HOME, CATALINA_OPTS or whatever to set server params.
   See /comparison-2012-APR/tomcat-config/README.txt

Installing the webapp into tomcat:

Option a: Take a zip file in /comparison-2012-APR/webapps and uncompress it
          into <tomcat>/webapps directory. Remember use one tomcat server per
          test application.

Option b: Go for example to /perfbench-code/<you selected test app>-jpa and 
          execute "mvn install". Take the war or the uncompressed directory and
          copy it into <tomcat>/webapps

Running JMeter

1. Take the code from github.

2. Create a build.properties file pointing to your jmeter instalation and set
   context.host variable to the location where the server is.

3. Go to <you selected test app>-jpa (for example myfaces-jpa), and type:

   ant jmeter-custom

   Enter the required parameters and that's it. For warmup the server try:

   ant jmeter-custom-warmup

4. In <you selected test app>-jpa/src/test/jmeter there are two files:
  
   booking.jmx : jmeter script used by ant. Uses HttpClient4 as http request 
                 client.
   booking-jmeter-direct.jmx : the same as before but with some extra reports.
                               Open it with JMeter GUI and execute it manually.
                               Useful when you need to change the http request
                               client (Java, HttpClient4, ...).

================================================================================

About the code

================================================================================

The code stored in /perfbench-code was taken from:

http://code.google.com/p/perfbench/

/jmeter-utils licensed under ASL 2.0, test applications are derived work from 
seam 2.2.1 booking application example. Seam 2.2.1 is licensed under LGPL.

Unfortunately, the code, the tests and the initial analysis from that site are so
poor that are not worth mention it or even worst take it seriously. Instead, I 
took what's useful and I resolved all nasty problems to ensure a fair and 
accurate comparison.