package net.krglok.realms.tool;

import java.awt.Point;
import java.io.File;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.maps.PlanMap;
import net.krglok.realms.unittest.DataTest;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.Test;

public class SettlePlanTest
{

	private static final char CORNER = '+';
	private static final char TOPLINE = '-';
	private static final char SIDELINE = '|';
	private static final char SPACE = ' ';
	private static final char CENTER = 'O';
	private static final char ROAD = '#';
	

	@SuppressWarnings("unused")
	private String setStoreString(byte[] planRow)
	{
		char[] charRow = new char[planRow.length];
		String sRow = "";
		for (int i = 0; i < charRow.length; i++)
		{
			charRow[i] = (char) planRow[i];
		}
		sRow = String.valueOf(charRow);
		return sRow;
	}
	

	
	private int setRegionLength(int radius)
	{
		return (2 * radius)-1;
	}
	
	private byte[][] setDefaultHaupthaus(byte[][] planMap, int radius)
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
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX+i][posY] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX ;
		posY = settleRadius + offsetY + setRegionLength(regionRadius)-1;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX+i][posY] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX ;
		posY = settleRadius + offsetY ;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX][posY+i] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX + setRegionLength(regionRadius)-1;
		posY = settleRadius + offsetY ;
		for (int i = 0; i < setRegionLength(regionRadius); i++)
		{
			if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
			{
				planMap[posX][posY+i] = (byte) 254;
			}
		}
		posX = settleRadius + offsetX +regionRadius-1 ;
		posY = settleRadius + offsetY +regionRadius-1 ;
		if ((posX > 0) && (posX < radius) && (posY > 0) && (posY < radius))
		{
			planMap[posX][posY] = (byte) 255;
		}
		
		return planMap;
	}

	/**
	 * Erstellt eine Stadtplanzeile mit einer Rasternummerierung 0-9
	 * jeweils von der Mitte nach links und rechts 
	 * 0 ist die Mitte
	 *  
	 * @param radius
	 * @return
	 */
	private char[] makeScaleCols(int radius)
	{
		int offset = radius;
		int len = setRegionLength(radius);
		char[] row = makeDefaultRow(radius);
		int index = 0; 
		char value = '0';
		for (int i = 0; i < radius; i++)
		{
			if (index > 9) { index = 0;}
			switch(index)
			{
			case 1 : value = '1';
			break;
			case 2 : value = '2';
			break;
			case 3 : value = '3';
			break;
			case 4 : value = '4';
			break;
			case 5 : value = '5';
			break;
			case 6 : value = '6';
			break;
			case 7 : value = '7';
			break;
			case 8 : value = '8';
			break;
			case 9 : value = '9';
			break;
			default : 
				value = '0';
			}
			
			row[radius+i-1] = value;
			row[radius-i-1] = value; 
			index++;
		}
		return row;
	}

	
	/**
	 * Erstellt eine Stadtplanzeile mite Begrenzung links und rechts
	 * die anderen Felder sind leer 
	 * @param radius
	 * @return
	 */
	private char[] makeDefaultRow(int radius)
	{
		int len = setRegionLength(radius);
		char[] row = new char[len];
		row[0] = SIDELINE;
		row[len-1] = SIDELINE;
		for (int i = 1; i < row.length-1; i++)
		{
			row[i] = SPACE;
		}
		return row;
	}

	/**
	 * Erstellt eine Stadtplanzeile als Markierungwaagerechte Begrentzung
	 * (oben und unten)
	 * @param radius
	 * @return
	 */
	private char[] makeTopRow(int radius)
	{
		int len = setRegionLength(radius);
		char[] row = new char[len];
		row[0] = CORNER;
		row[len-1] = CORNER;
		for (int i = 1; i < row.length-1; i++)
		{
			row[i] = TOPLINE;
		}
		return row;
	}

	/**
	 * Erstellt eine Stadtplanzeile mit Zentrum Markierung und ROAD Markierung
	 * waagerecht
	 * 
	 * @param radius
	 * @return
	 */
	private char[] makeCenterRow(int radius)
	{
		int len = setRegionLength(radius);
		char[] row =  makeDefaultRow(radius);
		for (int i = 1; i < row.length-1; i++)
		{
			row[i] = ROAD;
		}
		row[radius-1] = CENTER;
		return row;
	}

	/**
	 * Erstellt eine Stadtplanzeile mit zentraler Road Markierung
	 * in den zentralen Spalten
	 * @param radius
	 * @return
	 */
	private char[] makeRoadRow(int radius)
	{
		int len = setRegionLength(radius);
		char[] row =  makeDefaultRow(radius);
		row[radius] = ROAD;
		row[radius-1] = ROAD;
		row[radius-2] = ROAD;
		return row;
	}
	
	private char[] makeFullRoadRow(int radius)
	{
		int len = setRegionLength(radius);
		char[] row =  makeDefaultRow(radius);
		row[radius] = ROAD;
		row[radius-1] = ROAD;
		row[radius-2] = ROAD;
		for (int i = 1; i < row.length-1; i++)
		{
			row[i] = ROAD;
		}
		return row;
	}
	
	private byte[][] makeDefaultMapPlan(int radius)
	{
		int line =  setRegionLength(radius);
		byte[][] planMap = new byte[line][line];
	
		for (int i = 0; i < line; i++)
		{
		}
		
		return planMap;
	}
	
	
	/**
	 * Erstellt den Ausdruck eines leeren Stadplans 
	 * @param radius
	 * @param name
	 */
	private void printPlan(int radius, String name)
	{
		char[] row ;
		
		System.out.println(name);
		row = makeScaleCols(radius);
		System.out.println(row);
		row = makeTopRow(radius);
		System.out.println(row);
		for (int i = 1; i < radius-2; i++)
		{
			row = makeRoadRow(radius);
			System.out.print(row);
			System.out.println(String.valueOf(39-i));
		}
		row = makeFullRoadRow(radius);
		System.out.print(row);
		System.out.println(String.valueOf(1));
		row = makeCenterRow(radius);
		System.out.print(row);
		System.out.println(String.valueOf(0));
		row = makeFullRoadRow(radius);
		System.out.print(row);
		System.out.println(String.valueOf(1));
		for (int i = 2; i < radius-1; i++)
		{
			row = makeRoadRow(radius);
			System.out.print(row);
			System.out.println(String.valueOf(i));
		}
		row = makeTopRow(radius);
		System.out.println(row);
		row = makeScaleCols(radius);
		System.out.println(row);
	}
	
	/**
	 * Erstellt eine Liste der Settlements aus der Testdatenbank
	 * @param data
	 */
	private void printsettleList(DataTest data)
	{
		System.out.println("Settelements ["+data.getSettlements().size()+"]");
		for (Settlement settle : data.getSettlements().values())
		{
			System.out.println(settle.getId()+ ":"+settle.getName()+":"+ ":"+settle.getSettleType().toString());
		}
	}

	private void printBuildingList(DataTest data, Settlement settle)
	{
		System.out.println("======================================");
		System.out.println(settle.getId()+ ":"+settle.getName()+":"+ ":"+settle.getSettleType().toString());
		for (Building building : data.getBuildings().values())
		{
			if (building.getSettleId()==settle.getId())
			{
				System.out.println(building.getId()+ ":"+building.getHsRegionType()+":"+ ":"+building.getHsRegion());
			}
		}
	}
	
	private Point getPosition(Settlement settle, Building building )
	{
	  Point point = new Point();	
	  
	  int dx = 1;
	  int dy = 1;
	  
	  return point;
	}
	
	/**
	 * Erstellt einen Stadplan fuer ein Settlement
	 * Die Gebaeude weren als Grundriss (Quadratisch) dargestellt.
	 * Das Zentrum und das zentrale Strassenkreuz werden markiert.
	 */
	@Test
	public void test()
	{
		DataTest testData = new DataTest(); //logTest);
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Stronghold";
        File regionFolder = new File(path, "RegionConfig");
        if (!regionFolder.exists()) {
        	System.out.println("Folder not found !");
            return;
        }
		int radius = 40; 
		String name = "HAMLET";
		int settleId = 3;
		
		Settlement settle = testData.getSettlements().getSettlement(settleId);

		printsettleList(testData);		
		printBuildingList(testData, settle);
//		printPlan(radius, name);
		
	}		



}
