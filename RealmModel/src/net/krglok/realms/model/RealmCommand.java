package net.krglok.realms.model;


public class RealmCommand 
{
	private RealmCommandType command;
	private RealmSubCommandType subCommand;
	private String[] args;
	
	
	public RealmCommand()
	{
		command = RealmCommandType.NONE;
		subCommand = RealmSubCommandType.NONE;
	}

	public RealmCommand(String command, String subCommand)
	{
		this.setCommand(command);
		this.setSubCommand(subCommand);
	}
	
	public RealmCommand(String command, String subCommand, String[] args)
	{
		this.setCommand(command);
		this.setSubCommand(subCommand);
		this.args = args;
	}
	
	public RealmCommand(String command, String[] values)
	{
		setCommand(command);
		setSubAndArgs(values);
	}
	
	public RealmCommandType command()
	{
		return this.command;
	}
	
	public RealmSubCommandType subCommand()
	{
		return this.subCommand;
	}
	
	public String[] args()
	{
		return args;
	}
	
	private void setCommand(String value)
	{
		
		this.command = RealmCommandType.getRealmCommandType(value);
	}
	
	private void setSubAndArgs(String[] values)
	{
		if (values.length > 0)
		{
			subCommand = RealmSubCommandType.getRealmSubCommandType(values[0]);
		} else
		{
			subCommand = RealmSubCommandType.NONE;
		}
		for (int i = 1; i < values.length-1; i++) 
		{
			this.args[i-1] = values[i];
		}
	}
	
	private void setSubCommand(String name)
	{
		this.subCommand = RealmSubCommandType.getRealmSubCommandType(name);
	}
	
}
