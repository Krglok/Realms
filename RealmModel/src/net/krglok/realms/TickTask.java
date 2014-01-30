package net.krglok.realms;

public class TickTask implements Runnable
{
    //private final transient Server server;
    private final Realms plugin;
    private static int counter = 0;
    private static boolean isProduction = false;
    private static int prodLimit = 100;  // GameDay;
    private static int taxCounter = 0;
    private static int taxLimit = prodLimit * 10;
    private static int buildMin = prodLimit * 4 / 10;
    private static int buildMax = prodLimit * 7 / 10;
	
    public static int getProdCounter()
	{
		return prodLimit;
	}

	public TickTask(Realms plugin)
	{
		this.plugin = plugin;
		counter = 0;
	}
    
	public static void setProduction(boolean value)
	{
		TickTask.isProduction = value;
	}
	
	public static int getCounter()
	{
		return counter;
	}
	
	public static void setProdCounter(int value)
	{
		TickTask.prodLimit = value;
	}
	
	public static boolean isProduction()
	{
		return TickTask.isProduction;
	}
	
	
	@Override
	public void run()
	{
		counter++;
		taxCounter++;
		// starte speichern der Settlement vor onTick
		plugin.getRealmModel().OnTick();
//		System.out.println("[Realms] Tick "+counter);
//		if ((counter > buildMin) && (counter < buildMax))
//		{
//			System.out.println("BuildRequest");
			plugin.onBuildRequest();
			plugin.onCleanRequest();
//		}
		
		if (counter == (prodLimit/2))
		{
			if (isProduction)
			{
				plugin.getRealmModel().OnTrade();
				plugin.getLog().info("[Realms] Trader calculation");
			}

		}
		if (counter == prodLimit)
		{
			counter = 0;
			if (isProduction)
			{
				plugin.getRealmModel().OnProduction();
				plugin.getLog().info("[Realms] production calculation");
//				System.out.println("[Realms] Production");
//				plugin.getLog().info("Tax counter "+taxCounter);
			} else
			{
				System.out.println("[Realms] Production "+isProduction);
				
			}
			
		} 
		if(taxCounter >= taxLimit)
		{
			taxCounter = 0;
			if (isProduction)
			{
				plugin.getRealmModel().OnTax();
				plugin.getLog().info("[Realms] Tax calculation");
				System.out.println("[Realms] Tax");
			}
		}
	}

}
