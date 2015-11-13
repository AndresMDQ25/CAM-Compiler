package camcompiler;

public class SA9 extends SemanticAction{
    //Accion Semantica 9: BORRA TODO
    public SA9(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){
        lA.increaseLines();
        lA.setString(new String());
        lA.setCode(0);
        lA.setCurrentState(0);
        lA.setCurrentChar(new String());
    }    
}

