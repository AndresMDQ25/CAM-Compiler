//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "Parser.y"
package byacc;


import camcompiler.CAMerror;
import camcompiler.LexicAnalyzer;
import camcompiler.Token;
import java.io.IOException;
import camcompiler.SymbolsTable;
import camcompiler.SymbolsTableEntry;


//#line 29 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short ERROR=259;
public final static short FINAL=260;
public final static short IF=261;
public final static short THEN=262;
public final static short ELSE=263;
public final static short ENDIF=264;
public final static short PRINT=265;
public final static short INT=266;
public final static short BEGIN=267;
public final static short END=268;
public final static short UNSIGNED=269;
public final static short LONG=270;
public final static short MY=271;
public final static short LOOP=272;
public final static short FROM=273;
public final static short TO=274;
public final static short BY=275;
public final static short SEMICOLON=276;
public final static short STRING=277;
public final static short PLUSLE=278;
public final static short MINUN=279;
public final static short MULTIPLY=280;
public final static short DIVIDE=281;
public final static short EQUAL=282;
public final static short COMMA=283;
public final static short LEFTPARENTHESIS=284;
public final static short RIGHTPARENTHESIS=285;
public final static short GREATTHAN=286;
public final static short LESSTHAN=287;
public final static short LEFTBRACE=288;
public final static short RIGHTBRACE=289;
public final static short EQUALEQUAL=290;
public final static short GREATEQUAL=291;
public final static short LESSEQUAL=292;
public final static short DISTINCT=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    4,    4,    2,    2,    5,    5,    7,
    7,    7,    6,    6,    3,    3,    8,    8,    9,    9,
    9,   10,   10,   10,   10,   12,   12,   16,   16,   16,
   17,   17,   17,   18,   18,   18,   13,   13,   13,   14,
   14,   14,   14,   15,   15,   19,   19,   19,   19,   19,
   19,   11,   11,   11,   20,   20,   21,   21,   22,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    1,    2,    1,    3,    1,    3,
    1,    1,    1,    2,    3,    1,    2,    1,    1,    1,
    1,    2,    1,    2,    2,    3,    2,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    7,    9,    2,    8,
    7,    5,    2,    4,    2,    3,    3,    3,    3,    3,
    3,    4,    3,    2,    2,    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   13,    0,    0,    1,    0,    7,    0,   14,    0,
    0,    0,    0,    0,    0,    0,    2,    6,   16,   20,
   19,    0,   23,    0,    0,   12,   11,    0,   27,    0,
   39,    0,   45,    0,    0,   18,   43,    0,    0,   54,
    0,   57,    0,   56,    0,   22,   24,   25,    8,    0,
   34,   35,    0,    0,    0,   33,    0,    0,    0,   15,
   17,    0,    0,   53,    0,   55,   58,   10,   36,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   44,    0,   52,    0,    0,   31,   32,    0,    0,    0,
    0,    0,    0,    0,   42,    0,    0,    5,    0,    0,
    4,    0,   37,   41,    0,    0,   40,   38,
};
final static short yydgoto[] = {                          4,
    5,    6,   98,   99,    7,    8,   28,   35,   19,   20,
   21,   22,   23,   24,   25,   54,   55,   56,   58,   43,
   44,   45,
};
final static short yysindex[] = {                      -202,
    0,    0, -243,    0,    0,  -40,    0, -242,    0,    0,
 -240, -252, -251, -248, -245, -231,    0,    0,    0,    0,
    0, -258,    0, -213, -196,    0,    0, -221,    0, -206,
    0, -206,    0, -246,  -74,    0,    0, -171, -242,    0,
 -207,    0,  -57,    0, -184,    0,    0,    0,    0, -159,
    0,    0, -151, -210, -175,    0,  -41, -170, -165,    0,
    0, -163, -156,    0, -161,    0,    0,    0,    0, -206,
 -206, -206, -206, -206, -206, -206, -206, -206, -206, -138,
    0, -250,    0, -175, -175,    0,    0, -210, -210, -210,
 -210, -210, -210,  -40,    0, -219,  -40,    0, -174, -236,
    0, -202,    0,    0,  -77, -135,    0,    0,
};
final static short yyrindex[] = {                         0,
   19,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -197, -191,    0,    0,    0,    0,    0,
    0,    0, -146,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -153, -115,    0,    0, -154, -149, -142,
 -141, -140, -137,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   47,  -33,   -6,   42,   -4,    0,  114,    0,    0,    9,
    0,  117,    0,    0,    0,  -29,   39,   45,    0,    0,
  113,    0,
};
final static int YYTABLESIZE=307;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
   21,   18,   57,   31,   33,   95,   51,   52,   11,   41,
   37,   42,   12,   26,   27,   29,   13,   46,    3,  104,
   51,   52,   36,   15,   10,   11,    9,   38,   53,   12,
   59,   32,   34,   13,    2,   14,   65,    3,   42,   39,
   15,   30,   53,   61,   88,   89,   90,   91,   92,   93,
   51,   52,   96,    1,   49,  100,   16,   40,   70,   71,
   97,   50,   47,    2,   30,   30,    3,   70,   71,   30,
  105,   97,   53,   30,   30,   30,   26,   30,   26,   48,
   30,   64,   30,   30,   30,   11,   30,   30,  102,  103,
  101,   67,   18,   30,   30,   30,   30,   68,   30,   30,
   30,   30,   28,   28,   72,   73,   69,   28,   84,   85,
   82,   28,   28,   28,   80,   28,   86,   87,   28,   81,
   28,   28,   28,   94,   28,   28,   50,   83,  108,   59,
   49,   28,   28,   28,   28,   50,   28,   28,   28,   28,
   29,   29,   46,   47,   48,   29,  107,   51,  106,   29,
   29,   29,   63,   29,   62,   66,   29,    0,   29,   29,
   29,    0,   29,   29,    0,    0,    0,    0,    0,   29,
   29,   29,   29,    0,   29,   29,   29,   29,   10,   11,
    0,    0,   11,   12,    0,    0,   12,   13,    2,   14,
   13,    3,    0,   60,   15,    0,    0,   15,   10,   11,
   70,   71,    0,   12,    0,    0,    0,   13,    2,   14,
   16,    3,    0,   39,   15,   10,   11,    0,    0,    0,
   12,    0,    0,    0,   13,    2,   14,    0,    3,    0,
   16,   15,    0,    0,    0,    0,   70,   71,    0,    0,
    0,    0,    0,    0,   74,   75,    0,   16,   76,   77,
   78,   79,    0,    0,    0,    0,    9,    9,    0,    0,
    0,    9,    0,   21,   21,    9,    9,    9,    0,    9,
    0,    9,    9,    0,    9,    9,   21,    0,    0,    9,
    0,    0,    3,    9,    9,    9,    0,    9,    9,   21,
    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
    0,    6,   32,  256,  256,  256,  257,  258,  257,   16,
  256,   16,  261,  256,  257,  256,  265,  276,    0,  256,
  257,  258,   14,  272,  256,  257,  270,  273,  279,  261,
  277,  284,  284,  265,  266,  267,   43,  269,   43,  271,
  272,  282,  279,   35,   74,   75,   76,   77,   78,   79,
  257,  258,   82,  256,  276,  275,  288,  289,  278,  279,
   94,  283,  276,  266,  256,  257,  269,  278,  279,  261,
  100,  105,  279,  265,  266,  267,  274,  269,  276,  276,
  272,  289,  274,  275,  276,  257,  278,  279,  263,  264,
   97,  276,   97,  285,  286,  287,  288,  257,  290,  291,
  292,  293,  256,  257,  280,  281,  258,  261,   70,   71,
  274,  265,  266,  267,  285,  269,   72,   73,  272,  285,
  274,  275,  276,  262,  278,  279,  283,  289,  264,  276,
  285,  285,  286,  287,  288,  285,  290,  291,  292,  293,
  256,  257,  285,  285,  285,  261,  105,  285,  102,  265,
  266,  267,   39,  269,   38,   43,  272,   -1,  274,  275,
  276,   -1,  278,  279,   -1,   -1,   -1,   -1,   -1,  285,
  286,  287,  288,   -1,  290,  291,  292,  293,  256,  257,
   -1,   -1,  257,  261,   -1,   -1,  261,  265,  266,  267,
  265,  269,   -1,  268,  272,   -1,   -1,  272,  256,  257,
  278,  279,   -1,  261,   -1,   -1,   -1,  265,  266,  267,
  288,  269,   -1,  271,  272,  256,  257,   -1,   -1,   -1,
  261,   -1,   -1,   -1,  265,  266,  267,   -1,  269,   -1,
  288,  272,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
   -1,   -1,   -1,   -1,  286,  287,   -1,  288,  290,  291,
  292,  293,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,
   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,  269,
   -1,  271,  272,   -1,  256,  257,  276,   -1,   -1,  261,
   -1,   -1,  264,  265,  266,  267,   -1,  269,  288,  289,
  272,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  288,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"ID","CTE","ERROR","FINAL","IF","THEN","ELSE","ENDIF","PRINT",
