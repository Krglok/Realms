package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;
import net.krglok.realms.science.KnowledgeNode;

import org.junit.Test;

public class AchivementTest
{


	@Test
	public void testMakeName()
	{
		String name = Achivement.makeName(AchivementType.BOOK, AchivementName.BLACKSMITH);
		int i = 0;
		for (BuildPlanType bType : BuildPlanType.values())
		{
			if (AchivementName.contains(bType.name()))
			{
				i++;
				System.out.print(bType+" | ");
				System.out.print(bType.name()+" | "+i);
				System.out.println("");
			}
		}
		System.out.println("");
		int expected = 18;	// definierte required achivements
		int actual = i;
		assertEquals(expected, actual);
	}

	@Test
	public void testSplitName()
	{
		String name = Achivement.makeName(AchivementType.BOOK, AchivementName.BLACKSMITH);
		
		int i = 0;
		System.out.println("");
		System.out.println(name+" | "+Achivement.splitNameTyp(name)+":"+Achivement.splitNameName(name));
		int expected = 1;	// definierte required achivements
		int actual = i;
		assertEquals(expected, actual);
	}

}
