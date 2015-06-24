package bscipt;

public class Main {

	public static void main(String[] args) 
	{
		// start the clock for the entire program
		final long startTime = System.nanoTime();
		
		new Evolver();
		
		final long total_duration = (System.nanoTime() - startTime) / 1000000;
		System.out.println("DONE!");
		System.out.println("\n");
		System.out.println("                  H: M: S ");
		int seconds = (int) total_duration/1000;
		int s = seconds % 60;
		int m = (seconds / 60) % 60;
		int h = (seconds / (60 * 60)) % 24;
		
		
		
		String ss = ""+s;
		if(s < 10)
		{
			ss = "0"+s;
		}
		
		String mm = ""+m;
		if(m < 10)
		{
			mm = "0"+m;
		}
		
		String hh = ""+h;
		if(h < 10)
		{
			hh = "0"+h;
		}
		
		
		System.out.println("Total Duration: ["  + hh + ":" + mm + ":" + ss + "]");
	}

}
