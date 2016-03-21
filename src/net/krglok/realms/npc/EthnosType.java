package net.krglok.realms.npc;

/**
 * the ethno types define races that build urban structures.
 * 
 * DEFAULT means that no Ethno is selected
 * 
 * @author Windu
 *
 */
public enum EthnosType
{
	DEFAULT,
	HUMAN,
	ELF,
	DWARF,
	VILLAGER,
	ORC,
	GOBLIN,
	DAEMON;
	
	public boolean contains(String value)
	{
		for (EthnosType eType : EthnosType.values())
		{
			if (eType.name().equals(value))
			{
				return true;
			}
		}
		return false;
	}
}
