package net.krglok.realms.unittest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.krglok.realms.data.TestServer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.junit.Test;

public class TestServerTest
{

	@Test
	public void testGetRecipe()
	{
		
		TestServer  server = new TestServer();
		Map<Character,ItemStack> myRecipeMap = new HashMap<Character,ItemStack>();
		String itemRef = "WOOD";
		Material material = Material.getMaterial(itemRef);
		ItemStack itemStack = new ItemStack(material);
//		ShapedRecipe myRecipe = new ShapedRecipe(itemStack);
//		myRecipeMap = myRecipe.getIngredientMap();
		
		List<Recipe> recipes = Bukkit.getServer().getRecipesFor(itemStack);
		
		for (Recipe item : recipes)
		{
			System.out.println(item);
		}
	}

}
