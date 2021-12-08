import java.util.*;
import java.nio.*;
import java.io.*;
public class sim {
	protected static double wattageSum;
	protected static double wattageSumSmart;
	protected static double wattageSumNotSmart;
	protected static int counter;
	protected static double[] wattagePerLocation;
	public sim() throws NumberFormatException, FileNotFoundException {
		setVars.seperate();
		
	}
	//just a write method to call that will write to a file with the string passed
	public static void write(String textToWrite, String output) throws IOException {
		File f = new File(output);
		FileWriter fw = new FileWriter(f,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(textToWrite);
		bw.newLine();
		bw.close();
	}
	//simulate method(main one)
	public static void simulate(int timeSteps) throws IOException {
		wattagePerLocation = new double[100];
		Arrays.fill(wattagePerLocation,new Double(0.0));

		counter = 0;
		//makes the file not exist to make it new one
		File f = new File("report");
		f.delete();
		//loop through time steps
		for(int i = 0; i < timeSteps; i++) {
			wattageSumSmart = 0.0;
			wattageSumNotSmart = 0.0;
			wattageSum = 0.0;
			int t = i+1;
			System.out.println("Time step number: " + t);
			write("Time Step Number: " + t,"report");
			Random r = new Random();
			double pOn = r.nextDouble();
			//if if goes through low pwr more than once brown out(not working still)
			wattageSumSmart = checkRandSmart(pOn,wattageSum);
			wattageSumNotSmart = checkRandNotSmart(pOn, wattageSum);
			wattageSum = wattageSumSmart + wattageSumNotSmart;
		
			//check if it goes through low pwr 
			while(wattageSum >= setVars.getMaxWattage()) {
				//low power; if {its still greater than max do brownout} 
				wattageSum = changeToLowPwr(wattageSum,wattageSumSmart,pOn);
				wattageSum = brownOut(wattageSum,pOn);
				
			}
			
		
			
		
			
			
		
			
			
		}//end timestep loop with i
		
	}//end simulate()
	//change all smart to low and update wattage
public static double changeToLowPwr(double wattage, double wattageSmart, double rand) throws IOException {
	double tempwatt = 0.0;
	int count = 0;
	for(int i =0; i < setVars.lowPwrMode.length; i++) {
		if(setVars.getProbOnSmart(i)<= rand) {
		tempwatt = tempwatt+ setVars.getLowPowerMode(i);
			count++;
			String text = "Appliance in low power: " + setVars.getSmartAppList(i);
			//check if its less than max; if so break;
			if((wattageSmart-tempwatt) < setVars.getMaxWattage()) {
				break;
			}
			write(text,"report");
			
		}
	}
	System.out.println("Number of appliances turned to low is: " + count);
	write("Number turned to low: " + count, "report");
	//System.out.println(tempwatt + " tempwatt");

	wattage =   (wattageSmart-tempwatt);
	System.out.println(wattage + " wattage returned on low");
	return wattage;
}
//get power usage for smart
public static double checkRandSmart(double rand, double wattage) {
	for(int i = 0; i < setVars.smart.length; i++) {
		if(setVars.getProbOnSmart(i) <= rand) {
			wattage = wattage + setVars.getPowerUsageSmart(i);
			
		}
	}
	//System.out.println(wattage + " smart");
	return wattage;
}
//get power usage for not smart
public static double checkRandNotSmart(double rand, double wattage) {
	for(int j = 0; j < setVars.notSmart.length; j++) {
		if(setVars.getProbOnNotSmart(j) <= rand) {
			wattage = wattage + setVars.getPowerUsageNotSmart(j);
		}
	}	
	//System.out.println(wattage + " notSmart");
	return wattage;
}
//location wattage
public static double returnWattage(int locationi, int location, double rand) {
	//returns power usage in a location
	double g = 0.0;
	double wattage = 0.0;
	double wattS = 0.0;
	double wattNS = 0.0;
	for(int j = 0; j < (setVars.powerUsageSmart.length); j++) {
		
		String str = setVars.smart[j];
		String[] strA = str.split(",",0);
		if(Integer.parseInt(strA[0]) > 10000000+location) {
			break;
		}
		if(Integer.parseInt(strA[0]) < 10000000+locationi) {
			break;
		}
		else {
			if(setVars.getProbOnSmart(j) <= rand) {
				double temp = Double.parseDouble(strA[2]);
				
			wattS = wattS + temp;
			//System.out.println(wattS);
			}
		}
		
	}
	for(int k = 0; k < setVars.powerUsageNotSmart.length; k++) {
		String str1 = setVars.notSmart[k];
		String[] strB = str1.split(",",0);
		if(Integer.parseInt(strB[0]) > 10000000+location) {
			break;
		}
		if(Integer.parseInt(strB[0]) < 10000000+locationi) {
			break;
		}
		else {
			if(setVars.getProbOnNotSmart(k) <= rand) {
				double t = Double.parseDouble(strB[2]);
				wattNS = wattNS + t;
				//System.out.println(wattNS);
			}
		}
	}
	wattage = wattS + wattNS;
	g = wattage;
	wattage = 0;
	//System.out.println(g + " this is in get method");
	return g;
}
//brown out method
public static double brownOut(double wattage, double rand) throws IOException {
	
	//return power usage per location in an array
	//check if after locations are turned off if its below max; if so break; else continue 
	double[] tempArray = new double[100];
	int[] loc = new int[100];
	double largestWatt = 0.0;
	double tempWatt = 0.0;
	//returnWattage(2,rand);
	for(int i = 1; i < 101; i++) {
		tempArray[i-1] = returnWattage(i-100,i,rand);
		//System.out.println(tempArray[i-1] + " " + i);
		
	}
	for(int j = 0; j < 100; j++) {
		if(j == 0) {
			wattagePerLocation[j] = tempArray[j];
		}
	
		else {
			wattagePerLocation[j] = tempArray[j] - tempArray[j-1];
		}
		loc[j] = 10000000+j+1;
	}
	for(int k = 0; k < 101; k++) {
			tempWatt = tempWatt + wattagePerLocation[k];
			if((wattage-tempWatt) < setVars.getMaxWattage()) {
				break;
			}
			String m = "Locations that are turned off: " + String.valueOf(loc[k]);
			write(m,"report");
		
	}
	

	wattage = wattage-tempWatt;
	return wattage;
}

}
