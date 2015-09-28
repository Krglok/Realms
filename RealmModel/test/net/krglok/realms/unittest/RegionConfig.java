package net.krglok.realms.unittest;

import java.util.ArrayList;
import java.util.List;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;

import org.bukkit.inventory.ItemStack;

public class RegionConfig
{
	    private String name;
	    private  ArrayList<String> friendlyClasses;
	    private  ArrayList<String> enemyClasses;
	    private  ArrayList<String> effects;
	    private  int radius;
	    private  ArrayList<ItemStack> requirements;
	    private  ArrayList<ItemStack> reagents;
	    private  ArrayList<ItemStack> upkeep;
	    private  ArrayList<ItemStack> output;
	    private  double upkeepChance;
	    private  double moneyRequirement;
	    private  double moneyOutput;
	    private  double exp;
	    private  List<String> superRegions;
	    private  int buildRadius;
	    private  int rawBuildRadius;
	    private  int rawRadius;
	    private  String description;
	    private  String group;
	    private  int powerDrain;
	    private  int housing;
	    private  List<String> biome;
	    
	    public RegionConfig()
	    {
	        this.name = "";
	        this.group = "";
	        this.description = "";
	        this.friendlyClasses = new ArrayList<String>();
	        this.enemyClasses = new ArrayList<String>();
	        this.effects = new ArrayList<String>();
	        this.superRegions = new ArrayList<String>();
	        this.radius = 0;
	        this.rawRadius = (int) Math.sqrt(radius);
	        this.rawBuildRadius = (int) Math.sqrt(buildRadius);
	        this.buildRadius = 0;
	        this.requirements = new ArrayList<ItemStack>();
	        this.reagents = new ArrayList<ItemStack>();
	        this.upkeep = new ArrayList<ItemStack>();
	        this.output = new ArrayList<ItemStack>();
	        this.upkeepChance = 0.0;
	        this.moneyRequirement = 0.0;
	        this.moneyOutput = 0.0;
	        this.exp = 0.0;
	        this.powerDrain = 0;
	        this.housing = 0;
	        this.biome = new ArrayList<String>();
	
	    }
	    
	    public RegionConfig(String name
	    		, String group
	    		, ArrayList<String> friendlyClasses
	    		, ArrayList<String> enemyClasses
	    		, ArrayList<String> effects
	    		, int radius
	    		, int buildRadius
	    		, ArrayList<ItemStack> requirements
	    		, List<String> superRegions
	    		, ArrayList<ItemStack> reagents
	    		, ArrayList<ItemStack> upkeep
	    		, ArrayList<ItemStack> output
	    		, double upkeepChance
	    		, double moneyRequirement
	    		, double moneyOutput
	    		, double exp
	    		, String description
	    		, int powerDrain
	    		, int housing
	    		, List<String> biome
	    		) 
	    {
	        this.name = name;
	        this.group = group;
	        this.friendlyClasses = friendlyClasses;
	        this.enemyClasses = enemyClasses;
	        this.effects = effects;
	        this.radius = radius;
	        this.rawRadius = (int) Math.sqrt(radius);
	        this.rawBuildRadius = (int) Math.sqrt(buildRadius);
	        this.buildRadius = buildRadius;
	        this.requirements = requirements;
	        this.superRegions = superRegions;
	        this.reagents = reagents;
	        this.upkeep = upkeep;
	        this.output = output;
	        this.upkeepChance = upkeepChance;
	        this.moneyRequirement = moneyRequirement;
	        this.moneyOutput = moneyOutput;
	        this.exp = exp;
	        this.description = description;
	        this.powerDrain = powerDrain;
	        this.housing = housing;
	        this.biome = biome;
	    }

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public ArrayList<String> getFriendlyClasses()
		{
			return friendlyClasses;
		}

		public void setFriendlyClasses(ArrayList<String> friendlyClasses)
		{
			this.friendlyClasses = friendlyClasses;
		}

		public ArrayList<String> getEnemyClasses()
		{
			return enemyClasses;
		}

