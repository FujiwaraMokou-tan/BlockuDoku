/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blockudoku;

/**
 * This class is destinated to reciece a error code
 *
 * @author Eduardo Ferreira and Duarte Conceição
 * @version 15/05/2020
 */
public class BlockuDokuIllegalArgumentException extends IllegalArgumentException
{
    private ErrorCode errorCode;

    /**
     * Constructor for objects of class BlockuDokuIllegalArgumentException, that recieves a error code
     */
    public BlockuDokuIllegalArgumentException(ErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }

   //Getter
    public ErrorCode getErrorCode(){
        return errorCode;
    }
}
