package net.krglok.realms.model;

import java.util.ArrayList;
import java.util.Calendar;

public class LogList
{
	private ArrayList<String> logList;

	public LogList()
	{
		logList = new ArrayList<String>();
	}

	public ArrayList<String> getLogList()
	{
		return logList;
	}

	public void setLogList(ArrayList<String> logList)
	{
		this.logList = logList;
	}

	/**
	 * Set a transaction text, there are automatic add datetime
	 * date / time / user / text
	 * @param text
	 */
	public void addlog(String text, String user)
	{
		Calendar cal = Calendar.getInstance ();
		String dateTime = cal.get( Calendar.DAY_OF_MONTH ) +
                "." + (cal.get( Calendar.MONTH ) + 1 ) +
                "." + cal.get( Calendar.YEAR ) + " " + 
				cal.get( Calendar.HOUR_OF_DAY ) + ":" +
                cal.get( Calendar.MINUTE ) + ":" +
                cal.get( Calendar.SECOND ) ;
		String transaction = dateTime + "/" + user + "/" + text;
		this.logList.add(transaction);
	}

	public int size()
	{
		return logList.size();
	}
}