		public void setEnemyClasses(ArrayList<String> enemyClasses)
		{
			this.enemyClasses = enemyClasses;
		}

		public ArrayList<String> getEffects()
		{
			return effects;
		}

		public void setEffects(ArrayList<String> effects)
		{
			this.effects = effects;
		}

		public int getRadius()
		{
			return radius;
		}

		public void setRadius(int radius)
		{
			this.radius = radius;
		}

		public ArrayList<ItemStack> getRequirements()
		{
			return requirements;
		}

		public ItemList getRequirementsItem()
		{
			ItemList subList = new ItemList();
			for (ItemStack item : requirements)
			{
				subList.addItem(item.getType().name(), item.getAmount());
			}
			return subList;
		}
		
		public void setRequirements(ArrayList<ItemStack> requirements)
		{
			this.requirements = requirements;
		}

		public ArrayList<ItemStack> getReagents()
		{
			return reagents;
		}
		
		public ItemList getReagentsItem()
		{
			ItemList subList = new ItemList();
			for (ItemStack item : reagents)
			{
				subList.addItem(item.getType().name(), item.getAmount());
			}
			return subList;
		}

		public void setReagents(ArrayList<ItemStack> reagents)
		{
			this.reagents = reagents;
		}

		public ArrayList<ItemStack> getUpkeep()
		{
			return upkeep;
		}

		public ItemList getUpkeepItem()
		{
			ItemList subList = new ItemList();
			for (ItemStack item : upkeep)
			{
				subList.addItem(item.getType().name(), item.getAmount());
			}
			return subList;
		}
		
		public void setUpkeep(ArrayList<ItemStack> upkeep)
		{
			this.upkeep = upkeep;
		}

		public ArrayList<ItemStack> getOutput()
		{
			return output;
		}

		public ItemList getOutputItem()
		{
			ItemList subList = new ItemList();
			for (ItemStack item : output)
			{
				subList.addItem(item.getType().name(), item.getAmount());
			}
			return subList;
		}
		
		public void setOutput(ArrayList<ItemStack> output)
		{
			this.output = output;
		}

		public double getUpkeepChance()
		{
			return upkeepChance;
		}

		public void setUpkeepChance(double upkeepChance)
		{
			this.upkeepChance = upkeepChance;
		}

		public double getMoneyRequirement()
		{
			return moneyRequirement;
		}

		public void setMoneyRequirement(double moneyRequirement)
		{
			this.moneyRequirement = moneyRequirement;
		}

		public double getMoneyOutput()
		{
			return moneyOutput;
		}

		public void setMoneyOutput(double moneyOutput)
		{
			this.moneyOutput = moneyOutput;
		}

		public double getExp()
		{
			return exp;
		}

		public void setExp(double exp)
		{
			this.exp = exp;
		}

		public List<String> getSuperRegions()
		{
			return superRegions;
		}

		public void setSuperRegions(List<String> superRegions)
		{
			this.superRegions = superRegions;
		}

		public int getBuildRadius()
		{
			return buildRadius;
		}

		public void setBuildRadius(int buildRadius)
		{
			this.buildRadius = buildRadius;
		}

		public int getRawBuildRadius()
		{
			return rawBuildRadius;
		}

		public void setRawBuildRadius(int rawBuildRadius)
		{
			this.rawBuildRadius = rawBuildRadius;
		}

		public int getRawRadius()
		{
			return rawRadius;
		}

		public void setRawRadius(int rawRadius)
		{
			this.rawRadius = rawRadius;
		}

		public String getDescription()
		{
			return description;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}

		public String getGroup()
		{
			return group;
		}

		public void setGroup(String group)
		{
			this.group = group;
		}

		public int getPowerDrain()
		{
			return powerDrain;
		}

		public void setPowerDrain(int powerDrain)
		{
			this.powerDrain = powerDrain;
		}

		public int getHousing()
		{
			return housing;
		}

		public void setHousing(int housing)
		{
			this.housing = housing;
		}

		public List<String> getBiome()
		{
			return biome;
		}

		public void setBiome(List<String> biome)
		{
			this.biome = biome;
		}
	
}
