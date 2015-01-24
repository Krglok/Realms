package net.krglok.realms.tool;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import net.krglok.realms.data.DataStoreNpc;
import net.krglok.realms.data.SQliteConnection;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NpcList;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfiguration;
import org.junit.Test;

public class TableDataTest
{
	NpcList npcList = new NpcList();;
	DataStoreNpc npcDataStore;
	
	private void getnpcList()
	{
		String pathName = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		npcDataStore = new DataStoreNpc(pathName, null); // no sql access , normal yml file
		NpcData npc;
		ArrayList<String> refList = npcDataStore.readDataList();
		for (String ref : refList)
		{
			npc = npcDataStore.readData(ref);
			npcList.putNpc(npc);
		}

	}
	
	private void writeTest(TableNpc testTable)
	{
		int count = 0;
		for ( NpcData npc : npcList.values())
		{
			if (count < 100)
			{
				long time1 = System.nanoTime();
				if (testTable.contain(npc) == true)
				{
					if (npc.getId() < 21)
					{
						testTable.writeNpc(npc);
					    long time2 = System.nanoTime();
				    	System.out.println(npc.getId()+" Update Time [ms]: "+(time2 - time1)/1000000);
					}
	//				System.out.println("FOUND "+npc.getId());
				} else
				{
					testTable.addNpc(npc);
				    long time2 = System.nanoTime();
			    	System.out.println(npc.getId()+" Insert Time [ms]: "+(time2 - time1)/1000000);
	//				System.out.println("MISSED "+npc.getId());
				}
			}
			count++;
		}

	}
	
	
	@Test
	public void test() throws SQLException
	{
		getnpcList();
		String pathName = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; //\\Realms";
		SQliteConnection sql = new SQliteConnection(pathName);
		TableNpc testTable = new TableNpc(sql, npcDataStore);
		testTable.makeTableDefinitions("npc");
		if (sql.open() == false)
		{
			System.out.println("SQL not open");
			return;
		}

		ResultSet result;
		System.out.println("DB   "+sql.getDbName());
		System.out.println("File "+sql.getFileName());
		System.out.println("npc  "+sql.isTable("npc"));
//		
//		String query = "SELECT * FROM npc WHERE ID = "+TableData.makeSqlString("1");
//		ResultSet result = sql.query(query );
//		System.out.println(result.getString(1));
//		System.out.println(result.getString(2));
		
		int id = 0;
		result = testTable.readObject(id);
		System.out.println(result.getString(1));
		System.out.println(result.getString(2));
	
		ConfigurationSection section = new MemoryConfiguration();
		NpcData dataObject = npcList.get(1);

		if (testTable.contain(dataObject) == true)
		{
			System.out.println("FOUND "+dataObject.getId());
			id = dataObject.getId();

			long time1 = System.nanoTime();
			
			for (int i = 0; i < 10; i++)
			{
				
				result = testTable.readObject(id+i);
				if (result.next() == true)
				{
					try
					{
						npcDataStore.config.loadFromString(result.getString(2));
						section = npcDataStore.config.getRoot();
						NpcData npcData = npcDataStore.initDataObject(section);
					    long time2 = System.nanoTime();
				    	System.out.println(npcData.getId()+" Read Time [ms]: "+(time2 - time1)/1000000);
						System.out.println("Name : "+npcData.getName());
					} catch (InvalidConfigurationException e)
					{
						e.printStackTrace();
					}
				}
			}
			
		} else
		{
			System.out.println("MISSED "+dataObject.getId());
		}

//		writeTest(testTable);
		
		fail("Not yet implemented");
	}

}
