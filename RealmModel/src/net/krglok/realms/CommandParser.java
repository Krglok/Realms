package net.krglok.realms;

import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.SettleType;

public class CommandParser
{
	private RealmsCommand[] commandList;

	public CommandParser(RealmsCommand[] commandList)
	{
		this.commandList = commandList;
	}
	

	public RealmsCommand getRealmsCommand(String[] args)
	{
	
		if (args.length == 0)
		{
			return findSubCommand(RealmsSubCommandType.NONE);
		}
		RealmsSubCommandType subCommand = RealmsSubCommandType.getRealmSubCommandType(args[0]);
		if (subCommand != RealmsSubCommandType.NONE)
		{
			return checkParameter(subCommand, args);
		}else
		{
			return findSubCommand(RealmsSubCommandType.HELP);
		}
	}
	
	private RealmsCommand findSubCommand(RealmsSubCommandType subCommand)
	{
		if (commandList.length > 0)
		{
			for (int i = 0; i < commandList.length; i++)
			{
				if (commandList[i].subCommand() == subCommand)
				{
					commandList[i].getErrorMsg().clear();
					return commandList[i];
				}
			}
		}
		return null;
	}
	
	
	private RealmsCommand checkParameter (RealmsSubCommandType subCommand, String[] args )
	{
//		args = decreaseArgs(args);
		RealmsCommand cmd = findSubCommand(subCommand);
		// SubCommand NOT found
		if (cmd == null)
		{
			return  findSubCommand(RealmsSubCommandType.HELP);
		}
//		// SubCommand NO arguments
//		if (cmd.getRequiredArgs() == 0 )
//		{
//			return cmd;
//		}
		// Check Arguments of SubCommand
		cmd.addErrorMsg("Arguments "+(args.length-1)+" < "+cmd.getRequiredArgs());
		if ((args.length-1) < cmd.getRequiredArgs() )
		{
			return  findSubCommand(RealmsSubCommandType.HELP);
		} else
		{
			String[] paraTypes = cmd.getParaTypes();
			int len = 0;
			if ((args.length-1) > paraTypes.length)
			{
				len = paraTypes.length;
			} else
			{
				len = (args.length-1);
			}
			cmd.addErrorMsg("Arguments "+(args.length-1));
			// Array of required ArgumentTypes
			for (int i = 0; i <len; i++)
			{
				cmd.addErrorMsg("SetPara "+i+":"+paraTypes[i]);
				if (paraTypes[i].equalsIgnoreCase(int.class.getName()))
				{
					cmd.setPara(i, (int) Integer.valueOf(args[i+1]));
				}
				if (boolean.class.getName() == paraTypes[i])
				{
					cmd.setPara(i, Boolean.valueOf(args[i+1]));
				}
				if (double.class.getName() == paraTypes[i])
				{
					cmd.setPara(i, Double.valueOf(args[i+1]));
				}
				if (SettleType.class.getName() == paraTypes[i])
				{
					
				}
				if (BuildingType.class.getName() == paraTypes[i])
				{
					
				}
				if (String.class.getName() == paraTypes[i])
				{
					cmd.addErrorMsg("String Parmeter "+i+" : "+args[i+1]);
					cmd.setPara(i, args[i+1]);
				} else
				{
					// error Handling 
				}
			}
		}
		return cmd;
	}
	
}
