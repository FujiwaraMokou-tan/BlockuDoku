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
 * This class add all the existent blocks to one list of Block
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/2020
 */
public class IndexOfBlocks
{
    private List<Block> allBlocks;
    Random random; 
    /**
     * Constructor for objects of class IndexOfBlocks, initialize the list of block as an arraylist, an object from the class random and fill the previous list
     */
    public IndexOfBlocks()
    {
        allBlocks = new ArrayList();
        random = new Random(System.nanoTime());
        populate();
    }

    /**
     * Method that add all the existen blocks to the list 
     */
    private void populate(){
        allBlocks.add(new BlockI());
        allBlocks.add(new BlockL());
        allBlocks.add(new BlockT());
        allBlocks.add(new BlockQ());
        allBlocks.add(new BlockS());
        allBlocks.add(new BlockZ());
        allBlocks.add(new BlockJ());
        allBlocks.add(new BlockQExtended());
        allBlocks.add(new BlockTExtended());
        allBlocks.add(new BlockLMax());
        allBlocks.add(new BlockLMin());
        allBlocks.add(new BlockI1());
        allBlocks.add(new BlockI2());
        allBlocks.add(new BlockI3());
    }
    
    //Getter
    public Block getRandomBlock(){ return allBlocks.get(random.nextInt(14)); }
}