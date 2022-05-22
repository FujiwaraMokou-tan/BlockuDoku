/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

/**
 * This class works as a way to create block named "BlocoS"
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/20
 */
public class BlockS extends Block
{
    /**
     * Constructor for objects of class BlockS, sends all the required information to Superclass
     */
    public BlockS()
    {
        super(1, 1, 1, 3, 3, 4);
        create();
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
        block[0][1] = '#';
        block[0][2] = '#';
        block[1][1] = '#';
        block[1][0] = '#';
    }
}
