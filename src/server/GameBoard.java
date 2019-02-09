package server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GameBoard {
    Lock lock;
    int[][] board;
    int[] lastMove;

    public GameBoard() {
        this.board = new int[3][3];
        setBlankBoard();
        lock = new ReentrantLock();
        lastMove = new int[2];
    }

    public void setNewSymbol(boolean leadingSymbol, int m, int n){
        if(leadingSymbol == true)
            board[m][n] = 1;
        else
            board[m][n] = 0;
        lastMove[0] = m;
        lastMove[1] = n;
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

    public boolean checkIfFinished(){
        int k = 0;
        while(k < 3) {
            if (board[k][0] == board[k][1] && board[k][2] == board[k][0])
                return true;
            else if(board[0][k] == board[1][k] && board[0][k] == board[2][k])
                return true;
        }
        if(board[0][0] == board[1][1] && board[0][0] == board[2][2])
            return true;
        else if(board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return true;
        else
            return false;

    }
}
