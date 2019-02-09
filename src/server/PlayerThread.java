package server;

import java.io.*;
import java.net.Socket;

public class PlayerThread extends Thread{
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    boolean leadingSymbol;
    GameBoard board;

    public PlayerThread(Socket socket, boolean leadingSymbol, GameBoard board) {
        this.socket = socket;
        this.leadingSymbol = leadingSymbol;
        this.board = board;

        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e){
            System.err.println(e);
        }
    }

    public void run(){
        int[] lastMove;
        String instruction = "";

        try {
            if(leadingSymbol == true) {
                out.println(1);
                out.flush();


                while(!instruction.equals("end")){
                    board.setLock();

                    lastMove = board.getLastMove();
                    for(int move : lastMove)
                        out.println(move);
                    out.flush();

                    instruction = in.readLine();

                    if(instruction.equals("action")){
                        board.setNewSymbol(leadingSymbol, Integer.parseInt(in.readLine()), Integer.parseInt(in.readLine()));
                        board.printBoard();
                        board.setUnlock();
                        sleep(500);
                    }
                }

            } else{
                out.println(0);
                out.flush();

                board.setLock();
                instruction = in.readLine();
                if(instruction.equals("action")){
                    board.setNewSymbol(leadingSymbol, Integer.parseInt(in.readLine()), Integer.parseInt(in.readLine()));
                    board.printBoard();
                    board.setUnlock();
                    sleep(500);
                }

                while(!instruction.equals("end")){
                    board.setLock();

                    lastMove = board.getLastMove();
                    for(int move : lastMove)
                        out.println(move);
                    out.flush();

                    instruction = in.readLine();

                    if(instruction.equals("action")){
                        board.setNewSymbol(leadingSymbol, Integer.parseInt(in.readLine()), Integer.parseInt(in.readLine()));
                        board.printBoard();
                        board.setUnlock();
                        sleep(500);
                    }
                }
            }

        } catch (Exception e){
            System.err.println(e);
        }
    }
}
