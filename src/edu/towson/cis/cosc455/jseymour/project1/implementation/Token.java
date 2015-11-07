package edu.towson.cis.cosc455.jseymour.project1.implementation;

public class Token
{
	public static String DOCB = "#BEGIN";
	public static String DOCE = "#END";
	public static String HEAD = "^";
	public static String TITLEB = "<";
	public static String TITLEE = ">";
	public static String PARAB = "{";
	public static String PARAE = "}";
	public static String DEFB = "$DEF";
	public static String USEB = "$USE";
	public static String DEFUSEE = "$END";
	public static String EQSIGN = "=";
	public static String BOLD = "**";
	public static String ITALICS = "*";
	public static String LISTITEMB = "+";
	public static String LISTITEME = ";";
	public static String NEWLINE = "~";
	public static String LINKB = "[";
	public static String LINKE = "]";
	public static String AUDIO = "@";
	public static String VIDEO = "%";
	public static String ADDRESSB = "(";
	public static String ADDRESSE = ")";
	
	public static String[] token = 	{DOCB,DOCE,HEAD,TITLEB,TITLEE,PARAB,PARAE,DEFB,USEB,DEFUSEE,EQSIGN,BOLD,ITALICS,LISTITEMB,LISTITEME,NEWLINE,LINKB,LINKE,AUDIO,VIDEO,ADDRESSB,ADDRESSE};
}
