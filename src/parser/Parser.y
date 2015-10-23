//declaraciones


%token ID CTE ERROR FINAL IF THEN ELSE ENDIF PRINT INT BEGIN END UNSIGNED LONG MY LOOP FROM TO BY OPERATOR STRING 

%left '+' '-'
%left '*' '/'

%%
programa            : bloque
                    ;
bloque              : declarativas ejecutables
                    | error {SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine())}
                    ;
declarativas        : declarativas declarativa
                    | declarativa
                    ;

declarativa         : tipo listavariables ';'
                    | error {SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine())}
                    ;

listavariables      : listavariables ',' ID
                    | ID
                    | error {SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine())}
                    ;

tipo                : INT
                    | UNSIGNED LONG
                    ;

ejecutables         : BEGIN listaejecutables END
                    | ejecutablesimple
                    ;

listaejecutables    : listaejecutables ejecutable
                    | ejecutable
                    ;

ejecutablesimple    : ambito
                    | ejecutable
		    | error {SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine())}
                    ;

ejecutable          : asignacion ';'
                    | sentenciaIF ';'
                    | sentenciaLOOP ';'
                    | sentenciaPRINT ';'
                    ;

asignacion          : ID '=' expresion
                    | ID error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine())}
                    ;

expresion           : expresion '+' termino
                    | expresion '-' termino
                    | termino
                    ;
termino             : termino '*' factor
                    | termino '/' factor
                    | factor
                    ;
factor              : ID
                    | CTE
                    ;

sentenciaIF         : IF '(' condicion ')' THEN bloque ENDIF
                    | IF '(' condicion ')' THEN bloque ELSE bloque ENDIF
                    | IF error {SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine())}
                    ;


sentenciaLOOP       : LOOP FROM asignacion TO expresion BY expresion bloque
                    | LOOP error {SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine())}
                    ;

sentenciaPRINT      : PRINT '(' STRING ')'
                    | PRINT error {SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine())} 
                    ;

condicion           : expresion '=' '=' expresion {$$ = $1 == $3}
                    | expresion '>' '=' expresion {}
                    | expresion '<' '=' expresion {}
                    | expresion '>' expresion {}
                    | expresion '<' expresion {}
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