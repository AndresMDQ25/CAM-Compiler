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
public class Token {

    String value;
    int code;
    public Token(){value=new String();}
    public Token(Token t){this.value=t.getValue();}
    public Token(int code, String value) {this.code = code; this.value = value; }
    public int getCode(){return code;}
    public String getValue() {return value;}
    public void setValue(String value) {this.value=value;}
    public void addAtEnd (char c) {value=value+c;}
    
    //@override
    public boolean equals(Token t){
        return t.getCode()==code && t.getValue().equals(this.value);
    }
}
