package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UdpListener extends Thread {

    private int port;

    public UdpListener(int port){
        this.port = port;   
    }
    
    public void listenner() throws IOException {
        //Cria o DatagramSocket para aguardar mensagens
        DatagramSocket ds;
        try {
            ds = new DatagramSocket(this.port);
            System.out.println("Ouvindo a porta: " + this.port);

            byte[] bMsg = new byte[256];
            DatagramPacket pkg = new DatagramPacket(bMsg, bMsg.length);
            ds.receive(pkg);
            JOptionPane.showMessageDialog(null, new String(pkg.getData()).trim(), "Mensagem recebida", 1);
            ds.close();
        } catch (SocketException ex) {
            Logger.getLogger(UdpListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        try {
            listenner();
        } catch (IOException ex) {
            Logger.getLogger(UdpListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
