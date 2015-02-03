package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.citizensnpcs.trait.Age;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.VillagerProfession;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.kingdom.NobleTrait;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.npc.SettlerTrait;
import net.krglok.realms.unit.UnitType;

public class NobleManager
{
	private  Realms plugin;
	private boolean isEnabled = false;
//	private NpcList npcList = new NpcList();
	private boolean isSpawn = false;
	private ArrayList<Integer> spawnList = new ArrayList<Integer>();
	private boolean isNpcInit = false;

	
	public NobleManager(Realms plugin)
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


	/**
	 * @return the isSpawn
	 */
	public boolean isSpawn()
	{
		return isSpawn;
	}


	/**
	 * @param isSpawn the isSpawn to set
	 */
	public void setSpawn(boolean isSpawn)
	{
		this.isSpawn = isSpawn;
	}


	/**
	 * @return the spawnList
	 */
	public ArrayList<Integer> getSpawnList()
	{
		return spawnList;
	}


	/**
	 * @param spawnList the spawnList to set
	 */
	public void setSpawnList(ArrayList<Integer> spawnList)
	{
		this.spawnList = spawnList;
	}


	/**
	 * @return the isNpcInit
	 */
	public boolean isNpcInit()
	{
		return isNpcInit;
	}


	/**
	 * @param isNpcInit the isNpcInit to set
	 */
	public void setNpcInit(boolean isNpcInit)
	{
		this.isNpcInit = isNpcInit;
	}
	
	public void initNoble()
	{
		System.out.println("[REALMS] Init spawnList of Noble ");
		
		for (NpcData npcData : plugin.getData().getNpcs().values())
		{
			if ((npcData.isSpawned == false)
				&& (npcData.getNpcType() == NPCType.NOBLE)
				)
			{
				if (npcData.isAlive())
				{
					spawnList.add(npcData.getId());
				}
			}
		}
		isNpcInit = true;
	}

	public void createNoble(NpcData npc, LocationData position)
	{
		if (isEnabled == false)  
		{
			System.out.println("Noble Manager not enabled !");
			return; 
		}
		
		if (npc.isSpawned == false)
		{
			if (npc.getNpcType() == NPCType.NOBLE)
			{
				System.out.println("Noble create : "+npc.getId()+":"+npc.getNpcType());
				int spawnId = doNobleSpawn( npc.getName(), npc.getNoble(), position,npc.getSettleId(), npc.getHomeBuilding()) ;
				if (spawnId >= 0)
				{
					npc.isSpawned = true;
					npc.spawnId = spawnId;
				}
			}
		}
	}

	public int doNobleSpawn(String name, NobleLevel noble, LocationData position, int settleId, int buildingId)
	{
//		System.out.println("[REALMS) Settler Spawn "+name);
		NPC npc = null;
		NobleTrait nTrait = null;
		position.setZ(position.getZ()+1);

		npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "noble");
		
