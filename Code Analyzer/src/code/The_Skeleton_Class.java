//Using this class as a skeleton for all other classes
//Just copying it pasting in other class bodies
package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * This class is not meant to be used.
 * Will be removed in the future
 * @author Yusaf Ali
 *	In use by
 */
public class The_Skeleton_Class {
	private String fileAddress = new String("");
	
	public void setfileAddress(String fileAddr)
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
		}
		br.close();
	}
	public String getResult()
	{
		return "";
	}
}
