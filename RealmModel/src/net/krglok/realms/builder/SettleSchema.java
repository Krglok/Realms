package net.krglok.realms.builder;

import org.bukkit.block.Biome;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.maps.PlanMap;

/**
 * <pre>
 * the settle schema include a standard configuration schema for buildings in a settlement of a specific type.
 * the schema define the relative position of the buildings in a settlement.
 * the buildings describe as BuildPlanType
 * <0,0> is the center , the location of the settlement.
 * the positions are relative to center
 * </pre>
 * @author oduda
 *
 */
public class SettleSchema
{
	final static int MIN_DISTANCE = 2;
	
	private SettleType settleType;
	private PlanMap settlePlan;
	private int radius;
	private int edge = radius * 2 -1;
	private BuildPositionList bPositions;
	private boolean markUp = false;
	private boolean regiment = false;

	/**
	 * Create settleSchema  for Colonist
	 * @param settleType
	 * @param radius
	 * @param markup
	 */
	public SettleSchema(SettleType settleType,  int radius, boolean markup)
	{
		this.settleType = settleType;
		this.radius = radius;
		this.settlePlan = new PlanMap(settleType.name());
		this.setbPositions(new BuildPositionList());
		this.markUp = markup;
	}
	
	/**
	 * set the settle paramter for a default HAMLET
	 * 1 HALL 
	 * 4 HOME
	 * 2 WHEAT
	 * 1 WOODCUTTER
	 * 1 QUARRY
	 * @return  list BuildPlans with position for Hamlet
	 */
	public static SettleSchema initDefaultHamlet()
	{
		SettleSchema schema = new SettleSchema(SettleType.HAMLET, 40,true);
		schema.setRegiment(false); 
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HALL, new LocationData("", -10.0, 0.0, -10.0)));
		
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -16.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  16.0, 0.0, 7.0)));
	
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT,      new LocationData("", -7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WOODCUTTER, new LocationData("", -16.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT,      new LocationData("",  7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.QUARRY,     new LocationData("",  16.0, 0.0, 16.0)));

		schema.getbPositions().add(new BuildPosition(BuildPlanType.SHEPHERD,  new LocationData("", -7.0, 0.0, -22.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("", -16.0, 0.0, -22.0)));

		schema.getbPositions().add(new BuildPosition(BuildPlanType.BAKERY,      new LocationData("",  7.0, 0.0, -7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.CARPENTER,     new LocationData("",  16.0, 0.0, -7.0)));

		return schema;
	}
	
	public static SettleSchema initBiomeHamlet()
	{
		
		SettleSchema schema = new SettleSchema(SettleType.HAMLET, 40,true);
		schema.setRegiment(false); 
		schema.getbPositions().add(new BuildPosition(BuildPlanType.KNIFESHOP,     new LocationData("", 25.0, 0.0, -7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.COWSHED,    new LocationData("", -7.0, 0.0, 25.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("", -16.0, 0.0, 25.0)));
		return schema;
	}
	
	public static SettleSchema initBiomeHamlet(Biome biome)
	{
		SettleSchema schema = new SettleSchema(SettleType.HAMLET, 40,true);
		schema.setRegiment(false); 
		if (biome.name().contains("PLAIN"))
		{
			schema.getbPositions().add(new BuildPosition(BuildPlanType.KNIFESHOP,  new LocationData("", 25.0, 0.0, -7.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.COWSHED,    new LocationData("", -7.0, 0.0, 25.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("", -16.0, 0.0, 25.0)));
			return schema;
		}
		if (biome.name().contains("FOREST"))
		{
			schema.getbPositions().add(new BuildPosition(BuildPlanType.CABINETMAKER, new LocationData("", 25.0, 0.0, -7.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.CARPENTER,    new LocationData("", -7.0, 0.0, 25.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("", -16.0, 0.0, 25.0)));
			return schema;
		}
		if ((biome.name().contains("MOUNTAIN"))
			|| (biome.name().contains("EXTREME"))
			)
		{
			schema.getbPositions().add(new BuildPosition(BuildPlanType.PICKAXESHOP, new LocationData("", 25.0, 0.0, -7.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.STONEMINE,    new LocationData("", -23.0, 0.0, -9.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.SMELTER, new LocationData("", -25.0, 0.0, 7.0)));
			return schema;
		}
		if (biome.name().contains("SWAMP"))
		{
			schema.getbPositions().add(new BuildPosition(BuildPlanType.KNIFESHOP,  new LocationData("", 25.0, 0.0, -7.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.SPIDERSHED,    new LocationData("", -7.0, 0.0, 25.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.MUSHROOM, new LocationData("", -16.0, 0.0, 25.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.KITCHEN, new LocationData("", -25.0, 0.0, 7.0)));
			return schema;
		}
		if (biome.name().contains("DESERT"))
		{
			schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("", -16.0, 0.0, 25.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.SHEPHERD,     new LocationData("", 25.0, 0.0, -7.0)));
			schema.getbPositions().add(new BuildPosition(BuildPlanType.SHEPHERD,    new LocationData("", -7.0, 0.0, 25.0)));
		}
		if (biome.name().contains("OCEAN"))
		{
		}
		if (biome.name().contains("HELL"))
		{
		}
		schema.getbPositions().add(new BuildPosition(BuildPlanType.BAKERY,     new LocationData("", 25.0, 0.0, -7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT,    new LocationData("", -7.0, 0.0, 25.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("", -16.0, 0.0, 25.0)));
		return schema;
	}
	
	public static int getRange(int r1, int r2)
	{
		int range = 0;
		range = r1 + r2 -1 + MIN_DISTANCE;
		return range;
	}
	

	/**
	 * set the setlle parameter for a CAMP
	 * 1 building from type fort in the center
	 * @return
	 */
	public static SettleSchema initCamp()
	{
		SettleSchema schema = new SettleSchema(SettleType.CAMP, 10,false);
		schema.setRegiment(true); 
		schema.getbPositions().add(new BuildPosition(BuildPlanType.FORT, new LocationData("", 0.0, 0.0, 0.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.FORT, new LocationData("", 0.0, 0.0, 0.0)));
		return schema;
	}
	
	
	/**
	 * 
	 * @return  list BuildPlans with position for Hamlet
	 */
	public static SettleSchema initHellHamlet()
	{
		SettleSchema schema = new SettleSchema(SettleType.HAMLET, 40,true);
		schema.setRegiment(false); 
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HALL, new LocationData("", -10.0, 0.0, -10.0)));
		
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -16.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  16.0, 0.0, 7.0)));
	
		schema.getbPositions().add(new BuildPosition(BuildPlanType.MUSHROOM, new LocationData("", -7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.MUSHROOM, new LocationData("",  7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.DUSTCUTTER, new LocationData("", -16.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.NETHERQUARRY, new LocationData("",  16.0, 0.0, 16.0)));
		
		return schema;
	}

	public SettleType getSettleType()
	{
		return settleType;
	}

	public void setSettleType(SettleType settleType)
	{
		this.settleType = settleType;
	}

	public PlanMap getSettlePlan()
	{
		return settlePlan;
	}

	public void setSettlePlan(PlanMap settlePlan)
	{
		this.settlePlan = settlePlan;
	}

	public int getRadius()
	{
		return radius;
	}

	public int getEdge()
	{
		return edge;
	}

	/**
	 * @return the bPositions
	 */
	public BuildPositionList getbPositions()
	{
		return bPositions;
	}

	/**
	 * @param bPositions the bPositions to set
	 */
	public void setbPositions(BuildPositionList bPositions)
	{
		this.bPositions = bPositions;
	}

	/**
	 * @return the markUp
	 */
	public boolean isMarkUp()
	{
		return markUp;
	}

	/**
	 * @param markUp the markUp to set
	 */
	public void setMarkUp(boolean markUp)
	{
		this.markUp = markUp;
	}

	/**
	 * @return the regiment
	 */
	public boolean isRegiment()
	{
		return regiment;
	}

	/**
	 * @param regiment the regiment to set
	 */
	public void setRegiment(boolean regiment)
	{
		this.regiment = regiment;
	}

	
}
