package net.krglok.realms.data;

import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.entity.EntityType;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ItemList;

public interface ConfigInterface
{

	public Boolean initConfigData();
	
	public String getVersion();
	
	public String getPluginName();

	public ItemList getToolItems();

	public ItemList getWeaponItems();

	public ItemList getArmorItems();
	
	public BuildPlanType superRegionToBuildingType(String superRegionTypeName);
	
	public String getRegionType(BuildPlanType bType);

	public BuildPlanType regionToBuildingType(String regionTypeName);

	public ItemList getBuildMaterialItems();
	
	public ItemList getMaterialItems();
	
	public ItemList getOreItems();
	
	public ItemList getValuables();
	
	public ItemList getRawItems();
	
	public ItemList getFoodItems();
	
	public boolean isUpdateCheck();
	
	public boolean isAutoUpdate();
	
	public ArrayList<EntityType> getMobsToRepel();
}
