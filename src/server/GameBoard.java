package server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameBoard {
    Lock lock;
    boolean player2connected, enemyMadeMove;
    int[][] board;
    int[] lastMove;
    int moves;

    public GameBoard() {
        this.board = new int[3][3];
        setBlankBoard();
        lock = new ReentrantLock();
        lastMove = new int[2];
        player2connected = enemyMadeMove = false;
        moves = 0;
    }

    public void setNewSymbol(boolean leadingSymbol, int m, int n){
        if(leadingSymbol == true)
            board[m][n] = 1;
        else
            board[m][n] = 0;
        lastMove[0] = m;
        lastMove[1] = n;
        moves += 1;
    }

    public int[] getLastMove(){
        return lastMove;
    }

    public void printBoard(){
        int i = 0;
        while( i < 3){
            int j = 0;
            while( j < 3){
                System.out.print(board[i][j] + " ");
                j += 1;
            }
            System.out.println("");
            i += 1;
        }
    }

    public void setBlankBoard(){
        int i = 0;
        while( i < 3){
            int j = 0;
            while( j < 3){
                board[i][j] = -1;
                j += 1;
            }
            i += 1;
        }
    }

    public void setLock(){
        lock.lock();
    }

    public void setUnlock(){
        lock.unlock();
    }

    public boolean isFinished(){
        int k = 0;
        while(k < 3) {
            if (board[k][0] == board[k][1] && board[k][2] == board[k][0] && board[k][0] != -1)
                return true;
            else if(board[0][k] == board[1][k] && board[0][k] == board[2][k] && board[0][k] != -1)
                return true;
            else
                k += 1;
        }
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != -1)
            return true;
        else if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != -1)
            return true;
        else
            return false;

    }

    public boolean isDraw(){
        if(!isFinished() && moves > 8)
            return true;
        else
            return false;
    }

    public boolean isPlayer2connected() {
        return player2connected;
    }

    public void setPlayer2connected(boolean player2connected) {
        this.player2connected = player2connected;
    }

    public boolean isEnemyMadeMove() {
        return enemyMadeMove;
    }

    public void setEnemyMadeMove(boolean enemyMadeMove) {
        this.enemyMadeMove = enemyMadeMove;
    }
}
