package net.krglok.realms.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;

public class PriceTable extends TableData 
{
	/**
	 * check the table and make a create if false;
	 * @param sql
	 */
	public PriceTable(SQliteConnection sql)
	{
		super(sql,"baseprice");
		makeTableDefinitions(tablename);
		if (checkTable() == false)
		{
			createTable();
		}
	}
	
	public void makeTableDefinitions(String tablename)
	{
		this.tablename = tablename;
		this.fieldnames = new String[] { "objectname","valuename", "value" };
		this.fieldtypes = new String[] { "String",  "String", "Double" };
		this.indexnames = new String[] {  tablename+"_idx2"  };
		this.indexfields = new String[] { "objectname,valuename" };
	}

	/**
	 * make a single SQL Replace to a pricelist item 
	 * HINT: Replace make an update or insert a new row
	 * @param basekey
	 * @param itemRef
	 * @return
	 */
	public void replaceItemValue(String basekey, String itemRef, double price)
	{
		
		String query = "SELECT rowid, * FROM "+tablename
				+" WHERE "+fieldnames[0]+"="+makeSqlString(basekey)
				+" AND "+fieldnames[1]+"="+makeSqlString(itemRef)
				;
		
		String insert = "INSERT INTO "+tablename
				+" ("+ getFieldNames() +") "
				+" VALUES ("+makeSqlString(basekey)+", "+makeSqlString(itemRef)+", "+String.valueOf(price)+" ) "
				;
		
		String update = "UPDATE "+tablename
				+" SET "+fieldnames[2]+"="+String.valueOf(price)
				+" WHERE "+fieldnames[0]+"="+makeSqlString(basekey)
				+" AND "+fieldnames[1]+"="+makeSqlString(itemRef)
				;
		try 
		{
//			ResultSet result  = this.sql.query(query);
//			if ((result.next() == false))
//			{
//				this.sql.insert(insert);
//			} else
//			{
//				this.sql.update(update);
//			}
			if (this.sql.update(update) == false)
			{
				this.sql.insert(insert);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("[REALMS] Replace Error on "+tablename+":"+itemRef);
		}
		
//		return this.readObjectSection(basekey, itemRef);
	}
	
	public void insertItem(String basekey, String itemRef, double price) 
	{
		String insert = "INSERT INTO "+tablename
				+" ("+ getFieldNames() +") "
				+" VALUES ("+makeSqlString(basekey)+", "+makeSqlString(itemRef)+", "+String.valueOf(price)+" ) "
				;
		try
		{
			this.sql.insert(insert);
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("[REALMS] Insert Error on "+tablename+":"+itemRef);
		}
		
	}
	
	public void updateItem(String basekey, String itemRef, double price)
	{
		String update = "UPDATE "+tablename
				+" SET "+fieldnames[2]+"="+String.valueOf(price)
				+" WHERE "+fieldnames[0]+"="+makeSqlString(basekey)
				+" AND "+fieldnames[1]+"="+makeSqlString(itemRef)
				;
		try
		{
			this.sql.update(update);
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("[REALMS] Insert Error on "+tablename+":"+itemRef);
		}
		
	}
	
	/**
	 * make a direct replace for every item in the priclist
	 * @param items
	 * @param basekey
	 */
	public void writePriceData(ItemPriceList items, String basekey, boolean isNew)
	{
		if (isNew == true)
		{
			String query = "DELETE FROM "+tablename+" WHERE objectname="+makeSqlString(basekey);
			delete(query);
			for (ItemPrice item : items.values())
			{
				insertItem(basekey, item.ItemRef(), item.getFormatedBasePrice());
			}
		} else
		{
			for (ItemPrice item : items.values())
			{
				updateItem(basekey, item.ItemRef(), item.getFormatedBasePrice());
			}
			
		}
	}
	
	public ResultSet selectPricedata()
	{
		String query = "SELECT rowid,* FROM "+tablename + " ORDER BY "+fieldnames[1];
//				+" WHERE "+fieldnames[0]+"="+makeSqlString(basekey)
//				+" AND "+fieldnames[1]+"="+makeSqlString(itemRef)
				;
		try 
		{
			ResultSet resultSet = this.sql.query(query);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Select Error on "+tablename);
			return null;
		}
		
	}
}
