package net.krglok.realms.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.krglok.realms.npc.NpcData;
import net.krglok.realms.tool.TableData;

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
				Integer.class.getName(),
				String.class.getName()
				
		};
		this.indexnames = new String[]
		{
			tablename+"_idx1"
		};
		this.indexnames = new String[]
			{  
				"ID"
			};
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
			return sql.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Select Error on "+tablename);
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
			insert(insertQuery);
		} else
		{
			String updateQuery = "UPDATE "+tablename
					+" SET "+fieldnames[1]+"="+makeSqlString(value)
					+" WHERE "+fieldnames[0]+"="+makeSqlString(refId)
					;		
			update(updateQuery);
			
		}

	}
	
}
