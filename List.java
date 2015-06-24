package bscipt;

import java.util.Scanner;

public class List 
{
	boolean pause = false;
	int length;
	int vars;
	Node dummy;
	
	List()
	{
		dummy = new Node("dummy");
		dummy.next = dummy;
		dummy.prev = dummy;
		length = 0;
		vars = 0;
	}
	
	public List clone()
	{
		List clone = new List();
		
		Node current = dummy;
				
		while(current.next.value != "dummy")
		{
			
			current = current.next;
			Node clonode = new Node(current.value);
			clone.add_node(clonode);
		}	
		return clone;
	}
	
	
	public Node getNode(String value)
	{
		Node current = dummy;
		while(current.value != value)
		{
			current = current.next;
			if(current == dummy)
			{
				//System.out.print("End Of The Line");
				return null;
			}
		}
		return current;		
	}
	
	public Node getNthVariable(int n)
	{
		//System.out.println("Finding the " + n + "th variable.");
		Node current = dummy;
		int i = -1;
		int j = 0;
		
		while(i < n)
		{
			current = current.next;
			if(current.variable)
			{
				i++;
			}
			else
			{
				j++;
				if(j > length)
				{
					//System.out.println("something's wrong");
				}
			}
		}

		return current;			
	}
	
	public void add_node(Node node)
	{
		if(node.value.trim().length() < 1)
		{
			////System.out.println("I'm not adding an empty Node or only whitespace");
			return;
		}
		
		node.prev = dummy.prev;
		node.next = dummy;
		node.next.prev = node;
		node.prev.next = node;
		
		length++;
		if(node.variable)
		{
			vars++;
		}
	}
	
	public void replaceNthVariableBy(int n, List list)
	{
		replaceNodeByList(getNthVariable(n), list);
	}
	
	public void replaceNodeByList(Node node, List list)
	{
		//System.out.println("Replacing " + node.value);
		//System.out.println("By " + list.toString());
		
		if(!node.variable)
		{
			//System.out.println("ERROR: this is not a variable.");
			pause();
		}
		
		//list.dummy.next = first list node
		//list.dummy.prev = last list node
		
		
		/*
		  	     A         C
		         -->       -->       
		prenode       node     postnode
		         <--       <-- 
			     B         D
			  
		A = node.previous.next = prenode.next
		B = node.previous
		C = node.next
		D = node.next.previous = postnode.previous  
			  
		*/
		
		Node prenode = node.prev;
		Node postnode = node.next;
		
		Node listfirst = list.dummy.next;
		Node listlast = list.dummy.prev;
		
		
		////System.out.println(prenode.value + " -> " + node.value + " -> " + postnode.value);
		////System.out.println(prenode.value + " -> " + listfirst + " -> " + postnode.value);
		
		
		//System.out.println(this.toString());
		
		/*
			 	     A         C
			        n->       n->       
			prenode       node     postnode
			        <-p       <-p 
				     B         D
		*/
		
		
		prenode.next = listfirst;
		listfirst.prev = prenode;
		
		postnode.prev = listlast;
		listlast.next = postnode;
		
		////System.out.println(prenode.value + " -> " + listfirst.value + "..." + listlast.value + " -> " + postnode.value);
		////System.out.println(prenode.value + " -> " + prenode.next.value + "..." + prenode.next.next.value + " -> " + prenode.next.next.next.value);
		
		
		////System.out.println(this.toString());
		
		
				
		length--;
		length += list.length;
		
		vars--;
		vars += list.vars;
		
		// 
		/*
		 
		  old:____________
		  
		
		list.dummy.next.prev = n.next;
		list.dummy.prev.next = n.prev;
		
		n.prev.next = list.dummy.next;
		n.next.prev = list.dummy.prev;
	
		  _________________
		  
		  
		  
		  
		  Node addBefore(Node w, T x) {
        Node u = new Node();
        u.x = x;
        u.prev = w.prev;
        u.next = w;
        u.next.prev = u;
        u.prev.next = u;
        n++;
        return u;
    }
		 */
	}
	
	
	
	// debug
	
	public void list()
	{
		//System.out.println("This list contains " + vars + " variables:");
		
		Node current = dummy;
		//int i = 1;
		while(current.next.value != "dummy")
		{
			current = current.next;
			//System.out.println(i++ + ". " + current.value);
			
		}	
		
		//pause();
	}
	

	public String toString()
	{
		
		Node current = dummy;
		String text = "";
		
		
		int loops = 0;
		
		while(current.next.value != "dummy")
		{
			
			
			//if(current.prev != null && current.next != null)
			//{
			//	//System.out.println("...................................");
			//	//System.out.println(" prev:   " + current.prev.value);
			//	//System.out.println(loops + " c:     " + current.value);
			//	//System.out.println(" next:   " + current.next.value);
			//	//System.out.println("...................................");
			//}
			
			current = current.next;
			text += current.value;
			loops++;
			//System.out.println(loops + current.value);
			if(loops > 2000)
			{
				break;
			}
		}
		
		return(text);		
	}
	
	
	
	public void pause()
	{
		if(pause)
		{
			System.out.println("\n    \"ENTER\" to continue...");
			   
			   @SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			   scanner.nextLine();
		}
	   
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
