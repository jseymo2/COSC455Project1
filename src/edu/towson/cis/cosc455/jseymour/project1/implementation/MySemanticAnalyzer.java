package edu.towson.cis.cosc455.jseymour.project1.implementation;


import java.io.IOException;
import java.util.Stack;

public class MySemanticAnalyzer
{
	private static boolean head = false;
	private static boolean bold = false;
	private static boolean italics = false;
	private static String var = "";
	
	public static void translate() throws IOException, CompilerException
	{
		while(!MyCompiler.parseTree.isEmpty())
		{
			//System.out.println(MyCompiler.parseTree.size());
			switch(MyCompiler.parseTree.peek().toUpperCase())
			{
			case Token.DOCB:
				MyCompiler.outStack.push("\n<html>");
				MyCompiler.parseTree.pop();
				break;
			case Token.DOCE:
				MyCompiler.outStack.push("</html>");
				MyCompiler.parseTree.pop();
				break;
			case Token.HEAD:
				if(head)
				{
					MyCompiler.outStack.push("<head>");
					head = !head;
				}
				else
				{
					MyCompiler.outStack.push("\n</head>");
					head = !head;
				}
				MyCompiler.parseTree.pop();
				break;
			case Token.TITLEB:
				MyCompiler.outStack.push("\n<title>");
				MyCompiler.parseTree.pop();
				break;
			case Token.TITLEE:
				MyCompiler.outStack.push("</title>");
				MyCompiler.parseTree.pop();
				break;
			case Token.PARAB:
				MyCompiler.outStack.push("\n<p>");
				MyCompiler.parseTree.pop();
				break;
			case Token.PARAE:
				MyCompiler.outStack.push("</p>");
				MyCompiler.parseTree.pop();
				break;
			case Token.DEFUSEE:
				MyCompiler.parseTree.pop();
				var = MyCompiler.parseTree.pop();
				if(MyCompiler.parseTree.peek().equalsIgnoreCase(Token.EQSIGN))
				{
					MyCompiler.parseTree.pop();
					MyCompiler.parseTree.pop();
					MyCompiler.parseTree.pop();
				}
				else
				{
					MyCompiler.outStack.push(getVariable(var));
				}
				break;
			case Token.BOLD:
				if(bold)
				{
					MyCompiler.outStack.push("\n<b>");
					bold = !bold;
				}
				else
				{
					MyCompiler.outStack.push("</b>");
					bold = !bold;
				}
				MyCompiler.parseTree.pop();
				break;
			case Token.ITALICS:
				if(italics)
				{
					MyCompiler.outStack.push("\n<i>");
					italics = !italics;
				}
				else
				{
					MyCompiler.outStack.push("</i>");
					italics = !italics;
				}
				MyCompiler.parseTree.pop();
				break;
			case Token.LISTITEMB:
				MyCompiler.outStack.push("\n<li>");
				MyCompiler.parseTree.pop();
				break;
			case Token.LISTITEME:
				MyCompiler.outStack.push("</li>");
				MyCompiler.parseTree.pop();
				break;
			case Token.NEWLINE:
				MyCompiler.outStack.push("\n<br>");
				MyCompiler.parseTree.pop();
				break;
//			case Token.LINKB:
//				MyCompiler.outStack.push("</a>");
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.LINKE:
//				MyCompiler.outStack.push(">");
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.AUDIO:
//				MyCompiler.outStack.push("<li>");
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.VIDEO:
//				MyCompiler.outStack.push("<li>");
//				MyCompiler.parseTree.pop();
//				break;
//			case Token.ADDRESSB:
//				MyCompiler.outStack.push("<li>");
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
					while(!MyCompiler.parseTree.peek().equalsIgnoreCase(Token.ADDRESSB))
						var = MyCompiler.parseTree.pop() + " " + var;
				}
				MyCompiler.parseTree.pop();
				switch(MyCompiler.parseTree.peek())
				{
				case Token.LINKE:
					MyCompiler.parseTree.pop();
					String var2 = MyCompiler.parseTree.pop();
					while(!MyCompiler.parseTree.peek().equalsIgnoreCase(Token.LINKB))
						var2 = MyCompiler.parseTree.pop() + " " + var2;
					MyCompiler.outStack.push(("\n<a href=\"" + var + "\">" + var2 + "</a>"));
					MyCompiler.parseTree.pop();
					break;
				case Token.AUDIO:
					MyCompiler.outStack.push(("<br>\n<audio controls>" +
										"\n\t<source src=\"" + var + "\">" +
										"\n</audio><br>"));
					MyCompiler.parseTree.pop();
					break;
				case Token.VIDEO:
					MyCompiler.outStack.push(("\n<iframe src=\"" + var + "\"/>"));
					MyCompiler.parseTree.pop();
					break;
				}
				break;
				default:
					MyCompiler.outStack.push(MyCompiler.parseTree.pop());
					break;
			}
		}
	}
	
	private static String getVariable(String v) throws CompilerException
	{
		boolean inPara = true;
		boolean allowPara = true;
		String out = "";
		Stack<String> tmp = new Stack<String>();
		//tmp.push(MyCompiler.parseTree.pop());
		while(!MyCompiler.parseTree.isEmpty())
		{
			if(MyCompiler.parseTree.peek().equalsIgnoreCase(Token.PARAB))
			{
				inPara = false;
				allowPara = false;
				tmp.push(MyCompiler.parseTree.pop());
				continue;
			}
			else
			{
				if(MyCompiler.parseTree.peek().equalsIgnoreCase(Token.PARAE))
				{
					inPara = true;
					allowPara = false;
					tmp.push(MyCompiler.parseTree.pop());
					continue;
				}
			}
			if(MyCompiler.parseTree.peek().equalsIgnoreCase(Token.EQSIGN) && (inPara ? allowPara : true))
			{
				tmp.push(MyCompiler.parseTree.pop());
				if(MyCompiler.parseTree.peek().equalsIgnoreCase(v))
				{
					break;
				}
			}
			else
			{
				tmp.push(MyCompiler.parseTree.pop());
			}
		}
		if (!MyCompiler.parseTree.isEmpty())
		{
			MyCompiler.parseTree.push(tmp.pop());
			out = tmp.peek();
			while (!tmp.isEmpty())
			{
				MyCompiler.parseTree.push(tmp.pop());
			}
			MyCompiler.parseTree.pop();
		}
		else
		{
			throw new CompilerException("Semantic Error: Variable \"" + v + "\" not defined.");
		}
		return out;
	}
}
