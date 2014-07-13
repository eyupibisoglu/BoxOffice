package model;
import java.util.Calendar;

import lombok.Data;


@Data
public class Time implements java.io.Serializable
{
	private static final long	serialVersionUID	= 5662651900640098663L;
	private Byte hour;
	private Byte minute;
	
	public Time()
	{
		
	}
	
	public Time(Byte hour, Byte minute)
	{
		this.hour = hour;
		this.minute = minute;
	}
	
	public boolean compare(Time time)
	{
		if (getHour() > time.getHour())
			return true;
		
		if (getHour().equals(time.getHour()) && getMinute() > time.getMinute())
			return true;
		
		return false;
	}
	
	public Time now()
	{
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date);  
		return new Time((byte) calendar.get(Calendar.HOUR_OF_DAY), (byte) calendar.get(Calendar.HOUR));
	}
	
	@Override
	public String toString()
	{
		return hour + ":" + minute;
	}
}
