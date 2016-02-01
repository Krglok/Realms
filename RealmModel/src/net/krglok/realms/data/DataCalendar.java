package net.krglok.realms.data;
/**
 * Calculate the Calendar Darta based days.
 * Hold the age in ingame days
 * 
 * @author Windu
 *
 */

public class DataCalendar
{
	long age;
	int year ;
	int month ;
	int day ;
	int week ;
	int dayOfWeek;
	char separator = '.';
	char separatorEng = '/';
	
	public DataCalendar()
	{
		age = 1;
		year = 0;
		month = 1;
		day = 1;
		week = 1;
		dayOfWeek=1;
	}

	public DataCalendar(long age)
	{
		super();
		doCalc(age);
	}
	
	public void stepDay()
	{
		this.age++;
	}
	
	
	public static DataCalendar calcCalendar(int age)
	{
		DataCalendar calendar = new DataCalendar();
		calendar.year = (int) (age / (12 * 30));
		int restYear = (int) (age - (calendar.year * 12 * 30));
		calendar.month = restYear / 30;
		calendar.day = restYear - (calendar.month * 30);
		calendar.week = calendar.day / 7;
		calendar.dayOfWeek = calendar.day - (calendar.week * 7);
		return calendar;
	}
	
	public void doCalc(long age2)
	{
		this.age = age2;
		this.year = (int) (age2 / (12 * 30));
		int restYear = (int) (age2 - (this.year * 12 * 30));
		this.month = restYear / 30;
		this.day = restYear - (this.month * 30);
		this.week = this.day / 7;
		this.dayOfWeek = this.day - (this.week * 7);
	}
	
	public int getDayofWeek()
	{
		return dayOfWeek;
	}
	
	/**
	 * return Date in German format <Day.Month.Year>
	 * 
	 * @return
	 */
	public String getCalendarDateGer()
	{
		String s = String.valueOf(day)+separator+String.valueOf(month)+separator+String.valueOf(year);
		return s;
	}
	
	/**
	 * return Date in english Format  <Month/Day/Year>
	 * 
	 * @return
	 */
	public String getCalendarDateEng()
	{
		String s = String.valueOf(month)+separatorEng+String.valueOf(day)+separatorEng+String.valueOf(year);
		return s;
	}
	
	/**
	 * return date as Year, Month, Day  as Sortorder
	 * 
	 * @param separator
	 * @return
	 */
	public String getCalendarDateOrder(String separator)
	{
		String s = String.valueOf(year)+separator+String.valueOf(month)+separator+String.valueOf(day);
		return s;
	}
	
}
