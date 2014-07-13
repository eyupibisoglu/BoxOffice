package model;

import lombok.Data;

@Data
public class Ticket implements java.io.Serializable
{
	private static final long	serialVersionUID	= 7235233381518570436L;
	private Theater theater;
	private Movie movie;
	private Person customer;
	private Time time;
	private Seat seat;
	private Date date;
	private Price price;
	
}
