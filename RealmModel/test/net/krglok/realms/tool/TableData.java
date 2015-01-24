package net.krglok.realms.tool;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.krglok.realms.data.SQliteConnection;



/**
 * <pre>
 * Abstract class for storage of YML data in SQLite Database
 * use the SQLibrary for database handling
 *
 * make basic datahandling
 * - insert
 * - update
 * - delete
 * - select
 * 
 * Should be use a single sqlconnection !
 * 
 * Timing Hint:
 * - insert/update need 250-450ms per record
 * - select need 3-5 ms per record
 * - uese this as relative values not a fixed value for each computer 
 * - measured on i7 with 16GB RAM and with SQLite 3.x
 * - you can use SQLIte Database Browser from http://sqlitebrowser.sourceforge.net
 *  
 * @author Windu
 * </pre>
 */
public abstract class TableData 
{
	protected SQliteConnection sql;
	public String tablename ;
	public String[] fieldnames; 
	public String[] fieldtypes; 
	public String[] indexnames; 
	public String[] indexfields;
//	public ResultSet resultSet;
	
	/**
	 * the Database must be given to make automatic access
	 * @param sql
	 */
	public TableData(SQliteConnection sql, String tableName)
	{
		super();
		this.sql = sql;
		tablename = tableName;
		fieldnames = null;
		fieldtypes = null;
		indexnames = null;
		indexfields = null;
	}

	/**
	 * delete single quote from String
	 * @param value
	 * @return 
	 */
	private static String prepareString(String value)
	{
		String result = "";
		String sChar = "'";
		if (value.contains(sChar) == false)
		{
			return value;
		}
		for (int i=0; i < value.length(); i++)
		{
			if (value.charAt(i) != sChar.charAt(0))
			{
				result = result + value.charAt(i);
			}
		}
		return result;
	}
	
	public String getFieldNames()
	{
		String result = fieldnames[0];
		for (int i=1; i < fieldnames.length; i++)
		{
			result = result + ", "+fieldnames[i];
		}
		return result;
	}
	
	/**
	 * Bound a text into a SQL Quotation
	 * delete single quotes from text
	 * @param value
	 * @return
	 */
	public static String makeSqlString(String value)
	{
		return "'"+prepareString(value)+"'";
	}

	
	/**
	 * Check the table if exist in Database
	 * should be used in derived class on instances 
	 * @return
	 */
	public boolean checkTable()
	{
		if (this.tablename != "")
		{
			try
			{
				return sql.isTable(tablename);
			} catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println("[REALMS] SQL table not found "+tablename);
			}
		}
		System.out.println("[REALMS] SQL table not found "+tablename);
		return false;
	}
	
	private boolean existTabel(String tableName)
	{
		
		return false;
	}
	
	/**
	 * ctreate a table based on the definitions.
	 * automatic rowid used as PRIMARY KEY
	 * @return
	 */
	protected boolean createTable()
	{
		if (this.tablename == "")
		{
			System.out.println("[REALMS] SQL tablename is empty ! ");
			return false;
		}
		if (this.fieldnames == null)
		{
			System.out.println("[REALMS] SQL NO fieldnames defined "+tablename);
			return false;
		}
		for (String fieldname : this.fieldnames)
		{
			if (fieldname == "")
			{
				System.out.println("[REALMS] SQL empty fieldname "+tablename);
				return false;
			}
		}
		if (this.indexnames == null)
		{
			System.out.println("[REALMS] Warning, SQL NO index defined "+tablename);
		}
		String fielddefs = makeFieldDefs();
		String query = "CREATE TABLE IF NOT EXISTS "+tablename+ " ("+fielddefs+") ";
		try {
			sql.query(query);
			System.out.println("[REALMS] SQL TABLE created "+this.tablename);
			if (sql.isTable(this.tablename) == true)
			{
				// make the indexes
				if (this.indexnames != null)
				{
					for (int i=0; i < this.indexnames.length; i++)
					{
					  createIndex(i);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	private void createIndex(int index)
	{
		if (index < this.indexfields.length)
		{
			String query = "CREATE INDEX IF NOT EXISTS "+this.indexnames[index] +" ON "+ this.tablename + " ("+this.indexfields[index]+") ";
			try {
				sql.query(query);
				System.out.println("[REALMS] SQL INDEX created "+this.tablename+":"+this.indexnames[index]);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("[REALMS] SQL INDEX not created "+this.tablename+":"+this.indexnames[index]);
			}
		}
	}
		
	
	private String makeFieldType(int i)
	{
		String result = " TEXT";
		if (i < fieldtypes.length)
		{
			result =  " "+this.fieldtypes[i] ;
		}
		return result;
	}
	
	/**
	 * <pre>
	 * available basictypes
		TEXT
		NUMERIC
		INTEGER
		REAL
		NONE
	 * 
	 * set TEXT as default datatype
	 * 
	 * @return  fielddefinitions based on fieldname and fieldtype
	 * </pre>
	 */
	private String makeFieldDefs()
	{
//		TEXT
//		NUMERIC
//		INTEGER
//		REAL
//		NONE
		
		String result = fieldnames[0] + makeFieldType(0) ;
		for (int i=1; i<fieldnames.length; i++)
		{
			result = result + ", " + fieldnames[i] + makeFieldType(i);
		}
		
		return result ;
	}
	
	

	/**
	 * <pre>
	 * make a select on the first field of the table
	 * HINT: in a default table this is the ID
	 * 
	 * @param id
	 * @return
	 * </pre>
	 */
	public ResultSet readObject(String query)
	{
		try {
			return sql.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Select Error on "+tablename);
			return null;
		}
	}
	
	/**
	 * make a insert for the table.
	 * the String must be a well formated SQL
	 * hint: the PRIMARY key is the first field and must be a integer value
	 * 
	 * @param query
	 * @return
	 */
	public boolean insert(String query)
	{
		try {
			return sql.insert(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Insert Error on "+tablename);
		}
		
		return false;
	}

	/**
	 * make a update for the table.
	 * the String must be a well formated SQL
	 * hint: the PRIMARY key is the first field and must be a integer value
	 * 
	 * @param query
	 * @return
	 */
	public boolean update(String query)
	{
		try {
			return sql.update(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Update Error on "+tablename);
		}
		
		return false;
	}

	/**
	 * make a delete for the table.
	 * the String must be a well formatted SQL
	 * hint: only the PRIMARY key is necessary for delete
	 * 
	 * @param query
	 * @return
	 */
	public boolean delete(String query)
	{
		try {
			return sql.delete(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Delete Error on "+tablename);
		}
		
		return false;
	}
	
}
