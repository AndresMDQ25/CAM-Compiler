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
import camcompiler.SyntacticLogger;

import java.util.Vector;


//#line 32 "Parser.java"




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
public final static short CTEINT=258;
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
public final static short CTEUL=294;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    4,    4,    2,    2,    5,    5,    7,
    7,    7,    6,    6,    3,    3,    8,    8,    9,    9,
    9,   10,   10,   10,   10,   12,   12,   17,   17,   16,
   16,   16,   18,   18,   18,   19,   19,   19,   20,   20,
   20,   22,   22,   22,   21,   21,   21,   23,   23,   13,
   13,   13,   14,   14,   14,   14,   15,   15,   24,   24,
   24,   24,   24,   24,   25,   25,   25,   11,   11,   26,
   26,   27,   27,   28,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    1,    2,    1,    3,    1,    3,
    1,    1,    1,    2,    3,    1,    2,    1,    1,    1,
    1,    2,    1,    2,    2,    3,    2,    3,    2,    3,
    3,    1,    3,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    2,    1,    1,    2,    1,    1,    7,
    9,    2,    8,    7,    5,    2,    4,    2,    3,    3,
    3,    3,    3,    3,    4,    3,    2,    2,    1,    2,
    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   13,    0,    0,    1,    0,    7,    0,   14,    0,
    0,    0,    0,    0,    0,    0,    2,    6,   16,   20,
   19,    0,   23,    0,    0,    0,   12,   11,    0,   27,
    0,   52,    0,   58,    0,    0,   18,   56,    0,    0,
   67,    0,   72,    0,   71,    0,   22,   24,   25,   68,
    8,    0,   45,   48,    0,   49,    0,    0,   38,   46,
    0,    0,    0,   15,   17,    0,    0,    0,   66,    0,
   70,   73,   10,   47,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   57,   29,    0,    0,   65,
    0,    0,   36,   37,    0,    0,    0,    0,    0,    0,
    0,   42,   43,    0,    0,    0,   41,   55,    0,    0,
    5,    0,   44,    0,    0,    0,    0,    0,    4,    0,
   50,    0,    0,   39,   40,   54,    0,    0,   53,   51,
};
final static short yydgoto[] = {                          4,
    5,  110,  111,  112,    7,    8,   29,   36,   19,   20,
   21,   22,   23,   24,   25,   57,   67,  105,   58,  106,
   59,  107,   60,   62,   26,   44,   45,   46,
};
final static short yysindex[] = {                      -241,
    0,    0, -252,    0,    0,  -81,    0, -240,    0,    0,
 -249, -248, -247,  -59, -242, -201,    0,    0,    0,    0,
    0, -244,    0, -230, -213, -254,    0,    0, -186,    0,
 -253,    0, -253,    0, -218,   -9,    0,    0, -190, -240,
    0, -212,    0,  -98,    0, -204,    0,    0,    0,    0,
    0, -172,    0,    0, -164,    0, -235, -251,    0,    0,
   19, -175, -170,    0,    0, -243, -152, -157,    0, -154,
    0,    0,    0,    0, -253, -253, -253, -253, -253, -253,
 -253, -253, -253, -253, -120,    0,    0, -210, -237,    0,
 -251, -251,    0,    0, -235, -235, -235, -235, -235, -235,
  -81,    0,    0, -112, -203, -200,    0,    0, -217,  -81,
    0, -162,    0, -210, -210, -210, -210, -234,    0,  -81,
    0, -200, -200,    0,    0,    0, -118, -111,    0,    0,
};
final static short yyrindex[] = {                         0,
   27,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -124,  -82,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -121,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -64,  -46,    0,    0, -129, -128, -123, -119, -113, -108,
    0,    0,    0,    0, -110, -183,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -158, -138,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  165,   -6,  -27,   -4,    0,  138,    0,    0,   43,
  153,    0,    0,    0,    0,  -30,    0,  -83,   37,   10,
   55,   28,    0,    0,    0,    0,  137,    0,
};
final static int YYTABLESIZE=315;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
   21,   18,   61,   53,   54,  109,   30,   32,   34,   42,
   69,   43,   87,   38,    1,   27,   28,    9,  108,  102,
  103,  126,  102,  103,    2,   55,    3,    3,   77,   78,
   39,   47,   31,   16,  127,   33,   35,   70,   88,   43,
   56,  104,   75,   76,  104,   48,  102,  103,   95,   96,
   97,   98,   99,  100,   10,   11,   37,  118,   63,   12,
  114,  115,   49,   13,    2,   14,   66,    3,  104,   40,
   15,   72,   35,   35,  114,  115,   69,   35,   65,  116,
  117,   35,   35,   35,   73,   35,   16,   41,   35,   51,
   35,   35,  128,   74,   35,   35,   52,   33,   33,  129,
  120,  121,   33,  119,   35,   18,   33,   33,   33,   85,
   33,   91,   92,   33,   86,   33,   33,   34,   34,   33,
   33,   89,   34,  122,  123,   52,   34,   34,   34,   33,
   34,   93,   94,   34,   90,   34,   34,   10,   11,   34,
   34,  101,   12,  124,  125,  113,   13,    2,   14,   34,
    3,   26,  130,   15,   74,   62,   63,   10,   11,  114,
  115,   59,   12,   28,    6,   60,   13,    2,   14,   16,
    3,   61,   40,   15,   10,   11,   64,   68,   50,   12,
   71,    0,    0,   13,    2,   14,    0,    3,    0,   16,
   15,    0,    0,   32,    0,   32,   32,   11,    0,    0,
    0,   12,   32,   32,   32,   13,   16,   32,   32,   32,
   32,   30,   15,   30,   30,    0,    0,    0,    0,    0,
   30,   30,   30,    0,    0,   30,   30,   30,   30,   31,
    0,   31,   31,    0,    0,    0,    0,    0,   31,   31,
   31,    0,    0,   31,   31,   31,   31,   11,    0,    0,
    0,   12,    0,    0,    0,   13,    9,    9,   64,    0,
    0,    9,   15,   21,   21,    9,    9,    9,    0,    9,
    0,    9,    9,   69,   69,    0,   21,    0,    0,    0,
    0,    0,    9,    9,    0,    0,   69,    9,    9,   21,
    0,    9,    9,    9,    0,    9,   75,   76,    9,   69,
    0,    0,    0,    0,   79,   80,    0,    0,   81,   82,
   83,   84,    0,    0,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
    0,    6,   33,  257,  258,   89,  256,  256,  256,   16,
    0,   16,  256,  256,  256,  256,  257,  270,  256,  257,
  258,  256,  257,  258,  266,  279,    0,  269,  280,  281,
  273,  276,  282,  288,  118,  284,  284,   44,  282,   44,
  294,  279,  278,  279,  279,  276,  257,  258,   79,   80,
   81,   82,   83,   84,  256,  257,   14,  275,  277,  261,
  278,  279,  276,  265,  266,  267,  257,  269,  279,  271,
  272,  276,  256,  257,  278,  279,  289,  261,   36,  280,
  281,  265,  266,  267,  257,  269,  288,  289,  272,  276,
  274,  275,  120,  258,  278,  279,  283,  256,  257,  127,
  263,  264,  261,  110,  288,  110,  265,  266,  267,  285,
  269,   75,   76,  272,  285,  274,  275,  256,  257,  278,
  279,  274,  261,  114,  115,  283,  265,  266,  267,  288,
  269,   77,   78,  272,  289,  274,  275,  256,  257,  278,
  279,  262,  261,  116,  117,  258,  265,  266,  267,  288,
  269,  276,  264,  272,  276,  285,  285,  256,  257,  278,
  279,  285,  261,  274,    0,  285,  265,  266,  267,  288,
  269,  285,  271,  272,  256,  257,  285,   40,   26,  261,
   44,   -1,   -1,  265,  266,  267,   -1,  269,   -1,  288,
  272,   -1,   -1,  276,   -1,  278,  279,  257,   -1,   -1,
   -1,  261,  285,  286,  287,  265,  288,  290,  291,  292,
  293,  276,  272,  278,  279,   -1,   -1,   -1,   -1,   -1,
  285,  286,  287,   -1,   -1,  290,  291,  292,  293,  276,
   -1,  278,  279,   -1,   -1,   -1,   -1,   -1,  285,  286,
  287,   -1,   -1,  290,  291,  292,  293,  257,   -1,   -1,
   -1,  261,   -1,   -1,   -1,  265,  256,  257,  268,   -1,
   -1,  261,  272,  263,  264,  265,  266,  267,   -1,  269,
   -1,  271,  272,  263,  264,   -1,  276,   -1,   -1,   -1,
   -1,   -1,  256,  257,   -1,   -1,  276,  261,  288,  289,
   -1,  265,  266,  267,   -1,  269,  278,  279,  272,  289,
   -1,   -1,   -1,   -1,  286,  287,   -1,   -1,  290,  291,
  292,  293,   -1,   -1,  288,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=294;
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
null,null,null,"ID","CTEINT","ERROR","FINAL","IF","THEN","ELSE","ENDIF","PRINT",
"INT","BEGIN","END","UNSIGNED","LONG","MY","LOOP","FROM","TO","BY","SEMICOLON",
"STRING","PLUSLE","MINUN","MULTIPLY","DIVIDE","EQUAL","COMMA","LEFTPARENTHESIS",
"RIGHTPARENTHESIS","GREATTHAN","LESSTHAN","LEFTBRACE","RIGHTBRACE","EQUALEQUAL",
"GREATEQUAL","LESSEQUAL","DISTINCT","CTEUL",
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
"ejecutablesimple : listaambitos",
"ejecutablesimple : ejecutable",
"ejecutablesimple : error",
"ejecutable : asignacion SEMICOLON",
"ejecutable : sentenciaIF",
"ejecutable : sentenciaLOOP SEMICOLON",
"ejecutable : sentenciaPRINT SEMICOLON",
"asignacion : ID EQUAL expresion",
"asignacion : ID error",
"asignacionLOOP : ID EQUAL expresionLOOP",
"asignacionLOOP : ID error",
"expresion : expresion PLUSLE termino",
"expresion : expresion MINUN termino",
"expresion : termino",
"expresionLOOP : expresionLOOP PLUSLE terminoLOOP",
"expresionLOOP : expresionLOOP MINUN terminoLOOP",
"expresionLOOP : terminoLOOP",
"termino : termino MULTIPLY factor",
"termino : termino DIVIDE factor",
"termino : factor",
"terminoLOOP : terminoLOOP MULTIPLY factorLOOP",
"terminoLOOP : terminoLOOP DIVIDE factorLOOP",
"terminoLOOP : factorLOOP",
"factorLOOP : ID",
"factorLOOP : CTEINT",
"factorLOOP : MINUN CTEINT",
"factor : ID",
"factor : constant",
"factor : MINUN CTEINT",
"constant : CTEINT",
"constant : CTEUL",
"sentenciaIF : IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ENDIF",
"sentenciaIF : IF LEFTPARENTHESIS condicion RIGHTPARENTHESIS THEN bloquesentencias ELSE bloquesentencias ENDIF",
"sentenciaIF : IF error",
"sentenciaLOOP : LOOP FROM asignacionLOOP TO expresionLOOP BY expresionLOOP bloquesentencias",
"sentenciaLOOP : LOOP FROM asignacionLOOP TO expresionLOOP BY error",
"sentenciaLOOP : LOOP FROM asignacionLOOP TO error",
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
"listaambitos : ambito listaambitos",
"listaambitos : ambito",
"declarativasambito : declarativasambito declarativaambito",
"declarativasambito : declarativaambito",
"declarativaambito : declarativa",
"declarativaambito : sentenciaMY SEMICOLON",
"sentenciaMY : MY listavariables",
};

