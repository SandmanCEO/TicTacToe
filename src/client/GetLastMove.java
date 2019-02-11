package client;

import javafx.concurrent.Task;

public class GetLastMove extends Task<int[]> {
    ClientTCP connection;

    public GetLastMove(ClientTCP connection) {
        this.connection = connection;
    }

    @Override
    public int[] call() throws Exception{
        int[] temp = new int[2];
        temp[0] = Integer.parseInt(connection.receive());
        temp[1] = Integer.parseInt(connection.receive());

        return temp;
    }
}
