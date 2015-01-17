package net.krglok.realms.npc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.trait.LookClose;
import net.krglok.realms.CommandRealms;
import net.krglok.realms.Realms;
import net.krglok.realms.command.CmdRealmsTest;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.manager.NpcManager;
import net.krglok.realms.unit.UnitType;

/**
 * This is a trait for the realms plugin to handle the special NPC of the
 * settlement
 * 
 * @author Krglok
 * 
 */

public class SettlerTrait extends Trait
{

	private Realms plugin = null;

	private static String traitName = "settler";
	
	boolean SomeSetting = false;
	private NPCType npcType ;
	private UnitType unitType ;
	private int settleId;
	private int buildingId;
	private int produceId;
	private int targetId;
	private LocationData locationData;
	private Location targetLocation;
	private ArrayList<String> seenPlayer;

	public SettlerTrait()
	{
		super(traitName);
		plugin = (Realms) Bukkit.getServer().getPluginManager().getPlugin("Realms");
		
		this.npcType = NPCType.BEGGAR;
		this.unitType = UnitType.SETTLER;
		this.settleId = 0;
		this.buildingId = 0;
		this.targetId = 0;
		this.produceId = 0;
		this.locationData = null;
		this.targetLocation = null;
		this.seenPlayer = new ArrayList<String>();
	}


	public int getSettleId()
	{
		return settleId;
	}


	public void setSettleId(int settleId)
	{
		this.settleId = settleId;
	}


	public int getBuildingId()
	{
		return buildingId;
	}


	public void setBuildingId(int buildingId)
	{
		this.buildingId = buildingId;
	}


	public int getTargetId()
	{
		return targetId;
	}


	public void setTargetId(int targetId)
	{
		this.targetId = targetId;
	}


	public NPCType getsNPCType()
	{
		return npcType;
	}

	public void setsNPCType(NPCType npcType)
	{
		this.npcType = npcType;
	}

	public UnitType getsUnitType()
	{
		return unitType;
	}

	public void setsUnitType(UnitType unitType)
	{
		this.unitType = unitType;
	}
	
	// Here you should load up any values you have previously saved.
	// This does NOT get called when applying the trait for the first time, only
	// loading onto an existing npc at server start.
	// This is called AFTER onAttach so you can load defaults in onAttach and
	// they will be overridden here.
	// This is called BEFORE onSpawn so do not try to access
	// npc.getBukkitEntity(). It will be null.
	public void load(DataKey key)
	{
//		SomeSetting = key.getBoolean("SomeSetting", plugin.getConfig().getBoolean("Defaults.SomeSetting"));
		npcType = NPCType.valueOf(key.getString("NPCType", NPCType.BEGGAR.name()));
		unitType = UnitType.valueOf(key.getString("UnitType", UnitType.SETTLER.name()));
		settleId = key.getInt("settleId", 0);
		buildingId = key.getInt("buildingId",0);
		locationData = LocationData.toLocation(key.getString("targetLocation"));
		targetLocation = null;
//		System.out.println("[REALMS] Trait load setting : "+key.toString());
	}

	// Save settings for this NPC. These values will be added to the citizens
	// saves.yml under this NPC.
	public void save(DataKey key)
	{
//		key.setBoolean("SomeSetting", SomeSetting);
		key.setString("NPCType", npcType.name());
		key.setString("UnitType", unitType.name());
		key.setInt("settleId", settleId);
		key.setInt("buildingId", buildingId);
		System.out.println("[REALMS] Trait save setting : "+key.toString());
	}

