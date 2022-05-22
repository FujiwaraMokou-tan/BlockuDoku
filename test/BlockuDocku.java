/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import blockudoku.Board;
import blockudoku.ErrorCode;
import blockudoku.Player;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class is dedicated to test some of the classes from the project
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 27/05/2020
 */
public class BlockuDocku {
    
    private Player p1;
    private Player p2;
    private ErrorCode ec;
    private Board b;
    private char[][] a;
    
    
    public BlockuDocku() {
        a = new char[2][2];
        a[0][0] = '#';
        a[0][1] = '#';
        a[1][0] = '#';
        a[1][1] = '#';
      
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
       p1 = new Player("Duarte");
       p2 = new Player("Eduardo");
       p2.loadCurrentScore(1000);
       b = new Board();
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Method that test the class Player 
     */
    @Test
    public void testPlayer(){
       assertEquals("Duarte", p1.getUsername());
         assertEquals(1000, p2.getCurrentScore());
    }
    
    /**
     *Method that tests the class ErrorCode 
     */
    @Test
    public void testErrorCode(){
       assertEquals("One of the values for the coordinates is not allowed!", ec.MUST_USE_CORRECT_COORDINATES.toString());
         assertEquals("Please insert numbers only.", ec.NUMBERS_ONLY.toString());
    }
    
    
    /**
     * Method that test the class Board
     */
     @Test
    public void testBoard(){
         assertEquals(0, b.placeBlockOnBoard(a, 5, 5));
         
    }

    
}
