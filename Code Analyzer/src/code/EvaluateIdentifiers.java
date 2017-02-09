package code;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class EvaluateIdentifiers {
	private String patternClassDataMembers = new String("[a-z]{1}(([_]{0,1}[a-z]{0,}){0,})[_]{1}");
	private String patternFunctionStyle = new String("([A-Z]{1}[a-z]{0,}){1,}");
	private String patternVariableStyle = new String("([a-z]{1,})([_]{1}[a-z]{1,}){0,}");
	private String patternConstantStyle = new String("[k]{1}([A-Z]{1}[a-z]{0,}){1,}");
	private String patternParameterStyle = new String(patternVariableStyle);
	private ArrayList<variable_data> thelist = new ArrayList<variable_data>();
	private float tParameters = 0;
	private float tFunctions = 0;
	private float tConstants = 0;
	private float tVariables = 0;
	private float scoreParameters = 0;
	private float scoreFunctions = 0;
	private float scoreConstants = 0;
	private float scoreVariables = 0;
	private float[] SpaceAssign = {0,0,0,0};
	private JTable evTable;
	private float totalLOC = 0;
	private float wrongLengthLOC = 0;
	private float correctLengthLOC = 0;
	private int numberOfComments = 0;
	public EvaluateIdentifiers(ArrayList<variable_data> recvList, float totalLOC, float invalidLOC, int numberOfComments, float[] spaceAssign)
	{
		this.thelist = recvList;
		this.wrongLengthLOC = invalidLOC;
		this.totalLOC = totalLOC;
		this.correctLengthLOC = this.totalLOC - this.wrongLengthLOC;
		this.numberOfComments = numberOfComments;
		this.SpaceAssign[0] = spaceAssign[0];	//Total1
		this.SpaceAssign[1] = spaceAssign[1];	//Score1
		this.SpaceAssign[2] = spaceAssign[2];	//Total2
		this.SpaceAssign[3] = spaceAssign[3];	//Score2
	}
	public void evaluator()
	{
		counttotals();
		for(int iter1 = 0; iter1 < thelist.size(); iter1++)
		{
			if(thelist.get(iter1).category == "Function")
			{
				if(thelist.get(iter1).name.matches(patternFunctionStyle))
				{
					this.scoreFunctions++;
				}
			}
			else if(thelist.get(iter1).category == "Variable")
			{
				if(thelist.get(iter1).name.matches(patternVariableStyle))
				{
					this.scoreVariables++;
				}
			}
			else if(thelist.get(iter1).category == "Parameter")
			{
				if(thelist.get(iter1).name.matches(patternParameterStyle))
				{
					this.scoreParameters++;
				}	
			}
			else if(thelist.get(iter1).category == "Constant")
			{
				if(thelist.get(iter1).name.matches(patternConstantStyle))
				{
					this.scoreConstants++;
				}	
			}
		}
		//After this comment we will try to find the Functions and their length
		illustrateInformationInTable();
	}
	private void counttotals()
	{
		for(int iter2 = 0; iter2 < thelist.size(); iter2++)
		{
			if(thelist.get(iter2).category == "Function")
			{
				tFunctions++;
			}
			else if(thelist.get(iter2).category == "Variable")
			{
				tVariables++;
			}
			else if(thelist.get(iter2).category == "Parameter")
			{
				tParameters++;
			}
			else if(thelist.get(iter2).category == "Constant")
			{
				tConstants++;
			}
		}
	}

	private void illustrateInformationInTable()
	{
		evTable = new JTable();
		evTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Sr. No.", "Name", "Result", "Total", "Score"
			}
		));
		//This setting will enable auto sorting of the evTable
		evTable.setAutoCreateRowSorter(true);
		//Storing Table model in a variable model
		DefaultTableModel model = (DefaultTableModel) evTable.getModel();
		TableColumnModel tcm = evTable.getColumnModel();
		tcm.getColumn(0).setMaxWidth(50);
		evTable.setColumnModel(tcm);
		//Making a evTable row sorter
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
		//Setting the rowSorter of the evTable
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int columnIndexToSort = 1;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		sorter.addRowSorterListener(new RowSorterListener()
		{
			public void sorterChanged(RowSorterEvent evt) {
				for (int i = 0; i < evTable.getRowCount(); i++) {
					evTable.setValueAt(i + 1, i, 0);
		        }
		}});
		sorter.setSortable(0, false);
		sorter.setSortable(1, false);
		sorter.setSortable(2, false);
		sorter.setSortable(3, false);
		sorter.setSortable(4, false);
		evTable.setRowSorter(sorter);
		model = (DefaultTableModel) evTable.getModel();
		Object[] row = {model.getRowCount(),"Name","Result","Total","Score"};
		if(Float.toString(getScoreFunctions()) == "NaN") {
			Object[] row1 = {model.getRowCount(),"Function Names","0%",Float.toString(this.tFunctions),Float.toString(this.scoreFunctions)};
			model.addRow(row1);
		}
		else {
			Object[] row1 = {model.getRowCount(),"Function Names",Float.toString(this.getScoreFunctions())+"%",Float.toString(this.tFunctions),Float.toString(this.scoreFunctions)};
			model.addRow(row1);
		}
		if(Float.toString(getScoreVariables()) == "NaN") {
			Object[] row2 = {model.getRowCount(),"Variable Names","0%",Float.toString(this.tVariables),Float.toString(this.scoreVariables)};
			model.addRow(row2);
		}
		else {
			Object[] row2 = {model.getRowCount(),"Variable Names",Float.toString(this.getScoreVariables())+"%",Float.toString(this.tVariables),Float.toString(this.scoreVariables)};
			model.addRow(row2);
		}
		if(Float.toString(getScoreParameters()) == "NaN") {
			Object[] row3 = {model.getRowCount(),"Parameter Names","0%",Float.toString(this.tParameters),Float.toString(this.scoreParameters)};
			model.addRow(row3);
		}
		else {
			Object[] row3 = {model.getRowCount(),"Parameter Names",Float.toString(this.getScoreParameters())+"%",Float.toString(this.tParameters),Float.toString(this.scoreParameters)};
			model.addRow(row3);
		}
		if(Float.toString(getScoreConstants()) == "NaN"){
			Object[] row4 = {model.getRowCount(),"Constant Names","0%",Float.toString(this.tConstants),Float.toString(this.scoreConstants)};
			model.addRow(row4);
		}
		else {
			Object[] row4 = {model.getRowCount(),"Constant Names",Float.toString(this.getScoreConstants())+"%",Float.toString(this.tConstants),Float.toString(this.scoreConstants)};
			model.addRow(row4);
		}
		if(Float.toString(getScoreSpaceAssign()) == "NaN"){
			Object[] row5 = {model.getRowCount(),"Spacing: Assignment Operator","0%",Float.toString(this.SpaceAssign[0]),Float.toString(this.SpaceAssign[1])};
			model.addRow(row5);
		}
		else {
			Object[] row5 = {model.getRowCount(),"Spacing: Assignment Operator",Float.toString(this.getScoreSpaceAssign())+"%",Float.toString(this.SpaceAssign[0]),Float.toString(this.SpaceAssign[1])};
			model.addRow(row5);
		}
		if(Float.toString(getScoreSpaceAssign()) == "NaN"){
			Object[] row6 = {model.getRowCount(),"Spacing: Relational Operator","0%",Float.toString(this.SpaceAssign[2]),Float.toString(this.SpaceAssign[3])};
			model.addRow(row6);
		}
		else {
			Object[] row6 = {model.getRowCount(),"Spacing: Relational Operator",Float.toString(this.getScoreSpaceRelate())+"%",Float.toString(this.SpaceAssign[2]),Float.toString(this.SpaceAssign[3])};
			model.addRow(row6);
		}
		Object[] row7 = {model.getRowCount(),"Ok Length Lines",Float.toString(this.getScoreLengthLOC())+"%",Float.toString(this.totalLOC),Float.toString(this.correctLengthLOC)};
		model.addRow(row7);
		Object[] row8 = {model.getRowCount(),"Comment Percent",Float.toString(this.getScorePercentComment())+"%",Float.toString(this.totalLOC),Float.toString(this.numberOfComments)};
		model.addRow(row8);
		
		evTable.setModel(model);
	}
	
	public JTable retEvTable()
	{
		return evTable;
	}
	
	public float getScoreFunctions()
	{
		return (this.scoreFunctions / this.tFunctions) * 100;
	}
	public float getScoreVariables()
	{
		return (this.scoreVariables / this.tVariables) * 100;
	}
	public float getScoreParameters()
	{
		return (this.scoreParameters / this.tParameters) * 100;
	}
	public float getScoreConstants()
	{
		return (this.scoreConstants / this.tConstants) * 100;
	}
	public float getScoreLengthLOC()
	{
		return (this.correctLengthLOC / this.totalLOC) * 100;
	}
	public float getScorePercentComment()
	{
		return ((float)this.numberOfComments / this.totalLOC) * 100;
	}
	public float getScoreSpaceAssign()
	{
		return (this.SpaceAssign[1] / this.SpaceAssign[0]) * 100;
	}
	public float getScoreSpaceRelate() {
		return (this.SpaceAssign[3] / this.SpaceAssign[2]) * 100;
	}
}
//Show a evTable that tells which variable is correct and which is not
