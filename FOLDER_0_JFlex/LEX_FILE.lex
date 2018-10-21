import java_cup.runtime.*;

%%

%class Lexer
%line
%cupsym TokenNames
%cup

%{
	private Symbol symbol(int type) { return new Symbol(type, yyline, yycolumn); }
	private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value); }
	public int getLine() { return yyline + 1; } 
	public int getTokenStartPosition() { return yycolumn + 1; } 
%}

LineTerminator		= \r|\n|\r\n
WhiteSpace		= {LineTerminator} | [ \t\f]
INTEGER			= 0 | [1-9][0-9]*
LETTER			= [a-z] | [A-Z]
ALPHANUM		= {LETTER} | [0-9]
STRING			= [\"]{ALPHANUM}*[\"]
ID			= {LETTER}+{ALPHANUM}*
InputCar		= [^\r\n]
EndofLineComment	= "//" {InputCar}* {LineTerminator}?
OldSchoolComment	= "/*"( [^"*"] | "*"[^"/"])*"*/"
%%


<YYINITIAL> {
"("				{ return symbol(TokenNames.LPAREN); }
")"				{ return symbol(TokenNames.RPAREN); }
"["				{ return symbol(TokenNames.LBRACK); }
"]"				{ return symbol(TokenNames.RBRACK); }
"{"				{ return symbol(TokenNames.LBRACE); }
"}"				{ return symbol(TokenNames.RBRACE); }
"NIL"				{ return symbol(TokenNames.NIL); }
"+"				{ return symbol(TokenNames.PLUS); }
"-"				{ return symbol(TokenNames.MINUS); }
"*"				{ return symbol(TokenNames.TIMES); }
"/"				{ return symbol(TokenNames.DIVIDE); }
","				{ return symbol(TokenNames.COMMA); }
"."				{ return symbol(TokenNames.DOT); }
";"				{ return symbol(TokenNames.SEMICOLON); }
":="				{ return symbol(TokenNames.ASSIGN); }
"="				{ return symbol(TokenNames.EQ); }
"<"				{ return symbol(TokenNames.LT); }
">"				{ return symbol(TokenNames.GT); }
"ARRAY"				{ return symbol(TokenNames.ARRAY); }
"CLASS"				{ return symbol(TokenNames.CLASS); }
"EXTENDS"			{ return symbol(TokenNames.EXTENDS); }
"RETURN"			{ return symbol(TokenNames.RETURN); }
"WHILE"				{ return symbol(TokenNames.WHILE); }
"IF"				{ return symbol(TokenNames.IF); }
"NEW"				{ return symbol(TokenNames.NEW); }
{INTEGER}			{ return symbol(TokenNames.INT, new Integer(yytext())); }
{STRING}			{ return symbol(TokenNames.STRING, new String(yytext())); }
{ID}				{ return symbol(TokenNames.ID, new String(yytext())); }   
{EndofLineComment}		{  return symbol(TokenNames.COMMENT); }
{OldSchoolComment}		{  return symbol(TokenNames.COMMENT); }
{WhiteSpace}			{ /* just skip what was found, do nothing */ }
<<EOF>>				{ return symbol(TokenNames.EOF); }
}

