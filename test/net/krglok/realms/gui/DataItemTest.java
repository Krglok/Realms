package net.krglok.realms.gui;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import net.krglok.realms.core.Owner;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.data.DataStorage;

import org.bukkit.block.Biome;
import org.junit.Test;

public class DataItemTest
{
	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
	DataStorage data = new DataStorage(dataFolder);
	
	
	private void printObjFieldList(Object object) 
	{
		System.out.println("FieldList :  "+object.getClass().getSimpleName());
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
					try
					{
						System.out.println(object.getClass().getSimpleName()+"."+obj.getName()
								+" ["+obj.getType().getSimpleName()+"] "
								+obj.getModifiers()
//								+"="+obj.get(obj)
								);
					} catch (Exception e)
					{
						System.out.println("Exception");
					}
				}
			}
		}
		if (object.getClass().getSuperclass() != null)
		{
			printObjClassList(object);
		}
	}
	
	private void printObjClassList(Object object)
	{
		System.out.println("Superclass: "+object.getClass().getSuperclass().getSimpleName());
		for (Field obj : object.getClass().getSuperclass().getDeclaredFields())
		{
			if ((obj.getName().startsWith("$")==false)
			    && (obj.getModifiers() < 5)
				)
			{
				try
				{
					System.out.println(object.getClass().getSimpleName()
						+"."+object.getClass().getSuperclass().getSimpleName()
						+"."+obj.getName()+" ["+obj.getType().getSimpleName()
						+"] "
						+obj.getModifiers()
//						+"="+obj.get(obj)
						);
				} catch (Exception e)
				{
					System.out.println("Exception");
				}
			}
		}
	}
	
	private void printFieldList(DataItem dataItem)
	{
		System.out.println("Class: "+dataItem.getItemObject().getClass().getSimpleName()+" / FieldList");
		for (DataItemField field : dataItem.fields.values())
		{
			System.out.println(field.id
					+":"+field.getFieldName()
					+":"+field.getFieldDef().fieldType
					+"="+field.asString()
					);
		}
	}
	
	private void printSettleItemList(DataItemList dataItemList)
	{
		if (dataItemList.getColumns() != null)
		{
			System.out.println(dataItemList.getColumns().getColumnByName("id").asString(4)
					+":"+dataItemList.getColumns().getColumnByName("name").asString(16)
					+":"+dataItemList.getColumns().getColumnByName("SettleType").asString(10)
					+":"+dataItemList.getColumns().getColumnByName("biome").asString(15)
					+":"+dataItemList.getColumns().getColumnByName("age").asString(7)
					);
		}
		
		
		for (DataItem dataItem : dataItemList.values())
		{
			System.out.println(dataItem.getFieldByName("id").asString(4)
					+":"+dataItem.getFieldByName("name").asString(16)
					+":"+dataItem.getFieldByName("SettleType").asString(10)
					+":"+dataItem.getFieldByName("biome").asString(15)
					+":"+dataItem.getFieldByName("age").asString(7)
					);
		}
	}

	private void printItemList(DataItemList dataItemList)
	{
		if (dataItemList.getColumns() != null)
		{
			System.out.println(dataItemList.getColumns().getColumnByName("itemRef").asString(12)
					+":"+dataItemList.getColumns().getColumnByName("value").asString(10)
					);
		}
		
		
		for (DataItem dataItem : dataItemList.values())
		{
			System.out.println(dataItem.getFieldByName("itemRef").asString(12)
					+":"+dataItem.getFieldByName("value").asString(10)
					);
		}
	}
	
	@Test
	public void test()
	{
		data.initData();
//		Settlement settle = data.getSettlements().getSettlement(4);
//		printObjFieldList(settle);

		Settle_DMM settle_DMM = new Settle_DMM();
		settle_DMM.initDataItemList(data.getSettlements());
		ItemList_DMM itemList = new ItemList_DMM();
		itemList.initDataItemList(data.getSettlements().getSettlement(1).getWarehouse().getItemList());
		
		System.out.println(settle_DMM.getName()+": "+settle_DMM.dataList().size());
		printSettleItemList(settle_DMM.dataList());

		System.out.println(itemList.getName()+": "+itemList.dataList().size());
		printItemList(itemList.dataList());
		fail("Not yet implemented");
	}

}
