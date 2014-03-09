package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.ItemPriceList;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.core.KingdomList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.ConfigTest;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

/**
 * 
 * @author Windu
 *
 */
public class DataTestTest
{
	private Boolean isOutput = false; // set this to false to suppress println

	private ItemPriceList readPriceData() 
	{
        String base = "BASEPRICE";
        ItemPriceList items = new ItemPriceList();
		try
		{
			//\\Program Files\\BuckitTest\\plugins\\Realms
            File DataFile = new File("\\GIT\\OwnPlugins\\Realms\\plugins", "baseprice.yml");
            if (!DataFile.exists()) 
            {
            	DataFile.createNewFile();
            	return items;
            }
            FileConfiguration config = new YamlConfiguration();
            config.load(DataFile);
            
            if (config.isConfigurationSection(base))
            {
            	
    			Map<String,Object> buildings = config.getConfigurationSection(base).getValues(false);
            	for (String ref : buildings.keySet())
            	{
            		Double price = config.getDouble(base+"."+ref,0.0);
            		ItemPrice item = new ItemPrice(ref, price);
            		items.add(item);
            	}
            }
		} catch (Exception e)
		{
		}
		return items;
	}
	
	
	@Test
	public void testInitOwnerList()
	{
		DataTest data = new DataTest();
		OwnerList oList = data.getTestOwners();
		int expected = 6; 
		int actual = oList.size();
		assertEquals(expected, actual);
		Owner owner = oList.getOwner("NPC0");

		if (isOutput)
		{	
			System.out.println("===Owner Test ====");
			System.out.println(owner.getPlayerName());
			System.out.println(owner.getLevel().name());
		}
	}

