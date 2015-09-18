package camcompiler;

import java.util.Vector;

public class Consumer {
    
    private LexicAnalyzer l;
    private const Token _TOKENFIN=new FinalToken();
    private Vector<Token> v;

    public Consumer (LexicAnalyzer newL){
        this.l=newL;
        v=new Vector();
    }
    
    public void consume(){
        Token aux=l.getToken();
        v.add(aux);
        while (!aux.equals(_TOKENFIN))
            v.add(aux);
    }
    
    public void showConsumed(){
        for (Token v1 : v) {
            System.out.println(v1.getCode()+"  "+v1.getValue());
        }
    }
}       
            
            