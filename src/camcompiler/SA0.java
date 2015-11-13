package camcompiler;

public class SA0 extends SemanticAction{
    //Accion Semantica 0: AGREGA UN CARACTER AL FINAL DEL TOKEN
    public SA0(LexicAnalyzer lA) {
        super(lA);
    } 

    public void run(){
        String c = lA.getChar();
        String s;
        s = lA.getString();
        s+=c;
        lA.setString(s);
        if (c == "$")
                lA.setCode(260); //FINAL TOKEN
    }
}
