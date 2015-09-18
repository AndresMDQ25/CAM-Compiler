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
public class SA5 extends SemanticAction{
    //VERIFICA PALABRAS RESERVADAS
    public SA5(){} 
    public Token run(Token t, Error e, int line){
        String word=t.getValue();
        if(word.equals("IF")||word.equals("LOOP")||word.equals("BY")||word.equals("MY")||
           word.equals("THEN")||word.equals("ELSE")||word.equals("ENDIF")||word.equals("PRINT")||
           word.equals("INT")||word.equals("BEGIN")||word.equals("END")||word.equals("FROM")||word.equals("TO")||
           word.equals("UNSIGNED")||word.equals("LONG"))
            return t;
        else{
         e.addLog("not recognized reserved word", line);
         return null;   
        }
    }
    
}
