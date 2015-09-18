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
public class SA1 extends SemanticAction{
    //TRUNCA MAYORES A 15
    private static final int _LONG = 15;
    public SA1(){} 
    public Token run(Token t,LexicAnalyzer lA ){
        Warning w = lA.getWarning();
        int line = lA.getLine();
        Reader r = lA.getReader();
        String value=t.getValue();
        SemanticAction sa = new SA4();
        sa.run(r, t);
        if (value.length()>_LONG){
            value=value.substring(0, _LONG-1);
            t.setValue(value);
            w.addLog("truncated chain",line);
        }

        return t;

    }
}
