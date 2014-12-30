package net.krglok.realms.sim;

import javax.swing.JProgressBar;

import net.krglok.realms.TickTask;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.model.RealmModel;

public class SimTask implements Runnable
{
    private static long counter = 0;
    private static int tick = 0;
    private static int taxCounter = 0;
    private static int taxLimit = 30;
    private static boolean isProduction = false;
    private static int prodLimit = (int) ConfigBasis.GameDay;
	
	JProgressBar pBar;
	RealmModel realmModel;

	public static void main(JProgressBar progressBar)
	{
	}
	
	public static void setIsProduction(boolean value)
	{
		isProduction = value;
	}
	
	public static long getCounter()
	{
		return counter;
	}
	
	public static void setCounter(long l)
	{
		counter = l;
	}
	
	public static void setProdLimit(int value)
	{
		prodLimit = value;
	}
	
	public static boolean isProduction()
	{
		return isProduction;
	}
	
	
	public SimTask(JProgressBar progressBar, RealmModel realmModel)
	{
		this.pBar = progressBar;
		this.realmModel = realmModel;
		tick = 0;
	}

	@Override
	public void run()
	{
		tick++;
		if (tick > pBar.getMaximum())
		{
			tick = 1;
		}
		pBar.setValue(tick);
		
	}

	public void doTick()
	{
		realmModel.OnTick();
//		plugin.onBuildRequest();
//		plugin.onCleanRequest();
//		plugin.onSignRequest();

		if (isProduction)
		{
			taxCounter++;
			realmModel.OnProduction("SteamHaven");
//			plugin.getLog().info("[Realms] production calculation");
		} else
		{
			System.out.println("[Realms] Production "+isProduction);
			
		}
		// start Day Event
//		if( (plugin.getServer().getWorlds().get(0).getTime() >= 23000))
//		{
//				if (isProduction)
//				{
//					plugin.getRealmModel().OnTrade();
//					plugin.getLog().info("[Realms] Trader calculation");
//				}
//		}
			
		
		//Tax run
		if(taxCounter >= taxLimit)
		{
			taxCounter = 0;
			if (isProduction)
			{
				realmModel.OnTax();
//				plugin.getLog().info("[Realms] Tax calculation");
				System.out.println("[Realms] Tax");
			}
		}
		
	}
}
