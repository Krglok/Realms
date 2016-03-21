package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import net.krglok.realms.data.KnowledgeData;
import net.krglok.realms.science.KnowledgeNode;

import org.junit.Test;

public class KnowledgeListTest
{

	@Test
	public void testGetIntKnowledgeType()
	{
		KnowledgeData kData = new KnowledgeData();

		int expected = 8;
		int actual =  kData.getKnowledgeList().size();
		if (expected != actual)
		{
			System.out.println("KnowledgeList ["+kData.getKnowledgeList().size()+"]");
			System.out.print("  Key ");
			System.out.println(" | ");
			for (KnowledgeNode kNode : kData.getKnowledgeList().values())
			{
				System.out.print(kNode.getAchievName());
				System.out.print(" | ");
				System.out.print("require : "+kNode.getRequirements().size());
				System.out.print(" | ");
				System.out.print("build perm : "+kNode.getBuildPermission().size());
				System.out.print(" | ");
				System.out.print("settl perm : "+kNode.getSettlePermission().size());
				System.out.print(" | ");
				System.out.print("");
				System.out.print(" | ");
				System.out.println("");
			}
		}
		assertEquals(expected, actual);
//		fail("Not yet implemented");
	}

}
