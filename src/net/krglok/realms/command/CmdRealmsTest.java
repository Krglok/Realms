package net.krglok.realms.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.krglok.realms.Realms;
import net.krglok.realms.Common.LocationData;
import net.krglok.realms.Common.aRealmsCommand;
import net.krglok.realms.Common.RealmsCommandType;
import net.krglok.realms.Common.RealmsSubCommandType;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.manager.CampPosition;
import net.krglok.realms.manager.HeightAnalysis;
import net.krglok.realms.manager.PositionFace;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.unit.Regiment;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class CmdRealmsTest extends aRealmsCommand
{
	private int page; 
	private String faceName;
	private PositionFace face;
	private int settleId;
	
	public CmdRealmsTest( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.TEST);
		description = new String[] {
				ChatColor.YELLOW+"/realms TEST [page] {settleId} {face} ",
		    	"Show high analysis for position  ",
		    	"finding  "
			};
			requiredArgs = 1;
			this.page = 0;
			this.settleId = 0;
			this.faceName = "";
			this.face = PositionFace.NORTH;
	}

	@Override
	public void setPara(int index, String value)
	{
		switch(index)
		{
		case 2: faceName = value;
		default:
			break;
		}
	}

	@Override
	public void setPara(int index, int value)
	{
		switch (index)
		{
		case 0 :
			page = value;
			break;
		case 1:
			settleId = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, boolean value)
	{

	}

	@Override
	public void setPara(int index, double value)
	{

	}

	@Override
	public String[] getParaTypes()
	{
		return new String[] {int.class.getName(), int.class.getName(), String.class.getName()  };
	}

	private boolean isGround(Block block)
	{
		switch(block.getType())
		{
		case LOG : return false;
		case LOG_2 : return false;
		case LEAVES : return false;					
		case LEAVES_2 : return false;
		case AIR : return false;
		case WATER : return false;
		case STATIONARY_WATER: return false;
		case LAVA: return false;
		case STATIONARY_LAVA: return false;
		default:
			return true;
		}
	}
	
	private BlockFace[] getFaceList()
	{
		BlockFace[] checkFaces = new BlockFace[] 
				{BlockFace.NORTH, 
				BlockFace.NORTH_EAST, 
				BlockFace.NORTH_WEST, 
				BlockFace.EAST, 
				BlockFace.SOUTH_EAST, 
				BlockFace.SOUTH_WEST, 
				BlockFace.SOUTH,
				BlockFace.WEST
				};
		return checkFaces;
	}
	
	private Block scanNeibour(Block block)
	{
		BlockFace[] faces = getFaceList();
		for (BlockFace face : faces)
		{
			Block faceBlock = block.getRelative(face);
			Block near = block.getWorld().getHighestBlockAt(faceBlock.getLocation());
//			System.out.println(face.name()+":"+near.getType().name());
			if (isGround(near) == true)
			{
				return near;
			}
		}
		return null;
	}
	
	private Location findGround(Location position)
	{
		Location groundPos = position.clone();
		Block block = groundPos.getBlock().getRelative(BlockFace.DOWN);
		if (isGround(block))
		{
			System.out.println("Ground found");
			return block.getLocation();
		}
		if (scanNeibour(block) == null)
		{
			for (BlockFace face : getFaceList())
			{
				Block near = scanNeibour(block.getRelative(face));
				if (near != null)
				{
					System.out.println("Ground found");
					return near.getLocation(); 
				}
			}
		}
		System.out.println("Ground NOT found");
		return groundPos;
	}

//	private boolean findSettleCamp(RealmModel rModel, int settleId, PositionFace face)
//	{
//		for (CampPosition campPos : rModel.getData().getCampList().values())
//		{
//			if (campPos.getSettleId() == settleId)
//			{
//				if (campPos.getFace() == face)
//				{
//					return true;
//				}
//			}
//		}
//		return false;
//	}


	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
    	ArrayList<String> msg = new ArrayList<String>();

		msg.add(ChatColor.RED+"Realms Test ");
		
		face = PositionFace.valueOf(faceName);
		CampPosition campPos = null;
		Regiment regiment = plugin.getData().getRegiments().getRegiment(1);
		if (regiment.getRaiderManager().checkSettleCamp(plugin.getRealmModel(), settleId, face) == false)
		{
			campPos = regiment.getRaiderManager().getNewScanPosition(plugin.getRealmModel(), regiment,settleId, face);
			plugin.doNewCampScan( campPos);
			msg.add("CampPosition New ");
		} else
		{
			campPos = plugin.getData().getCampList().getCampPosition(settleId, face);
			msg.add("CampPosition found ");
		}
		Location location = plugin.makeLocation(campPos.getPosition());
		Player player = (Player) sender;
		player.teleport(location);
		Settlement settle = plugin.getData().getSettlements().getSettlement(settleId);
		msg.add("CampPos : "+settleId+":"+settle.getName()+":"+campPos.getFace().name());
		msg.add("Avaerage: "+campPos.getAnalysis().getAverage()+"  Start: "+campPos.getAnalysis().getStart());
		msg.add("Min: "+campPos.getAnalysis().getMin()+" Max: "+campPos.getAnalysis().getMax()+" Diff: "+(campPos.getAnalysis().getMax()-campPos.getAnalysis().getMin()));
		msg.add("Coverage: "+campPos.getAnalysis().scale()+"   Valid: "+campPos.getAnalysis().isValid());
		msg.add("Position: "+campPos.getPosition().toString());
		plugin.getMessageData().printPage(sender, msg, 1);
		this.settleId = 0;
		this.faceName = "";
		this.face = PositionFace.NORTH;
	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (plugin.getData().getSettlements().getSettlement(settleId) == null)
		{
			errorMsg.add(ChatColor.RED+"Wrong Settlement : "+settleId);
			return false;
		}
			
		
		if (PositionFace.contain(faceName) == false)
		{
			errorMsg.add(ChatColor.RED+"Wrong PositionFace: "+faceName);
			errorMsg.add(PositionFace.valueHelp()); 
			return false;
		}
		
		return true;
	}

}
