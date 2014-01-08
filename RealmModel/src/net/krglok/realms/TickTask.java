package net.krglok.realms;

public class TickTask implements Runnable
{
    //private final transient Server server;
    private final Realms plugin;
	public  static long GameDay = Realms.dayNight / Realms.RealmTick;
    private static int counter = 0;
    private static boolean isProduction = false;
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
		counter++;
		// starte speichern der Settlement vor onTick
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
