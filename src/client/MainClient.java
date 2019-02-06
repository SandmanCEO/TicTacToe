package client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class MainClient extends Application {
    Stage window;
    Scene setConnection;

    void setConnectionFunction(){
        Text portText = new Text ("Podaj numer portu serwera");
        TextField inputPort = new TextField();
        Text ipText = new Text ("Podaj IP");
        TextField inputIP = new TextField();
        Button exit = new Button ("Wyjście");
        exit.setOnAction( e -> {
            window.close();
        });
        Button connectButton = new Button ("Połącz");
        connectButton.setOnAction( e -> {
        });

        VBox setPortLayout = new VBox(10);
        setPortLayout.setPadding(new Insets(20,20,20,20));
        setPortLayout.getChildren().addAll(portText,inputPort, ipText, inputIP, connectButton, exit);

        setConnection = new Scene (setPortLayout, 600, 400);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("TicTacToe");
        setConnectionFunction();


        window.setScene(setConnection);
        window.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
