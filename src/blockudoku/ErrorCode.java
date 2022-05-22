/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;
/**
 * Enumeration class ErrorCode is dedicated to have all the possible errors 
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/2020
 */
public enum ErrorCode
{
    MUST_USE_CORRECT_COORDINATES, THERES_A_BLOCK_THERE, THERES_A_PROBLEM_LOADING_FILE, NUMBERS_ONLY;
    
    /**
     * Method that returns the text for one especified error
     * @return 
     */
    @Override
    public String toString() {
        switch (this) {
            case MUST_USE_CORRECT_COORDINATES:
            return "One of the values for the coordinates is not allowed!";
            case THERES_A_BLOCK_THERE:
            return "The Block you're trying to place crashes with another Block";
            case THERES_A_PROBLEM_LOADING_FILE:
            return "There is no saveFile to load from, you must save a game in order to load it.";
            case NUMBERS_ONLY:
            return "Please insert numbers only.";
            default:
            return "";
        }
    }
}

