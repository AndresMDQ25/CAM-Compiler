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
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;


//#line 35 "Parser.java"




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
   13,   13,   26,   26,   27,   14,   14,   14,   14,   15,
   15,   25,   28,   28,   28,   28,   28,   28,   31,   29,
   30,   30,   30,   33,   11,   11,   32,   32,   34,   34,
   35,   36,   36,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    1,    2,    1,    3,    1,    3,
    1,    1,    1,    2,    3,    1,    2,    1,    1,    1,
    1,    2,    1,    2,    2,    3,    2,    1,    3,    2,
    3,    3,    1,    3,    3,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    1,    1,    2,    1,    1,
    6,    2,    2,    4,    1,    8,    7,    5,    2,    4,
    2,    1,    3,    3,    3,    3,    3,    3,    0,    2,
    4,    3,    2,    0,    3,    1,    2,    1,    1,    2,
    2,    2,    1,
};
final static short yydefred[] = {                         0,
    0,   13,    0,    0,    1,    0,    7,    0,   14,    0,
   28,    0,    0,    0,    0,    2,    6,   16,   20,   19,
    0,   23,    0,    0,    0,    0,    0,   12,   11,    0,
   52,    0,   61,    0,    0,   18,   59,    0,   22,   24,
   25,   27,    0,   69,    0,   70,    8,    0,   49,    0,
   50,   46,    0,    0,   39,   47,    0,   62,    0,   15,
   17,    0,    0,    0,    0,   75,    0,   73,    0,   79,
    0,   78,    0,   10,   48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   60,   30,    0,    0,
   83,    0,   72,    0,   77,   80,    0,    0,    0,    0,
    0,    0,    0,    0,   37,   38,    0,   44,    0,   43,
    0,    0,   42,   58,    0,   82,   71,    0,    5,   55,
   51,    0,   45,    0,    0,    0,    0,    0,    4,    0,
   53,    0,    0,   40,   41,   57,    0,    0,   56,   54,
};
final static short yydgoto[] = {                          4,
    5,  118,  119,  120,    7,    8,   30,   35,   18,   19,
   20,   21,   22,   23,   24,   52,   53,   64,  111,   54,
  112,   55,  113,   56,   57,  121,  122,   58,   26,   46,
   27,   71,   44,   72,   73,   92,
};
final static short yysindex[] = {                      -187,
    0,    0, -258,    0,    0,  -13,    0, -199,    0,    0,
    0, -250, -246,  -71, -200,    0,    0,    0,    0,    0,
 -255,    0, -243, -225, -252,    0, -265,    0,    0, -256,
    0, -244,    0, -222,   23,    0,    0, -179,    0,    0,
    0,    0, -244,    0, -195,    0,    0, -169,    0, -166,
    0,    0,   47, -221,    0,    0, -190,    0, -172,    0,
    0, -140, -165, -151, -215,    0, -130,    0, -161,    0,
   50,    0, -147,    0,    0, -244, -244, -244, -244, -244,
 -244, -244, -244, -244, -244, -129,    0,    0, -242, -239,
    0, -125,    0, -152,    0,    0, -221, -221, -215, -215,
 -215, -215, -215, -215,    0,    0,  -13,    0, -119,    0,
 -192, -183,    0,    0, -253,    0,    0,  -13,    0,    0,
    0, -164,    0, -242, -242, -242, -242, -204,    0,  -13,
    0, -183, -183,    0,    0,    0, -176, -124,    0,    0,
};
final static short yyrindex[] = {                         0,
   36,    0,    0,    0,    0, -146,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -146,    0,    0,    0,    0,    0,
    0,    0,    0,  -87,    0,    0,    0,    0,    0,    0,
    0, -133,    0,    0, -123,    0,    0,    0,    0,    0,
 -146,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -117,    0,    0,    0,    0,  -69,  -51, -134, -116,
 -114, -113, -112, -105,    0,    0, -146,    0,    0,    0,
 -122, -131,    0,    0,    0,    0,    0, -146,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -146,
    0, -111,  -91,    0,    0,    0, -146,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  160,   -4, -106,   -3,    0,    0,    0,    0,   -7,
  118,    0,    0,    0,    0,   -6,  -34,    0,  -85,   28,
  -18,   24,  -16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  108,    0,    0,
};
final static int YYTABLESIZE=340;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         25,
   21,   16,   17,   42,  115,   31,   36,   25,   65,   33,
   76,    9,   11,   49,   11,  108,  114,   11,  108,   47,
   39,  128,   45,  138,  124,  125,   48,   61,   25,   43,
  139,   63,   40,   32,   50,    3,  109,   34,   25,  109,
   69,   70,  137,   99,  100,  101,  102,  103,  104,   51,
   41,  136,   11,  108,   59,   37,   28,   29,   84,   85,
   10,   11,   76,   77,   25,   12,   94,   70,    1,   13,
    2,   14,   38,    3,  109,   67,   15,   62,    2,   10,
   11,    3,  110,  110,   12,  124,  125,   74,   13,    2,
   14,   75,    3,   68,   86,   15,  126,  127,  130,  131,
   25,  124,  125,   97,   98,  132,  133,  105,  106,  134,
  135,   25,   87,  129,   17,   88,   89,  110,  110,  110,
  110,  110,   90,   25,   36,   36,   91,   93,   96,   36,
   25,  116,  107,   36,   36,   36,  117,   36,  123,  140,
   36,   69,   36,   36,   34,   34,   36,   36,   28,   34,
   66,   29,   26,   34,   34,   34,   36,   34,   81,    6,
   34,   66,   34,   34,   35,   35,   34,   34,   67,   35,
   63,   64,   65,   35,   35,   35,   34,   35,   95,   68,
   35,    0,   35,   35,    0,   11,   35,   35,   33,   12,
   33,   33,    0,   13,    0,    0,   35,   33,   33,   33,
   15,    0,   33,   33,   33,   33,   31,    0,   31,   31,
    0,    0,    0,    0,    0,   31,   31,   31,    0,    0,
   31,   31,   31,   31,   32,    0,   32,   32,    0,    0,
    0,    0,    0,   32,   32,   32,    0,    0,   32,   32,
   32,   32,   10,   11,    0,    0,    0,   12,    0,    0,
    0,   13,    2,   14,    0,    3,    9,    9,   15,    0,
    0,    9,    0,   21,   21,    9,    9,    9,    0,    9,
    0,    9,    9,   76,   76,    0,   21,    0,    0,   11,
    0,    0,    0,   12,    0,    0,   76,   13,    9,   21,
   60,    9,    9,    0,   15,    0,    9,    0,   74,   76,
    9,    9,    9,    0,    9,   10,   11,    9,    0,    0,
   12,    0,    0,    0,   13,    2,   14,    0,    3,    0,
   67,   15,    0,    9,   76,   77,    0,    0,    0,    0,
    0,    0,   78,   79,    0,    0,   80,   81,   82,   83,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
    0,    6,    6,  256,   90,  256,   14,   14,   43,  256,
    0,  270,  257,  258,  257,  258,  256,  257,  258,  276,
  276,  275,  288,  130,  278,  279,  283,   35,   35,  282,
  137,   38,  276,  284,  279,    0,  279,  284,   45,  279,
   45,   45,  128,   78,   79,   80,   81,   82,   83,  294,
  276,  256,  257,  258,  277,  256,  256,  257,  280,  281,
  256,  257,  278,  279,   71,  261,   71,   71,  256,  265,
  266,  267,  273,  269,  279,  271,  272,  257,  266,  256,
  257,  269,   89,   90,  261,  278,  279,  257,  265,  266,
  267,  258,  269,  289,  285,  272,  280,  281,  263,  264,
  107,  278,  279,   76,   77,  124,  125,   84,   85,  126,
  127,  118,  285,  118,  118,  256,  282,  124,  125,  126,
  127,  128,  274,  130,  256,  257,  257,  289,  276,  261,
  137,  257,  262,  265,  266,  267,  289,  269,  258,  264,
  272,  288,  274,  275,  256,  257,  278,  279,  282,  261,
  285,  274,  276,  265,  266,  267,  288,  269,  276,    0,
  272,   44,  274,  275,  256,  257,  278,  279,  285,  261,
  285,  285,  285,  265,  266,  267,  288,  269,   71,  285,
  272,   -1,  274,  275,   -1,  257,  278,  279,  276,  261,
  278,  279,   -1,  265,   -1,   -1,  288,  285,  286,  287,
  272,   -1,  290,  291,  292,  293,  276,   -1,  278,  279,
   -1,   -1,   -1,   -1,   -1,  285,  286,  287,   -1,   -1,
  290,  291,  292,  293,  276,   -1,  278,  279,   -1,   -1,
   -1,   -1,   -1,  285,  286,  287,   -1,   -1,  290,  291,
  292,  293,  256,  257,   -1,   -1,   -1,  261,   -1,   -1,
   -1,  265,  266,  267,   -1,  269,  256,  257,  272,   -1,
   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,  269,
   -1,  271,  272,  263,  264,   -1,  276,   -1,   -1,  257,
   -1,   -1,   -1,  261,   -1,   -1,  276,  265,  288,  289,
  268,  256,  257,   -1,  272,   -1,  261,   -1,  288,  289,
  265,  266,  267,   -1,  269,  256,  257,  272,   -1,   -1,
  261,   -1,   -1,   -1,  265,  266,  267,   -1,  269,   -1,
  271,  272,   -1,  288,  278,  279,   -1,   -1,   -1,   -1,
   -1,   -1,  286,  287,   -1,   -1,  290,  291,  292,  293,
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
"factorLOOP : identificador",
"factorLOOP : CTEINT",
"factorLOOP : MINUN CTEINT",
"factor : identificador",
"factor : constant",
"factor : MINUN CTEINT",
"constant : CTEINT",
"constant : CTEUL",
"sentenciaIF : IF LEFTPARENTHESIS condicionIF RIGHTPARENTHESIS THEN cuerpoIF",
"sentenciaIF : IF error",
"cuerpoIF : bloquesentenciasTHEN ENDIF",
"cuerpoIF : bloquesentenciasTHEN ELSE bloquesentencias ENDIF",
"bloquesentenciasTHEN : bloquesentencias",
"sentenciaLOOP : LOOP FROM asignacionLOOP TO expresionLOOP BY expresionLOOP bloquesentencias",
"sentenciaLOOP : LOOP FROM asignacionLOOP TO expresionLOOP BY error",
"sentenciaLOOP : LOOP FROM asignacionLOOP TO error",
"sentenciaLOOP : LOOP error",
"sentenciaPRINT : PRINT LEFTPARENTHESIS STRING RIGHTPARENTHESIS",
"sentenciaPRINT : PRINT error",
"condicionIF : condicion",
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
"sentenciaMY : MY listavariablesMY",
"listavariablesMY : listavariablesMY ID",
"listavariablesMY : ID",
};

