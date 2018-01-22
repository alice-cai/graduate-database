import java.util.*;
import java.io.*;

public class PastData_Manager
{

	private ArrayList<PastData> pastData;
	private GraduateDatabase gradData;
		
	public PastData_Manager()
	{
		pastData = new ArrayList<PastData>();
		gradData = new GraduateDatabase(); // graduate database loads from a text file.
	}
	
   
   public GraduateDatabase getGrad()
   {
      return gradData;
   }
   
	public ArrayList<PastData> findPastData (int programID)
	{
		ArrayList<PastData> p = new ArrayList<>();
		for (int i = 0; i < pastData.size(); i++)
		{
			if (pastData.get(i).checkProgram(programID))
				p.add(pastData.get(i));
		}
		return p;
	}
	
	public double admissionAverage(int programID)
	{
		return gradData.calculateMean(programID);
	}
	

	public void addPastData(PastData p)
	{	
      pastData.add(p);
      
		// input info about the program here
	}
	public int getSize()
	{
		return pastData.size();
	}
}