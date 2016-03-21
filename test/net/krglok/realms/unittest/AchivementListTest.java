package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementList;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;

import org.junit.Test;

public class AchivementListTest
{

	private AchivementList initBookList(int wide)
	{
		AchivementList aList = new AchivementList();
		Achivement achiv;
		int i = 0;
		if (wide == 0) { wide = 1;}
		for (AchivementName aName : AchivementName.values())
		{
			achiv = new Achivement(AchivementType.BOOK, aName);
			i++;
			if (i == wide)
			{
				aList.put(achiv.getName(),achiv);
				i= 0;
			}
		}
		return aList;
	}

	private AchivementList initBuildList(int wide)
	{
		AchivementList aList = new AchivementList();
		Achivement achiv;
		int i = 0;
		if (wide == 0) { wide = 1;}
		for (AchivementName aName : AchivementName.values())
		{
			achiv = new Achivement(AchivementType.BUILD, aName);
			i++;
			if (i == wide)
			{
				aList.put(achiv.getName(),achiv);
				i= 0;
			}
		}
		return aList;
	}
	
	@Test
	public void testCheckList()
	{
		AchivementList aList = new AchivementList();
		String key = Achivement.makeName(AchivementType.BOOK, AchivementName.HOME);
		Achivement achiv = new Achivement(AchivementType.BOOK, AchivementName.HOME);
		aList.put(key,achiv);
		
		for (AchivementName aName : AchivementName.values())
		{
			achiv = new Achivement(AchivementType.BOOK, aName);
			aList.put(achiv.getName(),achiv);
		}

		int expected = 24;
		int actual = aList.size();
		if (expected != actual)
		{
			for (Achivement achive : aList.values())
			{
				System.out.println(achive.getName());
			}
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetSubListAchivementType()
	{
		AchivementList aList = new AchivementList();
		aList = initBookList(3);
		aList.add(initBuildList(2));
		aList.add(initBuildList(3));
		
		int expected = 2;
		int actual = aList.size();
		if (expected != actual)
		{
			System.out.println("Size "+"["+aList.size()+"]");
			for (String key: aList.sortItems())
			{
				
				System.out.println(aList.get(key).getName());
			}
		}
		
		AchivementList aListSub = aList.getSubList(AchivementName.BAKERY);
		System.out.println("Size "+"["+aListSub.size()+"]");
		for (String key: aListSub.sortItems())
		{
			
			System.out.println(aList.get(key).getName());
		}
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetSubListAchivementName()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testContains()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetAchivementNameAchivementType()
	{
		fail("Not yet implemented");
	}

}
