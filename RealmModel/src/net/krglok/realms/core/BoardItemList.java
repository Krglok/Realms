package net.krglok.realms.core;

import java.io.Serializable;
import java.util.HashMap;

/**
 * hold multible rows of a statistik, each row is a different item
 * @author Windu
 *
 */
public class BoardItemList extends HashMap<String,BoardItem>  
{

	private static final long serialVersionUID = 1L;
	
	private int cycleCount;
	private int periodCount;

	public BoardItemList()
	{
		cycleCount	= 0;
		periodCount = 0;
		
	}
	
	public int getCycleCount()
	{
		return cycleCount;
	}

	public void setCycleCount(int cycleCount)
	{
		this.cycleCount = cycleCount;
	}
	
	public int getPeriodCount()
	{
		return periodCount;
	}

	public void setPeriodCount(int periodCount)
	{
		this.periodCount = periodCount;
	}
	
	public boolean addBoardItem(BoardItem boardItem)
	{
		if (this.containsKey(boardItem))
		{
			return false;
		}
		String key = boardItem.getName();
		if (key == "")
		{
			return false;
		}
		this.put(key, boardItem);
		return true;
	}

	public BoardItem addBoardItem(String name)
	{
		if (name == "" )
		{
			return null;
		}
		BoardItem boardItem = new BoardItem(name);
		addBoardItem(boardItem);
		return boardItem;
	}
	
	public void resetLastAll()
	{
		for (BoardItem boardItem : this.values())
		{
			resetValue(boardItem.getName());
		}
	}

	public void resetValue(String name)
	{
		BoardItem boardItem;
		if (this.containsKey(name))
		{
		boardItem = this.get(name) ;
		} else
		{
			boardItem = this.addBoardItem(name);
		}
		boardItem.setLastValue(0.0);
	}
	
	
	/**
	 * add vallue to Last , cycleSum and periodSum
	 * @param name
	 * @param value
	 */
	public void addCycleValue(String name, double value)
	{
		BoardItem boardItem;
		if (this.containsKey(name))
		{
			boardItem = this.get(name) ;
		} else
		{
			boardItem = this.addBoardItem(name);
		}
		boardItem.setLastValue(value);
		boardItem.setCycleSum(boardItem.getCycleSum()+value);
		boardItem.setPeriodSum(boardItem.getPeriodSum()+value);
	}

	public void addCycle()
	{
		this.cycleCount++;
	}
	
	/**
	 * reset cycleCounter and sum
	 * @param name
	 */
	public void newCycle(String name)
	{
		this.cycleCount = 0;
		for (BoardItem b : this.values())
		{
			b.setCycleSum(0.0);
		}
	}

	/**
	 * 
	 * @param name
	 */
	public void addPeriod(String name)
	{
		periodCount++;
	}
	
	/**
	 * reset period Counter and Sum
	 * reset cycleCounter and sum
	 * @param name
	 */
	public void newPeriod(String name)
	{
		for (BoardItem b : this.values())
		{
			b.setPeriodSum(0.0);
		}
	}
}
