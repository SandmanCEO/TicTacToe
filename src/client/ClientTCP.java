package client;

import server.GameBoard;

import java.io.*;
import java.net.Socket;

public class ClientTCP {
    Socket socket;
    PrintWriter out;
    BufferedReader in;

    public ClientTCP(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e){
            System.err.println(e);
        }
    }

    public void send(String text){
        try{
            out.println(text);
            out.flush();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    public boolean getLeadingSymbol() {
        String temp = null;
        int tempint = -1;
        try {
            temp = in.readLine();
            tempint = Integer.parseInt(temp);
            System.out.println(temp);
        } catch (Exception e) {
            System.err.println(e);
        }

        if(tempint == 0)
            return false;
        else if(tempint == 1)
            return true;
        else{
            System.out.println("NULL");
            return false;
        }
    }

    public String receive(){
        String temp = null;
        try{
            while(!in.ready()){

            }
            temp = in.readLine();
        }catch (Exception e){
            System.err.print(e);
        }
        return temp;
    }
}
