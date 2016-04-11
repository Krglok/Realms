package net.krglok.realms.manager;

import org.bukkit.block.Biome;

import net.krglok.realms.Common.LocationData;

public class BiomeLocation
{
	private Biome biome;
	private LocationData position;
	
	
	public BiomeLocation(Biome biome, LocationData position)
	{
		super();
		this.biome = biome;
		this.position = position;
	}


	public Biome getBiome()
	{
		return biome;
	}


	public void setBiome(Biome biome)
	{
		this.biome = biome;
	}


	public LocationData position()
	{
		return position;
	}


	public void setPosition(LocationData position)
	{
		this.position = position;
	}
	
	
}
