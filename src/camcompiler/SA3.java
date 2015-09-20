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
    public SA3(LexicAnalyzer lA) {
        super(lA);
    }
    //CHECKEA RANGO UNSIGNED LONG -> 0<n< 2^32 -1 and removes _ul
    public void run (){
        Error e = lA.getError();
        String s = lA.getString();
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
            lA.setString(Integer.toString(value));
            lA.setCode(258);
        }
       else
       {
            e.addLog("Constant out of range", line);
            lA.setString("");  
            lA.setCode(-1);
       }            
    }
}
