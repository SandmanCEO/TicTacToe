package client;

import java.io.*;
import java.net.Socket;

public class ClientTCP{
    private PrintWriter out;
    private BufferedReader in;

    ClientTCP(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void send(String text){
        try{
            out.println(text);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    boolean getLeadingSymbol() {
        int temp = -1;
        try {
            temp = Integer.parseInt(in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(temp == 0)
            return false;
        else if(temp == 1)
            return true;
        else{
            System.out.println("NULL");
            return false;
        }
    }

    public String receive(){
        String temp = null;
        try{
            temp = in.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }

}
