package exceptions;

public class ReadException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public ReadException()
	{ }
	
	public ReadException(String s)
	{
		super(s);
	}
}
