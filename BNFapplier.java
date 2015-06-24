package bscipt;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


// TODO
/*
 * Implement "" for literal
 * Implement NONpi
 * 
 * check out GEVA
 * 
 * 
 */






public class BNFapplier 
{

	Boolean pause = false;
	Boolean print = false;
	Boolean piGE = true;
	public int maxWraps;
	String startSymbol;
	Hashtable<String, List[]> rules;
	String bnf;
	
	
	
	public BNFapplier(String type, int mw)
	{
		maxWraps = mw;
		load_grammar(type);

		
		
		
		
	}
	

	
	public void load_grammar(String type) 
	{
		String path = "/piGE/Grammar/";
		String entireGrammar = loadGrammarFromFile(path + type + ".bnf");
		
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/_logical.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/_mathexpr.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/_mastermind.bnf");
		
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/a_spaceless.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/blender3d_hof.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/efp_grammar_gr.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/HelloWorld_grammar.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/lawnmower.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/letter_grammar.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/LSys_1.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/LSysEx_1.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/max_grammar.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/paint.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/royal_tree.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/sf_grammar_gr.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/sf_interpreter.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/sf_interpreter_enhanced.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/simple_sr.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/sr_grammar_gr.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/sr_grammar_intrp.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/sr_grammar_sch.bnf");
		//String entireGrammar = loadGrammarFromFile("/piGE/Grammar/sudokuEasy0_grammar.bnf");
		
		
		bnf = entireGrammar;
		convertStringToRules(entireGrammar);
	}



	public int[] generateRandomIntArray(int bits, int length) {
		int[] randomIntegers = new int[length];
		
		for(int i = 0; i < length; i++)
		{
			randomIntegers[i] = (int) (Math.random() * Math.pow(2, bits));
		}
		
		return randomIntegers;
	}
	

	public void test()
	{
		List list = new List();
		Node n = new Node("<Test>");
		list.add_node(n);
		
		n = new Node("<Test2>");
		list.add_node(n);
		
		n = new Node("Test3");
		list.add_node(n);
		
		
		list.list();
		
		
		
		List list_2 = new List();
		
		n = new Node("onder");
		list_2.add_node(n);
		
		n = new Node("<deel>");
		list_2.add_node(n);
		
		n = new Node("tje");
		list_2.add_node(n);
		
		list_2.list();
		
		
		list.replaceNthVariableBy(1, list_2);
		list.list();
		
		
		List list_3 = new List();
		
		n = new Node("en_ook");
		list_3.add_node(n);
		
		n = new Node("het");
		list_3.add_node(n);
		
		n = new Node("derde_deel");
		list_3.add_node(n);
		
		list_3.list();
		
		
		
		list.replaceNthVariableBy(0, list_3);
		list.list();
		
/*  BNFapplier() put in constuctor
		
		// picked integer array
		int[] intArray =  {200, 92, 16, 160, 252, 177, 220, 2, 149, 68, 205, 130, 111, 234};
		
		int[] intArray2 = {201, 92, 16, 160, 252, 177, 220, 2, 149, 68, 205, 130, 111, 234};
		
		
		String bitString = recodeIntegersToBitstring(intArray, 8);
		List nodeList = convert_bitstring_to_nodelist(bitString);
		
		String bitString2 = recodeIntegersToBitstring(intArray2, 8);
		List nodeList2 = convert_bitstring_to_nodelist(bitString2);
		
		
		System.out.println("Original:");
		System.out.println(bitString);
		System.out.println(nodeList.toString());
		System.out.println("");
		
		System.out.println("Modified:");
		System.out.println(bitString2);
		System.out.println(nodeList2.toString());
		System.out.println("");
		
		
		
		
		
		
		
		
		
		
		
		
		for(int i = 0; i < 1; i++)
		{
			
			System.out.println("Random:");
			
			// generate 8bit integer array of length 14
			int[] randomIntArray = generateRandomIntArray(8, 14);
			System.out.println(Arrays.toString(randomIntArray));
			
			// recode array to bitstring
			bitString = recodeIntegersToBitstring(randomIntArray, 8);
			System.out.println(bitString);
			
			// calculate phenotype, convert it to a string and print
			nodeList = convert_bitstring_to_nodelist(bitString);
			System.out.println(nodeList.toString());
			
			System.out.println("");
		}
		
		
		*/
		
		
		
	}
	
	
	
	
	
	
	public List convert_bitstring_to_nodelist(String bitString, boolean position_independent)
	{
		List currentPhenotype = new List();
		Node n = new Node(startSymbol);
		currentPhenotype.add_node(n);
		
		int noNT; //number_of_Non_Terminals
		
		int[] codonArray = decodeBitstringToIntegers(bitString, 8);
		
		
		// number of Non-Terminals:
		currentPhenotype.list();
		
		// if Position Independent
		if(position_independent)
		{
			for(int wrapIndex = 0; wrapIndex < maxWraps; wrapIndex++)
			{
				for(int codonIndex = 0; codonIndex < codonArray.length; codonIndex += 2)
				{
					//System.out.println();
					noNT = currentPhenotype.vars;
					
					if(noNT == 0)
					{
						return currentPhenotype;
					}
					currentPhenotype = applyRule(currentPhenotype, codonArray[codonIndex], codonArray[codonIndex+1], position_independent);
				}
			}
		}
		else // standard GE
		{
			for(int wrapIndex = 0; wrapIndex < maxWraps; wrapIndex++)
			{
				for(int codonIndex = 0; codonIndex < codonArray.length; codonIndex += 1)
				{
					//System.out.println();
					noNT = currentPhenotype.vars;
					
					if(noNT == 0)
					{
						return currentPhenotype;
					}
					currentPhenotype = applyRule(currentPhenotype, codonArray[codonIndex], -1, position_independent);
				}
			}
		}
		
		
		
		
		
		return currentPhenotype;
		
	}
	
