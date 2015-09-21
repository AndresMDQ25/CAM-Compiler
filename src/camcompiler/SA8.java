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
        String word=lA.getString();
        switch(word) {
            case "+" : lA.setCode(276);
            case "*" : lA.setCode(276);
            case "/" : lA.setCode(276); 
            }
    }
}
