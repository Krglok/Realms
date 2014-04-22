package net.krglok.realms.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import lib.PatPeter.SQLibrary.Database;

public class PriceTable extends TableData 
{
	/**
	 * check the table and make a create if false;
	 * @param sql
	 */
	public PriceTable(Database sql)
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
		this.indexnames = new String[] { tablename+"_idx1", tablename+"_idx2"  };
		this.indexnames = new String[] { "objectname", "objectname,valuename" };
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
		
		String query = "SELECT rowid FROM "+tablename
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
			ResultSet result = this.sql.query(query);
			if (result.getRow() == 0)
			{
				this.sql.query(insert);
			} else
			{
				this.sql.query(update);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("[REALMS] Replace Error on "+tablename+":"+itemRef);
		}
		
//		return this.readObjectSection(basekey, itemRef);
	}
	
	/**
	 * make a direct replace for every item in the priclist
	 * @param items
	 * @param basekey
	 */
	public void writePriceData(ItemPriceList items, String basekey)
	{
		resultSet = this.readObject(basekey);
		for (ItemPrice item : items.values())
		{
			replaceItemValue( basekey, item.ItemRef(), item.getFormatedBasePrice());
		}
	}
	
	public ResultSet selectPricedata()
	{
		String query = "SELECT rowid,* FROM "+tablename
//				+" WHERE "+fieldnames[0]+"="+makeSqlString(basekey)
//				+" AND "+fieldnames[1]+"="+makeSqlString(itemRef)
				;
		try 
		{
			java.sql.PreparedStatement pQuery = sql.prepare(query);
			return this.sql.query(pQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[REALMS] Select Error on "+tablename);
			return null;
		}
		
	}
}
