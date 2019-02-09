package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import server.GameBoard;

import java.io.BufferedReader;

import static java.lang.Integer.parseInt;

public class MainClient extends Application {
    Stage window;
    Scene setConnection, gameBoard;
    ClientTCP connection;
    Button[] buttons;
    boolean leadingSymbol;
    GameBoard board;

    void setConnectionFunction(){
        Text portText = new Text ("Podaj numer portu serwera");
        TextField inputPort = new TextField();
        inputPort.setText("4943");
        Text ipText = new Text ("Podaj IP");
        TextField inputIP = new TextField();
        inputIP.setText("192.168.1.112");
        Button exit = new Button ("Wyjście");
        exit.setOnAction( e -> {
            window.close();
        });
        Button connectButton = new Button ("Połącz");
        connectButton.setOnAction( e -> {
            connection = new ClientTCP(inputIP.getText(), Integer.parseInt(inputPort.getText()));
            leadingSymbol = connection.getLeadingSymbol();
            System.out.println(leadingSymbol);
            board = new GameBoard();
            board.setBlankBoard();
            board.printBoard();
            if(leadingSymbol == false)
                window.setScene(gameBoard);
            else
                waitForEnemyMove();
        });

        VBox setPortLayout = new VBox(10);
        setPortLayout.setPadding(new Insets(20,20,20,20));
        setPortLayout.getChildren().addAll(portText,inputPort, ipText, inputIP, connectButton, exit);

        setConnection = new Scene (setPortLayout, 600, 400);
    }

    void waitForEnemyMove(){
        try{
            int m, n;
            m = Integer.parseInt(connection.receive());
            n = Integer.parseInt(connection.receive());
            if(leadingSymbol == true)
                buttons[m * 3 + n].setText("O");
            else
                buttons[m * 3 + n].setText("X");

            window.setScene(gameBoard);
        } catch (Exception e){
            System.err.println(e);
        }
    }

    void setGameBoard() {
        buttons = new Button[9];

        int index = 0;
        while(index < 9){
            buttons[index] = new Button("  ");
            index += 1;
        }

        buttons[0].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[0].setText("X");
            else
                buttons[0].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(0));
            connection.send(String.valueOf(0));

            waitForEnemyMove();
        });

        buttons[1].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[1].setText("X");
            else
                buttons[1].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(0));
            connection.send(String.valueOf(1));
            waitForEnemyMove();
        });

        buttons[2].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[2].setText("X");
            else
                buttons[2].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(0));
            connection.send(String.valueOf(2));

            waitForEnemyMove();
        });

        buttons[3].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[3].setText("X");
            else
                buttons[3].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(1));
            connection.send(String.valueOf(0));

            waitForEnemyMove();
        });

        buttons[4].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[4].setText("X");
            else
                buttons[4].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(1));
            connection.send(String.valueOf(1));

            waitForEnemyMove();
        });

        buttons[5].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[5].setText("X");
            else
                buttons[5].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(1));
            connection.send(String.valueOf(2));

            waitForEnemyMove();
        });

        buttons[6].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[6].setText("X");
            else
                buttons[6].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(2));
            connection.send(String.valueOf(0));

            waitForEnemyMove();
        });

        buttons[7].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[7].setText("X");
            else
                buttons[7].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(2));
            connection.send(String.valueOf(1));

            waitForEnemyMove();
        });

        buttons[8].setOnAction( e -> {
            if(leadingSymbol == true)
                buttons[8].setText("X");
            else
                buttons[8].setText("O");
            window.setScene(gameBoard);

            connection.send("action");
            connection.send(String.valueOf(2));
            connection.send(String.valueOf(2));

            waitForEnemyMove();
        });

        GridPane gridPane = new GridPane();

        int i, j, k;
        i = j = k = 0;

        while(i < 3){
            j = 0;
            while(j < 3){
                gridPane.add(buttons[k], j, i, 1, 1);
                k += 1;
                j += 1;
            }
            i += 1;
        }

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
