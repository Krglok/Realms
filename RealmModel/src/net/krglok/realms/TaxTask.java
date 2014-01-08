package net.krglok.realms;


public class TaxTask implements Runnable
{
    private final Realms plugin;
    public static final long TICKTIME = 50;
    public static final long DAY_SECONDS = 86400000;
	public static long TAX_SCHEDULE = Realms.dayNight / Realms.RealmTick * 30;  //1728000;
    private static int counter = 0;
    private static boolean isTax = true;
    private static int taxCounter = 1;
	
	public TaxTask(Realms plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		counter++;
		if (counter == taxCounter)
		{
			counter = 0;
			if (isTax)
			{
				plugin.getRealmModel().OnTax();
				plugin.getLog().info("[Realms] Tax calculation");
			}
		}
		
	}


	public static long getTAX_SCHEDULE()
	{
		return TAX_SCHEDULE;
	}


	public static void setTAX_SCHEDULE(long tAX_SCHEDULE)
	{
		TAX_SCHEDULE = tAX_SCHEDULE;
	}

	public static int getCounter()
	{
		return counter;
	}

	public static void setCounter(int counter)
	{
		TaxTask.counter = counter;
	}

	public static boolean isTax()
	{
		return isTax;
	}

	public static void setTax(boolean isTax)
	{
		TaxTask.isTax = isTax;
	}

	public static int getTaxCounter()
	{
		return taxCounter;
	}

	public static void setTaxCounter(int taxCounter)
	{
		TaxTask.taxCounter = taxCounter;
	}

}
