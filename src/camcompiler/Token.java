/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.util.Objects;

/**
 *
 * @author Mariano
 */
public class Token {

    private String value;
    private int code;
    private int pointer;
    public Token(){value=new String();}
    public Token(Token t){this.value=t.getValue();}
    public Token(int code, String value) {
        this.code = code; 
        this.value = value;
    }
    public void setPointer(int pointer) {this.pointer = pointer;}
    public int getPointer() {return this.pointer;}
    public int getCode(){return code;}
    public String getValue() {return value;}
    public void setValue(String value) {this.value=value;}
    public void addAtEnd (char c) {value=value+c;}
    
    @Override
    public boolean equals(Object t){
        return ((Token) t).getCode()==code && ((Token)t).getValue().equals(this.value);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.value);
        hash = 23 * hash + this.code;
        return hash;
    }
}
