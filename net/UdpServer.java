package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.bind.Marshaller;

public class UdpServer extends Thread {

    private DatagramSocket serverSocket;
    private String address;
    private int port;
    private String msg;

    public UdpServer() {
        clear();
    }

    public final void clear() {
        try {
            int p = (int) (Math.random() * 100 + 1000);
            System.out.println("porta: " + p);
            serverSocket = new DatagramSocket(p);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void connect(String address, String port) {
        this.address = address;
        this.port = Integer.parseInt(port);

        UdpListener listener = new UdpListener(this.port);     
        Thread tListenner = new Thread(listener);
        tListenner.start();
    }

    public void write(int pos) {

        try {
            InetAddress addr = InetAddress.getByName(this.address);
            byte[] bMsg = "oioioi".getBytes();

            //Monta o pacote a ser enviado
            DatagramPacket pkg = new DatagramPacket(bMsg, bMsg.length, addr, this.port);

            //Cria o datagram que será responsavel por enviar a mensagem
            DatagramSocket ds = new DatagramSocket();
            ds.send(pkg);
            System.out.println("Mensagem enviada para:" + addr.getCanonicalHostName());
            ds.close();
        } catch (Exception ex) {
            Logger.getLogger(UdpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
