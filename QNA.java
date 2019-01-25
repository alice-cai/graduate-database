// Class: 	QNA
// Author: 	Ordencia Wu
// Date: 	January 15, 2018
// School: 	A.Y Jackson Secondary School
// Purpose: This class contains fields of a QNA object (representing each Q&A), acessors and mutators, and a method to return 
//			information of each QNA object as String

public class QNA
{
	private String question;   //fields of each QNA object
	private String answer;
	private String category;
	private int id;

	public QNA (int id, String question, String answer, String category) // constructor takes in all fields as parametres
	{
		this.id = id;  // initialize each field
		this.question = question;
		this.answer = answer;
		this.category = category;
	} // constructor

	public String getQuestion ()  // a group of accessors and mutators for each field
	{
		return question;
	}

	public void setQuestion (String question)
	{
		this.question = question;
	}

	public String getAnswer ()
	{
		return answer;
	}

	public void setAnswer (String answer)
	{
		this.answer = answer;
	}

	public String getCategory ()
	{
		return category;
	}

	public void setCategory (String category)
	{
		this.category = category;
	}

	public int getId ()
	{
		return id;
	}

	public void setId (int id)
	{
		this.id = id;
	}

	public String toString ()   // this method returns content of each QNA in organized format
	{
		if (answer.isEmpty())   // if QNA is not answered
		{                       // output only three elements
			return "ID: " + id + "\nQuestion: " + question + "\nCategory: " + category; 
		}
		else                    // otherwise output all elements
		{
			return "ID: " + id + "\nQuestion: " + question + "\nAnswer: " + answer + "\nCategory: " + category;
		}
	}  // toString method
}  // QNA class