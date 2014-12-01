package net.krglok.realms.command;

import net.krglok.realms.Realms;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdSettleDeleteBuild extends RealmsCommand {
	
	private int settleId;


	public CmdSettleDeleteBuild() {
		super(RealmsCommandType.SETTLE, RealmsSubCommandType.DELETE);
		description = new String[] {
				ChatColor.YELLOW+"/settle DELETE [SettleID] ",
				"Delete the first BuildCommand of a Settlement  ",
				"You must  be the Owner of the Settlement ! ",
		    	"or an OP  ",
		};
		this.settleId = 0;
		requiredArgs = 1;
	}

	@Override
	public void setPara(int index, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPara(int index, int value) {
		switch (index)
		{
		case 0 :
			settleId = value;
			break;
		default:
			break;
		}

	}

	@Override
	public void setPara(int index, boolean value) {

	}

	@Override
	public void setPara(int index, double value) {

	}

	@Override
	public String[] getParaTypes() {
		return new String[] {int.class.getName() };
	}

	@Override
	public void execute(Realms plugin, CommandSender sender) 
	{
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add("The Model is busy or not enaled !");
			errorMsg.add("");
			return;
		}
		if ( plugin.getRealmModel().getSettlements().getSettlement(this.settleId).settleManager().getCmdBuilder().size() > 0 )
		{
			errorMsg.add("Build command deleted : "+plugin.getRealmModel().getSettlements().getSettlement(this.settleId).settleManager().getCmdBuilder().get(0).getbType());
			errorMsg.add("");
			plugin.getRealmModel().getSettlements().getSettlement(this.settleId).settleManager().getCmdBuilder().remove(0);
			return ;
		} else
		{
			errorMsg.add("There is no command in queue !");
			errorMsg.add("");
			return ;
			
		}

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender) {
		if (plugin.getRealmModel().getModelStatus() != ModelStatus.MODEL_ENABLED)
		{
			errorMsg.add("The Model is busy or not enaled !");
			errorMsg.add("");
			return false;
		}
		if ( plugin.getRealmModel().getSettlements().getSettlement(this.settleId).settleManager().getCmdBuilder().size() < 1 )
		{
			errorMsg.add("There is no command in queue !");
			errorMsg.add("");
			return false;
		}
		if (sender.isOp())
		{
			return true;
		}

		return isSettleOwner(plugin, sender, settleId);
	}

}
