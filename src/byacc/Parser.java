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
    9,   10,   10,   10,   10,   12,   12,   16,   18,   18,
   17,   17,   17,   19,   19,   19,   20,   20,   20,   21,
   21,   21,   23,   23,   23,   22,   22,   22,   24,   24,
   13,   13,   13,   14,   14,   14,   14,   15,   15,   25,
   25,   25,   25,   25,   25,   28,   26,   27,   27,   27,
   30,   11,   11,   29,   29,   31,   31,   32,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    1,    2,    1,    3,    1,    3,
    1,    1,    1,    2,    3,    1,    2,    1,    1,    1,
    1,    2,    1,    2,    2,    3,    2,    1,    3,    2,
    3,    3,    1,    3,    3,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    1,    2,    1,    1,
    7,    9,    2,    8,    7,    5,    2,    4,    2,    3,
    3,    3,    3,    3,    3,    0,    2,    4,    3,    2,
    0,    3,    1,    2,    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   13,    0,    0,    1,    0,    7,    0,   14,    0,
   28,    0,    0,    0,    0,    2,    6,   16,   20,   19,
    0,   23,    0,    0,    0,    0,    0,   12,   11,    0,
   53,    0,   59,    0,    0,   18,   57,    0,   22,   24,
   25,   27,    0,   66,    0,   67,    8,    0,   46,   49,
    0,   50,    0,    0,   39,   47,    0,    0,   15,   17,
    0,    0,    0,    0,   72,    0,   70,    0,   76,    0,
   75,    0,   10,   48,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   58,   30,    0,    0,    0,
   69,    0,   74,   77,    0,    0,    0,    0,    0,    0,
    0,    0,   37,   38,    0,   43,   44,    0,    0,    0,
   42,   56,    0,   68,    0,    5,    0,   45,    0,    0,
    0,    0,    0,    4,    0,   51,    0,    0,   40,   41,
   55,    0,    0,   54,   52,
};
final static short yydgoto[] = {                          4,
    5,  115,  116,  117,    7,    8,   30,   35,   18,   19,
   20,   21,   22,   23,   24,   25,   53,   63,  109,   54,
  110,   55,  111,   56,   57,   26,   46,   27,   70,   44,
   71,   72,
};
final static short yysindex[] = {                      -246,
    0,    0, -261,    0,    0,  -51,    0, -202,    0,    0,
    0, -250, -249, -101, -244,    0,    0,    0,    0,    0,
 -263,    0, -257, -255, -251,    0, -264,    0,    0, -227,
    0, -254,    0, -217,  -30,    0,    0, -190,    0,    0,
    0,    0, -254,    0, -239,    0,    0, -188,    0,    0,
 -182,    0,  -67, -194,    0,    0, -189, -180,    0,    0,
 -153, -170, -160, -179,    0, -202,    0, -168,    0,  -68,
    0, -141,    0,    0, -254, -254, -254, -254, -254, -254,
 -254, -254, -254, -254, -115,    0,    0, -242, -178, -145,
    0, -136,    0,    0, -194, -194, -179, -179, -179, -179,
 -179, -179,    0,    0,  -51,    0,    0,  -99, -147, -123,
    0,    0, -186,    0,  -51,    0, -122,    0, -242, -242,
 -242, -242, -133,    0,  -51,    0, -123, -123,    0,    0,
    0, -139,  -86,    0,    0,
};
final static short yyrindex[] = {                         0,
   36,    0,    0,    0,    0,  -96,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -96,    0,    0,    0,    0,    0,
    0,    0,    0, -142,    0,    0,    0,    0,    0,    0,
 -117,    0,    0,  -82,    0,    0,    0,    0,    0,  -96,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -81,
    0,    0,    0,    0, -124, -106,  -89,  -85,  -83,  -78,
  -77,  -76,    0,    0,  -96,    0,    0,    0,  -61, -204,
    0,    0,    0,    0,  -96,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -96,    0, -184, -159,    0,    0,
    0,  -96,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  217,   -6,  -74,   -4,    0,  156,    0,    0,   24,
  184,    0,    0,    0,    0,  191,  -35,    0,  -75,   99,
   57,  107,   61,    0,    0,    0,    0,    0,    0,    0,
  160,    0,
};
final static int YYTABLESIZE=324;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
   21,   17,   49,   50,   42,   31,   33,   64,    9,    1,
   73,   37,   39,  113,  106,  107,   10,   11,   40,    2,
   41,   12,    3,   45,   51,   13,    2,   14,   38,    3,
   43,   66,   15,   32,   34,    3,  108,   36,   68,   52,
   69,   97,   98,   99,  100,  101,  102,  132,   47,   67,
  133,   36,   36,   28,   29,   48,   36,  134,   60,   58,
   36,   36,   36,   92,   36,   69,   61,   36,   73,   36,
   36,   34,   34,   36,   36,   74,   34,  112,  106,  107,
   34,   34,   34,   36,   34,   83,   84,   34,  123,   34,
   34,  119,  120,   34,   34,   85,   35,   35,   75,   76,
  108,   35,   87,   34,   86,   35,   35,   35,  124,   35,
   17,   88,   35,   89,   35,   35,   10,   11,   35,   35,
   91,   12,  131,  106,  107,   13,    2,   14,   35,    3,
  119,  120,   15,   33,   94,   33,   33,   48,  119,  120,
  125,  126,   33,   33,   33,  108,  105,   33,   33,   33,
   33,   31,  114,   31,   31,   11,  121,  122,  118,   12,
   31,   31,   31,   13,   28,   31,   31,   31,   31,   32,
   15,   32,   32,   95,   96,  127,  128,  135,   32,   32,
   32,  129,  130,   32,   32,   32,   32,   10,   11,  103,
  104,   66,   12,   26,   78,   63,   13,    2,   14,   64,
    3,   60,   66,   15,   10,   11,   61,   62,   65,   12,
   75,   76,   29,   13,    2,   14,    6,    3,   77,   78,
   15,   90,   79,   80,   81,   82,   11,   65,   62,   93,
   12,    0,    0,    0,   13,    0,    0,   59,    0,    0,
    0,   15,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    9,    9,    0,    0,
    0,    9,    0,   21,   21,    9,    9,    9,    0,    9,
    0,    9,    9,   73,   73,    0,   21,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   73,    0,    9,   21,
    0,    9,    9,    0,    0,    0,    9,    0,   71,   73,
    9,    9,    9,    0,    9,    0,    0,    9,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
    0,    6,  257,  258,  256,  256,  256,   43,  270,  256,
    0,  256,  276,   89,  257,  258,  256,  257,  276,  266,
  276,  261,  269,  288,  279,  265,  266,  267,  273,  269,
  282,  271,  272,  284,  284,    0,  279,   14,   45,  294,
   45,   77,   78,   79,   80,   81,   82,  123,  276,  289,
  125,  256,  257,  256,  257,  283,  261,  132,   35,  277,
  265,  266,  267,   70,  269,   70,  257,  272,  257,  274,
  275,  256,  257,  278,  279,  258,  261,  256,  257,  258,
  265,  266,  267,  288,  269,  280,  281,  272,  275,  274,
  275,  278,  279,  278,  279,  285,  256,  257,  278,  279,
  279,  261,  256,  288,  285,  265,  266,  267,  115,  269,
  115,  282,  272,  274,  274,  275,  256,  257,  278,  279,
  289,  261,  256,  257,  258,  265,  266,  267,  288,  269,
  278,  279,  272,  276,  276,  278,  279,  283,  278,  279,
  263,  264,  285,  286,  287,  279,  262,  290,  291,  292,
  293,  276,  289,  278,  279,  257,  280,  281,  258,  261,
  285,  286,  287,  265,  282,  290,  291,  292,  293,  276,
  272,  278,  279,   75,   76,  119,  120,  264,  285,  286,
  287,  121,  122,  290,  291,  292,  293,  256,  257,   83,
   84,  288,  261,  276,  276,  285,  265,  266,  267,  285,
  269,  285,  271,  272,  256,  257,  285,  285,  285,  261,
  278,  279,  274,  265,  266,  267,    0,  269,  286,  287,
  272,   66,  290,  291,  292,  293,  257,   44,   38,   70,
  261,   -1,   -1,   -1,  265,   -1,   -1,  268,   -1,   -1,
   -1,  272,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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
