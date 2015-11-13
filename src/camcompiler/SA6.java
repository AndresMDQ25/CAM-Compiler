package camcompiler;

public class SA6 extends SemanticAction{
    //Accion Semantica 6: AUMENTA EN 1 LA CANTIDAD DE LINEAS QUE GUARDA EL ANALIZADOR LEXICO
    public SA6(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){
        lA.increaseLines();
        String newValue=lA.getString();
        newValue= newValue.substring(0, newValue.length()-2);
        lA.setString(newValue);         
    }
}
