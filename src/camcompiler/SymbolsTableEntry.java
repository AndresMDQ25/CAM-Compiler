package camcompiler;

public class SymbolsTableEntry {
    private final int code;
    private String lexema;
    private int cant;
    private final int ltype;
    private String stype = "          ";
    private final Token t;
    private String scope; 
    private String myScope = "";
    public SymbolsTableEntry(int code,int ltype, String lexema, int cant, Token t){
        this.code=code;
        this.ltype=ltype;
        this.lexema=lexema;
        this.cant=cant;
        this.t=t;
        this.scope = "";
    }
    
    public int getCode() {
        return this.code;
    }
    
    public void setMyScope(String scope) {
        this.myScope = scope;
    }
    
    public String getMyScope() {
        return this.myScope;
    }

    public String toString() {
        String sType;
        switch (this.ltype) {
            case 277:  sType="ST";
                      break;
            case 257:  sType="ID";
                      break;
            case 258: sType="CT";
                      break;
            case 294: sType="CT";
                      break;
            default: sType="NK";
                      break;
        }
        String s = this.code +"           "+ sType +"           "+ this.stype +"           "+ this.cant+"           "+ this.lexema+this.scope; 
        return s;
    }
    public String getLexema() {
        return this.lexema;
    }
    public Token getToken() {
        return this.t;
    }
    public String getName() {
        if (this.ltype == 277) 
            return "string"+this.stype;
        else
            return this.lexema+this.scope; 
    }
    public String getASMName() {
        if (this.ltype == 277) {
            String lexeme = this.lexema;
            String toReturn = "string"+this.stype+" db \""+this.lexema+"\", 10";
            return toReturn;
        }
        else if (this.stype.equals("ULONG")) {
            if (this.ltype == 258)
                return "_"+this.lexema+this.scope+" dd "+this.lexema;
            else
                return "_"+this.lexema+this.scope+" dd 0";   
        }
        else {
            if (this.ltype == 258)
                return "_"+this.lexema+this.scope+" dw "+this.lexema;
            else
                return "_"+this.lexema+this.scope+" dw 0"; 
        }
    }
    public int getCant() {
        return this.cant;
    }
    public int getType() {
        return this.ltype;
    }
    public String getSType() {
        return this.stype;
    }
    public String getScope() {
        return this.scope;
    }
    public void increaseCant() {
        this.cant++;
    }
    public void setLexema(String lexema){
        this.lexema = lexema;
    }
    public void addScope(String scope) {
        this.scope = scope;
    }
    public void setSType(String syntacticType) {
        this.stype = syntacticType;
    } 
}
