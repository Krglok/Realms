package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

public interface iRealmsCommand
{

	public RealmsCommandType command();
	
	public RealmsSubCommandType subCommand();
	
	public String[] getParaTypes();
	
	public void execute(Realms plugin, CommandSender sender);
	
	public boolean canExecute(Realms plugin, CommandSender sender);
	
	public String[] getDescription();

	public void setDescription(String[] newDescription);
	
	public ArrayList<String> getDescriptionString();
	
	public int getRequiredArgs();
	
	public void setPara(int index , String value);

	public void setPara(int index , int value);

	public void setPara(int index , boolean value);

	public void setPara(int index , double value);
}
