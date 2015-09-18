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
    public SA3(){}
    //Check  constant range  -> 0<n< 2^32 -1 and removes _ul
    public Token run (Token t,LexicAnalyzer lA){
        Error e = lA.getError();
        String s = t.getValue();
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
       if ((value > 0) &&(!(value < maxUnsigned)))
            t.setValue(Integer.toString(value));
       else
       {
            e.addLog("Constant out of range", line);
            t.setValue("");                        
       }     
//0<token< 2^32 - 1       
    return t;
    }
}
