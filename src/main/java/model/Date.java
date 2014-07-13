package model;
import java.util.Calendar;

import lombok.Data;


@Data
public class Date implements java.io.Serializable
{
	private static final long	serialVersionUID	= 2827833705826542342L;
	private Short year;
	private Byte month;
	private Byte day;
	
	public Date()
	{
		
	}
	
	public Date(Short year, Byte month, Byte day)
	{
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public Date now()
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(new java.util.Date());
	    int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int day = cal.get(Calendar.DAY_OF_MONTH);
	    
		return new Date((short) year, (byte) month, (byte) day);
	}
	
	@Override
	public String toString()
	{
		return getYear() + "." + getMonth() + "." + getDay();
	}
}
