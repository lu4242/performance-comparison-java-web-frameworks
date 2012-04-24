package jmeterutils;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxHeapDumper {

    public static void main(String[] args) throws Exception {
        // http://www.mhaller.de/archives/85-Java-heap-dumps.html
        String url = "service:jmx:rmi:///jndi/rmi://127.0.0.1:9004/jmxrmi";
        JMXServiceURL jmxURL = new JMXServiceURL(url);
        JMXConnector connector = JMXConnectorFactory.connect(jmxURL);
        MBeanServerConnection connection = connector.getMBeanServerConnection();
        ObjectName name = new ObjectName("com.sun.management:type=HotSpotDiagnostic");
        Object[] params = new Object[] { args[0], Boolean.TRUE };
        String[] signature = new String[] { String.class.getName(), boolean.class.getName() };
        try {
            connection.invoke(name, "dumpHeap", params, signature);
        } catch(Exception e) {
            System.out.println("heap dump attempt failed: " + e);
            System.out.println("trying alternate approach...");
            // http://blog.igorminar.com/2007/03/how-java-application-can-discover-its.html
            String jvmPid = (String) connection.getAttribute(new ObjectName("java.lang:type=Runtime"), "Name");
            String pid = jvmPid.substring(0, jvmPid.lastIndexOf('@'));
            System.out.printf("jvmPid = '%s', pid = '%s'\n", jvmPid, pid);
            String javaHome = System.getenv("JAVA_HOME");
            System.out.println("using JAVA_HOME from environment: '" + javaHome + "'");
            String command = javaHome + "/bin/jmap -dump:format=b,file=" + args[0] + " " + pid;
            System.out.println("running command: " + command);
            Process p = Runtime.getRuntime().exec(command);
            System.out.println("return code: " + p.waitFor());

        }
        System.out.println("*** heap dumped to: " + args[0]);
    }

}
