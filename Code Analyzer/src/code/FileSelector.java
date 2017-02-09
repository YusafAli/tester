package code;

import java.awt.Component;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileSelector {
	private Component razor1 = null;
	private String addrOfFile = null;
	
	public FileSelector(JPanel contentPane_1) {
		razor1 = contentPane_1;
	}

	public void openAFile()
	{
		JFileChooser newFile = new JFileChooser();
		//FileFilter filter = null;
		//filter;
		//newFile.addChoosableFileFilter(filter);
		int fileVariable = newFile.showOpenDialog(razor1);
		if(fileVariable == JFileChooser.APPROVE_OPTION)
		{
			addrOfFile = newFile.getName(newFile.getSelectedFile());
			addrOfFile = newFile.getCurrentDirectory().toString() + "\\" + addrOfFile;
		}
		else
		{
			addrOfFile = "None Selected";
		}
	}

	public String getAddressOfFile() {
		return addrOfFile;
	}
}
