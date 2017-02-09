package code;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import code.Token_Info;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 * 
 * @author Yusaf Ali
 *	In use by
 *		Main_menu
 */
public class Token_Table
{
	private JTable table;
	private DefaultTableModel model;
	//Constructor
	public Token_Table(ArrayList<Token_Info> listofTokens) {
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Sr. No.", "Category", "Name", "Scope", "Type", "Length", "Column", "Line"
			}
		));
		//This setting will enable auto sorting of the table
		table.setAutoCreateRowSorter(true);
		//Storing Table model in a variable model
		model = (DefaultTableModel) table.getModel();
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setMaxWidth(50);
		table.setColumnModel(tcm);
		//Making a table row sorter
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
		//Setting the rowSorter of the table
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		int columnIndexToSort = 1;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		sorter.addRowSorterListener(new RowSorterListener()
		{
			public void sorterChanged(RowSorterEvent evt) {
				for (int i = 0; i < table.getRowCount(); i++) {
					table.setValueAt(i + 1, i, 0);
		        }
		}});
		sorter.setSortable(0, false);
		sorter.setSortable(1, false);
		sorter.setSortable(2, false);
		sorter.setSortable(3, false);
		sorter.setSortable(4, false);
		table.setRowSorter(sorter);
		model = (DefaultTableModel) table.getModel();
		for(int iter1 = 0; iter1 < listofTokens.size(); iter1++)
		{
			Object[] row = {model.getRowCount(),listofTokens.get(iter1).category,listofTokens.get(iter1).token,
							listofTokens.get(iter1).scope,listofTokens.get(iter1).type,listofTokens.get(iter1).length,
							listofTokens.get(iter1).colNum,listofTokens.get(iter1).lineNum};
			model.addRow(row);
		}
	}
	public JTable retTable()
	{
		return this.table;
	}
}
