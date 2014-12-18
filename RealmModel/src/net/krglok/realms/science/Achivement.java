package net.krglok.realms.science;


/**
 * Der Name ist absichtlicht falsch geschrieben damit eine relevance zu den MC achievements besteht 
 * aber doch ein Unterschied besteht, der klar macht, dass dies ICHT die gleichen ojeekte sind.
 *    
 * @author Windu
 *
 */
public class Achivement
{
	private AchivementType achiveType;
	private AchivementName achiveName;
	private boolean isEnaled;
	
	/**
	 * create new Achievement with new ID
	 */
	public Achivement(AchivementType achiveType,AchivementName achiveName )
	{
		this.achiveName = achiveName;
		this.achiveType = achiveType;
		this.setEnaled(false);
	}

	public Achivement(AchivementType achivementType, AchivementName achivementName, boolean isEnabled)
	{
		this.achiveName = achivementName;
		this.achiveType = achivementType;
		this.setEnaled(isEnabled);
	}
	


	public AchivementType getAchiveType()
	{
		return achiveType;
	}

	public AchivementName getAchiveName()
	{
		return achiveName;
	}

	public static String makeName(AchivementType achiveType,AchivementName achiveName)
	{
		return achiveType+"_"+achiveName;
	}

	public String getName()
	{
		return makeName(this.achiveType,this.achiveName);
	}

	public static String splitNameTyp(String value)
	{
        String[] params = value.split("_");

		return params[0];
	}

	public static String splitNameName(String value)
	{
        String[] params = value.split("_");

		return params[1];
	}

	public String getTypeName()
	{
		return setTypeName();
	}

	private String setTypeName()
	{
		return  achiveType.name()+"_"+achiveName.name();
	}

	/**
	 * @return the isEnaled
	 */
	public boolean isEnaled()
	{
		return isEnaled;
	}

	/**
	 * @param isEnaled the isEnaled to set
	 */
	public void setEnaled(boolean isEnaled)
	{
		this.isEnaled = isEnaled;
	}

}
