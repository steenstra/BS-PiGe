package bscipt;

public class Node 
{
	Node prev;
	Node next;
	Boolean variable;
	String value;

	public Node(String s)
	{
		value = s.replaceAll("[\\r\\n]", "");
		if(value.length() > 0 && value.charAt(0) == '<')
		{
			variable = true;
		}		
		else
		{
			variable = false;
		}
	}
	
	
	
}
