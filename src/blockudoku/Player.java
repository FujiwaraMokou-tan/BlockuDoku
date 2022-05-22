/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * This class is dedicated to define the player nickname, score and to set, save
 * and load the board
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 22/05/2020
 */
public class Player {

    private final String username;
    private Board board;
    private int currentScore;
    private ObservableList<Text> obsList;
    private ListView<Text> listView;

    /**
     * Constructor for objects of class Player, set a board, a initial score of
     * 0 and the player username
     *
     * @param username
     */
    public Player(String username) {
        this.obsList = FXCollections.observableArrayList();
        this.listView = new ListView<>();
        this.listView.setPrefSize(250, 920);
        this.listView.setStyle("-fx-control-inner-background: lightgrey;");
        this.listView.setItems(obsList);
        this.username = username;
        board = new Board();
        currentScore = 0;
    }

    //Getters
    public String getUsername() {
        return username;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Method that shows the board game
     *
     * @return
     */
    public VBox showBoard() {
        return board.showBoard();
    }

    /**
     * method to set a new score
     *
     * @param currentScore
     */
    public void loadCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * Method that replace the '.' on the board for '#' and add points to the
     * players current score
     *
     * @param roundBlock
     * @param posY
     * @param posX
     */
    public void placeInBoard(Block roundBlock, int posY, int posX) {
        currentScore += board.placeBlockOnBoard(roundBlock.getBlock(), posX, posY) + roundBlock.getPoints();
    }

    public int load(String save) {
        File file = new File("src/Saves/" + save);
        try {
            Scanner reader = new Scanner(file);
            String lineFromFile = reader.nextLine();
            if (!lineFromFile.equals(username)) {
                return -1;
            }
            int position;
            int counter = 0;
            char[][] loadedBoard = new char[9][9];
            while (reader.hasNextLine() && counter < 9) {
                lineFromFile = reader.nextLine();
                position = 1;
                for (int i = 0; i < 9; i++) {
                    loadedBoard[counter][i] = lineFromFile.charAt(position);
                    position += 3;
                }
                counter++;
            }
            board.load(loadedBoard);
            loadCurrentScore(reader.nextInt());
            return reader.nextInt();
        } catch (IOException e) {
            Alerts.display("" + new BlockuDokuIllegalArgumentException(ErrorCode.THERES_A_PROBLEM_LOADING_FILE).getErrorCode());
            return -1;
        }
    }

    public Round loadBlocks(int gameMode, String save) {
        File file = new File("src/Saves/" + save);
        try {
            Scanner reader = new Scanner(file);
            String lineFromFile = "";
            int position;
            int counter = 0;
            char[][] loadedBoard = new char[9][9];
            while (reader.hasNextLine() && counter < 12) {
                lineFromFile = reader.nextLine();
                counter++;
            }
            int xSize = 0;
            int ySize = 0;
            int points = 0;
            counter = 0;
            int counter2 = -1;
            char[][] transfer = new char[3][3];
            Round round = new Round(gameMode);
            round.fetchRoundOfBlocks();
            while (reader.hasNextLine()) {
                lineFromFile = reader.nextLine();
                if (lineFromFile.charAt(1) == ',') {
                    xSize = Character.getNumericValue(lineFromFile.charAt(0));
                    ySize = Character.getNumericValue(lineFromFile.charAt(2));
                    points = Integer.parseInt(lineFromFile.substring(4, lineFromFile.length()));
                    transfer = new char[xSize][ySize];
                    counter = 0;
                    counter2++;
                } else {
                    for (int z = 0; z < ySize; z++) {
                        transfer[counter][z] = lineFromFile.charAt(z);
                    }
                    counter++;
                }
                round.addBlockFromSave(counter2, transfer, points);
            }

            for (int t = ++counter2; t < 3; t++) {
                round.getRoundBlocks().set(t, null);
            }

            return round;
        } catch (IOException e) {
            Alerts.display("" + new BlockuDokuIllegalArgumentException(ErrorCode.THERES_A_PROBLEM_LOADING_FILE).getErrorCode());
            return null;
        }
    }

    public void save(int gameMode, String save, Round round) {
        try {
            FileWriter writer = new FileWriter("src/Saves/" + save);
            char[][] saveBoard = new char[9][9];
            saveBoard = board.saveBoard();
            String lineToFile = "" + username + "\n";
            for (int x = 0; x < 9; x++) {
                for (int i = 0; i < 9; i++) {
                    lineToFile += "|" + saveBoard[x][i] + "|";
                }
                lineToFile += "\n";
            }
            lineToFile += getCurrentScore();
            lineToFile += "\n";
            lineToFile += gameMode;
            lineToFile += "\n";
            int xSize = 0;
            int ySize = 0;
            for (int i = 0; i < round.getRoundSize(); i++) {
                if (round.getRoundBlocks().get(i) != null) {
                    xSize = round.getRoundBlocks().get(i).getBlock().length;
                    ySize = round.getRoundBlocks().get(i).getBlock()[0].length;
                    lineToFile += xSize + "," + ySize + "-" + round.getRoundBlocks().get(i).getPoints() + "\n";
                    for (int x = 0; x < round.getRoundBlocks().get(i).getBlock().length; x++) {
                        for (int y = 0; y < round.getRoundBlocks().get(i).getBlock()[x].length; y++) {
                            lineToFile += round.getRoundBlocks().get(i).getBlock()[x][y];
                        }
                        lineToFile += "\n";
                    }

                }
            }
            writer.write(lineToFile);
            writer.close();
            Alerts.display("The Game was successfully Saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that reads from a file all the scores from an unique player and
     * display them
     *
     * @return
     */
    public VBox personalScores() {
        VBox vbox = new VBox();
        File file = new File("src/Saves/Profiles.txt");
        try {
            Scanner reader = new Scanner(file);
            String lineFromFile = "";
            String output = "";
            Text text = new Text("");
            int counter = 0;
            while (reader.hasNextLine()) {
                lineFromFile = reader.nextLine();
                if (lineFromFile.equals("Username: " + getUsername().toUpperCase())) {
                    output += "The player <" + getUsername() + "> has the following scores:";
                    while (reader.hasNextLine()) {
                        lineFromFile = reader.nextLine();
                        if (lineFromFile.substring(0, 8).equals("Username")) {
                            break;
                        } else {
                            output += "\n";
                            for (int i = 0; i < lineFromFile.length(); i++) {
                                if (lineFromFile.charAt(i) == 'S' && i > 1) {
                                    output += "" + lineFromFile.substring((i + 7), (i + 17)) + " - " + lineFromFile.substring((i + 18), (i + 26)) + " || " + lineFromFile.substring((i + 36), (i + 46)) + " - " + lineFromFile.substring((i + 47), (i + 55));
                                    break;
                                } else {
                                    output += "" + lineFromFile.charAt(i);
                                }
                            }
                        }
                    }
                }
            }
            text.setText(output);
            text.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 18));
            vbox.getChildren().add(text);
            return vbox;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Method that compares all the players scores and compare them, printing
     * them in order
     *
     * @return
     */
    public VBox ranking() {
        VBox vbox = new VBox();
        this.obsList.clear();
        File file = new File("src/Saves/Profiles.txt");
        try {
            Scanner reader = new Scanner(file);
            String nameFromFile = "";
            String fileIterator = "";
            String extractScore = "";
            int score;
            List<Integer> list = new ArrayList<>();
            Map<String, Integer> bestScores = new HashMap<>();
            if (file.length() == 0) {
                return null;
            }
            nameFromFile = reader.nextLine();
            while (reader.hasNextLine()) {
                fileIterator = reader.nextLine();
                for (int x = 0; x < fileIterator.length(); x++) {
                    if (fileIterator.charAt(x) == '|') {
                        extractScore = extractScore.substring(7, x - 1);
                        score = Integer.parseInt(extractScore);
                        list.add(score);
                        extractScore = "";
                        break;
                    } else if (fileIterator.substring(0, 8).equals("Username")) {
                        Collections.sort(list);
                        Collections.reverse(list);
                        bestScores.put(nameFromFile, list.get(0));
                        list.clear();
                        extractScore = "";
                        nameFromFile = fileIterator;
                        break;
                    } else {
                        extractScore += fileIterator.charAt(x);
                    }
                }
            }
            Collections.sort(list);
            Collections.reverse(list);
            bestScores.put(nameFromFile, list.get(0));
            int counter = bestScores.size();
            Text[] text = new Text[counter];
            for (int x = 1; x <= counter; x++) {
                text[x - 1] = new Text("");
                text[x - 1].setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 22));
                score = Collections.max(bestScores.values());
                for (String player : bestScores.keySet()) {
                    if (bestScores.get(player) == score) {
                        //System.out.println(x + "º " + player + "- Best Score: " + score);
                        text[x - 1].setText(x + "º " + player + "- Best Score: " + score);
                        //obsList.add(x + "º " + player + "- Best Score: " + score);
                        bestScores.remove(player);
                        obsList.add(text[x - 1]);
                        break;
                    }
                }
                if (x == 10) {
                    break;
                }
            }
            vbox.getChildren().add(listView);
            return vbox;
        } catch (IOException e) {
            Alerts.display(e.getMessage());
            return null;
        }
    }

    /**
     * method that saves the username of the player, his score and when he
     * started and ended play
     *
     * @param gameStart
     */
    public void saveScore(LocalDateTime gameStart) {
        try {
            boolean usernameDoesntExist = true;
            File file = new File("src/Saves/Profiles.txt");
            Scanner reader = new Scanner(file);
            String lineToFile = "";
            String allScoresInFile = "";
            String currentLineInFile = "";
            lineToFile += "Score: ";
            lineToFile += getCurrentScore();
            LocalDateTime gameFinal = LocalDateTime.now();
            lineToFile += " |Start: ";
            lineToFile += gameStart;
            lineToFile += "|End: ";
            lineToFile += gameFinal;
            String[] usernames;
            while (reader.hasNextLine()) {
                currentLineInFile = reader.nextLine();
                allScoresInFile += "" + currentLineInFile + "\n";
                usernames = currentLineInFile.split(" ");
                if (usernames[1].equals(getUsername().toUpperCase())) {
                    usernameDoesntExist = false;
                    allScoresInFile += lineToFile + "\n";
                }
            }
            if (usernameDoesntExist) {
                allScoresInFile += "Username: " + getUsername().toUpperCase() + "\n" + lineToFile;
            }
            //System.out.println(allScoresInFile);
            try (FileWriter writer = new FileWriter("src/Saves/Profiles.txt")) {
                writer.write(allScoresInFile);
            }
        } catch (IOException e) {
            Alerts.display(e.getMessage());
        }
    }

    public void clearPersonalScores() {
        File file = new File("src/Saves/Profiles.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            String stringToFile = "";
            printWriter.print(stringToFile);
            printWriter.flush();
            printWriter.close();

        } catch (IOException e) {
        }
    }

}
