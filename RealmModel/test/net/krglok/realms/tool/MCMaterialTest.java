package net.krglok.realms.tool;

import static org.junit.Assert.*;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Test;

public class MCMaterialTest
{

	
	
	@Test
	public void test()
	{
		for (Material mat : Material.values())
		{
			if (mat.isBlock() == false)
			{
				System.out.print(mat.name());
				ItemStack item = new ItemStack(mat, 1);
				System.out.println("");
			}
		}
		
		fail("Not yet implemented");
	}

}
