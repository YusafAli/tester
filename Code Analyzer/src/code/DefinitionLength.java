//Change name of this class and use it for any other module
//Like counting the number of lines in a function.
package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DefinitionLength
{
	private String addrOfFile;
	private String patternFunctionStart = new String("");
	private float TotalFunctions;
	private float CorrectFunctions;
	public DefinitionLength(String addrOfFile)
	{
		this.addrOfFile = new String(addrOfFile);
	}
	public void doProcess() throws IOException
	{
		File theFile = new File(this.addrOfFile);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String tempStr = new String("");
		while((tempStr = br.readLine()) != null)
		{
			
		}
		br.close();
	}
	public String getResult()
	{
		return "";
	}
}