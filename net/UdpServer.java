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
    
    public void connect(String address, String port, DatagramPacket pkg) {
        this.address = address;
        this.port = Integer.parseInt(port);
        if(pkg != null){
            sender.makePlay(2, Integer.parseInt(new String(pkg.getData(), 0, pkg.getLength()).trim()));
        }
        listener = null;
    }

    public void write(String data) {

        try {
            InetAddress addr = InetAddress.getByName(this.address);
            String s = data;
            byte[] bMsg = s.getBytes();
            DatagramPacket pkg = new DatagramPacket(bMsg, bMsg.length, addr, this.port);
            serverSocket.send(pkg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String read() throws IOException {
        byte[] bMsg = new byte[256];
        String s = "";
        while ("".equals(s)) {
            DatagramPacket pkg = new DatagramPacket(bMsg, bMsg.length);
            serverSocket.receive(pkg);
            s = new String(pkg.getData());
        }
        return s;
    }

}
