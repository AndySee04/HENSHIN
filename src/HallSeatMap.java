package Movie_App;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class HallSeatMap {
	
	private static final String bookingFile = "Bookings.txt";

    // Method to get seat details for booking
	public static String getSeatDetails(String hallName, String date, String time, int quantity) {
	    StringBuilder selectedSeats = new StringBuilder(); 

	    // Define seat columns and rows
	    String[] seat_columns = { 
	        "\n\t      1 ", "2 ", "3 ", "4 ", "5 ", "6 ", "7 ", "8 ", "9 "
	    };
	    String[] seat_rows = {
	        "A", "B", "C", "D", "E", "F"
	    };

	    // Initialize 2-dimensional array of seat_numbers
	    String[][] seat_numbers = {
	        {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
	        {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
	        {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
	        {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
	        {"0", "0", "0", "0", "0", "0", "0", "0", "0"},
	        {"0", "0", "0", "0", "0", "0", "0", "0", "0"}
	    };

	    // Retrieve the previously booked seats for the specified hall, date, and time
	    StringBuilder previousSeats;
	    previousSeats = getSeatNumbers(hallName, date, time);

	    // Mark the previously booked seats in the seat_numbers array
	    markPreviousSeats(seat_numbers, previousSeats.toString());

	    Scanner scanner = new Scanner(System.in);

	    // Loop to select seats until required quantity is met
	    do {
	        printSeatMap(seat_numbers, seat_columns, seat_rows);
	        try {
	            // Prompt user to select seat
	            System.out.println("\nEnter row and column number to select your preferred seat: ");
	            String input = scanner.next();
	            input = input.toUpperCase();

	            // Extract row and column from user input
	            char rowIdentifier = input.charAt(0);
	            int col = Integer.parseInt(input.substring(1)) - 1;
	            int row = rowIdentifier - 'A';

	            // Check if selected seat is valid
	            if (row >= 0 && row < seat_rows.length && col >= 0 && col < seat_columns.length) {
	                if (seat_numbers[row][col].equals("0")) {
	                    seat_numbers[row][col] = "X"; // Mark seat as selected
	                    selectedSeats.append(seat_rows[row]).append(input.substring(1)).append(" ");
	                    printSeatMap(seat_numbers, seat_columns, seat_rows); // Display updated seat map
	                    System.out.println("\nSelected successfully!");
	                    quantity--; // Remaining ticket quantity is reduced by 1
	                    System.out.println("You still need to select " + quantity + " more seat.");
	                } else {
	                    System.out.println("The seat has been selected, please select another seat!");
	                }
	            } else {
	                System.out.println("Please enter a valid seat!");
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("Please enter a valid seat!");
	        }
	    } while (quantity > 0); // Continue loop until no more tickets left

	    
	    // Convert selectedSeats from StringBuilder into an array of seat
	    String[] selectedSeatsArray = selectedSeats.toString().split("\\s+");

	    // Sort the array of seat in alphabetical and numerical order
	    Arrays.sort(selectedSeatsArray);
	 
	    // Go through the sorted list of seat one by one
	    StringBuilder seatsString = new StringBuilder();
	    for (String seat : selectedSeatsArray) {
	        seatsString.append(seat).append(" ");
	    }
	    
	    // Remove trailing comma and space
	    String formattedSeats = seatsString.substring(0, seatsString.length() - 1);
	    
	    // Print the formatted list of sorted selected seats
	    System.out.println("\nYour seats are: " + formattedSeats);
	    
	    return formattedSeats; // Return formatted list of sorted selected seats
}

	// Method to print the seat map
    private static void printSeatMap(String[][] seat_numbers, String[] seat_columns, String[] seat_rows) {
    	// Display the seat map
	    System.out.println("");
        System.out.println("\t  =================================");
        System.out.println("\t  |                               |");
        System.out.println("\t  |             SCREEN            |");
        System.out.println("\t  |                               |");
        System.out.println("\t  =================================");
        
        // Print seat columns
        for (int i = 0; i < seat_columns.length; i++) {
            System.out.print(" " + seat_columns[i]);
        }
        System.out.println("\n\t   _______________________________");

        // Print seat rows and seat numbers
        for (int rowIdx = 0; rowIdx < seat_rows.length; rowIdx++) {
            System.out.print("\n\t" + seat_rows[rowIdx] + "  |");
            for (int colIdx = 0; colIdx < seat_numbers[rowIdx].length; colIdx++) {
                System.out.print("  " + seat_numbers[rowIdx][colIdx]);
            }
            System.out.println("  |");
        }
        
        // Display seat map footer
        System.out.println(" ___________________________________________________");
    }
    
    
    //Method to mark the previous seats has been selected for the current seat selection session.
    private static void markPreviousSeats(String[][] seat_numbers, String previousSeats) {
        String[] bookedSeats = previousSeats.split(" ");
        for (String seat : bookedSeats) {
            char row = seat.charAt(0);
            int col = Integer.parseInt(seat.substring(1)) - 1;
            int rowIndex = row - 'A';
            if (rowIndex >= 0 && rowIndex < seat_numbers.length && col >= 0 && col < seat_numbers[rowIndex].length) {
                seat_numbers[rowIndex][col] = "X"; // Mark seat as booked
            }
        }
    }
    
    //Method to get the previous seat numbers from Bookings.txt file
	private static StringBuilder getSeatNumbers(String hallName, String date, String time) {
        StringBuilder allSeats = new StringBuilder();
		try {
            BufferedReader reader = new BufferedReader(new FileReader(bookingFile)); //Read the file 
            String line;
            
            while ((line = reader.readLine()) != null) {

            	String[] attributes = line.split(",");
            	String dateSearch = attributes[3].trim(); 			// Extract date
            	String timeSearch = attributes[4].trim(); 			// Extract time 
            	String hallNameSearch = attributes[5].trim(); 		// Extract hall
            	String seatsSearch = attributes[7].trim(); 			// Extract seats
         
            	if ((dateSearch.equals(date))&&(timeSearch.equals(time))&&(hallNameSearch.equals(hallName))) {
            		
            		allSeats.append(seatsSearch).append(" ");
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return allSeats;
	}
}