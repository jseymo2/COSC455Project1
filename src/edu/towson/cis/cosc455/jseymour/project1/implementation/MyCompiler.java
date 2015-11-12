package edu.towson.cis.cosc455.jseymour.project1.implementation;


import java.awt.Desktop;
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
	private static boolean err = false;
	static Stack<String> parseTree = new Stack<String>();
	static Stack<String> outStack = new Stack<String>();
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
		catch (CompilerException e)
		{
			System.out.println(e.getErrorMessage());
			err = true;
		}
		
		try
		{
			MySyntaxAnalyzer.markdown();
		}
		catch (CompilerException e)
		{
			System.out.println(e.getErrorMessage());
			err = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			err = true;
		}
		
		try
		{
			fis.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			err = true;
		}
		
		file = new File(args[0].trim().substring(0, args[0].trim().lastIndexOf(".mkd")) + ".html");
		try
		{
			fos = new FileOutputStream(file);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			err = true;
		}
		
		if (!err)
		{
			try
			{
				MySemanticAnalyzer.translate();
				while(!outStack.isEmpty())
				{
					fos.write((outStack.pop() + " ").getBytes());
				}
				fos.close();
				openHTMLFileInBrowser(args[0].trim().substring(0,
						args[0].trim().lastIndexOf(".mkd"))
						+ ".html");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				err = true;
			}
			catch (CompilerException e)
			{
				System.out.println(e.getErrorMessage());
				err = true;
			}
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
			err = true;
		}
		return false;
	}
	
	private static void openHTMLFileInBrowser(String htmlFileStr){
		File file= new File(htmlFileStr.trim());
		if(!file.exists()){
			System.err.println("File "+ htmlFileStr +" does not exist.");
			return;
		}
		try{
			Desktop.getDesktop().browse(file.toURI());
		}
		catch(IOException ioe){
			System.err.println("Failed to open file");
			ioe.printStackTrace();
			err = true;
		}
		return ;
	}

}
