package net.krglok.realms;

import java.util.ArrayList;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.citizensnpcs.trait.LookClose;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.UnitTrait;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class UnitManager
{
	private  Realms plugin;
	private boolean isEnabled = false;
//	private NpcList npcList = new NpcList();
	private boolean isSpawn = false;
	private ArrayList<Integer> spawnList = new ArrayList<Integer>();
	private boolean isNpcInit = false;

	
	public UnitManager(Realms plugin)
	{
		this.plugin = plugin;
	}
	
	public void initNpc()
	{
		System.out.println("[REALMS] Init spawnList of Units ");
		
		for (NpcData npcData : plugin.getData().getNpcs().values())
		{
			if ((npcData.isSpawned == false)
				&& (npcData.getNpcType() == NPCType.MILITARY)
				)
			{
				spawnList.add(npcData.getId());
			}
		}
		isNpcInit = true;
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

	/**
	 * make a sentry NPC
	 * 
	 * @param npc
	 * @param position
	 */
	public void createUnit(NpcData npc, LocationData position)
	{
		if (isEnabled == false)  
		{
			System.out.println("NPC Manager not enabled !");
			return; 
		}
		if (npc.isSpawned == false)
		{
			if (npc.getUnitType() != UnitType.SETTLER)
			{
				int spawnId = doUnitSpawn( npc.getName(), npc.getUnitType(), position,npc.getSettleId(), npc.getRegimentId()); // npc.getHomeBuilding()) ;
				if (spawnId >= 0)
				{
					npc.isSpawned = true;
					npc.spawnId = spawnId;
				}
			}
		}
//		npcList.put(npcId, new NpcData(npcId, npcType, UnitType.SETTLER, name, settleId, buildingId, GenderType.MAN, 20));
	}
	

	public void doAddUnit(NPC npc, UnitType unitType, int settleId, int regimentId)
	{
		UnitTrait unit;
		unit = new UnitTrait();
		npc.addTrait(unit);
		unit.setRegimentId(regimentId);
		unit.setSettleId(settleId);
		
		npc.getTrait(LookClose.class).lookClose(true);
		float newSpeed = (float) 0.8;
		npc.getNavigator().getDefaultParameters().speedModifier(newSpeed );
		
		switch(unitType)
		{
		case MILITIA:
			Equipment equip = npc.getTrait(Equipment.class);
			equip.set(EquipmentSlot.HAND,new ItemStack(Material.STONE_SWORD,1));
			equip.set(EquipmentSlot.BOOTS,new ItemStack(Material.LEATHER_BOOTS,1));
			equip.set(EquipmentSlot.LEGGINGS,new ItemStack(Material.LEATHER_LEGGINGS,1));
			equip.set(EquipmentSlot.CHESTPLATE,new ItemStack(Material.LEATHER_CHESTPLATE,1));
			equip.set(EquipmentSlot.HELMET,new ItemStack(Material.LEATHER_HELMET,1));
			break;
		default:
			npc.getTrait(Equipment.class).set(EquipmentSlot.HAND,new ItemStack(Material.STICK,1));
			npc.getTrait(Equipment.class).set(EquipmentSlot.HELMET,new ItemStack(Material.LEATHER_HELMET,1));
			npc.getTrait(Equipment.class).set(EquipmentSlot.BOOTS,new ItemStack(Material.LEATHER_BOOTS,1));
//			sentry.getInstance().RespawnDelaySeconds = 20;
			break;
		}
		
	}
	
	
	public int doUnitSpawn(String name, UnitType unitType, LocationData position, int settleId, int regimentId)
	{
		System.out.println("[REALMS) Sentry Spawn "+name);
		NPC npc = null;
		position.setZ(position.getZ()+1);
		npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "Militia");
		
		if (npc != null)
		{
			Location pos = new Location(plugin.getServer().getWorld(position.getWorld()), position.getX(), position.getY(), position.getZ());
			npc.spawn(pos);
			doAddUnit( npc, unitType,  settleId,  regimentId);
			return npc.getId();
		} else
		{
			System.out.println("[REALMS) Unit NOT spawn "+name);
			return -1;
		}
	}

}
