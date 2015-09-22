/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

public class SA8 extends SemanticAction{
    //NO HACE NADA
    public SA8(LexicAnalyzer lA){
        super(lA);
    } 
    public void run(){
        String word = lA.getString();
        String symbol=lA.getChar();
        switch(symbol) {
            case "+" : { lA.setCode(276); break;}
            case "*" : { lA.setCode(276); break;}
            case "/" : { lA.setCode(276); break;}
            case "," : { lA.setCode(276); break;}
            case ";" : { lA.setCode(276); break;}
            case "-" : {
                    if (word.length() > 1) {
                        word = word.substring(0, word.length()-1);
                        lA.setString(word);
                    }
                    else {
                        lA.setCode(276);
                    }                    
                    break;
            }
        }
    }
}
