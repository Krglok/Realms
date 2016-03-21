package net.krglok.realms.data;

import java.util.HashMap;

import org.bukkit.Material;

import net.krglok.realms.Common.ItemList;

public class RecipeData
{
	// ItemList = HashMap<String, Integer> 
	HashMap<String,ItemList> recipeList = new HashMap<String, ItemList>();

	public RecipeData()
	{

	}

	
	public ItemList getFoodRecipe(String itemRef)
	{
		ItemList itemList = new ItemList();
		switch (Material.getMaterial(itemRef))
		{
		case BREAD :
			itemList.addItem(Material.BREAD.name(), 6);
			itemList.addItem(Material.WHEAT.name(), 18);
			break;
		case COOKED_BEEF :
			itemList.addItem(Material.COOKED_BEEF.name(), 6);
			itemList.addItem(Material.RAW_BEEF.name(), 6);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case GRILLED_PORK :
			itemList.addItem(Material.GRILLED_PORK.name(), 6);
			itemList.addItem(Material.PORK.name(), 6);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case COOKED_CHICKEN :
			itemList.addItem(Material.COOKED_CHICKEN.name(), 6);
			itemList.addItem(Material.RAW_CHICKEN.name(), 6);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case COOKED_FISH :
			itemList.addItem(Material.COOKED_FISH.name(), 6);
			itemList.addItem(Material.RAW_FISH.name(), 6);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case CAKE_BLOCK :
			itemList.addItem(Material.CAKE_BLOCK.name(), 1);
			itemList.addItem(Material.MILK_BUCKET.name(), 3);
			itemList.addItem(Material.SUGAR.name(), 2);
			itemList.addItem(Material.EGG.name(), 1);
			itemList.addItem(Material.WHEAT.name(), 3);
			break;
		case CAKE :
			itemList.addItem(Material.CAKE.name(), 8);
			itemList.addItem(Material.COCOA.name(), 1);
			itemList.addItem(Material.WHEAT.name(), 2);
			break;
			
		default :
//			itemList = getToolRecipe(itemRef);
			break;
		}
		return itemList;
	}
	
