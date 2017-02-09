package code;
/**
 * 
 * @author Yusaf Ali
 *	In use by
 *		EvaluateIdentifiers
 *		IdentifierTable
 *		LineCommentTable
 *		M_Identifier_Detector
 *		M_PercentComment
 *		Main_menu
 */
public class variable_data {
	//Data type or return type
	public String type = new String("");
	//Name of the identifier
	public String name = new String("");
	//Category = Function, Constant, Variable, Parameter, Not
	public String category = new String("Not");
	//Scope = Global or Local or Class
	public String scope = new String("Not");
}
