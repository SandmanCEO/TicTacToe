package client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Integer.parseInt;

public class MainClient extends Application {
    Stage window;
    Scene setConnection, gameBoard;

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
            window.setScene(gameBoard);
        });

        VBox setPortLayout = new VBox(10);
        setPortLayout.setPadding(new Insets(20,20,20,20));
        setPortLayout.getChildren().addAll(portText,inputPort, ipText, inputIP, connectButton, exit);

        setConnection = new Scene (setPortLayout, 600, 400);
    }

    void setGameBoard() {
        Button button1 = new Button("Button 1");
        Button button2 = new Button("Button 2");
        Button button3 = new Button("Button 3");
        Button button4 = new Button("Button 4");
        Button button5 = new Button("Button 5");
        Button button6 = new Button("Button 6");
        Button button7 = new Button("Button 7");
        Button button8 = new Button("Button 8");
        Button button9 = new Button("Button 9");

        GridPane gridPane = new GridPane();

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 1, 0, 1, 1);
        gridPane.add(button3, 2, 0, 1, 1);
        gridPane.add(button4, 0, 1, 1, 1);
        gridPane.add(button5, 1, 1, 1, 1);
        gridPane.add(button6, 2, 1, 1, 1);
        gridPane.add(button7, 0, 2, 1, 1);
        gridPane.add(button8, 1, 2, 1, 1);
        gridPane.add(button9, 2, 2, 1, 1);
        gameBoard=new Scene(gridPane, 600,400);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("TicTacToe");

        setConnectionFunction();
        setGameBoard();

        window.setScene(setConnection);
        window.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
