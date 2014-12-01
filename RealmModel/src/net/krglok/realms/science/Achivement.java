package net.krglok.realms.science;

public class Achivement
{
	private String id;
	private String name;
	private String material;
	
	public Achivement()
	{
		this.id = "";
		this.name = "";
		this.material = "";
	}
	
	public Achivement(String id, String name)
	{
		this.id = id;
		this.name = name;
		this.material = "";
	}

	public Achivement(String id, String name, String materialName)
	{
		this.id = id;
		this.name = name;
		this.material = materialName;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMaterial()
	{
		return material;
	}

	public void setMaterial(String material)
	{
		this.material = material;
	}

}
