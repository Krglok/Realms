package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.data.KnowledgeData;
import net.krglok.realms.science.RealmPermission;

import org.junit.Test;

public class RealmPermissionTest
{

	@Test
	public void testHasPermission()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testInitPermission()
	{
		RealmPermission realmPerm = new RealmPermission();
		KnowledgeData kData = new KnowledgeData();
		realmPerm.initKnowledgeNode(kData.getKnowledgeList().get("TECH_0"));
		System.out.println("PermissionList ["+realmPerm.size()+"]");
		System.out.print("  Key ");
		System.out.println(" | ");
		for (String perm : realmPerm)
		{
			System.out.print(perm);
			System.out.println(" | ");
			
		}
		fail("Not yet implemented");
	}

}
