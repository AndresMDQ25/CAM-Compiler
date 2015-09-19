/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import javafx.util.Pair;

/**
 *
 * @author Andres
 */
public class SA3 extends SemanticAction{  
    public SA3(){}
    //CHECKEA RANGO UNSIGNED LONG -> 0<n< 2^32 -1 and removes _ul
    public Pair<Token, Integer> run (Token t,LexicAnalyzer lA){
        Error e = lA.getError();
        String s = t.getValue();
        int line = lA.getLine();
        //REMOVES _ul
        char[] dst = new char[s.length()-3];                
        s.getChars(0, s.length()-3, dst, 0);
        s= "";
        for (char ch: dst){
            s+=ch;
        }
        int value= Integer.valueOf(s);                       
        long maxUnsigned = 4294967295L;
        //2^32 -1   4294967295
        if ((value > 0) &&(!(value < maxUnsigned))){
            t.setValue(Integer.toString(value));
            Pair p = new Pair(t,268);
            return p;
        }
       else
       {
            e.addLog("Constant out of range", line);
            t.setValue("");                        
            Pair p = new Pair(null,-1);
            return p;
       }            
    }
}
