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
    private BufferedReader in;
    private BufferedWriter out;
    private final int port;
    
    public TcpServer(Sender sender, int port) {
        this.sender = sender;
        this.port = port;
        clear();
    }
    
    public final void clear(){
        try {
            tcp = new TcpListener(this, port);
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
        System.out.println("vai ler");
        return in.readLine();
    }
    
}
