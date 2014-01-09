package net.krglok.realms.core;

import java.util.HashMap;

public class BoardItemList extends HashMap<String,BoardItem>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoardItemList()
	{
		
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
		boardItem.setCycleCount(boardItem.getCycleCount()+1);
		boardItem.setCycleSum(boardItem.getCycleSum()+value);
		boardItem.setPeriodSum(boardItem.getPeriodSum()+value);
	}
	
	/**
	 * reset cycleCounter and sum
	 * @param name
	 */
	public void newCycle(String name)
	{
		BoardItem boardItem;
		if (this.containsKey(name))
		{
		boardItem = this.get(name) ;
		} else
		{
			boardItem = this.addBoardItem(name);
		}
		boardItem.setCycleCount(0);
		boardItem.setCycleSum(0.0);
	}

	/**
	 * 
	 * @param name
	 */
	public void addPeriod(String name)
	{
		BoardItem boardItem;
		if (this.containsKey(name))
		{
		boardItem = this.get(name) ;
		} else
		{
			boardItem = this.addBoardItem(name);
		}
		boardItem.setPeriodCount(boardItem.getPeriodCount()+1);
	}
	
	/**
	 * reset period Counter and Sum
	 * reset cycleCounter and sum
	 * @param name
	 */
	public void newPeriod(String name)
	{
		BoardItem boardItem;
		if (this.containsKey(name))
		{
		boardItem = this.get(name) ;
		} else
		{
			boardItem = this.addBoardItem(name);
		}
		boardItem.setPeriodCount(0);
		boardItem.setPeriodSum(0.0);
		boardItem.setCycleCount(0);
		boardItem.setCycleSum(0.0);
	}
}