	public ItemList getRecipe(String itemRef)
	{
		ItemList itemList = new ItemList();
		switch (Material.getMaterial(itemRef))
		{
		case WOOD :
			itemList.addItem(Material.WOOD.name(), 4);
			itemList.addItem(Material.LOG.name(), 1);
			break;
		case STICK :
			itemList.addItem(Material.STICK.name(), 4);
			itemList.addItem(Material.WOOD.name(), 2);
			break;
		case STONE :
			itemList.addItem(Material.STONE.name(), 6);
			itemList.addItem(Material.COBBLESTONE.name(), 6);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case IRON_INGOT:
			itemList.addItem(Material.IRON_INGOT.name(), 6);
			itemList.addItem(Material.IRON_ORE.name(), 6);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case GOLD_INGOT:
			itemList.addItem(Material.GOLD_INGOT.name(), 4);
			itemList.addItem(Material.GOLD_ORE.name(), 4);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case FENCE:
			itemList.addItem(Material.FENCE.name(), 2);
			itemList.addItem(Material.STICK.name(), 6);
			break;
		case FENCE_GATE:
			itemList.addItem(Material.FENCE.name(), 1);
			itemList.addItem(Material.STICK.name(), 4);
			itemList.addItem(Material.WOOD.name(), 2);
			break;
		case IRON_BARDING:
			itemList.addItem(Material.IRON_BARDING.name(), 16);
			itemList.addItem(Material.IRON_INGOT.name(), 6);
			itemList.addItem(Material.IRON_INGOT.name(), 6);
			break;
		case GLASS:
			itemList.addItem(Material.GLASS.name(), 5);
			itemList.addItem(Material.SAND.name(), 5);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case GLASS_BOTTLE:
			itemList.addItem(Material.GLASS_BOTTLE.name(), 3);
			itemList.addItem(Material.GLASS.name(), 3);
			break;
		case WOOD_DOOR:
			itemList.addItem(Material.WOOD_DOOR.name(), 1);
			itemList.addItem(Material.WOOD.name(), 6);
			break;
		case IRON_DOOR:
			itemList.addItem(Material.IRON_DOOR.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 6);
			break;
		case BED:
			itemList.addItem(Material.BED.name(), 1);
			itemList.addItem(Material.WOOD.name(), 3);
			itemList.addItem(Material.WOOL.name(), 3);
			break;
		case SIGN:
			itemList.addItem(Material.SIGN.name(), 1);
			itemList.addItem(Material.WOOD.name(), 6);
			itemList.addItem(Material.STICK.name(), 1);
			break;
		case WOOD_STAIRS:
			itemList.addItem(Material.WOOD_STAIRS.name(), 4);
			itemList.addItem(Material.WOOD.name(), 6);
			break;
		case WOOD_STEP:
			itemList.addItem(Material.WOOD_STEP.name(), 6);
			itemList.addItem(Material.WOOD.name(), 3);
			break;
		case COBBLESTONE_STAIRS:
			itemList.addItem(Material.COBBLESTONE_STAIRS.name(), 4);
			itemList.addItem(Material.COBBLESTONE.name(), 6);
			break;
		case STEP:
			itemList.addItem(Material.STEP.name(), 6);
			itemList.addItem(Material.COBBLESTONE.name(), 3);
			break;
		case STONE_AXE:
			itemList.addItem(Material.STONE_AXE.name(), 3);
			itemList.addItem(Material.COBBLESTONE.name(), 9);
			itemList.addItem(Material.STICK.name(), 6);
			break;
		case STONE_PICKAXE:
			itemList.addItem(Material.STONE_PICKAXE.name(), 3);
			itemList.addItem(Material.COBBLESTONE.name(), 9);
			itemList.addItem(Material.STICK.name(), 6);
			break;
		case STONE_HOE:
			itemList.addItem(Material.STONE_HOE.name(), 3);
			itemList.addItem(Material.COBBLESTONE.name(), 6);
			itemList.addItem(Material.STICK.name(), 6);
			break;
		case STONE_SPADE:
			itemList.addItem(Material.STONE_SPADE.name(), 3);
			itemList.addItem(Material.COBBLESTONE.name(), 3);
			itemList.addItem(Material.STICK.name(), 6);
			break;
		case COOKED_BEEF :
			itemList.addItem(Material.COOKED_BEEF.name(), 5);
			itemList.addItem(Material.RAW_BEEF.name(), 5);
			itemList.addItem(Material.LOG.name(), 1);
			break;
		case COOKED_FISH :
			itemList.addItem(Material.COOKED_FISH.name(), 5);
			itemList.addItem(Material.RAW_FISH.name(), 5);
			itemList.addItem(Material.LOG.name(), 1);
			break;
		case COOKED_CHICKEN :
			itemList.addItem(Material.COOKED_CHICKEN.name(), 5);
			itemList.addItem(Material.RAW_CHICKEN.name(), 5);
			itemList.addItem(Material.LOG.name(), 1);
			break;
		case GRILLED_PORK :
			itemList.addItem(Material.GRILLED_PORK.name(), 5);
			itemList.addItem(Material.PORK.name(), 5);
			itemList.addItem(Material.LOG.name(), 1);
			break;
		default :
			//itemList = getFoodRecipe(itemRef);
			break;
		}
		return itemList;
	}
	

	public ItemList getToolRecipe(String itemRef)
	{
		ItemList itemList = new ItemList();
		switch (Material.getMaterial(itemRef))
		{
		case ARROW :
			itemList.addItem(Material.ARROW.name(), 4);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.FEATHER.name(), 1);
			itemList.addItem(Material.FLINT.name(), 1);
			break;
		case STONE_AXE :
			itemList.addItem(Material.STONE_AXE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.COBBLESTONE.name(), 3);
			break;
		case STONE_PICKAXE :
			itemList.addItem(Material.STONE_PICKAXE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.COBBLESTONE.name(), 3);
			break;
		case WOOD_AXE :
			itemList.addItem(Material.WOOD_AXE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.WOOD.name(), 3);
			break;
		case WOOD_PICKAXE :
			itemList.addItem(Material.WOOD_PICKAXE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.WOOD.name(), 3);
			break;
		case IRON_AXE :
			itemList.addItem(Material.IRON_AXE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 3);
			break;
		case IRON_PICKAXE :
			itemList.addItem(Material.IRON_PICKAXE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 3);
			break;
		case TORCH:
			itemList.addItem(Material.TORCH.name(), 4);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.COAL.name(), 1);
			break;
		case REDSTONE_TORCH_OFF:
			itemList.addItem(Material.REDSTONE_TORCH_OFF.name(), 4);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.REDSTONE.name(), 1);
			break;
		case WOOD_SPADE :
			itemList.addItem(Material.WOOD_SPADE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.WOOD.name(), 1);
			break;
		case WOOD_HOE :
			itemList.addItem(Material.WOOD_HOE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.WOOD.name(), 2);
			break;
		case FENCE:
			itemList.addItem(Material.FENCE.name(), 2);
			itemList.addItem(Material.STICK.name(), 6);
			break;
		case BED :
			itemList.addItem(Material.BED.name(), 1);
			itemList.addItem(Material.WOOL.name(), 3);
			itemList.addItem(Material.WOOD.name(), 3);
			break;
		case PAPER:
			itemList.addItem(Material.PAPER.name(), 3);
			itemList.addItem(Material.SUGAR_CANE.name(), 3);
			break;
		case BOOKSHELF :
			itemList.addItem(Material.BOOKSHELF.name(), 1);
			itemList.addItem(Material.BOOK.name(), 3);
			itemList.addItem(Material.WOOD.name(), 6);
			break;
		case BOOK :
			itemList.addItem(Material.BOOK.name(), 1);
			itemList.addItem(Material.PAPER.name(), 3);
			itemList.addItem(Material.LEATHER.name(), 1);
			break;
		case BOOK_AND_QUILL :
			itemList.addItem(Material.BOOK_AND_QUILL.name(), 1);
			itemList.addItem(Material.BOOK.name(), 1);
			itemList.addItem(Material.INK_SACK.name(), 1);
			itemList.addItem(Material.FEATHER.name(), 1);
			break;
		case CHEST :
			itemList.addItem(Material.CHEST.name(), 1);
			itemList.addItem(Material.WOOD.name(), 8);
			break;
		case WORKBENCH :
			itemList.addItem(Material.WORKBENCH.name(), 1);
			itemList.addItem(Material.WOOD.name(), 4);
			break;
		case FURNACE :
			itemList.addItem(Material.FURNACE.name(), 1);
			itemList.addItem(Material.COBBLESTONE.name(), 8);
			break;
		case STONE_SPADE :
			itemList.addItem(Material.STONE_SPADE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.COBBLESTONE.name(), 1);
			break;
		case IRON_SPADE :
			itemList.addItem(Material.IRON_SPADE.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 1);
			break;

		default :
