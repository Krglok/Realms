package net.krglok.realms;

public class TickTask implements Runnable
{
    //private final transient Server server;
    private final Realms plugin;
    private static int counter = 0;
    private static boolean isProduction = true;
    private static int prodCounter = 20;
	
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
		TickTask.prodCounter = value;
	}
	
	
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		counter++;
		plugin.getRealmModel().OnTick();
		if (counter == prodCounter)
		{
			counter = 0;
			if (isProduction)
			{
				plugin.getRealmModel().OnProduction();
				plugin.getLog().info("[Realms] production calculation");
			}
			
		}
	}

}
