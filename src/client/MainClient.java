package client;

import client.task.GetInstruction;
import client.task.GetLastMove;
import client.task.WaitForPlayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClient extends Application {
    private Stage window;
    private Scene setConnection, gameBoard, waitForPlayer, finish;
    private Button[] buttons;
    private boolean leadingSymbol;
    private boolean[] usedButtons;
    private Text result;
    private ClientTCP connection;
    private Image blankImg, circleImg, crossImg;

    private void setConnectionFunction(){
        Text welcomeText = new Text("Witaj w grze kółko i krzyżyk!");
        welcomeText.setStyle("-fx-font-size: 16pt;");
        Text portText = new Text ("Podaj numer portu serwera");
        TextField inputPort = new TextField();
        inputPort.setText("4943");
        inputPort.setMaxWidth(120);
        Text ipText = new Text ("Podaj IP");
        TextField inputIP = new TextField();
        inputIP.setText("192.168.1.112");
        inputIP.setMaxWidth(120);
        Button exit = new Button ("Wyjście");
        Button connectButton = new Button ("Połącz");
        exit.setOnAction( e -> window.close());

        connectButton.setOnAction( e -> {
            window.setScene(waitForPlayer);
            connection = new ClientTCP(inputIP.getText(), Integer.parseInt(inputPort.getText()));
            leadingSymbol = connection.getLeadingSymbol();
            System.out.println(leadingSymbol);
            if(!leadingSymbol) {
                WaitForPlayer task = new WaitForPlayer(connection);

                task.setOnSucceeded(successEvent -> {
                    if (task.getValue()) {
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

        BorderPane border = new BorderPane();
        GridPane setPortLayout = new GridPane();
        HBox top = new HBox();
        border.setTop(top);
        border.setCenter(setPortLayout);

        top.getChildren().add(welcomeText);
        top.setPadding(new Insets(50, 100, 20, 150));

        setPortLayout.setVgap(10);
        setPortLayout.setHgap(30);
        setPortLayout.setPadding(new Insets(40, 50, 200, 150));

        setPortLayout.add(ipText, 0, 0, 1, 1);
        setPortLayout.add(inputIP, 0, 1, 1, 1);
        setPortLayout.add(portText, 1, 0, 1, 1);
        setPortLayout.add(inputPort, 1, 1, 1, 1);
        setPortLayout.add(connectButton, 0, 2, 1, 1);
        setPortLayout.add(exit, 1, 2, 1, 1);

        setConnection = new Scene (border, 600, 400);
        setConnection.getStylesheets().addAll("stylesheets/setConnection.css");
    }

    private void setGameBoard() {
        buttons = new Button[9];

        int index = 0;
        while(index < 9){
            buttons[index] = new Button("", new ImageView(blankImg));
            index += 1;
        }

        buttons[0].setOnAction( e -> buttonHandler(0));

        buttons[1].setOnAction( e -> buttonHandler(1));

        buttons[2].setOnAction( e -> buttonHandler(2));

        buttons[3].setOnAction( e -> buttonHandler(3));

        buttons[4].setOnAction( e -> buttonHandler(4));

        buttons[5].setOnAction( e -> buttonHandler(5));

        buttons[6].setOnAction( e -> buttonHandler(6));

        buttons[7].setOnAction( e -> buttonHandler(7));

        buttons[8].setOnAction( e -> buttonHandler(8));

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 50, 50, 175));

        int i, j, k;
        i = k = 0;

        while(i < 3){
            j = 0;
            while(j < 3){
                gridPane.add(buttons[k], j, i, 1, 1);
                k += 1;
                j += 1;
            }
            i += 1;
        }

        gameBoard = new Scene(gridPane, 600,400);
        gameBoard.getStylesheets().addAll("stylesheets/gameBoard.css");
    }

    private void buttonHandler(int no){
        setDisableButtons();
        connection.send("move");
        connection.send(String.valueOf(Math.floorDiv(no, 3)));
        connection.send(String.valueOf(no % 3));
        if(leadingSymbol)
            buttons[no].setGraphic(new ImageView(crossImg));
        else
            buttons[no].setGraphic(new ImageView(circleImg));
        usedButtons[no] = true;
        window.setScene(gameBoard);
        waitForInstruction();
    }

    private void setWaitForPlayer(){
        Text waitText = new Text("Czekam na drugiego gracza");
        waitText.setStyle("-fx-font-size: 16pt;");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(50, 100, 0, 150));
        layout.getChildren().addAll(waitText);
        waitForPlayer = new Scene(layout, 600, 400);
        waitForPlayer.getStylesheets().addAll("stylesheets/waitForPlayer.css");
    }

    private void setFinish(){
        result = new Text("");
        Button exit = new Button("Zakończ");
        exit.setOnAction( e -> window.close());

        VBox layout = new VBox(40);
        layout.setPadding(new Insets(50, 100, 100, 200));
        layout.getChildren().addAll(result, exit);
        finish = new Scene(layout, 600, 400);
        finish.getStylesheets().addAll("stylesheets/finish.css");
    }

    private void setDisableButtons(){
        for(Button button : buttons)
            button.setDisable(true);
    }

    private void setEnableButtons(){
        int k = 0;

        while(k < 9){
            if(!usedButtons[k])
                buttons[k].setDisable(false);
            k += 1;
        }
    }

    private void setNewMove(int m, int n){
        if(leadingSymbol)
            buttons[m * 3 + n].setGraphic(new ImageView(circleImg));
        else
            buttons[m * 3 + n].setGraphic(new ImageView(crossImg));
        usedButtons[m * 3 + n] = true;
    }

    private void waitForPlayerMove(){
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

    private void waitForInstruction(){
        GetInstruction task = new GetInstruction(connection);
        task.setOnSucceeded(successEvent -> {
            String instruction = task.getValue();

            switch (instruction){
                case "draw":
                    result.setText("Remis!");
                    window.setScene(finish);
                    break;
                case "youWon":
                    result.setText("Wygrałeś!");
                    result.setFill(Color.GREEN);
                    window.setScene(finish);
                    break;
                case "youLose":
                    result.setText("Przegrałeś!");
                    result.setFill(Color.RED);
                    window.setScene(finish);
                    break;
                case "nextMove":
                    setEnableButtons();
                    break;
                case "enemyMove":
                    waitForPlayerMove();
                    break;
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        window.setTitle("TicTacToe");

        blankImg = new Image("pics/blank.jpg");
        circleImg = new Image("pics/circle.jpg");
        crossImg = new Image("pics/cross.jpg");

        usedButtons = new boolean[9];

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
