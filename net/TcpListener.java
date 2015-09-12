package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpListener extends Thread {
    
    private ServerSocket serverSocket;
    private TcpServer server;

    public TcpListener(TcpServer server, int port) throws IOException {
        this.server = server;
        serverSocket = new ServerSocket(port);
    }
    
    @Override
    public void run(){
        Socket socket = null;
        while(socket == null){
            try{ socket = serverSocket.accept(); } 
            catch (IOException ex) { ex.printStackTrace(); }
        }
        server.connected(socket);
    }
    
}
