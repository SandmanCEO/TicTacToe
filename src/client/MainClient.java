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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Integer.parseInt;

public class MainClient extends Application {
    Stage window;
    Scene setConnection, gameBoard, waitForPlayer, finish;
    Button[] buttons;
    boolean leadingSymbol;
    GameBoard board;
    Text result;
    ClientTCP connection;

    void setConnectionFunction(){
        Text portText = new Text ("Podaj numer portu serwera");
        TextField inputPort = new TextField();
        inputPort.setText("4943");
        Text ipText = new Text ("Podaj IP");
        TextField inputIP = new TextField();
        inputIP.setText("192.168.1.112");
        Button exit = new Button ("Wyjście");
        Button connectButton = new Button ("Połącz");
        exit.setOnAction( e -> {
            window.close();
        });

        connectButton.setOnAction( e -> {
            window.setScene(waitForPlayer);
            connection = new ClientTCP(inputIP.getText(), Integer.parseInt(inputPort.getText()));
            leadingSymbol = connection.getLeadingSymbol();
            System.out.println(leadingSymbol);
            if(leadingSymbol == false) {
                WaitForPlayer task = new WaitForPlayer(connection);

                task.setOnSucceeded(successEvent -> {
                    if (task.getValue() == true) {
                        window.setScene(gameBoard);
                        waitForInstruction();
                    }
                });

                ExecutorService executorService = Executors.newFixedThreadPool(1);
                executorService.execute(task);
                executorService.shutdown();
                setDisableButtons();
            } else {
                setDisableButtons();
                window.setScene(gameBoard);

                waitForInstruction();
            }
        });

        VBox setPortLayout = new VBox(10);
        setPortLayout.setPadding(new Insets(20,20,20,20));
        setPortLayout.getChildren().addAll(portText,inputPort, ipText, inputIP, connectButton, exit);

        setConnection = new Scene (setPortLayout, 600, 400);
    }

    void setGameBoard() {
        buttons = new Button[9];

        int index = 0;
        while(index < 9){
            buttons[index] = new Button("  ");
            index += 1;
        }

        buttons[0].setOnAction( e -> {
            setDisableButtons();
            connection.send("0");
            connection.send("0");
            if(leadingSymbol == true)
                buttons[0].setText("X");
            else
                buttons[0].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
        });

        buttons[1].setOnAction( e -> {
            setDisableButtons();
            connection.send("0");
            connection.send("1");
            if(leadingSymbol == true)
                buttons[1].setText("X");
            else
                buttons[1].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
        });

        buttons[2].setOnAction( e -> {
            setDisableButtons();
            connection.send("0");
            connection.send("2");
            if(leadingSymbol == true)
                buttons[2].setText("X");
            else
                buttons[2].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
        });

        buttons[3].setOnAction( e -> {
            setDisableButtons();
            connection.send("1");
            connection.send("0");
            if(leadingSymbol == true)
                buttons[3].setText("X");
            else
                buttons[3].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
        });

        buttons[4].setOnAction( e -> {
            setDisableButtons();
            connection.send("1");
            connection.send("1");
            if(leadingSymbol == true)
                buttons[4].setText("X");
            else
                buttons[4].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
        });

        buttons[5].setOnAction( e -> {
            setDisableButtons();
            connection.send("1");
            connection.send("2");
            if(leadingSymbol == true)
                buttons[5].setText("X");
            else
                buttons[5].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
        });

        buttons[6].setOnAction( e -> {
            setDisableButtons();
            connection.send("2");
            connection.send("0");
            if(leadingSymbol == true)
                buttons[6].setText("X");
            else
                buttons[6].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();

        });

        buttons[7].setOnAction( e -> {
            setDisableButtons();
            connection.send("2");
            connection.send("1");
            if(leadingSymbol == true)
                buttons[7].setText("X");
            else
                buttons[7].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
        });

        buttons[8].setOnAction( e -> {
            setDisableButtons();
            connection.send("2");
            connection.send("2");
            if(leadingSymbol == true)
                buttons[8].setText("X");
            else
                buttons[8].setText("O");
            window.setScene(gameBoard);
            waitForInstruction();
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


    void setWaitForPlayer(){
        Text waitText = new Text("Czekam na drugiego gracza");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(waitText);
        waitForPlayer = new Scene(layout, 600, 400);
    }

    void setFinish(){
        result = new Text("");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(result);
        finish = new Scene(layout, 600, 400);
    }

    void setDisableButtons(){
        for(Button button : buttons)
            button.setDisable(true);
    }

    void setEnableButtons(){
        for(Button button : buttons)
            button.setDisable(false);
    }

    void setNewMove(int m, int n){
        if(leadingSymbol == true)
            buttons[m * 3 + n].setText("O");
        else
            buttons[m * 3 + n].setText("X");
    }

    void waitForPlayerMove(){
        GetLastMove task = new GetLastMove(connection);

        task.setOnSucceeded(successEvent -> {
            int[] temp = task.getValue();
            setNewMove(temp[0], temp[1]);
            window.setScene(gameBoard);
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();

        waitForInstruction();
    }

    void waitForInstruction(){
        GetInstruction task = new GetInstruction(connection);
        task.setOnSucceeded(successEvent -> {
            String instruction = task.getValue();
            if(instruction.equals("draw")){
                result.setText("Remis!");
                window.setScene(finish);
            } else if(instruction.equals("youWon")){
                result.setText("Wygrałeś!");
                window.setScene(finish);
            }else if(instruction.equals("youLose")){
                result.setText("Przegrałeś!");
                window.setScene(finish);
            }else if(instruction.equals("nextMove")){
                setEnableButtons();
            }else if(instruction.equals("enemyMove")){
                waitForPlayerMove();
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("TicTacToe");

        setConnectionFunction();
        setGameBoard();
        setWaitForPlayer();
        setFinish();

        window.setScene(setConnection);
        window.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
