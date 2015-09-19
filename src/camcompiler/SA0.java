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
public class SA0 extends SemanticAction{
    //AGREGA UN CARACTER AL FINAL DEL TOKEN
    public SA0(){} 
    public Pair<Token, Integer> run(Token t, LexicAnalyzer lA){
        char c = lA.getChar();
        t.addAtEnd(c);
        Pair p = new Pair(t,-1);
        return p;
    }
    
}
