package net.krglok.realms.core;



public class ItemPrice extends Item 
{
	private Double basePrice; 
	
	/**
	 * basePrice = 0.0;
	 * @param itemRef
	 * @param value
	 */
	public ItemPrice(String itemRef, Double price)
	{
		super(itemRef, 0);
		basePrice = price; 
	}
	
	/**
	 * set basePrice
	 * @param itemRef
	 * @param amount
	 * @param price
	 */
	public ItemPrice (String itemRef, Double price, int amount)
	{
		super(itemRef, amount);
		basePrice = price;
	}

	/**
	 * @return the basePrice
	 */
	public Double getBasePrice()
	{
		return basePrice;
	}

	/**
	 * @param basePrice the basePrice to set
	 */
	public void setBasePrice(Double basePrice)
	{
		this.basePrice = basePrice;
	}

	public Double getFormatedBasePrice()
	{
		Integer iPrice = (int) (basePrice * 100);
		double  Price  = iPrice;
		return Price/100.0;
	}

}
