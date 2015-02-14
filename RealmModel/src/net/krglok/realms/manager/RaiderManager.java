package net.krglok.realms.manager;

import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
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
public class RaiderManager 
{
	private static int CHECKDELAY = 20; //100;
	private static int lastSettleId = 0;
	
//	private ArrayList<ScanResult> scanRequest;
	protected RaiderAction raiderAction;
	private int targetId;
	private PositionFace lastFace = PositionFace.NORTH;
	private int faceIndex = 0;
	private int checkCounter = CHECKDELAY;
	private int actualCampPos = 0;
	private PositionFace[] faceList = new PositionFace[] { PositionFace.NORTH, PositionFace.EAST, PositionFace.SOUTH, PositionFace.WEST}; 
	protected boolean hasBattle = false;
	
	public RaiderManager()
	{
//		scanRequest = new ArrayList<ScanResult>();
		targetId = 0;
		raiderAction = RaiderAction.NONE;
	}
	
	public RaiderAction getRaiderAction()
	{
		return raiderAction;
	}

	public void setRaiderAction(RaiderAction raiderAction)
	{
		this.raiderAction = raiderAction;
	}
	
	public boolean hasBattle()
	{
		return hasBattle;
	}

	public void setHasBattle(boolean hasBattle)
	{
		this.hasBattle = hasBattle;
	}

	/**
	 * toggle action if in DAYx
	 * ignore :
	 * - SCANPOS
	 * - MOVE 
	 * hint: used within realms.onDay()
	 */
	public void nextDay()
	{
		System.out.println("[REALMS] raider toggle day "+raiderAction.name());
		switch(raiderAction)
		{
		case DAY1 : 
			raiderAction = RaiderAction.DAY2;
			break;
		case DAY2 : 
//			raiderAction = RaiderAction.NEWPOS;
			raiderAction = RaiderAction.DAY3;
			break;
		case DAY3:
			raiderAction = RaiderAction.NEWPOS;
			break;
		default :
			break;
		}
	}

	private PositionFace nextFace()
	{
		faceIndex++;
		if (faceIndex >= faceList.length)
		{
			faceIndex = 0;
		}
		return faceList[faceIndex];
	}

	/**
	 * run the raiderManager actions
	 * 
	 * @param rModel
	 * @param regiment
	 */
	public void run(RealmModel rModel, Regiment regiment)
	{
//		if (regiment)
		
		switch (raiderAction)
		{
		case BATTLE_END:
			doBattleEnd(rModel, regiment);
			break;
		case SCANPOS: 
			doScanPos(rModel, regiment);
			break;
		case NEWPOS:
			doNewPos(rModel, regiment);
			break;
		case MOVE:
			break;
		case DAY1:
			doDay1(rModel, regiment);
			break;
		case DAY2:
//			doDay1(rModel, regiment);
			break;
		case DAY3:
//			doDay1(rModel, regiment);
			break;
		default:
			raiderAction = RaiderAction.SCANPOS;
			break;
		}
	}
	