"INT","BEGIN","END","UNSIGNED","LONG","MY","LOOP","FROM","TO","BY","SEMICOLON",
"STRING","PLUSLE","MINUN","MULTIPLY","DIVIDE","EQUAL","COMMA","LEFTPARENTHESIS",
"RIGHTPARENTHESIS","GREATTHAN","LESSTHAN","LEFTBRACE","RIGHTBRACE","EQUALEQUAL",
"GREATEQUAL","LESSEQUAL","DISTINCT",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : declarativas ejecutables",
"bloque : error",
"bloquesentencias : declarativas ejecutables",
"bloquesentencias : ejecutables",
"declarativas : declarativas declarativa",
"declarativas : declarativa",
"declarativa : tipo listavariables SEMICOLON",
"declarativa : error",
"listavariables : listavariables COMMA ID",
"listavariables : ID",
"listavariables : error",
"tipo : INT",
"tipo : UNSIGNED LONG",
"ejecutables : BEGIN listaejecutables END",
"ejecutables : ejecutablesimple",
"listaejecutables : listaejecutables ejecutable",
"listaejecutables : ejecutable",
"ejecutablesimple : ambito",
"ejecutablesimple : ejecutable",
"ejecutablesimple : error",
"ejecutable : asignacion SEMICOLON",
"ejecutable : sentenciaIF",
"ejecutable : sentenciaLOOP SEMICOLON",
"ejecutable : sentenciaPRINT SEMICOLON",
"asignacion : ID EQUAL expresion",
"asignacion : ID error",
"expresion : expresion PLUSLE termino",
"expresion : expresion MINUN termino",
"expresion : termino",
"termino : termino MULTIPLY factor",
"termino : termino DIVIDE factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"factor : MINUN CTE",
"sentenciaIF : IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ENDIF",
"sentenciaIF : IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ELSE bloque ENDIF",
"sentenciaIF : IF error",
"sentenciaLOOP : LOOP FROM asignacion TO expresion BY expresion bloquesentencias",
"sentenciaLOOP : LOOP FROM asignacion TO expresion BY error",
"sentenciaLOOP : LOOP FROM asignacion TO error",
"sentenciaLOOP : LOOP error",
"sentenciaPRINT : PRINT LEFTPARENTHESIS STRING RIGHTPARENTHESIS",
"sentenciaPRINT : PRINT error",
"condicion : expresion EQUALEQUAL expresion",
"condicion : expresion GREATEQUAL expresion",
"condicion : expresion LESSEQUAL expresion",
"condicion : expresion GREATTHAN expresion",
"condicion : expresion LESSTHAN expresion",
"condicion : expresion DISTINCT expresion",
"ambito : LEFTBRACE declarativasambito ejecutables RIGHTBRACE",
"ambito : LEFTBRACE ejecutables RIGHTBRACE",
"ambito : LEFTBRACE RIGHTBRACE",
"declarativasambito : declarativasambito declarativaambito",
"declarativasambito : declarativaambito",
"declarativaambito : declarativa",
"declarativaambito : sentenciaMY SEMICOLON",
"sentenciaMY : MY listavariables",
};

