package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import code.EvaluateIdentifiers;
import code.FileSelector;
import code.IdentifierTable;
import code.LineCommentTable;
import code.M_ExpressionSpacing;
import code.M_CharPerLine;
import code.M_Identifier_Detector;
import code.M_PercentComment;
import code.Token_Extractor;
import code.Token_Info;
import code.Token_Table;
import code.variable_data;

import java.io.IOException;
import java.util.ArrayList;
import java.awt.CardLayout;


@SuppressWarnings("serial")
public class Main_menu extends JFrame {

	static Main_menu frame = null;
	private CardLayout cl = new CardLayout();
	
	//Jpanel1 Components
	private JPanel contentPane;
	private JPanel panel_1 = new JPanel();
	private JPanel panel_2 = new JPanel();
	private JPanel panel_3 = new JPanel();
	private JTextField fileAddressField;
	private JButton btnAnalyze;
	private JButton btnBrowseFile;
	private String addrOfFile = new String("");
	
	//Jpanel2 Components
	private JScrollPane scrollPane;
	private JTextArea textArea;
	
	//Jpanel3 Components - JTextFields
	private JTextField textFieldFunction;
	private JTextField textFieldVariable;
	private JTextField textFieldConstant;
	private JTextField textFieldComment;
	private JTextField textFieldSpaceAssignment;
	private JTextField textFieldSpaceRelational;
	private JScrollPane scrollPane_1;
	
