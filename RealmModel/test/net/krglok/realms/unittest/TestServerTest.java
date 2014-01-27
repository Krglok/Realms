package net.krglok.realms.unittest;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.junit.Test;

public class TestServerTest
{

	@Test
	public void testGetRecipe()
	{
		
//		ServerTest  server = new ServerTest();
//		Map<Character,ItemStack> myRecipeMap = new HashMap<Character,ItemStack>();
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
