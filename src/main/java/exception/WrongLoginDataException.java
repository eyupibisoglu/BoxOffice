package exception;

public class WrongLoginDataException extends Exception
{
	private static final long	serialVersionUID	= 2999698891397971203L;

	public WrongLoginDataException()
	{
		super();
	}

	public WrongLoginDataException(String message)
	{
		super(message);
	}
	
}
