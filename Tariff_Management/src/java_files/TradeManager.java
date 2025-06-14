package java_files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TradeManager {
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("Welcome to the Tariff Management program!");
    	while(true) {
    		System.out.println("Chose an option from the following:\n");
    		System.out.println("1. Process Trade Data (Part 1)");
    		System.out.println("2. Resolve Trade Disputes (Part 2)");
    		System.out.println("3. quit");

        int choice = scanner.nextInt();

        if (choice == 1) {
            part1Imp();
        } else if (choice == 2) {
        	part2Imp();
        }else if(choice == 3) {
        	scanner.close();
        	System.exit(1);
        }
    }
    }
    //implementation of part 1 of the assignment
    public static void part1Imp() {
    	try {
			Driver.addTariffs("src/txt_files/TradeData.txt","src/txt_files/UpdatedTradeData.txt");
			System.out.println("\nCheck UpdatedTradeData.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Error: file not found in src folder.");
            System.exit(1);
			
		} 
    }
    
    //implementation of part 2 of the assignment
    public static void part2Imp() {
    	System.out.println("Welcome to the Tariff Management System");
        System.out.println("Developed by [Your Name], Student ID: [Your ID]");
        System.out.println("---------------------------------------------\n");

        // Part (a): Create at least two empty TariffLists
        TariffList tariffList1 = new TariffList();
        TariffList tariffList2 = new TariffList();

        // Part (b): Read Tariffs.txt and populate tariffList1
        try {
            Scanner tariffScanner = new Scanner(new File("src/txt_files/Tariffs.txt"));
            while (tariffScanner.hasNextLine()) {
                String line = tariffScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(" ");
                    if (parts.length == 4) {
                        String destinationCountry = parts[0];
                        String originCountry = parts[1];
                        String productCategory = parts[2];
                        double minimumTariff = Double.parseDouble(parts[3]);
                        
                        // Check for duplicates before adding
                        if (!tariffList1.contains(originCountry, destinationCountry, productCategory)) {
                            Tariff newTariff = new Tariff(destinationCountry, originCountry, productCategory, minimumTariff);
                            tariffList1.addToStart(newTariff);
                        }
                    }
                }
            }
            tariffScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Tariffs.txt file not found in src folder.");
            System.exit(1);
        }

        // Part (c): Process TradeRequests.txt
        ArrayList<String> tradeRequests = new ArrayList<>();
        try {
            Scanner tradeScanner = new Scanner(new File("src/txt_files/TradeRequests.txt"));
            while (tradeScanner.hasNextLine()) {
                String line = tradeScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    tradeRequests.add(line);
                }
            }
            tradeScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: TradeRequests.txt file not found in src folder.");
            System.exit(1);
        }

        // Process each trade request
        for (String request : tradeRequests) {
            String[] parts = request.split(" ");
            if (parts.length == 6) {
                String requestId = parts[0];
                String originCountry = parts[1];
                String destinationCountry = parts[2];
                String productCategory = parts[3];
                double tradeValue = Double.parseDouble(parts[4]);
                double proposedTariff = Double.parseDouble(parts[5]);

                // Find the matching tariff
                TariffList.TariffNode foundNode = tariffList1.find(originCountry, destinationCountry, productCategory);
                if (foundNode != null) {
                    double minimumTariff = foundNode.getTariff().getMinimumTariff();
                    String result = tariffList1.evaluateTrade(proposedTariff, minimumTariff);
                    
                    // Print the outcome with details
                    System.out.print(requestId + " - ");
                    if (result.equals("Trade request accepted.")) {
                        System.out.println("Accepted.");
                        System.out.println("Proposed tariff meets or exceeds the minimum requirement.\n");
                    } 
                    else if (result.equals("Trade request conditionally accepted.")) {
                        double surcharge = tradeValue * ((minimumTariff - proposedTariff) / 100);
                        System.out.println("Conditionally Accepted.");
                        System.out.printf("Proposed tariff %.1f%% is within 20%% of the required minimum tariff %.1f%%.\n", 
                                           proposedTariff, minimumTariff);
                        System.out.printf("A surcharge of $%.2f is applied.\n\n", surcharge);
                    } 
                    else {
                        System.out.println("Rejected");
                        System.out.printf("Proposed tariff %.1f%% is more than 20%% below the required minimum tariff %.1f%%.\n\n", 
                                        proposedTariff, minimumTariff);
                    }
                } else {
                    System.out.println(requestId + " - No matching tariff policy found for this trade request.\n");
                }
            }
        }

        // Part (d): Prompt user to search for tariffs
        Scanner userInput = new Scanner(System.in);
        System.out.println("\nEnter tariff details to search (or 'quit' to exit):");
        while (true) {
            System.out.print("Origin Country: ");
            String origin = userInput.nextLine().trim();
            if (origin.equalsIgnoreCase("quit")) break;
            
            System.out.print("Destination Country: ");
            String destination = userInput.nextLine().trim();
            if (destination.equalsIgnoreCase("quit")) break;
            
            System.out.print("Product Category: ");
            String category = userInput.nextLine().trim();
            if (category.equalsIgnoreCase("quit")) break;

            // Search and display results
            TariffList.TariffNode found = tariffList1.find(origin, destination, category);
            if (found != null) {
                System.out.println("\nTariff found:");
                System.out.println(found.getTariff());
            } else {
                System.out.println("\nNo matching tariff found.");
            }
            System.out.println();
        }
        userInput.close();

        // Part (e): Test other methods (simplified for brevity)
        System.out.println("\nTesting other methods...");
        
        // Test copy constructor
        TariffList copiedList = new TariffList(tariffList1);
        System.out.println("Copied list equals original: " + tariffList1.equals(copiedList));
        
        // Test adding a new tariff
        Tariff testTariff = new Tariff("Canada", "Mexico", "Energy", 15.0);
        tariffList1.addToStart(testTariff);
        System.out.println("Added new tariff, new size: " + tariffList1.getSize());
        
        // Test finding the new tariff
        TariffList.TariffNode foundTest = tariffList1.find("Mexico", "Canada", "Energy");
        System.out.println("Found test tariff: " + (foundTest != null));
        
        System.out.println("\nProgram completed successfully.");
    }
}
