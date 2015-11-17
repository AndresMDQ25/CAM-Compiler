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
    9,   10,   10,   10,   10,   12,   12,   16,   19,   18,
   18,   17,   17,   17,   20,   20,   20,   21,   21,   21,
   22,   22,   22,   24,   24,   24,   23,   23,   23,   25,
   25,   13,   13,   13,   14,   14,   14,   14,   15,   15,
   26,   26,   26,   26,   26,   26,   29,   27,   28,   28,
   28,   31,   11,   11,   30,   30,   32,   32,   33,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    1,    2,    1,    3,    1,    3,
    1,    1,    1,    2,    3,    1,    2,    1,    1,    1,
    1,    2,    1,    2,    2,    3,    2,    1,    0,    4,
    2,    3,    3,    1,    3,    3,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    2,    1,    1,    2,    1,
    1,    7,    9,    2,    8,    7,    5,    2,    4,    2,
    3,    3,    3,    3,    3,    3,    0,    2,    4,    3,
    2,    0,    3,    1,    2,    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   13,    0,    0,    1,    0,    7,    0,   14,    0,
   28,    0,    0,    0,    0,    2,    6,   16,   20,   19,
    0,   23,    0,    0,    0,    0,    0,   12,   11,    0,
   54,    0,   60,    0,    0,   18,   58,    0,   22,   24,
   25,   27,    0,   67,    0,   68,    8,    0,   47,   50,
    0,   51,    0,    0,   40,   48,    0,    0,   15,   17,
    0,    0,    0,   73,    0,   71,    0,   77,    0,   76,
    0,   10,   49,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   59,   31,    0,    0,    0,   70,
    0,   75,   78,    0,    0,    0,    0,    0,    0,    0,
    0,   38,   39,    0,    0,   57,   44,   45,    0,    0,
    0,   43,   69,    0,    5,    0,    0,   46,    0,    0,
    0,    0,    0,    4,    0,   52,   56,    0,    0,    0,
   41,   42,    0,   55,   53,
};
final static short yydgoto[] = {                          4,
    5,  114,  115,  116,    7,    8,   30,   35,   18,   19,
   20,   21,   22,   23,   24,   25,   53,   62,   87,  110,
   54,  111,   55,  112,   56,   57,   26,   46,   27,   69,
   44,   70,   71,
};
final static short yysindex[] = {                      -174,
    0,    0, -257,    0,    0,  -57,    0, -124,    0,    0,
    0, -249, -234, -107, -184,    0,    0,    0,    0,    0,
 -253,    0, -239, -224, -248,    0, -269,    0,    0, -259,
    0, -254,    0, -250,  -36,    0,    0, -201,    0,    0,
    0,    0, -254,    0, -251,    0,    0, -197,    0,    0,
 -196,    0,  -73, -140,    0,    0, -202, -186,    0,    0,
 -159, -173, -127,    0, -124,    0, -182,    0,  -74,    0,
 -171,    0,    0, -254, -254, -254, -254, -254, -254, -254,
 -254, -254, -254, -135,    0,    0, -129, -228, -164,    0,
 -155,    0,    0, -140, -140, -127, -127, -127, -127, -127,
 -127,    0,    0,  -57, -183,    0,    0,    0,  -99, -158,
 -111,    0,    0,  -57,    0,  -92, -102,    0, -225, -183,
 -183, -183, -183,    0,  -57,    0,    0, -143, -111, -111,
    0,    0, -117,    0,    0,
};
final static short yyrindex[] = {                         0,
   36,    0,    0,    0,    0, -120,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -120,    0,    0,    0,    0,    0,
    0,    0,    0, -148,    0,    0,    0,    0,    0,    0,
  -96,    0,  -86,    0,    0,    0,    0,    0, -120,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -82,    0,
    0,    0,    0, -130, -112,  -89,  -78,  -69,  -61,  -59,
  -58,    0,    0, -120,    0,    0,    0,    0,    0,    0,
 -208,    0,    0, -120,    0,    0,  -71,    0,    0,    0,
    0,    0,    0,    0, -120,    0,    0, -120, -188, -163,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  211,   -6,  -40,   -4,    0,  163,    0,    0,   41,
  186,    0,    0,    0,    0,    0,  -34,    0,    0,  -93,
  110,   68,  119,  100,    0,    0,    0,    0,    0,    0,
    0,  162,    0,
};
final static int YYTABLESIZE=324;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
   21,   17,   49,   50,   10,   11,   31,   42,   63,   12,
   74,  117,    9,   13,    2,   14,   47,    3,   45,   65,
   15,   33,   39,   48,   51,  128,   58,  106,  107,  108,
  127,  107,  108,   43,   32,    3,   40,   66,   67,   52,
   68,   96,   97,   98,   99,  100,  101,   37,   37,   34,
  109,   41,   37,  109,   36,   61,   37,   37,   37,   72,
   37,   73,   91,   37,   68,   37,   37,   35,   35,   37,
   37,   37,   35,  107,  108,   60,   35,   35,   35,   37,
   35,    1,   84,   35,  133,   35,   35,  134,   38,   35,
   35,    2,   36,   36,    3,  109,   86,   36,   85,   35,
   88,   36,   36,   36,   93,   36,   90,  124,   36,   17,
   36,   36,   10,   11,   36,   36,  119,   12,   48,  120,
  121,   13,    2,   14,   36,    3,  104,   34,   15,   34,
   34,   28,   29,  113,  120,  121,   34,   34,   34,   82,
   83,   34,   34,   34,   34,   32,  135,   32,   32,   11,
   74,   75,  105,   12,   32,   32,   32,   13,  118,   32,
   32,   32,   32,   33,   15,   33,   33,   67,  122,  123,
  125,  126,   33,   33,   33,  120,  121,   33,   33,   33,
   33,   10,   11,   94,   95,   29,   12,  129,  130,   26,
   13,    2,   14,   79,    3,   64,   65,   15,   10,   11,
  102,  103,   30,   12,   74,   75,   65,   13,    2,   14,
    6,    3,   76,   77,   15,   61,   78,   79,   80,   81,
   11,  131,  132,   62,   12,   63,   66,   89,   13,   64,
   92,   59,    0,    0,    0,   15,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    9,    9,    0,    0,
    0,    9,    0,   21,   21,    9,    9,    9,    0,    9,
    0,    9,    9,   74,   74,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   74,    0,    9,   21,
    0,    9,    9,    0,    0,    0,    9,    0,   72,   74,
    9,    9,    9,    0,    9,    0,    0,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
    0,    6,  257,  258,  256,  257,  256,  256,   43,  261,
    0,  105,  270,  265,  266,  267,  276,  269,  288,  271,
  272,  256,  276,  283,  279,  119,  277,  256,  257,  258,
  256,  257,  258,  282,  284,    0,  276,  289,   45,  294,
   45,   76,   77,   78,   79,   80,   81,  256,  257,  284,
  279,  276,  261,  279,   14,  257,  265,  266,  267,  257,
  269,  258,   69,  272,   69,  274,  275,  256,  257,  278,
  279,  256,  261,  257,  258,   35,  265,  266,  267,  288,
  269,  256,  285,  272,  125,  274,  275,  128,  273,  278,
  279,  266,  256,  257,  269,  279,  256,  261,  285,  288,
  274,  265,  266,  267,  276,  269,  289,  114,  272,  114,
  274,  275,  256,  257,  278,  279,  275,  261,  283,  278,
  279,  265,  266,  267,  288,  269,  262,  276,  272,  278,
  279,  256,  257,  289,  278,  279,  285,  286,  287,  280,
  281,  290,  291,  292,  293,  276,  264,  278,  279,  257,
  278,  279,  282,  261,  285,  286,  287,  265,  258,  290,
  291,  292,  293,  276,  272,  278,  279,  288,  280,  281,
  263,  264,  285,  286,  287,  278,  279,  290,  291,  292,
  293,  256,  257,   74,   75,  282,  261,  120,  121,  276,
  265,  266,  267,  276,  269,  285,  271,  272,  256,  257,
   82,   83,  274,  261,  278,  279,  285,  265,  266,  267,
    0,  269,  286,  287,  272,  285,  290,  291,  292,  293,
  257,  122,  123,  285,  261,  285,  285,   65,  265,   44,
   69,  268,   -1,   -1,   -1,  272,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,
   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,  269,
   -1,  271,  272,  263,  264,   -1,  276,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  276,   -1,  288,  289,
   -1,  256,  257,   -1,   -1,   -1,  261,   -1,  288,  289,
  265,  266,  267,   -1,  269,   -1,   -1,  272,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  288,
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
"asignacion : identificador EQUAL expresion",
"asignacion : identificador error",
"identificador : ID",
"$$1 :",
"asignacionLOOP : ID $$1 EQUAL expresionLOOP",
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
"$$2 :",
"ambitotemp : $$2 ambito",
"ambito : LEFTBRACE declarativasambito ejecutables RIGHTBRACE",
"ambito : LEFTBRACE ejecutables RIGHTBRACE",
"ambito : LEFTBRACE RIGHTBRACE",
"$$3 :",
"listaambitos : ambitotemp $$3 listaambitos",
"listaambitos : ambitotemp",
"declarativasambito : declarativasambito declarativaambito",
"declarativasambito : declarativaambito",
"declarativaambito : declarativa",
"declarativaambito : sentenciaMY SEMICOLON",
"sentenciaMY : MY listavariables",
};

