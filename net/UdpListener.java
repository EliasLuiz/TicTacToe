package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpListener extends Thread {

    private DatagramSocket socket;
    private UdpServer server;

    public UdpListener(UdpServer server, DatagramSocket socket){
        this.server = server; 
        this.socket = socket;
    }
    
    @Override
    public void run(){
        try {
            byte[] bMsg = new byte[256];
            DatagramPacket pkg = new DatagramPacket(bMsg, bMsg.length);
            socket.receive(pkg);
            server.connect(pkg.getAddress().getHostAddress(), pkg.getPort() + "", pkg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
