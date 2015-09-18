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
public class AS0 extends SemanticAction{
    //AGREGA UN CARACTER AL FINAL DEL TOKEN
    public AS0(){} 
    public Token run(Token t, char c){
        t.addAtEnd(c);
        return t;
    }
    
}
