package net.krglok.realms.manager;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import net.krglok.realms.Realms;
import net.citizensnpcs.api.*;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCDataStore;
import net.citizensnpcs.api.npc.NPCRegistry;

public class NpcManager
{
	private  Realms plugin;
	
	private NPCDataStore store;
	
	private boolean isEnabled = false;
	
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

	public void createNPC(String name, Location position)
	{
		NPCRegistry npcReg =  CitizensAPI.createNamedNPCRegistry(name, store);
		
		NPC npc = npcReg.createNPC(EntityType.VILLAGER, "Villager");
		npc.setName(name);
		npc.setProtected(true);
		npc.spawn(position);
	}
	
}
