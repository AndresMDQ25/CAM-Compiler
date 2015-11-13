package camcompiler;

public class SA11 extends SemanticAction {
    //Accion Semantica 11: BORRA EL ULTIMO CARACTER DEL TOKEN, RETROCEDE EL READER Y SETEA TOKEN DE OPERADOR
    public SA11(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){        
        SemanticAction s=new SA4(lA);
        s.run();
        s=new SA8(lA);
        s.run();
    }
}