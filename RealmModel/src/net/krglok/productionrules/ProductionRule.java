package net.krglok.productionrules;

import java.util.ArrayList;

public class ProductionRule
{
	public String product;
	public ArrayList<ProductionRule> ingredients;
	
	public Boolean canProduce()
	{
		//sind alle Zutaten vorhanden )
		return true;
	}
	
	public void produce()
	{
		//zutaten abbuchen
		//product zurückgeben
	}
	

	
	public ProductionRule()
	{
		
	}

	public ArrayList<ProductionRule> getMissingIngredients()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
