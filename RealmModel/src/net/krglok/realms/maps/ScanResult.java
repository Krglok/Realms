package net.krglok.realms.maps;

import org.bukkit.Material;

/**
 * Scanresult hold the scandata for concrete position aus an array
 * the square is chunk
 * 
 * @author Windu
 *
 */
public class ScanResult {
	
	private int edge = 16;
	
	private ScanData[][] scanArray;
	
	private boolean isReady ;
	
	/**
	 * erezugt ein  scanarray von 16x16
	 */
	public ScanResult()
	{
		this.scanArray = new ScanData[edge][edge];
		this.isReady = false;
	}

	public ScanResult(int radius)
	{
		this.scanArray = new ScanData[edge][edge];
		this.isReady = false;
	}
	
	public  ScanData[][]  resetScan()
	{
		ScanData scanData;
		for (int i=0; i < edge; i++)
		{
			for (int j=0; j < edge; j++)
			{
				scanData = scanArray[i][j];
				scanData.blockMat = Material.AIR;
				scanData.height = 0;
			}
		}
		
		return scanArray;
	}

	public int getEdge() {
		return edge;
	}

	public ScanData[][] getScanArray() {
		return scanArray;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

}
