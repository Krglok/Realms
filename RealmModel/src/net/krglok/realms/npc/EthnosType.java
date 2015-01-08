package net.krglok.realms.npc;

/**
 * the ethno types define races that build urban structures.
 * 
 * @author Windu
 *
 */
public enum EthnosType
{
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
