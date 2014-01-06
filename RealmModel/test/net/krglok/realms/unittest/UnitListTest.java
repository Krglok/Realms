/**
 * 
 */
package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.Unit;
import net.krglok.realms.core.UnitList;
import net.krglok.realms.core.UnitType;

import org.junit.Test;

/**
 * @author oduda
 *
 */
public class UnitListTest
{
	private Boolean isOutput = false; // set this to false to suppress println

	@Test
	public void testUnitList()
	{
		UnitList unitList = new UnitList();
//		unitList.initUnitList();
		int expected = 11;
		int actual = unitList.size();

		//isOutput = true;
		if(isOutput)
		{
			System.out.println("==UnitList : "+unitList.size());
			for(Unit unit : unitList.values())
			{
				System.out.println(unit.getUnitType().name()+" : "+unit.getUnitCount());
			}
			assertEquals("UnitList",expected,actual);
		}
	}
	
	@Test
	public void testAddUnitCount()
	{
		UnitList unitList = new UnitList();
//		unitList.initUnitList();
		int expected = 5;
		unitList.put(UnitType.UNIT_WORKER.name(),new Unit(UnitType.UNIT_WORKER));
		unitList.addUnitCount(UnitType.UNIT_WORKER, expected);

		isOutput = false;
		if(isOutput)
		{
			System.out.println("==UnitList : "+unitList.size());
			for(Unit unit : unitList.values())
			{
				System.out.println(unit.getUnitType().name()+" : "+unit.getUnitCount());
			}
		}
		int actual =  unitList.getUnit(UnitType.UNIT_WORKER).getUnitCount();
		assertEquals(expected,actual);
	}
	
}
