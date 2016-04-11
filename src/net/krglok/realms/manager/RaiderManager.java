package net.krglok.realms.manager;

import java.util.Iterator;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unit.RegimentStatus;


/**
 * <pre>
 * the regiment manager realize the controller and manager of an regiment.
 * the regiment manager make the the decisions and define the rules of engagement
 * only a player can make different decisions.
 * 
 * the manager can interact with the world and send commands and requests to other managers
 * the RaiderManager work as a  finite state machine. all action done by RaiderCycle and RaiderAction. 

 * @author Windu
 * </pre>
 */
public class RaiderManager 
{
	private static int CHECKDELAY = 20; //100;
	private static int lastSettleId = 0;
	
//	private ArrayList<ScanResult> scanRequest;
	protected RaiderAction raiderAction;
	protected RaiderCycle raiderCycle;
	private int targetId;
	private PositionFace lastFace = PositionFace.NORTH;
	private int faceIndex = 0;
	private int checkCounter = CHECKDELAY;
	private int actualCampPos = 0;
	private PositionFace[] faceList = new PositionFace[] { PositionFace.NORTH, PositionFace.EAST, PositionFace.SOUTH, PositionFace.WEST}; 
	protected boolean hasBattle = false;
	protected boolean hasNewPos = false;
	private int scanCounter = 0;
	private int lastFound = 0;
	private boolean tradeStart = false;
	private boolean tradeEnde = false;
	private Iterator<Item> tradeList ;
	
	
	public RaiderManager()
	{
//		scanRequest = new ArrayList<ScanResult>();
		targetId = 0;
		raiderAction = RaiderAction.NONE;
		raiderCycle = RaiderCycle.DAY1;
	}
	
	public RaiderAction getRaiderAction()
	{
		return raiderAction;
	}

	public void setRaiderAction(RaiderAction raiderAction)
	{
		this.raiderAction = raiderAction;
	}
	
	public RaiderCycle getRaiderCycle()
	{
		return raiderCycle;
	}

	public void setRaiderCycle(RaiderCycle raiderCycle)
	{
		this.raiderCycle = raiderCycle;
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
		System.out.println("[REALMS] raiderManager status is"+raiderCycle.name());
		raiderCycle = RaiderCycle.nextCyle(raiderCycle);
		System.out.println("[REALMS] raiderManager toggle to"+raiderCycle.name());
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
		switch(raiderCycle)
		{
		case DAY1:
			tradeStart = false;
			tradeEnde = false;
			doDay1(rModel, regiment);
			break;
		case DAY2:
			doDay2(rModel, regiment);
			break;
		case DAY3:
			doDay3(rModel, regiment);
			break;
		case DAY4:
			doDay4(rModel, regiment);
			break;
		case DAY5:
			doDay5(rModel, regiment);
			break;
		case DAY6:
			doDay6(rModel, regiment);
			break;
		case DAY7:
			doDay7(rModel, regiment);
			break;
			default:
				raiderCycle = RaiderCycle.DAY1;
				break;
		
		}
//		System.out.println("[REALMS] raiderManager "+raiderCycle.name()+":"+raiderAction.name());
		
		switch (raiderAction)
		{
//		case BATTLE_END:
//			doBattleEnd(rModel, regiment);
//			break;
		case SCANPOS:
			if (checkCounter > 0)
			{
				checkCounter--;
				return;
			}
			checkCounter = CHECKDELAY;
			
			doScanPos(rModel, regiment);
			break;
		case TRADE:
			doTrade(rModel, regiment);
			break;
		case NEWPOS:
			doNewPos(rModel, regiment);
			break;
		case MOVE:
			break;
		default:
			raiderAction = RaiderAction.NONE;
			break;
		}
	}
	
