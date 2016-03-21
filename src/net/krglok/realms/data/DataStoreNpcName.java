package net.krglok.realms.data;

import java.util.List;

import net.krglok.realms.Common.AbstractDataStore;
import net.krglok.realms.npc.NpcNamen;

import org.bukkit.configuration.ConfigurationSection;

public class DataStoreNpcName  extends AbstractDataStore<NpcNamen>
{
	public DataStoreNpcName(String dataFolder)
	{
		super(dataFolder, "npcName", "NPCNAME", false, null);
	}

	public DataStoreNpcName(String dataFolder, String fileName)
	{
		super(dataFolder, fileName, "NPCNAME", true, null);
	}
	
	/**
	 * 
	 * @param NpcNamen dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, NpcNamen dataObject)
	{
//		// 
		section.set("WOMAN", dataObject.getWomanNames());
		section.set("MAN", dataObject.getManNames());
	}


	/**

	 * @return NpcNamen , real data Class
	 */
	@Override
	public NpcNamen initDataObject(ConfigurationSection data)
	{
//		// 
		NpcNamen npcNamen = new NpcNamen();
		List<?> wList =  data.getList("WOMAN");
		
		if (wList != null)
		{
			if (wList.size() > 0)
			{
				for (Object ref : wList)
				{
//					System.out.println(ref.toString());
					npcNamen.getWomanNames().add(ref.toString());
				}
			}
		}
		
		List<?> mList =  data.getList("MAN");
		if (mList != null)
		{
			if (mList.size() > 0)
			{
				for (Object ref : mList)
				{
//					System.out.println(ref.toString());
					npcNamen.getManNames().add(ref.toString());
				}
			}
		}
		return npcNamen;
	}
	
	
}
