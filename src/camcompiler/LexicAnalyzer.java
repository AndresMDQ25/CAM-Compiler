/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

/**
 *
 * @author Andres
 */
public class LexicAnalyzer {
    
    Logger errors = new Error();
    Logger warnings = new Warning();
    Reader reader = new Reader();
    
    private final int[][] next_state = {{2,2,2,2,3,1,-1,-1,-1,10,7,6,6,0,12,-1,-1,8,-1,-1,0,0,0,0,-1},
		 {2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
		 {2,2,2,2,2,2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                 {0,0,0,0,3,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,-1},
                 {0,0,5,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
                 {0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
                 {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                 {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                 {8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,-1,8,8,8,8,9,8,-1},
                 {0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1},
                 {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                 {11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,11,-1},
                 {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,12,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};
    
    SemanticAction Sa0 = new SA0();
    SemanticAction Sa1 = new SA1();
    SemanticAction Sa2 = new SA2();
    SemanticAction Sa3 = new SA3();
    SemanticAction Sa4 = new SA4();
    SemanticAction Sa5 = new SA5();
    SemanticAction Sa6 = new SA6(); //line++
    SemanticAction Sa7 = new SA7();
    SemanticAction Sa8 = new SA8();
        
    private SemanticAction[][] sem_action = {{Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa7,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa7,Sa4,Sa4,Sa4,Sa4},
                {Sa8,Sa8,Sa8,Sa8,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7},
                {Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1,Sa1},
                {Sa7,Sa7,Sa7,Sa7,Sa8,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa0,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7},
                {Sa7,Sa7,Sa8,Sa2,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7},
                {Sa7,Sa3,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7},
                {Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa8,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4},
                {Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa8,Sa8,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4},    
                {Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa7},
                {Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa8,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa7,Sa8,Sa7,Sa7},
                {Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa8,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4},
                {Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa8,Sa4},
                {Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa8,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4,Sa4}};
    
    public LexicAnalyzer() {
        
    }
    
    public Token getToken() {
        
    }
    public Logger getError() {
        return this.errors;
    }
    public Logger getWarning() {
        return this.warnings;
    }
    public String getLine() {
        
    }
    public getReader() {
        
    }
    public getChar() {
        
    }        
}
    

    

