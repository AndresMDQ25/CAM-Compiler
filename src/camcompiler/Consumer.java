package camcompiler;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class Consumer {
    
    private final LexicAnalyzer l;
    private  Token _TOKENFIN=new Token(260,"$");
    private final Vector<Map.Entry> v;

    public Consumer (LexicAnalyzer newL){
        this.l=newL;
        v=new Vector();
        System.out.println("Consumer created");
    }
    
    public void consume() throws IOException{         
        Map.Entry aux=l.getToken();
        System.out.println("DESPUES DE GET TOKEN");        
        v.add(aux);
        while (!aux.equals(_TOKENFIN)){
            aux=l.getToken();
            v.add(aux);
        }
            
    }
    
    public String showConsumed(){
        String t = null;
        for (Map.Entry v1 : v) {
            //System.out.println(((Token)(v1.getKey())).getCode() + " " + ((Token)(v1.getKey())).getValue());          
            t+=((Token)(v1.getKey())).getCode() + " " + ((Token)(v1.getKey())).getValue();
            System.out.println(t);
        }
        return t;
    }
}       
            
            