package edu.towson.cis.cosc455.jseymour.project1.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

public class MyCompiler
{
	
	public static String currentToken = "";
	public static int currentLine = 1;
	static Stack<String> parseTree = new Stack<String>();
	static File file = null;
	static FileInputStream fis = null;
	static FileOutputStream fos = null;

	public static void main(String[] args)
	{
		if(!loadFile(args[0].trim()))
		{
			return;
		}
		
		try
		{
			MyLexicalAnalyzer.getNextToken();
		}
		catch (CompilerException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			MySyntaxAnalyzer.markdown();
		}
		catch (CompilerException e)
		{
			System.out.println(e.getErrorMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		try
		{
			fis.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(args[0].trim().substring(0, args[0].trim().lastIndexOf(".mkd")));
		try
		{
			fos = new FileOutputStream(file);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static boolean loadFile(String s)
	{
		if(!s.endsWith(".mkd"))
		{
			System.out.println("Invalid file extension. Must be a \".mkd\" file.");
			return false;
		}
		try
		{
			file = new File(s);
			fis = new FileInputStream(file);
			
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
