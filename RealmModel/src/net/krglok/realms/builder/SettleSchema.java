package net.krglok.realms.builder;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.manager.PlanMap;

/**
 * <pre>
 * the settle schema include a standard building schema for a settlement of a specific type.
 * the schema define the realtive position of the buildings in a settlement.
 * <0,0> is the center , the location of the settlement.
 * the positions are relative to center
 * </pre>
 * @author oduda
 *
 */
public class SettleSchema
{
	private SettleType settleType;
	private PlanMap settlePlan;
	private int radius;
	private int edge = radius * 2 -1;
	private BuildPositionList bPositions;

	public SettleSchema(SettleType settleType,  int radius)
	{
		this.settleType = settleType;
		this.radius = radius;
		this.settlePlan = new PlanMap(settleType.name(), radius);
		this.setbPositions(new BuildPositionList());
	}
	
	/**
	 * 
	 * @return
	 */
	public static SettleSchema initDefaultHamlet()
	{
		SettleSchema schema = new SettleSchema(SettleType.SETTLE_HAMLET, 40);
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HALL, new LocationData("", -10.0, 0.0, -10.0)));
		
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("", -16.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  7.0, 0.0, 7.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.HOME, new LocationData("",  16.0, 0.0, 7.0)));
	
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("", -7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WHEAT, new LocationData("",  7.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.WOODCUTTER, new LocationData("", -16.0, 0.0, 16.0)));
		schema.getbPositions().add(new BuildPosition(BuildPlanType.QUARRY, new LocationData("",  16.0, 0.0, 16.0)));
		
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
