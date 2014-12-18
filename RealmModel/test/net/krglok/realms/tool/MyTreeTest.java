package net.krglok.realms.tool;

import static org.junit.Assert.*;

import net.krglok.realms.data.KnowledgeData;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.science.AchivementName;
import net.krglok.realms.science.AchivementType;
import net.krglok.realms.science.KnowledgeNode;
import net.krglok.realms.science.KnowledgeType;
import net.krglok.realms.tool.MyTree.Node;

import org.junit.Test;

public class MyTreeTest
{

	@Test
	public void testMyTree()
	{
		MyTree tree = new MyTree();
		
		boolean expected = true;
		boolean actual  = (tree != null ? true : false);
		
		assertEquals(expected, actual);

	}

	@Test
	public void testAddRoot()
	{
		MyTree tree = new MyTree();
		MyTree.Node<String> node = new MyTree.Node<String>("ROOT");
		tree.getRoots().add(node);
		boolean expected = true;
		boolean actual  = (tree != null ? true : false);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testAddChild()
	{
		MyTree tree = new MyTree();
		MyTree.Node<String> node = new MyTree.Node<String>("ROOT");
		tree.getRoots().add(node);
		/// implicite parent
		String key1 = "Child_1";
		node.addChild(new MyTree.Node<String>(key1));
		boolean expected = true;
		boolean actual  = (tree.getRoots().get(0).getChild(key1) != null ? true : false);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testAddData()
	{
		MyTree tree = new MyTree();
		MyTree.Node<String> node = new MyTree.Node<String>("ROOT");
		tree.getRoots().add(node);
		/// implicite parent
		String key1 = "Child_1";
		MyTree.Node<String> child = node.addChild(new MyTree.Node<String>(key1));
		Object data = new Object();
		if (tree.addData(node, data) == false)
		{
			System.out.println("OOPS ein node oder data ist NULL");
		}
		boolean expected = true;
		boolean actual  = (tree.getData(tree.getRoots().get(0)) != null ? true : false);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetData()
	{
		MyTree tree = new MyTree();
		MyTree.Node<String> root = new MyTree.Node<String>("ROOT");
		tree.getRoots().add(root);
		/// implicite parent
		String key1 = "Child_1";
		MyTree.Node<String> child = root.addChild(new MyTree.Node<String>(key1));
		Achivement data = new Achivement(AchivementType.BUILD,AchivementName.HOME);
		if (tree.addData(root, data) == false)
		{
			System.out.println("OOPS ein node oder data ist NULL");
		}
		boolean expected = true;
		boolean actual  = (tree.getData(tree.getRoots().get(0)) != null ? true : false);
		MyTree.Node<String> node =  tree.getRoots().get(0);
		Achivement achiv = (Achivement) tree.getData(node);
		System.out.println(achiv.getName());
		assertEquals(expected, actual);
	}


	@Test
	public void testGetDataList()
	{
		KnowledgeData knData = new KnowledgeData();
		MyTree tree = new MyTree();
		String rootName = "TECH_0"; 
		
		MyTree.Node<String> root = new MyTree.Node<String>(rootName);
		tree.getRoots().add(root);
		/// implicite parent
		String key1 = "TECH_1";
		MyTree.Node<String> child = root.addChild(new MyTree.Node<String>(key1));
		KnowledgeNode data = knData.getKnowledgeList().get(rootName);
		if (tree.addData(root, data) == false)
		{
			System.out.println("OOPS ein node oder data ist NULL");
		}
		
		int expected = 1;
		int actual  = tree.getDataList().size();
		assertEquals(expected, actual);
	}

}