	public boolean findSettleCamp(RealmModel rModel, int settleId, PositionFace face)
	{
		for (CampPosition campPos : rModel.getData().getCampList().values())
		{
			if (campPos.getSettleId() == settleId)
			{
				if (campPos.getFace() == face)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private void getNewPosition(RealmModel rModel, Regiment regiment)
	{
		// do not make new scan until last scan ready 
		// make new scan for next settlement
		lastSettleId++;
		int maxPos = rModel.getSettlements().size();
		if (lastSettleId < maxPos)
		{
			if (findSettleCamp(rModel, lastSettleId,  lastFace) == false)
			{
//			System.out.println("RaiderManager nextSettle "+lastSettleId);
				CampPosition campPos = new CampPosition();
				campPos.setSettleId(lastSettleId);
				campPos.setPosition(rModel.getData().getSettlements().getSettlement(lastSettleId).getPosition());
				campPos.setFace(lastFace);
				campPos.setActiv(true);
				rModel.getData().getCampList().addCampPosition(campPos);
				rModel.getData().writeCampPosition(campPos);
				actualCampPos = campPos.getId();
			}
		} else
		{
			lastFace = nextFace();
			lastSettleId = 0;
		}
		
	}

	/**
	 * search for next raider traget
	 * 
	 * @param rModel
	 * @param regiment
	 * @return
	 */
	private int doSearchPos(RealmModel rModel, Regiment regiment)
	{
		int foundSettle = 0;
		for (Settlement settle : rModel.getData().getSettlements().values())
		{
			if (settle.getBarrack().getUnitList().size() == 0)
			{
				if (regiment.getCampList().containsKey(settle.getId()) == false)
				{
					if (settle.getKingdomId() == 0)
					{
						return settle.getId();
					}
				}
			}
		}

		for (Settlement settle : rModel.getData().getSettlements().values())
		{
			if (settle.getBarrack().getUnitList().size() == 0)
			{
				if (regiment.getCampList().containsKey(settle.getId()) == false)
				{
					if (settle.getKingdomId() > 0)
					{
						return settle.getId();
					}
				}
			}
		}
		return 0;
	}
	
	private void doScanPos(RealmModel rModel, Regiment regiment)
	{
		if (checkCounter > 0)
		{
//			System.out.println("RaiderManager "+checkCounter);
			checkCounter--;
			return;
		}
		checkCounter = CHECKDELAY;
//		System.out.println("RaiderManager "+regiment.getRegStatus());
		if (regiment.getRegStatus() ==  RegimentStatus.NONE)
		{
			regiment.setRegStatus(RegimentStatus.IDLE);
		}
		
		if (regiment.getRegStatus() == RegimentStatus.IDLE)
		{
//			System.out.println("CampPos "+rModel.getData().InitCampList().size());
			if (actualCampPos == 0)
			{
				for (CampPosition campPos : rModel.getData().getCampList().values())
				{
					if (campPos.isActiv() == true)
					{
						actualCampPos = campPos.getId();
						return;
					}
				}
				getNewPosition(rModel, regiment);
			} else
			{
				if (rModel.getData().getCampList().getCampPosition(actualCampPos).isActiv() == false)
				{
					CampPosition campPos = rModel.getData().getCampList().getCampPosition(actualCampPos);
//						System.out.println("RaiderManager actual Pos "+actualCampPos+" Valid "+campPos.isValid());
					if (campPos.isValid() == true)
					{
						rModel.getData().writeCampPosition(campPos);
						return;
					}
//					rModel.getData().writeCampPosition(campPos);
					rModel.getData().removeCampPosition(campPos);
					rModel.getData().getCampList().remove(campPos.getId());
					actualCampPos = 0;
				}
				return;
			}
			raiderAction = RaiderAction.NEWPOS;
		}
		
	}
	
	
	private void doNewPos(RealmModel rModel, Regiment regiment)
	{
		int newTarget = doSearchPos(rModel, regiment);
		if (newTarget > 0)
		{
			doStartMove(rModel, regiment, newTarget);
			raiderAction = RaiderAction.DAY1;
		}
	}
	
	private void doStartMove(RealmModel rModel, Regiment regiment, int newTarget)
	{
		regiment.setSettleId(newTarget);
		for (CampPosition campPos : rModel.getData().getCampList().values())
		{
			if (campPos.getSettleId() == newTarget)
			{
				if (campPos.isValid())
				{
					if (regiment.getCampList().containsKey(newTarget) == false)
					{
						regiment.setSettleId(newTarget);
						regiment.setTarget(LocationData.copyLocation(campPos.getPosition()));
						regiment.getCampList().put(newTarget, campPos.getFace());
						raiderAction = RaiderAction.MOVE;
						rModel.getData().writeRegiment(regiment);
					}
				}
			}
		}
		
		
		
	}
	
	/**
	 * determine the action of the regiment after a raid on a settlement 
	 * @param rModel
	 * @param regiment
	 */
	private void doBattleEnd(RealmModel rModel, Regiment regiment)
	{
		if (regiment.getBattle().winBattle() == true)
		{
			raiderAction = raiderAction.DAY3;
		} else
		{
			raiderAction = raiderAction.NEWPOS;
		}
	}
	
	
	private void doDay1(RealmModel rModel, Regiment regiment)
	{
		
	}
	
	
}
