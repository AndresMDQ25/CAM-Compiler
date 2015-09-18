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
public class SA7 extends SemanticAction{
    //ERROR DE CARACTER INVALIDO
    public SA7(){} 
    public Token run(Error e, int line){
        e.addLog("invalid character or expression", line);
        return null;
    }
}
