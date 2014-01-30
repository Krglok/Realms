package net.krglok.realms.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import net.krglok.realms.builder.BuildPlan;
import net.krglok.realms.builder.BuildPlanHome;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildPlanWheat;
import net.krglok.realms.builder.BuildPlanWoodCutter;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;

/**
 * the build manager realize the Controller and Manager of the building process
 * for the Settlement.
 * - buildup the standard hamlet
 * - buildup the standard buildings
 * - buildup the mine(s)
 * the manager can interact with the world and send commands and requests to other managers
 * the build manager receive a BuildingType and position to buildup a building
 * the building process is a  finite state machine 
 	NONE		, do nothing,
	PREBUILD	, startup the build process, do somthing before building
	READY,		, prebuild Ready, do something if nessary
	STARTED,	, build tstep by step
	POSTBUILD	, do something after building fullfill
	DONE,		, do 
	WAIT;

 * @author oduda
 *
 */
public class BuildManager
{
	private enum BuildStatus
	{

		NONE,
		PREBUILD,
		READY,
		STARTED,
		POSTBUILD,
		DONE,
		WAIT;
		
	}

//	private Settlement settle;
	private HashMap<String, BuildPlan> buildPlanList = new HashMap<String, BuildPlan>();
	private BuildPlan buildPlan;
	private LocationData buildLocation;
	private int h;
	private int r;
	private int c;
	private ItemList requiredItems;
	private ItemList buildStore;
	private int workerNeeded;
	private int workerInstalled;
	private ArrayList<ItemLocation> buildRequest;
	private ArrayList<Item> materialRequest;
	private BuildStatus bStatus = BuildStatus.NONE;
	char[][] signText = new char[4][15] ;
	private ArrayList<ItemLocation> cleanRequest;
	private ArrayList<ItemLocation> resultBlockRequest;


	public BuildManager()//Settlement settle)
	{
//		this.settle = settle;
		this.requiredItems = new ItemList();
		this.buildStore    = new ItemList();
		this.buildRequest  = new ArrayList<ItemLocation>();
		this.cleanRequest  = new ArrayList<ItemLocation>();
		this.resultBlockRequest = new ArrayList<ItemLocation>();
		this.materialRequest   = new ArrayList<Item>();
		bStatus = BuildStatus.NONE;
		initBuildPlans();
	}
	
	public String getStatus()
	{
		return bStatus.name();
	}
	
	private void initBuildPlans()
	{
		buildPlanList.put(BuildPlanType.HOME.name(), new BuildPlanHome());
		buildPlanList.put(BuildPlanType.WHEAT.name(), new BuildPlanWheat());
		buildPlanList.put(BuildPlanType.WOODCUTTER.name(), new BuildPlanWoodCutter());
	}
	
	/**
	 * Set new Building parameter and start building
	 * @param bType
	 * @param centerPos
	 * @return
	 */
	public boolean newBuild(BuildPlanType bType, LocationData centerPos)
	{
		System.out.println("new Build : "+bStatus.name());
		if (bStatus == BuildStatus.NONE)
		{
			h = 0;
			r = 0;
			c = -1;  // for iteration start
			String sPos = "";
			switch (bType)
			{
			case HOME :
				buildPlan = buildPlanList.get(bType.name());
				buildLocation = centerPos;
				signText[2] = bType.name().toCharArray();
				sPos = String.valueOf((int)(centerPos.getX()))+":"+String.valueOf((int)(centerPos.getZ()));
				signText[2] = sPos.toCharArray();
				bStatus = BuildStatus.PREBUILD;
				return true;
			case WHEAT :
				buildPlan = buildPlanList.get(bType.name());
				buildLocation = centerPos;
				signText[2] = bType.name().toCharArray();
				sPos = String.valueOf((int)(centerPos.getX()))+":"+String.valueOf((int)(centerPos.getZ()));
				signText[2] = sPos.toCharArray();
				bStatus = BuildStatus.PREBUILD;
				return true;
			case WOODCUTTER :
				buildPlan = buildPlanList.get(bType.name());
				buildLocation = centerPos;
				signText[2] = bType.name().toCharArray();
				sPos = String.valueOf((int)(centerPos.getX()))+":"+String.valueOf((int)(centerPos.getZ()));
				signText[2] = sPos.toCharArray();
				bStatus = BuildStatus.PREBUILD;
				return true;
			default:
				buildPlan = null;
				buildLocation = null;
				signText[2] = bType.name().toCharArray();
				sPos = String.valueOf((int)(centerPos.getX()))+":"+String.valueOf((int)(centerPos.getZ()));
				signText[2] = sPos.toCharArray();
				bStatus = BuildStatus.NONE;
			}
		}
		return false;
	}
	
