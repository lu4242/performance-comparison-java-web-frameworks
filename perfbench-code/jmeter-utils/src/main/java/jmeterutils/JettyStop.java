package jmeterutils;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class JettyStop {

    public static void main(String[] args) throws Exception {
        Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 8079);
        OutputStream out = s.getOutputStream();        
        String message = args.length > 0 ? args[0] : "";
        System.out.println("*** jetty stop: sending message: '" + message + "'");
        out.write((message + "\r\n").getBytes());
        out.flush();
        s.close();
    }
    
}
