package net.krglok.realms.core;

/**
 * the realmModel is the top Class of the realm handling.
 * all functions and algorithm of the settlements and realm will be executed inside the model.
 * all rules are implemented here.
 * all storage, textoutput, commandhandling we be done outside of the model
 * the model represent a abstract realisation of an ancient political and economic feudalsystem
 * (Lehensystem oder Feudalismus).
 * the real objects in the game are realized and representent by a different plugin, HeroStronghold.
 * the aspects and parameters of HeroStronghold are imported in the realmModel
 * - consume of items by region
 * - production of items by region upkeep
 * - production of money by region
 * - production of power in superregion
 * - consum of money by superregion
 * - the instances of the regions and superregions are used as Buildings and Areas
 *  the aspects and parameters of the HeroStronghold are abstracted in the realmModel the real value 
 *  and connection to the HeroStronghold objects are done outside the realmModel by the plugin that 
 *  use this realmModel.
 *    
 * @author oduda    19.12.2013
 *
 */
public class RealmModel
{
	private static final String REALM_MODEL = "RealmModel";
	private static final String REALM_MODEL_VER = "0.1.0";

	private Boolean isEnabled;
	
	private OwnerList owners;
	private RealmList realms;
	private SettlementList settlements;
	
	/**
	 * instances an empty Model , must be initialize external !
	 * @param realmCounter
	 * @param settlementCounter
	 */
	public RealmModel(int realmCounter, int settlementCounter)
	{
		isEnabled = false;
		setOwners(new OwnerList());
		setRealms(new RealmList(realmCounter));
		setSettlements(new SettlementList(settlementCounter));
	}

	/**
	 * 
	 * @return  name and version
	 */
	public String getModelName()
	{
		return REALM_MODEL+" Ver. "+REALM_MODEL_VER;
	}
	
	/**
	 * 
	 * @return version
	 */
	public String getModelVersion()
	{
		return REALM_MODEL_VER;
	}
	
	/**
	 * 
	 * @return OwnerList
	 */
	public OwnerList getOwners()
	{
		return owners;
	}

	/**
	 * replace OwnerList
	 * @param owners
	 */
	public void setOwners(OwnerList owners)
	{
		this.owners = owners;
	}

	/**
	 * 
	 * @return RealmList
	 */
	public RealmList getRealms()
	{
		return realms;
	}

	/**
	 * replace RealmList
	 * @param realms
	 */
	public void setRealms(RealmList realms)
	{
		this.realms = realms;
	}

	/**
	 * the settlementList incorporate buildings and units
	 * @return SettlementList
	 */
	public SettlementList getSettlements()
	{
		return settlements;
	}

	/**
	 * replace SettlementList
	 * @param settlements
	 */
	public void setSettlements(SettlementList settlements)
	{
		this.settlements = settlements;
	}
	
}
