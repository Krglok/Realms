package net.krglok.realms.Common;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.krglok.realms.npc.NpcData;

/**
 * <pre>
 * This is a SQLite version for store large YML files with section of a list of objects
 *  * create tables and indices  on given definition
 * use PRIMARY KEY as Integer
 * the table structure is predefined
 * - column 0 = Integer ID
 * - column 1 = Text Section
 * 
 * the column section must be a YML formated text section
 * the YML data stored in YML-format in the text-field Section
 * the YML formatted and extracted with the configfile class from Bukkit

 * @author Windu
 * </pre>
 */

public class TableYml extends TableData
{

	public TableYml(SQliteConnection sql, String tableName)
	{
		super(sql, tableName);
		makeDefaultDefinitions(tablename);
		try
		{
			if (sql.isTable(tableName)==false)
			{
				if (this.createTable() == false)
				{
					System.out.println("[REALMS] SQL TABLE NOT created "+this.tablename);
				}
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * fields: 
		"ID",
		"Section",
	 * indices:
		tablename+"_idx1",
	 * indexfields:
		"ID",
	 * 
	 * @param tablename
	 * </pre>
	 */
	public void makeDefaultDefinitions(String tablename)
	{
		this.tablename = tablename;
		this.fieldnames = new String[] 
		{  
			"ID",
			"Section"
		};
		this.fieldtypes = new String[]
		{
				"INTEGER",
				"TEXT"
				
		};
		this.indexnames = new String[]
		{
			tablename+"_idx1"
		};
		this.indexfields = new String[]
			{  
				"ID"
			};
	}

	public void delete(int Id)
	{
		String objectName = String.valueOf(Id);
		String query = "DELETE FROM "+tablename+ " WHERE "+fieldnames[0]+"="+makeSqlString(objectName);
		try
		{
			sql.execute(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("[REALMS] SQL Delete Error on "+tablename);

		}
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
	public ResultSet readObject(int id)
	{
		String objectName = String.valueOf(id);
		//		this.resultSet = null;
		String query = "SELECT * FROM "+tablename+ " WHERE "+fieldnames[0]+"="+makeSqlString(objectName );
		try {
			if (sql.isOpen())
			{
				return sql.query(query);
			} else
			{
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] SQL Select Error on "+tablename);
			return null;
		}
	}

	/**
	 * search for PRIMARY key in table
	 * 
	 * @param id
	 * @return
	 */
	public boolean contain(int id)
	{
		String refId = String.valueOf(id);
		
		String selectQuery = "SELECT "+fieldnames[0]+" FROM "+tablename
				+" WHERE "+fieldnames[0]+"="+makeSqlString(refId)
				;		
		try
		{
			ResultSet result = this.sql.query(selectQuery);
			if (result.next())
			{
				return true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("[REALMS] SQL Contain Error on "+tablename);
		}
		return false;
	}

	public void writeObject(int id, String value)
	{
		String refId = String.valueOf(id);

		if (contain(id) == false)
		{
			String insertQuery = "INSERT INTO "+this.tablename
					+" ("+ this.getFieldNames() +") "
					+" VALUES ("+TableData.makeSqlString(refId)+", "+TableData.makeSqlString(value)+")" 
					;
			if (insert(insertQuery) == false)
			{
				System.out.println("[REALMS] SQL Insert Error on "+tablename);
			}
		} else
		{
			String updateQuery = "UPDATE "+tablename
					+" SET "+fieldnames[1]+"="+makeSqlString(value)
					+" WHERE "+fieldnames[0]+"="+makeSqlString(refId)
					;		
			if (update(updateQuery) == false)
			{
				System.out.println("[REALMS] SQL Update Error on "+tablename);
			}
			
		}

	}
	
}
