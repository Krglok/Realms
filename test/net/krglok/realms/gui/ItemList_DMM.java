package net.krglok.realms.gui;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;

public class ItemList_DMM
{

	private DataItemList dataList;
	private String name;

	public ItemList_DMM()
	{
		dataList = new DataItemList();
	}
	
	public String getName()
	{
		return this.name;
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
			if (dataItem.fields.getFieldByName("itemRef").asString().equalsIgnoreCase(name))
			{
				return dataItem;
			}
		}
		
		return null;
	}
	
	public DataItem initDataItem(Item item)
	{

		DataItem dataItem = new DataItem();
		if (item != null)
		{
			dataItem.addField(DataItemField.initField("itemRef", "ItemRef",10,item.ItemRef()));
			dataItem.addField(DataItemField.initField("value", "Value", 15, item.value()));
			dataItem.setItemObject(item);
		}
		return dataItem;
	}
	
	public void initDataItemList(ItemList itemList)
	{
		this.name = "ItemList";
		for (Item item : itemList.values())
		{
			DataItem dataItem = initDataItem(item);
			dataList.addItem(dataItem);
		}
	}
	
}
