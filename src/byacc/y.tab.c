#ifndef lint
static const char yysccsid[] = "@(#)yaccpar	1.9 (Berkeley) 02/21/93";
#endif

#define YYBYACC 1
#define YYMAJOR 1
#define YYMINOR 9
#define YYPATCH 20140101

#define YYEMPTY        (-1)
#define yyclearin      (yychar = YYEMPTY)
#define yyerrok        (yyerrflag = 0)
#define YYRECOVERING() (yyerrflag != 0)

#define YYPREFIX "yy"

#define YYPURE 0


#ifndef YYSTYPE
typedef int YYSTYPE;
#endif

/* compatibility with bison */
#ifdef YYPARSE_PARAM
/* compatibility with FreeBSD */
# ifdef YYPARSE_PARAM_TYPE
#  define YYPARSE_DECL() yyparse(YYPARSE_PARAM_TYPE YYPARSE_PARAM)
# else
#  define YYPARSE_DECL() yyparse(void *YYPARSE_PARAM)
# endif
#else
# define YYPARSE_DECL() yyparse(void)
#endif

/* Parameters sent to lex. */
#ifdef YYLEX_PARAM
# define YYLEX_DECL() yylex(void *YYLEX_PARAM)
# define YYLEX yylex(YYLEX_PARAM)
#else
# define YYLEX_DECL() yylex(void)
# define YYLEX yylex()
#endif

/* Parameters sent to yyerror. */
#ifndef YYERROR_DECL
#define YYERROR_DECL() yyerror(const char *s)
#endif
#ifndef YYERROR_CALL
#define YYERROR_CALL(msg) yyerror(msg)
#endif

extern int YYPARSE_DECL();

