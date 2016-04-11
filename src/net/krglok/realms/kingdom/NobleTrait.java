
package net.krglok.realms.kingdom;

import java.util.ArrayList;

import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.trait.LookClose;
import net.krglok.realms.Realms;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.npc.NPCType;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.unit.UnitType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class NobleTrait extends Trait
{
	private Realms plugin = null;

	private static String traitName = "noble";
	
	private NPCType npcType ;
	private UnitType unitType ;
	private NobleLevel noble;
	private int settleId;
	private int lehenId;
	private int buildingId;
	private int produceId;
	private int targetId;
	private LocationData locationData;
	private Location targetLocation;
	private ArrayList<String> seenPlayer;
	public  boolean isStuck = false;

	public NobleTrait()
	{
		super(traitName);
		plugin = (Realms) Bukkit.getServer().getPluginManager().getPlugin("Realms");
		
		this.npcType = NPCType.NOBLE;
		this.unitType = UnitType.SETTLER;
		this.noble = NobleLevel.COMMONER;
		this.settleId = 0;
		this.lehenId = 0;
		this.buildingId = 0;
		this.targetId = 0;
		this.produceId = 0;
		this.locationData = null;
		this.targetLocation = null;
		this.seenPlayer = new ArrayList<String>();
}

	/**
	 * @return the plugin
	 */
	public Realms getPlugin()
	{
		return plugin;
	}

	/**
	 * @param plugin the plugin to set
	 */
	public void setPlugin(Realms plugin)
	{
		this.plugin = plugin;
	}

	/**
	 * @return the npcType
	 */
	public NPCType getNpcType()
	{
		return npcType;
	}

	/**
	 * @param npcType the npcType to set
	 */
	public void setNpcType(NPCType npcType)
	{
		this.npcType = npcType;
	}

	/**
	 * @return the unitType
	 */
	public UnitType getUnitType()
	{
		return unitType;
	}

	/**
	 * @param unitType the unitType to set
	 */
	public void setUnitType(UnitType unitType)
	{
		this.unitType = unitType;
	}

	/**
	 * @return the lehenId
	 */
	public int getLehenId()
	{
		return lehenId;
	}

	/**
	 * @param lehenId the lehenId to set
	 */
	public void setLehenId(int lehenId)
	{
		this.lehenId = lehenId;
	}

	/**
	 * @return the buildingId
	 */
	public int getBuildingId()
	{
		return buildingId;
	}

	/**
	 * @param buildingId the buildingId to set
	 */
	public void setBuildingId(int buildingId)
	{
		this.buildingId = buildingId;
	}

	/**
	 * @return the produceId
	 */
	public int getProduceId()
	{
		return produceId;
	}

	/**
	 * @param produceId the produceId to set
	 */
	public void setProduceId(int produceId)
	{
		this.produceId = produceId;
	}

	/**
	 * @return the targetId
	 */
	public int getTargetId()
	{
		return targetId;
	}

	/**
	 * @param targetId the targetId to set
	 */
	public void setTargetId(int targetId)
	{
		this.targetId = targetId;
	}

	/**
	 * @return the locationData
	 */
	public LocationData getLocationData()
	{
		return locationData;
	}

	/**
	 * @param locationData the locationData to set
	 */
	public void setLocationData(LocationData locationData)
	{
		this.locationData = locationData;
	}

	/**
	 * @return the targetLocation
	 */
	public Location getTargetLocation()
	{
		return targetLocation;
	}

	/**
	 * @param targetLocation the targetLocation to set
	 */
	public void setTargetLocation(Location targetLocation)
	{
		this.targetLocation = targetLocation;
	}

	/**
	 * @return the seenPlayer
	 */
	public ArrayList<String> getSeenPlayer()
	{
		return seenPlayer;
	}

	/**
	 * @param seenPlayer the seenPlayer to set
	 */
	public void setSeenPlayer(ArrayList<String> seenPlayer)
	{
		this.seenPlayer = seenPlayer;
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
//		npcType = NPCType.valueOf(key.getString("NPCType", NPCType.BEGGAR.name()));
//		unitType = UnitType.valueOf(key.getString("UnitType", UnitType.SETTLER.name()));
//		settleId = key.getInt("settleId", 0);
//		buildingId = key.getInt("buildingId",0);
//		locationData = LocationData.toLocation(key.getString("targetLocation"));
//		targetLocation = null;
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
//		System.out.println("[REALMS] Trait save setting : "+key.toString());
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
			if (plugin.getData().getNpcs().getCitizenId(this.getNPC().getId()).isAlive() == false)
			{
				npc.despawn();
				return;
			}
		}
		NpcData npcData = plugin.getData().getNpcs().getCitizenId(this.getNPC().getId());
		if (npcData == null) { return; }
		
		if (event.getClicker().getItemInHand().getType() == Material.BLAZE_ROD)
		{
			Settlement settle = plugin.getData().getSettlements().getSettlement(npcData.getSettleId());
			if (settle != null)
			{
				event.getClicker().sendMessage("I am a Settler of "+settle.getName());
			} else
			{
				event.getClicker().sendMessage("I have no home and hiking around ");
			}
			event.getClicker().sendMessage("My name is "+this.getNPC().getFullName()+" | "+npcData.getAge()+" years old "+npcData.getGender());
			event.getClicker().sendMessage("My title is "+npcData.getNoble().name());
			event.getClicker().sendMessage(this.getNPC().getId()+":"+npcData.getId()+" job "+npcData.getNobleAction()+" as "+npcData.getNpcType()+" : pregnant "+npcData.isSchwanger());
			Equipment equip = npc.getTrait(Equipment.class);
			for (ItemStack item : equip.getEquipment())
			{
				if (item != null)
				{
					event.getClicker().sendMessage(": "+item.getType()+":"+item.getAmount()+":"+item.getDurability());
				}
			}
			
			npc.getTrait(LookClose.class).lookClose(true);
			return;
		}
		if (event.getClicker().getItemInHand().getType() == Material.STICK)
		{
//			event.getClicker().sendMessage("my home is "+this.getNPC().getTrait(SettlerTrait.class).getBuildingId());
//			event.getClicker().sendMessage("my target is "+this.getNPC().getTrait(SettlerTrait.class).getTargetId());
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
			event.getClicker().sendMessage(ChatColor.YELLOW+"Hallo, "+playerName+". I am "+npcData.getNoble().name()+" "+this.getNPC().getFullName());
			if (this.settleId > 0)
			{
				if (lehenId > 0)
				{
					event.getClicker().sendMessage("This is the lehen "+plugin.getData().getLehen().getLehen(lehenId).getName());
				}
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
			System.out.println("[REALMS] Trait Noble , NPC : "+event.getNPC().getId());
			if (plugin.getData().getNpcs().getCitizenId(this.getNPC().getId()).isAlive() == false)
			{
				npc.despawn();
				return;
			}
			
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
		plugin.getServer().getLogger().info(npc.getName() + " has been assigned NobleTrait!");

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
	
	@EventHandler
	public void onNaviStuck(net.citizensnpcs.api.ai.event.NavigationStuckEvent event)
	{
		if (event.getNPC() != this.getNPC())
		{
			return;
		} else
		{
			isStuck = true;
		}
	
	}

	@EventHandler
	public void onNaviBegin(net.citizensnpcs.api.ai.event.NavigationBeginEvent event)
	{
		if (event.getNPC() != this.getNPC())
		{
			return;
		} else
		{
		}
	}

	@EventHandler
	public void onNaviCancel(net.citizensnpcs.api.ai.event.NavigationCancelEvent event)
	{
		if (event.getNPC() != this.getNPC())
		{
			return;
		} else
		{
			isStuck = true;
		}
	
	}
	@EventHandler
	public void onNaviComplet(net.citizensnpcs.api.ai.event.NavigationCompleteEvent event)
	{
		if (event.getNPC() != this.getNPC())
		{
			return;
		} else
		{
		}
	
	}
	
	@EventHandler
	public void onNaviReplace(net.citizensnpcs.api.ai.event.NavigationReplaceEvent event)
	{
		if (event.getNPC() != this.getNPC())
		{
			return;
		} else
		{
	
		}
	}

	/**
	 * @return the noble
	 */
	public NobleLevel getNoble()
	{
		return noble;
	}

	/**
	 * @param noble the noble to set
	 */
	public void setNoble(NobleLevel noble)
	{
		this.noble = noble;
	}
	
	
}
