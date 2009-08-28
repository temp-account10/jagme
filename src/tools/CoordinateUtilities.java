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
}
