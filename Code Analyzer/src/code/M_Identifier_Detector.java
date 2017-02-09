package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Yusaf Ali
 *	This is the main class that holds everything
 */
public class M_Identifier_Detector {
	private String fileAddress = null;
	private ArrayList<variable_data> allVariables = new ArrayList<variable_data>();
	
	public void setfileAddress(String fileAddr)
	{
		fileAddress = new String(fileAddr);
	}
	
	/*
	 * The main thing that a module will do is calculation.
	 */
	public void doProcess() throws IOException
	{
		boolean inBlock = false, inClass = false;
		boolean inFunctionDeclaration = false;
		int inBlockLevel = 0, tempHolder = 0;
		File theFile = new File(fileAddress);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String tempStr = new String("");
		while((tempStr = br.readLine()) != null)
		{
			//This if statement will check if it is a function declaration.
			if(tempStr.matches("/(.*)[(](.*)[)]/"))
			{
				inFunctionDeclaration = true;
			}
			else
			{
				inFunctionDeclaration = false;
			}
			//Checks for a brace open, a new block
			if(tempStr.matches("[.]{0,}[\\x7B]{1}[.]{0,}"))
			{
				inBlock = true;	
				inBlockLevel++;
			}
			//Checks for a brace close, end of block
			if(tempStr.matches("[.]{0,}[\\x7D]{1}[.]{0,}"))
			{
				inBlockLevel--;
				if(inBlockLevel == tempHolder) inClass = false;
				if(inBlockLevel == 0) inBlock = false;
			}
			//This if is used to check if the scope is in class by checking if a class is started or not
			if(tempStr.contains("class"))
			{
				inClass = true;
				tempHolder = inBlockLevel;
			}
			//This if is used to check if a line contains any Function or other Identifier
			if(hazmat(tempStr) != 0)
			{
				returnVariableNames(tempStr, inBlock, inClass, inFunctionDeclaration);
			}
		}
		br.close();
		refinery();
		trimThemAll();
	}
	
	/*
	 * This function is temporary.
	 */
	private void returnVariableNames(String variableCollection, boolean inBlock, boolean inClass, boolean inFunctionDeclaration)
	{
		String[] arrayofVar = variableCollection.split(" ");
		for(int iter1 = 0; iter1 < arrayofVar.length-1; iter1++)
		{
			if(hazmat(arrayofVar[iter1]) != 0)
			{
				try 
				{
					variable_data tempObj1 = new variable_data();
					tempObj1.name = arrayofVar[iter1 + 1].toString();
					tempObj1.type = arrayofVar[iter1].toString();
					//Setting Constant
					if(variableCollection.contains("const"))
						tempObj1.category = "Constant";
					else
						tempObj1.category = "Not";
					//Set the scope of the Variable
					if(inBlock)
						tempObj1.scope = "Local";
					else
						tempObj1.scope = "Global";
					//Set if data member of the class
					if(inClass)
						tempObj1.scope = "Class";
					else
						tempObj1.scope = "Global";
					if(inFunctionDeclaration)
						tempObj1.scope = "Parameter";
					allVariables.add(tempObj1);
					int iter2 = 1;
					//This loop is used to check for multi declaration in single line.
					while(arrayofVar[iter1+iter2].contains(","))
					{
						if(hazmat(arrayofVar[iter1+iter2+1]) == 0)
						{
							break;
						}
						else
						{
							variable_data tempObj2 = new variable_data();
							tempObj2.name = arrayofVar[iter1 + iter2 + 1].toString();
							tempObj2.type = arrayofVar[iter1].toString();
							//Setting the next storage Constant
							if(variableCollection.contains("const"))
								tempObj2.category = "Constant";
							else
								tempObj2.category = "Not";
							//Set the scope of the Variable
							if(inBlock)
								tempObj2.scope = "Local";
							else
								tempObj2.scope = "Global";
							if(inClass)
								tempObj2.scope = "Class";
							else
								tempObj2.scope = "Global";
							if(inFunctionDeclaration)
								tempObj2.scope = "Parameter";
							allVariables.add(tempObj2);
							iter2++;
						}
					}
				}
				catch(Exception e)
				{
					//e.printStackTrace(); Because there will be an ArrayOutOfBoundsExceptionThrown
				}
			}
		}
	}
	
	public void refinery()
	{
		//This loop is used to differentiate between functions and variables
		for(int iter3 = 0; iter3 < allVariables.size(); iter3++)
		{
			variable_data tempObj3 = new variable_data();
			tempObj3 = allVariables.get(iter3);
			if(tempObj3.name.contains("("))
			{
				tempObj3.name = tempObj3.name.substring(0, tempObj3.name.indexOf('('));
				tempObj3.category = "Function";
			}
			if(tempObj3.name.endsWith(",") || tempObj3.name.endsWith(";"))
			{
				tempObj3.name = tempObj3.name.substring(0, tempObj3.name.length()-1);
				if(tempObj3.category == "Not")
					tempObj3.category = "Variable";
			}
			if(tempObj3.name.contains(")"))
			{
				tempObj3.name = tempObj3.name.substring(0, tempObj3.name.indexOf(')'));
				if(tempObj3.category == "Not")
					tempObj3.category = "Parameter";
			}
			allVariables.set(iter3, tempObj3);
		}
		//This loop is used to correct the String variable of type
		for(int iter5 = 0; iter5 < allVariables.size(); iter5++)
		{
			if(allVariables.get(iter5).type.contains("("))
			{
				//A = A.substring(A.indexOf('ch')+1, A.length()); where A = A.get(index).type
				allVariables.get(iter5).category = "Parameter";
				allVariables.get(iter5).type = allVariables.get(iter5).type.substring(allVariables.get(iter5).type.indexOf('(')+1, allVariables.get(iter5).type.length());
			}
		}
	}
	
	public void trimThemAll()
	{
		for(int iter7 = 0; iter7 < allVariables.size(); iter7++)
		{
			allVariables.get(iter7).name = allVariables.get(iter7).name.trim();
			allVariables.get(iter7).type = allVariables.get(iter7).type.trim();
			allVariables.get(iter7).category = allVariables.get(iter7).category.trim();
			allVariables.get(iter7).scope = allVariables.get(iter7).scope.trim();
		}
	}
	/*
	 * After doing the thing, the results will be accessed through this procedure(method)
	 */
	public String getResult()
	{
		String retStr = new String("");
		for(int iter6 = 0; iter6 < allVariables.size(); iter6++)
		{
			retStr += "Name = " + allVariables.get(iter6).name + ", Category = " + allVariables.get(iter6).category + ", Type = " + allVariables.get(iter6).type +
					", Scope = " + allVariables.get(iter6).scope + "\n";
		}
		return retStr;
	}
	

	/*
	 * This function is temporary.
	 */
	public void displayAllVariable()
	{
		System.out.println("Nothing");
	}
	 public ArrayList<variable_data> accessValues()
	 {
		 return allVariables;
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


/*
To do
	Find a place to put ifs for distinguishing between data elemtns
	s
*/