package net.krglok.realms.manager;

import net.krglok.realms.kingdom.Lehen;

public class LehenManager
{

	
	private int ROUTEDELAY = 20;
	private int delayRoutes;
	private LehenStatus lStatus ;

	public LehenManager()
	{
		delayRoutes = 0;
		lStatus = LehenStatus.NONE;
	}

    public void run(Lehen lehen)
    {
	    if(delayRoutes > ROUTEDELAY)
	    {
	      if(needResources())
	      {
	        if(lehen.isFreeNPC())
	        {
	          startNextTransport(lehen);
	        }else
	        {
	        }
	      }else
	      {
	      }
	    }else
	    {
	      delayRoutes++;
	    }
	    
	    switch (lStatus)
	    {
	    case PRODUCTION :
	    	break;
	    case DEFEND :
	    	break;
	    case ATTACK :
	    	break;

	    case SIEGED :
	    	break;
	    default:
	    	break;

	    }
    }
	
    private boolean needResources()
    {
    	return false;
    }
    
    private void startNextTransport(Lehen lehen)
    {
    	
    }
}
