package model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Theater implements java.io.Serializable
{
	private static final long	serialVersionUID	= -1734511095899237403L;
	private Byte number;
	private Integer capacity;
	private List<Scene> scenes = new ArrayList<Scene>();

	public Theater(int capacity)
	{
		this.capacity = capacity;
	}

	public Theater(byte number)
	{
		this.number = number;
	}

	public String toString()
	{
		return getNumber() + ". theater";
	}
}
