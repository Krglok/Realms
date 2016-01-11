package net.krglok.realms.manager;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.RouteOrder;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.core.TradeTransport;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.model.RealmModel;

/**
 * Der Lehen manager steuert die internen automatischen Abläufe des Lehens
 * - warentransport
 *  
 * 
 * Das Lehen hat keine eigene Production von Waren, sondern ist auf die VErsorgung von aussen angewiesen.
 * Zusaetzlich muessen die eigenen Regimenter versorgt werden.
 *  
 * @author Windu
 *
 */

public class LehenManager
{

	
	private int ROUTEDELAY = 20;
	private int delayRoutes;
	private LehenStatus lStatus ;
	private Lehen lehen;

	public LehenManager()
	{
		delayRoutes = 0;
		lStatus = LehenStatus.NONE;
	}

    public void run(RealmModel rModel, Lehen lehen)
    {
	    //  not on each tick 
    	
    	if(delayRoutes > ROUTEDELAY)
	    {
	      if(needResources(lehen))
	      {
	        if(lehen.isFreeNPC())
	        {
	          startNextTransport(rModel,  lehen);
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
	
    /**
     * check for items in the required List of the lehen.
     * this are the imediate needed items/blocks
     * 
     * @param lehen
     * @return
     */
    private boolean needResources(Lehen lehen)
    {
    	if (lehen.getrequiredItems().size() > 0)
    	{
    		return true;
    	}
    	return false;
    }
    
    /**
     * Make a transport order for the next item in required list.
     * no special priority 
     * 
     * @param rModel
     * @param lehen
     */
    private void startNextTransport(RealmModel rModel, Lehen lehen)
    {
    	Item item = lehen.getrequiredItems().values().iterator().next();
    	Double price = rModel.getData().getPriceList().getBasePrice(item.ItemRef()); 
    	RouteOrder rOrder = new RouteOrder(0, lehen.getId(), item.ItemRef(), item.value(), price, true);
		lehen.getTrader().makeRouteOrder(rModel.getTradeMarket(), rOrder, rModel.getTradeTransport(), lehen, rModel.getSettlements());
		// item can remove by itenname, because items are unique in the list
		lehen.getrequiredItems().remove(item.ItemRef());
    }
}
