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
        tcp = new TcpServer(this);
        udp = new UdpServer(this);
    }

    public void connect(String address, String port, boolean isTcp) {
        this.isTcp = isTcp;
        
        if(isTcp)
            tcp.connect(address, port);
        else
            udp.connect(address, port, false);
    }
    
    public void writePlay(int pos) {
        if(isTcp)
            tcp.write(pos + "\n"); 
        else
            udp.write(pos + "\n");
    }
    
    public void readPlay() {
        String read = "";
        if(isTcp)
            while("".equals(read)){
                try { read = tcp.read(); } 
                catch (IOException ex) { ex.printStackTrace(); }
            }
        else
            while("".equals(read)){
                try { read = udp.read(); } 
                catch (IOException ex) { ex.printStackTrace(); }
        }
        
        game.makePlay(2, Integer.parseInt(read));
    }

    public void connected(boolean isTcp) {
        this.isTcp = isTcp;
        game.connected();
    }
    
}
