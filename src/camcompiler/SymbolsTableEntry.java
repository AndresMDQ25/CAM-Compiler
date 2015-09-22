/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

/**
 *
 * @author Mariano
 */
class SymbolsTableEntry {
    private final int code;
    private final String lexema;
    private int cant;
    private final int type;
    private final Token t;
    
    public SymbolsTableEntry(int code,int type, String lexema, int cant, Token t){
        this.code=code;
        this.type=type;
        this.lexema=lexema;
        this.cant=cant;
        this.t=t;
    }
    
    public int getCode(){return this.code;}
    public String getLexema(){return this.lexema;}
    public Token getToken(){return this.t;}
    public String getName(){return this.type+this.lexema;}
    public int getCant() {return this.cant;}
    public int getType() {return this.type;}
    public void increaseCant(){this.cant++;}    
}
