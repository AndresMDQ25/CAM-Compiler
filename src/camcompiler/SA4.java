package camcompiler;

public class SA4 extends SemanticAction{
    //Accion Semantica 4: BORRA EL ULTIMO CARACTER DEL TOKEN Y RETROCEDE EL READER
    public SA4(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){        
        Reader r =  lA.getReader();
        String newValue = lA.getString();
        String lastChar = lA.getChar();
        r.goToPrevChar();
        newValue = newValue.substring(0, newValue.length()-lastChar.length());
        lA.setString(newValue); 
    }
}
