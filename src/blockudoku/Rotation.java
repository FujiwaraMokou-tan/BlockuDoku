/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

/**
 * This enumeration class contains all the four allowed tyes of rotations for the blocks and sends how many times it should rotate 90º
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/20
 */
public enum Rotation
{
    ZERO, NINETY, ONE_HUNDRED_EIGHTY, TWO_HUNDRED_SEVENTY;
    
    /**
     * Method to get the right number of 90º rotations
     * @return (int value), returns the number of times the block will be rotated 90º
     */
    public int getNumberOfRotations(){
        switch(this){
            case ZERO:
            return 0;
            case NINETY:
            return 1;
            case ONE_HUNDRED_EIGHTY:
            return 2;
            case TWO_HUNDRED_SEVENTY:
            return 3;
            default:
            return 0;
        }
    }
}
