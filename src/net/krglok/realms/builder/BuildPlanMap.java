package net.krglok.realms.builder;

public class BuildPlanMap extends BuildPlan
{

	public BuildPlanMap(BuildPlanType buildingType, int radius, int offsetY)
	{
		super(buildingType, radius, offsetY);
	}

	/**
	 * remember all uildPlam are cubes !!
	 * the cube must be initialized from tmx file
	 * @param edge , edge of the cube  
	 */
	@Override
	public byte[][][] initCube(int edge)
	{
		byte[][][] cube = new byte[edge][edge][edge];
		BuildPlan.clearCube(cube);
		this.setCube(cube);
		return cube;
	}

}
