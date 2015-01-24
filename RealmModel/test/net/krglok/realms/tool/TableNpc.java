package net.krglok.realms.tool;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.data.DataStoreNpc;
import net.krglok.realms.data.SQliteConnection;
import net.krglok.realms.data.TableYml;
import net.krglok.realms.npc.NpcData;

public class TableNpc extends TableYml
{
	
	private DataStoreNpc npcDataStore;
	
	
	public TableNpc(SQliteConnection sql, DataStoreNpc npcDataStore)
	{
		super(sql, "npc");
		this.npcDataStore = npcDataStore;
	}

	public void makeTableDefinitions(String tablename)
	{
		this.tablename = tablename;
		this.fieldnames = new String[] { "ID","Section" };
		this.fieldtypes = new String[] { "Integer",  "String" };
		this.indexnames = new String[] {  tablename+"_idx2"  };
		this.indexfields = new String[] { "ID" };
	}

	public String  makeValue(ConfigurationSection section)
	{
		String value = "";
		for (String key :section.getKeys(false))
		{
			value = value + key+": "+section.getString(key)+"\n";
		}
		return value;
	}
	
	
	public void addNpc(NpcData npc)
	{
		ConfigurationSection section = new MemoryConfiguration();
		npcDataStore.initDataSection(section, npc);
		String value = makeValue(section);

//		String value = "name"+": "+npc.getName();
		String id = String.valueOf(npc.getId());
		String insertQuery = "INSERT INTO "+tablename
				+" ("+ getFieldNames() +") "
				+" VALUES ("+makeSqlString(id)+", "+makeSqlString(value)+")" 
				;

		insert(insertQuery);
	}
	
	
	public boolean contain(NpcData npc)
	{
		String id = String.valueOf(npc.getId());
		
		String selectQuery = "SELECT "+fieldnames[0]+" FROM "+tablename
				+" WHERE "+fieldnames[0]+"="+makeSqlString(id)
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
	
	public void writeNpc(NpcData npc)
	{
		ConfigurationSection section = new MemoryConfiguration();
		npcDataStore.initDataSection(section, npc);
		String value = makeValue(section);
//		String value = "name"+": "+npc.getName();
		
		String id = String.valueOf(npc.getId());
		
		String updateQuery = "UPDATE "+tablename
				+" SET "+fieldnames[1]+"="+makeSqlString(value)
				+" WHERE "+fieldnames[0]+"="+makeSqlString(id)
				;		

		if (contain(npc) == false)
		{
			addNpc(npc);
		} else
		{
			update(updateQuery);
			
		}
	}
	
	
	public NpcData readNpc(int id)
	{
		NpcData npc = new NpcData();
		ConfigurationSection section = new MemoryConfiguration();
		
		
		return npc;
	}
	
}