	@EventHandler
	public void click(net.citizensnpcs.api.event.NPCRightClickEvent event)
	{
		// Handle a click on a NPC. The event has a getNPC() method.
		// Be sure to check event.getNPC() == this.getNPC() so you only handle
		// clicks on this NPC!
		if (event.getNPC() != this.getNPC())
		{
//			System.out.println("[REALMS] Trait Settler , wrong NPC instance !  : "+event.getNPC().getId());
			return;
		} else
		{
			System.out.println("[REALMS] Trait Settler , NPC : "+event.getNPC().getId());
			
		}
		if (event.getClicker().getItemInHand().getType() == Material.BLAZE_ROD)
		{
			event.getClicker().sendMessage("Hallo,my name is "+this.getNPC().getFullName());
			event.getClicker().sendMessage("my Job is "+this.getNPC().getTrait(SettlerTrait.class).getsNPCType());
//			for (Trait trait : npc.getTraits())
//			{
//				event.getClicker().sendMessage("MyTrait :"+ trait.getName()+":"+trait.getClass().getName());
//			}
			plugin.npcManager.equipNpc(npc, this.npcType);
			ItemStack[] equip = npc.getTrait(Equipment.class).getEquipment();
			int i = 0;
			for (ItemStack item : equip)
			{
				if (item != null)
				{
					event.getClicker().sendMessage("equip "+i+": "+item.getType());
				} else
				{
					event.getClicker().sendMessage("equip "+i+": "+"NULL");
					
				}
				i++;
			}

			npc.getTrait(LookClose.class).lookClose(true);
//			if (buildingId == 0)
//			{
//				Region region = CmdRealmsTest.findRegionAtPosition(plugin, npc.getStoredLocation());
//				buildingId = region.getID();
//			}
			return;
		}
		if (event.getClicker().getItemInHand().getType() == Material.STICK)
		{
			event.getClicker().sendMessage("I am a Settler of "+this.getNPC().getTrait(SettlerTrait.class).getName());
			event.getClicker().sendMessage("my name is "+this.getNPC().getFullName());
			event.getClicker().sendMessage("my job is "+this.getNPC().getTrait(SettlerTrait.class).getsNPCType());
			event.getClicker().sendMessage("my village "+this.getNPC().getTrait(SettlerTrait.class).getSettleId());
			event.getClicker().sendMessage("my home is "+this.getNPC().getTrait(SettlerTrait.class).getBuildingId());
			event.getClicker().sendMessage("my target is "+this.getNPC().getTrait(SettlerTrait.class).getTargetId());
			return;
		} else
		{
			// simple message
			String playerName = "stranger";
			boolean isSeen = false;
			if (seenPlayer.contains(event.getClicker().getUniqueId().toString()))
			{
				playerName = ChatColor.GREEN+event.getClicker().getDisplayName()+ChatColor.YELLOW;
				isSeen = true;
			} else
			{
				seenPlayer.add(event.getClicker().getUniqueId().toString());
			}
			if (this.npcType == NPCType.CHILD)
			{
				event.getClicker().sendMessage("I dont speak with alien !");
				return;
			}
			event.getClicker().sendMessage(ChatColor.YELLOW+"Hallo, "+playerName+". My name is "+this.getNPC().getFullName());
			if (this.settleId > 0)
			{
				event.getClicker().sendMessage("This is the settlement "+plugin.getRealmModel().getSettlements().getSettlement(this.settleId).getName());
			}
			if (isSeen)
			{
				event.getClicker().sendMessage("I have seen you before ");
				
			} else
			{
				event.getClicker().sendMessage("I have never seen you before ");
			}
			
//			LocationData pos = plugin.getRealmModel().getSettlements().getSettlement(this.settleId).getPosition();
//			targetLocation = new Location(plugin.getServer().getWorld(pos.getWorld()),pos.getX(), pos.getY(),pos.getZ());
//			npc.getNavigator().setTarget(targetLocation);
//			npc.faceLocation(targetLocation);
//			npc.getNavigator().setPaused(false);
		}
		
	}

	@EventHandler
	public void click(net.citizensnpcs.api.event.NPCLeftClickEvent event)
	{
		if (event.getNPC() != this.getNPC())
		{
//			System.out.println("[REALMS] Trait Settler , wrong NPC instance !  : "+event.getNPC().getId());
			return;
		} else
		{
			System.out.println("[REALMS] Trait Settler , NPC : "+event.getNPC().getId());
			
		}
		if (event.getClicker().getItemInHand().getType() == Material.BLAZE_ROD)
		{
			event.getClicker().sendMessage("Only on <RightClck> and for Ops and Admin ");
			return;
		}
		if (event.getClicker().getItemInHand().getType() == Material.STICK)
		{
			event.getClicker().sendMessage("Only on <RightClck> and for Ops and Admin ");
			return;
		}
		if (event.getClicker().getItemInHand().getType() == Material.AIR)
		{
			event.getClicker().sendMessage("I have no Action for you ");
			event.getClicker().sendMessage("Please ask the managers in the HALL. ");
			return;
		} else
		{
			event.getClicker().sendMessage("I warn you! Dont hurt me ! ");
			event.getClicker().sendMessage("or I calling the the Militia ! ");
			return;
			
		}

	}
	// Run code when your trait is attached to a NPC.
	// This is called BEFORE onSpawn so do not try to access
	// npc.getBukkitEntity(). It will be null.
	@Override
	public void onAttach()
	{
		plugin.getServer().getLogger().info(npc.getName() + " has been assigned MyTrait!");

		// This will send a empty key to the Load method, forcing it to load the
		// config.yml defaults.
		// Load will get called again with a real key if this NPC has previously
		// been saved
		load(new net.citizensnpcs.api.util.MemoryDataKey());
	}

	// Run code when the NPC is despawned. This is called before the entity
	// actually despawns so npc.getBukkitEntity() is still valid.
	@Override
	public void onDespawn()
	{
		
	}

	// Run code when the NPC is spawned. Note that npc.getBukkitEntity() will be
	// null until this method is called.
	// This is called AFTER onAttach and AFTER Load when the server is started.
	@Override
	public void onSpawn()
	{
		if (plugin.getRealmModel().isInit() )
		{
			
			
		} else
		{
			System.out.println("[REALMS] "+traitName+ "not spawned because RelamModel not ready !");
		}

	}

	// run code when the NPC is removed. Use this to tear down any repeating
	// tasks.
	@Override
	public void onRemove()
	{
		
	}

}
