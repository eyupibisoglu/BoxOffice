package model;
import lombok.Data;


@Data
public class Person implements java.io.Serializable
{
	private String name;
	private String surname;

	public Person(String name, String surname)
	{
		this.name = name;
		this.surname = surname;
	}
	
	
}
