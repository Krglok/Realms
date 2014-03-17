package net.krglok.realms.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogListTest
{

	@Test
	public void testLogList()
	{
		String path = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms";
		LogList logTest = new LogList(path);
		
		logTest.addText("TestUser","\"TESTDATA1\";\"TESTDATA2\"");
		logTest.addBank("TestUser", "add", 0, 1000 );

		logTest.run();
		
		fail("Not yet implemented");
	}

}
