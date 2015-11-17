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


%}

%token ID CTEINT ERROR FINAL IF THEN ELSE ENDIF PRINT INT BEGIN END UNSIGNED LONG MY LOOP FROM TO BY SEMICOLON STRING PLUSLE MINUN MULTIPLY DIVIDE EQUAL COMMA LEFTPARENTHESIS RIGHTPARENTHESIS GREATTHAN LESSTHAN LEFTBRACE RIGHTBRACE EQUALEQUAL GREATEQUAL LESSEQUAL DISTINCT CTEUL

%left PLUSLE MINUN
%left MULTIPLY DIVIDE

%%
programa            : bloque
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

listavariables      : listavariables COMMA ID {int pointer = yylval.tok.getPointer(); IDlist.addElement(pointer);}
                    | ID {int pointer = yylval.tok.getPointer(); IDlist.addElement(pointer);}
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

asignacion          : identificador EQUAL expresion 
                    | identificador error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
                    ;

identificador       : ID {  int pointer = yylval.tok.getPointer();
                            System.out.println(pointer);
                            System.out.println(yylval.tok.getCode());
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
                    ;

asignacionLOOP      : ID {  int pointer = yylval.tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());} EQUAL expresionLOOP 
                    | ID error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
                    ;

expresion           : expresion PLUSLE termino 
                    | expresion MINUN termino
                    | termino
                    ;

expresionLOOP       : expresionLOOP PLUSLE terminoLOOP 
                    | expresionLOOP MINUN terminoLOOP
                    | terminoLOOP
                    ;

termino             : termino MULTIPLY factor 
                    | termino DIVIDE factor 
                    | factor 
                    ;

terminoLOOP         : terminoLOOP MULTIPLY factorLOOP 
                    | terminoLOOP DIVIDE factorLOOP 
                    | factorLOOP 
                    ;

factorLOOP          : ID {  int pointer = yylval.tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
                    | CTEINT {
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
                    | MINUN CTEINT {
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
                    ; 

factor              : ID {  int pointer = yylval.tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
                    | constant
                    | MINUN CTEINT {
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
                    ;

constant            : CTEINT {
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
                    | CTEUL {
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "ULONG";
                                entry.setSType(temp);
                            }
                    ;

sentenciaIF         : IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ENDIF 
                    | IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ELSE bloquesentencias ENDIF
                    | IF error {SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
                    ;


sentenciaLOOP       : LOOP FROM asignacionLOOP TO expresionLOOP BY expresionLOOP bloquesentencias 
                    | LOOP FROM asignacionLOOP TO expresionLOOP BY error 
                    | LOOP FROM asignacionLOOP TO error 
                    | LOOP error {SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());}
                    ;

sentenciaPRINT      : PRINT LEFTPARENTHESIS STRING RIGHTPARENTHESIS 
                    | PRINT error {SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());} 
                    ;

condicion           : expresion EQUALEQUAL expresion 
                    | expresion GREATEQUAL expresion 
                    | expresion LESSEQUAL expresion 
                    | expresion GREATTHAN expresion
                    | expresion LESSTHAN expresion 
                    | expresion DISTINCT expresion 
                    ;

ambitotemp          : {         int number = myScope.getScopesContained()+1;
                                String numb = Integer.toString(number);
                                Scope currentScope = new Scope(numb, myScope);
                                myScope.push(currentScope);
                                myScope = currentScope;
                                System.out.println(myScope.getScopeSuffix());
                                } ambito
                    ;

ambito              : LEFTBRACE declarativasambito ejecutables RIGHTBRACE {synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
                    | LEFTBRACE ejecutables RIGHTBRACE {synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
                    | LEFTBRACE RIGHTBRACE {synlog.addLog("Empty scope ends",lexicAnalyzer.getLine());}
                    ;

listaambitos        : ambitotemp {myScope = myScope.getFather();System.out.println(myScope.getScopeSuffix());} listaambitos
                    | ambitotemp {myScope = myScope.getFather();System.out.println(myScope.getScopeSuffix());}
                    ;

declarativasambito  : declarativasambito declarativaambito
                    | declarativaambito 
                    ;

declarativaambito   : declarativa 
                    | sentenciaMY SEMICOLON 
                    ;
sentenciaMY         : MY listavariables 
                    ;



%%
private LexicAnalyzer lexicAnalyzer;
private CAMerror SyntaxError;
private SymbolsTable symbolsTable;
private SyntacticLogger synlog;

private Vector<Integer> IDlist = new Vector<Integer>();
private String currentType = new String();

private Scope myScope = new Scope("0", null);


public SyntacticLogger getSynLog() {return this.synlog;}
public CAMerror getSyntaxError(){return SyntaxError;}

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