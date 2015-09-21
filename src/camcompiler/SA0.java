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
public class SA0 extends SemanticAction{
    //AGREGA UN CARACTER AL FINAL DEL TOKEN
    public SA0(LexicAnalyzer lA) {
        super(lA);
    } 
    public void run(){
        char c = lA.getChar();
        String s;
        s = lA.getString();
        s += c;
        lA.setString(s);
        if (c == '$') {
            lA.setCode(260);
        }
    }    
}
