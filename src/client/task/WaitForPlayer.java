package client.task;

import client.ClientTCP;
import javafx.concurrent.Task;

public class WaitForPlayer extends Task<Boolean> {
    private ClientTCP connection;

    public WaitForPlayer(ClientTCP connection){
        this.connection = connection;
    }

    @Override
    public Boolean call(){
        return connection.receive().equals("player2connected");
    }
}
