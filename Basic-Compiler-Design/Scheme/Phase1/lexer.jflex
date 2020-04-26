package cup.example;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

%%

%class Lexer
%public
%unicode
%line
%column
%cup
%char
%{
	

    public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(is);
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    }
    
    private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
		return symbolFactory.newSymbol(name, code,
						new Location(yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    public Symbol symbol(String name, int code, String lexem){
	return symbolFactory.newSymbol(name, code, 
						new Location(yyline+1, yycolumn +1, yychar), 
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength()), lexem);
    }
    
    protected void emit_warning(String message){
    	System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
%}

newline    = \r | \n | \r\n
whitespace = [ \t\f]+ | {newline}+

keywords = define|set\!|display|let|lambda|cons|car|cdr|list|quote
mathematicalOperators = \+|\-|\*|\/|expt|quotient|remainder
logicOperators = not|and|or
string = \"([^\"])*\"
character = \#\\.|\#\\x[0-9a-fA-F]{1,5}

identifier=([^\(\)\\\"\. \n\t\r\f\[\]\#\+\-\{\}\;])([^\(\)\[\]\{\}\;\t \f\n\r]*)
delimiter=\(|\)|\{|\}|\[|\]|\||\'|\"

integer = [+-]?[0-9]+
binary = \#[bB][01]+
octal = \#[oO][0-7]+
hexadecimal = \#[xX][0-9a-fA-F]+
float = [-+]?[0-9]*\.[0-9]+|[-+]?[0-9]+\.[0-9]*
real = ({float}|{integer})[eE][-+]?[0-9]+
rational = {integer}\/0*[1-9][0-9]*
complex = ({integer}|{float}|{real}|{rational})[+-]({integer}|{float}|{real}|{rational})i

boolean = \#t|\#f

number = {integer}|{binary}|{octal}|{hexadecimal}|{float}|{real}|{rational}|{complex}

operator = {mathematicalOperators}|{logicOperators}
comment = \;.*


%eofval{
    System.exit(0);
%eofval}

%state CODESEG

%%  

<YYINITIAL> {

  {whitespace}                 { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "whitespace"); }
  {keywords}                   { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "keywords--->" + yytext()); }
  {operator}                   { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "operator--->" + yytext()); }
  {number}                     { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "number  --->" + yytext()); }
  {string}                     { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "string  --->" + yytext()); }
  {boolean}                    { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "boolean --->" + yytext()); }
  {character}                  { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "character-->" + yytext()); }
  {delimiter}                  { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "delimiter-->" + yytext()); }
  {identifier}                 { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "identifier->" + yytext()); }
  {comment}                    { System.out.println("Line" + (yyline+1) + ",\tColumn " + (yycolumn+1) + "\t: " + "comment --->" + yytext()); }
}



// error fallback
.|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }
