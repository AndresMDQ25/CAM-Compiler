/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import javafx.util.Pair;

/**
 *
 * @author Mariano
 */
public class SA4 extends SemanticAction{
    //BORRA EL ULTIMO CARACTER DEL TOKEN Y RETROCEDE EL READER
    public SA4(){} 
    public Pair<Token, Integer> run(Token t,LexicAnalyzer lA){
        Reader r =  lA.getReader();
        String newValue=t.getValue();
        newValue=newValue.substring(0, newValue.length()-2);
        t.setValue(newValue);
        r.goToPrevChar();        
        Pair p= new Pair(t,-1);
        return p;
    }
}
