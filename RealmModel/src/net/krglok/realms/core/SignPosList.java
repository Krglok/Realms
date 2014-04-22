package net.krglok.realms.core;

import java.util.HashMap;

public class SignPosList  extends  HashMap<Integer,SignPos>
{
	private static final long serialVersionUID = 8497519828467355230L;
	
	public SignPosList()
	{
		
	}
	
	/**
	 * Find the text in first line 
	 * Hint: this ist the BuildingType or a Keyword
	 * @param value
	 * @return
	 */
	public SignPos findSignType(String value)
	{
		SignPos result = new SignPos();
		for (SignPos sign : this.values())
		{
			if (sign.getText()[0].equalsIgnoreCase(value))
			{
				return sign;
			}
		}
		
		return result;
	}
	
	
}
