package net.krglok.realms.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Build a predefined SQLite connection for the plugin realms.
 * 
 *  
 * - as external tool you can use SQLIte Database Browser from http://sqlitebrowser.sourceforge.net
 * 
 * @author Windu
 *
 */
public class SQliteConnection
{
	
	private boolean isOpen = false;
	private Connection connection;
	private String path = "";
	private String dbName = "realms";
	private String fileName = dbName+".db";
	private File file ;
	private Statement statement;

	/**
	 * Holder for the last update count by a query.
	 */
	protected int lastUpdate;

	public SQliteConnection()
	{
		this.path = "";
		this.file = null;
	}

	public SQliteConnection(String pathName)
	{
		this.path = pathName;
		setFile();
	}
	
	
	protected void setFile()  
	{
		File folder;
		try
		{
			folder = new File(this.path);
			if (!folder.exists())
			{
				folder.mkdir();
			}
			file = new File(folder.getAbsolutePath() + File.separator + fileName);
		} catch (Exception e)
		{
			this.file = null;
			System.out.println("SetFile: " + e);
			System.out.println("[REALMS] setFile sqlite,JDBC");
		}
	}

	protected boolean initialize() {
		try {
			Class.forName("org.sqlite.JDBC");
		  return true;
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found in initialize(): " + e);
			System.out.println("[REALMS] init sqlite,JDBC");
		  return false;
		}
	}

	/**
	 * open connection to database with jdbc for SQLite
	 * @return
	 * @throws SQLException 
	 */
	public boolean open() throws SQLException 
	{
		
		if (initialize()) 
		{
			connection = DriverManager.getConnection("jdbc:sqlite:" + (file == null ? ":memory:" : file.getAbsolutePath()));
			this.statement  = connection.createStatement();
			return true;
		} else 
		{
			return false;
		}
	}

	/**
	 * check for tablename in database Metadat
	 * 
	 * @param tableName
	 * @return true if  found
	 * @throws SQLException 
	 */
	public boolean isTable(String tableName) throws SQLException
	{
		DatabaseMetaData md = null;
		md = this.connection.getMetaData();
		ResultSet tables = md.getTables(null, null, tableName, null);
		if (tables.next()) 
		{
			tables.close();
			return true;
		} else {
			tables.close();
			return false;
		}
	}
	
	/**
	 * execute query 
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public ResultSet query(String query) throws SQLException
	{
		return this.statement.executeQuery(query);
	}
	
	/**
	 * execute insert, return no resultset 
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public boolean insert(String sql) throws SQLException
	{
//		PreparedStatement pQuery = connection.prepareStatement(sql);
//		pQuery.executeUpdate();
//		return true;
	    if (this.statement.executeUpdate(sql) > 0) 
	    {
	    	return true;
	    }
		return false;
	}

	/**
	 * execute upate, return no resltset
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public boolean update(String sql) throws SQLException
	{
		if (this.statement.executeUpdate(sql) > 0)
		{
	    	return true;
	    }
		return false;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(String sql) throws SQLException
	{
		if (this.statement.executeUpdate(sql) > 0)
		{
	    	return true;
	    }
		return false;
	}
	
	
	public boolean insertBatch()
	{
	
		
		return false;
	}
	
	
	public boolean isOpen()
	{
		return isOpen;
	}

	public void setOpen(boolean isOpen)
	{
		this.isOpen = isOpen;
	}

	public Connection getConnection()
	{
		return connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}

	public String getPath()
	{
		return this.path;
	}

	public String getFileName()
	{
		return this.fileName;
	}

	public String getDbName()
	{
		return this.dbName;
	}
	
}
