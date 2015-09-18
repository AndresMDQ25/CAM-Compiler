/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

/**
 *
 * @author Andres
 */
public class SA3 extends SemanticAction{
    @Override
    //Check that constant range be in 0<n< 2^32 -1
    public Token run (Token t){        
        Token result;
        int value= Integer.valueOf(t.getValue);                
        long maxUnsigned = 4294967295L;
        //2^32 -1   4294967295
       if ((value > 0) &&(!(value < maxUnsigned)))
            result.setValue(Integer.toString(value));
       else
       {
            result.setValue("");            
            //Error constante fuera de rango
       }     
//0<token< 2^32 - 1       
    return result;
    }
}
