package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.krglok.realms.builder.BuildPlanType;

import org.junit.Test;

public class BuildPlanTypeTest
{

	@Test
	public void testGetBuildPlanType()
	{
	
		BuildPlanType expected = BuildPlanType.HOME;
		BuildPlanType actual   = BuildPlanType.getBuildPlanType("HOME");

		if (expected != actual)
		{
			System.out.println("BuildPlanType unsorted");
			for (BuildPlanType bType : BuildPlanType.values())
			{
				System.out.println(":"+bType);
			}
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetBuildPlanOrder()
	{
		ArrayList<BuildPlanType> bTypes ;
		bTypes = BuildPlanType.getBuildPlanOrder();
		BuildPlanType expected = BuildPlanType.HOME;
		BuildPlanType actual   = bTypes.get(2);
		
		if (expected != actual)
		{
			System.out.println("BuildPlanType unsorted");
			for (BuildPlanType bType : bTypes)
			{
				System.out.println(":"+bType);
			}
		}
		assertEquals(expected, actual);
	}

	@Test
	public void testGetBuildPlanGroup()
	{
		ArrayList<BuildPlanType> bTypes ;
		bTypes = BuildPlanType.getBuildPlanGroup(200);
		BuildPlanType expected = BuildPlanType.PIGPEN;
		BuildPlanType actual   = bTypes.get(bTypes.size()-1);
		
		if (expected != actual)
		{
			System.out.println("BuildPlanType unsorted");
			for (BuildPlanType bType : bTypes)
			{
				System.out.println(":"+bType);
			}
		}
		assertEquals(expected, actual);
	}

}