//#line 290 "Parser.y"
private LexicAnalyzer lexicAnalyzer;
private CAMerror SyntaxError;
private SymbolsTable symbolsTable;
private SyntacticLogger synlog;

private List pInv= new ArrayList();
private Stack<Integer> pila = new Stack<Integer>();
private int nro_p;

private Vector<Integer> IDlist = new Vector<Integer>();
private String currentType = new String();

private Scope myScope = new Scope("0", null);


public SyntacticLogger getSynLog() {return this.synlog;}
public CAMerror getSyntaxError(){return SyntaxError;}

private void completar(int nro_p_inc, int num) {
    pInv.set(nro_p_inc, num);
}

private int generar(String text) {
    pInv.add(text);
    return pInv.size()-1;
}

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

private void imprimirPolaca(){
    for (int i=0; i<pInv.size();i++)
    {                    
        if (!(pInv.get(i) instanceof String))
        {
            if (((int)pInv.get(i))>256)
                System.out.print(symbolsTable.getEntry((int)pInv.get(i)).getLexema()+" ");
        }
    }
}
//#line 478 "Parser.java"
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
//#line 28 "Parser.y"
{System.out.println(pInv);imprimirPolaca();
                             }
break;
case 3:
//#line 31 "Parser.y"
{SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine());}
break;
case 6:
//#line 38 "Parser.y"
{}
break;
case 8:
//#line 42 "Parser.y"
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
//#line 52 "Parser.y"
{SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine());}
break;
case 10:
//#line 55 "Parser.y"
{int pointer = val_peek(0).tok.getPointer(); IDlist.addElement(pointer);}
break;
case 11:
//#line 56 "Parser.y"
{int pointer = val_peek(0).tok.getPointer(); IDlist.addElement(pointer);}
break;
case 12:
//#line 57 "Parser.y"
{SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine());}
break;
case 13:
//#line 60 "Parser.y"
{currentType = "INTEG";}
break;
case 14:
//#line 61 "Parser.y"
{currentType = "ULONG";}
break;
case 21:
//#line 74 "Parser.y"
{SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine());}
break;
case 22:
//#line 77 "Parser.y"
{synlog.addLog("Asignation",lexicAnalyzer.getLine());}
break;
case 23:
//#line 78 "Parser.y"
{synlog.addLog("IF",lexicAnalyzer.getLine());}
break;
case 24:
//#line 79 "Parser.y"
{synlog.addLog("LOOP",lexicAnalyzer.getLine());}
break;
case 25:
//#line 80 "Parser.y"
{synlog.addLog("PRINT",lexicAnalyzer.getLine());}
break;
case 26:
//#line 83 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 27:
//#line 85 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 28:
//#line 88 "Parser.y"
{  
                            int pointer = val_peek(0).tok.getPointer();
                             
                            
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            Token t = val_peek(0).tok;
                            entry.addScope(myScope.getScopeSuffix());                                                        
                            boolean isInScope = symbolsTable.inScope(pointer);
                            if (!isInScope) {
                                SyntaxError.addLog("Variable not declared",lexicAnalyzer.getLine());
                                symbolsTable.removeEntry(pointer);
                            }
                            pointer = t.getPointer();
                            pInv.add(pointer);
                            yyval = val_peek(0);}
