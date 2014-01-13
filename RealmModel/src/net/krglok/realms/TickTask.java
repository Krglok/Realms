package net.krglok.realms;

public class TickTask implements Runnable
{
    //private final transient Server server;
    private final Realms plugin;
    private static int counter = 0;
    private static boolean isProduction = false;
    private static int prodLimit = 40;  // GameDay;
    private static int taxCounter = 0;
    private static int taxLimit = prodLimit * 10;
	
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
	
	
	
	@Override
	public void run()
	{
		counter++;
		taxCounter++;
		// starte speichern der Settlement vor onTick
		plugin.getRealmModel().OnTick();
		if (counter == prodLimit)
		{
			counter = 0;
			if (isProduction)
			{
				plugin.getRealmModel().OnProduction();
				plugin.getLog().info("[Realms] production calculation");
//				plugin.getLog().info("Tax counter "+taxCounter);
			}
			
		}
		if(taxCounter >= taxLimit)
		{
			taxCounter = 0;
			if (isProduction)
			{
				plugin.getRealmModel().OnTax();
				plugin.getLog().info("[Realms] Tax calculation");
			}
		}
	}

}
