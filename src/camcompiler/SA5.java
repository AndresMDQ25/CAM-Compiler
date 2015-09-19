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
public class SA5 extends SemanticAction{
    //VERIFICA PALABRAS RESERVADAS
    public SA5(){} 
    public Pair<Token, Integer> run(Token t,LexicAnalyzer lA){
        Error e = lA.getError();
        int line = lA.getLine();
        String word=t.getValue();                
        if(word.equals("IF"))
        {
            Pair p = new Pair(t,260);
            return p;
        }                         
        else if(word.equals("THEN"))
        {
            Pair p = new Pair(t,261);
            return p;
        }
        else if(word.equals("ELSE"))
        {
            Pair p = new Pair(t,262);
            return p;
        }    
        else if(word.equals("ENDIF"))
        {
            Pair p = new Pair(t,263);
            return p;
        }    
        else if(word.equals("PRINT"))
        {
            Pair p = new Pair(t,264);
            return p;
        }
        else if(word.equals("INT"))
        {
            Pair p = new Pair(t,265);
            return p;
        }    
        else if (word.equals("BEGIN"))
        {
            Pair p = new Pair(t,266);
            return p;
        }    
        else if(word.equals("END"))
        {
            Pair p = new Pair(t,267);
            return p;
        }        
        else if(word.equals("UNSIGNED"))
        {
            Pair p = new Pair(t,268);
            return p;
        }    
        else if(word.equals("LONG"))
        {
            Pair p = new Pair(t,269);
            return p;
        }
        else if(word.equals("MY"))
        {
            Pair p = new Pair(t,270);
            return p;
        }
        else if (word.equals("LOOP"))
        {
            Pair p = new Pair(t,271);
            return p;
        }
        else if(word.equals("FROM"))
        {
            Pair p = new Pair(t,272);
            return p;
        }
        else if(word.equals("TO"))
        {
            Pair p = new Pair(t,273);
            return p;
        }        
        else if (word.equals("BY"))
        {
            Pair p = new Pair(t,274);
            return p;
        }
        else{
            e.addLog("not recognized reserved word", line);
            Pair p= new Pair(null,-1);
            return p;   
        }
    }
    
}
