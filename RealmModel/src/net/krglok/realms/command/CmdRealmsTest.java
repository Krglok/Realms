package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import multitallented.redcastlemedia.bukkit.herostronghold.region.SuperRegion;
import net.krglok.realms.Realms;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

public class CmdRealmsTest extends RealmsCommand
{
    private final String MARKER_SET = "markers";
	private int page; 
	private String itenName;
	
	public CmdRealmsTest( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST [ITEM]   ",
		    	" Show recipe test  ",
		    	" "
			};
			requiredArgs = 1;
			page = 0;
			itenName = "";
	}

	@Override
	public void setPara(int index, String value)
	{
		switch (index)
		{
		case 0 :
			itenName = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 1 :
			page = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {String.class.getName(), int.class.getName()  };
	}

    public List<Recipe> getRecipesFor(Realms plugin, ItemStack result) 
    {
    	if (result == null)
    	{
        	System.out.println("Result cannot be null");
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
    			costSum = costSum * ConfigBasis.SETTLE_SELL_FACTOR;
    			msg.add(itemRef+":"+ConfigBasis.setStrformat2(costSum, 8));
        	}
        }
        return msg;
     }



	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();

		msg.add(ChatColor.RED+"Realms Test ");

		msg.add(ChatColor.GREEN+"Recipe: "+itenName);
		msg.addAll(getRecipe(plugin,itenName));
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (isMaterial(itenName) == false)
		{
			errorMsg.add(ChatColor.RED+"No valid MaterialName ");
			return false;
		}
		
		return true;
//		if (sender instanceof Player)
//		{
//			return true;
//		}
//		errorMsg.add("Not a console command !");
//		errorMsg.add("The command must send by a Player !");
//		return false;
	}

}
