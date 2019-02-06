package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClient extends Application {
    Stage window;
    Scene setConnection;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("TicTacToe");



        window.setScene(setConnection);
        window.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
