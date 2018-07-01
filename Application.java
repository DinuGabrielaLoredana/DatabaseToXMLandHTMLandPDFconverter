package external_lib;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;

import com.lowagie.text.DocumentException;
public class Application implements ActionListener{
	
	static String[] tableStrings = new String[10];
	JComboBox<String> tableList;
	JFrame frame;
	static JTextArea bigTextField;
    JTextField nameField, idField,surnameField,gradeField, pathField;
    JLabel subjectLabel,surnameLabel,idLabel,nameLabel, tableLable,pathLabel;
    JButton addButton,clearButton, generateXML,generatePDF,generateHTML, deleteButton,showButton;
   
    Application() throws FileNotFoundException, UnsupportedEncodingException, SQLException
    {
        frame=new JFrame("Aplicatie");
        showButton = new JButton("Show Table");
        generateSelections();
        tableList = new JComboBox<String>(tableStrings);
        tableList.setSelectedIndex(0);
        idLabel = new JLabel("ID:   ");
		nameLabel = new JLabel("Name:   ");
		surnameLabel = new JLabel("Surname:   ");
		subjectLabel = new JLabel("Grade:   ");
		tableLable = new JLabel("Table from database     ");		
		idField = new JTextField(10);
		nameField = new JTextField(20);
		surnameField  = new JTextField(20);
		gradeField  = new JTextField(100);
		addButton = new JButton("Add"); 
        clearButton=new JButton("Clear"); 
        deleteButton = new JButton("Delete");
        bigTextField = new JTextArea();      
        pathLabel= new JLabel("path to save to  ");
        pathField = new JTextField();
        generateXML = new JButton("Generate XML");
        generatePDF= new JButton("Generate PDF");
        generateHTML= new JButton("Generate HTML");
        this.setBounds();
        this.setLayout();
        this.addActionListener();
        this.addToFrame();
  
        
    }
    
	public void setBounds() {
    	idLabel.setBounds(30,50,20,30);
    	nameLabel.setBounds(30,50,100,110);
    	surnameLabel.setBounds(30,50,180,190);
    	subjectLabel.setBounds(30,50,300,310);
    	tableLable.setBounds(30,200,400,100);
    	idField.setBounds(100,55,300,30);
    	nameField.setBounds(100,95,300,30);
    	surnameField.setBounds(100,135,300,30);
    	bigTextField.setBounds(500,0,500,1000);
    	gradeField.setBounds(100,190,300,30);
    	tableList.setBounds(170,233,300,30);
        addButton.setBounds(130,310,100,40);
        deleteButton.setBounds(350,310 ,100, 40);
        clearButton.setBounds(240,310,100,40);
        pathLabel.setBounds(110,430,100,40);
        pathField.setBounds(180,475,220,30);
        generateXML.setBounds(50,510,140,40);
        generatePDF.setBounds(200,510,140,40);
        generateHTML.setBounds(350,510,140,40);
        showButton.setBounds(20, 310, 100, 40);
    }
    
    public void addToFrame() {
    	 frame.add(idLabel);
    	 frame.add(nameLabel);
    	 frame.add(surnameLabel);
    	 frame.add(subjectLabel);
    	 frame.add(idField);
    	 frame.add(nameField);
    	 frame.add(surnameField);
    	 frame.add(gradeField);
    	 frame.add(bigTextField);
    	 frame.add(addButton);
         frame.add(clearButton);
         frame.add(tableList);
         frame.add(tableLable);
         frame.add(pathLabel);
         frame.add(pathField);
         frame.add(generateXML);
         frame.add(generatePDF);
         frame.add(generateHTML);
         frame.add(deleteButton);
         frame.add(showButton);
    }
    public void setLayout() {
    	frame.setLayout(null);
        frame.setVisible(true);
        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    public void addActionListener() {
    	addButton.addActionListener(this);
    	generateXML.addActionListener(this);
    	generatePDF.addActionListener(this);
    	generateHTML.addActionListener(this);
        clearButton.addActionListener(this);
        tableList.addActionListener(this);
        deleteButton.addActionListener(this);
        showButton.addActionListener(this);
        
    }
    public void actionPerformed(ActionEvent e)
    {
       String path = pathField.getText();
       String selectedTable = tableList.getSelectedItem().toString(); 

    	if(e.getSource()==generatePDF) {
    		selectedTable = tableList.getSelectedItem().toString();
    		try {
				ConnectionMySQL.HtmlToPdf(selectedTable,path);
			} catch (FOPException | TransformerException | DocumentException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
        
        if(e.getSource()==generateXML)
	        {
	        	selectedTable = tableList.getSelectedItem().toString();
		        try {
					ConnectionMySQL.MakeXML(selectedTable,path);
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (TransformerException e1) {
					e1.printStackTrace();
				}
	        }
	        
        if(e.getSource()==generateHTML)
	        {
	        	selectedTable = tableList.getSelectedItem().toString();
	        	try {
				ConnectionMySQL.XmlToHtml(selectedTable,path);
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (TransformerException e1) {
					e1.printStackTrace();
				} catch (DocumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        }
      
        if(e.getSource()==clearButton) {
	            bigTextField.setText("");
	            nameField.setText("");
	            surnameField.setText("");
	            idField.setText("");
	            gradeField.setText("");
           }
        if(e.getSource()==addButton) {
	        	selectedTable = tableList.getSelectedItem().toString();
	        	String Name = nameField.getText();
	        	String Surname = surnameField.getText();
	        	int grade =Integer.parseInt(gradeField.getText());
	        	int ID = Integer.parseInt(idField.getText());
	        	try {
					ConnectionMySQL.AddToTable(selectedTable, ID, Name, Surname, grade);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        			
        }
        
        if(e.getSource()==deleteButton) {
        	selectedTable = tableList.getSelectedItem().toString();
        	int ID = Integer.parseInt(idField.getText());
        	try {
				ConnectionMySQL.DeleteFromTable(selectedTable, ID);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        			
        }
        
        if(e.getSource()==showButton) {
        	selectedTable = tableList.getSelectedItem().toString();
        	 String rezult="";
			try {
				rezult = ConnectionMySQL.ShowTable(selectedTable);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	 bigTextField.setText(rezult);
        }
        
       
    }
    
    private void generateSelections() throws FileNotFoundException, UnsupportedEncodingException, SQLException {
    	 
		 int i=0;
	        String fileName = "TablesInDatabase.txt";
	        String line = null;

	        try {
	            FileReader fileReader = new FileReader(fileName);
	            BufferedReader bufferedReader = new BufferedReader(fileReader);
	            while((line = bufferedReader.readLine()) != null) {
					tableStrings[i] = line;
	                i++;
	            }   
	            bufferedReader.close(); } 
	            catch(IOException ex) {
	                System.out.println(
	                    "Error reading file '"+ fileName + "'");                  
	             
	            }
		
	}
	 public static void main(String args[]) throws SQLException, ParserConfigurationException, TransformerException, DocumentException, IOException  {
		
		 
	     new ApplicationLogIn();
		 
		
	
	 }	 
}
