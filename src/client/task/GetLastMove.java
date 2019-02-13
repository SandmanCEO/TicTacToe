package client.task;

import client.ClientTCP;
import javafx.concurrent.Task;

public class GetLastMove extends Task<int[]> {
    private ClientTCP connection;

    public GetLastMove(ClientTCP connection) {
        this.connection = connection;
    }

    @Override
    public int[] call(){
        int[] temp = new int[2];
        temp[0] = Integer.parseInt(connection.receive());
        temp[1] = Integer.parseInt(connection.receive());

        return temp;
    }
}
