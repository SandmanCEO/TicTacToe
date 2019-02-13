package client.task;

import client.ClientTCP;
import javafx.concurrent.Task;

public class GetInstruction extends Task<String> {
    private ClientTCP connection;

    public GetInstruction(ClientTCP connection) {
        this.connection = connection;
    }

    @Override
    public String call(){
        String instruction;
        instruction = connection.receive();
        System.out.println(instruction);
        return instruction;
    }
}
