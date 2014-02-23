package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.OwnerList;
import net.krglok.realms.data.ConfigTest;
import net.krglok.realms.data.DataTest;
import net.krglok.realms.data.MessageTest;
import net.krglok.realms.data.ServerTest;
import net.krglok.realms.manager.SettleManager;
import net.krglok.realms.model.RealmModel;

import org.junit.Test;

public class SettleManagerTest
{

	@Test
	public void test()
	{
		ServerTest server = new ServerTest();
		DataTest     data = new DataTest();
		ConfigTest config = new ConfigTest();
		MessageTest   msg = new MessageTest();
		
		RealmModel rModel = new RealmModel(0, 0, server, config, data, msg);
		
		SettleManager sMgr = new SettleManager(rModel);
		
	}

}
