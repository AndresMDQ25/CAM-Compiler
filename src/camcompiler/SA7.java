package camcompiler;

public class SA7 extends SemanticAction{
    //Accion Semantica 7: ERROR DE CARACTER INVALIDO
    public SA7(LexicAnalyzer lA) {
        super(lA);
    }         

    public void run() {
        CAMerror e = lA.getError(); 
        int line = lA.getLine();
        e.addLog("invalid character or expression", line);
        if(lA.getChar().equals("/n"))
            lA.increaseLines();
        lA.setString(new String());
        lA.setCode(0);
        lA.setCurrentState(0);
        lA.setCurrentChar(new String());
    }
}
