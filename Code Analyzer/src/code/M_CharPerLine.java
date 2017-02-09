package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class M_CharPerLine {
	private String fileAddress = new String("");
	private float invalidLines = 0;
	private float totalLines = 0;
	
	public M_CharPerLine(String fileAddr)
	{
		fileAddress = new String(fileAddr);
	}
	public void doProcess() throws IOException
	{
		File theFile = new File(fileAddress);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String tempStr = new String("");
		while((tempStr = br.readLine()) != null)
		{
			totalLines++;
			if(tempStr.length() > 80)
			{
				invalidLines++;
			}
		}
		br.close();
	}
	public float getBigLineCount()
	{
		return invalidLines;
	}
	public float getTotalLineCount()
	{
		return totalLines;
	}
}
