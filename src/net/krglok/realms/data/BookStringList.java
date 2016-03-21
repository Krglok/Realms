package net.krglok.realms.data;

import java.util.ArrayList;
/**
 *  Realisiert eine spezielle ArrayList um die Zeilen von BookAndQuill
 *  automatische zu Formatieren und aufzuteilen, wenn diese laenger sind
 *  als die maxinmale Zeilenlaenge 
 *  
 * @author Windu
 * created : 02.01.2016
 */

public class BookStringList extends ArrayList<String>
{
	protected static int maxLen = 18;

	public BookStringList()
	{
		
	}

	/**
	 * split string into parts of maxLen (19)
	 * 
	 * @param in
	 * @return
	 */
	private String[] splitmaxLen(String in)
	{
		int len = (in.length() / maxLen)+1;
		String[] out = new String[len];
		int pos = 0;
		int index = maxLen;
		for (int i = 0; i < len; i++)
		{
			if (index < in.length())
			{
				out[i] = in.substring(pos, index);
				pos = pos + maxLen;
				index = index+maxLen;
			} else
			{
				out[i] = setStrleft(in.substring(pos, in.length()),maxLen);
				if (out[i] == null)
				{
					out[i] =setStrleft(out[i],maxLen);
				}
			}
		}
		return out;
	}
	
	/**
	 * formatted string to length(19)
	 * 
	 * @param in
	 * @param len
	 * @return
	 */
	private String setStrleft(String in, int len)
	{
		char[] out = new char[len];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = ' ';
		}
		if (len >= in.length())
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < zw.length; i++)
			{
				out[i] = zw[i]; 
			}
		} else
		{
			char[] zw  = in.toCharArray();
			for (int i = 0; i < out.length; i++)
			{
				out[i] = zw[i]; 
			}
		}
		return String.valueOf(out);
	}

	/**
	 * overwrite methode add of ArrayList
	 * store string in parts of lengt og maxLen(19)
	 * automatic split or extend length of string
	 * 
	 */
	public boolean add(String value)
	{
		if (value.length() > maxLen)
		{
			String[] out = splitmaxLen(value);
			for (int i = 0; i < out.length; i++)
			{
				super.add(setStrleft(out[i],maxLen)+"\n");
			}
			return true;
		} else
		{
			return super.add(setStrleft(value,maxLen)+"\n");
		}
	}
}
