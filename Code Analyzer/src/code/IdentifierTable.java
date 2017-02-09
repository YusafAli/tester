package code;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import code.variable_data;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class IdentifierTable extends JFrame {
	private JTable table;
	private DefaultTableModel model;
	//Constructor
	public IdentifierTable(ArrayList<variable_data> thatList) {
		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Sr. No.", "Category", "Name", "Scope", "Type"
				}
			));
		//This setting will enable auto sorting of the table
		table.setAutoCreateRowSorter(true);
		//Storing Table model in a variable model
		model = (DefaultTableModel) table.getModel();
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
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setMaxWidth(50);
		table.setColumnModel(tcm);
		for(int iter1 = 0; iter1 < thatList.size(); iter1++)
		{
			Object[] row = {model.getRowCount(),thatList.get(iter1).category,thatList.get(iter1).name,thatList.get(iter1).scope,thatList.get(iter1).type};
			model.addRow(row);
		}
	}
	public JTable retTable()
	{
		return this.table;
	}
}