//			itemList = getWeaponRecipe(itemRef);
			break;
		}
		return itemList;
	}

	public ItemList getWeaponRecipe(String itemRef)
	{
		ItemList itemList = new ItemList();
		switch (Material.getMaterial(itemRef))
		{
		case STONE_SWORD :
			itemList.addItem(Material.STONE_SWORD.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.COBBLESTONE.name(), 2);
			break;
		case IRON_SWORD :
			itemList.addItem(Material.IRON_SWORD.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 2);
			break;
		case GOLD_SWORD :
			itemList.addItem(Material.GOLD_SWORD.name(), 1);
			itemList.addItem(Material.STICK.name(), 1);
			itemList.addItem(Material.GOLD_INGOT.name(), 2);
			break;
		case BOW :
			itemList.addItem(Material.BOW.name(), 1);
			itemList.addItem(Material.STICK.name(), 3);
			itemList.addItem(Material.STRING.name(), 3);
			break;
			
		default :
//			itemList = getArmorRecipe(itemRef);
			break;
		}
		return itemList;
	}

	public ItemList getArmorRecipe(String itemRef)
	{
		ItemList itemList = new ItemList();
		switch (Material.getMaterial(itemRef))
		{
		case LEATHER_HELMET :
			itemList.addItem(Material.LEATHER_HELMET.name(), 1);
			itemList.addItem(Material.LEATHER.name(), 3);
			break;
		case LEATHER_CHESTPLATE :
			itemList.addItem(Material.LEATHER_CHESTPLATE.name(), 1);
			itemList.addItem(Material.LEATHER.name(), 8);
			break;
		case LEATHER_BOOTS :
			itemList.addItem(Material.LEATHER_HELMET.name(), 1);
			itemList.addItem(Material.LEATHER.name(), 3);
			break;
		case LEATHER_LEGGINGS :
			itemList.addItem(Material.LEATHER_LEGGINGS.name(), 1);
			itemList.addItem(Material.LEATHER.name(), 7);
			break;
		case IRON_HELMET :
			itemList.addItem(Material.IRON_BOOTS.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 4);
			break;
		case IRON_CHESTPLATE :
			itemList.addItem(Material.IRON_CHESTPLATE.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 8);
			break;
		case IRON_BOOTS :
			itemList.addItem(Material.IRON_BOOTS.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 4);
			break;
		case IRON_LEGGINGS :
			itemList.addItem(Material.IRON_LEGGINGS.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 7);
			break;
		case CHAINMAIL_HELMET :
			itemList.addItem(Material.IRON_BOOTS.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 4);
			break;
		case CHAINMAIL_CHESTPLATE :
			itemList.addItem(Material.IRON_CHESTPLATE.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 8);
			break;
		case CHAINMAIL_BOOTS :
			itemList.addItem(Material.IRON_BOOTS.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 4);
			break;
		case CHAINMAIL_LEGGINGS :
			itemList.addItem(Material.IRON_LEGGINGS.name(), 1);
			itemList.addItem(Material.IRON_INGOT.name(), 7);
			break;
		default :
			break;
		}
		return itemList;
	}
	
}
