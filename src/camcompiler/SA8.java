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
public class SA8 extends SemanticAction{
    //NO HACE NADA
    public SA8(){} 
    @Override
    public Pair<Token,Integer> run(Token t, LexicAnalyzer lA){
        Pair p= new Pair(null,-1);
        return p;
    }
    
}
