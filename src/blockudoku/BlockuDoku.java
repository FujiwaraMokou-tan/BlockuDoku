/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.application.Application;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is dedicated to have all the methos that allow the game run
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 22/05/2020
 */
public class BlockuDoku extends Application /*implements EventHandler<ActionEvent> */ {
    
    static int gameMode = -1;
    static Player player;
    public Round round;
    static LocalDateTime gameStart;
    private TextField tf;
    boolean gameIsRunning = false;
    boolean nameChosen = false;
    private BorderPane root;
    private ObservableList<Text> obsList;
    private ListView<Text> listView;
    
    @Override
    public void start(Stage primaryStage) {
        this.obsList = FXCollections.observableArrayList();
        this.listView = new ListView<>();
        this.listView.setPrefSize(250, 920);
        this.listView.setItems(obsList);
        this.root = new BorderPane();
        addTopContents("Register your name:");
        addBottomContents();
        Scene scene = new Scene(root, 1280, 1024);
        primaryStage.setTitle("Blockudoku");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void addTopContents(String content) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #00B7E1;");
        Text text = new Text(content);
        text.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 20));
        hbox.getChildren().add(text);
        hbox.setAlignment(Pos.CENTER);
        root.setTop(hbox);
    }
    
    public void addBottomContents() {
        VBox vbox = new VBox();
        Button btn = new Button("Enter");
        this.tf = new TextField();
        tf.setMinSize(12, 10);
        btn.setPrefWidth(1280);
        btn.setPrefHeight(65);
        btn.setStyle("-fx-font-size: 2em; ");
        btn.setOnAction(e -> readInput());
        vbox.getChildren().add(tf);
        vbox.getChildren().add(btn);
        root.setBottom(vbox);
    }
    
    public void addLeftContents() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #CAF2FA;");
        Text textLeft1 = new Text("1 – New Game");
        textLeft1.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        Text textLeft2 = new Text("2 – Open Game");
        textLeft2.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        Text textLeft3 = new Text("3 – Personal Score");
        textLeft3.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        Text textLeft4 = new Text("4 – Ranking (TOP 10)");
        textLeft4.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        Text textLeft5 = new Text("0 – Exit");
        textLeft5.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        vbox.getChildren().add(textLeft1);
        vbox.getChildren().add(textLeft2);
        vbox.getChildren().add(textLeft3);
        vbox.getChildren().add(textLeft4);
        vbox.getChildren().add(textLeft5);
        textLeft1.setTranslateY(50);
        textLeft2.setTranslateY(100);
        textLeft3.setTranslateY(150);
        textLeft4.setTranslateY(200);
        textLeft5.setTranslateY(250);
        root.setLeft(vbox);
    }
    
    public void readInput() {
        VBox vbox = new VBox();
        String input = this.tf.getText();
        if (!input.isEmpty()) {
            if (!nameChosen) {
                player = new Player(input);
                addTopContents("Hello <" + player.getUsername() + ">");
                nameChosen = true;
                addLeftContents();
            } else {
                input = input.toUpperCase();
                if (!gameIsRunning) {
                    if (input.equals("1")) {
                        chooseMode();
                    } else if (input.equals("2")) {
                        loadButtons();
                    } else if (input.equals("3")) {
                        vbox = player.personalScores();
                        root.setCenter(vbox);
                    } else if (input.equals("4")) {
                        vbox = player.ranking();
                        root.setCenter(vbox);
                    } else if (input.equals("0")) {
                        System.exit(0);
                    } else if (input.substring(0, 4).equals("LOAD")) {
                        load(input.substring(5, input.length()) + ".txt");
                    } else if (input.equals("RESET RANKING")){
                        player.clearPersonalScores();
                    }
                } else {
                    int choice;
                    int posX;
                    int posY;
                    if (input.equals("SAVE")) {
                        saveButtons();
                        player.saveScore(gameStart);
                    } else if (input.substring(0, 4).equals("SAVE")) {
                        save(input.substring(5, input.length()) + ".txt");
                        player.saveScore(gameStart);
                    } else if (input.equals("CANCEL")) {
                        System.exit(0);
                    } else if (input.length() != 4) {
                        Alerts.display("Please insert only 4 characters when trying to place a block 'Z-YX' following this format (Z = Block chosen/ - = seperator between Block and coordinates / Y = Y position in the board / X = X position in the board ) ");
                    } else {
                        choice = input.charAt(0) - 65;
                        posX = input.charAt(2) - 65;
                        posY = input.charAt(3) - '0';
                        if (input.charAt(1) == '-') {
                            try {
                                if (choice >= 0 && choice < 3) {
                                    player.placeInBoard(round.getRoundBlocks().get(choice), posY, posX);
                                    round.getRoundBlocks().set(choice, null);
                                } else {
                                    Alerts.display("Pick a Block between A and C!");
                                }
                            } catch (BlockuDokuIllegalArgumentException e) {
                                Alerts.display("" + e.getErrorCode());
                            }
                        } else {
                            Alerts.display("Please use the '-' letter to seperate the chosen block from the coordinates.");
                        }
                        VBox boardPlay = player.showBoard();
                        root.setCenter(boardPlay);
                        if (round.getUsableBlocks() == 0) {
                            round.getRoundBlocks().clear();
                            round.fetchRoundOfBlocks();
                            showBlocks();
                        } else {
                            showBlocks();
                        }
                    }
                }
            }
            this.tf.setText("");
        }
    }
    
    private void loadButtons() {
        VBox vbox = new VBox();
        Button btn1 = new Button("Load1");
        Button btn2 = new Button("Load2");
        Button btn3 = new Button("Load3");
        btn1.setPrefWidth(200);
        btn1.setPrefHeight(200);
        btn2.setPrefWidth(200);
        btn2.setPrefHeight(200);
        btn3.setPrefWidth(200);
        btn3.setPrefHeight(200);
        btn1.setStyle("-fx-background-color: #9CFBE7");
        btn2.setStyle("-fx-background-color: #9CFBE7");
        btn3.setStyle("-fx-background-color: #9CFBE7");
        btn1.setTranslateY(45);
        btn2.setTranslateY(160);
        btn3.setTranslateY(265);
        btn1.setOnAction(e -> load("Savefile1.txt"));
        btn2.setOnAction(e -> load("Savefile2.txt"));
        btn3.setOnAction(e -> load("Savefile3.txt"));
        vbox.getChildren().addAll(btn1, btn2, btn3);
        root.setRight(vbox);
    }
    
    private void load(String save) {
        gameMode = player.load(save);
        if (gameMode != -1) {
            gameIsRunning = true;
            gameStart = LocalDateTime.now();
            round = new Round(gameMode);
            round.fetchRoundOfBlocks();
            round = player.loadBlocks(gameMode, save);
            showBlocks();
            VBox boardPlay = player.showBoard();
            root.setCenter(boardPlay);
            addBottomContents();
            root.setRight(null);
        } else {
            Alerts.display("This player cannot load this save");
        }
    }
    
    public void saveButtons() {
        VBox vbox = new VBox();
        Button btn1 = new Button("Save1");
        Button btn2 = new Button("Save2");
        Button btn3 = new Button("Save3");
        btn1.setPrefWidth(200);
        btn1.setPrefHeight(200);
        btn2.setPrefWidth(200);
        btn2.setPrefHeight(200);
        btn3.setPrefWidth(200);
        btn3.setPrefHeight(200);
        btn1.setTranslateY(45);
        btn2.setTranslateY(160);
        btn3.setTranslateY(265);
        btn1.setStyle("-fx-background-color: #9CFBE7");
        btn2.setStyle("-fx-background-color: #9CFBE7");
        btn3.setStyle("-fx-background-color: #9CFBE7");
        btn1.setOnAction(e -> save("Savefile1.txt"));
        btn2.setOnAction(e -> save("Savefile2.txt"));
        btn3.setOnAction(e -> save("Savefile3.txt"));
        vbox.getChildren().addAll(btn1, btn2, btn3);
        root.setRight(vbox);
    }
    
    private void save(String save) {
        player.save(gameMode, save, round);
        root.setRight(null);
    }
    
    public void showBlocks() {
        obsList.clear();
        Text[] text = new Text[round.getRoundSize()];
        VBox painelList = new VBox();
        int letter = 65;
        Text textLeft1 = new Text("Blocks left to play:");
        textLeft1.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 24));
        textLeft1.setFill(Color.web("23D71E"));
        obsList.add(textLeft1);
        String append = "";
        for (int i = 0; i < round.getRoundSize(); i++) {
            if (round.getRoundBlocks().get(i) != null) {
                append += "Block " + String.valueOf((char) (i + letter)) + "\n";
                for (int x = 0; x < round.getRoundBlocks().get(i).getBlock().length; x++) {
                    for (int y = 0; y < round.getRoundBlocks().get(i).getBlock()[x].length; y++) {
                        if (round.getRoundBlocks().get(i).getBlock()[x][y] == ' ') {
                            append += "_";
                        } else {
                            append += round.getRoundBlocks().get(i).getBlock()[x][y];
                        }
                    }
                    append += "\n";
                }
                text[i] = new Text();
                text[i].setText(append);
                text[i].setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 30));
                obsList.add(text[i]);
                append = "";
            }
        }
        painelList.getChildren().add(listView);
        root.setLeft(painelList);
    }
    
    public void chooseMode() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #CAF2FA;");
        Text textLeft1 = new Text("1 – New Game (Basic Mode)");
        textLeft1.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        Text textLeft2 = new Text("2 – New Game (Advanced Mode)");
        textLeft2.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        Text textLeft3 = new Text("0 – Go Back");
        textLeft3.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 16));
        vbox.getChildren().add(textLeft1);
        vbox.getChildren().add(textLeft2);
        vbox.getChildren().add(textLeft3);
        textLeft1.setTranslateY(50);
        textLeft2.setTranslateY(100);
        textLeft3.setTranslateY(150);
        root.setLeft(vbox);
        VBox vbox2 = new VBox();
        Button btn = new Button("Enter");
        btn.setPrefWidth(1280);
        btn.setPrefHeight(65);
        btn.setStyle("-fx-font-size: 2em; ");
        this.tf = new TextField();
        tf.setMinSize(120, 10);
        btn.setOnAction(e -> modeButton());
        vbox2.getChildren().add(tf);
        vbox2.getChildren().add(btn);
        root.setBottom(vbox2);
    }
    
    public void modeButton() {
        String input = this.tf.getText();
        if (!input.isEmpty()) {
            if (input.equals("1") || input.equals("2")) {
                gameMode = Integer.parseInt(input);
                gameIsRunning = true;
                gameStart = LocalDateTime.now();
                addBottomContents();
                addTopContents("<" + player.getUsername() + "> Please choose your next play.");
                round = new Round(gameMode);
                round.fetchRoundOfBlocks();
                showBlocks();
                VBox boardPlay = player.showBoard();
                root.setCenter(boardPlay);
                root.setRight(null);
            } else if (input.equals("0")) {
                addLeftContents();
                addBottomContents();
            }
        }        
    }

    /**
     * Main method that have basically all the commands that allow the game run
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
