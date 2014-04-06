package net.krglok.realms.builder;

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

	public SettleSchema(SettleType settleType,  int radius)
	{
		this.settleType = settleType;
		this.radius = radius;
		this.settlePlan = new PlanMap(settleType.name());
		this.setbPositions(new BuildPositionList());
	}
	
	/**
	 * 
	 * @return  list BuildPlans with position for Hamlet
	 */
	public static SettleSchema initDefaultHamlet()
	{
		SettleSchema schema = new SettleSchema(SettleType.HAMLET, 40);
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HALL, new LocationData("", -10.0, 0.0, -10.0)));
		
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -16.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  16.0, 0.0, 7.0)));
	
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT,      new LocationData("", -7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WOODCUTTER, new LocationData("", -16.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT,      new LocationData("",  7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.QUARRY,     new LocationData("",  16.0, 0.0, 16.0)));
		
		return schema;
	}
	
	public static SettleSchema initBasicHamlet()
	{
		SettleSchema schema = new SettleSchema(SettleType.HAMLET, 40);
		schema.getbPositions().add(new BuildPosition(BuildPlanType.SHEPHERD,     new LocationData("", 16.0, 0.0, 16.0)));
		
		schema.getbPositions().add(new BuildPosition(BuildPlanType.CARPENTER,    new LocationData("", -25.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.CABINETMAKER, new LocationData("", -25.0, 0.0, 16.0)));
		return schema;
	}
	
	public static int getRange(int r1, int r2)
	{
		int range = 0;
		range = r1 + r2 -1 + MIN_DISTANCE;
		return range;
	}
	

	/**
	 * 
	 * @return  list BuildPlans with position for Hamlet
	 */
	public static SettleSchema initHellHamlet()
	{
		SettleSchema schema = new SettleSchema(SettleType.HAMLET, 40);
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

	
}
