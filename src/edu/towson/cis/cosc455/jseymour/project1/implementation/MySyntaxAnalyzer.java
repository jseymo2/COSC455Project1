package edu.towson.cis.cosc455.jseymour.project1.implementation;

//import edu.towson.cis.cosc455.jseymour.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer// implements SyntaxAnalyzer
{

	public static void markdown() throws CompilerException
	{
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.DOCB))
		{
			pushToken();
			variableDefine();
			head();
			body();
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.DOCE))
			{
				// pushToken();
				// check that there is no more text
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.DOCE + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// error
			// invalid start
			// or empty file
		}
	}

	public static void head() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.HEAD))
		{
			pushToken();
			title();
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.HEAD))
			{
				pushToken();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.HEAD + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, head is optional
		}
	}

	public static void title() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.TITLEB))
		{
			pushToken();
			while(MyLexicalAnalyzer.isValidText())
			{
				pushToken();
			}
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.TITLEE))
			{
				pushToken();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.TITLEE + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, title is optional
		}
	}

	public static void body() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.LISTITEMB) || 
				MyCompiler.currentToken.equalsIgnoreCase(Token.AUDIO) || 
				MyCompiler.currentToken.equalsIgnoreCase(Token.VIDEO) || 
				MyCompiler.currentToken.equalsIgnoreCase(Token.NEWLINE) || 
				MyCompiler.currentToken.equalsIgnoreCase(Token.USEB) || 
				MyCompiler.currentToken.equalsIgnoreCase(Token.BOLD) || 
				MyCompiler.currentToken.equalsIgnoreCase(Token.ITALICS) || 
				MyCompiler.currentToken.equalsIgnoreCase(Token.LINKB) ||
				MyLexicalAnalyzer.isValidText())
		{
			innerText();
			body();
		}
		else
		{
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.PARAB))
			{
				paragraph();
				body();
			}
			else
			{
				// no error, body optional
			}
		}
	}

	public static void paragraph() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.PARAB))
		{
			pushToken();
			variableDefine();
			innerText();
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.PARAE))
			{
				pushToken();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.PARAE + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			throw new CompilerException("Syntax Error: Expected " + Token.PARAB + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
		}
	}

	public static void innerText() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.LISTITEMB))
		{
			listitem();
			innerText();
		}
		else
		{
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.AUDIO))
			{
				audio();
				innerText();
			}
			else
			{
				if(MyCompiler.currentToken.equalsIgnoreCase(Token.VIDEO))
				{
					video();
					innerText();
				}
				else
				{
					if(MyCompiler.currentToken.equalsIgnoreCase(Token.NEWLINE))
					{
						newline();
						innerText();
					}
					else
					{
						if(MyCompiler.currentToken.equalsIgnoreCase(Token.USEB))
						{
							variableUse();
							innerItem();
						}
						else
						{
							if(MyCompiler.currentToken.equalsIgnoreCase(Token.BOLD))
							{
								bold();
								innerItem();
							}
							else
							{
								if(MyCompiler.currentToken.equalsIgnoreCase(Token.ITALICS))
								{
									italics();
									innerItem();
								}
								else
								{
									if(MyCompiler.currentToken.equalsIgnoreCase(Token.LINKB))
									{
										link();
										innerItem();
									}
									else
									{
										if(MyLexicalAnalyzer.isValidText())
										{
											pushToken();
											innerItem();
										}
										else
										{
											// no error, all optional
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public static void variableDefine() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.DEFB))
		{
			pushToken();
			while(MyLexicalAnalyzer.isValidText())
			{
				pushToken();
			}
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.EQSIGN))
			{
				pushToken();
				while(MyLexicalAnalyzer.isValidText())
				{
					pushToken();
				}
				if(MyCompiler.currentToken.equalsIgnoreCase(Token.DEFUSEE))
				{
					pushToken();
					variableDefine();
				}
				else
				{
					throw new CompilerException("Syntax Error: Expected " + Token.DEFUSEE + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
				}
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.EQSIGN + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, definitions optional
		}
	}

	public static void variableUse() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.USEB))
		{
			pushToken();
			while(MyLexicalAnalyzer.isValidText())
			{
				pushToken();
			}
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.DEFUSEE))
			{
				pushToken();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.DEFUSEE + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, variables optional
		}
	}

	public static void bold() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.BOLD))
		{
			pushToken();
			while(MyLexicalAnalyzer.isValidText())
			{
				pushToken();
			}
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.BOLD))
			{
				pushToken();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.BOLD + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, bold optional
		}
	}

	public static void italics() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.ITALICS))
		{
			pushToken();
			while(MyLexicalAnalyzer.isValidText())
			{
				pushToken();
			}
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.ITALICS))
			{
				pushToken();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.ITALICS + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, italics optional
		}
	}

	public static void listitem() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.LISTITEMB))
		{
			pushToken();
			innerItem();
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.LISTITEME))
			{
				pushToken();
				listitem();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.LISTITEME + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, listitem optional
		}
	}

	public static void innerItem() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.USEB))
		{
			variableUse();
			innerItem();
		}
		else
		{
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.BOLD))
			{
				bold();
				innerItem();
			}
			else
			{
				if(MyCompiler.currentToken.equalsIgnoreCase(Token.ITALICS))
				{
					italics();
					innerItem();
				}
				else
				{
					if(MyCompiler.currentToken.equalsIgnoreCase(Token.LINKB))
					{
						link();
						innerItem();
					}
					else
					{
						if(MyLexicalAnalyzer.isValidText())
						{
							pushToken();
							innerItem();
						}
						else
						{
							// no error, innerItem optional
						}
					}
				}
			}
		}
	}

	public static void link() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.LINKB))
		{
			pushToken();
			while(MyLexicalAnalyzer.isValidText())
			{
				pushToken();
			}
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.LINKE))
			{
				pushToken();
				address();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.LINKE + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			// no error, link optional
		}
	}

	public static void audio() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.AUDIO))
		{
			pushToken();
			address();
		}
		else
		{
			// no error, audio optional
		}
	}

	public static void video() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.VIDEO))
		{
			pushToken();
			address();
		}
		else
		{
			// no error, video optional
		}
	}
	
	public static void address() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.ADDRESSB))
		{
			pushToken();
			while(MyLexicalAnalyzer.isValidText())
			{
				pushToken();
			}
			if(MyCompiler.currentToken.equalsIgnoreCase(Token.ADDRESSE))
			{
				pushToken();
			}
			else
			{
				throw new CompilerException("Syntax Error: Expected " + Token.ADDRESSE + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
			}
		}
		else
		{
			throw new CompilerException("Syntax Error: Expected " + Token.ADDRESSB + ", got " + MyCompiler.currentToken + ", at line " + MyCompiler.currentLine + ".");
		}
	}

	public static void newline() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.NEWLINE))
		{
			pushToken();
		}
		else
		{
			// no error, newline optional
		}
	}
	
	private static void pushToken()
	{
		MyCompiler.parseTree.push(MyCompiler.currentToken);
		MyLexicalAnalyzer.getNextToken();
	}

}
