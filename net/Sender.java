package net;

import ctrl.TicTacToe;
import java.io.IOException;

public class Sender {
    
    private final TicTacToe game;
    private final TcpServer tcp;
    private final UdpServer udp;
    private boolean isTcp;
    
    public Sender(TicTacToe t){
        game = t;
        int p = (int) (Math.random() * 100 + 1000);
        System.out.println(p);
        //tcp = new TcpServer(this, p);
        udp = new UdpServer();
        tcp = null;
    }

    public void connect(String address, String port, boolean isTcp) {
        this.isTcp = isTcp;
        
        if(isTcp)
            tcp.connect(address, port);
        else
            udp.connect(address, port);
    }
    
    public void writePlay(int pos) {
        if(isTcp)
            tcp.write(pos + "\n"); 
        else
            udp.write(pos);
    }
    
    public void readPlay() {
        String read = "";
        if(isTcp)
            while("".equals(read)){
                try { read = tcp.read(); System.out.println("leu: " + read); } 
                catch (IOException ex) { ex.printStackTrace(); }
            }
        /*else
            while(read == ""){
                try { udp.receptor(); } 
                catch (IOException ex) { ex.printStackTrace(); }
            }*/
        
        game.makePlay(2, Integer.parseInt(read));
    }

    public void connected(boolean isTcp) {
        this.isTcp = isTcp;
        game.connected();
    }
    
}
