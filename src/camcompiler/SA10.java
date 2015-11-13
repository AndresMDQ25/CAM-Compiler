package camcompiler;

public class SA10 extends SemanticAction {
    //Accion Semantica 10: SETEA TOKEN STRING 
    public SA10(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){        
        String newValue = lA.getString();
        newValue = newValue.substring(0, newValue.length()-1);
        lA.setString(newValue); 
        lA.setCode(277); //STRING TOKEN
    }
}