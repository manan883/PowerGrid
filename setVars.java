import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.*;
public class setVars {
	private static double maxWattage;
	private static int timeSteps;
	private static String fileName;
	protected static int totalLines;
	protected static int smartLines;
	protected static int notSmartLines;
	protected static ArrayList<Integer> locations;
	protected static String[] total;
	protected static String[] smart;
	protected static String[] notSmart;
	protected static double[] powerUsage;
	protected static double[] powerUsageSmart;
	protected static double[] lowPwrMode;
	protected static double[] powerUsageNotSmart;
	protected static double[] probOnSmart;
	protected static double[] probOnNotSmart;
	protected static double[] probOn;

	public setVars() {
		maxWattage = 0;
		timeSteps = 0;
		fileName = "output.txt";
	}
	//set some set and get methods 
	public static void setMaxWattage(double power) {
		maxWattage = power;
	}
	public static double getMaxWattage() {
		return maxWattage;
	}
	public static void setTimeSteps(int t) {
		timeSteps = t;
	}
	public static int getTimeSteps() {
		return timeSteps;
	}
	public static void setFileName(String s) {
		fileName = s;
	}
	public static String getFileName() {
		return fileName;
	}
	public static int getLocation(int k) {
		return locations.get(k);
	}
	public static String getSmartAppList(int m) {
		return smart[m];
	}
	public static String getNotSmartAppList(int m) {
		return notSmart[m];
	}
	public static double getPowerUsageSmart(int m) {
		return powerUsageSmart[m];
	}
	public static double getLowPowerMode(int m) {
		return lowPwrMode[m];
	}
	public static double getPowerUsageNotSmart(int m) {
		return powerUsageNotSmart[m];
	}
	public static double getPowerUsage(int m) {
		return powerUsage[m];
	}
	public static double getProbOnSmart(int m) {
		return probOnSmart[m];
	}
	public static double getProbOnNotSmart(int m) {
		return probOnNotSmart[m];
	}
	public static double getProbOn(int m) {
		return probOn[m];
	}
	
	public static String getFileLine(int m) {
		return total[m];
	}
	public static boolean isItSmart(int m) {
		if(powerUsage[m] == powerUsageSmart[m]) {
			return true;
		}
		else {
		return false;
		}
	}
	public static double smartDifference(int m) {
		return powerUsageSmart[m] - lowPwrMode[m];
	}
	public static void test() {
		
		for(int i = 0; i < smart.length; i++) {
			String str = smart[i];
			String[] strA = str.split(",",0);
			System.out.println(strA[0]);
		}
	}

	
	
	//parse file into arrays
	public static void seperate() throws NumberFormatException, FileNotFoundException {
	totalLines = 0;
	smartLines = 0;
	notSmartLines = 0;
	File in = new File("output.txt");
	Scanner sc = new Scanner(in);
	Scanner scnr = new Scanner(in);
	while(sc.hasNextLine()) {
		sc.nextLine();
		totalLines++;
	}
	while(scnr.hasNextLine()) {
		String str = scnr.nextLine();
		if(str.contains("true")) {
			smartLines++;
			notSmartLines = totalLines - smartLines;
		}
	}
	locations = new ArrayList<Integer>(totalLines);
	smart = new String[smartLines];
	//Arrays.fill(smart,"");
	notSmart = new String[notSmartLines];
	fileToArray();
	locationSeperation();
	smartSeperate();
	notSmart();
	powerUsage();	
	probOn();
}
public static void fileToArray() throws FileNotFoundException, NumberFormatException {
	total = new String[totalLines];
	File in = new File("output.txt");
	Scanner scnr = new Scanner(in);
	int i = 0;
	while(scnr.hasNext()) {
		total[i] = scnr.nextLine();
		i++;
	}
	scnr.close();
}
public static void locationSeperation() throws FileNotFoundException, NumberFormatException {
	File in = new File("output.txt");
	Scanner scnr = new Scanner(in);

	int i = 0;
	while(scnr.hasNext()) {
		StringTokenizer stringToken = new StringTokenizer(scnr.nextLine());
		locations.add(Integer.parseInt(stringToken.nextToken(",")));
		i++;
	}

}
public static void smartSeperate() throws FileNotFoundException {
	File tst = new File("output.txt");
	Scanner s = new Scanner(tst);

	int i = 0;
	int k = 0;
	while(s.hasNextLine()) {
		String str = s.nextLine();
		if(str.contains("true")) {
			smart[i] = str;				
			i++;
		}

	
	}
	
	s.close();
}
public static void notSmart() throws FileNotFoundException {
	int k = 0;
	File tst = new File("output.txt");
	Scanner sc = new Scanner(tst);
	while(sc.hasNextLine()) {
		String str = sc.nextLine();
		if(str.contains("false")) {
			notSmart[k] = str;		
			k++;
		}
	
	}
	sc.close();
}
public static void powerUsage() throws FileNotFoundException{
	powerUsage = new double[totalLines];
	powerUsageSmart = new double[smartLines];
	powerUsageNotSmart = new double[notSmartLines];
	lowPwrMode = new double[smartLines];
	for(int i = 0; i < smart.length-1; i++) {
		String temp = smart[i];
		String[] strA = temp.split(",",0);
		powerUsageSmart[i] = Double.parseDouble(strA[2]);
		double percentage = Double.parseDouble(strA[5]);
		double multiplier = 1-percentage;
		double lowpwr = multiplier*powerUsageSmart[i];
		lowPwrMode[i] = lowpwr;
	}
	for(int j = 0; j < notSmart.length; j++) {
		String temp1 = notSmart[j];
		String [] strB = temp1.split(",", 0);
		powerUsageNotSmart[j] = Double.parseDouble(strB[2]);
	}
	for(int k = 0; k < totalLines; k++) {
		String temp2 = total[k];
		String[] strC = temp2.split(",",0);
		powerUsage[k] = Double.parseDouble(strC[2]);
}
	}



public static void probOn() {
	probOnSmart = new double[smartLines];
	probOnNotSmart = new double[notSmartLines];
	probOn = new double[totalLines];
	for(int i = 0; i < smart.length; i++) {
		String temp = smart[i];
		String[] strA = temp.split(",",0);
		//3
		probOnSmart[i] = Double.parseDouble(strA[3]);
		
	}
	for(int j = 0; j < notSmart.length; j++) {
		String temp1 = notSmart[j];
		String[] strB = temp1.split(",",0);
		//3
		probOnNotSmart[j] = Double.parseDouble(strB[3]);
	}
	for(int k = 0; k < totalLines; k++) {
		String temp2 = total[k];
		String[] strC = temp2.split(",",0);
		probOn[k] = Double.parseDouble(strC[3]);
	}
}








} // end class