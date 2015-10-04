package net;

import ctrl.TicTacToe;

public class Sender {
    
    private final TicTacToe game;
    private TcpServer tcp;
    private int tcpPort;
    private UdpServer udp;
    private int udpPort;
    private boolean isTcp;
    private boolean connected;
    
    public Sender(TicTacToe t){
        game = t;
        tcpPort = (int) (Math.random() * 100 + 1000);
        System.out.println("porta tcp: " + tcpPort);
        udpPort = (int) (Math.random() * 100 + 1000);
        System.out.println("porta udp: " + udpPort);
        game.createDialog("TCP: " + tcpPort + "\nUDP: " + udpPort, "Escutando nas portas");
        tcp = new TcpServer(this, tcpPort);
        udp = new UdpServer(this, udpPort);
    }

    public void connect(String address, String port, boolean isTcp) {
        this.isTcp = isTcp;
        
        if(isTcp)
            tcp.connect(address, port);
        else
            udp.connect(address, port, null);
        
        connected = true;
    }
    
    public void writePlay(int pos) {
        if(isTcp)
            tcp.write(pos + ""); 
        else
            udp.write(pos + "");
    }
    
    public void readPlay() {
        PlayReader reader = new PlayReader(tcp, udp, game, isTcp);
        reader.start();
    }

    public void connected(boolean isTcp) {
        this.isTcp = isTcp;
        if(!connected){
            connected = true;
            game.connected();
        }
    }
    
    public void makePlay(int player, int pos){
        game.setLastPlay(1);
        game.makePlay(2, pos);
    }
    
}