	/**
	 * run on TickTask to build one block tick
	 */
	public void run(Settlement settle)
	{
//		if (buildPlan == null)
//		{
//			return;
//		}
		switch (bStatus)
		{
		case  PREBUILD: 
//			System.out.println("run : "+bStatus.name());
			preBuild(settle);
			break;
		case READY : 
//			System.out.println("run : "+bStatus.name());
			doReady();
			break;
		case STARTED : 
//			System.out.println("run : "+bStatus.name());
			addBuildRequest();
			break;
		case POSTBUILD : 
//			System.out.println("run : "+bStatus.name());
			doPostBuild();
			break;
		case DONE : 
//			System.out.println("run : "+bStatus.name());
			doDone();
			break;
		case WAIT : 
//			System.out.println("run : "+bStatus.name());
			doWait();
			break;
		default :
//			System.out.println(bStatus.name());
		
		}
		signText[1] = bStatus.toString().toCharArray(); 
	}
	
	/**
	 * do something before build the Building
	 * cleanUp Building area
	 */
	private void preBuild(Settlement settle)
	{
//		System.out.println("pre : "+bStatus.name());
		if (buildPlan == null)
		{
//			System.out.println("No Plan"+bStatus.name());
			return;
		}
		if (buildLocation.getWorld() == "")
		{
//			System.out.println("No Location"+bStatus.name());
			return;
		}
		
		
		int edge = buildPlan.getRadius() * 2 -1; 
		if (bStatus == BuildStatus.PREBUILD)
		{
			// make block position
			// generate Location for placing the block
			// the buildLocation define the center of the plan in x/z plane
			// the offset define the position of level 0 relative to surface 
			LocationData l = new LocationData(buildLocation.getWorld(), buildLocation.getX(), buildLocation.getY(),buildLocation.getZ());
			l.setX(l.getX()-buildPlan.getRadius()+1); 
			l.setY(l.getY()+buildPlan.getOffsetY()); 
			l.setZ(l.getZ()-buildPlan.getRadius()+1);
			 
			// Iterate thru BuildingPlan
			/// increment column for next step
//			System.out.println("h:"+h+" r: "+r+" c: "+c);
			c++;

			if (c < edge)
			{
			} else
			{
				r++;
				if (r < edge)
				{
//					System.out.println(".");
					c = 0;
				} else
				{
//					System.out.println("------");
					h++;
					r = 0;
					c = 0;
				}
			}
				
			if ((h < edge) && (r < edge) && c < edge )
			{
					if ((edge+buildPlan.getOffsetY()) >= 0)
					{
						l.setX(l.getX()+c); 
						l.setY(l.getY()+h); 
						l.setZ(l.getZ()+r);
						cleanRequest.add(new ItemLocation(Material.AIR ,l));
						if ((h+buildPlan.getOffsetY()) >= 0)
						{
							if (c == 0)
							{
								l.setX(l.getX()-1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setX(l.getX()+1);
							}
							if (c == 6)
							{
								l.setX(l.getX()+1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setX(l.getX()-1);
							}
							if (r == 0)
							{
								l.setZ(l.getZ()-1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setZ(l.getZ()+1);
								
							}
							if (r == 6)
							{
								l.setZ(l.getZ()+1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setZ(l.getZ()+1);
							}
							if ((c == 0) && (r == 0))
							{
								l.setZ(l.getZ()-1);
								l.setX(l.getX()-1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setX(l.getX()+1);
								l.setZ(l.getZ()+1);
							}
							if ((c == 0) && (r == 6))
							{
								l.setZ(l.getZ()+1);
								l.setX(l.getX()-1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setX(l.getX()+1);
								l.setZ(l.getZ()-1);
							}
							if ((c == 6) && (r == 0))
							{
								l.setZ(l.getZ()-1);
								l.setX(l.getX()+1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setX(l.getX()+1);
								l.setZ(l.getZ()-1);
							}
							if ((c == 6) && (r == 6))
							{
								l.setZ(l.getZ()+1);
								l.setX(l.getX()+1);
								cleanRequest.add(new ItemLocation(Material.AIR ,l));
								l.setX(l.getX()-1);
								l.setZ(l.getZ()-1);
							}
						}
					}
			}
		}
		if (h >= edge)
		{
			h = 0;
			r = 0;
			c = -1;  // for iteration start
			bStatus = BuildStatus.READY;
			System.out.println("deposit Warehouse");
			for (ItemLocation iLoc   : resultBlockRequest)
			{
				if (iLoc.itemRef() != Material.AIR)
				{
					settle.getWarehouse().depositItemValue(iLoc.itemRef().name(), 1);
				}
			}
		} 
//		System.out.println((""+h+":"+r+":"+c)+" >");
		
	}
	
	/**
	 * go to BuildStatus.STARTED if readiness is given
	 * 
	 */
	private void doReady()
	{
//		System.out.println("ready : "+bStatus.name());
		// get all blocks that are not AIR from buildpPlan position in the world
		// and put them into the warehouse
		bStatus = BuildStatus.STARTED;
	}
	
	/**
	 *  get block and location from buildplan
	 * 	generate Location for placing the block
	 *	the buildLocation define the center of the plan in x/z plane
	 *	the offset define the position of level 0 relative to surface 
     *  go to BuildStatus.DONE if all blocks are placed
     *  go to BuildStatus.WAIT if required items not found
     *  
	 * @param itemRef
	 * @param x
	 * @param y
	 * @param z
	 */
	private void addBuildRequest()
	{
		int edge = buildPlan.getRadius() * 2 -1; 
		if (bStatus == BuildStatus.STARTED)
		{
			// make block position
			// generate Location for placing the block
			// the buildLocation define the center of the plan in x/z plane
			// the offset define the position of level 0 relative to surface 
			LocationData l = new LocationData(buildLocation.getWorld(), buildLocation.getX(), buildLocation.getY(),buildLocation.getZ());
			l.setX(l.getX()-buildPlan.getRadius()+1); 
			l.setY(l.getY()+buildPlan.getOffsetY()); 
			l.setZ(l.getZ()-buildPlan.getRadius()+1);
			 
			// Iterate thru BuildingPlan
			/// increment column for next step
//			System.out.println("h:"+h+" r: "+r+" c: "+c);
			c++;

			if (c < edge)
			{
			} else
			{
				r++;
				if (r < edge)
				{
//					System.out.println(".");
					c = 0;
				} else
				{
//					System.out.println("------");
					h++;
					r = 0;
					c = 0;
				}
			}
				
			if ((h < edge) && (r < edge) && c < edge )
			{
				if (ConfigBasis.getPlanMaterial(buildPlan.getCube()[h][r][c]) != Material.AIR)
				{						
//					System.out.print((""+h+":"+r+":"+c)+" > "+ConfigBasis.getPlanMaterial(buildPlan.getCube()[h][r][c]) );
					l.setX(l.getX()+c); 
					l.setY(l.getY()+h); 
					l.setZ(l.getZ()+r);
					buildRequest.add(new ItemLocation(ConfigBasis.getPlanMaterial(buildPlan.getCube()[h][r][c]) ,l));
				}
			}
		}
		if (h >= edge)
		{
			bStatus = BuildStatus.POSTBUILD;
		} 
//		System.out.println((""+h+":"+r+":"+c)+" >");
	}
	
	private void doPostBuild()
	{
		System.out.println(bStatus.name());
		// aufraeumen !!!
		bStatus = BuildStatus.DONE;
	}
	
	/**
	 * go to BuildStatus.NONE
	 */
	private void doDone()
	{
		System.out.println("doDone : "+bStatus.name());
		buildLocation = null;
		buildPlan = null;
		h = 0;
		r = 0;
		c = 0;
		bStatus = BuildStatus.NONE;
		System.out.println("FullFill : "+bStatus.name());
	}
	
	/**
	 * check for fullfill required conditions
	 * go to BuildStatus.STARTED
	 */
	private void doWait()
	{
		System.out.println(bStatus.name());
		if (bStatus == BuildStatus.WAIT)
		{
			if (checkRequired() == true)
			{
				bStatus = BuildStatus.STARTED;
			}
		}
	}
	
	/**
	 * check the required items for fullfilled
	 * @return
	 */
	private boolean checkRequired()
	{
		for (Item item : requiredItems.values())
		{
			if (item.value() > 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public ArrayList<ItemLocation> getBuildRequest()
	{
		return buildRequest;
	}


	public ArrayList<Item> getItemRequest()
	{
		return materialRequest;
	}
	
	public HashMap<String, BuildPlan> getBuildPlanList()
	{
		return buildPlanList;
	}

	public void setBuildPlanList(HashMap<String, BuildPlan> buildPlanList)
	{
		this.buildPlanList = buildPlanList;
	}

	public BuildPlan getActualBuild()
	{
		return buildPlan;
	}

	public void setActualBuild(BuildPlan actualBuild)
	{
		this.buildPlan = actualBuild;
	}

	public ItemList getRequiredItems()
	{
		return requiredItems;
	}

	public void setRequiredItems(ItemList requiredItems)
	{
		this.requiredItems = requiredItems;
	}



	public ItemList getBuildStore()
	{
		return buildStore;
	}



	public void setBuildStore(ItemList buildStore)
	{
		this.buildStore = buildStore;
	}



	public int getWorkerNeeded()
	{
		return workerNeeded;
	}



	public void setWorkerNeeded(int workerNeeded)
	{
		this.workerNeeded = workerNeeded;
	}



	public int getWorkerInstalled()
	{
		return workerInstalled;
	}



	public void setWorkerInstalled(int workerInstalled)
	{
		this.workerInstalled = workerInstalled;
	}






	public LocationData getActualPosition()
	{
		return buildLocation;
	}


	public void setActualPosition(LocationData actualPosition)
	{
		this.buildLocation = actualPosition;
	}

	
	public char[][] getSignText()
	{
		return signText;
	}

	
	public char[] getSignText(int index)
	{
		return signText[index];
	}

	
	public void setSignText(char[][] signText)
	{
		this.signText = signText;
	}

	
	public void setSignText(int index, char[] signText)
	{
		this.signText[index] = signText;
	}

	/**
	 * @return the getBlockRequest
	 */
	public ArrayList<ItemLocation> resultBlockRequest()
	{
		return resultBlockRequest;
	}

	/**
	 * @return the cleanRequest
	 */
	public ArrayList<ItemLocation> getCleanRequest()
	{
		return cleanRequest;
	}


	
	
}
