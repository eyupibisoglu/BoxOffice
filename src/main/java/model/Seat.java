package model;
import lombok.Data;

@Data
public class Seat implements java.io.Serializable
{
	private static final long	serialVersionUID	= -6704736827702531249L;
	private String number;
	private Boolean isAvailable = true;
	
	public Seat(String number)
	{
		this.number = number;
	}
	
	@Override
	public String toString()
	{
		return number;
	}
}
