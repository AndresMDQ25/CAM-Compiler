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
public class SA7 extends SemanticAction{
    //ERROR DE CARACTER INVALIDO
    public SA7(LexicAnalyzer lA) {
        super(lA);
    }         

    /**
     *
     */
    @Override
    public void run() {
        ErrorLex e = lA.getError(); 
        int line = lA.getLine();
        e.addLog("invalid character or expression", line);
        lA.setString(new String());
        lA.setCode(0);
        lA.setCurrentState(0);
    }
}
