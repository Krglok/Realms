package net.krglok.realms.manager;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.MobType;
import net.krglok.realms.Realms;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.npc.GenderType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcList;
import net.krglok.realms.npc.SettlerTrait;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.UnitType;

/**
 * <pre>
 * the npcManager must know the plugin diectly, so it cannot involved in RealmsModel
 * the npcManager manage and control the visible NPC in World
 * the manager store the NPC id of citizens in relation to the settlement (HashMap<NpcId, SettleId>)
 * a settlement id = 0 means this is a free/rogue npc
 * Based on the settlement the trait will be added to the npc created by citizens.
 * 
 * 
 * @author Windu
 * </pre>
 */

public class NpcManager
{
	private  Realms plugin;
	private boolean isEnabled = false;
	private NpcList npcList = new NpcList();

	private static int EQUIP_HAND = 0;
	private static int EQUIP_HELMET = 1;
	private static int EQUIP_CHEST = 2;
	private static int EQUIP_LEGGING = 3;
	private static int EQUIP_BOOT = 4;

	public NpcManager(Realms plugin) 
	{
		this.plugin = plugin;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled()
	{
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public HashMap<Integer, NpcData> getNpcList()
	{
		return npcList;
	}

	public void setNpcList(NpcList npcList)
	{
		this.npcList = npcList;
	}

	public void initNpc()
	{
		System.out.println("[REALMS] Init Trait to NPC ");
		NPC npc ;
		NpcData npcData;
		for (int key : npcList.keySet())
		{
			npc = CitizensAPI.getNPCRegistry().getById(key);
			if (npc != null)
			{
				npcData = npcList.get(plugin);
				doAddTrait(npc, npcData.getNpcType(), npcData.getSettleId(), 0);
				System.out.println("[REALMS] Add Trait for NPC "+key);
			} else
			{
				System.out.println("[REALMS] Citizen NPC not found "+key);
			}
		}
	}
	
	/**
	 * create a NPC with citizens2 and the trait settler
	 * the NPC will be stored in the npcList
	 * 
	 * @param name
	 * @param npcType
	 * @param position
	 * @param settle
	 * @param buildingId
	 */
	public void createNPC(String name, NPCType npcType,LocationData position, Settlement settle, int buildingId)
	{
		if (isEnabled == false)  
		{
			System.out.println("NPC Manager not enabled !");
			return; 
		}
		int settleId = 0;
		if (settle != null)
		{
			settleId = settle.getId();
		}
		int npcId = doNPCSpawn( name, npcType, position,settleId, buildingId) ;
		npcList.put(npcId, new NpcData(npcId, npcType, UnitType.SETTLER, name, settleId, buildingId, GenderType.MAN, 20));
	}
	
	public void createUnitNPC(String name, UnitType npcType,LocationData position, Regiment regiment)
	{
		

	}

	private void storeNPCinList(int npcId, NpcData npcData)
	{
		
		npcList.put(npcId,npcData);
	}
	
	/**
	 * create a colored helmet
	 * 
	 * @param npcType
	 * @param color
	 * @return
	 */
	private ItemStack makeNpcHelmet(NPCType npcType, Color color)
	{
		ItemStack item ;
		item = new ItemStack(Material.LEATHER_HELMET);
		LeatherArmorMeta armorMeta = (LeatherArmorMeta) item.getItemMeta();
		armorMeta.setColor(color);
		return item;
	}
	

	private ItemStack makeNPCTool(NPCType npcType)
	{
		ItemStack item = null ;
		switch(npcType)
		{
		case MANAGER:
			item = new ItemStack(Material.BOOK);
			break;
		case FARMER:
			System.out.println("[REALMS] Trait makeNPCTool"+npcType.name());
			item = new ItemStack(Material.WOOD_HOE);
			break;
		case BUILDER:
			item = new ItemStack(Material.WOOD_SPADE);
			break;
		case TRADER:
			item = new ItemStack(Material.CHEST);
			break;
		case CRAFTSMAN:
			item = new ItemStack(Material.WOOD_AXE);
			break;
		case SETTLER:
			break;
		case CHILD:
			break;
		case BEGGAR:
			System.out.println("[REALMS] Trait makeNPCTool"+npcType.name());
			item = new ItemStack(Material.ROTTEN_FLESH);
			break;
			
		default:
			item = null;
			break;
		}
		return item;
	}
	
	public void equipNpc(NPC npc, NPCType npcType)
	{
		Equipment equip = npc.getTrait(Equipment.class);
		Inventory inv   = npc.getTrait(Inventory.class);
		Color color = Color.WHITE;
		switch(npcType)
		{
		case MANAGER:
			equip.set(EQUIP_HAND, makeNPCTool(npcType));
			equip.set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			break;
		case SETTLER:
			equip.set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			break;
		case FARMER:
			System.out.println("[REALMS] Trait equip "+npcType.name());
			equip.set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			equip.set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case BUILDER:
			equip.set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			equip.set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case TRADER:
			equip.set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			equip.set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case CRAFTSMAN:
			equip.set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			equip.set(EQUIP_HAND, makeNPCTool(npcType));
			break;
		case CHILD:
			equip.set(EQUIP_HELMET, makeNpcHelmet(npcType, color));
			break;
		case BEGGAR:
			equip.set(EQUIP_HAND, makeNPCTool(npcType));
			break;
			
		default:
			break;
		}
		
	}
	
	/**
	 * set the sttler trait to an citizen NPC
	 * 
	 * @param npc
	 * @param npcType
	 * @param settleId
	 * @param buildingId
	 * @return
	 */
	public SettlerTrait doAddTrait(NPC npc, NPCType npcType, int settleId, int buildingId)
	{
		SettlerTrait sTrait = new SettlerTrait();
		switch(npcType)
		{
		case MANAGER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
		case SETTLER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
		case CHILD:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
		case FARMER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
		case BUILDER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
		case TRADER:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
		case CRAFTSMAN:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(npcType);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
			
		default:
			npc.addTrait(sTrait);
			sTrait.setsNPCType(NPCType.BEGGAR);
			sTrait.setsUnitType(UnitType.SETTLER);
			sTrait.setSettleId(settleId);
			sTrait.setBuildingId(buildingId);
			equipNpc(npc, npcType);
			break;
		}
		return sTrait;
		
	}
	
	public int doNPCSpawn(String name, NPCType npcType, LocationData position, int settleId, int buildingId)
	{
		NPC npc ;
		SettlerTrait sTrait;
		int npcId = 0;
		switch(npcType)
		{
		case MANAGER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName("Manager");
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			npcId = npc.getId(); 
			break;
		case SETTLER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName("Siedler");
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case FARMER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName("Farmer");
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case BUILDER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName("Baumeister");
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case TRADER:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName("Trader");
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case CRAFTSMAN:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Settler");
			npc.setProtected(true);
			npc.setName("Handwerker");
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		case CHILD:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Teenager");
			npc.setProtected(true);
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
			
		default:
			npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Beggar");
			npc.setProtected(false);
			npc.setName("Beggar");
			sTrait = doAddTrait(npc, npcType, settleId, buildingId);
			break;
		}
		Location pos = new Location(plugin.getServer().getWorld(position.getWorld()), position.getX(), position.getY(), position.getZ());
		npc.spawn(pos);
		return npc.getId();
	}


}
