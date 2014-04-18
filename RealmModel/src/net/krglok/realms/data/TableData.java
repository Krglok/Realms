package net.krglok.realms.data;

import java.sql.SQLException;

import lib.PatPeter.SQLibrary.Database;

public abstract class TableData 
{
	private Database sql;
	protected String tablename ;
	protected String[] fieldnames; 
	protected String[] fieldtypes; 
	protected String[] indexnames; 
	protected String[] indexfields;
	
	/**
	 * the Database must be given to make automatic access
	 * @param sql
	 */
	public TableData(Database sql)
	{
		this.sql = sql;
		tablename = "";
		fieldnames = null;
		fieldtypes = null;
		indexnames = null;
		indexfields = null;
	}
	
	
	
	/**
	 * Check the table if exist in Database
	 * should be used in derived class on instances 
	 * @return
	 */
	public boolean checkTable()
	{
		if (tablename != "")
		{
			return sql.isTable(tablename);
		}
		System.out.println("[REALMS] SQL table not found "+tablename);
		return false;
	}
	
	/**
	 * ctreate a table based on the definitions.
	 * automatic rowid used as PRIMARY KEY
	 * @return
	 */
	protected boolean createTable()
	{
		if (tablename == "")
		{
			System.out.println("[REALMS] SQL tablename is empty ! ");
			return false;
		}
		if (fieldnames == null)
		{
			System.out.println("[REALMS] SQL NO fieldnames defined "+tablename);
			return false;
		}
		for (String fieldname : fieldnames)
		{
			if (fieldname == "")
			{
				System.out.println("[REALMS] SQL empty fieldname "+tablename);
				return false;
			}
		}
		if (indexnames == null)
		{
			System.out.println("[REALMS] Warning, SQL NO index defined "+tablename);
		}
		String fielddefs = makeFieldDefs();
		String query = "CREATE TABLE IF NOT EXIST "+tablename+ " ("+fielddefs+") ";
		try {
			sql.query(query);
			if (sql.isTable(tablename) == true)
			{
				// make the indexes
				if (indexnames != null)
				{
					for (int i=0; i < indexnames.length; i++)
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
		if (index < indexfields.length)
		{
			String query = "CREATE INDEX IF NOT EXISTS "+indexnames[index] +" ("+indexfields[index]+") ";
			try {
				sql.query(query);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("[REALMS] SQL INDEX not created "+tablename+":"+indexnames[index]);
			}
		}
	}
		
	
	private String makeFieldType(int i)
	{
		String result = " TEXT";
		if (i < fieldtypes.length)
		{
			result = result + " "+fieldtypes[i] + ", ";
		}
		return result;
	}
	
	private String makeFieldDefs()
	{
//		TEXT
//		NUMERIC
//		INTEGER
//		REAL
//		NONE
		
		String result = fieldnames[0] + makeFieldType(0) ;
		for (int i=0; i<fieldnames.length; i++)
		{
			result = result + ", " + fieldnames[i] + makeFieldType(i);
		}
		
		return result ;
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
		
		return false;
	}

	
}