"asignacionLOOP : identificador EQUAL expresionLOOP",
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
"$$1 :",
"ambitotemp : $$1 ambito",
"ambito : LEFTBRACE declarativasambito ejecutables RIGHTBRACE",
"ambito : LEFTBRACE ejecutables RIGHTBRACE",
"ambito : LEFTBRACE RIGHTBRACE",
"$$2 :",
"listaambitos : ambitotemp $$2 listaambitos",
"listaambitos : ambitotemp",
"declarativasambito : declarativasambito declarativaambito",
"declarativasambito : declarativaambito",
"declarativaambito : declarativa",
"declarativaambito : sentenciaMY SEMICOLON",
"sentenciaMY : MY listavariables",
};

//#line 221 "Parser.y"
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
//#line 442 "Parser.java"
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
{int pointer = val_peek(0).tok.getPointer(); IDlist.addElement(pointer);}
break;
case 11:
//#line 53 "Parser.y"
{int pointer = val_peek(0).tok.getPointer(); IDlist.addElement(pointer);}
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
{  int pointer = val_peek(0).tok.getPointer();
                            System.out.println("pointer: "+pointer);
                            System.out.println("code   : "+val_peek(0).tok.getCode());
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());                                                        
                            boolean isInScope = symbolsTable.inScope(pointer,entry);
                            if (!isInScope)
                                SyntaxError.addLog("Variable not declared",lexicAnalyzer.getLine());
                            yyval = val_peek(0);}
