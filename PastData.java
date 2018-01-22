import java.util.*;

public class PastData
{

	private int year;
	private double lowestAverage;
	private double mean;
	private double median;
	private int admissionCount;
	private int program;

	public PastData(int y, int p, double lowest, double mean, double median, int admission)
	{
		year = y;
		program = p;
		lowestAverage = lowest;
		this.mean = mean;
		this.median = median;
		admissionCount = admission;
	}
	
	public void display()
	{
		String input;
		System.out.print(this);
		System.out.print("Press any key to return to previous menu.");
		input = Method.sc.nextLine(); // this outputs all the information of the data of a certain year of a program.
	}
	
	public boolean checkProgram(int n)
	{
		return (n==program);
	}
	
	public int getYear()
	{
		return year;
	}
	
	public String toString()
	{
		return("Year: " + year + "\nLowest average admitted: " + lowestAverage + "\nMean of admission average: " + mean + "\n Median of admission average: " + median + "\n Total admission: " + admissionCount);
	}
}