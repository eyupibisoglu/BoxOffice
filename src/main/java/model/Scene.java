package model;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Scene implements java.io.Serializable
{
	private static final long	serialVersionUID	= -7786239082694046768L;
	private Movie movie;
	private Date date;
	private Time time;
	private Price price;
	private List<Seat> seats;

	public Scene(int capacity)
	{
		seats = new ArrayList<Seat>(capacity);
	}
}
