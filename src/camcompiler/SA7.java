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
public class SA7 extends SemanticAction{
    //ERROR DE CARACTER INVALIDO
    public SA7(){}         
    public Pair<Token, Integer> run(Token t, LexicAnalyzer lA) {
        Error e = lA.getError(); 
        int line = lA.getLine();
        e.addLog("invalid character or expression", line);
        Pair p= new Pair(null,-1);
        return p;
    }
}
