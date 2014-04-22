package net.krglok.realms.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.Database;

/**
 * Abstract class for storage of YML data in SQLite Database
 * use the SQLibrary for database handling
 *
 * create tables and indices  on given definition
 * use automatic rowid of SQlite, so no PRIMARY KEY must be defined
 * 
 * make basic datahandling
 * - insert
 * - update
 * - delete
 * - select
 * 
 * @author Windu
 *
 */
public abstract class TableData 
{
	protected Database sql;
	public String tablename ;
	public String[] fieldnames; 
	public String[] fieldtypes; 
	public String[] indexnames; 
	public String[] indexfields;
	public ResultSet resultSet;
	
	/**
	 * the Database must be given to make automatic access
	 * @param sql
	 */
	public TableData(Database sql, String tableName)
	{
		super();
		this.sql = sql;
		tablename = tableName;
		fieldnames = null;
		fieldtypes = null;
		indexnames = null;
		indexfields = null;
		resultSet = null;
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
			return sql.checkTable(tablename);
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
			String query = "CREATE INDEX IF NOT EXISTS "+this.indexnames[index] +" ("+this.indexfields[index]+") ";
			try {
				sql.query(query);
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
	 * fields: 
		"objectname",
		"sectionname",
		"valuename",
		"value"
	 * indices:
		tablename+"_idx1",
		tablename+"_idx2",
		tablename+"_idx3"
	 * indexfields:
		"objectname",
		"objectname,sectionname",
		"objectname,sectionname,valuename"
	 * 
	 * @param tablename
	 * </pre>
	 */
	public void makeDefaultDefinitions(String tablename)
	{
		this.tablename = tablename;
		this.fieldnames = new String[] 
		{  
			"objectname",
			"sectionname",
			"valuename",
			"value"
		};
		this.fieldtypes = new String[]
		{
				String.class.getName(),
				String.class.getName(),
				String.class.getName(),
				String.class.getName()
				
		};
		this.indexnames = new String[]
		{
			tablename+"_idx1",
			tablename+"_idx2",
			tablename+"_idx3"
		};
		this.indexnames = new String[]
			{  
				"objectname",
				"objectname,sectionname",
				"objectname,sectionname,valuename"
			};
	}
	
	/**
	 * <pre>
	 * make a select on the first field of the table
	 * HINT: in a default table this is the objectname
	 * @param objectName
	 * @return
	 * </pre>
	 */
	public ResultSet readObject(String objectName)
	{
//		this.resultSet = null;
		String query = "SELECT rowid, * FROM "+tablename+ "WHERE "+fieldnames[0]+"="+makeSqlString(objectName);
		try {
			this.resultSet = sql.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Select Error on "+tablename);
		}
		return this.resultSet;
	}

	public ResultSet readObjectSection(String objectName, String sectionName)
	{
//		this.resultSet = null;
		String query = "SELECT rowid, * FROM "+tablename
				+" WHERE "+fieldnames[0]+"="+makeSqlString(objectName)
				+" AND "+fieldnames[1]+"="+makeSqlString(sectionName);
		try {
			this.resultSet = sql.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Select Error on "+tablename);
		}
		return this.resultSet;
	}
	
	
	/**
	 * make a fullinsert for the table.
	 * baesd on the field list
	 * hint: the PRIMARY key is the first field and will be set to NULL
	 * @param values
	 * @return  true if insert succesful
	 */
	public boolean insert(String[] values)
	{
		String query = "";
		try {
			resultSet = sql.query(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[REALMS] Insert Error on "+tablename);
		}
		
		return false;
	}

	
}
