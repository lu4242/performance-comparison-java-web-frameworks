package jmeterutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyStart {

    private static Server server;
    private static boolean stop;

    public static void main(String[] args) throws Exception {
        String contextPath = args[0];
        String warPath = args[1];
        System.out.println("*** jetty main: context path: " + contextPath + ", war: " + warPath);
        Thread monitor = new MonitorThread();
        monitor.start();
        SocketConnector connector = new SocketConnector();
        connector.setPort(8080);
        createServer(connector, contextPath, warPath);
        while(!stop) {
            server.start();
            System.out.println("*** jetty main: started jetty");
            server.join();
        }
        System.out.println("*** jetty main: FULL STOP");
    }

    private static void createServer(SocketConnector connector, String contextPath, String warPath) {
        server = new Server();
        server.setConnectors(new Connector[]{connector});
        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath(contextPath);
        context.setWar(warPath);
        server.addHandler(context);
    }

    private static class MonitorThread extends Thread {

        private ServerSocket socket;

        public MonitorThread() {
            setDaemon(true);
            setName("StopMonitor");
            try {
                socket = new ServerSocket(8079, 1, InetAddress.getByName("127.0.0.1"));
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            System.out.println("*** jetty monitor: listening on 8079");
            while(true) {
                Socket accept = null;
                try {
                    accept = socket.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    String message = reader.readLine();
                    if(message.equals("restart")) {
                        System.out.println("*** jetty monitor: restart message received");
                        server.stop();
                        server.destroy();
                        System.out.println("*** jetty monitor: stopped jetty");
                    } else {
                        System.out.println("*** jetty monitor: stop message received");
                        stop = true;
                        server.stop();
                        accept.close();
                        socket.close();
                        break;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("*** jetty monitor: exiting");
        }
    }

}
