//declaraciones


%token ID CTE ERROR FINAL IF THEN ELSE ENDIF PRINT INT BEGIN END UNSIGNED LONG MY LOOP FROM TO BY SEMICOLON STRING PLUSLE MINUN MULTIPLY DIVIDE EQUAL COMMA LEFTPARENTHESIS RIGHTPARENTHESIS GREATTHAN LESSTHAN LEFTBRACE RIGHTBRACE   

%left PLUSLE MINUN
%left MULTIPLY DIVIDE

%%
programa            : bloque {System.out.println("programa");}
                    ;
bloque              : declarativas ejecutables {System.out.println("bloque");}
                    | error {SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine())}
                    ;
declarativas        : declarativas declarativa {System.out.println("declarativas");}
                    | declarativa {System.out.println("declarativas2");}
                    ;

declarativa         : tipo listavariables SEMICOLON {System.out.println("declarativa");}
                    | error {SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine())}
                    ;

listavariables      : listavariables COMMA ID {System.out.println("listavariables");}
                    | ID {System.out.println("listavariables2");}
                    | error {SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine())}
                    ;

tipo                : INT {System.out.println("tipo");}
                    | UNSIGNED LONG {System.out.println("tipo2");}
                    ;

ejecutables         : BEGIN listaejecutables END {System.out.println("ejecutables (BEGIN y END)");}
                    | ejecutablesimple {System.out.println("ejecutables (simple)");}
                    ;

listaejecutables    : listaejecutables ejecutable {System.out.println("listaejecutables");}
                    | ejecutable {System.out.println("listaejecutables2");}
                    ;

ejecutablesimple    : ambito {System.out.println("ejecutablesimple");}
                    | ejecutable {System.out.println("ejecutablesimple2");}
		    		| error {SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine())}
                    ;

ejecutable          : asignacion SEMICOLON {System.out.println("ejecutable asig");}
                    | sentenciaIF SEMICOLON {System.out.println("ejecutable IF");}
                    | sentenciaLOOP SEMICOLON {System.out.println("ejecutable LOOP");}
                    | sentenciaPRINT SEMICOLON {System.out.println("ejecutable PRINT");}
                    ;

asignacion          : ID EQUAL expresion {System.out.println("asignacion");}
                    | ID error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine())}
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
                    | CTE {System.out.println("factor2");}
                    ;

sentenciaIF         : IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloque ENDIF {System.out.println("sentenciaIF");}
                    | IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloque ELSE bloque ENDIF {System.out.println("sentenciaIF ELSE");}
                    | IF error {SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine())}
                    ;


sentenciaLOOP       : LOOP FROM asignacion TO expresion BY expresion bloque {System.out.println("sentenciaLOOP");}
                    | LOOP error {SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine())}
                    ;

sentenciaPRINT      : PRINT LEFTPARENTHESIS STRING RIGHTPARENTHESIS {System.out.println("sentenciaPRINT");}
                    | PRINT error {SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine())} 
                    ;

condicion           : expresion EQUAL EQUAL expresion {} {System.out.println("condicion==");}
                    | expresion GREATTHAN EQUAL expresion {} {System.out.println("condicion>=");}
                    | expresion LESSTHAN EQUAL expresion {} {System.out.println("condicion<=");}
                    | expresion GREATTHAN expresion {} {System.out.println("condicion>");}
                    | expresion LESSTHAN expresion {} {System.out.println("condicion<");}
                    ;

ambito              : LEFTBRACE declarativasambito ejecutables RIGHTBRACE {System.out.println("ambito");}
                    | LEFTBRACE ejecutables RIGHTBRACE {System.out.println("ambito solo ejecutables");}
                    | LEFTBRACE RIGHTBRACE {System.out.println("ambito vacio");}
                    ;

declarativasambito  : declarativasambito declarativaambito {System.out.println("declarativaSambito");}
                    | declarativaambito {System.out.println("declarativaSambito2");}
                    ;

declarativaambito   : declarativa {System.out.println("declarativaambito");}
                    | sentenciaMY SEMICOLON {System.out.println("declarativaambito MY");}
                    ;
sentenciaMY         : MY listavariables {System.out.println("sentenciaMY");}
                    ;



%%