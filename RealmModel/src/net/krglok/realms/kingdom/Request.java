package net.krglok.realms.kingdom;

public class Request
{
	public static int REQUEST_STATUS_NONE = 0;
	public static int REQUEST_STATUS_OPEN = 1;
	public static int REQUEST_STATUS_ACCEPT = 2;
	public static int REQUEST_STATUS_REJECT = 3;
	
	private static int ID = 0;
	
	private int id;
	private int ownerId;
	private int status;
	
	public Request()
	{
		this.id = 0;
		this.ownerId = 0;
		this.status = 0;
	}

	public static int getID()
	{
		return ID;
	}

	public static void initID(int iD)
	{
		ID = iD;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(int ownerId)
	{
		this.ownerId = ownerId;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}


	public String getStatus(int value)
	{
		switch(value)
		{
		case 1: return "OPEN";
		case 2: return "ACCEPT";
		case 3: return "REJECT";
		default :
			return "NONE";
		}
	}
}
