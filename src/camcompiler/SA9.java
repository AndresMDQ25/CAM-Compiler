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
public class SA9 extends SemanticAction{
    //BORRA TODO
    public SA9(LexicAnalyzer lA) {
        super(lA);
    } 
    public void run(){
        lA.setString("");
    }    
}

