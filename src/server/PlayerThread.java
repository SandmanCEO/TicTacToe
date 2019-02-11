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

    void setLeadingSymbol(boolean symbol){
        if(leadingSymbol == true)
            out.println(1);
        else
            out.println(0);
        out.flush();
    }

    void sendLastMove(){
        int[] lastMove;

        try {
            while (!board.isEnemyMadeMove()) {
                sleep(500);
            }
            System.out.println("getting last move");
            lastMove = board.getLastMove();
            board.setEnemyMadeMove(false);

            sendEnemyMove();

            for (int m : lastMove)
                out.println(m);
            out.flush();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    void sendWinner(){
        try{
            out.println("youWon");
            out.flush();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    void sendLoser(){
        try{
            out.println("youLose");
            out.flush();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    void sendDraw(){
        try{
            out.println("draw");
            out.flush();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    void sendNextMove(){
        try{
            out.println("nextMove");
            out.flush();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    void sendEnemyMove(){
        try{
            out.println("enemyMove");
            out.flush();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    public void run(){
        try {
            if(leadingSymbol == false) {
                setLeadingSymbol(false);
                while (!board.isPlayer2connected()){
                    sleep(500);
                }
                out.println("player2connected");
                out.flush();
                while (true){
                    if(board.isDraw()){
                        sendDraw();
                    } else if(board.isFinished()){
                        sendLoser();
                    }else {
                        sendNextMove();
                        board.setNewSymbol(leadingSymbol, Integer.parseInt(in.readLine()), Integer.parseInt(in.readLine()));
                        board.setEnemyMadeMove(true);
                        if (board.isFinished()) {
                            sendWinner();
                        }else if(board.isDraw())
                            sendDraw();
                        sleep(1000);
                        sendLastMove();
                    }
                }
            } else{
                setLeadingSymbol(true);
                board.setPlayer2connected(true);
                sendLastMove();
                while (true){
                    if(board.isDraw()){
                        sendDraw();
                    } else if(board.isFinished()){
                        sendLoser();
                    }else {
                        sendNextMove();
                        board.setNewSymbol(leadingSymbol, Integer.parseInt(in.readLine()), Integer.parseInt(in.readLine()));
                        board.setEnemyMadeMove(true);
                        if (board.isFinished()) {
                            sendWinner();
                        }else if (board.isDraw())
                            sendDraw();
                        sleep(1000);
                        sendLastMove();
                    }
                }

            }

        } catch (Exception e){
            System.err.println(e);
        }
    }
}
