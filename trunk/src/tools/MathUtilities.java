package tools;

public class MathUtilities
{
	public static int getWhole(double value)
	{
		int result = 0;
		
		if(value >= 0)
		{
			result = (int)(Math.floor(value));
		}
		else if(value < 0)
		{
			result = (int)(Math.ceil(value));
		}
		
		return result;
	}
	
	public static double getFraction(double value)
	{
		double result = 0.0;
		
		if(value >= 0)
		{
			result = value - Math.floor(value);
		}
		else if(value < 0)
		{
			result = value - Math.ceil(value);
		}
		
		return result;	
	}
}
