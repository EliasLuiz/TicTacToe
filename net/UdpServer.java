package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer extends Thread {
    
    private DatagramSocket serverSocket;
    private String address;
    private int port;
    
    public UdpServer() {
        clear();
    }
    
    public final void clear(){
        try { 
            int p = (int) (Math.random() * 100 + 1000);
            System.out.println("porta: " + p);
            serverSocket = new DatagramSocket(p); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        
    }
    
    public void connect(String address, String port) {
        this.address = address;
        this.port = Integer.parseInt(port);
    }
    
    public void write(int data) {
        String d = data + "";
        try {
            serverSocket.send( new DatagramPacket(
                    d.getBytes(), d.getBytes().length, 
                    InetAddress.getByName(address), port));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public int read() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket datagram = new DatagramPacket(buffer, port);
        serverSocket.receive(datagram);
        return Integer.parseInt(new String(datagram.getData()));
    }
    
}