#define ID 257
#define CTE 258
#define ERROR 259
#define FINAL 260
#define IF 261
#define THEN 262
#define ELSE 263
#define ENDIF 264
#define PRINT 265
#define INT 266
#define BEGIN 267
#define END 268
#define UNSIGNED 269
#define LONG 270
#define MY 271
#define LOOP 272
#define FROM 273
#define TO 274
#define BY 275
#define OPERATOR 276
#define STRING 277
#define YYERRCODE 256
static const short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    4,    4,    6,    6,    6,
    5,    5,    3,    3,    7,    7,    8,    8,    8,    9,
    9,    9,    9,   11,   11,   15,   15,   15,   16,   16,
   16,   17,   17,   12,   12,   12,   13,   13,   14,   14,
   18,   18,   18,   18,   18,   10,   10,   10,   19,   19,
   20,   20,   21,
};
static const short yylen[] = {                            2,
    1,    2,    1,    2,    1,    3,    1,    3,    1,    1,
    1,    2,    3,    1,    2,    1,    1,    1,    1,    2,
    2,    2,    2,    3,    2,    3,    3,    1,    3,    3,
    1,    1,    1,    7,    9,    2,    8,    2,    4,    2,
    4,    4,    4,    3,    3,    4,    3,    2,    2,    1,
    1,    2,    2,
};
static const short yydefred[] = {                         0,
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
static const short yydgoto[] = {                          4,
    5,    6,   17,    7,    8,   28,   35,   19,   20,   21,
   22,   23,   24,   25,   54,   55,   56,   58,   43,   44,
   45,
};
static const short yysindex[] = {                      -200,
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
static const short yyrindex[] = {                         0,
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
static const short yygindex[] = {                         0,
  -25,    0,    8,   19,    0,   77,    0,    0,   12,    0,
   79,    0,    0,    0,    9,   28,   17,    0,    0,   75,
    0,
};
#define YYTABLESIZE 295
static const short yytable[] = {                         24,
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
static const short yycheck[] = {                         59,
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
#define YYFINAL 4
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 277
#define YYTRANSLATE(a) ((a) > YYMAXTOKEN ? (YYMAXTOKEN + 1) : (a))
#if YYDEBUG
static const char *yyname[] = {

"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,
"';'","'<'","'='","'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,"'}'",0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,"ID","CTE","ERROR","FINAL","IF","THEN","ELSE","ENDIF",
"PRINT","INT","BEGIN","END","UNSIGNED","LONG","MY","LOOP","FROM","TO","BY",
"OPERATOR","STRING","illegal-symbol",
};
static const char *yyrule[] = {
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
#endif

int      yydebug;
int      yynerrs;

int      yyerrflag;
int      yychar;
YYSTYPE  yyval;
YYSTYPE  yylval;

/* define the initial stack-sizes */
#ifdef YYSTACKSIZE
#undef YYMAXDEPTH
#define YYMAXDEPTH  YYSTACKSIZE
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 10000
#define YYMAXDEPTH  10000
#endif
#endif

#define YYINITSTACKSIZE 200

typedef struct {
    unsigned stacksize;
    short    *s_base;
    short    *s_mark;
    short    *s_last;
    YYSTYPE  *l_base;
    YYSTYPE  *l_mark;
} YYSTACKDATA;
/* variables for the parser stack */
static YYSTACKDATA yystack;

#if YYDEBUG
#include <stdio.h>		/* needed for printf */
#endif

#include <stdlib.h>	/* needed for malloc, etc */
#include <string.h>	/* needed for memset */

/* allocate initial stack or double stack size, up to YYMAXDEPTH */
static int yygrowstack(YYSTACKDATA *data)
{
    int i;
    unsigned newsize;
    short *newss;
    YYSTYPE *newvs;

    if ((newsize = data->stacksize) == 0)
        newsize = YYINITSTACKSIZE;
    else if (newsize >= YYMAXDEPTH)
        return -1;
    else if ((newsize *= 2) > YYMAXDEPTH)
        newsize = YYMAXDEPTH;

    i = (int) (data->s_mark - data->s_base);
    newss = (short *)realloc(data->s_base, newsize * sizeof(*newss));
    if (newss == 0)
        return -1;

    data->s_base = newss;
    data->s_mark = newss + i;

    newvs = (YYSTYPE *)realloc(data->l_base, newsize * sizeof(*newvs));
    if (newvs == 0)
        return -1;

    data->l_base = newvs;
    data->l_mark = newvs + i;

    data->stacksize = newsize;
    data->s_last = data->s_base + newsize - 1;
    return 0;
}

#if YYPURE || defined(YY_NO_LEAKS)
static void yyfreestack(YYSTACKDATA *data)
{
    free(data->s_base);
    free(data->l_base);
    memset(data, 0, sizeof(*data));
}
#else
#define yyfreestack(data) /* nothing */
#endif

#define YYABORT  goto yyabort
#define YYREJECT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR  goto yyerrlab

int
YYPARSE_DECL()
{
    int yym, yyn, yystate;
#if YYDEBUG
    const char *yys;

    if ((yys = getenv("YYDEBUG")) != 0)
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = YYEMPTY;
    yystate = 0;

#if YYPURE
    memset(&yystack, 0, sizeof(yystack));
#endif

    if (yystack.s_base == NULL && yygrowstack(&yystack)) goto yyoverflow;
    yystack.s_mark = yystack.s_base;
    yystack.l_mark = yystack.l_base;
    yystate = 0;
    *yystack.s_mark = 0;

yyloop:
    if ((yyn = yydefred[yystate]) != 0) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = YYLEX) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = yyname[YYTRANSLATE(yychar)];
            printf("%sdebug: state %d, reading %d (%s)\n",
                    YYPREFIX, yystate, yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("%sdebug: state %d, shifting to state %d\n",
                    YYPREFIX, yystate, yytable[yyn]);
#endif
        if (yystack.s_mark >= yystack.s_last && yygrowstack(&yystack))
        {
            goto yyoverflow;
        }
        yystate = yytable[yyn];
        *++yystack.s_mark = yytable[yyn];
        *++yystack.l_mark = yylval;
        yychar = YYEMPTY;
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;

    yyerror("syntax error");

    goto yyerrlab;

yyerrlab:
    ++yynerrs;

yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yystack.s_mark]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("%sdebug: state %d, error recovery shifting\
 to state %d\n", YYPREFIX, *yystack.s_mark, yytable[yyn]);
#endif
                if (yystack.s_mark >= yystack.s_last && yygrowstack(&yystack))
                {
                    goto yyoverflow;
                }
                yystate = yytable[yyn];
                *++yystack.s_mark = yytable[yyn];
                *++yystack.l_mark = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("%sdebug: error recovery discarding state %d\n",
                            YYPREFIX, *yystack.s_mark);
#endif
                if (yystack.s_mark <= yystack.s_base) goto yyabort;
                --yystack.s_mark;
                --yystack.l_mark;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = yyname[YYTRANSLATE(yychar)];
            printf("%sdebug: state %d, error recovery discards token %d (%s)\n",
                    YYPREFIX, yystate, yychar, yys);
        }
#endif
        yychar = YYEMPTY;
        goto yyloop;
    }

yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("%sdebug: state %d, reducing by rule %d (%s)\n",
                YYPREFIX, yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    if (yym)
        yyval = yystack.l_mark[1-yym];
    else
        memset(&yyval, 0, sizeof yyval);
    switch (yyn)
    {
case 3:
#line 13 "Parser.y"
	{SyntaxError.addLog("Invalid block structure",lexicAnalyzer.getLine())}
break;
case 7:
#line 20 "Parser.y"
	{SyntaxError.addLog("Invalid declarative sentence",lexicAnalyzer.getLine())}
break;
case 10:
#line 25 "Parser.y"
	{SyntaxError.addLog("Invalid declaration of variables list",lexicAnalyzer.getLine())}
break;
case 19:
#line 42 "Parser.y"
	{SyntaxError.addLog("Invalid simple executable",lexicAnalyzer.getLine())}
break;
case 25:
#line 52 "Parser.y"
	{SyntaxError.addLog("Invalid assigment",lexicAnalyzer.getLine())}
break;
case 36:
#line 69 "Parser.y"
	{SyntaxError.addLog("Invalid use of IF",lexicAnalyzer.getLine())}
break;
case 38:
#line 74 "Parser.y"
	{SyntaxError.addLog("Invalid use of LOOP",lexicAnalyzer.getLine())}
break;
case 40:
#line 78 "Parser.y"
	{SyntaxError.addLog("Invalid use of PRINT",lexicAnalyzer.getLine())}
break;
case 41:
#line 81 "Parser.y"
	{}
break;
case 42:
#line 82 "Parser.y"
	{}
break;
case 43:
#line 83 "Parser.y"
	{}
break;
case 44:
#line 84 "Parser.y"
	{}
break;
case 45:
#line 85 "Parser.y"
	{}
break;
#line 571 "y.tab.c"
    }
    yystack.s_mark -= yym;
    yystate = *yystack.s_mark;
    yystack.l_mark -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("%sdebug: after reduction, shifting from state 0 to\
 state %d\n", YYPREFIX, YYFINAL);
#endif
        yystate = YYFINAL;
        *++yystack.s_mark = YYFINAL;
        *++yystack.l_mark = yyval;
        if (yychar < 0)
        {
            if ((yychar = YYLEX) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = yyname[YYTRANSLATE(yychar)];
                printf("%sdebug: state %d, reading %d (%s)\n",
                        YYPREFIX, YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("%sdebug: after reduction, shifting from state %d \
to state %d\n", YYPREFIX, *yystack.s_mark, yystate);
#endif
    if (yystack.s_mark >= yystack.s_last && yygrowstack(&yystack))
    {
        goto yyoverflow;
    }
    *++yystack.s_mark = (short) yystate;
    *++yystack.l_mark = yyval;
    goto yyloop;

yyoverflow:
    yyerror("yacc stack overflow");

yyabort:
    yyfreestack(&yystack);
    return (1);

yyaccept:
    yyfreestack(&yystack);
    return (0);
}
