package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Warehouse;
import org.junit.Test;

public class WarehouseTest
{

	@Test
	public void testWarehouse()
	{
		Warehouse warehouse = new Warehouse(1000);
		int expected = 1000;
		int actual = warehouse.getItemMax();
		assertEquals(expected, actual);
		
	}

	@Test
	public void testCheckItemMax()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getToolItems();
		Warehouse warehouse = new Warehouse(10000);
		warehouse.setItemList(iList);
		warehouse.setStoreCapacity();

		String lastRef = "";
		int i = 0;
		int value = 0;
		for (String itemRef : iList.keySet())
		{
			i++;
			value = value + i;
			warehouse.depositItemValue(itemRef, i);
			lastRef = itemRef;
		}
		Boolean expected = true;
		Boolean actual = warehouse.depositItemValue(lastRef, i);
		if (expected != actual)
		{
			System.out.println(" ");
			System.out.println("testCheckItemMax "+warehouse.getFreeCapacity()+"/"+warehouse.getItemMax()/64);
			for (Item item : iList.values())
			{
				System.out.println(ConfigBasis.setStrleft(item.ItemRef(),16)
						+":"+warehouse.getItemList().getValue(item.ItemRef())
						+":"+warehouse.getTypeCapacityList().get(item.ItemRef()).value()*64);
			}
			
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testCheckItemMaxNot()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getToolItems();
		Warehouse warehouse = new Warehouse(200);
		warehouse.setItemList(iList);
		warehouse.setStoreCapacity();

		String lastRef = "";
		int i = 0;
		int value = 0;
		for (String itemRef : iList.keySet())
		{
			i++;
			value = value + i;
			warehouse.depositItemValue(itemRef, i);
			lastRef = itemRef;
		}
		Boolean expected = false;
		Boolean actual = warehouse.depositItemValue(lastRef, i);
		if (expected != actual)
		{
			System.out.println(" ");
			System.out.println("testCheckItemMaxNot "+warehouse.getFreeCapacity()+"/"+warehouse.getItemMax()/64);
			for (Item item : iList.values())
			{
				System.out.println(ConfigBasis.setStrleft(item.ItemRef(),16)
						+":"+warehouse.getItemList().getValue(item.ItemRef())
						+":"+warehouse.getTypeCapacityList().get(item.ItemRef()).value()*64);
			}
			
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testCheckCapacity()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getToolItems();
		Warehouse warehouse = new Warehouse(17260);
		warehouse.setItemList(iList);
		warehouse.setStoreCapacity();

		String lastRef = "";
		int i = 160;
		int value = 0;
		for (String itemRef : iList.keySet())
		{
			i++;
			value = value + (int)(Math.random()*i);
			warehouse.depositItemValue(itemRef, value);
			lastRef = itemRef;
		}
		Boolean expected = false;
		Boolean actual = warehouse.depositItemValue(lastRef, i);
		if (expected != actual)
		{
			System.out.println(" ");
			System.out.println("testCheckCapacity "+warehouse.getFreeCapacity()+"/"+warehouse.getItemMax()/64);
			for (Item item : iList.values())
			{
				System.out.println(ConfigBasis.setStrleft(item.ItemRef(),16)
						+":"+warehouse.getItemList().getValue(item.ItemRef())
						+":"+warehouse.getTypeCapacityList().get(item.ItemRef()).value()*64);
			}
			
		}
		String[][] dataRows = new String[warehouse.getItemList().size()][3];
		int index = 0;
		for (String Ref : warehouse.getItemList().sortItems())
		{
			Item item = warehouse.getItemList().get(Ref);
			if (index <100)
			{
				dataRows[index][0] = ConfigBasis.setStrleft(item.ItemRef(),15);
				dataRows[index][1] = ConfigBasis.setStrright(item.value(),7);
				dataRows[index][2] = ConfigBasis.setStrright(0.0,5);
			}
			index ++;
		}
		for (int j = 0; j < dataRows.length; j++)
		{
			System.out.print(dataRows[j][0]+"|");
			System.out.print(dataRows[j][1]+"|");
			System.out.print(dataRows[j][2]+"|");
			System.out.println("");
		}

		assertEquals(expected, actual);
	}
	

}