break;
case 29:
//#line 105 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                        pInv.add(toAdd);}
break;
case 30:
//#line 107 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine());}
break;
case 31:
//#line 110 "Parser.y"
{ String toAdd = val_peek(1).tok.getValue();
                                                pInv.add(toAdd);
                                                }
break;
case 32:
//#line 113 "Parser.y"
{ String toAdd = val_peek(1).tok.getValue();
                                                pInv.add(toAdd);}
break;
case 34:
//#line 118 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                        pInv.add(toAdd);}
break;
case 35:
//#line 120 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                        pInv.add(toAdd);}
break;
case 37:
//#line 125 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                pInv.add(toAdd);}
break;
case 38:
//#line 127 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                pInv.add(toAdd);}
break;
case 40:
//#line 132 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 41:
//#line 134 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 44:
//#line 140 "Parser.y"
{  int pointer = val_peek(0).tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 45:
//#line 146 "Parser.y"
{
                                val_peek(0).tok.setValue("-"+val_peek(0).tok.getValue());                                
                                int pointer = val_peek(0).tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 48:
//#line 160 "Parser.y"
{                                    
                                val_peek(0).tok.setValue("-"+val_peek(0).tok.getValue());
                                int pointer = val_peek(0).tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "-"+entry.getLexema();
                                entry.setLexema(temp);
                                temp = "INTEG";
                                entry.setSType(temp);
                                }
break;
case 49:
//#line 172 "Parser.y"
{
                                int pointer = val_peek(0).tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "INTEG";
                                entry.setSType(temp);
                            }
break;
case 50:
//#line 179 "Parser.y"
{
                                int pointer = val_peek(0).tok.getPointer();
                                pInv.add(pointer);
                                SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                                String temp = "ULONG";
                                entry.setSType(temp);
                            }
break;
case 51:
//#line 188 "Parser.y"
{int nro_p_inc = pila.pop(); completar(nro_p_inc, nro_p + 1);}
break;
case 52:
//#line 189 "Parser.y"
{SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine());}
break;
case 55:
//#line 196 "Parser.y"
{int nro_p_inc = pila.pop(); completar(nro_p_inc, nro_p + 3); nro_p = generar(" "); pila.push(nro_p); nro_p = generar("BI");}
break;
case 59:
//#line 202 "Parser.y"
{SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine());}
break;
case 61:
//#line 206 "Parser.y"
{SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine());}
break;
case 62:
//#line 209 "Parser.y"
{nro_p = generar(" "); pila.push(nro_p); nro_p = generar("BF");}
break;
case 63:
//#line 212 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 64:
//#line 214 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 65:
//#line 216 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 66:
//#line 218 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 67:
//#line 220 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 68:
//#line 222 "Parser.y"
{String toAdd = val_peek(1).tok.getValue();
                                                    pInv.add(toAdd);}
