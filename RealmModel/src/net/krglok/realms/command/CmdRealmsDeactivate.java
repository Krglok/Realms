package net.krglok.realms.command;

import java.util.ArrayList;

import net.krglok.realms.Realms;
import net.krglok.realms.model.ModelStatus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CmdRealmsDeactivate extends RealmsCommand
{

	public CmdRealmsDeactivate( )
	{
		super(RealmsCommandType.REALMS, RealmsSubCommandType.DEACTIVATE);
		description = new String[] {
				ChatColor.YELLOW+"/realms DEACTIVATE ",
		    	"Set the RealmsModel to Disable, stop the RealmModel.  ",
		    	"Only when enabled, the Realm Model is stoped  ",
		    	"in any other status the RealmModel is busy  ",
		    	"TickTask stop  ",
		    	"  "
			};
			requiredArgs = 0;
	}

	@Override
	public void setPara(int index, String value)
	{

	}

	@Override
	public void setPara(int index, int value)
	{

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
		return null;
	}

	@Override
	public void execute(Realms plugin, CommandSender sender)
	{
		ArrayList<String> msg = new ArrayList<String>();
		plugin.getRealmModel().OnDisable();
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_DISABLED)
		{
			msg.add("[Realm Model] Disabled");
			msg.add(plugin.getRealmModel().getModelName()+" Vers.: "+plugin.getRealmModel().getModelVersion());
			msg.add("All Task are not stoped !");
		} else
		{
			msg.add("[Realm Model] NOT Disabled");
			msg.add("Something unknown is wrong or the model is busy  :(");
			plugin.getLog().info("[Realm Model] NOT Disabled. Something unknown is wrong "+plugin.getRealmModel().getModelStatus());
		}
		plugin.getMessageData().printPage(sender, msg, 1);

	}

	@Override
	public boolean canExecute(Realms plugin, CommandSender sender)
	{
		if (sender.isOp() == false)
		{
			errorMsg.add("Only for Ops and Admins !  ");
			return false;
		}
		if (plugin.getRealmModel().getModelStatus() == ModelStatus.MODEL_ENABLED)
		{
			return true;
		}
		errorMsg.add("The Model not enabled OR busy !  ");
		return false;
	}

}
