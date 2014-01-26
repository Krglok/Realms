package net.krglok.productionrules;

import java.util.ArrayList;
import java.util.Stack;


public class ProductionEngine
{
    private Stack<ProductionRule> stack;
    
	public  void executer(ProductionRule r)
	{
		if (r.canProduce())
		{
			r.produce();
			//product zurückgeben
		}else
		{
			ArrayList<ProductionRule> mm = r.getMissingIngredients();
			
			// jede Zutat auf Stack legen
		}
		
	}
	
	
	
	public ProductionEngine()
	{
		// rule auf den Stack
		
		// while (!stack.isEmpty)
		  //if stack.peek.canproduce -> produce, produkt in speicher, stacvk.pop
		  //else stack.peek.getmissing auf stack, bereits vorh zutataten reservieren
		
		//prodkt aus speicher zurückgeben
		
		
	}
	
}