	@Test
	public void testInitRealmList()
	{
		DataTest data = new DataTest();
		KingdomList rList = data.getTestRealms();
		int expected = 1; 
		int actual = rList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Realm Test 1 ====");
			System.out.println(rList.getKingdom(1).getName());
			System.out.println(rList.getKingdom(1).getOwner().getPlayerName());
		}
	}

	@Test
	public void testInitRealmListOwner()
	{
		DataTest data = new DataTest();
		KingdomList rList = data.getTestRealms();
		int expected = 1; 
		int actual = rList.size();
		assertEquals(expected, actual);
		
		if (isOutput)
		{	
			System.out.println("===Realm Test 2 ====");
			System.out.println(rList.getKingdom(1).getName());
			System.out.println(rList.getKingdom(1).getOwner().getPlayerName());
		}
	}
	
	@Test
	public void testInitItemList()
	{
//		TestServer server = new TestServer();
		ItemList iList = new ItemList();
		iList.depositItem("AIR",5);
		int expected = 5;
		int actual = iList.getValue("AIR");
		assertEquals(expected, actual);
		if (isOutput)
		{	
			System.out.println("===Item Test 1 ====");
			System.out.println(iList.size());
		}
	}
	
	@Test
	public void testInitItemWeaponList()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getWeaponItems();
		int expected = 6;
		int actual = iList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Item Test 2 ====");
			int i = 0;
			for (String ref : iList.keySet())
			{
				if (ref.contains("SWORD"))
				{
					i++;
					System.out.println(ref+ " : "+iList.getValue(ref));
				}
			}
			System.out.println(i);
		}
	}

	@Test
	public void testInitItemArmorList()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getArmorItems();
		int expected = 20;
		int actual = iList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Item Test 3 ====");
			int i = 0;
			for (String ref : iList.keySet())
			{
				if (ref.contains("CHAIN"))
				{
					i++;
					System.out.println(ref+ " : "+iList.getValue(ref));
				}
			}
			
			System.out.println(i);
		}
	}

	@Test
	public void testInitItemToolList()
	{
		ConfigTest data = new ConfigTest();
		ItemList iList = data.getToolItems();
		int expected = 24;
		int actual = iList.size();
		assertEquals(expected, actual);

		if (isOutput)
		{	
			System.out.println("===Item Test 4 ====");
			int i = 0;
			for (String ref : iList.keySet())
			{
				if (ref.contains("STONE"))
				{
					i++;
					System.out.println(ref+ " : "+iList.getValue(ref));
				}
			}
			
			System.out.println(i);
		}
	}
	@Test
	public void testInitSettleList()
	{
		DataTest data = new DataTest();
        ItemPriceList itemPrices = data.getPriceList();

		SettlementList settleList = new SettlementList(0);
		settleList.addSettlement(data.readSettlement(1,itemPrices));
		settleList.addSettlement(data.readSettlement(2,itemPrices));
		settleList.addSettlement(data.readSettlement(3,itemPrices));
		settleList.addSettlement(data.readSettlement(4,itemPrices));
		ItemPriceList priceList = readPriceData(); 
		
		int expected = 4; 
		int actual = settleList.getSettlements().size();

		
		if (expected != actual)
		{	
			System.out.println("===SettleList Test ====");
			for (Settlement settle : settleList.getSettlements().values())
			{
				System.out.println(settle.getId()+":"+settle.getName());
			}
			ItemList items = new ItemList();
			for (Settlement settle : settleList.getSettlements().values())
			{
				for (Item item : settle.getWarehouse().getItemList().values())
				{
					items.put(item.ItemRef(), item);
				}
			}
			String header = "|";
//			header = header + item.ItemRef()+"|";
			System.out.println("===SettleList Warehouse ===["+items.size()+"]");
			for (Item item : items.values())
			{
				System.out.println(ConfigBasis.setStrleft(item.ItemRef(), 15)
						+":"+ConfigBasis.setStrright(String.valueOf(item.value()),5)
						+":"+ConfigBasis.setStrright(String.valueOf(priceList.getBasePrice(item.ItemRef())), 6)
						); 
			}
			for (Settlement settle : settleList.getSettlements().values())
			{
				System.out.println(settle.getName()+"== HighPrice ==["+items.size()+"]");
				for (Item item : settle.getWarehouse().getItemList().values())
				{
					if (priceList.getBasePrice(item.ItemRef()) >= 10.0)
						System.out.println(ConfigBasis.setStrleft(item.ItemRef(), 15)
								+":"+ConfigBasis.setStrright(String.valueOf(item.value()),5)
								+":"+ConfigBasis.setStrright(String.valueOf(priceList.getBasePrice(item.ItemRef())), 6)
								); 
				}
			}
			for (Settlement settle : settleList.getSettlements().values())
			{
				System.out.println(settle.getName()+"== HighValue ==["+items.size()+"]");
				for (Item item : settle.getWarehouse().getItemList().values())
				{
					if ((item.value() >= 30) && (item.ItemRef() != Material.WHEAT.name()))
					{
						System.out.println(ConfigBasis.setStrleft(item.ItemRef(), 15)
								+":"+ConfigBasis.setStrright(String.valueOf(item.value()),5)
								+":"+ConfigBasis.setStrright(String.valueOf(priceList.getBasePrice(item.ItemRef())), 6)
								);
					}
				}
			}
			for (Settlement settle : settleList.getSettlements().values())
			{
				double sum = 0.0;
				for (Item item : settle.getWarehouse().getItemList().values())
				{
					sum = sum + (item.value() * priceList.getBasePrice(item.ItemRef()));
				}
				sum = ConfigBasis.format2(sum);
				System.out.println(settle.getName()+" Warenwert = "+ConfigBasis.setStrright(String.valueOf(sum), 12)+"");
			}
			for (Settlement settle : settleList.getSettlements().values())
			{
				double sum = 0.0;
				sum = (settle.getResident().getSettlerCount() * 10.0);
				sum = ConfigBasis.format2(sum);
				System.out.println(settle.getName()+" Siedlerwert = "+ConfigBasis.setStrright(String.valueOf(sum), 12)+"");
			}
			
		}
		assertEquals(expected, actual);
	}
	
}
