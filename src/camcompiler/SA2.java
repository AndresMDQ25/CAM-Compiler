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
public class SA2 extends SemanticAction{
    //CHEQUEA RANGO DE CTES  -> -2^15 -1<n< 2^15 -1 Y SACA _i
    public SA2(){}
    public Pair<Token, Integer> run (Token t, LexicAnalyzer lA ){
        Error e = lA.getError();
        String s = t.getValue();
        int line = lA.getLine();
        //REMOVES _i
        char[] dst = new char[s.length()-2];                
        s.getChars(0, s.length()-2, dst, 0);
        s= "";
        for (char ch: dst){
            s+=ch;
        }
        int value= Integer.valueOf(s);                
        short maxShort = Short.MAX_VALUE;
        short minShort = Short.MIN_VALUE;        
        if ((value >= minShort) &&(value <= maxShort))
            t.setValue(Integer.toString(value));
        else
        {
            e.addLog("Constant out of range", line);
            t.setValue("");                        
        } 
        Pair p = new Pair(t,265);
    return p;
    }
}