//#line 133 "Parser.y"
private LexicAnalyzer lexicAnalyzer;
private CAMerror SyntaxError;
private SymbolsTable symbolsTable;

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

public Parser(LexicAnalyzer lA,CAMerror sErr)
{
    System.out.println("ENTRE AL PARSER");
    this.lexicAnalyzer = lA;
    this.SyntaxError = sErr;
    this.symbolsTable = lA.getST();
}
//#line 392 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse() throws IOException
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 22 "Parser.y"
{System.out.println("programa");}
break;
case 2:
//#line 24 "Parser.y"
{System.out.println("bloque");}
break;
case 3:
//#line 25 "Parser.y"
{SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine());}
break;
case 4:
//#line 28 "Parser.y"
{System.out.println("bloque sentencias");}
break;
case 5:
//#line 29 "Parser.y"
{System.out.println("bloque sentencias solo ejecutables");}
break;
case 6:
//#line 32 "Parser.y"
{System.out.println("declarativas");}
break;
case 7:
//#line 33 "Parser.y"
{System.out.println("declarativas2");}
break;
case 8:
//#line 36 "Parser.y"
{System.out.println("declarativa");}
break;
case 9:
//#line 37 "Parser.y"
{SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine());}
break;
case 10:
//#line 40 "Parser.y"
{System.out.println("listavariables");}
break;
case 11:
//#line 41 "Parser.y"
{System.out.println("listavariables2");}
break;
case 12:
//#line 42 "Parser.y"
{SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine());}
break;
case 13:
//#line 45 "Parser.y"
{System.out.println("tipo");}
break;
case 14:
//#line 46 "Parser.y"
{System.out.println("tipo2");}
break;
case 15:
//#line 49 "Parser.y"
{System.out.println("ejecutables (BEGIN y END)");}
break;
case 16:
//#line 50 "Parser.y"
{System.out.println("ejecutables (simple)");}
break;
case 17:
//#line 53 "Parser.y"
{System.out.println("listaejecutables");}
break;
case 18:
//#line 54 "Parser.y"
{System.out.println("listaejecutables2");}
break;
case 19:
//#line 57 "Parser.y"
{System.out.println("ejecutablesimple");}
break;
case 20:
//#line 58 "Parser.y"
{System.out.println("ejecutablesimple2");}
break;
case 21:
//#line 59 "Parser.y"
{SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine());}
break;
case 22:
//#line 62 "Parser.y"
{System.out.println("ejecutable asig");}
break;
case 23:
//#line 63 "Parser.y"
{System.out.println("ejecutable IF");}
break;
case 24:
//#line 64 "Parser.y"
{System.out.println("ejecutable LOOP");}
break;
case 25:
//#line 65 "Parser.y"
{System.out.println("ejecutable PRINT");}
break;
case 26:
//#line 68 "Parser.y"
{System.out.println("asignacion");}
break;
case 27:
//#line 69 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 28:
//#line 72 "Parser.y"
{System.out.println("expresion+");}
break;
case 29:
//#line 73 "Parser.y"
{System.out.println("expresion-");}
break;
case 31:
//#line 76 "Parser.y"
{System.out.println("termino*");}
break;
case 32:
//#line 77 "Parser.y"
{System.out.println("termino/");}
break;
case 33:
//#line 78 "Parser.y"
{System.out.println("termino factor");}
break;
case 34:
//#line 80 "Parser.y"
{System.out.println("factor");}
break;
case 35:
//#line 81 "Parser.y"
{System.out.println("factor2");}
break;
case 36:
//#line 82 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                System.out.println("factor negativo"+temp);
                                }
