package bscipt;

import java.util.Scanner;

public class Node_List 
{
	Node dummy;
	Node first;
	Node last;
	int number_of_variables;
	int length;
	
	public Node_List()
	{
		number_of_variables = 0;
		dummy = new Node(";");
		dummy.next = dummy;
		dummy.prev = dummy;
		length = 0;
	}
	
	public void add_node(Node n)
	{
		if(n.value.trim().length() < 1)
		{
			//System.out.println("I'm not adding an empty Node or only whitespace");
			return;
		}
				
		
		if(first == null)
		{
			first = n;
			n.prev = dummy;
			n.next = dummy;
			dummy.next = n;
			dummy.prev = n;
			
		}
		else
		{
			Node current = first;
			while(current.next != null)
			{
				current = current.next;				
			}
			current.next = n;
			n.prev = current;
		}
		
		last = n;
		
		
		length++;
		if(n.variable)
		{
			number_of_variables++;
		}
		
		check_length();
	}
	
	public void check_length()
	{	
		int i = 0;
		Node current = dummy;
		while(current.next != null)
		{
			current = current.next;
			i++;
		}		
		if(i != length)
		{
			System.out.println("The reported length is: " + length);
			System.out.println("The actual length is:   " + i);
			pause();
		}
	}
	
	public void pause()
	{
	   System.out.println("Press \"ENTER\" to continue...");
	   @SuppressWarnings("resource")
	Scanner scanner = new Scanner(System.in);
	   scanner.nextLine();
	}
	

	public String get_value_of_nth_variable(int n)
	{
		int i = -1;
		Node current = dummy;
		while(i != n && current.next != null)
		{
			current = current.next;
			if(current.variable)
			{
				i++;
			}				
		}
		return current.value;
	}	
	
	
	
	public Node find_nth_variable(int n)
	{
		int i = -1;
		Node current = dummy;
		while(i != n)
		{
			current = current.next;
			if(current.variable)
			{
				i++;
			}				
		}
		return current;
	}
	
	
	public void replace_node_by_nodelist(Node x, Node_List list)
	{
		x.prev.next = list.first;
		x.next.prev = list.last;
		
		list.first.prev = x.prev;
		list.last.next = x.next;
				
		length--;
		length += list.length;
		number_of_variables--;
		number_of_variables += list.number_of_variables;
	}
	
	
	public void replace_nth_variable_by(int n, Node_List list)
	{
		replace_node_by_nodelist(find_nth_variable(n), list);
	}

	
	/*
	//                  = start at 0
	public void replace_nth_variable_by(int n, Node_List list)
	{
		
		System.out.println("Check if both this and the list are valid: ");
		list.check_length();
		
		this.check_length();
		
		
		int i = -1;
		Node current = dummy;
		while(i != n && current.next != null)
		{
			current = current.next;
			if(current.variable)
			{
				i++;
			}
			
				
		}
		
		System.out.println("____________________________________");
		System.out.println("In:      " + this.toString());
		System.out.println(" length: " + this.length);
		System.out.println("Replace: " + current.value + "(" + n + ")");
		System.out.println("By:      " + list.toString());
		System.out.println(" length: " + list.length);
		System.out.println("____________________________________");
		
		current.previous.next = list.first;
		list.first.previous = current.previous;
		
		if(current.next != null)
		{
			current.next.previous = list.last;	
			list.last.next = current.next;
			
			current.previous.next = list.first;
			list.first.previous = current.previous;
		}
		else
		{
			list.last.next = null;
			current.previous.next = list.first;
			list.first.previous = current.previous;
			System.out.println("---- there is no next node in this list");
		}
		
		length--;
		length += list.length;
		number_of_variables--;
		number_of_variables += list.number_of_variables;
	}
	
	*/
	
	public String toString()
	{
		String text = "";
		Node current = dummy;
		
		int loops = 0;
		
		while(current.next != null && loops < 10)
		{
			
			if(current.prev != null && current.next != null)
			{
				System.out.println("...................................");
				System.out.println(" prev:   " + current.prev.value);
				System.out.println(loops + " c:     " + current.value);
				System.out.println(" next:   " + current.next.value);
				System.out.println("...................................");
			}
			
			current = current.next;
			text += current.value;
			loops++;
		}		
		return(text);		
	}
	
	
	public void list()
	{
		System.out.println("This list contains " + number_of_variables + " variables.");
		
		Node current = dummy;
		int i = 0;
		while(current.next != null)
		{
			current = current.next;
			System.out.println(i++ + ". " + current.value);
			
		}	
		
		System.out.println("____________________________________");
	}

}
