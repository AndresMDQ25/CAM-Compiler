/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import javafx.util.Pair;

/**
 *
 * @author Andres
 */
public abstract class SemanticAction {
    public abstract Pair<Token,Integer> run(Token t,LexicAnalyzer lA);
}
