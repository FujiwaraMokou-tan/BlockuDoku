/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

import javafx.animation.FadeTransition;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * This class is dedicated with all the functions of the board
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/2020
 */
public class Board {

    private Rectangle[][] board;

    //private Glow glow = new Glow();
    /**
     * Constructor for objects of class Board, creates a bidimensional array and
     * populate him
     */
    public Board() {
        //board = new char[9][9];
        board = new Rectangle[9][9];
        populate();
    }

    /**
     * Method that fills the bidimensional with '.'
     */
    private void populate() {
        //boolean alternate = true;
        for (int x = 0; x < 9; x++) {
            //hbox[x] = new HBox();
            for (int i = 0; i < 9; i++) {
                board[x][i] = new Rectangle();
                board[x][i].setX(40);
                board[x][i].setY(40);
                board[x][i].setWidth(80);
                board[x][i].setHeight(80);
                board[x][i].setArcWidth(10);
                board[x][i].setArcHeight(10);
                /*if(board[x][i].getFill().equals(Color.web("FF0000"))){
                    board[x][i].setFill(Color.web("FF0000"));
                }
                else if (!alternate) {
                    board[x][i].setFill(Color.web("FFFFFF")); //= '.';
                } else {
                    board[x][i].setFill(Color.web("B0F0FF"));
                }
                alternate = !alternate;*/
            }
        }
        recolorBoard();
    }

    private void recolorBoard() {
        boolean alternate = true;
        for (int x = 0; x < 9; x++) {
            for (int i = 0; i < 9; i++) {
                if (board[x][i].getFill().equals(Color.web("FF0000"))) {
                    board[x][i].setFill(Color.web("FF0000"));
                } else if (!alternate) {
                    board[x][i].setFill(Color.web("9BF899")); //= '.';
                } else {
                    board[x][i].setFill(Color.web("B0F0FF"));
                }
                alternate = !alternate;
            }
        }
    }

