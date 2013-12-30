

import net.krglok.realms.core.MemberLevel;
import net.krglok.realms.core.Owner;


public class InitTestOwner
{
	private Owner owner;
	
	public InitTestOwner(
			int id,
			MemberLevel level,
			int capital,
			String playerName,
			int realmID,
			Boolean isNPC)
	{
		owner = new Owner();
		owner.setId(id);
		owner.setLevel(level);
		owner.setCapital(capital);
		owner.setPlayerName(playerName);
		owner.setRealmID(realmID);
		owner.setIsNPC(isNPC);
	}

	public Owner getOwner()
	{
		return owner;
	}

	public void setOwner(Owner owner)
	{
		this.owner = owner;
	}
}