break;
case 30:
//#line 96 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 43:
//#line 119 "Parser.y"
{  int pointer = val_peek(0).tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
break;
case 44:
//#line 122 "Parser.y"
{
                                int pointer = val_peek(0).tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 45:
//#line 128 "Parser.y"
{
                                int pointer = val_peek(0).tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 46:
//#line 138 "Parser.y"
{  int pointer = val_peek(0).tok.getPointer();
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            entry.addScope(myScope.getScopeSuffix());}
break;
case 48:
//#line 142 "Parser.y"
{
                                int pointer = val_peek(0).tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 49:
//#line 152 "Parser.y"
{
                                int pointer = val_peek(0).tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 50:
//#line 158 "Parser.y"
{
                                int pointer = val_peek(0).tok.getPointer();
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "ULONG";
                                entry.setSType(temp);
                            }
break;
case 53:
//#line 168 "Parser.y"
{SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
break;
case 57:
//#line 175 "Parser.y"
{SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());}
break;
case 59:
//#line 179 "Parser.y"
{SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());}
break;
case 66:
//#line 190 "Parser.y"
{         int number = myScope.getScopesContained()+1;
                                String numb = Integer.toString(number);
                                Scope currentScope = new Scope(numb, myScope);
                                myScope.push(currentScope);
                                myScope = currentScope;
                                System.out.println(myScope.getScopeSuffix());
                                }
break;
case 68:
//#line 199 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 69:
//#line 200 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 70:
//#line 201 "Parser.y"
{synlog.addLog("Empty scope ends",lexicAnalyzer.getLine());}
break;
case 71:
//#line 204 "Parser.y"
{myScope = myScope.getFather();System.out.println(myScope.getScopeSuffix());}
break;
case 73:
//#line 205 "Parser.y"
{myScope = myScope.getFather();System.out.println(myScope.getScopeSuffix());}
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