	/**
	 * find settle and face in campPOs list
	 * 
	 * @param rModel
	 * @param settleId
	 * @param face
	 * @return
	 */
	public boolean checkSettleCamp(RealmModel rModel, int settleId, PositionFace face)
	{
		for (CampPosition campPos : rModel.getData().getCampList().values())
		{
			if ((campPos.getSettleId() == settleId) && (campPos.getSettleId() != lastFound))
			{
				if (campPos.getFace() == face)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param sType
	 * @return scan radius for setlement type
	 */
	public int setRange(SettleType sType)
	{
		switch(sType)
		{
		case HAMLET: return 40 + 60;
		case TOWN: return 70 + 60;
		case CITY: return 100 + 60;
		case METROPOLIS: return 200 + 60;
		default :
			return 10;
		}
	}
	
	/**
	 *  make new ScanPos based on settlement, and PositionFace
	 *  only scan on same world
	 * @param rModel
	 * @param regiment
	 */
	public CampPosition getNewScanPosition(RealmModel rModel, Regiment regiment, int lastSettleId, PositionFace lastFace)
	{
		if (rModel.getData().getSettlements().getSettlement(lastSettleId) != null)
		{
			// only scan on same world
			if (regiment.getPosition().getWorld().equalsIgnoreCase(rModel.getData().getSettlements().getSettlement(lastSettleId).getPosition().getWorld()))
			{
				CampPosition campPos = new CampPosition();
				campPos.setSettleId(lastSettleId);
				int range = setRange(rModel.getData().getSettlements().getSettlement(campPos.getSettleId()).getSettleType());
				LocationData pos = rModel.getData().getSettlements().getSettlement(campPos.getSettleId()).getPosition();
				campPos.setPosition(PositionFace.getScanPos(lastFace,pos ,range));
				campPos.setFace(lastFace);
				campPos.setActiv(true);
				rModel.getData().getCampList().addCampPosition(campPos);
				rModel.getData().writeCampPosition(campPos);
				actualCampPos = campPos.getId();
	//			System.out.println("[REALMS) New Scan Pos "+scanCounter+"  Settle "+lastSettleId+" : "+lastFace.name()+":"+range);
				return campPos;
			}
		}
		return null;
	}
	
	
	
	/**
	 * <pre>
	 * calculate new scanpos based on redo counter:
	 * 1 = NORTH
	 * 2 = NORTHEAST
	 * 3 = EAST
	 * 4 = SOUTHEAST
	 * 5 = SOUTH
	 * 6 = SOUTHWEST
	 * 7 = WEST
	 * 8 = NORTHWEST
	 * @param position
	 * @param redo
	 * @return
	 * </pre>
	 */
	private LocationData calculateNearScanPos(LocationData position, int redo)
	{
		int range = 10;
		
		switch (redo)
		{
		case 1: //NORTH
			position.setZ(position.getZ()-range);
			break;
		case 2: //NORTHEAST
			position.setZ(position.getZ()-range);
			position.setX(position.getX()+range);
			break;
		case 3: // EAST
			position.setX(position.getX()+range);
			break;
		case 4: // SOUTHEAST
			position.setZ(position.getZ()+range);
			position.setX(position.getX()+range);
			break;
		case 5: // SOUTH
			position.setZ(position.getZ()+range);
			break;
		case 6: // SOUTHWEST
			position.setZ(position.getZ()+range);
			position.setX(position.getX()-range);
			break;
		case 7: // WEST
			position.setX(position.getX()-range);
			break;
		case 8: // NORTHWEST
			position.setZ(position.getZ()-range);
			position.setX(position.getX()-range);
			break;
		default:
			break;
		}
		
		return position;
	}
	
	public CampPosition getRedoScanPosition(RealmModel rModel, CampPosition campPos, int lastSettleId, PositionFace lastFace)
	{
//		System.out.println("[REALMS) CampPos Redo Scan: "+scanCounter+":"+campPos.getRedo()+"  Settle "+lastSettleId+" : "+lastFace.name());
		lastSettleId = campPos.getSettleId();
		campPos.addRedo();
		if (campPos.getRedo() > 8)
		{
			campPos.setRedo(1);
		}
		LocationData newPos = calculateNearScanPos(campPos.getPosition(),campPos.getRedo());
		campPos.setPosition(newPos);
		campPos.setFace(lastFace);
		campPos.setActiv(true);
		rModel.getData().writeCampPosition(campPos);
		actualCampPos = campPos.getId();
		return campPos; 
	}

	
	private void doScanPos(RealmModel rModel, Regiment regiment)
	{
		// neuen Scan machen  oder alten Auswerten 
		if (actualCampPos == 0)
		{
			scanCounter++;
			lastSettleId++;
			// only new campPosition should scan 
			if (checkSettleCamp(rModel, lastSettleId, lastFace) == false)
			{
				getNewScanPosition(rModel, regiment,lastSettleId, lastFace);
				return;
			} else
			{
				CampPosition campPos = rModel.getData().getCampList().getCampPosition( lastSettleId, lastFace);
				if (campPos != null)
				{
					if (campPos.isValid() == false)
					{
						getRedoScanPosition(rModel, campPos, lastSettleId, lastFace);
					}
					return;
				}
			}
			System.out.println("[REALMS) skip Scan Pos "+scanCounter+"  Settle "+lastSettleId+" : "+lastFace.name());
			return;
		} else
		{
			if (rModel.getData().getCampList().getCampPosition(actualCampPos).isActiv() == false)
			{
				CampPosition campPos = rModel.getData().getCampList().getCampPosition(actualCampPos);
				if (campPos.isValid() == true)
				{
					rModel.getData().writeCampPosition(campPos);
//					System.out.println("[REALMS) Store Scan Pos "+actualCampPos+" NOT activ, do NEWPOS ");
//						raiderAction = RaiderAction.NEWPOS;
					actualCampPos = 0;
					return;
				}
				rModel.getData().writeCampPosition(campPos);
//				System.out.println("[REALMS) Reset Scan Pos "+actualCampPos+"  "+campPos.isValid());
				actualCampPos = 0;
			}
			return;
		}
	}
	
	
	private void doNewPos(RealmModel rModel, Regiment regiment)
	{
		lastFound = SearchNewPos(rModel, regiment);
		if (lastFound > 0)
		{
			doStartMove(rModel, regiment, lastFound);
			return;
		}
	}
	
	private boolean isOldCamp(Regiment regiment, int settleId)
	{
		for (Integer ref :regiment.getCampList().keySet())
		{
			if (ref == settleId) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * search for next raider campPos 
	 * 
	 * @param rModel
	 * @param regiment
	 * @return  id of CampPos
	 */
	private int SearchNewPos(RealmModel rModel, Regiment regiment)
	{
//		int foundSettle = 0;
		for (CampPosition campPos : rModel.getData().getCampList().values())
		{
			// only move to settlement on same world
			if (regiment.getPosition().getWorld().equalsIgnoreCase(campPos.getPosition().getWorld()))
			{
				if (campPos.isValid())
				{
					if (regiment.getSettleId() != campPos.getSettleId())
					{
						if (isOldCamp(regiment, campPos.getSettleId()) == false)
						{
							return campPos.getId();
						}
					}
				}
			}
		}
		// remove first entry in List to get free campPos
		if (regiment.getCampList().keySet().iterator().hasNext())
		{
			int key = regiment.getCampList().keySet().iterator().next();
			regiment.getCampList().remove(key);
		}

		System.out.println("[REALMS) doSearchPos NOT found, free first campPos");
		return 0;
	}

	private void doStartMove(RealmModel rModel, Regiment regiment, int newCamp)
	{
		CampPosition campPos = rModel.getData().getCampList().getCampPosition(newCamp);
		if (campPos != null)
		{
			if (campPos.isValid())
			{
				regiment.setSettleId(campPos.getSettleId());
				LocationData newPos = LocationData.copyLocation(campPos.getPosition());
				newPos.setY(newPos.getY()+1);
				regiment.setTarget(newPos);
				regiment.getCampList().put(campPos.getSettleId(), campPos.getFace());
				regiment.startMove();
				raiderAction = RaiderAction.MOVE;
				rModel.getData().writeRegiment(regiment);
				System.out.println("Raider Start MOVE "+newCamp+":"+campPos.getSettleId());
				return;
			}
			System.out.println("CampPos NOT Valid "+newCamp+":"+campPos.getSettleId()+":"+campPos.isValid());
		} else
		{
			System.out.println("CampPos NOT Valid "+newCamp);
		}
//		raiderAction = RaiderAction.SCANPOS;
		System.out.println("NewTarget NOT found in CampPos, do NONE");
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
			raiderAction = raiderAction.NONE;
		} else
		{
			raiderAction = raiderAction.NEWPOS;
		}
	}
	
	
	private void doDay1(RealmModel rModel, Regiment regiment)
	{
		if (scanCounter >= rModel.getSettlements().size())
		{
			System.out.println("[REALMS) END Scan Pos after "+scanCounter);
			scanCounter = 0;
			lastFace = nextFace();
			lastSettleId = 0;
			actualCampPos = 0;
		}
		raiderAction = RaiderAction.SCANPOS;
	}
	
	
	private void doDay2(RealmModel rModel, Regiment regiment)
	{
		hasNewPos = false;
		hasBattle = false;
		raiderAction = RaiderAction.TRADE;
	}
	
	private void doDay3(RealmModel rModel, Regiment regiment)
	{
		if (hasBattle == false)
		{
			if (checkSupply(regiment))
			{
				if (regiment.getSettleId() > 0)
				{
					System.out.println("Regiment "+regiment.getId()+" has low supply, will be raid !");
					Settlement settle = rModel.getData().getSettlements().getSettlement(regiment.getSettleId());
			    	regiment.startRaid(settle);
					hasBattle = true;
					raiderAction = RaiderAction.NONE;
				}
			}
		}
	}

	private void doDay4(RealmModel rModel, Regiment regiment)
	{
	}

	private void doDay5(RealmModel rModel, Regiment regiment)
	{
		
	}

	private void doDay6(RealmModel rModel, Regiment regiment)
	{
		if (hasNewPos == false)
		{
			raiderAction = RaiderAction.NEWPOS;
			hasNewPos = true;
		} else
		{
			
		}
	}

	private void doDay7(RealmModel rModel, Regiment regiment)
	{
		
	}
	
	private void doTrade(RealmModel rModel, Regiment regiment)
	{
		if (tradeStart == false)
		{
			tradeList = regiment.getWarehouse().getItemList().values().iterator();
			tradeStart = true;
			tradeEnde = false;
		} 
		if (tradeEnde == false)
		{
			if (tradeList.hasNext())
			{
				Item item = tradeList.next();
				doNextTrade( rModel,  regiment, item);
			} else
			{
				tradeEnde = true;
			}
		} else
		{
			buyFood(rModel, regiment);
		}
	}
	
	private void doNextTrade(RealmModel rModel, Regiment regiment, Item item)
	{
		Settlement settle = rModel.getData().getSettlements().getSettlement(regiment.getSettleId());
		if (settle != null)
		{
			int amount = 0;
			Item require = settle.getRequiredProduction().getItem(item.ItemRef());
			if (require != null)
			{
				if (item.value() > require.value())
				{
					double price = rModel.getData().getPriceList().getBasePrice(item.ItemRef());
					if (price < 0.1) { price = 0.1; }
					double cost = price * require.value() * 0.75;
					if (settle.getBank().getKonto() > cost)
					{
						System.out.println("RaiderTrade Required "+item.ItemRef());
						regiment.getWarehouse().withdrawItemValue(item.ItemRef(), require.value());
						settle.getWarehouse().depositItemValue(item.ItemRef(), require.value());
						settle.getBank().withdrawKonto(cost, "Raider", settle.getId());
						regiment.getBank().depositKonto(cost, "Raider", settle.getId());
					}
				}
				return;
			}
			if (ConfigBasis.initValuables().containsKey(item.ItemRef()))
			{
				amount = item.value();
				double price = rModel.getData().getPriceList().getBasePrice(item.ItemRef());
				if (price < 0.1) 
				{ 
					return ; 
				}
				double cost = price * amount * 0.75;
				if (settle.getBank().getKonto() > cost)
				{
					System.out.println("RaiderTrade Valuables "+item.ItemRef());
					regiment.getWarehouse().withdrawItemValue(item.ItemRef(), amount);
					settle.getWarehouse().depositItemValue(item.ItemRef(), amount);
					settle.getBank().withdrawKonto(cost, "Raider", settle.getId());
					regiment.getBank().depositKonto(cost, "Raider", settle.getId());
					return;
				}
			}
			if (ConfigBasis.initBuildMaterial().containsKey(item.ItemRef()))
			{
				double price = rModel.getData().getPriceList().getBasePrice(item.ItemRef());
				if (price < 0.1) 
				{ 
					return ; 
				}
				if (regiment.getWarehouse().getItemList().getValue(item.ItemRef()) < 2)
				{
					return;
				}
				amount = 1000;
				if (regiment.getWarehouse().getItemList().getValue(item.ItemRef()) < amount)
				{
					amount = regiment.getWarehouse().getItemList().getValue(item.ItemRef());
				}
				double cost = price * amount * 0.75;
				if (settle.getBank().getKonto() > cost)
				{
					amount = (int) (settle.getBank().getKonto() / cost)-1;
					cost = price * amount * 0.75;
				}
				if (settle.getBank().getKonto() > cost)
				{
					System.out.println("RaiderTrade BuildMaterial "+item.ItemRef());
					regiment.getWarehouse().withdrawItemValue(item.ItemRef(), amount);
					settle.getWarehouse().depositItemValue(item.ItemRef(), amount);
					settle.getBank().withdrawKonto(cost, "Raider", settle.getId());
					regiment.getBank().depositKonto(cost, "Raider", settle.getId());
					return;
				}
			}
		}
	}
	
	/**
	 * check for 7 days supply 
	 * 
	 * @param rModel
	 * @param regiment
	 */
	private  void buyFood(RealmModel rModel, Regiment regiment)
	{
		int amount = regiment.getBarrack().getUnitList().size() * 7;
		Item item = new Item("BREAD", amount);
		if (regiment.getWarehouse().getItemList().getValue(item.ItemRef()) < amount )
		{
			buyFromSettlement(rModel, regiment, item);
		}
		item = new Item("WHEAT", amount);
		if (regiment.getWarehouse().getItemList().getValue(item.ItemRef()) < amount )
		{
			buyFromSettlement(rModel, regiment, item);
		}
	}
	
	private void  buyFromSettlement(RealmModel rModel, Regiment regiment, Item item)
	{
		int amount = item.value();
		Settlement settle = rModel.getData().getSettlements().getSettlement(regiment.getSettleId());
		if (settle != null)
		{
			if (settle.getWarehouse().getItemList().getValue(item.ItemRef()) < item.value())
			{
				amount = settle.getWarehouse().getItemList().getValue(item.ItemRef());
			}
			double price = rModel.getData().getPriceList().getBasePrice(item.ItemRef());
			if (price < 0.1) 
			{ 
				price = 0.1; 
			}
			double cost = price * item.value() * 1.25;
			if (regiment.getBank().getKonto() > cost)
			{
				System.out.println("Raider Buy food "+item.ItemRef());
				settle.getWarehouse().withdrawItemValue(item.ItemRef(), amount);
				regiment.getWarehouse().depositItemValue(item.ItemRef(), amount);
				regiment.getBank().withdrawKonto(cost, "Raider", settle.getId());
				settle.getBank().depositKonto(cost, "Raider", settle.getId());
				return;
			}
		}
	}
	
	public boolean checkSupply(Regiment regiment)
	{
		int amount = regiment.getBarrack().getUnitList().size() * 7;
		int supply = 0;
		for (Item item : ConfigBasis.initFoodMaterial().values())
		{
			supply= supply + regiment.getWarehouse().getItemList().getValue(item.ItemRef());
		}
//		System.out.println("Raider Supply "+supply);
		if (supply >= amount)
		{
			return true;
		}
		return false;
	}
	
}
