package net.krglok.realms.Common;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.SettleType;

public class CommandParser
{
	private RealmsCommand[] commandList;

	public CommandParser(RealmsCommand[] commandList)
	{
		this.commandList = commandList;
	}
	

	public RealmsCommand getRealmsCommand(RealmsCommandType cmdType, String[] args)
	{
	
		if (args.length == 0)
		{
			return findSubCommand(cmdType, RealmsSubCommandType.NONE);
		}
		RealmsSubCommandType subCommand = RealmsSubCommandType.getRealmSubCommandType(args[0]);
		if (subCommand != RealmsSubCommandType.NONE)
		{
			return checkParameter(cmdType, subCommand, args); 
		}else
		{
			return findSubCommand(cmdType, RealmsSubCommandType.HELP);
		}
	}
	
	private RealmsCommand findSubCommand(RealmsCommandType cmdType, RealmsSubCommandType subCommand)
	{
		if (commandList.length > 0)
		{
			for (int i = 0; i < commandList.length; i++)
			{
				if ((commandList[i].subCommand() == subCommand)
					&& (commandList[i].command() == cmdType)
					)
				{
					commandList[i].getErrorMsg().clear();
					return commandList[i];
				}
			}
		}
		return null;
	}
	
	
	private RealmsCommand checkParameter (RealmsCommandType cmdType, RealmsSubCommandType subCommand, String[] args )
	{
//		args = decreaseArgs(args);
		RealmsCommand cmd = findSubCommand(cmdType, subCommand);
		// SubCommand NOT found
		if (cmd == null)
		{
			return  findSubCommand(cmdType, RealmsSubCommandType.HELP);
		}
		cmd.setParserError(false);		
		// Check Arguments of SubCommand
		if ((args.length-1) < cmd.getRequiredArgs() )
		{
			cmd.addErrorMsg(cmd.getDescription()[0]);
			cmd.addErrorMsg("Not enough Parameter given");
			cmd.addErrorMsg("The command requires "+cmd.getRequiredArgs());
			return  cmd; //findSubCommand(RealmsSubCommandType.HELP);
		} else
		{
			String[] paraTypes = cmd.getParaTypes();
			// in case of optional parameter null is possible
			if (paraTypes == null)
			{
//				cmd.addErrorMsg("No Parameter required");
				return cmd;
			}
			// check parameter definition against input parameter
			int len = 0;
			len = ((args.length-1) > paraTypes.length) ?   paraTypes.length : (args.length-1);
			// Array of required ArgumentTypes
			for (int i = 0; i <len; i++)
			{
				if (paraTypes[i].equalsIgnoreCase(int.class.getName()))
				{
					try
					{
						cmd.setPara(i, (int) Integer.valueOf(args[i+1]));
					} catch (Exception e)
					{
						cmd.setParserError(true);		
						cmd.addErrorMsg(cmd.getDescription()[0]);
						cmd.addErrorMsg("Wrong Datatype on "+(i+1)+":"+paraTypes[i]);
//							cmd.addErrorMsg("SetPara "+i+":"+paraTypes[i]);
					}
				}
				if (boolean.class.getName() == paraTypes[i])
				{
					try
					{
						cmd.setPara(i, Boolean.valueOf(args[i+1]));
					} catch (Exception e)
					{
						cmd.setParserError(true);		
						cmd.addErrorMsg(cmd.getDescription()[0]);
						cmd.addErrorMsg("Wrong Datatype on "+(i+1)+":"+paraTypes[i]);
//							cmd.addErrorMsg("SetPara "+i+":"+paraTypes[i]);
					}
				}
				if (double.class.getName() == paraTypes[i])
				{
					try
					{
						cmd.setPara(i, Double.valueOf(args[i+1]));
					} catch (Exception e)
					{
						cmd.setParserError(true);		
						cmd.addErrorMsg(cmd.getDescription()[0]);
						cmd.addErrorMsg("Wrong Datatype on "+(i+1)+":"+paraTypes[i]);
//							cmd.addErrorMsg("SetPara "+i+":"+paraTypes[i]);
					}
				}
				if (SettleType.class.getName() == paraTypes[i])
				{
					cmd.setParserError(true);		
					cmd.addErrorMsg(cmd.getDescription()[0]);
					cmd.addErrorMsg("Wrong Datatype SettleType "+(i+1)+":"+paraTypes[i]);
//						cmd.addErrorMsg("SetPara "+i+":"+paraTypes[i]);
					
				}
				if (BuildPlanType.class.getName() == paraTypes[i])
				{
					cmd.setParserError(true);		
					cmd.addErrorMsg(cmd.getDescription()[0]);
					cmd.addErrorMsg("Wrong Datatype BuildingType "+(i+1)+":"+paraTypes[i]);
//						cmd.addErrorMsg("SetPara "+i+":"+paraTypes[i]);
					
				}
				if (String.class.getName() == paraTypes[i])
				{
//						cmd.addErrorMsg("String Parmeter "+i+" : "+args[i+1]);
					try
					{
						cmd.setPara(i, args[i+1]);
					} catch (Exception e)
					{
						cmd.setParserError(true);		
						cmd.addErrorMsg(cmd.getDescription()[0]);
						cmd.addErrorMsg("Wrong Datatype on "+(i+1)+":"+paraTypes[i]);
//							cmd.addErrorMsg("SetPara "+i+":"+paraTypes[i]);
					}
				} else
				{
					// error Handling 
				}
			}
				
		}
		return cmd;
	}
	
}