break;
case 37:
//#line 91 "Parser.y"
{System.out.println("sentenciaIF");}
break;
case 38:
//#line 92 "Parser.y"
{System.out.println("sentenciaIF ELSE");}
break;
case 39:
//#line 93 "Parser.y"
{SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
break;
case 40:
//#line 97 "Parser.y"
{System.out.println("sentenciaLOOP");}
break;
case 41:
//#line 98 "Parser.y"
{System.out.println("ERROR en expresion2");}
break;
case 42:
//#line 99 "Parser.y"
{System.out.println("ERROR en expresion1");}
break;
case 43:
//#line 100 "Parser.y"
{SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());System.out.println("ERROR en LUP");}
break;
case 44:
//#line 103 "Parser.y"
{System.out.println("sentenciaPRINT");}
break;
case 45:
//#line 104 "Parser.y"
{SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());}
break;
case 46:
//#line 107 "Parser.y"
{System.out.println("condicion==");}
break;
case 47:
//#line 108 "Parser.y"
{System.out.println("condicion>=");}
break;
case 48:
//#line 109 "Parser.y"
{System.out.println("condicion<=");}
break;
case 49:
//#line 110 "Parser.y"
{System.out.println("condicion>");}
break;
case 50:
//#line 111 "Parser.y"
{System.out.println("condicion<");}
break;
case 51:
//#line 112 "Parser.y"
{System.out.println("condicion<>");}
break;
case 52:
//#line 115 "Parser.y"
{System.out.println("ambito");}
break;
case 53:
//#line 116 "Parser.y"
{System.out.println("ambito solo ejecutables");}
break;
case 54:
//#line 117 "Parser.y"
{System.out.println("ambito vacio");}
break;
case 55:
//#line 120 "Parser.y"
{System.out.println("declarativaSambito");}
break;
case 56:
//#line 121 "Parser.y"
{System.out.println("declarativaSambito2");}
break;
case 57:
//#line 124 "Parser.y"
{System.out.println("declarativaambito");}
break;
case 58:
//#line 125 "Parser.y"
{System.out.println("declarativaambito MY");}
break;
case 59:
//#line 127 "Parser.y"
{System.out.println("sentenciaMY");}
break;
//#line 779 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run() throws IOException
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
