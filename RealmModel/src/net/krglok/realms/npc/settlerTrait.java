package net.krglok.realms.npc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.citizensnpcs.api.astar.pathfinder.BlockExaminer;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.npc.ai.CitizensNavigator.DoorExaminer;
import net.citizensnpcs.trait.LookClose;
import net.citizensnpcs.trait.waypoint.Waypoints;
import net.krglok.realms.CommandRealms;
import net.krglok.realms.NpcManager;
import net.krglok.realms.Realms;
import net.krglok.realms.command.CmdRealmsTest;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
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
	private boolean isNavi = true;

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
		this.isNavi = true;
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
	 * @return the isNavi
	 */
	public boolean isNavi()
	{
		return isNavi;
	}


	/**
	 * @param isNavi the isNavi to set
	 */
	public void setNavi(boolean isNavi)
	{
		this.isNavi = isNavi;
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
			
		}
		if (event.getClicker().getItemInHand().getType() == Material.BLAZE_ROD)
		{
			event.getClicker().sendMessage("Hallo,my name is "+this.getNPC().getFullName());
			event.getClicker().sendMessage("my Job is "+this.getNPC().getTrait(SettlerTrait.class).getsNPCType());
			NpcData npcData = plugin.getData().getNpcs().getCitizenId(this.getNPC().getId());
			if (npcData == null) { return; }
			Settlement settle = plugin.getData().getSettlements().getSettlement(npcData.getSettleId());
			if (settle != null)
			{
				event.getClicker().sendMessage("I am a Settler of "+settle.getName());
			} else
			{
				event.getClicker().sendMessage("I have no home and hiking around ");
			}
			event.getClicker().sendMessage("my name is "+this.getNPC().getFullName()+" | "+npcData.getAge()+" years old "+npcData.getGender());
			event.getClicker().sendMessage(this.getNPC().getId()+":"+npcData.getId()+" job "+npcData.getNpcAction()+" as "+npcData.getNpcType()+" : pregnant "+npcData.isSchwanger());
			
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
//		plugin.getServer().getLogger().info(npc.getName() + " has been assigned SettlerTrait!");

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
			if (this.npcType == NPCType.MANAGER)
			{
				if (isNavi)
				{
					Location actual = event.getNPC().getEntity().getLocation();
					if (isCloseDoor(actual))
					{
						System.out.println(event.getNPC().getId()+" NPC "+" door opened ");
					} else
					{
						System.out.println(event.getNPC().getId()+" NPC "+" Stuck teleport");
						if (targetLocation != null)
						{
							event.getNPC().teleport(targetLocation, TeleportCause.PLUGIN);
						}
					}
				} else
				{
					if (this.npcType == NPCType.MANAGER)
					{
						System.out.println(event.getNPC().getId()+" NPC "+" Stuck wander");
					}
				}
			}
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
			if (isNavi == false)
			{
				if (this.npcType == NPCType.MANAGER)
				{
//					System.out.println(event.getNPC().getId()+" NPC "+" Start wander ");
				}
			} else
			{
				if (this.npcType == NPCType.MANAGER)
				{
//					System.out.println(event.getNPC().getId()+" NPC "+" Start linear ");
				}
			}
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
			
			if (this.npcType == NPCType.MANAGER)
			{
//				System.out.println(event.getNPC().getId()+" NPC "+" Cancal navigation ");
			}
			Location actual = event.getNPC().getEntity().getLocation();
			if (isCloseDoor(actual))
			{
				System.out.println(event.getNPC().getId()+" NPC "+" door opened ");
			}
			if (isNavi)
			{
				this.targetLocation = event.getNPC().getNavigator().getTargetAsLocation();
				isNavi = false;
				event.getNPC().getTrait(Waypoints.class).setWaypointProvider("wander");
				if (this.npcType == NPCType.MANAGER)
				{
//					System.out.println(event.getNPC().getId()+" NPC "+" set wander ");
				}
			} else
			{
				event.getNPC().getTrait(Waypoints.class).setWaypointProvider("linear");
				event.getNPC().getNavigator().setTarget(targetLocation);
				isNavi = true;
				if (this.npcType == NPCType.MANAGER)
				{
//					System.out.println(event.getNPC().getId()+" NPC "+" Set linear ");
				}
			}
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
			if (isNavi == true)
			{
				if (this.npcType == NPCType.MANAGER)
				{
//					System.out.println(event.getNPC().getId()+" NPC "+" Complete linear ");
				}
				Location actual = event.getNPC().getEntity().getLocation();
				if (isCloseDoor(actual))
				{
					System.out.println(event.getNPC().getId()+" NPC "+" door opened ");
				}
				this.targetLocation = event.getNPC().getNavigator().getTargetAsLocation();
				if (event.getNPC().getNavigator().getTargetAsLocation().distanceSquared(actual) > 2.0)
				{
					event.getNPC().getNavigator().setTarget(this.targetLocation);
					event.getNPC().getNavigator().setPaused(false);
					if (this.npcType == NPCType.MANAGER)
					{
//						System.out.println(event.getNPC().getId()+" NPC "+" Restart due to distance ");
					}
				}
			} else
			{
				event.getNPC().getTrait(Waypoints.class).setWaypointProvider("linear");
				event.getNPC().getNavigator().setTarget(targetLocation);
				isNavi = true;
				if (this.npcType == NPCType.MANAGER)
				{
					System.out.println(event.getNPC().getId()+" NPC "+" Complete wander ");
				}
				
			}
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
			if (this.npcType == NPCType.MANAGER)
			{
				if (isNavi)
				{
					if (this.npcType == NPCType.MANAGER)
					{
//						System.out.println(event.getNPC().getId()+" NPC "+" Replace linear ");
					}
				} else
				{
					if (this.npcType == NPCType.MANAGER)
					{
//						System.out.println(event.getNPC().getId()+" NPC "+" Replace wander ");
					}
				}
			}
		}

	}
	
	private boolean isCloseDoor(Location actual)
	{
		BlockFace[] blockFaces = {BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH};
		actual.setY(actual.getY());
		Block base = actual.getBlock();
		for(BlockFace bf : blockFaces) 
		{
		    Block bu = base.getRelative(bf);
		    if((bu.getType() == Material.WOODEN_DOOR)) 
		    {
		    	byte openData = 0x4;
		    	byte doorData = (byte) (bu.getData());
				if ((doorData & 0x4) == 0x4)
				{
					doorData = (byte) (doorData & 0x3);
				} else
				{
					doorData = (byte) (doorData | 0x7);
					
				}
		    	bu.setData(doorData);
		    	
			    if((bu.getRelative(BlockFace.UP).getType() == Material.WOODEN_DOOR)) 
			    {
			    	doorData = (byte) (bu.getRelative(BlockFace.UP).getData());
			    	if ((doorData & 0x1) == 1)
			    	{
			    		doorData = 0x9;
			    	} else
			    	{
			    		doorData = 0x8;
			    	}
			    	bu.getRelative(BlockFace.UP).setData(doorData);
			    }
		        return true;
		    }
		}
		return false;
	}
	
//	public void onN
}