	public List applyRule(List currentPhenotype, int CHOICEcodon, int RULEcodon, boolean position_independent)
	{
		int noNT = currentPhenotype.vars;
		int chosen_non_terminal = 0;
		
		if(position_independent)
		{
			chosen_non_terminal = RULEcodon % noNT;
		}
		
		
		String key_value = currentPhenotype.getNthVariable(chosen_non_terminal).value;
		List[] choices = rules.get(key_value);
		
		
		
		int chosen_choice = CHOICEcodon % choices.length;
		
		List choice = choices[chosen_choice].clone();
		
		if(print)
		{
			System.out.println("Length: " + currentPhenotype.length);
			System.out.println("The current Phenotype is:");
			System.out.println("\n     " + currentPhenotype.toString() + "\n");
			System.out.println("It contains " + noNT + " Non-Terminals.");
			System.out.println("The " + RULEcodon + "%" + noNT + "= " + chosen_non_terminal + "th NT must be replaced.");
			System.out.println("This is: " + key_value);		
			System.out.println("By the " + CHOICEcodon + "%" + choices.length + "= " + chosen_choice + "th choice.");
			System.out.println("This is: " + choices[chosen_choice]);
			
			System.out.println(" \n... Doing so ...\n");
		}
		
		
		currentPhenotype.replaceNthVariableBy(chosen_non_terminal, choice);
		
		if(print)
		{
			System.out.println("\nCurrent Phenotype: \n" + currentPhenotype.toString() + "\n\n");
			
		}
		
		
		pause();
		
		return currentPhenotype;
		
	}
	
	
	public int[] decodeBitstringToIntegers(String bitstring, int codonsize)
	{
		int length = bitstring.length()/codonsize;
		int[] intCodonArray = new int[length];
		
		for(int i=0; i<length; i++)
		{
			String codon = bitstring.substring(i*codonsize, (i+1)*codonsize);
			intCodonArray[i] = Integer.parseInt(codon, 2);
			
			//System.out.println(codon + " = " + intCodonArray[i]);
		}
		
		return intCodonArray;
	}
	
	public String recodeIntegersToBitstring(int[] intCodonArray, int codonsize)
	{
		String bitstring = "";
		
		for(int i=0; i<intCodonArray.length; i++)
		{
			String binaryString = Integer.toBinaryString(intCodonArray[i]);
			for(int j=0; j<8-binaryString.length(); j++)
			{
				bitstring += "0";
			}
			bitstring += Integer.toBinaryString(intCodonArray[i]);
		}
		
		return bitstring;
	}
	
	
	/// File reading
	
	public String loadGrammarFromFile(String filename)
	{
		String grammar = null;
		try {
			grammar = readFile(filename, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return grammar;
	}
	
	static String readFile(String path, Charset encoding) 
	  throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}
	
	public void pause()
	{
		if(pause)
		{
			System.out.println("Press \"ENTER\" to continue...");
		   @SuppressWarnings("resource")
		   Scanner scanner = new Scanner(System.in);
		   scanner.nextLine();
		}
	   
	}
	
	
	
	// final
	public void convertStringToRules(String rulestring)
	{
		rules = new Hashtable<String, List[]>();		
		Pattern rulePattern = Pattern.compile("<(.*?)> *\t*::= *\t*(.*?)($|\\n|\\r|\\r\\n)"); // double escape because http://www.coderanch.com/t/426877/java/java/remove-carriage-return-string
		Matcher ruleMatcher = rulePattern.matcher(rulestring);
		String firstRule = null;
		
		while (ruleMatcher.find())
		{
			String wholeRule = ruleMatcher.group();
			
			// first split the head and tail of the rule
			String[] splitRule = wholeRule.split("::= ");
			
			// next, divide the tail into different segments; the different options. Note that the | is surrounded with spaces. This is an aesthetic choice.
			String[] expressions = splitRule[1].split(" \\| ");
			
			///System.out.println(wholeRule);
			
			
			List[] List_Array = new List[expressions.length];
			
			for(int i = 0; i < expressions.length; i++)
			{	
				List list = new List();
				
				// cheating to keep delimiters (http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters)
				String string_with_temporary_delimiters = expressions[i].replace("<" , "~<");
				string_with_temporary_delimiters = string_with_temporary_delimiters.replace(">", ">~");
				
				//String noEntersLF = string_with_temporary_delimiters.replace("\n", "");
				
				
				String[] textnodes = string_with_temporary_delimiters.split("~");
								
				//
				
				
				
				
				///
				for(int j = 0; j < textnodes.length; j++)
				{
					Node n = new Node(textnodes[j]);
					list.add_node(n);	
				}
				List_Array[i] = list;
				
				list.list();
			}
			
			
			// keys must not have leading or trailing whitespace
			splitRule[0] = splitRule[0].trim();
			rules.put(splitRule[0], List_Array);
			
			if(firstRule == null)
			{
				firstRule = splitRule[0];
			}
			
			
		}
		
		
		System.out.println(rulestring);
		System.out.println("\nimported BNF grammar \n");
		
		// name of first rule is its start-symbol.
		startSymbol =  firstRule;
		
		System.out.println("HashTable Keys:");
		
		Enumeration<String> e = rules.keys();
	   		
		while (e.hasMoreElements())
	    {
	    	System.out.println(e.nextElement());
	    }
	   
		System.out.println();
		
		
		
	}

	
	
	
	
	// 
}
