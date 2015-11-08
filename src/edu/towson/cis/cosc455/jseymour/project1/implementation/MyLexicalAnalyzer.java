package edu.towson.cis.cosc455.jseymour.project1.implementation;

import java.io.IOException;

//import edu.towson.cis.cosc455.jseymour.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer //implements LexicalAnalyzer
{
	
	public static char nextCharacter = '\0';

	public static void getNextToken() {
		MyCompiler.currentToken = "";
		getCharacter();
		while(isSpace())
			getCharacter();
		while(!isSpace())
		{
			addCharacter();
		}
	}

	public static void getCharacter() {
		try
		{
			nextCharacter = (char)MyCompiler.fis.read();
			if(nextCharacter == '\n')
			{
				MyCompiler.currentLine++;
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addCharacter() {
		MyCompiler.currentToken = MyCompiler.currentToken + nextCharacter;
	}

	public static boolean isSpace() {
		return Character.isWhitespace(nextCharacter);
	}

	public static boolean lookupToken() {
		for(String s : Token.token)
		{
			if(MyCompiler.currentToken.equalsIgnoreCase(s))
				return true;
		}
		return false;
	}
	
	public static boolean isValidText()
	{
		for(char c : MyCompiler.currentToken.toCharArray())
		{
			if(Character.isLetterOrDigit(c))
				continue;
			switch(c) {
			case ',':
			case '.':
			case '"':
			case ':':
			case '?':
			case '_':
			case '!':
			case '/':
			case '\n':
			case '\t':
				continue;
			}
			return false;
		}
		return true;
	}

}
