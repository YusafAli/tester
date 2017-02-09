package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * 
 * @author Yusaf Ali
 *	In use by
 *		Main_menu
 */
public class Token_Extractor {
	private int numofTokens = 0;
	private int numofLines = 0;
	private String fileAddress = null;
	private String hehe = new String("");
	private String regexFunctionDeclaration = "(.*)[(](" + hehe + ")[)](.*)";
	private String regexForDeclaration = "([i][f]|[f][o][r])[\\x20]{0,}[(](.*)[)]";
	private ArrayList<Token_Info> tokenlist = new ArrayList<Token_Info>();
	private String[] identifierTypes = {"Other", "void", "bool", "char", "int", "float", "double", "string"};
	
	public Token_Extractor(String fileAddress)
	{
		this.fileAddress = new String(fileAddress);
	}
	//Suppose to take every token and divide it into a set of tokens and give them a meaning
	//Difficult because what would u do about a function name?
	public void doProcess() throws Exception
	{
		File theFile = new File(fileAddress);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String tempStr = new String("");
		while((tempStr = br.readLine()) != null)
		{
			numofLines++;
			extractor(tempStr);
			//You must take a split here
			//Also you will do some shit that will render M_VariableStyle a little bit useless
		}
		br.close();
		
	}
	//Used for output
	public ArrayList<Token_Info> returnListOfTokens()
	{
		return tokenlist;
	}
	//Used for returning the number of tokens in a code file
	public int returnNumofTokens()
	{
		return numofTokens;
	}
	//Used for splitting strings and differentiated between content type.
	public void extractor(String tempStr)
	{
		String[] tempSplit = tempStr.split(" ");
		for(int iter1 = 0; iter1 < tempSplit.length; iter1++)
		{
			Token_Info tempToken = new Token_Info();
			tempToken.token = new String(tempSplit[iter1]);
			tempToken.length = tempSplit[iter1].length();
			tempToken.colNum = tempStr.indexOf(tempSplit[iter1])+1;
			tempToken.lineNum = numofLines;
			tempToken.next = null;
			tempToken.scope = new String("");
			tempToken.category = new String("");
			tempToken.type = new String("");
			String nomnom = new String(tempSplit[iter1]);
			tempToken.token = new String(nomnom);
			hehe = new String(nomnom);
			
			if(tokenlist.size() > 0)
				tempToken.prev = tokenlist.get(tokenlist.size()-1);
			else
			{
				tempToken.prev = new Token_Info();
				tempToken.prev.token = "None";
			}
			tokenlist.add(tempToken);
		}
	}
	//Hazmat is used to find if a string is data type or not and which one it is
	private int hazmat(String tempStr)
	{
		if(tempStr.contains("void")) return 1;
		else if(tempStr.contains("bool")) return 2;
		else if(tempStr.contains("char")) return 3;
		else if(tempStr.contains("int")) return 4;
		else if(tempStr.contains("float")) return 5;
		else if(tempStr.contains("double")) return 6;
		else if(tempStr.contains("string")) return 7;
		else return 0;
	}
}