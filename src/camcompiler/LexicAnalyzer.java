/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camcompiler;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Andres
 */


public class LexicAnalyzer {
    
    private Logger errors = new Error();
    private Logger warnings = new Warning();
    private Reader reader;
    private int currentLine = 0;
    private String currentString;
    private int currentCode;
    private String currentChar;
    private SymbolsTable st;
    
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
    
    SemanticAction Sa0 = new SA0(this);
    SemanticAction Sa1 = new SA1(this);
    SemanticAction Sa2 = new SA2(this);
    SemanticAction Sa3 = new SA3(this);
    SemanticAction Sa4 = new SA4(this);
    SemanticAction Sa5 = new SA5(this);
    SemanticAction Sa6 = new SA6(this); //line++
    SemanticAction Sa7 = new SA7(this);
    SemanticAction Sa8 = new SA8(this);
        
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
    
    public LexicAnalyzer(String fileName, SymbolsTable st) throws IOException {
        System.out.println("Lexic Analyzer Created");
        this.reader = new Reader(fileName);
        this.st = st;
        
    }
    
    private int getColumn() {
        switch (currentChar) {
            case "a":  return 0; case "b":  return 0; case "c":  return 0; case "d":  return 0;
            case "e":  return 0; case "f":  return 0; case "g":  return 0; case "h":  return 0;
            case "i":  return 3; case "j":  return 0; case "k":  return 0; case "l":  return 1;
            case "m":  return 0; case "n":  return 0; case "o":  return 0; case "p":  return 0;
            case "q":  return 0; case "r":  return 0; case "s":  return 0; case "t":  return 0;
            case "u":  return 2; case "v":  return 0; case "w":  return 0; case "x":  return 0;
            case "y":  return 0; case "z":  return 0;
                
            case "A": return 14; case "B": return 14; case "C": return 14; case "D": return 14;
            case "E": return 14; case "F": return 14; case "G": return 14; case "H": return 14;
            case "I": return 14; case "J": return 14; case "K": return 14; case "L": return 14;
            case "M": return 14; case "N": return 14; case "O": return 14; case "P": return 14;
            case "Q": return 14; case "R": return 14; case "S": return 14; case "T": return 14;
            case "U": return 14; case "V": return 14; case "W": return 14; case "X": return 14;
            case "Y": return 14; case "Z": return 14; 
            
            case "0": return 4;  case "1": return 4;  case "2": return 4;  case "3": return 4;
            case "4": return 4;  case "5": return 4;  case "6": return 4;  case "7": return 4;
            case "8": return 4;  case "9": return 4;  
                
            case "@": return 5;  case "+": return 6;  case "/": return 7;  case "*": return 8;
            case "-": return 9;  case "<": return 10; case ">": return 11; case "=": return 12;
            case "_": return 13; case "(": return 15; case ")": return 16; case "\"":return 17;
            case ",": return 18; case ";": return 19; case "  ": return 21; case "/n": return 22;
            case " ": return 23; case "$": return 24; 
                
            default: return 20;
        }
    }
    
    public Map.Entry getToken() throws IOException {
        System.out.println("ENtre al getToken");
        this.currentString = "";        
        int currentState = 0;
        while (currentState != -1) {
            this.currentChar = reader.getChar();
            System.out.println("CURRENT CHAR: "+this.currentChar);
            if (this.currentChar == "/n") 
                Sa6.run();
            else 
                Sa0.run();
            int column = this.getColumn();
            sem_action[currentState][column].run();
            currentState = next_state[currentState][column];
        }
        
        //en estado final -> ya se tiene el par<currentString, currentToken> (atributo, num de Token)
        Token t = new Token(this.currentCode, this.currentString);
        //agregar a tabla de simbolos
        System.out.println("DESPUES DE WHILE: "+t.getValue());
        Map.Entry me = st.request(t);
        //ver que devolver (par de num de Token, referencia) ?        
        return me;
    }

    public Error getError() {
        return (Error)this.errors;
    }
    public Warning getWarning() {
        return (Warning)this.warnings;
    }
    public int getLine() {
        return this.currentLine;
    }
    
    public void increaseLines() {
        this.currentLine++;
    }
    
    public Reader getReader() {
        return this.reader;
    }
    public char getChar() {
        return this.currentChar.charAt(0);
    }    
    
    public String getString() {
        return this.currentString;
    }
    public void setString (String s) {
        this.currentString = s;
    }

    void setCode(int i) {
        this.currentCode = i;
    }
}
       

