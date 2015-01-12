package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.data.DataStorage;

import org.junit.Test;

public class NpcProductionTest
{

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 

	DataStorage data = new DataStorage(dataFolder);

	@Test
	public void test()
	{
		data.initData();

		fail("Not yet implemented");
	}

}
