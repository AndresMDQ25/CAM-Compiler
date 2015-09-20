/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

public class SA5 extends SemanticAction{
    //VERIFICA PALABRAS RESERVADAS
    public SA5(LexicAnalyzer lA) {
        super(lA);
    } 
    public void run(){
        Error e = lA.getError();
        int line = lA.getLine();
        String word=lA.getString();
        switch(word) {
            case "IF" : lA.setCode(261);
            case "THEN" : lA.setCode(262);
            case "ELSE" : lA.setCode(263);
            case "ENDIF" : lA.setCode(264); 
            case "PRINT" : lA.setCode(265); 
            case "INT" :  lA.setCode(266);
            case "BEGIN" :  lA.setCode(267);
            case "END" :  lA.setCode(268);
            case "UNSIGNED" :  lA.setCode(269);
            case "LONG" :  lA.setCode(270); 
            case "MY" :  lA.setCode(271);
            case "LOOP" :  lA.setCode(272); 
            case "FROM" :  lA.setCode(273);
            case "TO" :  lA.setCode(274); 
            case "BY" :  lA.setCode(275); 
            default : {
                e.addLog("not recognized reserved word", line);
                lA.setCode(257);
            }
        }
    }
}
