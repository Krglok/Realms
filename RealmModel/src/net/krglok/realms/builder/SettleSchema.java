package net.krglok.realms.builder;

import net.krglok.realms.core.PlanMap;
import net.krglok.realms.core.SettleType;

/**
 * the settle schema include a standard building schema for a settlement of a specific type
 * - Hamlet Schema
 *  
 * @author oduda
 *
 */
public class SettleSchema
{
	private SettleType settleType;
	private PlanMap settlePlan;
	private int radius;
	private int edge = radius * 2 -1;

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

	public SettleSchema(SettleType settleType,  int radius)
	{
		this.settleType = settleType;
		this.radius = radius;
		this.settlePlan = new PlanMap(settleType.name(), radius);
	}
	
}
