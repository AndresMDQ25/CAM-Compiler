//declaraciones
%{
package byacc;


import camcompiler.CAMerror;
import camcompiler.LexicAnalyzer;
import camcompiler.Token;
import java.io.IOException;
import camcompiler.SymbolsTable;
import camcompiler.SymbolsTableEntry;
import camcompiler.SyntacticLogger;

import java.util.Vector;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;


%}

%token ID CTEINT ERROR FINAL IF THEN ELSE ENDIF PRINT INT BEGIN END UNSIGNED LONG MY LOOP FROM TO BY SEMICOLON STRING PLUSLE MINUN MULTIPLY DIVIDE EQUAL COMMA LEFTPARENTHESIS RIGHTPARENTHESIS GREATTHAN LESSTHAN LEFTBRACE RIGHTBRACE EQUALEQUAL GREATEQUAL LESSEQUAL DISTINCT CTEUL

%left PLUSLE MINUN
%left MULTIPLY DIVIDE

%%
programa            : bloque{}
                    ;
bloque              : declarativas ejecutables
                    | error {SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine());}
                    ;

bloquesentencias    : declarativas ejecutables 
                    | ejecutables 
                    ;

declarativas        : declarativas declarativa {}
                    | declarativa 
                    ;

declarativa         : tipo listavariables SEMICOLON {
                                                        synlog.addLog("Declaration",lexicAnalyzer.getLine());
                                                        for (int i = 0; i < IDlist.size(); i++) {
                                                            SymbolsTableEntry entry = symbolsTable.getEntry(IDlist.elementAt(i));
                                                            entry.setSType(currentType);
                                                            entry.addScope(myScope.getScopeSuffix());
                                                        }
                                                        IDlist.removeAllElements();
                                                        currentType = "";
                                                    }
                    | error {SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine());}
                    ;

listavariables      : listavariables COMMA ID {int pointer = $3.tok.getPointer(); IDlist.addElement(pointer);}
                    | ID {int pointer = $1.tok.getPointer(); IDlist.addElement(pointer);}
                    | error {SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine());}
                    ;

tipo                : INT {currentType = "INTEG";}
                    | UNSIGNED LONG {currentType = "ULONG";}
                    ;

ejecutables         : BEGIN listaejecutables END
                    | ejecutablesimple 
                    ;

listaejecutables    : listaejecutables ejecutable 
                    | ejecutable 
                    ;

ejecutablesimple    : listaambitos 
                    | ejecutable 
		    | error {SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine());}
                    ;

ejecutable          : asignacion SEMICOLON {synlog.addLog("Asignation",lexicAnalyzer.getLine());}
                    | sentenciaIF {synlog.addLog("IF",lexicAnalyzer.getLine());}
                    | sentenciaLOOP SEMICOLON {synlog.addLog("LOOP",lexicAnalyzer.getLine());}
                    | sentenciaPRINT SEMICOLON {synlog.addLog("PRINT",lexicAnalyzer.getLine());}
                    ;

asignacion          : identificador EQUAL expresion {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | identificador error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
                    ;

identificador       : ID {  
                            int pointer = $1.tok.getPointer();
                            pInv.add(pointer);
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());                                                        
                            boolean isInScope = symbolsTable.inScope(pointer);
                            if (!isInScope) {
                                SyntaxError.addLog("Variable not declared",lexicAnalyzer.getLine());
                                symbolsTable.removeEntry(pointer);
                            }
                            $$ = $1;}
                    ;

asignacionLOOP      : identificador EQUAL expresionLOOP {String toAdd = $2.tok.getValue();
                                                        pInv.add(toAdd);}
                    | ID error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
                    ;

expresion           : expresion PLUSLE termino { String toAdd = $2.tok.getValue();
                                                pInv.add(toAdd);
                                                }
                    | expresion MINUN termino { String toAdd = $2.tok.getValue();
                                                pInv.add(toAdd);}
                    | termino
                    ;

expresionLOOP       : expresionLOOP PLUSLE terminoLOOP {String toAdd = $2.tok.getValue();
                                                        pInv.add(toAdd);}
                    | expresionLOOP MINUN terminoLOOP {String toAdd = $2.tok.getValue();
                                                        pInv.add(toAdd);}
                    | terminoLOOP
                    ;

termino             : termino MULTIPLY factor {String toAdd = $2.tok.getValue();
                                                pInv.add(toAdd);}
                    | termino DIVIDE factor {String toAdd = $2.tok.getValue();
                                                pInv.add(toAdd);}
                    | factor 
                    ;

