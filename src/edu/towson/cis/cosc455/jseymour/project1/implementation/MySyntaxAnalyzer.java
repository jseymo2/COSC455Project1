package edu.towson.cis.cosc455.jseymour.project1.implementation;

import edu.towson.cis.cosc455.jseymour.project1.interfaces.SyntaxAnalyzer;
import edu.towson.cis.cosc455.jseymour.project1.tokens.Token;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {

	@Override
	public void markdown() throws CompilerException
	{
		if(MyCompiler.currentToken.equalsIgnoreCase(Token.DOCB))
		{
			// TODO
		}
		else
		{
			// TODO
		}
	}

	@Override
	public void head() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void title() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void body() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void paragraph() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void innerText() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void variableDefine() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void variableUse() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void bold() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void italics() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void listitem() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void innerItem() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void link() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void audio() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void video() throws CompilerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void newline() throws CompilerException {
		// TODO Auto-generated method stub

	}

}
