package net.krglok.realms.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.kingdom.Lehen;




public class DataStoreLehen extends AbstractDataStore<Lehen>
{

	public DataStoreLehen(String dataFolder)
	{
		super(dataFolder, "lehen", "LEHEN", false);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param Lehen dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, Lehen dataObject)
	{
//		private int id;
//		private String name;
//		private NobleLevel nobleLevel;
//		private SettleType settleType;
//		private Owner owner;
//		private int KingdomId;
//		private int parentId;
		section.set("id", dataObject.getId());
		section.set("name", dataObject.getName());
		section.set("nobleLevel", dataObject.getNobleLevel().name());
		section.set("settleType", dataObject.getSettleType().name());
		section.set("owner", dataObject.getOwner().getPlayerName());
		section.set("kingdomId", dataObject.getKingdomId());
		section.set("parentId", dataObject.getParentId());
        section.set( "bank", dataObject.getBank().getKonto());
    	section.set("position", LocationData.toString(dataObject.getPosition()));
		
	}


	/**
	 * Override this for the concrete class

	 * @return Lehen , real data Class
	 */
	@Override
	public Lehen initDataObject(ConfigurationSection data)
	{
//		// 
		Lehen lehen = new Lehen();
		
		lehen.setId(data.getInt("id"));
		lehen.setName(data.getString("name"));
		String ref = data.getString("nobleLevel");
		lehen.setNobleLevel(NobleLevel.valueOf(ref));
		ref = data.getString("settleType");
		lehen.setSettleType(SettleType.valueOf(ref));
		lehen.setOwnerId(data.getString("owner"));
		lehen.setKingdomId(data.getInt("kingdomId", 0));
		lehen.setParentId(data.getInt("parentId",0));
		lehen.getBank().addKonto(data.getDouble( "bank",0.0),"SettleRead",lehen.getId());
		LocationData position = LocationData.toLocation(data.getString("position"));
		lehen.setPosition(position);
		return lehen;
	}
	
}
