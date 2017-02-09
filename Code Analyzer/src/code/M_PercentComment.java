package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class M_PercentComment {
	private String fileAddress = new String("");
	private int numberComments = 0;
	private int t_LOC = 0;
	private ArrayList<variable_data> listofLOC = new ArrayList<variable_data>();
	public void setfileAddress(String fileAddr)
	{
		fileAddress = new String(fileAddr);
	}
	public void doProcess() throws IOException
	{
		File theFile = new File(fileAddress);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String tempStr = new String("");
		boolean multiline = false;
		while((tempStr = br.readLine()) != null)
		{
			t_LOC++;
			variable_data tempStorage1 = new variable_data();
			tempStorage1.name = tempStr;
			tempStorage1.type = "Not";
			boolean comentify = false;
			//Search for /* in a line
			if(tempStr.matches("[^\"]{0,}[/][*][^\"]{0,}"))
			{
				if(tempStr.matches("[^\"]{0,}[/][/][^\"]{0,}"))
				{
					multiline = false;
					tempStorage1.type = "Comment";
					comentify = false;
				}
				/*
				else if(tempStr.matches("([^\"]{0,}[/]{2}[^\"]{0,}){1,}([^\"]{0,})(([^\"]{0,}[/][*][^\"]{0,}){0,}([^\"]{0,})([^\"]{0,}[/]{2}[^\"]{0,}){0,}){0,}") && multiline == true)
				{
					comentify = true;
					tempStorage1.type = "Comment";
				}*/
				else//if(tempStr.matches("([^\"]{0,}[/][*][^\"]{0,}){1,}([^\"]{0,})(([^\"]{0,}[/]{2}[^\"]{0,}){0,}([^\"]{0,})([^\"]{0,}[/][*][^\"]{0,}){0,}){0,}"))
				{
					numberComments++;
					multiline = true;
					tempStorage1.type = "Comment";
					comentify = true;
				}
			}
			//Search for */ in a line
			else if(tempStr.matches("[^\"]{0,}[*][/][^\"]{0,}") && multiline == true)
			{
				numberComments++;
				multiline = false;
				tempStorage1.type = "Comment";
				comentify = true;
			}
			//Mark a statement without /* or */ as comment depending on multiline.
			else if((!tempStr.matches("[^\"]{0,}[/][*][^\"]{0,}") && !tempStr.matches("[^\"]{0,}[*][/][^\"]{0,}")) && multiline == true)
			{
				numberComments++;
				comentify = true;
				tempStorage1.type = "Comment";
			}
			//Mark a statement without /* or */ as not comment depending on multiline.
			else if((!tempStr.matches("[^\"]{0,}[/][*][^\"]{0,}") && !tempStr.matches("[^\"]{0,}[*][/][^\"]{0,}")) && multiline == false)
			{
				//Not a comment
				tempStorage1.type = "Not Comment";
				if(comentify == true)
				{
					numberComments--;
					comentify = false;
				}
			}
			//What if a line is totally blanked?
			if(tempStr.matches("\\s*") && multiline == false)
			{
				tempStorage1.type = "Not Comment";
				if(comentify == true)
				{
					numberComments--;
					comentify = false;
				}
			}
			//in case of single line comment
			if(tempStr.matches("[^\"]{0,}[/]{2}[^\"]{0,}"))
			{
				numberComments++;
				tempStorage1.type = "Comment";
				if(comentify == true)
				{
					numberComments--;
				}
			}
			//add the line in the list
			listofLOC.add(tempStorage1);
		}
		br.close();
	}
	public ArrayList<variable_data> retLocList()
	{
		return listofLOC;
	}
	public int retTLOC()
	{
		return t_LOC;
	}
	public int retNComment()
	{
		return numberComments;
	}
	public String getResult()
	{
		return "Total Lines = " + t_LOC + "\n" + "Number of Comment Lines = " + numberComments;
	}
}