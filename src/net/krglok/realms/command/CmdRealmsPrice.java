package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class CmdRealmsPrice extends aRealmsCommand
{
	private String itemRef;
	private double price;

	public CmdRealmsPrice()
	{
		super(RealmsCommandType.REALMS,RealmsSubCommandType.PRICE);
		description = new String[] {
				ChatColor.YELLOW+"/realms PRICE [ITEM] [PRICE],   ",
		    	"Set the price of <Item> in the central pricelist ",
		    	"Item must be uppercase, MATERIAL.NAME ",
		    	"price <0.00> ",
		    	" "
			};
			requiredArgs = 1;
			this.itemRef = "";
			this.price = 0.0;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
				itemRef = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, int value)
	{

	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{
		switch (index)
		{
		case 1 :
				price = value;
			break;
		default:
			break;
		}

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {String.class.getName(), double.class.getName() };
	}

	
    public List<Recipe> getRecipesFor(Realms plugin, ItemStack result) 
    {
    	if (result == null)
    	{
        	System.out.println("[REALMS] Result cannot be null");
    	}
        List<Recipe> results = new ArrayList<Recipe>();
        Iterator<Recipe> iter = plugin.getServer().recipeIterator();
        while (iter.hasNext()) 
        {
            Recipe recipe = iter.next();
            ItemStack stack = recipe.getResult();
            if (stack.getType() != result.getType()) 
            {
            	continue;
            }
            results.add(recipe);
            
        }
        return results;
    }

	private ArrayList<String> getRecipe(Realms plugin, String itemRef)
	{
		ArrayList<String> msg = new ArrayList<String>();
		ItemStack item = new ItemStack(Material.valueOf(itemRef));
		List <Recipe> recipes = getRecipesFor(plugin,item);
		
		String  line = "";
//        String line = itemRef+"|";
        for (Recipe recipe  : recipes) 
        {
        	if (recipe instanceof ShapedRecipe)
        	{
        		double costSum = 0.0;
	        	ShapedRecipe me = (ShapedRecipe) recipe;
	        	for (ItemStack ingred :  me.getIngredientMap().values())
	        	{
	        		if (ingred != null)
	        		{
	        			double cost = plugin.getData().getPriceList().getBasePrice(ingred.getType().name());
	        			costSum = costSum + cost;
	        			msg.add(ingred.getType().name()+":"+ConfigBasis.setStrright(cost, 6));
	        		}
	        	}
    			msg.add("Ingredient :"+ConfigBasis.setStrformat2(costSum, 8));
    			price = costSum * ConfigBasis.SETTLE_SELL_FACTOR;
    			msg.add(itemRef+":"+ConfigBasis.setStrformat2(price, 8));
        	}
        }
        return msg;
     }
	
	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();
		msg.add(""+ itemRef);
		itemRef = itemRef.toUpperCase();
		if (price == 0.0)
		{
			msg.addAll(getRecipe(plugin, itemRef));
		}
		if (price > 0.0)
		{
			String sPrice = ConfigBasis.setStrformat2(plugin.getData().getPriceList().getBasePrice(itemRef),7);
			msg.add("Old Price : "+sPrice);
			// Set new price
			plugin.getData().getPriceList().add(itemRef, price);
			plugin.getData().writePriceList();
			String newPrice = ConfigBasis.setStrformat2(price,7);
			msg.add("New Price : "+newPrice);
		} else
		{
			msg.add(ChatColor.RED+"No recipe found, give a price value ");
		}
		plugin.getMessageData().printPage(sender, msg, 1);
		itemRef = "";
		price = 0.0; 

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp())
		{
			if (ConfigBasis.isMaterial(itemRef) == false)
			{
				errorMsg.add("ItemName not a valid Material");
				errorMsg.add("Give a valid Material Name in uppercase");
				return false;
			}
//			if (price <= 0.0)
//			{
//				errorMsg.add("Price must greater than 0.0");
//				errorMsg.add("");
//			}
			
		} else
		{
			errorMsg.add("You are not a OP ");
			return false;
		}
		return true;
	}

}
