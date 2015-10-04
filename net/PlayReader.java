package net;

import ctrl.TicTacToe;
import java.io.IOException;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class PlayReader extends Thread {
    
    private final TcpServer tcp;
    private final UdpServer udp;
    private final TicTacToe game;
    private final boolean isTcp;

    public PlayReader(TcpServer tcp, UdpServer udp, TicTacToe game, boolean isTcp) {
        this.tcp = tcp;
        this.udp = udp;
        this.game = game;
        this.isTcp = isTcp;
    }
    
    @Override
    public void run(){
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
        int p;
        try { p = Integer.parseInt(read); }
        catch (NumberFormatException e) {
            //Fazendo o que o Java por algum motivo faz errado as vezes
            p = read.chars().findFirst().getAsInt() - 48;
        }
        
        game.makePlay(2, p);
    }
    
    
}
