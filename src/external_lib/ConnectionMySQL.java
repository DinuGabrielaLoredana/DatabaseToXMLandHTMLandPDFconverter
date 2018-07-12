package external_lib;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.*;
import javax.xml.transform.*;
import com.lowagie.text.DocumentException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;


public class ConnectionMySQL {
	static String database = ApplicationLogIn.databaseField.getText();
	static String user = ApplicationLogIn.userField.getText();
	@SuppressWarnings("deprecation")
	static String password =""+ApplicationLogIn.passwordField.getText();

  public static String ShowTable(String table) throws SQLException {
	  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/"+database,user,password);
	  String query= "SELECT * FROM "+table;
	  String ret = "";
	  Statement stmt = con.createStatement();
	  ResultSet rs =  stmt.executeQuery(query);
	  ResultSetMetaData rsmd = rs.getMetaData();
	  ret = ret + rsmd.getColumnName(1)+" "+rsmd.getColumnName(2)+" "+rsmd.getColumnName(3)+" "+rsmd.getColumnName(4)+"\n";
	  while(rs.next()) {
		  ret=ret+(rs.getInt(rsmd.getColumnName(1))+" "+rs.getString(rsmd.getColumnName(2)) + " "+rs.getString(rsmd.getColumnName(3))+" "+rs.getString(rsmd.getColumnName(4))+"\n");
	  }
	  return ret;
  }
  
  public static void AddToTable(String table,int ID,String Name,String Surname,int Grade) throws SQLException {
	  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/"+database,user,password);
	  ResultSet rs = con.createStatement().executeQuery("select * from "+table);
	  ResultSetMetaData rsmd = rs.getMetaData();
	  String query = " insert into "+table+" ("+rsmd.getColumnName(1)+","+rsmd.getColumnName(2)+","+rsmd.getColumnName(3)+","+rsmd.getColumnName(4)+")"
		        + " values (?, ?, ?, ?)";
		      PreparedStatement preparedStmt = con.prepareStatement(query);
		      preparedStmt.setInt (1, ID);
		      preparedStmt.setString (2,Name);
		      preparedStmt.setString(3, Surname);
		      preparedStmt.setInt    (4, Grade);
		      preparedStmt.execute();
	
	 
  }
  
  public static void DeleteFromTable(String table,int ID) throws SQLException {
	  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/"+database,user,password);  
	  PreparedStatement st = con.prepareStatement("DELETE FROM "+ table+" WHERE ID ="+ID);
	  st.execute();
	  	 
  }
  
  public static void MakeXML(String table, String path1) throws ParserConfigurationException, SQLException, TransformerException {
	  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder builder = factory.newDocumentBuilder();
	  Document doc = builder.newDocument();
	  Element results = doc.createElement("Table");
	  doc.appendChild(results);
	  
	  Connection con = DriverManager.getConnection("jdbc:mysql://localhost/"+database,user,password);
	  ResultSet rs = con.createStatement().executeQuery("select * from "+table);
	  ResultSetMetaData rsmd = rs.getMetaData();
	  int colCount = rsmd.getColumnCount();
	  while (rs.next()) {
	      Element row = doc.createElement("Row");
	      results.appendChild(row);
	      for (int i = 1; i <= colCount; i++) {
	        String columnName = rsmd.getColumnName(i);
	        Object value = rs.getObject(i);
	        Element node = doc.createElement(columnName);
	        node.appendChild(doc.createTextNode(value.toString()));
	        row.appendChild(node);
	      }
	   }
	  
	  
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    DOMSource domSource = new DOMSource(doc);
	    StreamResult file = new StreamResult(new File(path1));
	    transformer.transform(domSource, file);
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    StringWriter sw = new StringWriter();
	    StreamResult sr = new StreamResult(sw);
	    transformer.transform(domSource, sr);
	    con.close();
	    rs.close();
  }
  
  
 
    public static void XmlToHtml(String table,String path1) throws ParserConfigurationException, SQLException, TransformerException, DocumentException, IOException{
    	String path = "xmlFile.xml";
    	MakeXML(table,path);
    	
    try {
        TransformerFactory tFactory=TransformerFactory.newInstance();

        Source xslDoc=new StreamSource("xslFile.xsl");
        Source xmlDoc=new StreamSource("xmlFile.xml");

        String outputFileName=path1;

        OutputStream htmlFile=new FileOutputStream(outputFileName);
        Transformer trasform=tFactory.newTransformer(xslDoc);
        trasform.transform(xmlDoc, new StreamResult(htmlFile));
    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
	    catch (TransformerConfigurationException e) 
	    {
	        e.printStackTrace();
	    }
	    catch (TransformerFactoryConfigurationError e) 
	    {
	        e.printStackTrace();
	    }
	    catch (TransformerException e) 
	    {
	        e.printStackTrace();
	    }

    
  }   
     public static void xmlToPdf(String table,String path1) throws TransformerException, DocumentException, IOException, FOPException, ParserConfigurationException, SQLException {
    	String path = "xmlFile.xml";
    	MakeXML(table,path);
    	File xsltFile = new File("xslFOfile.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File("xmlFile.xml"));
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance();
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        
        // Setup output
        OutputStream out;
        out = new java.io.FileOutputStream(path1);

        try {
        	fopFactory.setStrictValidation(false);
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            	
            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to
            // FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then
            // PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }



}
    
    	    
    
