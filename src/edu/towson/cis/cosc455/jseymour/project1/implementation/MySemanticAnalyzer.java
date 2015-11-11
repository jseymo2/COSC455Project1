package edu.towson.cis.cosc455.jseymour.project1.implementation;

import java.io.IOException;
import java.util.Stack;

public class MySemanticAnalyzer
{
	private static Stack<String> out = new Stack<String>();
	private static boolean head = false;
	private static boolean bold = false;
	private static boolean italics = false;
	private static String var = "";
	
	public static void translate() throws IOException, CompilerException
	{
		while(!MyCompiler.parseTree.isEmpty())
		{
			switch(MyCompiler.parseTree.peek().toUpperCase())
			{
			case Token.DOCB:
				MyCompiler.fos.write("\n<html>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.DOCE:
				MyCompiler.fos.write("<html>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.HEAD:
				if(head)
				{
					MyCompiler.fos.write("<head>".getBytes());
					head = !head;
				}
				else
				{
					MyCompiler.fos.write("\n<head>".getBytes());
					head = !head;
				}
				break;
			case Token.TITLEB:
				MyCompiler.fos.write("\n<title>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.TITLEE:
				MyCompiler.fos.write("<title>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.PARAB:
				MyCompiler.fos.write("\n<p>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.PARAE:
				MyCompiler.fos.write("<p>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.DEFUSEE:
				MyCompiler.parseTree.pop();
				var = MyCompiler.parseTree.pop();
				if(MyCompiler.parseTree.pop().equalsIgnoreCase(Token.EQSIGN))
				{
					MyCompiler.parseTree.pop();
					MyCompiler.parseTree.pop();
				}
				else
				{
					MyCompiler.fos.write(getVariable(var).getBytes());
					MyCompiler.parseTree.pop();
				}
				break;
			case Token.BOLD:
				if(bold)
					MyCompiler.fos.write("\n<b>".getBytes());
				else
					MyCompiler.fos.write("<b>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.ITALICS:
				if(italics)
					MyCompiler.fos.write("\n<i>".getBytes());
				else
					MyCompiler.fos.write("<i>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.LISTITEMB:
				MyCompiler.fos.write("\n<li>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.LISTITEME:
				MyCompiler.fos.write("<li>".getBytes());
				MyCompiler.parseTree.pop();
				break;
			case Token.NEWLINE:
				MyCompiler.fos.write("\n<br>".getBytes());
				MyCompiler.parseTree.pop();
				break;
//			case Token.LINKB:
//				MyCompiler.fos.write("</a>".getBytes());
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.LINKE:
//				MyCompiler.fos.write(">".getBytes());
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.AUDIO:
//				MyCompiler.fos.write("<li>".getBytes());
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.VIDEO:
//				MyCompiler.fos.write("<li>".getBytes());
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.ADDRESSB:
//				MyCompiler.fos.write("<li>".getBytes());
//				MyCompiler.parseTree.pop();
//				break;
			case Token.ADDRESSE:
				MyCompiler.parseTree.pop();
				if(MyCompiler.parseTree.peek().equalsIgnoreCase(Token.DEFUSEE))
				{
					MyCompiler.parseTree.pop();
					var = getVariable(MyCompiler.parseTree.pop());
				}
				else
				{
					var = MyCompiler.parseTree.pop();
				}
				MyCompiler.parseTree.pop();
				switch(MyCompiler.parseTree.peek())
				{
				case Token.LINKE:
					MyCompiler.fos.write(("\n<a href=\"" + var + "\">" + MyCompiler.parseTree.pop() + "</a>").getBytes());
					MyCompiler.parseTree.pop();
					break;
				case Token.AUDIO:
					MyCompiler.fos.write(("\n<audio controls>" +
										"\n\t<source src=\"" + var + "\">" +
										"\n</audio>").getBytes());
					MyCompiler.parseTree.pop();
					break;
				case Token.VIDEO:
					MyCompiler.fos.write(("\n<iframe src=\"" + var + "\"/>").getBytes());
					MyCompiler.parseTree.pop();
					break;
				}
				break;
			}
		}
	}
	
	private static String getVariable(String v) throws CompilerException
	{
		String out = "";
		Stack<String> tmp = new Stack<String>();
		tmp.push(MyCompiler.parseTree.pop());
		while(!MyCompiler.parseTree.peek().equalsIgnoreCase(v))
		{
			if(MyCompiler.parseTree.isEmpty())
			{
				throw new CompilerException("Semantic Error: Variable " + v + " not defined.");
			}
			else
			{
				tmp.push(MyCompiler.parseTree.pop());
			}
		}
		MyCompiler.parseTree.push(tmp.pop());
		out = tmp.peek();
		while(!tmp.isEmpty())
		{
			MyCompiler.parseTree.push(tmp.pop());
		}
		return out;
	}
}
