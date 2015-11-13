package camcompiler;

public class SA13 extends SemanticAction {
    //Accion Semantica 13: ELIMINA EL ULTIMO CARACTER
    public SA13(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){        
        String newValue = lA.getString();
        newValue = newValue.substring(0, newValue.length()-1);
        lA.setString(newValue); 
    }
}