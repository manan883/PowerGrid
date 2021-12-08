/* This is a stub code. You can modify it as you wish. */

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
class AppClient{
	protected static int lines;
	
	public static void listApp() throws IOException {
		//list everything
		File f = new File("ApplianceDetail.txt");
		Scanner sc = new Scanner(f);
		while(sc.hasNextLine()) {
			System.out.println(sc.nextLine());
		}
	}
	public static void deleteApp(String m) throws IOException {
		//delete line from file by making a new file and skipping the line wanting to be deleted. then rename temp file to input
		File input = new File("ApplianceDetail.txt");
		File temp = new File("temp.txt");
		BufferedReader br = new BufferedReader(new FileReader(input));
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
		String lineToRemove = m;
		String current;
		while((current = br.readLine()) != null ) {
			String trimmedLine = current.trim();
			if(trimmedLine.equals(lineToRemove)) continue;
			if(trimmedLine.equals("\r\n")) {
				continue;
			}
			bw.write(current + System.getProperty("line.separator"));
			
		}

		temp.renameTo(input);
		//input.delete();
		br.close();
		bw.close();
		
	}

	public static void addApp(String m) throws IOException {
		//adds app based on a string. writes it 
		FileWriter fw = new FileWriter("ApplianceDetail.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		fw.write("\r\n");
		fw.write(m);
		bw.flush();
		fw.close();
	}
	public static void addAppFromFile(String fileName) throws IOException {
		//appends 2 files
		FileWriter fw = new FileWriter("ApplianceDetail.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		BufferedReader bb = new BufferedReader(new FileReader("ApplianceDetail.txt"));
		String str;
		//bw.newLine();
		while((str = br.readLine()) != null) {
			bw.newLine();
			bw.write(str);
			bw.flush();
			
		}
		lines = 0;
		while((str = bb.readLine()) != null) {
			lines++;
		}
		bw.close();
		br.close();
		fw.close();
		bb.close();
		System.out.println(lines);
	}
	
	
	public static void main(String []args) throws NumberFormatException, IOException{
		

		//User interactive part
		String option1 = "";

		
		while(true){// Application menu to be displayed to the user.
			System.out.println("Enter the maximum wattage for the simulation: ");
			Scanner scan = new Scanner(System.in);
			String maxW = scan.nextLine();
			if(maxW.matches("-?\\d+(\\.\\d+)?")) {
				if(Double.parseDouble(maxW)>0) {
				double maxw = Double.parseDouble(maxW);
				System.out.println("Enter the number of time steps: ");
				System.out.flush();
				String ts = scan.nextLine();
				if(ts.matches("-?\\d+(\\.\\d+)?")) {
					if(Integer.parseInt(ts)>0) {
					int tt = Integer.parseInt(ts);
					setVars.setMaxWattage(maxw);
					setVars.setTimeSteps(tt);
					//sim.simulate(tt);
				}
				}
				}
				else {
					System.out.println("Invalid Input");
				}
				
			}
			else {
				System.out.println("Invalid Input");
			}
			System.out.println("Select an option:");
			System.out.flush();
			System.out.println("Type \"A\" Add an appliance");
			System.out.flush();
			System.out.println("Type \"D\" Delete an appliance");
			System.out.flush();
			System.out.println("Type \"L\" List the appliances");
			System.out.flush();
			System.out.println("Type \"F\" Read Appliances from a file");
			System.out.flush();
			System.out.println("Type \"R\" Start Simulation");
			System.out.flush();
			System.out.println("Type \"Q\" to Quit");
			System.out.flush();
			option1=scan.nextLine();

			/* Complete the skeleton code below */
			if(option1.equals("q") || option1.equals("Q")) {
				break;
			}
			else if(option1.equals("F") || option1.equals("f")) {
				ApplianceGenerator.main(null);
				System.out.println("Output.txt file has been randomized");
				System.out.println();
				
			}
			else if(option1.equals("A") || option1.equals("a")) {
				System.out.flush();
				System.out.println("Type 1 to append from a string");
				System.out.flush();
				System.out.println("Type 2 to append from a file");
				System.out.flush();
				String choice = scan.nextLine();
				if(!choice.equals("1") && !choice.equals("2")) {
					System.out.println("Invalid Input");
					break;
				}
				int c1 = Integer.parseInt(choice);
				if(c1 == 1) {
				System.out.println("Enter the appliance name, power used while on, power used while off or in low power mode, the probability its on, if its smart or not, the prob its smart: eg: ");
				System.out.println("Air Conditioner - 10000 BTU,1000,100,0.25,TRUE,0.5");
				String add = scan.nextLine();
				addApp(add);
				System.out.println();
				}
				else if(c1 == 2) {
					System.out.println("Type the file name with the .txt");
					System.out.flush();
					//scan.nextLine();
					String f = scan.nextLine();
					addAppFromFile(f);
					System.out.println();

				}
				else {
					System.out.println("Invalid Input");
				}
			}
			else if(option1.equals("D") || option1.equals("d")) {
				System.out.println("Give the exact string you want to delete ");
				String line = scan.nextLine();
				deleteApp(line);
				System.out.println();
			}
			else if(option1.equals("L") || option1.equals("l")) {
				listApp();
				System.out.println();
			}
			else if(option1.equals("R") || option1.equals("r")) {
				ApplianceGenerator.main(null);
				setVars.seperate();
				sim.simulate(setVars.getTimeSteps());
				System.out.println();
//				System.out.println("Enter the maximum wattage for the simulation: ");
//				String maxW = scan.nextLine();
//				if(maxW.matches("-?\\d+(\\.\\d+)?")) {
//					double maxw = Double.parseDouble(maxW);
//					System.out.println("Enter the number of time steps: ");
//					System.out.flush();
//					String ts = scan.nextLine();
//					if(ts.matches("-?\\d+(\\.\\d+)?")) {
//						int tt = Integer.parseInt(ts);
//						setVars.setMaxWattage(maxw);
//						sim.simulate(tt);
//					}
//					else {
//						System.out.println("Invalid Input");
//					}
//					
//				}
//				else {
//					System.out.println("Invalid Input");
//				}
//				System.out.println("Enter the number of time steps: ");
//				System.out.flush();
//				String ts = scan.nextLine();
//				int tt = Integer.parseInt(ts);
//				setVars.setMaxWattage(maxw);
//				sim.simulate(tt);
			}
			else {
				System.out.println("Invalid Input");
				System.out.println();
			}
			
				
		}
		
	}
}