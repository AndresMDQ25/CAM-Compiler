//declaraciones


%token ID CTE ERROR FINAL IF THEN ELSE ENDIF PRINT INT BEGIN END UNSIGNED LONG MY LOOP FROM TO BY OPERATOR STRING 

%left '+' '-'
%left '*' '/'

%%
programa            : bloque {System.out.println("programa");}
                    ;
bloque              : declarativas ejecutables {System.out.println("bloque");}
                    | error {SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine())}
                    ;
declarativas        : declarativas declarativa {System.out.println("declarativas");}
                    | declarativa {System.out.println("declarativas2");}
                    ;

declarativa         : tipo listavariables ';' {System.out.println("declarativa");}
                    | error {SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine())}
                    ;

listavariables      : listavariables ',' ID {System.out.println("listavariables");}
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

ejecutable          : asignacion ';' {System.out.println("ejecutable asig");}
                    | sentenciaIF ';' {System.out.println("ejecutable IF");}
                    | sentenciaLOOP ';' {System.out.println("ejecutable LOOP");}
                    | sentenciaPRINT ';' {System.out.println("ejecutable PRINT");}
                    ;

asignacion          : ID '=' expresion {System.out.println("asignacion");}
                    | ID error {SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine())}
                    ;

expresion           : expresion '+' termino {System.out.println("expresion+");}
                    | expresion '-' termino {System.out.println("expresion-");}
                    | termino
                    ;
termino             : termino '*' factor {System.out.println("termino*");}
                    | termino '/' factor {System.out.println("termino/");}
                    | factor {System.out.println("termino factor");}
                    ;
factor              : ID {System.out.println("factor");}
                    | CTE {System.out.println("factor2");}
                    ;

sentenciaIF         : IF '(' condicion ')' THEN bloque ENDIF {System.out.println("sentenciaIF");}
                    | IF '(' condicion ')' THEN bloque ELSE bloque ENDIF {System.out.println("sentenciaIF ELSE");}
                    | IF error {SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine())}
                    ;


sentenciaLOOP       : LOOP FROM asignacion TO expresion BY expresion bloque {System.out.println("sentenciaLOOP");}
                    | LOOP error {SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine())}
                    ;

sentenciaPRINT      : PRINT '(' STRING ')' {System.out.println("sentenciaPRINT");}
                    | PRINT error {SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine())} 
                    ;

condicion           : expresion '=' '=' expresion {} {System.out.println("condicion==");}
                    | expresion '>' '=' expresion {} {System.out.println("condicion>=");}
                    | expresion '<' '=' expresion {} {System.out.println("condicion<=");}
                    | expresion '>' expresion {} {System.out.println("condicion>");}
                    | expresion '<' expresion {} {System.out.println("condicion<");}
                    ;

ambito              : '{' declarativasambito ejecutables '}' {System.out.println("ambito");}
                    | '{' ejecutables '}' {System.out.println("ambito solo ejecutables");}
                    | '{' '}' {System.out.println("ambito vacio");}
                    ;

declarativasambito  : declarativasambito declarativaambito {System.out.println("declarativaSambito");}
                    | declarativaambito {System.out.println("declarativaSambito2");}
                    ;

declarativaambito   : declarativa {System.out.println("declarativaambito");}
                    | sentenciaMY ';' {System.out.println("declarativaambito MY");}
                    ;
sentenciaMY         : MY listavariables {System.out.println("sentenciaMY");}
                    ;



%%