package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.tool.LogList;

import org.junit.Test;

public class LogListTest
{

	@Test
	public void testLogList()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
		LogList logTest = new LogList(path);
		
//		logTest.addText("TestUser","\"TESTDATA1\";\"TESTDATA2\"");
		logTest.addBank("TestUser", "add", 0, 1000 );
//		17.03.2014 15:56:56;PRODUCTION;CraftManager;WHEAT;1;56;WHEAT;16;426
		logTest.addProduction("WHEAT", 1, 56, "WHEAT", 16, "CraftManager", 100);
		logTest.run();
		
		fail("Not yet implemented");
	}

}
