package net.krglok.realms.data;

public class DBCachRef
{
	

	private DBCachType ref;
	private int id;
	

	public DBCachRef(DBCachType ref, int id)
	{
		this.ref = ref;
		this.id = id;
	}


	/**
	 * @return the ref
	 */
	public DBCachType getRef()
	{
		return ref;
	}


	/**
	 * @param ref the ref to set
	 */
	public void setRef(DBCachType ref)
	{
		this.ref = ref;
	}


	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}
}
