//declaraciones


%token ID CTE ERROR FINAL IF THEN ELSE ENDIF PRINT INT BEGIN END UNSIGNED LONG MY LOOP FROM TO BY OPERATOR STRING 

%left '+' '-'
%left '*' '/'

%%
programa            : bloque
                    ;
bloque              : declarativas ejecutables
                    //| error {SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine())}
                    ;
declarativas        : declarativas declarativa
                    | declarativa
                    //| error {SyntaxError.addLog("Invalid declaratives sentences",lexicAnalyzer.getLine())}
                    ;

declarativa         : tipo listavariables ';'
                    //| error {SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine())}
                    ;

listavariables      : listavariables ',' ID
                    | ID
                    //| error {SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine())}
                    ;

tipo                : INT
                    | UNSIGNED LONG
                    //| error {SyntaxError.addLog("Invalid type",lexicAnalyzer.getLine())}
                    ;

ejecutables         : BEGIN listaejecutables END
                    | ejecutablesimple
                    //| error {SyntaxError.addLog("Invalid executables",lexicAnalyzer.getLine())}
                    ;

listaejecutables    : listaejecutables ejecutable
                    | ejecutable
                    ;

ejecutablesimple    : ambito
                    | ejecutable
                    ;

ejecutable          : asignacion ';'
                    | sentenciaIF ';'
                    | sentenciaLOOP ';'
                    | sentenciaPRINT ';'
                    //| error {SyntaxError.addLog("Invalid executable",lexicAnalyzer.getLine())}
                    ;

asignacion          : ID '=' expresion
                    //| error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine())}
                    ;

expresion           : expresion '+' termino
                    | expresion '-' termino
                    | termino
                    //| error {SyntaxError.addLog("Invalid expression",lexicAnalyzer.getLine())}
                    ;
termino             : termino '*' factor
                    | termino '/' factor
                    | factor
                    //| error {SyntaxError.addLog("Invalid term",lexicAnalyzer.getLine())}
                    ;
factor              : ID
                    | CTE
                    ;

sentenciaIF         : IF '(' condicion ')' THEN bloque ENDIF
                    | IF '(' condicion ')' THEN bloque ELSE bloque ENDIF
                    //| error {SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine())}
                    ;


sentenciaLOOP       : LOOP FROM asignacion TO expresion BY expresion bloque
                    //| error {SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine())}
                    ;

sentenciaPRINT      : PRINT '(' STRING ')'
                    //| error {SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine())} 
                    ;

condicion           : expresion '=' '=' expresion {$$ = $1 == $3}
                    | expresion '>' '=' expresion {}
                    | expresion '<' '=' expresion {}
                    | expresion '>' expresion {}
                    | expresion '<' expresion {}
                    //| error {SyntaxError.addLog("Invalid condition",lexicAnalyzer.getLine())}
                    ;

ambito              : '{' declarativasambito ejecutables '}'
                    | '{' ejecutables '}'
                    | '{' '}'
                    ;

declarativasambito  : declarativasambito declarativaambito
                    | declarativaambito
                    ;

declarativaambito   : declarativa
                    | sentenciaMY ';'
                    ;
sentenciaMY         : MY listavariables
                    ;



%%

{
    private SymbolsTable symtab;  
    private Error   SyntaxError;
    private Error   LexicError; 
    private LexicAnalyzer  lexicAnalyzer;
}
