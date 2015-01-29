package net.krglok.realms.unit;

import java.util.ArrayList;
import java.util.HashMap;

import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Equipment.EquipmentSlot;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.trait.LookClose;
import net.krglok.realms.Realms;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.npc.NpcData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public class UnitTrait  extends Trait
{
	private Realms plugin = null;

	private static String traitName = "unit";

	private UnitType unitType ;
	private int settleId;
	private int regimentId;
	private Location targetLocation;
	private ArrayList<String> seenPlayer;
	private ArrayList<String> enemyPlayer;
	private HashMap<String,Integer> agroPlayer;


	public UnitTrait()
	{
		super(traitName);
		plugin = (Realms) Bukkit.getServer().getPluginManager().getPlugin("Realms");
		this.unitType = UnitType.NONE ;
		this.settleId = 0;
		this.regimentId = 0;
		this.targetLocation = null;
		this.seenPlayer = new ArrayList<String>();
		this.enemyPlayer = new ArrayList<String>();
		this.agroPlayer = new HashMap<String,Integer>(); 

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
	 * @return the settleId
	 */
	public int getSettleId()
	{
		return settleId;
	}


	/**
	 * @param settleId the settleId to set
	 */
	public void setSettleId(int settleId)
	{
		this.settleId = settleId;
	}


	/**
	 * @return the regimentId
	 */
	public int getRegimentId()
	{
		return regimentId;
	}


	/**
	 * @param regimentId the regimentId to set
	 */
	public void setRegimentId(int regimentId)
	{
		this.regimentId = regimentId;
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
	 * @return the enemyPlayer
	 */
	public ArrayList<String> getEnemyPlayer()
	{
		return enemyPlayer;
	}


	/**
	 * @param enemyPlayer the enemyPlayer to set
	 */
	public void setEnemyPlayer(ArrayList<String> enemyPlayer)
	{
		this.enemyPlayer = enemyPlayer;
	}


	public void load(DataKey key)
	{
//		SomeSetting = key.getBoolean("SomeSetting", plugin.getConfig().getBoolean("Defaults.SomeSetting"));
//		System.out.println("[REALMS] Trait load setting : "+key.toString());
	}

	// Save settings for this NPC. These values will be added to the citizens
	// saves.yml under this NPC.
	public void save(DataKey key)
	{
//		key.setBoolean("SomeSetting", SomeSetting);
//		System.out.println("[REALMS] Trait save setting : "+key.toString());
	}
	
	// Run code when your trait is attached to a NPC.
	// This is called BEFORE onSpawn so do not try to access
	// npc.getBukkitEntity(). It will be null.
	@Override
	public void onAttach()
	{
		plugin.getServer().getLogger().info(npc.getName() + " has been assigned UnitTrait!");

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
			System.out.println("[REALMS] Trait Unit , NPC : "+event.getNPC().getId());
			
		}
		if (event.getClicker().getItemInHand().getType() == Material.BLAZE_ROD)
		{
			NpcData npcData = plugin.getData().getNpcs().getCitizenId(this.getNPC().getId());
			if (npcData == null) { return; }
			Settlement settle = plugin.getData().getSettlements().getSettlement(npcData.getSettleId());
			if (settle != null)
			{
				event.getClicker().sendMessage("I am a "+npcData.getUnitType()+" of "+settle.getName());
			} else
			{
				event.getClicker().sendMessage("I have no home and hiking around ");
			}
			event.getClicker().sendMessage("my name is "+this.getNPC().getFullName()+" | "+npcData.getAge()+" years old "+npcData.getGender());
			event.getClicker().sendMessage(this.getNPC().getId()+":"+npcData.getId()+" job "+npcData.getNpcAction()+" as "+npcData.getUnitType());
			Equipment equip = npc.getTrait(Equipment.class);
			equip.set(EquipmentSlot.HAND,new ItemStack(Material.STONE_SWORD,1));
			equip.set(EquipmentSlot.BOOTS,new ItemStack(Material.LEATHER_BOOTS,1));
			
			for (ItemStack item : equip.getEquipment())
			{
				if (item != null)
				{
					event.getClicker().sendMessage(": "+item.getType()+":"+item.getAmount()+":"+item.getDurability());
				}
			}
			return;
		}
		if (event.getClicker().getItemInHand().getType() == Material.STICK)
		{
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
			return;
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
			event.getClicker().sendMessage("Please ask the managers in the HALL. ");
			return;
		} else
		{
			event.getClicker().sendMessage("I warn you! Dont hurt me ! ");
			event.getClicker().sendMessage("We do not tolerate agression ! ");
			if (agroPlayer.keySet().contains(event.getClicker().getName()))
			{
				agroPlayer.put(event.getClicker().getName(),agroPlayer.get(event.getClicker().getName())+1);
			} else
			{
				agroPlayer.put(event.getClicker().getName(),1);
			}
			if (agroPlayer.get(event.getClicker().getName()) > 4)
			{
				if (agroPlayer.get(event.getClicker().getName()) > 6)
				{
					event.getClicker().sendMessage("You are registered as enemy, take the consequence !");
				} else
				{
					event.getClicker().sendMessage("You are not anymore welcome, leave the settlement !");
				}
			}
			return;
			
		}

	}


}