	//These are the modules. They are storing information that can be accessed by any function in this .java
	//The mod4 is not actually a mod, it is just for information regarding tokens plus a little bit usage in output visualization for the developer.
	//mod4 might be removed in the near future because the end user does not require this kind of information
	private M_PercentComment mod1;
	private M_Identifier_Detector mod2;
	private M_CharPerLine mod3;
	private Token_Extractor mod4;
	private M_ExpressionSpacing mod5;
	//These two store the lists, tokens and variables. Tokens wali is not fully implemented.
	//Variables list does not store all the information
	//private ArrayList<Token_Info> listofTokens; Not currently in use
	//private ArrayList<variable_data> listofIdentifiersAll;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					frame = new Main_menu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Main_menu(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Code Analyzer");
		setResizable(false);
		setBounds(50, 50, 950, 500);
		setVisible(true);
		setGui();
	}
	
	private void setGui() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(cl);
		
		setPanel1();
		setPanel2();
		setPanel3();
		
		contentPane.add(panel_1, "1");
		contentPane.add(panel_2, "2");
		contentPane.add(panel_3, "3");
		
		
		
		
		cl.show(contentPane, "1");
		
	}
	
	private void setPanel1()
	{
		panel_1.setLayout(null);
		
		btnAnalyze = new JButton("Analyze");
		btnAnalyze.setBounds(736, 428, 89, 23);
		panel_1.add(btnAnalyze);
		
		btnBrowseFile = new JButton("Browse File");
		btnBrowseFile.setBounds(837, 238, 87, 23);
		panel_1.add(btnBrowseFile);
		
		fileAddressField = new JTextField();
		fileAddressField.setBounds(10, 238, 815, 23);
		panel_1.add(fileAddressField);
		
		JTextArea txtrPleaseSelectA = new JTextArea();
		txtrPleaseSelectA.setBounds(10, 50, 908, 69);
		panel_1.add(txtrPleaseSelectA);
		txtrPleaseSelectA.setBackground(SystemColor.menu);
		txtrPleaseSelectA.setEditable(false);
		txtrPleaseSelectA.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrPleaseSelectA.setWrapStyleWord(true);
		txtrPleaseSelectA.setText("This program analyzes CPlusPlus source code."
				+ "\r\n\r\nSelect a source code file with the extension of cpp."
				+ " You can also type the address of your file, press enter after that.");
		txtrPleaseSelectA.setLineWrap(true);
		
		JButton btnCancel = new JButton("Exit");
		btnCancel.setBounds(835, 428, 89, 23);
		panel_1.add(btnCancel);
		
		JLabel lblNewLabel = new JLabel("Code Analyzer");
		lblNewLabel.setBounds(10, 11, 177, 45);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Traditional Arabic", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e4)
			{
				System.exit(0);
			}
		});
		
		fileAddressField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3) 
			{
				addrOfFile = new String(fileAddressField.getText());
			}
		});
		
		btnBrowseFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e1)
			{
				FileSelector obj1 = new FileSelector(contentPane);
				obj1.openAFile();
				addrOfFile = new String(obj1.getAddressOfFile());
				fileAddressField.setText(addrOfFile);
			}
		});
		
		btnAnalyze.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e2)
			{
				if(addrOfFile.isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please select a file");
				}
				else 
				{
					readyForPanel2();
					cl.show(contentPane, "2");
				}
			}
		});
	}
	
	private void setPanel2()
	{
		JButton btnBack = new JButton("Back");
		scrollPane = new JScrollPane();
		JButton btnPercentComment = new JButton("Comments");
		JButton btnVariable = new JButton("Identifiers");
		JButton btnTokenInfo = new JButton("Token Information");
		JButton btnLineSize = new JButton("Line Size");
		JTextArea lblClickOnThe = new JTextArea("Here you can see information regarding the source code such as, lines containing a comment,"
				+ " list of identifiers, all tokens in the source code and their location.");
		lblClickOnThe.setLineWrap(true);
		lblClickOnThe.setWrapStyleWord(true);
		lblClickOnThe.setBackground(UIManager.getColor("menu"));
		lblClickOnThe.setEditable(false);
		
		scrollPane.setBounds(169, 64, 755, 353);
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		btnPercentComment.setBounds(10, 64, 125, 25);
		btnPercentComment.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e1)
			{
				LineCommentTable mod1Table = new LineCommentTable(mod1.retLocList());
				scrollPane.setViewportView(mod1Table.retTable());
			}
		});

		btnVariable.setBounds(10, 100, 125, 25);
		btnVariable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e2)
			{
				IdentifierTable mod2Table = new IdentifierTable(mod2.accessValues());
				scrollPane.setViewportView(mod2Table.retTable());
			}
		});
		

		btnLineSize.setBounds(10, 136, 125, 25);
		btnLineSize.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				scrollPane.setViewportView(textArea);
				textArea.setText("Number of lines exceeding 80 characters = " + Float.toString(mod3.getBigLineCount()));
			}
		});
		
		btnTokenInfo.setBounds(10, 172, 125, 25);
		btnTokenInfo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e2)
			{
				Token_Table mod4Table = new Token_Table(mod4.returnListOfTokens());
				scrollPane.setViewportView(mod4Table.retTable());
			}
		});
		
		lblClickOnThe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblClickOnThe.setBounds(10, 11, 914, 42);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(835, 428, 89, 23);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		
		btnBack.setBounds(637, 428, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileAddressField.setText(null);
				cl.show(contentPane, "1");
			}
		});
		
		JButton btnEvaluate = new JButton("Evaluate");
		btnEvaluate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				readyForPanel3();
				cl.show(contentPane, "3");
			}
		});
		btnEvaluate.setBounds(736, 428, 89, 23);

		JButton btnSpaceAssign = new JButton("Assignment Operators");
		btnSpaceAssign.setBounds(10, 208, 125, 25);
		btnSpaceAssign.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e2)
			{
				scrollPane.setViewportView(mod5.tb1);
			}
		});
		
		JButton btnSpaceRelate = new JButton("Relational Operators");
		btnSpaceRelate.setBounds(10, 244, 125, 25);
		btnSpaceRelate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e2)
			{
				scrollPane.setViewportView(mod5.tb2);
			}
		});
		panel_2.setLayout(null);
		
		panel_2.add(btnSpaceRelate);
		panel_2.add(btnSpaceAssign);
		panel_2.add(btnEvaluate);
		panel_2.add(btnVariable);
		panel_2.add(btnPercentComment);
		panel_2.add(lblClickOnThe);
		panel_2.add(scrollPane);
		panel_2.add(btnExit);
		panel_2.add(btnBack);
		panel_2.add(btnTokenInfo);
		panel_2.add(btnLineSize);
		
	}
	
	private void readyForPanel2()
	{
		//First Part that is used to find the numberOfComments, TotalLOC
		mod1 = new M_PercentComment();
		mod1.setfileAddress(addrOfFile);
		try {mod1.doProcess();} catch (IOException exception1) {exception1.printStackTrace();}
		
		//Second Part that is used to find the Identifiers
		mod2 = new M_Identifier_Detector();
		mod2.setfileAddress(addrOfFile);
		try {mod2.doProcess();} catch (IOException exception1) {exception1.printStackTrace();}
		//listofIdentifiersAll = mod2.accessValues(); Currently not needed
		
		//Third Part that is used to show that how many lines have more than 80 characters
		mod3 = new M_CharPerLine(addrOfFile);
		try {mod3.doProcess();} catch (IOException exception1) {exception1.printStackTrace();}
		
		//Fourth Part that is used to show all tokens in the code, Tokenizing is done according to space as a seperation character
		mod4 = new Token_Extractor(addrOfFile);
		try {mod4.doProcess();} catch (Exception exception1) {exception1.printStackTrace();}
		
		//This part is actually used in the third card. Checks Assignment Operator and Relational Operator Spacings
		mod5 = new M_ExpressionSpacing(addrOfFile);
		mod5.doProcess();
	}
	
	private void setPanel3()
	{
		panel_3.setLayout(null);
		
		JLabel lblFunctionNames = new JLabel("Function Names");
		lblFunctionNames.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFunctionNames.setBounds(20, 50, 150, 14);
		
		JLabel lblVariableNames = new JLabel("Variable Names");
		lblVariableNames.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblVariableNames.setBounds(20, 81, 150, 14);
		
		JLabel lblConstantNames = new JLabel("Constant Names");
		lblConstantNames.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblConstantNames.setBounds(20, 111, 150, 14);
		
		JLabel lblParameterNames = new JLabel("Parameter Names");
		lblParameterNames.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblParameterNames.setBounds(20, 142, 150, 14);
		
		JLabel lblSpacingAssignmentOperators = new JLabel("Spacing: Assignment Operators");
		lblSpacingAssignmentOperators.setBounds(20, 173, 150, 14);

		JLabel lblSpacingRelationalOperators = new JLabel("Spacing: Relational Operators");
		lblSpacingRelationalOperators.setBounds(20, 204, 150, 14);
		
		this.textFieldFunction = new JTextField();
		this.textFieldVariable = new JTextField();
		this.textFieldConstant = new JTextField();
		this.textFieldComment = new JTextField();
		this.textFieldSpaceAssignment = new JTextField();
		
		
		textFieldFunction.setEditable(false);
		textFieldFunction.setBounds(205, 47, 160, 20);
		textFieldFunction.setColumns(10);
		
		textFieldVariable.setEditable(false);
		textFieldVariable.setBounds(205, 78, 160, 20);
		textFieldVariable.setColumns(10);
		
		textFieldConstant.setEditable(false);
		textFieldConstant.setBounds(205, 109, 160, 20);
		textFieldConstant.setColumns(10);
		
		textFieldComment.setEditable(false);
		textFieldComment.setBounds(205, 140, 160, 20);
		textFieldComment.setColumns(10);

		textFieldSpaceAssignment.setEditable(false);
		textFieldSpaceAssignment.setColumns(10);
		textFieldSpaceAssignment.setBounds(205, 171, 160, 20);
		
		textFieldSpaceRelational = new JTextField();
		textFieldSpaceRelational.setEditable(false);
		textFieldSpaceRelational.setColumns(10);
		textFieldSpaceRelational.setBounds(205, 202, 160, 20);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(375, 47, 549, 370);
		
		panel_3.add(scrollPane_1);
		
		JTextArea txtrResultsAreCalculated = new JTextArea();
		txtrResultsAreCalculated.setBackground(UIManager.getColor("menu"));
		txtrResultsAreCalculated.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtrResultsAreCalculated.setText("Results are calculated on the basis of how many items are correct. The result is in percentage.");
		txtrResultsAreCalculated.setWrapStyleWord(true);
		txtrResultsAreCalculated.setLineWrap(true);
		txtrResultsAreCalculated.setBounds(10, 11, 914, 23);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(835, 428, 89, 23);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JButton btnBack_1 = new JButton("Back");
		btnBack_1.setBounds(736, 428, 89, 23);
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(contentPane, "2");
			}
		});
		panel_3.add(txtrResultsAreCalculated);
		panel_3.add(btnExit);
		panel_3.add(btnBack_1);
		panel_3.add(textFieldFunction);
		panel_3.add(textFieldVariable);
		panel_3.add(textFieldConstant);
		panel_3.add(textFieldComment);
		panel_3.add(textFieldSpaceAssignment);
		panel_3.add(textFieldSpaceRelational);
		
		panel_3.add(lblFunctionNames);
		panel_3.add(lblVariableNames);
		panel_3.add(lblConstantNames);
		panel_3.add(lblParameterNames);
		panel_3.add(lblFunctionNames);
		panel_3.add(lblVariableNames);
		panel_3.add(lblSpacingAssignmentOperators);
		panel_3.add(lblSpacingRelationalOperators);
		
	}
	
	private void readyForPanel3()
	{
		//listofIdentifiersAll = mod2.accessValues();
		EvaluateIdentifiers evalve = new EvaluateIdentifiers(mod2.accessValues(), mod3.getTotalLineCount(), mod3.getBigLineCount(), mod1.retNComment(), mod5.getResult());
		evalve.evaluator();
		scrollPane_1.setViewportView(evalve.retEvTable());
		
		if(Float.toString(evalve.getScoreFunctions()) == "NaN")
			textFieldFunction.setText("No Functions Found");
		else
			textFieldFunction.setText(Float.toString(evalve.getScoreFunctions())+"%");
		if(Float.toString(evalve.getScoreVariables()) == "NaN")
			textFieldVariable.setText("No Variables Found");
		else
			textFieldVariable.setText(Float.toString(evalve.getScoreVariables())+"%");
		if(Float.toString(evalve.getScoreConstants()) == "NaN")
			textFieldConstant.setText("No Constants Found");
		else
			textFieldConstant.setText(Float.toString(evalve.getScoreConstants())+"%");
		if(Float.toString(evalve.getScoreParameters()) == "NaN")
			textFieldComment.setText("No Parameters Found");
		else
			textFieldComment.setText(Float.toString(evalve.getScoreParameters())+"%");
		if(Float.toString(evalve.getScoreSpaceAssign()) == "NaN")
			textFieldSpaceAssignment.setText("0");
		else
			textFieldSpaceAssignment.setText(Float.toString(evalve.getScoreSpaceAssign())+"%");
		if(Float.toString(evalve.getScoreSpaceRelate()) == "NaN")
			textFieldSpaceRelational.setText("0");
		else
			textFieldSpaceRelational.setText(Float.toString(evalve.getScoreSpaceRelate())+"%");
	}
}
//Check if u can do something about coupling and cohesion
//Check if u can do something about branch prediction
//Check if u can do something about visualization of the output data.
	//This can be done by showing the output percentage in histogram if u can implement it in the java.
	//One more thing: Show one bar, blue color shows total and red color shows the shit
//Check if there is anything alternative to regular expression
//------------------------------>>>>> /bSome regex/b
	//This /b thing can create word boundaries that might be helpful.
//Nigga! u got a site that documents the use of regular expressions