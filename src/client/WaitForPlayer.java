package client;

import javafx.concurrent.Task;

public class WaitForPlayer extends Task<Boolean> {
    ClientTCP connection;

    public WaitForPlayer(ClientTCP connection){
        this.connection = connection;
    }

    @Override
    public Boolean call() throws Exception{
        String instruction = "";
        instruction = connection.receive();

        if(instruction.equals("player2connected"))
            return true;
        else
            return false;
    }
}
