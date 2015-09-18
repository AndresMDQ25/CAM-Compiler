/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

/**
 *
 * @author Mariano
 */
public class SA4 extends SemanticAction{
    //BORRA EL ULTIMO CARACTER DEL TOKEN Y RETROCEDE EL READER
    public SA4(){} 
    public Token run(Reader r, Token t){
        String newValue=t.getValue();
        newValue=newValue.substring(0, newValue.length()-2);
        t.setValue(newValue);
        r.goToPrevChar();
        return t;
    }
}
