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
    }
    
    public void consume() throws IOException{         
        int aux=l.yylex();
        v.add(aux);
        SymbolsTableEntry s=(l.getST().getEntry(aux));
        while (!((Token)s.getToken()).equals(_TOKENFIN)){
            aux=l.yylex();
            v.add(aux);
            s=(l.getST().getEntry(aux));
        }
            
    }
    
    private String toToken(int number) {
        switch (number) {
            case 257 : return "ID";
            case 258 : return "CTE";
            case 259 : return "ERROR";
            case 260 : return "FINAL";
            case 261 : return "IF";
            case 262 : return "THEN";
            case 263 : return "ELSE";
            case 264 : return "ENDIF";
            case 265 : return "PRINT";
            case 266 : return "INT";
            case 267 : return "BEGIN";
            case 268 : return "END";
            case 269 : return "UNSIGNED";
            case 270 : return "LONG";
            case 271 : return "MY";
            case 272 : return "LOOP";
            case 273 : return "FROM";
            case 274 : return "TO";
            case 275 : return "BY";
            case 276 : return "OPERATOR";
            case 277 : return "STRING";
         
        }
        return "";
    }
    
    public String showConsumed(){
        String t = new String();
        t+="Index number - Token type - Token value \n";
        for (Integer i : v) {
            SymbolsTableEntry s=(l.getST().getEntry(i));
           t+=(s.getCode() + "                 - "+toToken(s.getType()) + "            -           " + s.getLexema())+"\n";          
            //t+=((Token)(v1.getKey())).getCode() + " " + ((Token)(v1.getKey())).getValue();
            //System.out.println(t);
        }
        return t;
    }
}       
            
            