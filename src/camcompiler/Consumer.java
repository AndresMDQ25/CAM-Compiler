/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.util.Vector;

/**
 *
 * @author Mariano
 */
public class Consumer {
    
    private LexicAnalyzer l;
    private const Token _TOKENFIN=new FinalToken();
    private Vector<Token> v;

    public Consumer (LexicAnalyzer newL){
        this.l=newL;
        v=new Vector();
    }
    
    public void consume(){
        Token aux=l.getToken();
        v.add(aux);
        while (!aux.equals(_TOKENFIN))
            v.add(aux);
    }
    
    public void showConsumed(){
        for (Token v1 : v) {
            System.out.println(v1.getCode()+"  "+v1.getValue());
        }
    }
}
