import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Cake;


public class MyList extends HashMap<String,Integer>
{
	public Bukkit myServer;
	private Cake cake;
	private ItemStack itemStack;
	private Inventory myInventory;
	private ShapedRecipe myRecipe;
	private Map<Character,ItemStack> myRecipeMap;
	
	public MyList()
	{
		cake = new Cake();
		itemStack = new ItemStack(Material.WORKBENCH);
		myRecipe = new ShapedRecipe(itemStack);
		myRecipe.getIngredientMap();
	}
	
	
}
