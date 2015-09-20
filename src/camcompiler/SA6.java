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
public class SA6 extends SemanticAction{
    //AUMENTA EN 1 LA CANTIDAD DE LINEAS QUE GUARDA EL ANALIZADOR LEXICO
    public SA6(){} 
    public void run(LexicAnalyzer lA){
        lA.increaseLines();
    }
}
