package net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TcpServer{
    
    private final Sender sender;
    private TcpListener tcp;
    private Socket socket;
    private int listeningPort;
    private BufferedReader in;
    private BufferedWriter out;
    
    public TcpServer(Sender sender, int listeningPort) {
        this.sender = sender;
        this.listeningPort = listeningPort;
        clear();
    }
    
    public final void clear(){
        try {
            tcp = new TcpListener(this, listeningPort);
            tcp.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        socket = null;
    }
    
    public void setBuffers(Socket socket){
        this.socket = socket;
        try { 
            in =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void connect(String address, String port) {
        try {
            socket = new Socket(address, Integer.parseInt(port)); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        tcp = null;
        setBuffers(socket);
    }
    
    public void connected(Socket socket) {
        setBuffers(socket);
        tcp = null;
        sender.connected(true);
    }
    
    public void write(String data){
        try {
            out.write(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public String read() throws IOException {
        while(!in.ready()) {}
        return in.readLine();
    }
    
}
