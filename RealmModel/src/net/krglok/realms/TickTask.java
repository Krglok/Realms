package net.krglok.realms;

import org.bukkit.Server;

public class TickTask implements Runnable
{
    //private final transient Server server;
    private final Realms plugin;
    private int counter;
    private boolean isProduction = true;
    private int prodCounter = 20;
	
	public TickTask(Realms plugin)
	{
		this.plugin = plugin;
		counter = 0;
	}
    
	public void setProduction(boolean value)
	{
		this.isProduction = value;
	}
	
	public int getCounter()
	{
		return counter;
	}
	
	public void setProdCounter(int value)
	{
		this.prodCounter = value;
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
				plugin.getLog().info("[Realms] production");
			}
			
		}
	}

}