		switch(noble)
		{
		case KNIGHT:
			npc.setProtected(true);
			npc.setName(noble.name()+" "+name);
			nTrait = doAddTrait(npc, noble, settleId, buildingId);
			break;
		case EARL:
			npc.setProtected(true);
			npc.setName(noble.name()+" "+name);
			nTrait = doAddTrait(npc, noble, settleId, buildingId);
			break;
		case LORD:
			npc.setProtected(true);
			npc.setName(noble.name()+" "+name);
			nTrait = doAddTrait(npc, noble, settleId, buildingId);
			break;
		case KING:
			npc.setProtected(true);
			npc.setName(noble.name()+" "+name);
			nTrait = doAddTrait(npc, noble, settleId, buildingId);
			break;
			
		default:
			npc.setProtected(false);
			npc.setName(name);
			nTrait = doAddTrait(npc, noble, settleId, buildingId);
			break;
		}
		if (npc != null)
		{
			Location pos = new Location(plugin.getServer().getWorld(position.getWorld()), position.getX(), position.getY(), position.getZ());
			npc.spawn(pos);
			equipNoble(npc, noble);
			return npc.getId();
		} else
		{
			return -1;
		}
	}
	
	public NobleTrait doAddTrait(NPC npc, NobleLevel noble, int settleId, int buildingId)
	{
//		System.out.println("[REALMS] add trait  NPC ");
		NobleTrait nTrait = new NobleTrait();
		npc.getTrait(LookClose.class).lookClose(true);
		float newSpeed = (float) 0.8;
		npc.getNavigator().getDefaultParameters().speedModifier(newSpeed );
//		Waypoints wp = npc.getTrait(Waypoints.class);
//		Anchors anchors = npc.getTrait(Anchors.class);
		switch(noble)
		{
		case KNIGHT:
			npc.addTrait(nTrait);
			nTrait.setNpcType(NPCType.NOBLE);
			nTrait.setUnitType(UnitType.SETTLER);
			nTrait.setNoble(noble);
			nTrait.setBuildingId(buildingId);
			equipNoble(npc, noble);
			break;
		case EARL:
			npc.addTrait(nTrait);
			nTrait.setNpcType(NPCType.NOBLE);
			nTrait.setUnitType(UnitType.SETTLER);
			nTrait.setNoble(noble);
			nTrait.setBuildingId(buildingId);
			equipNoble(npc, noble);
			break;
		case LORD:
			npc.addTrait(nTrait);
			nTrait.setNpcType(NPCType.NOBLE);
			nTrait.setUnitType(UnitType.SETTLER);
			nTrait.setNoble(noble);
			nTrait.setBuildingId(buildingId);
			equipNoble(npc, noble);
			break;
		case KING:
			npc.addTrait(nTrait);
			nTrait.setNpcType(NPCType.NOBLE);
			nTrait.setUnitType(UnitType.SETTLER);
			nTrait.setNoble(noble);
			nTrait.setBuildingId(buildingId);
			equipNoble(npc, noble);
			break;
			
		default:
			npc.addTrait(nTrait);
			nTrait.setNpcType(NPCType.NOBLE);
			nTrait.setUnitType(UnitType.SETTLER);
			nTrait.setNoble(NobleLevel.COMMONER);
			nTrait.setBuildingId(buildingId);
			break;
		}
		return nTrait;
		
	}

	public void equipNoble(NPC npc, NobleLevel noble)
	{
//		System.out.println("[REALMS] equip NPC  ");
//		Inventory inv   = npc.getTrait(Inventory.class);
		Equipment equip;
		switch(noble)
		{
		case KNIGHT:
			equip = npc.getTrait(Equipment.class);
			equip.set(EquipmentSlot.HAND,new ItemStack(Material.IRON_SWORD,1));
			equip.set(EquipmentSlot.BOOTS,new ItemStack(Material.DIAMOND_BOOTS,1));
			equip.set(EquipmentSlot.LEGGINGS,new ItemStack(Material.DIAMOND_LEGGINGS,1));
			equip.set(EquipmentSlot.CHESTPLATE,new ItemStack(Material.DIAMOND_CHESTPLATE,1));
			equip.set(EquipmentSlot.HELMET,new ItemStack(Material.DIAMOND_HELMET,1));
			break;
		case EARL:
			equip = npc.getTrait(Equipment.class);
			equip.set(EquipmentSlot.HAND,new ItemStack(Material.DIAMOND_SWORD,1));
			equip.set(EquipmentSlot.BOOTS,new ItemStack(Material.DIAMOND_BOOTS,1));
			equip.set(EquipmentSlot.LEGGINGS,new ItemStack(Material.DIAMOND_LEGGINGS,1));
			equip.set(EquipmentSlot.CHESTPLATE,new ItemStack(Material.DIAMOND_CHESTPLATE,1));
			equip.set(EquipmentSlot.HELMET,new ItemStack(Material.DIAMOND_HELMET,1));
			break;
		case LORD:
//			System.out.println("[REALMS] Trait equip "+npcType.name());
			equip = npc.getTrait(Equipment.class);
			equip.set(EquipmentSlot.HAND,new ItemStack(Material.DIAMOND_SWORD,1));
			equip.set(EquipmentSlot.BOOTS,new ItemStack(Material.DIAMOND_BOOTS,1));
			equip.set(EquipmentSlot.LEGGINGS,new ItemStack(Material.DIAMOND_LEGGINGS,1));
			equip.set(EquipmentSlot.CHESTPLATE,new ItemStack(Material.DIAMOND_CHESTPLATE,1));
			equip.set(EquipmentSlot.HELMET,new ItemStack(Material.GOLD_HELMET,1));
			break;
		case KING:
			equip = npc.getTrait(Equipment.class);
			equip.set(EquipmentSlot.HAND,new ItemStack(Material.GOLD_SWORD,1));
			equip.set(EquipmentSlot.BOOTS,new ItemStack(Material.GOLD_BOOTS,1));
			equip.set(EquipmentSlot.LEGGINGS,new ItemStack(Material.GOLD_LEGGINGS,1));
			equip.set(EquipmentSlot.CHESTPLATE,new ItemStack(Material.GOLD_CHESTPLATE,1));
			equip.set(EquipmentSlot.HELMET,new ItemStack(Material.GOLD_HELMET,1));
			break;
		default:
			break;
		}
		
	}
	
}
