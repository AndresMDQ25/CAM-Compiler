package camcompiler;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class Consumer {
    
    private final LexicAnalyzer l;
    private  Token _TOKENFIN=new Token(260,"$");
    private final Vector<Integer> v;

    public Consumer (LexicAnalyzer newL){
        this.l=newL;
        v=new Vector();
        System.out.println("Consumer created");
    }
    
    public void consume() throws IOException{         
        int aux=l.getToken();
        System.out.println("DESPUES DE GET TOKEN");        
        v.add(aux);
        SymbolsTableEntry s=(l.getST().getEntry(aux));
        while (!((Token)s.getToken()).equals(_TOKENFIN)){
            aux=l.getToken();
            v.add(aux);
            s=(l.getST().getEntry(aux));
        }
            
    }
    
    public String showConsumed(){
        String t = new String();
        for (Integer i : v) {
            SymbolsTableEntry s=(l.getST().getEntry(i));
           t+=(s.getCode() + " "+s.getType() + " " + s.getLexema())+"\n";          
            //t+=((Token)(v1.getKey())).getCode() + " " + ((Token)(v1.getKey())).getValue();
            //System.out.println(t);
        }
        return t;
    }
}       
            
            