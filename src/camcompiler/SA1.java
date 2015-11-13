package camcompiler;

public class SA1 extends SemanticAction{
    //Accion Semantica 1: TRUNCA NOMBRES MAYORES A 15
    private static final int _LONG = 15;
    public SA1(LexicAnalyzer lA) {
        super(lA);
    } 

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
        lA.setCode(257); //IDENTIFIER TOKEN

    }
}
