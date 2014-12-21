package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.core.NobleLevel;
import net.krglok.realms.core.Owner;
import net.krglok.realms.core.OwnerList;
import net.krglok.realms.data.KnowledgeData;
import net.krglok.realms.data.LogList;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementList;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;
import net.krglok.realms.science.RealmPermission;

import org.junit.Test;

public class OwnerListTest
{

	@Test
	public void testOwnerList()
	{
		OwnerList oList = new OwnerList();
		int expected = 0;
		int actual = oList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testAddOwnerOwner()
	{
		OwnerList oList = new OwnerList();
		Owner PCOwner = new Owner(0, NobleLevel.COMMONER, 0, "PC1", 0, false,"");
		oList.addOwner(PCOwner);
		int expected = 1;
		int actual = oList.size();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetOwner()
	{
		OwnerList oList = new OwnerList();
		Owner PCOwner = new Owner(0, NobleLevel.COMMONER, 0, "PC1", 0, false,"");
		oList.addOwner(PCOwner);
		int expected = 0;
		int actual = oList.getOwner("").getId();
		assertEquals(expected, actual);
	}

	@Test
	public void testSetRealm()
	{
		RealmPermission perms = new RealmPermission();
		KnowledgeData kData = new KnowledgeData();
		OwnerList oList = new OwnerList();
		Owner PCOwner = new Owner(0, NobleLevel.COMMONER, 0, "PC1", 0, false,"PC1");
		oList.addOwner(PCOwner);
		oList.getOwner("PC1").setKingdomId(99);
		Achivement achiv = new Achivement(AchivementType.BOOK, AchivementName.TECH0);
		oList.getOwner("PC1").getAchivList().add(achiv);
		achiv = new Achivement(AchivementType.BOOK, AchivementName.TECH1);
		oList.getOwner("PC1").getAchivList().add(achiv);

		perms.initPermission(PCOwner,kData.getKnowledgeList());
		
		System.out.println("OwnerListTest testSetRealm ");
		System.out.println("KnowledgeList ["+kData.getKnowledgeList().size()+"]");
		System.out.println("Owner Achivements ["+PCOwner.getAchivList().size()+"]");
		for (Achivement achive : PCOwner.getAchivList().values())
		{
			System.out.println(achiv.getAchiveName());
			
		}
		System.out.println("Owner permissions");
		for (String perm : perms)
		{
			System.out.println(perm);
		}
		int expected = 99;
		int actual = oList.getOwner("PC1").getKingdomId();
		assertEquals(expected, actual);
	}

}
