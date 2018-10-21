import java_cup.runtime.*;

%%

%class Lexer
%line
%column
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
INTEGER			= -? (0 | [1-9][0-9]*)
LETTER			= [a-z] | [A-Z]
ALPHANUM		= {LETTER} | [0-9]
STRING			= [\"]{ALPHANUM}*[\"]
ID			= {LETTER}+{ALPHANUM}*
InputCar		= [^\r\n]
EndofLineComment	= "//" {InputCar}* {LineTerminator}?
OldSchoolComment	= "/*"( [^"*"] | "*"[^"/"] )*"*/"
%%


<YYINITIAL> {
"("				{ return symbol(TokenNames.LPAREN); }
")"				{ return symbol(TokenNames.RPAREN); }
"["				{ return symbol(TokenNames.LBRACK); }
"]"				{ return symbol(TokenNames.RBRACK); }
"{"				{ return symbol(TokenNames.LBRACE); }
"}"				{ return symbol(TokenNames.RBRACE); }
"nil"				{ return symbol(TokenNames.NIL); }
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
"array"				{ return symbol(TokenNames.ARRAY); }
"class"				{ return symbol(TokenNames.CLASS); }
"extends"			{ return symbol(TokenNames.EXTENDS); }
"return"			{ return symbol(TokenNames.RETURN); }
"while"				{ return symbol(TokenNames.WHILE); }
"if"				{ return symbol(TokenNames.IF); }
"new"				{ return symbol(TokenNames.NEW); }
{EndofLineComment}		{ return symbol(TokenNames.COMMENT); }
{OldSchoolComment}		{ return symbol(TokenNames.COMMENT); }
{INTEGER}			{ return symbol(TokenNames.INT, new Integer(yytext())); }
{ID}				{ return symbol(TokenNames.ID, new String(yytext())); }   
{STRING}			{ return symbol(TokenNames.STRING, new String(yytext())); }
{WhiteSpace}			{ /* just skip what was found, do nothing */ }
<<EOF>>				{ return symbol(TokenNames.EOF); }
}

