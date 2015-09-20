/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

public abstract class SemanticAction {
    LexicAnalyzer lA;
    public SemanticAction(LexicAnalyzer lA){
        this.lA = lA;
    }
    public abstract void run();
}
