package camcompiler;

public class SymbolsTableEntry {
    private final int code;
    private String lexema;
    private int cant;
    private final int ltype;
    private String stype = "          ";
    private final Token t;
    
    public SymbolsTableEntry(int code,int ltype, String lexema, int cant, Token t){
        this.code=code;
        this.ltype=ltype;
        this.lexema=lexema;
        this.cant=cant;
        this.t=t;
    }
    
    public int getCode() {
        return this.code;
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
        String s = this.code +"           "+ sType +"           "+ this.stype +"           "+ this.cant+"           "+ this.lexema ; 
        return s;
    }
    public String getLexema() {
        return this.lexema;
    }
    public Token getToken() {
        return this.t;
    }
    public String getName() {
        return this.ltype+this.lexema;
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
    public void increaseCant() {
        this.cant++;
    }
    public void setLexema(String lexema){
        this.lexema = lexema;
    }
    public void setSType(String syntacticType) {
        this.stype = syntacticType;
    }
    
}
