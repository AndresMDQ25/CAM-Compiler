package camcompiler;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class Consumer {
    
    private final LexicAnalyzer l;
    private const Token _TOKENFIN=new Token("$");
    private final Vector<Map.Entry> v;

    public Consumer (LexicAnalyzer newL){
        this.l=newL;
        v=new Vector();
    }
    
    public void consume() throws IOException{
        Map.Entry aux=l.getToken();
        v.add(aux);
        while (!aux.equals(_TOKENFIN))
            v.add(aux);
    }
    
    public void showConsumed(){
        for (Map.Entry v1 : v) {
            System.out.println(((Token)(v1.getKey())).getCode() + " " + ((Token)(v1.getKey())).getValue());
        }
    }
}       
            
            