break;
case 69:
//#line 226 "Parser.y"
{         int number = myScope.getScopesContained()+1;
                                String numb = Integer.toString(number);
                                Scope currentScope = new Scope(numb, myScope);
                                myScope.push(currentScope);
                                myScope = currentScope;
                                }
break;
case 71:
//#line 234 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 72:
//#line 235 "Parser.y"
{synlog.addLog("Scope ends",lexicAnalyzer.getLine());}
break;
case 73:
//#line 236 "Parser.y"
{synlog.addLog("Empty scope ends",lexicAnalyzer.getLine());}
break;
case 74:
//#line 239 "Parser.y"
{myScope = myScope.getFather();}
break;
case 76:
//#line 240 "Parser.y"
{myScope = myScope.getFather();}
break;
case 82:
//#line 253 "Parser.y"
{  int pointer = val_peek(0).tok.getPointer(); 
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer); 
                            entry.addScope(myScope.getScopeSuffix());
                            Token t = entry.getToken();                                                        
                            boolean isInScope = symbolsTable.inScope(pointer);
                            if (!isInScope) {
                                SyntaxError.addLog("Variable not declared",lexicAnalyzer.getLine());
                                symbolsTable.removeEntry(pointer);
                            }
                                
                            else {
                                pointer = t.getPointer(); 
                                entry = symbolsTable.getEntry(pointer);
                                entry.setMyScope(myScope.getScopeSuffix());
                            }
                                
                        }
break;
case 83:
//#line 270 "Parser.y"
{  int pointer = val_peek(0).tok.getPointer(); 
                            SymbolsTableEntry entry = symbolsTable.getEntry(pointer);
                            Token t = entry.getToken();
                            entry.addScope(myScope.getScopeSuffix());                                                        
                            boolean isInScope = symbolsTable.inScope(pointer);
                            if (!isInScope) {
                                SyntaxError.addLog("Variable not declared",lexicAnalyzer.getLine());
                                symbolsTable.removeEntry(pointer);
                            }
                            else {
                                pointer = t.getPointer();
                                entry = symbolsTable.getEntry(pointer);
                                entry.setMyScope(myScope.getScopeSuffix());
                            }
                        }
break;
//#line 950 "Parser.java"
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
