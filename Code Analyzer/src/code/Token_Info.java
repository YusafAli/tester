package code;
/**
 * 
 * @author Yusaf Ali
 *	This class is used to store information of a token.
 *	An ArrayList will be created in order to store all tokens.
 *	In use by
 *		LineCommentTable
 *		Token_Extractor
 *		Token_Table
 *		Main_menu
 */
public class Token_Info {
	//Name of the token
	public String token;
	//These next three are used to store location of the token
	public int length;
	public int colNum;
	public int lineNum;
	//To store the next like in a list maybe used don't see how for now
	public Token_Info next;
	public Token_Info prev;
	//This variable will comment on the context of the line
	public boolean isClassObject;
	public boolean isStructObject;
	public boolean isClassName;
	public boolean isStructName;
	public boolean isClassDataMember;
	public boolean isStructDataMember;
	public boolean isParameter;
	public boolean isConstant;
	public boolean isGlobal;
	public boolean isLocal;
	public boolean isVariable;
	public boolean isFunction;
	public String scope, category, type;
	public Token_Info() {
		token = null;
		
	}
}
/*
 * Scope can contain
	 * Parameter, For, If, ClassPublic, ClassPrivate, Global
 * Category can contain
	 * Function, Constant, Variable, Parameter, Not
 * Type can contain
	 * int, float, double, char, string
 * kfafaf
 * safnkfs
 */