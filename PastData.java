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
	}//This is initilized from the Program Database class
	
	public void display()
	{
		String input;
		System.out.print(this);
		System.out.print("Press any key to return to previous menu.");
		input = Method.sc.nextLine(); // this outputs all the information of the data of a certain year of a program.
	}//This is initilized from admin's input
	
	public boolean checkProgram(int n)
	{
		return (n==program);
	}// Checks if the programID (n) corresponds with the past data object
	
	public int getYear()
	{
		return year;
	}//returns the year of the data
	
	public String toString()
	{
		return("Year: " + year + "\nLowest average admitted: " + lowestAverage + "\nMean of admission average: " + mean + "\nMedian of admission average: " + median + "\nTotal admission: " + admissionCount+ "\n");
	}//For displaying purposes
}
