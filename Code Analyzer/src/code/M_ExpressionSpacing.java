//Assignment Operator Spacing Module
package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class M_ExpressionSpacing {
	private String fileAddress = new String("");
	private float score1 = 0;			//Assignment
	private float occurenceCount1 = 0;	//Assignment
	private float score2 = 0;			//Relational
	private float occurenceCount2 = 0;	//Relational
	private String Lines1;				//Assignment
	private String Lines2;				//Relational
	private String regexForAssignment = new String("([ ]{0,})([^=!+/%]{0,})( ){1}((\\+=)|(-=)|(/=)|(\\*=)|(%=)|(=)){1}( ){1}([^=!+*-/%]{0,})([^=]{0,})([ ]{0,})");
	private String regexForRelational = new String("([ ]{0,})([^=+<>*-/% !]{0,})( ){1}((!=)|(<=)|(>=)|(==)|(<)|(>)){1}( ){1}([^=<>+*-/%! ]{0,}){1}(( ){1}((!=)|(<=)|(>=)|(==)|(<)|(>)){1}( ){1}([^<>=+*-/%! ]{0,})){0,}([^<>=!]{0,})([ ]{0,})");
	private ArrayList<String> List1;
	private ArrayList<String> List2;
	public JTable tb1 = new JTable(), tb2 = new JTable();
	//tb1 will show the lines of the assignment
	//tb2 will show the lines of the relational
	
/* The following regex was tested on freeformatter.com
 * "([^\\w]{0,})([^=+<>*-/% !]{0,})( ){1}((!=)|(<=)|(>=)|(==)|(<)|(>)){1}( ){1}([^=<>+*-/%! ]{0,}){1}(( ){1}((!=)|(<=)|(>=)|(==)|(<)|(>)){1}( ){1}([^<>=+*-/%! ]{0,})){0,}([^<>=!]{0,})([^\\w]{0,})"
 * The following regex was tested on freeformatter.com
 * "([^\\w]{0,})([^=!+*-/%]{0,})( ){1}((\\+=)|(-=)|(/=)|(\\*=)|(%=)|(=)){1}( ){1}([^=!+*-/%]{0,})([^=]{0,})([^\\w]{0,})"
 * */
	public void doProcess()
	{
		try
		{
			Pattern assignmentPattern = Pattern.compile(this.regexForAssignment);
			Pattern relationalPattern = Pattern.compile(this.regexForRelational);
			String par = "Write down the regex here that u wrote in your loli notebook";
			File theFile = new File(fileAddress);
			BufferedReader br = new BufferedReader(new FileReader(theFile));
			String tempStr = new String("");
			tb1.setModel(new DefaultTableModel(new Object [][]{}, new String[]{"Serial", "Line", "Grade"}));
			tb2.setModel(new DefaultTableModel(new Object [][]{}, new String[]{"Serial", "Line", "Grade"}));
			DefaultTableModel model1 = (DefaultTableModel) tb1.getModel();
			DefaultTableModel model2 = (DefaultTableModel) tb2.getModel();
			while((tempStr = br.readLine()) != null)
			{
				//trim necessary
				tempStr = tempStr.trim();
				if(tempStr.contains("=") || tempStr.contains("+=") || tempStr.contains("-=") || tempStr.contains("*=") || tempStr.contains("/="))
				{//This if is used to just detect if the line has an equation
					this.occurenceCount1++;
					boolean grade1 = false;
					Matcher assignmentMatcher = assignmentPattern.matcher(tempStr);
					if(assignmentMatcher.matches())
					{//This will actually grade it
						score1++;
						this.captureLine1(tempStr);
						grade1 = true;
					}
					if(grade1) model1.addRow(new Object[] {model1.getRowCount(),tempStr,"1"});
					else model1.addRow(new Object[] {model1.getRowCount(),tempStr,"0"});
				}
				if(tempStr.contains("<=") || tempStr.contains(">=") || tempStr.contains("!=") || tempStr.contains("<") || tempStr.contains(">") || tempStr.contains("=="))
				{//This if is used to detect only a relational expression
					this.occurenceCount2++;
					boolean grade2 = false;
					Matcher relationalMatcher = relationalPattern.matcher(tempStr);
					if(relationalMatcher.matches())
					{//Grader
						score2++;
						this.captureLine2(tempStr);
						grade2 = true;
					}
					if(grade2) model2.addRow(new Object[] {model2.getRowCount(),tempStr,"1"});
					else model2.addRow(new Object[] {model2.getRowCount(),tempStr,"0"});
				}
			}
			br.close();
			tb1.setModel(model1);
			tb2.setModel(model2);
			tb1.setAutoCreateRowSorter(true);
			tb2.setAutoCreateRowSorter(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public float[] getResult()
	{
		float[] result = {this.occurenceCount1, this.score1, this.occurenceCount2, this.score2};
		return result;
	}
	public float[] getResult1()
	{
		float[] result = {this.occurenceCount1, this.score1};
		System.out.println(result[0]);
		System.out.println(result[1]);
		return result;
	}
	public float[] getResult2()
	{
		float[] result = {this.occurenceCount2, this.score2};
		System.out.println(result[0]);
		System.out.println(result[1]);
		return result;
	}
	public float getScore1()
	{
		return score1;
	}
	public float getScore2()
	{
		return this.score2;
	}
	public float getOccurenceCount1()
	{
		return this.occurenceCount1;
	}
	public float getOccurenceCount2()
	{
		return this.occurenceCount2;
	}
	//------------------------------------------------------------------------------------
	//This section of functions are used for debugging
	public void captureLine1(String tempStr)
	{
		if(Lines1 == null)
		{
			Lines1 = new String(tempStr + "\n");
		}
		else
		{
			Lines1 = new String(Lines1 + tempStr + "\n");
		}
	}
	public void captureLine2(String tempStr)
	{
		if(Lines2 == null)
		{
			Lines2 = new String(tempStr + "\n");
		}
		else
		{
			Lines2 = new String(Lines2 + tempStr + "\n");
		}
	}
	public String showLines1()
	{
		return Lines1;
	}
	public String showLines2()
	{
		return Lines2;
	}
	//-------------------------------------------------------------------------------------
	public ArrayList<String> getList1()
	{
		return List1;
	}
	public ArrayList<String> getList2()
	{
		return List2;
	}
	//-------------------------------------------------------------------------------------
	//Constructor
	public M_ExpressionSpacing(String fileAddr)
	{
		fileAddress = new String(fileAddr);
	}
	public void setfileAddress(String fileAddr)
	{
		fileAddress = new String(fileAddr);
	}
}
/*
 * Evaluation is done in the old way
 * Get score
 * divide by total obtainable marks
 * like getting 47 marks outta 100, thus subject the answer to percentage form
 * Try the following regex to evaluate
 * "([^=!+*-/%]{0,})\b( ){1}((!=)|(\\+=)|(-=)|(/=)|(\\*=)|(%=)|(=)){1}( ){1}\b([^=!+*-/%]{0,})"
 */