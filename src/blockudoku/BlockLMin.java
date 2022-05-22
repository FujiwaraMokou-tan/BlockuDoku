/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

/**
 * This class works as a way to create block named "BlocoLMin"
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/20
 */
public class BlockLMin extends BlockL
{
    /**
     * Constructor for objects of class BlockLMin, sends all the required information to Superclass
     */
    public BlockLMin()
    {
        super(2,1,1,3,3,6);
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
        block[1][1] = '#';
        block[1][2] = '#';
    }
}
