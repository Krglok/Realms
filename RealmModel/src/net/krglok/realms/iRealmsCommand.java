package net.krglok.realms;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

/**
 * <pre>
 * Interface for the internal commands 
 * the command are hanled by the command interpreter
 * - getRequiredArgs , give the number of necessary parameters to the interpreter
 * - setPara(X),  for each used datatype is methode installed
 * - getParaTypes , give the datatype info of the parameter by position to the interpreter
 * - canExecute , give true or or false and can individually setup
 * - execute , realize the command individually
 * 
 * @author oduda
 *</pre>
 */
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
	
	public ArrayList<String> getErrorMsg();
}
