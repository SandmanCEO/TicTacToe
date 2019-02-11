package client;

import javafx.concurrent.Task;

public class GetInstruction extends Task<String> {
    ClientTCP connection;

    public GetInstruction(ClientTCP connection) {
        this.connection = connection;
    }

    @Override
    public String call() throws Exception{
        String instruction;
        instruction = connection.receive();
        System.out.println(instruction);
        return instruction;
    }
}
