package net.krglok.realms.core;

import java.util.HashMap;


public class RouteOrderList extends HashMap<Integer,RouteOrder>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3548747592098053513L;
	
	public RouteOrderList()
	{
		
	}

	/**
	 * add routeOrder to list. set the next free id to routeOrder.
	 * 
	 * @param routeOrder
	 */
	public void addRouteOrder(RouteOrder routeOrder)
	{
		int key = RouteOrder.getCOUNTER();
		while (this.containsKey(key))
		{
			key++;
		}
		RouteOrder.initCOUNTER(key);
		routeOrder.setId(key);
		this.put(key, routeOrder);
	}

	public boolean contains(String itemRef)
	{
		for (RouteOrder rOrder : this.values())
		{
			if (rOrder.ItemRef().equalsIgnoreCase(itemRef))
			{
				return true;
			}
		}
		return false;
	}
	
	public RouteOrder getRouteOrder(String itemRef)
	{
		for (RouteOrder rOrder : this.values())
		{
			if (rOrder.ItemRef().equalsIgnoreCase(itemRef))
			{
				return rOrder;
			}
		}
		return null;
	}
	
}