//#line 219 "Parser.y"
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
//#line 443 "Parser.java"
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
case 6:
//#line 35 "Parser.y"
{}
break;
case 8:
//#line 39 "Parser.y"
{
                                                        synlog.addLog("Declaration",lexicAnalyzer.getLine());
                                                        for (int i = 0; i < IDlist.size(); i++) {
                                                            SymbolsTableEntry entry = symbolsTable.getEntry(IDlist.elementAt(i));
                                                            entry.setSType(currentType);
                                                            entry.addScope(myScope.getScopeSuffix());
                                                        }
                                                        IDlist.removeAllElements();
                                                        currentType = "";
                                                    }
break;
case 9:
//#line 49 "Parser.y"
{SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine());}
break;
case 10:
//#line 52 "Parser.y"
{int pointer = yylval.tok.getPointer(); IDlist.addElement(pointer);}
break;
case 11:
//#line 53 "Parser.y"
{int pointer = yylval.tok.getPointer(); IDlist.addElement(pointer);}
break;
case 12:
//#line 54 "Parser.y"
{SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine());}
break;
case 13:
//#line 57 "Parser.y"
{currentType = "INTEG";}
break;
case 14:
//#line 58 "Parser.y"
{currentType = "ULONG";}
break;
case 21:
//#line 71 "Parser.y"
{SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine());}
break;
case 22:
//#line 74 "Parser.y"
{synlog.addLog("Asignation",lexicAnalyzer.getLine());}
break;
case 23:
//#line 75 "Parser.y"
{synlog.addLog("IF",lexicAnalyzer.getLine());}
break;
case 24:
//#line 76 "Parser.y"
{synlog.addLog("LOOP",lexicAnalyzer.getLine());}
break;
case 25:
//#line 77 "Parser.y"
{synlog.addLog("PRINT",lexicAnalyzer.getLine());}
break;
case 27:
//#line 81 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 28:
//#line 84 "Parser.y"
{  int pointer = yylval.tok.getPointer();
                            System.out.println(pointer);
                            System.out.println(yylval.tok.getCode());
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
break;
case 29:
//#line 91 "Parser.y"
{  int pointer = yylval.tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
break;
case 31:
//#line 94 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 44:
//#line 117 "Parser.y"
{  int pointer = yylval.tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
break;
case 45:
//#line 120 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 46:
//#line 126 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 47:
//#line 136 "Parser.y"
{  int pointer = yylval.tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
break;
case 49:
//#line 140 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 50:
//#line 150 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 51:
//#line 156 "Parser.y"
{
                                int pointer = yylval.tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "ULONG";
                                entry.setSType(temp);
                            }
break;
case 54:
//#line 166 "Parser.y"
{SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
break;
case 58:
//#line 173 "Parser.y"
{SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());}
break;
case 60:
//#line 177 "Parser.y"
{SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());}
break;
case 67:
//#line 188 "Parser.y"
{         int number = myScope.getScopesContained()+1;
                                String numb = Integer.toString(number);
                                Scope currentScope = new Scope(numb, myScope);
                                myScope.push(currentScope);
                                myScope = currentScope;
                                System.out.println(myScope.getScopeSuffix());
                                }
break;
case 69:
//#line 197 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 70:
//#line 198 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 71:
//#line 199 "Parser.y"
{synlog.addLog("Empty scope ends",lexicAnalyzer.getLine());}
break;
case 72:
//#line 202 "Parser.y"
{myScope = myScope.getFather();System.out.println(myScope.getScopeSuffix());}
break;
case 74:
//#line 203 "Parser.y"
{myScope = myScope.getFather();System.out.println(myScope.getScopeSuffix());}
break;
//#line 782 "Parser.java"
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
