package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import org.junit.Test;

public class SettlePlanTest
{
	private int setRegionLength(int radius)
	{
		return (2 * radius)-1;
	}
	
	
	private char[][] setDefaultHaupthaus(char[][] planMap)
	{
		int settleRadius = planMap.length / 2 +1;
		int regionRadius = 7;
		int offsetX = 2;
		int offsetY = 3;
//		System.out.println(SettleRadius);
		int posX = settleRadius + offsetX; // + setRegionLength(regionRadius);
		int posY = settleRadius + offsetY; // + setRegionLength(regionRadius);
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			planMap[posX+i][posY] = '+';
		}
		posX = settleRadius + offsetX ;
		posY = settleRadius + offsetY + setRegionLength(regionRadius)-1;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			planMap[posX+i][posY] = '+';
		}
		posX = settleRadius + offsetX ;
		posY = settleRadius + offsetY ;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			planMap[posX][posY+i] = '+';
		}
		posX = settleRadius + offsetX + setRegionLength(regionRadius)-1;
		posY = settleRadius + offsetY ;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			planMap[posX][posY+i] = '+';
		}
		posX = settleRadius + offsetX +regionRadius-1 ;
		posY = settleRadius + offsetY +regionRadius-1 ;
		planMap[posX][posY] = 'X';
		
		return planMap;
	}

	private char[] makeDefaultRow(int radius)
	{
		int len = setRegionLength(radius);
		char[] row = new char[len];
		for (int i = 0; i < row.length; i++)
		{
			row[i] = '.';
		}
		return row;
	}
	
	private char[][] makeDefaultMapPlan(int radius)
	{
		int line =  setRegionLength(radius);
		char[][] planMap = new char[line][line];
	
		for (int i = 0; i < planMap.length; i++)
		{
			if (i == radius)
			{
				char[] center = makeDefaultRow(radius);
				center[radius] = 'X';
				planMap[i] = center;
			} else
			{
				planMap[i] = makeDefaultRow(radius);
			}
		}
		
		return planMap;
	}
	
	@Test
	public void test()
	{
		int radius = 70; 
		
		char[] planRow = makeDefaultRow(radius);
		
		char[][] mapPlan = makeDefaultMapPlan(radius);

		setDefaultHaupthaus(mapPlan);
		
		for (int i = 0; i < mapPlan.length; i++)
		{
			System.out.println(mapPlan[i]);
		}
		
	}

}
