package net.krglok.realms.model;

public interface iModelCommand
{

	public ModelCommandType command();
	
	public String[] getParaTypes();
	
	public void execute();
	
	public boolean canExecute();
	
	

}
