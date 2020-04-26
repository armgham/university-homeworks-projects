package cup.example;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

%%

%class Lexer
%implements sym
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

mathematicalOperator = \+|\-|\*|\/|expt|quotient|remainder
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
relationalOperator =\<|\>|\<\=|\>\=|\=
operator = {mathematicalOperators}|{logicOperators}
comment = \;.*


%eofval{
    return symbolFactory.newSymbol("EOF",sym.EOF);
%eofval}

%state CODESEG

%%  

<YYINITIAL> {
  {whitespace}  {  }

  "define"                  { return symbolFactory.newSymbol("DEFINE", DEFINE); }
  "if"                      { return symbolFactory.newSymbol("IF", IF); }
  "do"                      { return symbolFactory.newSymbol("DO", DO); }
  "display"                 { return symbolFactory.newSymbol("DISPLAY", DISPLAY); }
  "newline"                 { return symbolFactory.newSymbol("NEW_LINE", NEW_LINE); }
  
  {string}                  { return symbolFactory.newSymbol("STRING_LITERAL", STRING_LITERAL, yytext()); }
  
  
  {boolean}                 { return symbolFactory.newSymbol("BOOLEAN_LITERAL", BOOLEAN_LITERAL, yytext()); }
  
  
  
  {integer}                 { return symbolFactory.newSymbol("INTEGER_LITERAL", INTEGER_LITERAL, new Integer(yytext())); }
  {float}					{ return symbolFactory.newSymbol("FLOAT_LITERAL", FLOAT_LITERAL, new Float(yytext())); }
  {relationalOperator}      { return symbolFactory.newSymbol("RELATIONAL_OPERATOR", RELATIONAL_OPERATOR, yytext()); }
  {mathematicalOperator}    { return symbolFactory.newSymbol("MATHEMATICAL_OPERATOR", MATHEMATICAL_OPERATOR, yytext()); }
  "("                       { return symbolFactory.newSymbol("LPAREN", LPAREN); }
  ")"                       { return symbolFactory.newSymbol("RPAREN", RPAREN); }
  
  {identifier}              { return symbolFactory.newSymbol("IDENTIFIER", IDENTIFIER, yytext()); }
  
  
}



// error fallback
.|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }
