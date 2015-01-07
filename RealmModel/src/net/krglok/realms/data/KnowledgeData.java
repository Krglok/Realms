package net.krglok.realms.data;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.KnowledgeList;
import net.krglok.realms.science.KnowledgeNode;
import net.krglok.realms.science.KnowledgeType;

public class KnowledgeData
{
	private static boolean isInit = false;
	private KnowledgeList knList;
	
	public KnowledgeData()
	{
		knList = new KnowledgeList();
		initKnowledgeData();
	}

	public static boolean isInit()
	{
		return isInit;
	}

	public KnowledgeList getKnowledgeList()
	{
		return knList;
	}

	private void initKnowledgeData()
	{
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 0), addTech0());
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 1), addTech1());
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 2), addTech2());
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 3), addTech3());
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 4), addTech4());
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 5), addTech5());
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 6), addTech6());
		knList.put(KnowledgeNode.makeTechId(KnowledgeType.TECH.name(), 7), addTech7());
		
		isInit = true;
	}
	
	private KnowledgeNode addTech0()
	{
		KnowledgeNode kNode = new KnowledgeNode(0, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH0);
		kNode.addBuildPlan(BuildPlanType.HOME);
		kNode.addBuildPlan(BuildPlanType.WOODCUTTER);
		kNode.addBuildPlan(BuildPlanType.QUARRY);
		kNode.addBuildPlan(BuildPlanType.WHEAT);
		kNode.addBuildPlan(BuildPlanType.SHEPHERD);

		return kNode;
	}

	private KnowledgeNode addTech1()
	{
		KnowledgeNode kNode = new KnowledgeNode(1, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH1);
		kNode.adRequirement(AchivementName.HOME);
		kNode.adRequirement(AchivementName.WOODCUTTER);
		kNode.adRequirement(AchivementName.QUARRY);
		kNode.adRequirement(AchivementName.WHEAT);
		kNode.adRequirement(AchivementName.SHEPHERD);

		kNode.addBuildPlan(BuildPlanType.HALL);
		kNode.addBuildPlan(BuildPlanType.CARPENTER);
		kNode.addBuildPlan(BuildPlanType.HUNTER);
		kNode.addBuildPlan(BuildPlanType.TAMER);
		kNode.addBuildPlan(BuildPlanType.FISHERHOOD);
		kNode.addBuildPlan(BuildPlanType.GATE);
		
		kNode.addBuildPlan(BuildPlanType.KEEP);

		kNode.addSettleType(SettleType.HAMLET);

		return kNode;
	}

	private KnowledgeNode addTech2()
	{
		KnowledgeNode kNode = new KnowledgeNode(2, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH2);
		kNode.adRequirement(AchivementName.HALL);
		kNode.adRequirement(AchivementName.CARPENTER);

		kNode.addBuildPlan(BuildPlanType.WAREHOUSE);
		kNode.addBuildPlan(BuildPlanType.BAKERY);
		kNode.addBuildPlan(BuildPlanType.CABINETMAKER);

		return kNode;
	}
	
	private KnowledgeNode addTech3()
	{
		KnowledgeNode kNode = new KnowledgeNode(3, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH3);
		kNode.adRequirement(AchivementName.CABINETMAKER);
		kNode.adRequirement(AchivementName.BAKERY);

		kNode.addBuildPlan(BuildPlanType.AXESHOP);
		kNode.addBuildPlan(BuildPlanType.PICKAXESHOP);
		kNode.addBuildPlan(BuildPlanType.HOESHOP);
		kNode.addBuildPlan(BuildPlanType.SPADESHOP);
		kNode.addBuildPlan(BuildPlanType.KNIFESHOP);

		return kNode;
	}

	private KnowledgeNode addTech4()
	{
		KnowledgeNode kNode = new KnowledgeNode(4, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH4);
		kNode.adRequirement(AchivementName.HOESHOP);
		kNode.adRequirement(AchivementName.KNIFESHOP);

		kNode.addBuildPlan(BuildPlanType.HOUSE);
		kNode.addBuildPlan(BuildPlanType.WORKSHOP);
		kNode.addBuildPlan(BuildPlanType.CHARBURNER);
		kNode.addBuildPlan(BuildPlanType.FARMHOUSE);
		kNode.addBuildPlan(BuildPlanType.STONEMINE);
		kNode.addBuildPlan(BuildPlanType.COWSHED);
		kNode.addBuildPlan(BuildPlanType.SPIDERSHED);
		kNode.addBuildPlan(BuildPlanType.CHICKENHOUSE);
		kNode.addBuildPlan(BuildPlanType.TAVERNE);

		kNode.addBuildPlan(BuildPlanType.CASTLE);

		kNode.addSettleType(SettleType.TOWN);

		return kNode;
	}

	private KnowledgeNode addTech5()
	{
		KnowledgeNode kNode = new KnowledgeNode(5, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH5);
		kNode.adRequirement(AchivementName.WORKSHOP);
		kNode.adRequirement(AchivementName.GUARDHOUSE);
		kNode.adRequirement(AchivementName.TANNERY);
		kNode.adRequirement(AchivementName.BLACKSMITH);

		kNode.addBuildPlan(BuildPlanType.TOWNHALL);
		kNode.addBuildPlan(BuildPlanType.MANSION);
		kNode.addBuildPlan(BuildPlanType.TANNERY);
		kNode.addBuildPlan(BuildPlanType.BOWMAKER);
		kNode.addBuildPlan(BuildPlanType.BLACKSMITH);
		kNode.addBuildPlan(BuildPlanType.FLETCHER);
		kNode.addBuildPlan(BuildPlanType.FARM);
		kNode.addBuildPlan(BuildPlanType.SMELTER);
		kNode.addBuildPlan(BuildPlanType.ARCHERY);
		kNode.addBuildPlan(BuildPlanType.GUARDHOUSE);
		kNode.addBuildPlan(BuildPlanType.WATCHTOWER);

		kNode.addBuildPlan(BuildPlanType.BIBLIOTHEK);
		
		kNode.addBuildPlan(BuildPlanType.STRONGHOLD);

		kNode.addSettleType(SettleType.CITY);

		return kNode;
	}

	private KnowledgeNode addTech6()
	{
		KnowledgeNode kNode = new KnowledgeNode(6, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH6);
		kNode.adRequirement(AchivementName.TOWNHALL);
		kNode.adRequirement(AchivementName.SMELTER);

		kNode.addBuildPlan(BuildPlanType.PIGPEN);
		kNode.addBuildPlan(BuildPlanType.WEAPONSMIH);
		kNode.addBuildPlan(BuildPlanType.ARMOURER);
		kNode.addBuildPlan(BuildPlanType.CHAINMAKER);
		kNode.addBuildPlan(BuildPlanType.HORSEBARN);
		kNode.addBuildPlan(BuildPlanType.BARRACK);
		kNode.addBuildPlan(BuildPlanType.CASERN);
		kNode.addBuildPlan(BuildPlanType.TOWER);
		kNode.addBuildPlan(BuildPlanType.DEFENSETOWER);

		return kNode;
	}

	private KnowledgeNode addTech7()
	{
		KnowledgeNode kNode = new KnowledgeNode(7, KnowledgeType.TECH);
		kNode.setAchievName(AchivementName.TECH7);
		kNode.adRequirement(AchivementName.BARRACK);
		kNode.adRequirement(AchivementName.TOWER);

		kNode.addBuildPlan(BuildPlanType.HEADQUARTER);
		kNode.addBuildPlan(BuildPlanType.GOLDSMELTER);
		kNode.addBuildPlan(BuildPlanType.GARRISON);
		kNode.addBuildPlan(BuildPlanType.LIBRARY);
		
		kNode.addBuildPlan(BuildPlanType.PALACE);

		kNode.addSettleType(SettleType.METROPOLIS);
		
		return kNode;
	}

	private KnowledgeNode addNoble0()
	{
		KnowledgeNode kNode = new KnowledgeNode(0, KnowledgeType.NOBLE);

		return kNode;
	}

}
