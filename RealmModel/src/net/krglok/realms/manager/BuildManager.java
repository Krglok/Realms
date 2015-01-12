package net.krglok.realms.manager;

import java.util.ArrayList;
import java.util.HashMap;

import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;
import net.krglok.realms.builder.BuildPlan;
import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.builder.BuildStatus;
import net.krglok.realms.builder.ItemListLocation;
import net.krglok.realms.builder.ItemLocation;
import net.krglok.realms.builder.RegionLocation;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.Warehouse;
import net.krglok.realms.model.RealmModel;

import org.bukkit.Material;

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
//	public enum BuildStatus
//	{
//
//		NONE,
//		PREBUILD,
//		READY,
//		STARTED,
//		POSTBUILD,
//		DONE,
//		WAIT;
//		
//	}

//	private HashMap<String, BuildPlan> buildPlanList = new HashMap<String, BuildPlan>();
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
	private ArrayList<RegionLocation> regionRequest;
	private ArrayList<ItemListLocation> chestSetRequest;
	private String ownerName;

	private int timeout;

	public BuildManager()
	{
		this.requiredItems = new ItemList();
		this.buildStore    = new ItemList();
		this.buildRequest  = new ArrayList<ItemLocation>();
		this.cleanRequest  = new ArrayList<ItemLocation>();
		this.resultBlockRequest = new ArrayList<ItemLocation>();
		this.materialRequest   = new ArrayList<Item>();
		this.regionRequest = new ArrayList<RegionLocation>();
		this.chestSetRequest = new ArrayList<ItemListLocation>();
		bStatus = BuildStatus.NONE;
		this.ownerName = ConfigBasis.NPC_0;
	}
	
	public BuildStatus getStatus()
	{
		return bStatus;
	}
	
	
	public static ItemList makeMaterialList(BuildPlan buildPlan)
	{
		ItemList items = new ItemList();
		for (int h = 0; h < buildPlan.getEdge(); h++)
		{
			for (int r = 0; r < buildPlan.getEdge(); r++)
			{
				for (int c = 0; c < buildPlan.getEdge(); c++)
				{
					Material mat = ConfigBasis.getPlanMaterial(buildPlan.getCube()[h][r][c]);
					if (mat != Material.AIR)
					{
						items.depositItem(mat.name(), 1);
					}
				}
			}
		}
		
		
		return items;
	}
	
	
	
	/**
	 * Set new Building parameter and start building
	 * @param bType
	 * @param centerPos
	 * @return true if build is started
	 */
	public boolean newBuild(BuildPlan bPlan, LocationData centerPos, String ownerName)
	{
//		System.out.println("new Build : "+":"+bType.getBuildingType().name()+":"+centerPos.getX()+":"+centerPos.getY()+":"+centerPos.getZ());
//		if (bStatus == BuildStatus.NONE)
		{
			h = 0;
			r = 0;
			c = -1;  // for iteration start
			String sPos = "";
			this.buildPlan = bPlan;
			this.buildLocation = centerPos;
			signText[2] = bPlan.getBuildingType().name().toCharArray();
			sPos = String.valueOf((int)(centerPos.getX()))+":"+String.valueOf((int)(centerPos.getZ()));
			signText[2] = sPos.toCharArray();
			bStatus = BuildStatus.PREBUILD;
			this.ownerName = ownerName;
			if (buildPlan == null)
			{
				bStatus = BuildStatus.NONE;
				System.out.println("Build Cancelled "+bStatus.name()+":"+bPlan.getBuildingType().name());
			}
		}
		return false;
	}
	

	public void runClean(RealmModel rModel, Warehouse warehouse, Settlement settle)
	{
		switch (bStatus)
		{
		case  PREBUILD: 
//			System.out.println("run : "+bStatus.name());
			preBuild(warehouse);
			break;
		case READY : 
//			System.out.println("run : "+bStatus.name());
			bStatus = BuildStatus.DONE;
			
			break;
		default :
//			System.out.println(bStatus.name());
			bStatus = BuildStatus.NONE;
		
		}
		signText[1] = bStatus.toString().toCharArray(); 
	}
	
	
	/**
	 * run on TickTask to build one block tick
	 * if owner is not a Settlement set null to parameter !!! 
	 * @param rModel
	 * @param warehouse
	 * @param settle
	 */
	public void run(RealmModel rModel, Warehouse warehouse, Settlement settle)
	{
//		if (buildPlan == null)
//		{
//			return;
//		}
		switch (bStatus)
		{
		case  PREBUILD: 
//			System.out.println("run : "+bStatus.name());
			preBuild(warehouse);
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
			doPostBuild(rModel);
			break;
		case DONE : 
//			System.out.println("run : "+bStatus.name());
			doDone(rModel, settle);
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

	private void doCleanStep()
	{
		int radius = buildPlan.getRadius(); 
		int edge =  radius * 2 -1;
		if (h+buildPlan.getOffsetY() >= 0 )
		{
			radius = radius + 1;
			edge =  radius * 2 -1;
		}
		// make block position
		// generate Location for placing the block
		// the buildLocation define the center of the plan in x/z plane
		// the offset define the position of level 0 relative to surface 
		LocationData l = new LocationData(buildLocation.getWorld(), buildLocation.getX(), buildLocation.getY(),buildLocation.getZ());
		l.setX(l.getX()-radius+1); 
		l.setY(l.getY()+buildPlan.getOffsetY()); 
		l.setZ(l.getZ()-radius);
		 
		// Iterate thru BuildingPlan
		/// increment column for next step
//		System.out.println("h:"+h+" r: "+r+" c: "+c);
		c++;

		if (c < edge)
		{
		} else
		{
			r++;
			if (r < edge)
			{
//				System.out.println(".");
				c = 0;
			} else
			{
//				System.out.println("------");
				h++;
				r = 0;
				c = 0;
			}
		}
			
		if ((h < edge) && (r < edge) && c < edge )
		{
//				if ((edge+buildPlan.getOffsetY()) >= 0)
				{
					l.setX(l.getX()+c); 
					l.setY(l.getY()+h); 
					l.setZ(l.getZ()+r);
					cleanRequest.add(new ItemLocation(Material.AIR ,l));
				}
		}
		
	}
	
	/**
	 * do something before build the Building
	 * cleanUp Building area
	 */
	private void preBuild(Warehouse warehouse)
	{
//		System.out.println("pre : "+bStatus.name());
		if (buildPlan == null)
		{
			System.out.println("No Plan"+bStatus.name());
			return;
		}
		if (buildLocation.getWorld() == "")
		{
			System.out.println("BuildManager No World "+bStatus.name());
			return;
		}
		
		
		if (bStatus == BuildStatus.PREBUILD)
		{
//			System.out.println("BuildManager Clean "+h);
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			// buildfaster for test only !!
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			// buildfaster for test only !!
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			// buildfaster for test only !!
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			doCleanStep();
			// buildfaster for test only !!
			
		}
		// ein level oberhalb mit abraeumen
		int edge = (buildPlan.getRadius()+1) * 2 -1; 
		if (h >= edge)
		{
			h = 0;
			r = 0;
			c = -1;  // for iteration start
			bStatus = BuildStatus.READY;
//			System.out.println("BuildManager deposit Warehouse");
			for (ItemLocation iLoc   : resultBlockRequest)
			{
				if (iLoc.itemRef() != Material.AIR)
				{
					warehouse.depositItemValue(iLoc.itemRef().name(), 1);
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
	 * make a single step in build process
	 * @param l
	 */
	private void doAddStep()
	{
		int edge = buildPlan.getRadius() * 2 -1; 
		LocationData l = new LocationData(buildLocation.getWorld(), buildLocation.getX(), buildLocation.getY(),buildLocation.getZ());
		l.setX(l.getX()-buildPlan.getRadius()+1); 
		l.setY(l.getY()+buildPlan.getOffsetY()); 
		l.setZ(l.getZ()-buildPlan.getRadius());
		// make block position
		// generate Location for placing the block
		// the buildLocation define the center of the plan in x/z plane
		// the offset define the position of level 0 relative to surface 
		 
		// Iterate thru BuildingPlan
		/// increment column for next step
		
		c++;

		if (c < edge)
		{
		} else
		{
			r++;
			if (r < edge)
			{
//				System.out.println(".");
				c = 0;
			} else
			{
//				System.out.println("------");
				h++;
				r = 0;
				c = 0;
			}
		}
			
		if ((h < edge) && (r < edge) && c < edge )
		{
			if (ConfigBasis.getPlanMaterial(buildPlan.getCube()[h][r][c]) != Material.AIR)
			{						
//				System.out.print((""+h+":"+r+":"+c)+" > "+ConfigBasis.getPlanMaterial(buildPlan.getCube()[h][r][c]) );
				l.setX(l.getX()+c); 
				l.setY(l.getY()+h); 
				l.setZ(l.getZ()+r);
				buildRequest.add(new ItemLocation(ConfigBasis.getPlanMaterial(buildPlan.getCube()[h][r][c]) ,l));
			}
		}

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
		if (cleanRequest.isEmpty())
		{
			int edge = buildPlan.getRadius() * 2 -1; 
			if (bStatus == BuildStatus.STARTED)
			{
//				System.out.println("h:"+h+" r: "+r+" c: "+c);
				doAddStep();
				doAddStep();
				doAddStep();
				doAddStep();
				doAddStep();
				doAddStep();
				doAddStep();
				// buildfaster for test only !!
//				doAddStep();
//				doAddStep();
//				doAddStep();
//				doAddStep();
//				doAddStep();
//				doAddStep();
//				doAddStep();
				// buildfaster for test only !!
			}
			if (h >= edge)
			{
				bStatus = BuildStatus.POSTBUILD;
			}
		} else
		{
//			System.out.println("Wait on Clean ready");
		}
//		System.out.println((""+h+":"+r+":"+c)+" >");
	}
	
	private void doPostBuild(RealmModel rModel)
	{
//		System.out.println(bStatus.name());
		// aufraeumen !!!
		// region erzeugen 
		if (buildRequest.isEmpty() )
		{
			if (buildPlan.getBuildingType().getValue() >= 50)
			{
				String regionType = rModel.getConfig().getRegionType(buildPlan.getBuildingType());
				if (regionType != "")
				{
					LocationData position = new LocationData(
							buildLocation.getWorld(), 
							buildLocation.getX(), 
							buildLocation.getY()+buildPlan.getOffsetY()+buildPlan.getRadius()-1, 
							buildLocation.getZ()-1
							);
					String owner = this.ownerName;
					regionRequest.add(new RegionLocation(regionType, position, owner,""));
				}
			}
			bStatus = BuildStatus.DONE;
			timeout = 0;
		}  else
		{
			System.out.println("Wait on Build ready");
		}
	}
	
	/**
	 * go to BuildStatus.NONE
	 */
	private void doDone(RealmModel rModel, Settlement settle)
	{
		String regionType = rModel.getConfig().getRegionType(buildPlan.getBuildingType());
		if (regionRequest.isEmpty())
		{
			if ((buildPlan.getBuildingType().getValue() > 50))
			{
				LocationData position = new LocationData(
						buildLocation.getWorld(), 
						buildLocation.getX(), 
						buildLocation.getY()+buildPlan.getOffsetY()+buildPlan.getRadius()-1, 
						buildLocation.getZ()-1
						);
				System.out.println("Chest Pos X:"+position.getX()+" Y:"+position.getY()+position.getY()+" Z:"+position.getZ());
				ItemList reagents = rModel.getServer().getRegionReagents(regionType);
				chestSetRequest.add(new ItemListLocation(reagents, position));

				if (settle != null)
				{
					Region region = rModel.getServer().getRegionAt(position);
					if (region != null)
					{
						System.out.println("Settle Building added : "+buildPlan.getBuildingType()+":"+region.getID());
						Building building = new Building(buildPlan.getBuildingType());
						building.setHsRegion(region.getID());
						building.setSettleId(settle.getId());
						rModel.getBuildings().addBuilding(building);
						rModel.getData().writeBuilding(building);
						settle.setBuildingList(rModel.getBuildings().getSubList(settle.getId()));
						System.out.println("Building added : "+buildPlan.getBuildingType()+":"+building.getId());
					} else
					{
						System.out.println("New region NOT found: ");
					}
				} else
				{
					System.out.println("Settlement  NOT found: ");
				}
			}			
			
//			System.out.println("doDone added : "+buildPlan.getBuildingType());
//			buildLocation = null;
			buildPlan = null;
			h = 0;
			r = 0;
			c = 0;
			bStatus = BuildStatus.NONE;
//			System.out.println("FullFill : "+bStatus.name());
		} else
		{
			System.out.println("Wait on regionRequest ready");
			timeout++;
			if (timeout > 100)
			{
				System.out.println("Timeout on regionRequest! aborded!");
				bStatus = BuildStatus.NONE;
			}
		}
	}
	
	/**
	 * check for fullfill required conditions
	 * go to BuildStatus.STARTED
	 */
	private void doWait()
	{
//		System.out.println(bStatus.name());
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
	
	public int getH()
	{
		return h;
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
		return null;
	}

	public void setBuildPlanList(HashMap<String, BuildPlan> buildPlanList)
	{
//		this.buildPlanList = buildPlanList;
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

	public ArrayList<RegionLocation> getRegionRequest()
	{
		return regionRequest;
	}

	public ArrayList<ItemListLocation> getChestSetRequest()
	{
		return chestSetRequest;
	}

	
	
}
