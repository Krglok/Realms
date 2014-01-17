package net.krglok.realms;

import net.krglok.realms.core.BuildingType;
import net.krglok.realms.core.SettleType;


public class RealmCommand 
{
	private RealmsCommandType command;
	private String[] typs;
	private String[] args;
	private String description;
	
	
	public RealmCommand()
	{
		command = RealmsCommandType.NONE;
		typs  = null; 
		args  = null;
		description = "None";
	}

//	public ModelCommand(ModelCommandType CommandType)
//	{
//		this.command = CommandType;
//		String[] argTyps = getArgTyps(command);
//		description =  argTyps[0];
//		typs = null;
//		args = null;
//	}
//	
//	public ModelCommand(ModelCommandType CommandType, String arg0)
//	{
//		this.command = CommandType;
//		String[] argTyps = getArgTyps(command);
//		description =  argTyps[0];
//		typs = new String[] {argTyps[1]};
//		args = new String[] {arg0};
//	}
//	
//	public ModelCommand(ModelCommandType CommandType, String arg0, String arg1)
//	{
//		this.command = CommandType;
//		String[] argTyps = getArgTyps(command);
//		description =  argTyps[0];
//		typs = new String[] {argTyps[1], argTyps[2]};
//		args = new String[] {arg0, arg1};
//	}
//	
//	public ModelCommand(ModelCommandType CommandType, String arg0, String arg1, String arg2)
//	{
//		this.command = CommandType;
//		String[] argTyps = getArgTyps(command);
//		description =  argTyps[0];
//		typs = new String[] {argTyps[1], argTyps[2], argTyps[3]};
//		args = new String[] {arg0, arg1, arg2};
//	}
	
	
	public RealmsCommandType command()
	{
		return this.command;
	}
	
	public String  getArg(int index)
	{
		if (args.length > index)
		{
			return this.args[index];
		} else
		{
			return "";
		}
	}
	
	public String[] args()
	{
		return args;
	}
	
	public String[] typs()
	{
		return typs;
	}
	
	
	public static String getDescription(RealmsCommandType command)
	{
		String [] argTyps = getArgTyps(command);
		return argTyps[0];
	}
	
	public static String splitDesc(String[] argTyps)
	{
		return argTyps[0];
	}
	
	
	/**
	 * liefert einen kombinierten String
	 * in [0] ist die Sysnax description des Command
	 * in den restlichen Elelmenten die Datentype der Argumente
	 * @param command
	 * @return
	 */
	public static String[] getArgTyps(RealmsCommandType command)
	{
		String[] typs = null;
		String desc = "";
		String stringTyp  = String.class.getName();
		String intTyp 	  = int.class.getName();
		String doubleTyp  = double.class.getName();
		String booleanTyp = boolean.class.getName();
		String settleTyp =  SettleType.class.getName();
		String buildingTyp = BuildingType.class.getName();
		
		switch (command)
		{
		case 	SETTLE:
			desc = "[SettleTyp] [Name] [OwnerName] ";
			typs = new String[] {desc, settleTyp, stringTyp, stringTyp};
			break;
		case 	STRONGHOLD :
			desc = "[StrongholdName] ";
			typs = new String[] {desc, stringTyp};
			break;
		case 	MODEL :
			typs = new String[] {buildingTyp, intTyp, stringTyp, booleanTyp};
			break;
		case 	OWNER :
			typs = new String[] {buildingTyp, stringTyp, booleanTyp};
			break;
		case 	REALM :
			typs = new String[] {intTyp};
			break;
		case 	REALMS :
			typs = new String[] {intTyp};
			break;
		default :
			desc = "No Command ";
			typs = new String[] {desc};

			break;
		}
		return typs;
	}
	
}