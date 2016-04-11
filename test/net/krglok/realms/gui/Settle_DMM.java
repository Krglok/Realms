package net.krglok.realms.gui;

import java.lang.reflect.Field;

import org.bukkit.block.Biome;

import net.krglok.realms.Common.LocationData;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.core.Townhall;

public class Settle_DMM
{
	private DataItemList dataList;
	private String name;
//	SettlementList settleList;
	
	public Settle_DMM()
	{
		super();
		this.dataList = new DataItemList();
	}
	
	public DataItemList dataList()
	{
		return dataList;
	}

	/*
	 * Holt ein einzelnes DatenItem anhand der IndexNr
	 */
	public DataItem dataItem(int index)
	{
		return dataList.getItem(index);
	}
	
	public DataItem dataItem(String name)
	{
		for (DataItem dataItem : dataList.values())
		{
			if (dataItem.fields.getFieldByName("Name").asString().equalsIgnoreCase(name))
			{
				return dataItem;
			}
		}
		
		return null;
	}


	/**
	 * Initialisiert an Dataitem aus einem Settlement objekt
	 * 
	 * @param settle
	 * @return
	 */
	public DataItem initDataItem(Settlement settle)
	{

		DataItem dataItem = new DataItem();
		if (settle != null)
		{
			dataItem.addField(DataItemField.initField("id", "Id",10,settle.getId()));
			dataItem.addField(DataItemField.initField("name", "Name", 30, settle.getName()));
			dataItem.addField(DataItemField.initField("buildingTax", "BuildingTax", 30, settle.getBuildingTax()));
			dataItem.addField(DataItemField.initField("biome", "Biome", 30, settle.getBiome().name())); //,"String"
			dataItem.addField(DataItemField.initField("owner", "Owner", 30, settle.getOwner().getPlayerName()));
			dataItem.addField(DataItemField.initField("isNpc", "IsNpc", 5, settle.getOwner().getIsNPC()));
			dataItem.addField(DataItemField.initField("settleType"  , "SettleType", 5, settle.getSettleType().name())); //String.class.getSimpleName(),
			dataItem.addField(DataItemField.initField("tributId", "TributId", 5, settle.getTributId()));
			dataItem.addField(DataItemField.initField("age", "Age","Long", 5, settle.getAge()));
			dataItem.addField(DataItemField.initField("world", "World", 20, settle.getPosition().getWorld()));
			dataItem.setItemObject(settle);
		}
		return dataItem;
	}
	
	
	private void makObjFieldList(Object object, DataItem dataItem)
	{
		for (Field obj : object.getClass().getDeclaredFields())
		{
			if ((obj.getType().getName().equalsIgnoreCase(String.class.getName()))
				|| (obj.getType().getName().equalsIgnoreCase(Integer.class.getName()))	
				|| (obj.getType().getName().equalsIgnoreCase(int.class.getName()))	
				|| (obj.getType().getName().equalsIgnoreCase(double.class.getName()))	
				|| (obj.getType().getName().equalsIgnoreCase(Double.class.getName()))	
				|| (obj.getType().getName().equalsIgnoreCase(boolean.class.getName()))	
				|| (obj.getType().getName().equalsIgnoreCase(Boolean.class.getName()))	
				|| (obj.getType().getName().equalsIgnoreCase(Biome.class.getName()))	
				)
			{
				if ((obj.getName().startsWith("$")==false)
					    && (obj.getModifiers() < 5)
						)
				{
					dataItem.fields.addField(DataItemField.initField(obj.getName(), obj.getName(), obj.getType().getSimpleName(),10));
					
				}
			}
		}
		if (object.getClass().getSuperclass() != null)
		{
			makeObjClassList(object, dataItem);
		}
	}
	
	private void makeObjClassList(Object object, DataItem dataItem)
	{
		for (Field obj : object.getClass().getSuperclass().getDeclaredFields())
		{
			if ((obj.getName().startsWith("$")==false)
			    && (obj.getModifiers() < 5)
				)
			{
				dataItem.fields.addField(DataItemField.initField(obj.getName(), obj.getName(), obj.getType().getSimpleName(),10));
			}
		}
	}
	
	public DataItem makeDataItem(Settlement settle)
	{
		DataItem dataItem = new DataItem();
		dataItem.setItemObject(settle);
		makObjFieldList(settle, dataItem);
		return dataItem;
	}
	
	/**
	 * erzeugt aus einer SettlementList die DataItemList
	 * 
	 * @param settleList
	 */
	public void initDataItemList(SettlementList settleList)
	{
		this.name = "SettlementList";
		for (Settlement settle : settleList.values())
		{
			DataItem dataItem = initDataItem(settle);
			dataList.addItem(dataItem);
		}
	}

	public String getName()
	{
		return name;
	}
	
}
