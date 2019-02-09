package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4943);
            GameBoard board = new GameBoard();

             Socket socket1 = serverSocket.accept();
             new PlayerThread(socket1, false, board).start();

            Socket socket2 = serverSocket.accept();
            new PlayerThread(socket2, true, board).start();

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (serverSocket != null)
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
