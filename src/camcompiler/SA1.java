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
    public SA1(LexicAnalyzer lA) {
        super(lA);
    } 

    /**
     *
     */
    @Override
    public void run(){
        SemanticAction sa = new SA4(lA);
        sa.run();
        Warning w = lA.getWarning();
        int line = lA.getLine();
        String value= lA.getString();
        if (value.length()>_LONG){
            value=value.substring(0, _LONG-1);
            lA.setString(value);
            w.addLog("truncated chain",line);
        }
        lA.setCode(257);

    }
}
