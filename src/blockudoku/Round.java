/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
/**
 * This class is dedicated into getting a proper round of 3 blocks to send to the
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/20
 */
public class Round
{
    Rotation rotation; //Enum of the class rotation
    Random random;     //Random so we can randomly choose the nº of rotations and the block that will be used
    private int gameMode;   //gameMode so the round knows which blocks to use
    private List<Block> roundBlocks; //Blocks that will be used in player to insert in board
    
    /**
     * Constructor for objects of class Round, creates a round for the game to send to the player
     * @param mode, mode that the game is using
     */
    public Round(int mode)
    {
        random = new Random(System.nanoTime());
        gameMode = mode;
        roundBlocks = new ArrayList();
        rotation = Rotation.ZERO;
    }

    /**
     * Method that returns the number of rotations a block will get
     * @return , int value that contains the number of rotations
     */
    private int getRotationNumber(){
        rotation = rotation.values()[random.nextInt(Rotation.values().length)];
        return rotation.getNumberOfRotations();
    }

    /**
     * Method that returns one block from all possible
     * @return , Block value that returns one of the blocks from all possible ones
     */

    public Block getRandomBlock(){
        IndexOfBlocks list = new IndexOfBlocks();
        return list.getRandomBlock();
    }
    
    /**
     * Method that grabs three blocks for the next Round and "prepares" them
     */
    public void fetchRoundOfBlocks(){
        int counter = 0;
        int rotations = 0;
        Block addBlock;
        do{
            addBlock = getRandomBlock();
            if(addBlock.getGameMode() == gameMode || gameMode == 2){
                rotations = getRotationNumber();
                roundBlocks.add(addBlock);
                for(int x = 0; x < rotations; x++){
                    rotate(counter, addBlock.getPivotX(), addBlock.getPivotY());
                }
                int xlength = roundBlocks.get(counter).getBlock().length;
                int xloop = xlength-1;
                int ylength = roundBlocks.get(counter).getBlock()[0].length;
                int yloop = ylength-1;
                for(int x = 0; x < roundBlocks.get(counter).getBlock().length; x++){
                    cleanBlock(counter);
                }

                for(int x = 0; x <= xloop; x++){
                    xlength--;
                    removeRows(counter, xlength, ylength);
                }
                xlength = roundBlocks.get(counter).getBlock().length;
                for(int x = 0; x < yloop; x++){
                    ylength--;
                    removeColumns(counter, xlength, ylength);
                }
                counter++;
            }
        }while(counter < 3);
    }

    /**
     * Method that rotates the block
     * @param position, gives the position on the list of three blocks for this round
     * @param pivotX, gets the pivot for X coordinate for this block
     * @param pivotY, gets the pivot for Y coordinate for this block
     */
    private void rotate(int position, int pivotX, int pivotY){
        char [][]  tempBlock = new char [roundBlocks.get(position).getBlock().length][roundBlocks.get(position).getBlock().length];
        for(int x = 0; x < tempBlock.length; x++){
            for(int y = 0; y < tempBlock[x].length; y++){
                tempBlock[x][y] = ' ';
            }
        }
        int tempX;
        int tempY;
        int coversionX;
        int coversionY;
        for(int x = 0; x < roundBlocks.get(position).getBlock().length; x++){
            for(int y = 0; y < roundBlocks.get(position).getBlock().length; y++){
                if(x == pivotX && pivotY == y && roundBlocks.get(position).getBlock()[x][y] == '#'){
                    tempBlock[x][y] = '#';
                    y++;
                }else if(x == pivotX && pivotY == y && roundBlocks.get(position).getBlock()[x][y] == ' '){
                    tempBlock[x][y] = ' ';
                    y++;
                }
                if(roundBlocks.get(position).getBlock()[x][y] == '#'){
                    tempX = x - pivotX;
                    tempY = y - pivotY;
                    coversionX = -1 * tempY;
                    coversionY = 1 * tempX;
                    coversionX = pivotX + coversionX;
                    coversionY = pivotY + coversionY;
                    tempBlock[coversionX][coversionY] = '#';
                } 
            }
        }
        roundBlocks.get(position).replaceBlock(tempBlock);
    }

    /**
     * Method that "Cleans the block", after rotation it moves the block to the upmost left side of the bi-dimentional array
     * @param position, gives the position on the list of three blocks for this round
     */
    private void cleanBlock(int position){
        boolean needsClean = true;
        for(int x = 0; x < roundBlocks.get(position).getBlock().length; x++){
            if(roundBlocks.get(position).getBlock()[x][0] == '#')
                needsClean = false;
        }
        if(needsClean)
            moveLeft(position);
        needsClean = true;
        for(int y = 0; y < roundBlocks.get(position).getBlock()[0].length; y++){
            if(roundBlocks.get(position).getBlock()[0][y] == '#')
                needsClean = false;
        }
        if(needsClean)
            moveUp(position);
    }

