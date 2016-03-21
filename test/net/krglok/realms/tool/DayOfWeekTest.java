package net.krglok.realms.tool;
	
import static org.junit.Assert.*;

import org.junit.Test;

/**<pre>
 *  
 * @author olaf.duda
 * </pre>
 */

public class DayOfWeekTest 
{
	private int getDayofWeek(int age)
	{
		int year = age / (12 * 30);
		int restYear = age - (year * 12 * 30);
		int month = restYear / 30;
		int restMonth = restYear - (month * 30);
		int week = restMonth / 7;
		int day = restMonth - (week * 7);
		
		return day;
	}
	
	private String getDatum(int age)
	{
		int year = age / (12 * 30);
		int restYear = age - (year * 12 * 30);
		int month = restYear / 30;
		int restMonth = restYear - (month * 30);
		String sYear = String.valueOf(year+1);
		String sMonth = String.valueOf(month+1);
		String sDay = String.valueOf(restMonth+1);
		return sDay+"."+sMonth+"."+sYear;
	}
	
	private String getProductionTyp(int age)
	{
		if (getDayofWeek(age) == 0)
		{
			return "Lehen production";
		} else
		{
			return "Building Production";
		}
	}
	
	private String getDayName(int day)
	{
		switch( day)
		{
		case 0 :return "Sunday";
		case 1 :return "Monday";
		case 2 :return "Thusday";
		case 3 :return "Wednesday";
		case 4 :return "Thursday";
		case 5 :return "Friday";
		case 6 :return "Saturday";
		
		}
		return "xxxx";
	}

	@Test
	public void test() 
	{
		int age = 1230;
		int day = 0;
		for (int i = 0; i < 380; i++) 
		{
			day = getDayofWeek(age+i);
//			System.out.println(getDayName(day));
			System.out.println(getDatum(age+i)+ " : "+getDayName(day)+" | "+ getProductionTyp(age+i));
		}
		
	}

}
