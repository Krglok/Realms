package net.krglok.realms;

public class CacheTask implements Runnable
{

    private final Realms plugin;
	public long CACHE_SCHEDULE =  10;  // 10 * 50 ms 
	public long DELAY_SCHEDULE =  150;  // 10 * 50 ms 

	
	public CacheTask(Realms plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void run()
	{
		if (plugin.getData().writeCache.size() > 0)
		{
			plugin.getData().writeCache.run();
		}
	}

}
