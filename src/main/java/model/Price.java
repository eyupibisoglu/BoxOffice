package model;
import lombok.Data;

@Data
public class Price implements java.io.Serializable
{
	private static final long	serialVersionUID	= -4112673377013423602L;
	private Float amount;

	public Price(Float amount)
	{
		this.amount = amount;
	}
	
	public String toString()
	{
		return amount.toString();
	}
}
