package net.krglok.realms.manager;

public enum ReputationStatus
{
	NONE (0),
	KNOWN (5),
	WELLKNOWN(30),
	CITIZEN(51),
	TRADER(55)
	;
	
	private final int value;

	ReputationStatus(int minValue)
	{
		this.value = minValue;
	}
	
	public int getValue()
	{
		return value;
	}

	public static ReputationStatus getReputationStatus(String name)
	{
		for (ReputationStatus repStatus : ReputationStatus.values())
		{
			if (repStatus.name().equals(name))
			{
				return repStatus;
			}
		}
		return NONE;
	}

	public static String ReputationStatusMessage(int reputationLevel)
	{
		String msg = "You are a ";

		if (reputationLevel < ReputationStatus.KNOWN.value)
		{
			msg = msg + "stranger";
		} else
		if (reputationLevel < ReputationStatus.WELLKNOWN.value)
		{
			msg = msg + "known face";
		} else
		if (reputationLevel < ReputationStatus.CITIZEN.value)
		{
			msg = msg + "well known face";
		} else
		if (reputationLevel < ReputationStatus.WELLKNOWN.value)
		{
			msg = msg + "  neighbor ";
		} else
		{
			msg = "You are accepted as trader ";
		} 

			
		
		return msg;
	}
	
}
