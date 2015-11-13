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

bloquesentencias    : declarativas ejecutables {System.out.println("bloque sentencias");}
                    | ejecutables {System.out.println("bloque sentencias solo ejecutables");}
                    ;

declarativas        : declarativas declarativa {System.out.println("declarativas");}
                    | declarativa {System.out.println("declarativas2");}
                    ;

declarativa         : tipo listavariables SEMICOLON {synlog.addLog("Declaration",lexicAnalyzer.getLine());}
                    | error {SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine());}
                    ;

listavariables      : listavariables COMMA ID {System.out.println("listavariables");}
                    | ID {System.out.println("listavariables2");}
                    | error {SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine());}
                    ;

tipo                : INT {System.out.println("tipo");}
                    | UNSIGNED LONG {System.out.println("tipo2");}
                    ;

ejecutables         : BEGIN listaejecutables END
                    | ejecutablesimple {System.out.println("ejecutables (simple)");}
                    ;

listaejecutables    : listaejecutables ejecutable {System.out.println("listaejecutables");}
                    | ejecutable {System.out.println("listaejecutables2");}
                    ;

ejecutablesimple    : ambito {System.out.println("ejecutablesimple");}
                    | ejecutable {System.out.println("ejecutablesimple2");}
		    | error {SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine());}
                    ;

ejecutable          : asignacion SEMICOLON {synlog.addLog("Asignation",lexicAnalyzer.getLine());}
                    | sentenciaIF {synlog.addLog("IF",lexicAnalyzer.getLine());}
                    | sentenciaLOOP SEMICOLON {synlog.addLog("LOOP",lexicAnalyzer.getLine());}
                    | sentenciaPRINT SEMICOLON {synlog.addLog("PRINT",lexicAnalyzer.getLine());}
                    ;

asignacion          : ID EQUAL expresion {System.out.println("asignacion");}
                    | ID error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
                    ;

expresion           : expresion PLUSLE termino {System.out.println("expresion+");}
                    | expresion MINUN termino {System.out.println("expresion-");}
                    | termino
                    ;
termino             : termino MULTIPLY factor {System.out.println("termino*");}
                    | termino DIVIDE factor {System.out.println("termino/");}
                    | factor {System.out.println("termino factor");}
                    ;
factor              : ID {System.out.println("factor");}
                    | constant {System.out.println("factor2");}
                    | MINUN CTEINT {
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                System.out.println("factor negativo"+temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
                    ;

constant            : CTEINT {
                                System.out.println("constante entera");
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
                    | CTEUL {
                                System.out.println("constante larga");
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "ULONG";
                                entry.setSType(temp);
                            }
                    ;

sentenciaIF         : IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ENDIF {System.out.println("sentenciaIF");}
                    | IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ELSE bloquesentencias ENDIF {System.out.println("sentenciaIF ELSE");}
                    | IF error {SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
                    ;


sentenciaLOOP       : LOOP FROM asignacion TO expresion BY expresion bloquesentencias {System.out.println("sentenciaLOOP");}
                    | LOOP FROM asignacion TO expresion BY error {System.out.println("ERROR en expresion2");}
                    | LOOP FROM asignacion TO error {System.out.println("ERROR en expresion1");}
                    | LOOP error {SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());System.out.println("ERROR en LUP");}
                    ;

sentenciaPRINT      : PRINT LEFTPARENTHESIS STRING RIGHTPARENTHESIS {System.out.println("sentenciaPRINT");}
                    | PRINT error {SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());} 
                    ;

condicion           : expresion EQUALEQUAL expresion {System.out.println("condicion==");}
                    | expresion GREATEQUAL expresion {System.out.println("condicion>=");}
                    | expresion LESSEQUAL expresion {System.out.println("condicion<=");}
                    | expresion GREATTHAN expresion {System.out.println("condicion>");}
                    | expresion LESSTHAN expresion {System.out.println("condicion<");}
                    | expresion DISTINCT expresion {System.out.println("condicion<>");}
                    ;

ambito              : LEFTBRACE declarativasambito ejecutables RIGHTBRACE {synlog.addLog("Scope",lexicAnalyzer.getLine());}
                    | LEFTBRACE ejecutables RIGHTBRACE {synlog.addLog("Scope",lexicAnalyzer.getLine());}
                    | LEFTBRACE RIGHTBRACE {synlog.addLog("Empty scope",lexicAnalyzer.getLine());}
                    ;

declarativasambito  : declarativasambito declarativaambito {System.out.println("declarativaSambito");}
                    | declarativaambito {System.out.println("declarativaSambito2");}
                    ;

declarativaambito   : declarativa {System.out.println("declarativaambito");}
                    | sentenciaMY SEMICOLON {System.out.println("declarativaambito MY");}
                    ;
sentenciaMY         : MY listavariables {synlog.addLog("MY",lexicAnalyzer.getLine());}
                    ;



%%
private LexicAnalyzer lexicAnalyzer;
private CAMerror SyntaxError;
private SymbolsTable symbolsTable;
private SyntacticLogger synlog;


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
    System.out.println("ENTRE AL PARSER");
    this.lexicAnalyzer = lA;
    this.SyntaxError = sErr;
    this.symbolsTable = lA.getST();
    this.synlog = synlog;
}