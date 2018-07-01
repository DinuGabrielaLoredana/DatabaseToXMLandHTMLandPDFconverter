package external_lib;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationLogIn implements ActionListener{
	JFrame mainFrame;
	static JTextField databaseField;
	static JTextField userField;
	static JTextField passwordField;
	JLabel databaseLabel,userLabel,passwordLabel;
	JButton connectButton;
	 ApplicationLogIn() throws FileNotFoundException, UnsupportedEncodingException, SQLException
	    {
	        mainFrame=new JFrame("Log In");
	        connectButton = new JButton("Connect");
	        
	        
	        databaseLabel = new JLabel("Database:   ");
			userLabel = new JLabel("User:   ");
			passwordLabel = new JLabel("Password:   ");
			databaseField = new JTextField();
			userField = new JTextField();
			passwordField = new JTextField();
		
		
			
	        this.setBounds();
	        this.setLayout();
	        this.addActionListener();
	        this.addToFrame();
	  
	        
	    }
	    
	private void addToFrame() {
		mainFrame.add(databaseLabel);
		mainFrame.add(userLabel);
		mainFrame.add(passwordLabel);
		mainFrame.add(databaseField);
		mainFrame.add(userField);
		mainFrame.add(passwordField);
		mainFrame.add(connectButton);
		
		
	}

	private void addActionListener() {
		connectButton.addActionListener(this);
		
	}

	private void setLayout() {
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		mainFrame.setSize(500,500);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
	}

	private void setBounds() {
		databaseLabel.setBounds(30,-90,100,300);
    	userLabel.setBounds(30,100,100,30);
    	passwordLabel.setBounds(30,200,100,30);
    	databaseField.setBounds(100,50,300,30);
    	userField.setBounds(100,100,300,30);
    	passwordField.setBounds(100,200,300,30);
    	connectButton.setBounds(250,410,140,40);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == connectButton) {
			String database = databaseField.getText();
			String user = userField.getText();
			String password =""+passwordField.getText();
			try {
				getTablesToFile(database,user,password);
			} catch (FileNotFoundException | UnsupportedEncodingException | SQLException e2) {
				
				e2.printStackTrace();
			}
			try {
				new Application();
				mainFrame.dispose();
			} catch (FileNotFoundException | UnsupportedEncodingException | SQLException e1) {
				
				e1.printStackTrace();
			}
		}
		
	}
	public  static void getTablesToFile(String database,String user,String pass) throws SQLException, FileNotFoundException, UnsupportedEncodingException {
	   	 
	  	  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/"+database,user,pass);
	  	  PrintWriter writer = new PrintWriter("TablesInDatabase.txt", "UTF-8");
	  	  DatabaseMetaData md = con.getMetaData();
	  	  ResultSet rs = md.getTables(database, null, null, null);
	  	  while (rs.next()) {
	  		  writer.println(rs.getString("TABLE_NAME") );
	  	  }
	  	  
	  	  writer.close();
	    
	    }
	
	 

}
