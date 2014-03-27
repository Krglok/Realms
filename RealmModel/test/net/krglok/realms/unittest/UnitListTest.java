/**
 * 
 */
package net.krglok.realms.unittest;

//<<<<<<< HEAD
//=======
import static org.junit.Assert.assertEquals;
import net.krglok.realms.unit.AbstractUnit;
import net.krglok.realms.unit.IUnit;
import net.krglok.realms.unit.UnitTypeList;
import net.krglok.realms.unit.UnitType;

//>>>>>>> origin/PHASE2
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
//<<<<<<< HEAD
//		UnitList unitList = new UnitList();
////		unitList.initUnitList();
//		int expected = 11;
//		int actual = unitList.size();
//
//		//isOutput = true;
//		if(isOutput)
//		{
//			System.out.println("==UnitList : "+unitList.size());
//			for(Unit unit : unitList.values())
//			{
//				System.out.println(unit.getUnitType().name()+" : "+unit.getUnitCount());
//			}
//			assertEquals("UnitList",expected,actual);
//		}
//=======
		UnitTypeList unitList = new UnitTypeList();
//		unitList.initUnitList();
		int expected = 11;
		int actual = unitList.size();

		//isOutput = true;
		if(isOutput)
		{
			System.out.println("==UnitList : "+unitList.size());
			for(IUnit unit : unitList)
			{
//				System.out.println(unit.getUnitType().name()+" : "+unit.getUnitCount());
			}
			assertEquals("UnitList",expected,actual);
		}
//>>>>>>> origin/PHASE2
	}
	
	@Test
	public void testAddUnitCount()
	{
//<<<<<<< HEAD
//		UnitList unitList = new UnitList();
////		unitList.initUnitList();
//=======
		UnitTypeList unitList = new UnitTypeList();
//		unitList.initUnitList();
//>>>>>>> origin/PHASE2
//		int expected = 5;
//		unitList.put(UnitType.UNIT_WORKER.name(),new Unit(UnitType.UNIT_WORKER));
//		unitList.addUnitCount(UnitType.UNIT_WORKER, expected);
//
//		isOutput = false;
//		if(isOutput)
//		{
//			System.out.println("==UnitList : "+unitList.size());
//			for(Unit unit : unitList.values())
//			{
//				System.out.println(unit.getUnitType().name()+" : "+unit.getUnitCount());
//			}
//		}
//		int actual =  unitList.getUnit(UnitType.UNIT_WORKER).getUnitCount();
//		assertEquals(expected,actual);
	}
	
}