//#line 201 "Parser.y"
private LexicAnalyzer lexicAnalyzer;
private CAMerror SyntaxError;
private SymbolsTable symbolsTable;
private SyntacticLogger synlog;

private Vector<Integer> IDlist = new Vector<Integer>();
private String currentType = new String();


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
//#line 430 "Parser.java"
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
case 3:
//#line 28 "Parser.y"
{SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine());}
break;
case 4:
//#line 31 "Parser.y"
{System.out.println("bloque sentencias");}
break;
case 5:
//#line 32 "Parser.y"
{System.out.println("bloque sentencias solo ejecutables");}
break;
case 6:
//#line 35 "Parser.y"
{System.out.println("declarativas");}
break;
case 7:
//#line 36 "Parser.y"
{System.out.println("declarativas2");}
break;
case 8:
//#line 39 "Parser.y"
{
                                                        synlog.addLog("Declaration",lexicAnalyzer.getLine());
                                                        for (int i = 0; i < IDlist.size(); i++) {
                                                            SymbolsTableEntry entry = symbolsTable.getEntry(IDlist.elementAt(i));
                                                            entry.setSType(currentType);
                                                        }
                                                        IDlist.removeAllElements();
                                                        currentType = "";
                                                    }
break;
case 9:
//#line 48 "Parser.y"
{SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine());}
break;
case 10:
//#line 51 "Parser.y"
{System.out.println("listavariables"); int pointer = yylval.tok.getPointer(); IDlist.addElement(pointer);}
break;
case 11:
//#line 52 "Parser.y"
{System.out.println("listavariables2"); int pointer = yylval.tok.getPointer(); IDlist.addElement(pointer);}
break;
case 12:
//#line 53 "Parser.y"
{SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine());}
break;
case 13:
//#line 56 "Parser.y"
{System.out.println("tipo"); currentType = "INTEG";}
break;
case 14:
//#line 57 "Parser.y"
{System.out.println("tipo2"); currentType = "ULONG";}
break;
case 16:
//#line 61 "Parser.y"
{System.out.println("ejecutables (simple)");}
break;
case 17:
//#line 64 "Parser.y"
{System.out.println("listaejecutables");}
break;
case 18:
//#line 65 "Parser.y"
{System.out.println("listaejecutables2");}
break;
case 19:
//#line 68 "Parser.y"
{System.out.println("ejecutablesimple");}
break;
case 20:
//#line 69 "Parser.y"
{System.out.println("ejecutablesimple2");}
break;
case 21:
//#line 70 "Parser.y"
{SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine());}
break;
case 22:
//#line 73 "Parser.y"
{synlog.addLog("Asignation",lexicAnalyzer.getLine());}
break;
case 23:
//#line 74 "Parser.y"
{synlog.addLog("IF",lexicAnalyzer.getLine());}
break;
case 24:
//#line 75 "Parser.y"
{synlog.addLog("LOOP",lexicAnalyzer.getLine());}
break;
case 25:
//#line 76 "Parser.y"
{synlog.addLog("PRINT",lexicAnalyzer.getLine());}
break;
case 26:
//#line 79 "Parser.y"
{System.out.println("asignacion");}
break;
case 27:
//#line 80 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 28:
//#line 83 "Parser.y"
{System.out.println("asignacion");}
break;
case 29:
//#line 84 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 30:
//#line 87 "Parser.y"
{System.out.println("expresion+");}
break;
case 31:
//#line 88 "Parser.y"
{System.out.println("expresion-");}
break;
case 33:
//#line 92 "Parser.y"
{System.out.println("expresion+");}
break;
case 34:
//#line 93 "Parser.y"
{System.out.println("expresion-");}
break;
case 36:
//#line 97 "Parser.y"
{System.out.println("termino*");}
break;
case 37:
//#line 98 "Parser.y"
{System.out.println("termino/");}
break;
case 38:
//#line 99 "Parser.y"
{System.out.println("termino factor");}
break;
case 39:
//#line 102 "Parser.y"
{System.out.println("termino*");}
break;
case 40:
//#line 103 "Parser.y"
{System.out.println("termino/");}
break;
case 41:
//#line 104 "Parser.y"
{System.out.println("termino factor");}
break;
case 42:
//#line 107 "Parser.y"
{System.out.println("factor");}
break;
case 43:
//#line 108 "Parser.y"
{
                                System.out.println("constante entera");
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 44:
//#line 115 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                System.out.println("factor negativo"+temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 45:
//#line 126 "Parser.y"
{System.out.println("factor");}
break;
case 46:
//#line 127 "Parser.y"
{System.out.println("factor2");}
break;
case 47:
//#line 128 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                System.out.println("factor negativo"+temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 48:
//#line 139 "Parser.y"
{
                                System.out.println("constante entera");
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 49:
//#line 146 "Parser.y"
{
                                System.out.println("constante larga");
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "ULONG";
                                entry.setSType(temp);
                            }
break;
case 50:
//#line 155 "Parser.y"
{System.out.println("sentenciaIF");}
break;
case 51:
//#line 156 "Parser.y"
{System.out.println("sentenciaIF ELSE");}
break;
case 52:
//#line 157 "Parser.y"
{SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
break;
case 53:
//#line 161 "Parser.y"
{System.out.println("sentenciaLOOP");}
break;
case 54:
//#line 162 "Parser.y"
{System.out.println("ERROR en expresion2");}
break;
case 55:
//#line 163 "Parser.y"
{System.out.println("ERROR en expresion1");}
break;
case 56:
//#line 164 "Parser.y"
{SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());System.out.println("ERROR en LUP");}
break;
case 57:
//#line 167 "Parser.y"
{System.out.println("sentenciaPRINT");}
break;
case 58:
//#line 168 "Parser.y"
{SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());}
break;
case 59:
//#line 171 "Parser.y"
{System.out.println("condicion==");}
break;
case 60:
//#line 172 "Parser.y"
{System.out.println("condicion>=");}
break;
case 61:
//#line 173 "Parser.y"
{System.out.println("condicion<=");}
break;
case 62:
//#line 174 "Parser.y"
{System.out.println("condicion>");}
break;
case 63:
//#line 175 "Parser.y"
{System.out.println("condicion<");}
break;
case 64:
//#line 176 "Parser.y"
{System.out.println("condicion<>");}
break;
case 65:
//#line 179 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 66:
//#line 180 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 67:
//#line 181 "Parser.y"
{synlog.addLog("Empty scope ends",lexicAnalyzer.getLine());}
break;
case 70:
//#line 188 "Parser.y"
{System.out.println("declarativaSambito");}
break;
case 71:
//#line 189 "Parser.y"
{System.out.println("declarativaSambito2");}
break;
case 72:
//#line 192 "Parser.y"
{System.out.println("declarativaambito");}
break;
case 73:
//#line 193 "Parser.y"
{System.out.println("declarativaambito MY");}
break;
case 74:
//#line 195 "Parser.y"
{synlog.addLog("MY",lexicAnalyzer.getLine());}
break;
//#line 889 "Parser.java"
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