    /**
     * Method that moves the block to the left side of the bi-dimentional array
     * @param position, gives the position on the list of three blocks for this round
     */
    private void moveLeft(int position){
        for(int x = 0; x < roundBlocks.get(position).getBlock().length; x++){
            for(int y = 1; y < roundBlocks.get(position).getBlock()[x].length; y++){
                if(roundBlocks.get(position).getBlock()[x][y] == '#'){
                    roundBlocks.get(position).getBlock()[x][y-1] = '#';
                    roundBlocks.get(position).getBlock()[x][y] = ' ';
                }
            }
        }
    }

    /**
     * Method that moves the block up on the bi-dimentional array
     * @param position, gives the position on the list of three blocks for this round
     */
    private void moveUp(int position){
        for(int x = 1; x < roundBlocks.get(position).getBlock().length; x++){
            for(int y = 0; y < roundBlocks.get(position).getBlock()[x].length; y++){
                if(roundBlocks.get(position).getBlock()[x][y] == '#'){
                    roundBlocks.get(position).getBlock()[x-1][y] = '#';
                    roundBlocks.get(position).getBlock()[x][y] = ' ';
                }
            }
        }
    }

    /**
     * Method that removes all the fully empty rows from a block
     * @param position, gives the position on the list of three blocks for this round
     * @param xlength, gives the current number of rows of the block
     * @param ylength, gives the current number of columns of the block
     */
    private void removeRows(int position, int xlength, int ylength){
        boolean needsRemoval = true;
        for(int y = 0; y < roundBlocks.get(position).getBlock()[0].length; y++){
            if(roundBlocks.get(position).getBlock()[xlength][y] == '#')
                needsRemoval = false;
        }
        if(needsRemoval){
            char[][] tempBlock = new char[xlength][ylength];
            for(int x = 0; x < tempBlock.length; x++){
                for(int y = 0; y < tempBlock[0].length; y++){
                    tempBlock[x][y] = ' ';
                }
            }

            for(int x = 0; x < tempBlock.length; x++){
                for(int y = 0; y < tempBlock[0].length; y++){
                    tempBlock[x][y] = roundBlocks.get(position).getBlock()[x][y];
                }
            }
            roundBlocks.get(position).replaceBlock(tempBlock);
        }
    }

    /**
     * Method that removes all the fully empty columns from a block
     * @param position, gives the position on the list of three blocks for this round
     * @param xlength, gives the current number of rows of the block
     * @param ylength, gives the current number of columns of the block
     */
    private void removeColumns(int position, int xlength, int ylength){
        boolean needsRemoval = true;
        for(int x = 0; x < roundBlocks.get(position).getBlock().length; x++){
            if(roundBlocks.get(position).getBlock()[x][roundBlocks.get(position).getBlock()[x].length-1] == '#'){
                needsRemoval = false;
            }
        }
        if(needsRemoval){
            char[][] tempBlock = new char[xlength][ylength];
            for(int x = 0; x < tempBlock.length; x++){
                for(int y = 0; y < tempBlock[0].length; y++){
                    tempBlock[x][y] = ' ';
                }
            }

            for(int x = 0; x < tempBlock.length; x++){
                for(int y = 0; y < tempBlock[0].length; y++){
                    tempBlock[x][y] = roundBlocks.get(position).getBlock()[x][y];
                }
            }
            roundBlocks.get(position).replaceBlock(tempBlock);
        }
    }

    /**
     * Method that removes all the fully empty columns from a block
     * @return int, returns the size of the list of blocks of this round
     */
    public int getRoundSize(){
        return roundBlocks.size();
    }

    /**
     * Method that removes all the fully empty columns from a block
     * @return List<char[][]>, returns the the list of blocks of this round
     */
    public List<Block> getRoundBlocks(){
        return roundBlocks;
    }

    public int getUsableBlocks(){
        int result = 0;
        for(int x =0; x < roundBlocks.size(); x++){
            if(roundBlocks.get(x) != null)
                result++;
        }
        return result;
    }
    
    public void addBlockFromSave(int index, char[][] block, int points){
        roundBlocks.get(index).replaceBlock(block);
        roundBlocks.get(index).setPoints(points);
    }
    
    
}