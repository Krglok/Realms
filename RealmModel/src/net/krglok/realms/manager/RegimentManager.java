package net.krglok.realms.manager;

import java.util.ArrayList;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.maps.ScanResult;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentStatus;


/**
 * the regiment manager realize the controller and manager of an regiment.
 * the regiment manager make the the decisions and define the rules of engagement
 * only a player can make different decisions.
 * 
 * the manager can interact with the world and send commands and requests to other managers
 * @author Windu
 *
 */
public class RegimentManager {

	private ArrayList<ScanResult> scanRequest;
	private int targetId;

	
	public RegimentManager()
	{
		scanRequest = new ArrayList<ScanResult>();
		targetId = 0;
	}

	public void run(RealmModel rModel, Regiment regiment)
	{
		if (regiment.getRegStatus() == RegimentStatus.NONE)
		{
			if (regiment.getPosition().getWorld() == "")
			{
				getNewPosition(rModel, regiment, targetId);
				regiment.setRegStatus(RegimentStatus.SEARCHPOS);
				
			}
		}
		if (regiment.getRegStatus() ==  RegimentStatus.SEARCHPOS)
		{
			doSearchPos( rModel,  regiment);
		}
	}
	
	private void getNewPosition(RealmModel rModel, Regiment regiment, int targetId)
	{
		int maxPos = rModel.getSettlements().getSettlements().size();
		if (maxPos > 0)
		{
			if (maxPos > 1)
			{
				double result = 1.0 + (Math.random() * maxPos);
				targetId = (int) result; 
			} else
			{
				if (targetId != maxPos)
				{
					targetId = maxPos;
				}
			}
			LocationData settlePos = rModel.getSettlements().getSettlement(targetId).getPosition();
			settlePos.setX(settlePos.getX()+120);
			settlePos.setZ(settlePos.getZ()+120);
			regiment.setPosition(settlePos);
		}
		
	}
	
	private void doSearchPos(RealmModel rModel, Regiment regiment)
	{
		
	}
	
}
