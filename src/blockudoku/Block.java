/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;


/**
 * Abstract class Blocks is dedicated to show the block associated to it, set the game mode for each block and "create" them
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/20
 */
public abstract class Block
{
    private final int gameMode;
    private final int pivotX;
    private final int pivotY;
    public  char [][]  block;
    public int points;
    
    /**
     * @param gameMode
     * @param pivotX
     * @param pivotY
     * @param xLength
     * @param yLength
     * @param points 
     */
    public Block(int gameMode, int pivotX, int pivotY, int xLength, int yLength, int points)
    {
        this.gameMode = gameMode;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        block = new char[xLength][yLength];
        this.points = points;
    }

    /**
     * Method that creates blocks
     */
     abstract void create();

    //Getters
    public int getPivotX(){ return pivotX; }
    public int getPivotY(){ return pivotY; }
    public int getGameMode(){ return gameMode; }
    public char[][] getBlock(){ return block; }
    public int getPoints(){ return points; }

    /**
     * Method that prints the block
     */
    public void show(){
        for(int x = 0; x < block.length; x++){
            for(int y = 0; y < block[x].length; y++){
                System.out.print(block[x][y]);
            }
            System.out.println();
        }
    }

    /**
     * Methor that replaces a block
     * @param newBlock - the block that will replace the old one
     */
    public void replaceBlock(char [][]  newBlock){
        block = newBlock;
    }
    
    public void setPoints(int p){
           points = p;
    }
    
}