    /**
     * Method that prints the board
     *
     * @return
     */
    public VBox showBoard() {
        recolorBoard();
        HBox[] hbox = new HBox[9];
        VBox vbox = new VBox();
        Text text = new Text("  |A||B||C||D||E||F||G||H||I|");
        text.setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 40));
        Text[] textNum = new Text[9];
        for (int z = 0; z < 9; z++) {
            textNum[z] = new Text("" + (z + 1));
            textNum[z].setFont(Font.font("Century Schoolbook", FontWeight.BOLD, 44));
        }
        vbox.getChildren().clear();
        for (int x = 0; x < 9; x++) {
            hbox[x] = new HBox();
            hbox[x].getChildren().add(textNum[x]);
            for (int i = 0; i < 9; i++) {
                hbox[x].getChildren().add(board[x][i]);
            }
        }
        vbox.getChildren().add(text);
        vbox.getChildren().add(hbox[0]);
        vbox.getChildren().add(hbox[1]);
        vbox.getChildren().add(hbox[2]);
        vbox.getChildren().add(hbox[3]);
        vbox.getChildren().add(hbox[4]);
        vbox.getChildren().add(hbox[5]);
        vbox.getChildren().add(hbox[6]);
        vbox.getChildren().add(hbox[7]);
        vbox.getChildren().add(hbox[8]);
        return vbox;
    }

    //Setter
    public VBox load(char[][] loadedBoard) {
        for (int x = 0; x < 9; x++) {
            for (int i = 0; i < 9; i++) {
                if (loadedBoard[x][i] == '#') {
                    board[x][i].setFill(Color.web("FF0000"));
                }
            }
        }
        return showBoard();
    }

    //Getter
    public Rectangle[][] getBoard() {
        return board;
    }

    public char[][] saveBoard() {
        char[][] boardSave = new char[9][9];
        for (int x = 0; x < 9; x++) {
            for (int i = 0; i < 9; i++) {
                if (board[x][i].getFill().equals(Color.web("FF0000"))) {
                    boardSave[x][i] = '#';
                } else {
                    boardSave[x][i] = '.';
                }
            }
        }
        return boardSave;
    }

    /**
     * Method that verifys if coordinate of the array have space for '#'
     *
     * @param roundBlock
     * @param posY
     * @param posX
     * @param timesUp
     * @return if the place is empty or not
     */
    private boolean verifyPlacement(char[][] roundBlock, int posY, int posX) {
        int xBlock = 0;
        int yBlock = 0;
        try {
            for (int x = posX; x < board.length; x++) {
                for (int y = posY; y < board[0].length; y++) {
                    if (board[x][y].getFill().equals(Color.web("FF0000")) && roundBlock[xBlock][yBlock] == '#') {
                        return false;
                    }
                    if (yBlock == roundBlock[0].length - 1) {
                        break;
                    }
                    yBlock++;
                }
                if (xBlock == roundBlock.length - 1) {
                    break;
                }
                xBlock++;
                yBlock = 0;
            }
            return true;
        } catch (Exception e) {
            throw new BlockuDokuIllegalArgumentException(ErrorCode.MUST_USE_CORRECT_COORDINATES);
        }
    }

    /**
     * Method that replaces the '.' for '#'
     *
     * @param roundBlock
     * @param posY
     * @param posX
     * @return score
     */
    public int placeBlockOnBoard(char[][] roundBlock, int posY, int posX) {
        int xBlock = 0;
        int yBlock = 0;
        --posX;
        int horizontalLength = posY + roundBlock[0].length - 1;
        int timesUp = 0;
        for (int z = 0; z < roundBlock.length; z++) {
            if (roundBlock[z][0] == ' ') {
                timesUp++;
            } else {
                break;
            }
        }
        int verticalLength = roundBlock.length - timesUp - 1 + posX;
        if (posX < 0 || posX > 8 || posY < 0 || posY > 8 || horizontalLength > 8 || verticalLength > 8) {
            throw new BlockuDokuIllegalArgumentException(ErrorCode.MUST_USE_CORRECT_COORDINATES);
        }
        posX = posX - timesUp;
        if (verifyPlacement(roundBlock, posY, posX)) {
            for (int x = posX; x < board.length; x++) {
                for (int y = posY; y < board[0].length; y++) {
                    if (board[x][y].getFill().equals(Color.web("FF0000")) && roundBlock[xBlock][yBlock] == ' ') {
                        board[x][y].setFill(Color.web("FF0000"));
                    } else if (roundBlock[xBlock][yBlock] == ' ') {
                        board[x][y].setFill(Color.web("9BF899"));
                    } else {
                        board[x][y].setFill(Color.web("FF0000"));
                    }
                    if (yBlock == roundBlock[0].length - 1) {
                        break;
                    }
                    yBlock++;
                }
                if (xBlock == roundBlock.length - 1) {
                    break;
                }
                xBlock++;
                yBlock = 0;
            }
        } else {
            throw new BlockuDokuIllegalArgumentException(ErrorCode.THERES_A_BLOCK_THERE);
        }
        return verifyScore();
    }

    /**
     * Method that see if the lines and collums are full, and if they are add
     * the respective points
     *
     * @return score
     */
    private int verifyScore() {
        int score = 0;
        for (int i = 0; i < 9; i++) {
            if (verifyLine(i)) {
                score += 36;
            }
            if (verifyColumn(i)) {
                score += 36;
            }
        }

        for (int i = 0; i < 9; i += 3) {
            for (int x = 0; x < 9; x += 3) {
                if (verifyBigSquare(i, x)) {
                    score += 46;
                }
            }
        }
        clearBlocks();
        return score;
    }

    /**
     * Methor that verifys if the line is full or not
     *
     * @param line
     * @return true if the line is full, false if dont
     */
    private boolean verifyLine(int line) {
        boolean isLineFull = true;
        //glow.setLevel(0.6);
        for (int i = 0; i < 9; i++) {
            if (board[line][i].getFill().equals(Color.web("9BF899")) || board[line][i].getFill().equals(Color.web("B0F0FF"))) {
                isLineFull = false;
            }
        }

        if (isLineFull) {
            //System.out.println("Success!Row");
            for (int i = 0; i < 9; i++) {
                FadeTransition transicao = new FadeTransition(Duration.millis(2000.0), board[line][i]);
                transicao.setFromValue(0.1);
                transicao.setToValue(1.0);
                transicao.setCycleCount(5);
                transicao.setAutoReverse(true);
                transicao.play();
                board[line][i].setFill(Color.web("000000"));
            }
        }
        return isLineFull;
    }

    /**
     * Methor that verifys if the collum is full or not
     *
     * @param line
     * @return true if the collum is full, false if dont
     */
    private boolean verifyColumn(int line) {
        boolean isColumnFull = true;

        for (int i = 0; i < 9; i++) {
            if (board[i][line].getFill().equals(Color.web("9BF899")) || board[i][line].getFill().equals(Color.web("B0F0FF"))) {
                isColumnFull = false;
            }
        }

        if (isColumnFull) {
            for (int i = 0; i < 9; i++) {
                FadeTransition transicao = new FadeTransition(Duration.millis(2000.0), board[i][line]);
                transicao.setFromValue(0.1);
                transicao.setToValue(1.0);
                transicao.setCycleCount(5);
                transicao.setAutoReverse(true);
                transicao.play();
                board[i][line].setFill(Color.web("000000"));
            }
        }

        return isColumnFull;
    }

    /**
     * Methor that verifys if the big square is full or not
     *
     * @param line
     * @return true if the big square is full, false if dont
     */
    private boolean verifyBigSquare(int line, int column) {
        boolean isSquareFull = true;
        for (int i = 0; i < 3; i++) {
            for (int x = 0; x < 3; x++) {
                if (board[line + i][column + x].getFill().equals(Color.web("9BF899")) || board[line + i][column + x].getFill().equals(Color.web("B0F0FF"))) {
                    isSquareFull = false;
                }
            }
        }

        if (isSquareFull) {
            //System.out.println("Success!Square");
            for (int i = 0; i < 3; i++) {
                for (int x = 0; x < 3; x++) {
                    FadeTransition transicao = new FadeTransition(Duration.millis(2000.0), board[line + i][column + x]);
                    transicao.setFromValue(0.1);
                    transicao.setToValue(1.0);
                    transicao.setCycleCount(5);
                    transicao.setAutoReverse(true);
                    transicao.play();
                    board[line + i][column + x].setFill(Color.web("000000"));
                }
            }
        }
        return isSquareFull;
    }

    /**
     * Method that clean the map replacing '#' for '.'
     */
    private void clearBlocks() {
        for (int x = 0; x < 9; x++) {
            for (int i = 0; i < 9; i++) {
                if (board[x][i].getFill().equals(Color.web("000000"))) {
                    board[x][i].setFill(Color.web("9BF899"));
                }
            }
        }
    }
}