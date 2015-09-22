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
public class SA11 extends SemanticAction {
    public SA11(LexicAnalyzer lA) {
        super(lA);
    } 

    /**
     *
     */
    @Override
    public void run(){        
        SemanticAction s=new SA4(lA);
        s.run();
        s=new SA8(lA);
        s.run();
    }
}