terminoLOOP         : terminoLOOP MULTIPLY factorLOOP {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | terminoLOOP DIVIDE factorLOOP  {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | factorLOOP 
                    ;

factorLOOP          : identificador 
                    | CTEINT {  int pointer = $1.tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
                    | MINUN CTEINT {
                                $2.tok.setValue("-"+$2.tok.getValue());                                
                                int pointer = $2.tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
                    ; 

factor              : identificador
                    | constant 
                    | MINUN CTEINT {                                    
                                $2.tok.setValue("-"+$2.tok.getValue());
                                int pointer = $2.tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
                    ;

constant            : CTEINT {
                                int pointer = $1.tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
                    | CTEUL {
                                int pointer = $1.tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "ULONG";
                                entry.setSType(temp);
                            }
                    ;

sentenciaIF         : IF LEFTPARENTHESIS condicionIF RIGHTPARENTHESIS THEN cuerpoIF {int nro_p_inc = pila.pop(); completar(nro_p_inc, nro_p + 1);}
                    | IF error {SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
                    ;

cuerpoIF            : bloquesentenciasTHEN ENDIF
                    | bloquesentenciasTHEN ELSE bloquesentencias ENDIF
                    ;

bloquesentenciasTHEN: bloquesentencias {int nro_p_inc = pila.pop(); completar(nro_p_inc, nro_p + 3); nro_p = generar(" "); pila.push(nro_p); nro_p = generar("BI");}
                    ;

sentenciaLOOP       : LOOP FROM asignacionLOOP TO expresionLOOP BY expresionLOOP bloquesentencias 
                    | LOOP FROM asignacionLOOP TO expresionLOOP BY error 
                    | LOOP FROM asignacionLOOP TO error 
                    | LOOP error {SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());}
                    ;

sentenciaPRINT      : PRINT LEFTPARENTHESIS STRING RIGHTPARENTHESIS 
                    | PRINT error {SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());} 
                    ;

condicionIF         : condicion {nro_p = generar(" "); pila.push(nro_p); nro_p = generar("BF");}
                    ;

condicion           : expresion EQUALEQUAL expresion {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | expresion GREATEQUAL expresion {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | expresion LESSEQUAL expresion {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | expresion GREATTHAN expresion{String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | expresion LESSTHAN expresion {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    | expresion DISTINCT expresion {String toAdd = $2.tok.getValue();
                                                    pInv.add(toAdd);}
                    ;

ambitotemp          : {         int number = myScope.getScopesContained()+1;
                                String numb = Integer.toString(number);
                                Scope currentScope = new Scope(numb, myScope);
                                myScope.push(currentScope);
                                myScope = currentScope;
                                } ambito
                    ;

ambito              : LEFTBRACE declarativasambito ejecutables RIGHTBRACE {synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
                    | LEFTBRACE ejecutables RIGHTBRACE {synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
                    | LEFTBRACE RIGHTBRACE {synlog.addLog("Empty scope ends",lexicAnalyzer.getLine());}
                    ;

listaambitos        : ambitotemp {myScope = myScope.getFather();} listaambitos
                    | ambitotemp {myScope = myScope.getFather();}
                    ;

declarativasambito  : declarativasambito declarativaambito
                    | declarativaambito 
                    ;

declarativaambito   : declarativa 
                    | sentenciaMY SEMICOLON 
                    ;
sentenciaMY         : MY listavariablesMY 
                    ;

listavariablesMY    : listavariablesMY ID {  int pointer = $2.tok.getPointer(); 
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer); 
                            entry.addScope(myScope.getScopeSuffix());
                            Token t = entry.getToken();                                                        
                            boolean isInScope = symbolsTable.inScope(pointer);
                            if (!isInScope) {
                                SyntaxError.addLog("Variable not declared",lexicAnalyzer.getLine());
                                symbolsTable.removeEntry(pointer);
                            }
                                
                            else {
                                pointer = t.getPointer(); 
                                entry = symbolsTable.getEntry(pointer);
                                entry.setMyScope(myScope.getScopeSuffix());
                            }
                                
                        }
                    | ID {  int pointer = $1.tok.getPointer(); 
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            Token t = entry.getToken();
                            entry.addScope(myScope.getScopeSuffix());                                                        
                            boolean isInScope = symbolsTable.inScope(pointer);
                            if (!isInScope) {
                                SyntaxError.addLog("Variable not declared",lexicAnalyzer.getLine());
                                symbolsTable.removeEntry(pointer);
                            }
                            else {
                                pointer = t.getPointer();
                                entry = symbolsTable.getEntry(pointer);
                                entry.setMyScope(myScope.getScopeSuffix());
                            }
                        }
                    ;



%%
private LexicAnalyzer lexicAnalyzer;
private CAMerror SyntaxError;
private SymbolsTable symbolsTable;
private SyntacticLogger synlog;

private List pInv= new ArrayList();
private Stack<Integer> pila = new Stack<Integer>();
private int nro_p;

private Vector<Integer> IDlist = new Vector<Integer>();
private String currentType = new String();

private Scope myScope = new Scope("0", null);


public SyntacticLogger getSynLog() {return this.synlog;}
public CAMerror getSyntaxError(){return SyntaxError;}

private void completar(int nro_p_inc, int num) {
    pInv.set(nro_p_inc, num);
}

private int generar(String text) {
    pInv.add(text);
    return pInv.size()-1;
}

int yylex() throws IOException {
    Token t = lexicAnalyzer.getToken();
    Token _TOKENFIN = new Token(260,"$");
    if (!t.equals(_TOKENFIN)){
	   yylval = new ParserVal(t);
	   return t.getCode();
	}
    return 0;
}

void yyerror(String err) {
    
}

public Parser(LexicAnalyzer lA, CAMerror sErr, SyntacticLogger synlog)
{
    this.lexicAnalyzer = lA;
    this.SyntaxError = sErr;
    this.symbolsTable = lA.getST();
    this.synlog = synlog;
}