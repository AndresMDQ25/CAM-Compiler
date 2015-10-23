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




/*





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
public final static short OPERATOR=276;
public final static short STRING=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    4,    4,    6,    6,    6,
    5,    5,    3,    3,    7,    7,    8,    8,    8,    9,
    9,    9,    9,   11,   11,   15,   15,   15,   16,   16,
   16,   17,   17,   12,   12,   12,   13,   13,   14,   14,
   18,   18,   18,   18,   18,   10,   10,   10,   19,   19,
   20,   20,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    1,    3,    1,    3,    1,    1,
    1,    2,    3,    1,    2,    1,    1,    1,    1,    2,
    2,    2,    2,    3,    2,    3,    3,    1,    3,    3,
    1,    1,    1,    7,    9,    2,    8,    2,    4,    2,
    4,    4,    4,    3,    3,    4,    3,    2,    2,    1,
    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,   11,    0,    0,    1,    0,    5,    0,   12,    0,
    0,    0,    0,    0,    0,    0,    2,    4,   14,   18,
   17,    0,    0,    0,    0,   10,    9,    0,   25,    0,
   36,    0,   40,    0,    0,   16,   38,    0,    0,   48,
    0,   51,    0,   50,    0,   20,   21,   22,   23,    6,
    0,   32,   33,    0,    0,   31,    0,    0,    0,   13,
   15,    0,    0,   47,    0,   49,   52,    8,    0,    0,
    0,    0,    0,    0,    0,    0,   39,    0,   46,    0,
    0,   29,   30,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   34,    0,    0,   37,
   35,
};
final static short yydgoto[] = {                          4,
    5,    6,   17,    7,    8,   28,   35,   19,   20,   21,
   22,   23,   24,   25,   54,   55,   56,   58,   43,   44,
   45,
};
final static short yysindex[] = {                      -200,
    0,    0, -237,    0,    0,  -74,    0, -212,    0,    0,
  -42,  -33,  -28, -215, -225, -108,    0,    0,    0,    0,
    0,  -22,  -19,   13,   31,    0,    0,   -1,    0, -183,
    0, -183,    0, -218, -166,    0,    0, -205, -212,    0,
  -58,    0,  -91,    0,   33,    0,    0,    0,    0,    0,
 -181,    0,    0,   25,   -8,    0,   -7,   37,   59,    0,
    0, -173,   61,    0,  -18,    0,    0,    0, -183, -183,
 -183, -183,   42,  -47,  -45, -154,    0, -183,    0,   -8,
   -8,    0,    0, -183, -183,   25, -183,   25, -200,  -35,
   25,   25,   25, -178, -183, -200,    0,  -40, -155,    0,
    0,
};
final static short yyrindex[] = {                         0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -59,  -39,    0,    0,    0,    0,    0,
    0,    0,   51,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -32,
   20,    0,    0,    0,    0,   70,    0,   71,    0,    0,
   72,   73,   74,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  -25,    0,    8,   19,    0,   77,    0,    0,   12,    0,
   79,    0,    0,    0,    9,   28,   17,    0,    0,   75,
    0,
};
final static int YYTABLESIZE=295;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         24,
   19,   28,   69,   28,   70,   28,   32,   69,   26,   70,
   26,   34,   26,   85,   16,   87,   40,    3,   30,   28,
   28,   28,   28,   41,   18,   36,   26,   26,   26,   26,
   37,   16,    9,   71,   42,   69,   46,   70,   72,   47,
   57,   11,   51,   26,   27,   12,   61,   38,   16,   13,
   65,   11,   75,   73,   74,    1,   15,   50,   59,   19,
   27,   42,   27,   94,   27,    2,   64,   69,    3,   70,
   99,   48,  100,   52,   53,   68,    3,   76,   27,   27,
   27,   27,   86,   88,   96,   97,   90,   82,   83,   49,
   11,   67,   91,   92,   12,   93,   80,   81,   13,   77,
   78,   60,   84,   98,   51,   15,   79,   89,  101,   53,
   44,   45,   41,   42,   43,   63,   62,   66,    0,    0,
    0,    0,    0,    7,    0,   19,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    7,    0,    0,    0,    0,    0,    0,   10,   11,    0,
    0,    0,   12,    0,    0,    0,   13,    2,   14,    0,
    3,    0,   39,   15,   10,   11,    0,    0,    0,   12,
    0,    0,    0,   13,    2,   14,    0,    3,    0,   39,
   15,   10,   11,    0,    0,    0,   12,    0,    0,    0,
   13,    2,   14,    0,    3,    0,    0,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   52,
   53,   52,   53,   29,   24,    1,   28,    0,    0,    0,
    0,    0,   31,   26,    0,    2,   28,   33,    3,   28,
    0,    0,    0,   26,   28,   28,   26,    0,    0,   95,
    0,   26,   26,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    7,    0,    0,
    0,    7,    0,   19,   19,    7,    7,    7,    0,    7,
    0,    7,    7,    7,    7,   27,    0,    0,    7,    0,
    3,    3,    7,    7,    7,   27,    7,    0,   27,    7,
    0,    0,    0,   27,   27,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   41,   43,   43,   45,   45,   40,   43,   41,   45,
   43,   40,   45,   61,  123,   61,  125,    0,   61,   59,
   60,   61,   62,   16,    6,   14,   59,   60,   61,   62,
  256,  123,  270,   42,   16,   43,   59,   45,   47,   59,
   32,  257,   44,  256,  257,  261,   35,  273,  123,  265,
   43,  257,   60,   61,   62,  256,  272,   59,  277,   59,
   41,   43,   43,   89,   45,  266,  125,   43,  269,   45,
   96,   59,   98,  257,  258,  257,   59,   41,   59,   60,
   61,   62,   74,   75,  263,  264,   78,   71,   72,   59,
  257,   59,   84,   85,  261,   87,   69,   70,  265,   41,
  274,  268,   61,   95,   44,  272,  125,  262,  264,   59,
   41,   41,   41,   41,   41,   39,   38,   43,   -1,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,
   -1,   -1,  261,   -1,   -1,   -1,  265,  266,  267,   -1,
  269,   -1,  271,  272,  256,  257,   -1,   -1,   -1,  261,
   -1,   -1,   -1,  265,  266,  267,   -1,  269,   -1,  271,
  272,  256,  257,   -1,   -1,   -1,  261,   -1,   -1,   -1,
  265,  266,  267,   -1,  269,   -1,   -1,  272,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  257,  258,  256,  274,  256,  256,   -1,   -1,   -1,
   -1,   -1,  256,  256,   -1,  266,  266,  256,  269,  269,
   -1,   -1,   -1,  266,  274,  275,  269,   -1,   -1,  275,
   -1,  274,  275,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,
   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,  269,
   -1,  271,  272,  256,  257,  256,   -1,   -1,  261,   -1,
  263,  264,  265,  266,  267,  266,  269,   -1,  269,  272,
   -1,   -1,   -1,  274,  275,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE","ERROR","FINAL","IF","THEN",
"ELSE","ENDIF","PRINT","INT","BEGIN","END","UNSIGNED","LONG","MY","LOOP","FROM",
"TO","BY","OPERATOR","STRING",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : declarativas ejecutables",
"bloque : error",
"declarativas : declarativas declarativa",
"declarativas : declarativa",
"declarativa : tipo listavariables ';'",
"declarativa : error",
"listavariables : listavariables ',' ID",
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
"ejecutable : asignacion ';'",
"ejecutable : sentenciaIF ';'",
"ejecutable : sentenciaLOOP ';'",
"ejecutable : sentenciaPRINT ';'",
"asignacion : ID '=' expresion",
"asignacion : ID error",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"sentenciaIF : IF '(' condicion ')' THEN bloque ENDIF",
"sentenciaIF : IF '(' condicion ')' THEN bloque ELSE bloque ENDIF",
"sentenciaIF : IF error",
"sentenciaLOOP : LOOP FROM asignacion TO expresion BY expresion bloque",
"sentenciaLOOP : LOOP error",
"sentenciaPRINT : PRINT '(' STRING ')'",
"sentenciaPRINT : PRINT error",
"condicion : expresion '=' '=' expresion",
"condicion : expresion '>' '=' expresion",
"condicion : expresion '<' '=' expresion",
"condicion : expresion '>' expresion",
"condicion : expresion '<' expresion",
"ambito : '{' declarativasambito ejecutables '}'",
"ambito : '{' ejecutables '}'",
"ambito : '{' '}'",
"declarativasambito : declarativasambito declarativaambito",
"declarativasambito : declarativaambito",
"declarativaambito : declarativa",
"declarativaambito : sentenciaMY ';'",
"sentenciaMY : MY listavariables",
};

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
int yyparse()
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
//#line 13 "Parser.y"
{SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine())}
break;
case 7:
//#line 20 "Parser.y"
{SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine())}
break;
case 10:
//#line 25 "Parser.y"
{SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine())}
break;
case 19:
//#line 42 "Parser.y"
{SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine())}
break;
case 25:
//#line 52 "Parser.y"
{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine())}
break;
case 36:
//#line 69 "Parser.y"
{SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine())}
break;
case 38:
//#line 74 "Parser.y"
{SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine())}
break;
case 40:
//#line 78 "Parser.y"
{SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine())}
break;
case 41:
//#line 81 "Parser.y"
{yyval = val_peek(3) == val_peek(1)}
break;
case 42:
//#line 82 "Parser.y"
{}
break;
case 43:
//#line 83 "Parser.y"
{}
break;
case 44:
//#line 84 "Parser.y"
{}
break;
case 45:
//#line 85 "Parser.y"
{}
break;
//#line 525 "Parser.java"
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
 */ /*
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */ /*
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */ 
/*
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
*/
//###############################################################



//}
//################### END OF CLASS ##############################
//