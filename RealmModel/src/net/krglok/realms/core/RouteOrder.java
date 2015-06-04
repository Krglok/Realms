package net.krglok.realms.core;


/**
 * <pre>
 * Die routeOrder ist eine SendOrder d.h. der Absender generiert den Auftrag.
 * Wenn der Absender die benötigte Menge auf Lager hat schickt er die Order los.
 * Die routeOrder speichert die transport daten zwischen zwei settlements des gleichen Owners.
 * Der Trader benutzt die routeOrder als Auftrag fuer seine warenlieferungen.
 * Durch die RouteOrder wird eine TradeOrder ausgelöst.
 * Die SourceId wird implizit bei der generierung der TradeOrder gesetzt und ist die Id des verarbeitenden Settlement
 * 
 * Eine routeOrder wird regelmässig ausgeführt und speichert den Zustand des Auftrages.
 * Eine routeOrder läuft endlos weiter und mus manuelll deaktiviert werden.
 *
 * Die Id wird automatisch von der RouteOrderList vergeben
 *      
 * @author Windu
 *
 * </pre>
 */
public class RouteOrder extends ItemPrice
{
	private static int COUNTER;

	private int id;
	private int targetId;
	private boolean isEnabled;
	private boolean isActive;
	
	/**
	 * default to isEnabled = true and isActive = false
	 * 
	 * @param itemRef
	 * @param amount
	 * @param price
	 */
			
	public RouteOrder(String itemRef, int amount, Double price)
	{
		super(itemRef, amount, price);
		this.id = 0;
		this.isEnabled = true;
		this.isActive = false;
		this.targetId = 0;
	}
	
	/**
	 * used for instances from file input
	 * 
	 * @param itemRef
	 * @param amount
	 * @param price
	 * @param isEnabled
	 */
	public RouteOrder(int orderId, int targeitId, String itemRef, int amount, Double price, boolean isEnabled)
	{
		super(itemRef, amount, price);
		this.id = orderId;
		this.targetId = targeitId;
		this.isEnabled = isEnabled;
		this.isActive = false;
	}

	/**
	 * @return the cOUNTER
	 */
	public static int getCOUNTER()
	{
		return COUNTER;
	}

	/**
	 * @param cOUNTER the cOUNTER to set
	 */
	public static void initCOUNTER(int counter)
	{
		COUNTER = counter;
	}
	
	
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getTargetId()
	{
		return targetId;
	}

	public void setTargetId(int targetId)
	{
		this.targetId = targetId;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	
	
	
}
