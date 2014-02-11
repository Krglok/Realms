package net.krglok.realms.manager;

public class ReputationData
{

	private ReputationType repTyp;
	private String playerName;
	private int value;
	private int settleId;
	
	
	public ReputationData(ReputationType repTyp, String playerName, int value,
			int settleId)
	{
		super();
		this.repTyp = repTyp;
		this.playerName = playerName;
		this.value = value;
		this.settleId = settleId;
	}


	public ReputationType getRepTyp()
	{
		return repTyp;
	}


	public void setRepTyp(ReputationType repTyp)
	{
		this.repTyp = repTyp;
	}


	public String getPlayerName()
	{
		return playerName;
	}


	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}


	public int getValue()
	{
		return value;
	}


	public void setValue(int value)
	{
		this.value = value;
	}


	public int getSettleId()
	{
		return settleId;
	}


	public void setSettleId(int settleId)
	{
		this.settleId = settleId;
	}
	
	
	
}
