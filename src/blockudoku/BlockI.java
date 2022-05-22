/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;


/**
 * This class works as a way to create block named "BlocoI"
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/20
 */
public class BlockI extends Block
{
    /**
     * Constructor for objects of class BlockI, sends all the required information to Superclass
     */
    public BlockI()
    {
        super(1, 2, 2, 5, 5, 4);
        create();
    }

    /**
     * Constructor for objects of class BlockI1 / BlockI2 or BlockI3, it receives the data from its subclass and sends it to the top of the hierarchy
     * @param gameMode - indicates what type of game mode this block belongs
     * @param pivotX - indicates the X cordinate where the pivot of this block is located (it's needed to perform rotation)
     * @param pivotY - indicates the Y cordinate where the pivot of this block is located (it's needed to perform rotation)
     * @param xLength - indicates the lenght of the rows
     * @param yLength - indicates the lenght of the columns
     */
    public BlockI(int gameMode,int pivotX, int pivotY, int xLength, int yLength, int points)
    {
        super(gameMode, pivotX, pivotY, xLength, yLength, points);
    }
    
    /**
     * Creates the specific Block
     */
    @Override
    public void create(){
        for(int x = 0; x < block.length; x++){
            for(int y = 0; y < block[x].length; y++){
                block[x][y] = ' ';
            }
        }
        block[0][2] = '#';
        block[1][2] = '#';
        block[2][2] = '#';
        block[3][2] = '#';
    }
}
