package net.krglok.realms.unittest;

import java.util.ArrayList;
import java.util.logging.Logger;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.data.DataStorage;

import org.junit.Test;

public class PriceTableTest
{
	private ArrayList<String> printPricelist(DataStorage data)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("============================");
		msg.add("Pricelist for Realms 0.9.5.3");
		for (String key : data.getPriceList().sortItems())
		{
			String s = ConfigBasis.setStrleft(key,14);
			s = s + ":"+ConfigBasis.setStrformat2(data.getPriceList().getBasePrice(key),8);
			msg.add(s);
		}
		return msg;
	}

	
	private ArrayList<String> printBuildinglist(ServerTest server)
	{
		ArrayList<String> msg = new ArrayList<String>();
		msg.add("============================");
		msg.add("Productionlist Realms 0.9.5.3");
		String s = ConfigBasis.setStrleft("Name",15)
		+"|"+ConfigBasis.setStrright("ID", 3)
		+ "|"+ConfigBasis.setStrleft("Product",12)
		+ "|"+ConfigBasis.setStrleft("Anz",3)
		+"|"+ConfigBasis.setStrleft("Price",7)
		+"|"+ConfigBasis.setStrleft("Umsatz",7)
		+"|"+ConfigBasis.setStrleft("Kosten",7)
		+"|"+ConfigBasis.setStrleft("Ertrag",7)
		+"|"+ConfigBasis.setStrleft("   Npc",7);
		msg.add(s);
		for (BuildPlanType bType : BuildPlanType.values())
		{
			if ((BuildPlanType.getBuildGroup(bType) == 200 )
				&& (bType.getValue() != 231)
				&& (bType.getValue() != 232)
				&& (bType.getValue() != 233)
				&& (bType.getValue() != 234)
				&& (bType.getValue() != 236)
				&& (bType.getValue() != 239)
				)
			{
				s = ConfigBasis.setStrleft(bType.name(),15)
						+"|"+ConfigBasis.setStrright(bType.getValue(), 3);
				ItemList output = server.getRegionOutput(bType.name());
				ItemList input = server.getRegionUpkeep(bType.name());
				if (output.size() > 0)
				{
					// hole das ItemRef des ersten OutputItem
					String refItem = output.values().iterator().next().ItemRef();
					// berechne Maetrialksoten
					double cost = server.getRecipePrice(refItem, input);
					// berechne Materialkostenanteil pro Item der Outputlist
					cost = cost / output.size();
					//erstelle Output liste 
					for (Item item : output.values())
					{
						double umsatz = server.data.getPriceList().getBasePrice(item.ItemRef())*item.value();
						String sOut = s 
								+ "|"+ConfigBasis.setStrleft(item.ItemRef(),12)
								+ "|"+ConfigBasis.setStrright(item.value(),3);
						sOut = sOut
							+"|"+ConfigBasis.setStrformat2(server.data.getPriceList().getBasePrice(item.ItemRef()),7)
							+"|"+ConfigBasis.setStrformat2(umsatz,7)
							+"|"+ConfigBasis.setStrformat2(cost,7)
							+"|"+ConfigBasis.setStrformat2(umsatz - cost,7)
							+"|"+ConfigBasis.setStrformat2((umsatz - cost)/2,7);
						msg.add(sOut);
					}
				}
			}
		}
		return msg;
	}
	
	
	/**
	 * Ausgabe einer Stringliste zur Console
	 * Die String mueessen bereits formatiert sein
	 * 
	 * @param msg
	 */
	public void printConsole(ArrayList<String> msg)
	{
		for (int i = 0; i < msg.size(); i++)
		{
			System.out.println(msg.get(i));
		}
	}
	
	@Test
	public void test()
	{
		Logger logger = Logger.getAnonymousLogger();
		String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
		DataStorage data = new DataStorage(dataFolder);
		data.initData();
		ServerTest server = new ServerTest(data);
		
		ArrayList<String> msg = new ArrayList<String>();
		msg = printPricelist(data);
		printConsole(msg);
		msg = printBuildinglist(server);
		printConsole(msg);
		
		System.out.print("ENDE");
	}

}
