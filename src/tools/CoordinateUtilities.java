package tools;

public class CoordinateUtilities
{
	public static int getDegreeOfDecimalCoordinate(double coordinate)
	{
		return MathUtilities.getWhole(coordinate);
	}
	
	public static int getMinutesOfDecimalCoordinate(double coordinate)
	{
		return MathUtilities.getWhole(MathUtilities.getFraction(coordinate) * 60);
	}
	
	public static double getSecondsOfDecimalCoordinate(double coordinate)
	{
		return MathUtilities.getFraction(MathUtilities.getFraction(coordinate) * 60) * 60;
	}
	
	public static double getDecimalCoordinateOfDegMinSec(String coordinateString)
	{
		double result = CoordinateUtilities.getSecondsOfDegMinSec(coordinateString) / 60.0;
		result = (result + CoordinateUtilities.getMinutesOfDegMinSec(coordinateString)) / 60.0;
		result += CoordinateUtilities.getDegreeOfDegMinSec(coordinateString);
		
		String direction = CoordinateUtilities.getDirectionOfDegMinSec(coordinateString);
		if(direction.equals("S") || direction.equals("W"))
		{
			result = 0 - result;
		}
		
		return result;
	}
	
	public static int getDegreeOfDegMinSec(String coordinateString)
	{
		String degreeString = coordinateString.substring(0, coordinateString.indexOf("°"));
		return new Integer(degreeString);
	}
	
	public static int getMinutesOfDegMinSec(String coordinateString)
	{
		String minutesString = coordinateString.substring(coordinateString.indexOf("°") + 1, coordinateString.indexOf("'"));
		return new Integer(minutesString);
	}
	
	public static double getSecondsOfDegMinSec(String coordinateString)
	{
		String secondsString = coordinateString.substring(coordinateString.indexOf("'") + 1, coordinateString.indexOf("''"));
		return new Double(secondsString);
	}
	
	public static String getDirectionOfDegMinSec(String coordinateString)
	{
		String direction = coordinateString.substring(coordinateString.lastIndexOf("''") + 2, coordinateString.length());
		return direction.trim();
	}
	
	public static String getDirectionOfLatitudeCoordinate(double coordinate)
	{
		String direction = "";
		
		if(coordinate > 0)
		{
			direction = "N";
		}
		else if(coordinate < 0)
		{
			direction = "S";
		}
		else
		{
			// TODO not really good: should be nothing
			direction = "N";
		}
		
		return direction;
	}
	
	public static String getDirectionOfLongitudeCoordinate(double coordinate)
	{
		String direction = "";
		
		if(coordinate > 0)
		{
			direction = "E";
		}
		else if(coordinate < 0)
		{
			direction = "W";
		}
		else
		{
			// TODO not really good: should be nothing
			direction = "E";
		}
		
		return direction;
	}
}
