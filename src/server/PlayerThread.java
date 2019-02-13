package server;

import entity.GameBoard;

import java.io.*;
import java.net.Socket;

public class PlayerThread extends Thread{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean leadingSymbol;
    private GameBoard board;

    PlayerThread(Socket socket, boolean leadingSymbol, GameBoard board) {
        this.socket = socket;
        this.leadingSymbol = leadingSymbol;
        this.board = board;

        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setLeadingSymbol(){
        if(leadingSymbol)
            out.println(1);
        else
            out.println(0);
        out.flush();
    }

    private void sendLastMove(){
        int[] lastMove;

        try {
            while (!board.isEnemyMadeMove()) {
                sleep(500);
            }
            lastMove = board.getLastMove();
            board.setEnemyMadeMove(false);

            sendEnemyMove();

            for (int m : lastMove)
                out.println(m);
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendWinner(){
        try{
            out.println("youWon");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendLoser(){
        try{
            out.println("youLose");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendDraw(){
        try{
            out.println("draw");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendNextMove(){
        try{
            out.println("nextMove");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendEnemyMove(){
        try{
            out.println("enemyMove");
            out.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void userService(){
        String instruction = "";
        try {
            while (!instruction.equals("end")) {
                if (board.isDraw()) {
                    sendDraw();
                } else if (board.isFinished()) {
                    sendLoser();
                    break;
                } else {
                    sendNextMove();
                    instruction = in.readLine();
                    System.out.println(instruction);
                    if(instruction.equals("move")) {
                        board.setNewSymbol(leadingSymbol, Integer.parseInt(in.readLine()), Integer.parseInt(in.readLine()));
                        board.setEnemyMadeMove(true);
                        if (board.isFinished()) {
                            sendWinner();
                            break;
                        } else if (board.isDraw()) {
                            sendDraw();
                            break;
                        }
                        sleep(1000);
                        sendLastMove();
                    }
                }
            }
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            if(!leadingSymbol) {
                setLeadingSymbol();
                while (!board.isPlayer2connected()){
                    sleep(500);
                }
                out.println("player2connected");
                out.flush();
                userService();
            } else{
                setLeadingSymbol();
                board.setPlayer2connected(true);
                sendLastMove();
                userService();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
