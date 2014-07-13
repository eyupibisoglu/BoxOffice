package model;

public class Customer extends Person
{
	private static final long	serialVersionUID	= 3814420959921314086L;

	public Customer(String name, String surname)
	{
		super(name, surname);
	}

	public String toString()
	{
		return getName() + " " + getSurname();
	}

}
