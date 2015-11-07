package edu.towson.cis.cosc455.jseymour.project1.implementation;

import java.io.File;
import java.io.FileInputStream;

public class MyCompiler
{
	
	public static String currentToken = "";
	static File file = null;
	static FileInputStream fis = null;

	public static void main(String[] args)
	{
		if(!loadFile(args[0]))
		{
			return;
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
		//catch(Exception e)
		{
			
		}
		return false;
	}

}
