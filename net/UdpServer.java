package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer {

    private final Sender sender;
    UdpListener listener;
    private DatagramSocket serverSocket;
    private String address;
    private int port;

    public UdpServer(Sender sender) {
        this.sender = sender;
        clear();
    }

    public final void clear() {
        try {
            int p = (int) (Math.random() * 100 + 1000);
            System.out.println("porta udp: " + p);
            serverSocket = new DatagramSocket(p);
            listener = new UdpListener(this, serverSocket);   
            listener.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void connect(String address, String port, boolean isPlay) {
        this.address = address;
        this.port = Integer.parseInt(port);
        if(isPlay)
            sender.readPlay();
    }

    public void write(String data) {

        try {
            InetAddress addr = InetAddress.getByName(this.address);
            String s = data;
            byte[] bMsg = s.getBytes();
            DatagramPacket pkg = new DatagramPacket(bMsg, bMsg.length, addr, this.port);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String read() throws IOException {
        byte[] bMsg = new byte[256];
        DatagramPacket pkg = new DatagramPacket(bMsg, bMsg.length);
        serverSocket.receive(pkg);
        return pkg.toString();
    }

}
