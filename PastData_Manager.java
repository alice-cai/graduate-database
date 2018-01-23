import java.util.*;
import java.io.*;

public class PastData_Manager
{

	private ArrayList<PastData> pastData;
	private GraduateDatabase gradData;
		
	public PastData_Manager()
	{
		pastData = new ArrayList<PastData>();
		gradData = InformationSystem.getGraduateDatabase(); // graduate database loads from a text file.
	}//Initializes the PastData_Manager class from file
	
   
   public GraduateDatabase getGrad()
   {
      return gradData;
   }// This is required for the program database to make use of the graudate database
   
	public ArrayList<PastData> findPastData (int programID)
	{
		ArrayList<PastData> p = new ArrayList<>();
		for (int i = 0; i < pastData.size(); i++)
		{
			if (pastData.get(i).checkProgram(programID))
				p.add(pastData.get(i));
		}
		return p;
	}//Searches the pastdata given the programID
	
	public double admissionAverage(int programID)
	{
		return gradData.calculateMean(programID);
	}//Returns the admission average based on the past data of a particular program
	

	public void addPastData(PastData p)
	{	
      pastData.add(p);
      
	}//This adds a past data object into the array
	
	public int getSize()
	{
		return pastData.size();
	}//Returns the number of past data objects
}