package net.krglok.realms.unit;

import java.util.ArrayList;

import net.krglok.realms.core.Bank;
import net.krglok.realms.core.Barrack;
import net.krglok.realms.core.BoardItemList;
import net.krglok.realms.core.BuildingList;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Resident;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Townhall;
import net.krglok.realms.core.Trader;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.manager.BuildManager;
import net.krglok.realms.manager.MapManager;
import net.krglok.realms.manager.SettleManager;
import net.krglok.realms.manager.TradeManager;

import org.bukkit.block.Biome;

/**
 * Das Privateer hat folgende Eigenschaften
  Einen Namen
  Eine aktuelle Position
  Einen Status
  Eine aktuelle Aktion, die es ausführt
  Power, die Macht der Einheiten, die es umfaßt
  Liste der Einheiten nach Typen unterteilt.
o Militia
o Archer
o Settler
 * @author Windu
 *
 */
public class Regiment {

	private static int lfdID;
	
	private int id;
	private RegimentType settleType = RegimentType.PRIVATEER;
	private LocationData position;
	private String name;
	private String owner;
	private Barrack barrack ;
	private Warehouse warehouse ;
	private Bank bank;
	private ItemList requiredProduction;
	
	private Boolean isEnabled;
	private Boolean isActive;
	
	private double hungerCounter = 0.0;
	private double foodConsumCounter;
	
	private BoardItemList battleOverview;

	private double FoodFactor = 0.0;

	private String world;
	private Biome biome;
	private long age;
	private BuildManager buildManager;
	

	
	
	
}
