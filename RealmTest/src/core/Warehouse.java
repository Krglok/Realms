package core;

/**
 * 
 * @author oduda
 *
 */
public class Warehouse 
{
	private Boolean isEnabled;
	private int itemMax;
	private ItemList itemList;
	
	public Warehouse()
	{
		setIsEnabled(false);
		setItemMax(0);
		setItemList(new ItemList());
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getItemMax() {
		return itemMax;
	}

	public void setItemMax(int itemMax) {
		this.itemMax = itemMax;
	}

	public ItemList getItemList() {
		return itemList;
	}

	public void setItemList(ItemList itemList) {
		this.itemList = itemList;
	}

}
