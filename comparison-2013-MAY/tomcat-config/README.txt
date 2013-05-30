Instruction to setup tomcat

In server.xml

1. Set autoDeploy="false"

      <Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="false">

2. Remove AccessLogValve (comment it)

        <!--  MyFaces PerfBench: Remove AccessLogValve
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log." suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b" />
         -->


Use JAVA_HOME, CATALINA_OPTS or whatever to set server params. For example:

SET JAVA_HOME=C:\jdk1.6.0_30
SET JAVA_OPTS=-Xms128m -Xmx128m -server

in linux

export JAVA_HOME=C:\jdk1.6.0_30
export JAVA_OPTS="-Xms128m -Xmx128m -server"

In conf directory there are the files already changed 
for Apache Tomcat 7.0.37