package net.krglok.realms.data;

import org.bukkit.configuration.ConfigurationSection;

import net.krglok.realms.Common.AbstractDataStore;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.manager.CampPosition;
import net.krglok.realms.manager.PositionFace;

public class DataStoreCampPos  extends AbstractDataStore<CampPosition>
{

	public DataStoreCampPos(String dataFolder)
	{
		super(dataFolder, "camppos", "CAMPPOS", false, null);
	}

	/**
	 * Override this for the concrete class
	 * 
	 * @param T dataObject, instance of real data Class
	 */
	@Override
	public void initDataSection(ConfigurationSection section, CampPosition dataObject)
	{
//		// 
		section.set("id", dataObject.getId());
		section.set("settleId", dataObject.getSettleId());
		section.set("position", dataObject.getPosition().toString());
		section.set("face", dataObject.getFace().name());
		section.set("redo", dataObject.getRedo());
		section.set("activ", dataObject.isActiv());
		section.set("camp", dataObject.isCamp());
		section.set("valid", dataObject.isValid());
		section.set("radius", dataObject.getAnalysis().getRadius());
		section.set("start", dataObject.getAnalysis().getStart());
		section.set("average", dataObject.getAnalysis().getAverage());
		section.set("min", dataObject.getAnalysis().getMin());
		section.set("max", dataObject.getAnalysis().getMax());
		section.set("scale", dataObject.getAnalysis().scale());
		section.set("isGround", dataObject.getAnalysis().isGround());
		section.set("isLava", dataObject.getAnalysis().isLava());
		section.set("isValid", dataObject.getAnalysis().isValid());
		
	}


	/**
	 * Override this for the concrete class

	 * @return T , real data Class
	 */
	@Override
	public CampPosition initDataObject(ConfigurationSection data)
	{
//		// 
		CampPosition campPos = new CampPosition();
		campPos.setId(data.getInt("id",0));
		campPos.setSettleId(data.getInt("settleId",0));
		campPos.setPosition(LocationData.toLocation(data.getString("position","")));
		campPos.setFace(PositionFace.valueOf((data.getString("face","NORTH"))));
		campPos.setRedo(data.getInt("redo",0));
		campPos.setActiv(data.getBoolean("activ",false));
		campPos.setCamp(data.getBoolean("camp",false));
		campPos.setValid(data.getBoolean("valid",false));
		return campPos;
	}


}
