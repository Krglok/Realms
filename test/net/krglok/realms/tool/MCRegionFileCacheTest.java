package net.krglok.realms.tool;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;


public class MCRegionFileCacheTest
{

	private byte readNext(DataInputStream chunk) throws IOException
	{
		byte b = 0;
		while (b == 0)
		{
			b = chunk.readByte();
		}
		
		return b;
	}
	
	private void getChunkData(DataInputStream chunk)
	{
		try
		{
			byte level = chunk.readByte();
			System.out.println("root:"+level);
			byte nbtType = readNext(chunk);
			System.out.println("Level:"+nbtType);
			
			nbtType = readNext(chunk);
			int xChunk = chunk.readInt();
			System.out.println("Chunk X:"+nbtType+":"+xChunk);

			nbtType = readNext(chunk);
			int zChunk = chunk.readInt();
			System.out.println("Chunk Z:"+nbtType+":"+zChunk);

			nbtType = readNext(chunk);
			long lUpdate = chunk.readLong();
			System.out.println("Update:"+nbtType+":"+lUpdate);
	
			nbtType = readNext(chunk);
			byte lPop = chunk.readByte();
			System.out.println("LightPop:"+nbtType+":"+lPop);

			nbtType = readNext(chunk);
			byte tPop = chunk.readByte();
			System.out.println("TerrainPop:"+nbtType+":"+tPop);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void readTest(DataInputStream chunk)
	{
		try
		{
			byte nbtType;
			
			for (int i = 0; i < 50; i++)
			{
				nbtType = (byte) chunk.read();
				switch(nbtType)
				{
				case 0: 
					System.out.println("End  :"+nbtType);
					break;
				case 1:
					System.out.println("Byte :"+nbtType);
					chunk.skipBytes(1);
					break;
				case 2:
					System.out.println("Short:"+nbtType);
					chunk.skipBytes(2);
					break;
				case 3:
					System.out.println("Int  :"+nbtType);
					chunk.skipBytes(4);
					break;
				case 4:
					System.out.println("Long :"+nbtType);
					chunk.skipBytes(8);
					break;
				case 5:
					System.out.println("Float:"+nbtType);
					chunk.skipBytes(4);
					break;
				case 6:
					System.out.println("Doubl:"+nbtType);
					chunk.skipBytes(8);
					break;
				case 7:
					int size = chunk.readInt();
					chunk.skipBytes(size);
					System.out.println("Array:"+nbtType+":"+size);
					break;
				case 8:
					int length = chunk.readShort();
//					chunk.skipBytes(length);
					System.out.println("Strin:"+nbtType+":"+length);
					break;
				case 9:
					byte lType = chunk.readByte();
					int lSize = 0;
					if (lType != 0)
					{
						lSize = chunk.readInt();
						chunk.skipBytes(lSize);
					}
					System.out.println("List :"+nbtType+":"+lType+":"+lSize);
					break;
				case 10:
					System.out.println("Compo:"+nbtType);
					break;
				case 11:
					int iSize = chunk.readInt();
//					chunk.skipBytes(iSize);
					System.out.println("IntAr:"+nbtType+":"+iSize);
					break;
				default:
				}
				
			}
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void readLocation(DataInputStream chunk)
	{
		try
		{
			byte nbtType;
			
			for (int i = 0; i < 40; i++)
			{
				nbtType = (byte) chunk.read();
				switch(nbtType)
				{
				case 0: 
					System.out.println("End  :"+nbtType);
					break;
				case 1:
					System.out.println("Byte :"+nbtType);
					chunk.skipBytes(1);
					break;
				case 2:
					System.out.println("Short:"+nbtType);
					chunk.skipBytes(2);
					break;
				case 3:
					System.out.println("Int  :"+nbtType);
					chunk.skipBytes(4);
					break;
				case 4:
					System.out.println("Long :"+nbtType);
					chunk.skipBytes(8);
					break;
				case 5:
					System.out.println("Float:"+nbtType);
					chunk.skipBytes(4);
					break;
				case 6:
					System.out.println("Doubl:"+nbtType);
					chunk.skipBytes(8);
					break;
				case 7:
					int size = chunk.readInt();
					chunk.skipBytes(size);
					System.out.println("Array:"+nbtType+":"+size);
					break;
				case 8:
					int length = chunk.readShort();
//					chunk.skipBytes(length);
					System.out.println("Strin:"+nbtType+":"+length);
					break;
				case 9:
					byte lType = chunk.readByte();
					int lSize = 0;
					if (lType != 0)
					{
						lSize = chunk.readInt();
						chunk.skipBytes(lSize);
					}
					System.out.println("List :"+nbtType+":"+lType+":"+lSize);
					break;
				case 10:
					System.out.println("Compo:"+nbtType);
					break;
				case 11:
					int iSize = chunk.readInt();
//					chunk.skipBytes(iSize);
					System.out.println("IntAr:"+nbtType+":"+iSize);
					break;
				default:
				}
				
			}
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private void readTestTag(DataInputStream chunk, String tagName)
	{
		try
		{
			char nbtChar;
			
			for (int i = 0; i < 4000; i++)
			{
				nbtChar = chunk.readChar();
				if (nbtChar >= ' ')
				{
					System.out.println(nbtChar);
				} else
				{
					System.out.print(".");
				}
				
			}
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test()
	{
		DataInputStream chunk = null;
		
		String dir = "D:\\Program Files\\Bukkit_172\\Draskoriascraft\\region";
//		String dir = "C:\\Users\\Windu\\AppData\\Roaming\\.minecraft\\saves\\design\\region";
		
		String path = dir +"\\"+ "r.0.0.mca";
		
		File pathhFile = new File(path);
		
		MCRegionFile rFile = new MCRegionFile(pathhFile);
		
		int xPos = 0;
		int zPos = 0;
		
		chunk = rFile.getChunkDataInputStream(xPos, zPos);

		if (chunk == null)
		{
			System.out.println("Nothing read: ");
		} else
		{
//			try
//			{
//				System.out.println("DataSize :"+chunk.available());
//			} catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			readTestTag(chunk, new String("Level"));
			readTest(chunk);
//			getChunkData(chunk);
		}

		
//		try
//		{
//			int xPosChunk = chunk.readInt();
//			int zPosChunk = chunk.readInt();
//			System.out.println("xPos :"+xPos+" / ReadX :"+xPosChunk);
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
				
		fail("Not yet implemented");
	}

}
