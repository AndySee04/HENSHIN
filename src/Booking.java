package Movie_App;

import java.io.*;
import java.util.*;

public class Booking 
{	
	private ArrayList<Booking> bookings = new ArrayList<>();
	private String bookingID;
	private String customername;
	private String moviename;
	private String date;
	private String time;
	private String hall;
	private int quantity;
	private String seat;
	private double price;
	
	private static final Scanner input = new Scanner(System.in);
	private static final String bookingFile = "Bookings.txt";
	
	// Assessor for bookings
	public ArrayList<Booking> getBookings()
	{
		return bookings;
	}
	public String getBookingID()
	{
		return bookingID;
	}
	public String getCustomerName()
	{
		return customername;
	}
	public String getMovieName()
	{
		return moviename;
	}
	public String getDate()
	{
		return date;
	}
	public String getTime()
	{
		return time;
	}
	public String getHall()
	{
		return hall;
	}
	public int getTicketQuantity()
	{
		return quantity;
	}
	public String getSeat()
	{
		return seat;
	}
	public double getPrice()
	{
		return price;
	}

	// Method to generate booking ID
    private String generateBookingId() 
    {
        // Get the last booking ID from Bookings.txt and generate the next one
        String lastBookingId = getLastBookingId();
        int lastBookingNum = Integer.parseInt(lastBookingId.substring(1));
        int nextBookingNum = lastBookingNum + 1;
        return "B" + String.format("%03d", nextBookingNum);
    }
    
	// Method to get last booking ID from Bookings.txt
    private String getLastBookingId() 
    {
        String lastBookingId = "B000"; // Initialize as "B000" instead of "B001"
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingFile))) 
        {
            String line;
            // Read each line until the end of file
            while ((line = reader.readLine()) != null) 
            {
            	lastBookingId = line.split(",")[0];
            }
        } 
        catch (IOException | NumberFormatException e) 
        {
            e.printStackTrace();
        }
        return lastBookingId;
    }
    
	// The constructor for Booking
	public Booking(String customername, String moviename, String date, String time, String hall, int quantity,String seat, double price)
	{
		this.bookingID = generateBookingId();
		this.customername = customername;
		this.moviename = moviename;
		this.date = date;
		this.time = time;
		this.hall = hall;
		this.quantity = quantity;
		this.seat = seat;
		this.price = price;
	}

    // Method to login as customer
    public static void loginCustomer(String customerName) {
    	System.out.println("");
        System.out.println("===============================================");
        System.out.println("========         CUSTOMER PAGE         ========");
        System.out.println("===============================================");
        
    	// Display welcome message
        System.out.println("Welcome back " + customerName + "!");
        
        int choice = 0;

        // Main menu loop
        do {
            // Display main menu options
        	System.out.println("");
        	System.out.println("-------------------------------");
            System.out.println("Do you want to:       ");
            System.out.println("[1] Check movies available now.");
            System.out.println("[2] View your bookings.        ");
            System.out.println("[3] Exit program.              ");
        	System.out.println("-------------------------------");
            
            // Get user choice
            choice = Choice.getUserChoice(input, 3);
            switch (choice) {
            case 1:
                // Select movie bookings
                selectMovieBooking(customerName);
                break;
            case 2:
                // View bookings for this customer
                Booking.viewBookingsCustomer(customerName);
                break;
            case 3:
            	// Exit program
                System.out.println("Thank you for using our application!");
                System.out.println("Exiting program...");
                System.exit(0);                    
                break;
            }
        } while (choice != 5);
    }
    
    // Method to login as admin
    public static void loginAdmin(String adminName) {
    	System.out.println("");
        System.out.println("===============================================");
        System.out.println("========          ADMIN PAGE          =========");
        System.out.println("===============================================");
        
    	// Display welcome message
        System.out.println("Welcome back " + adminName + "!");
        
        int choice = 0;

        // Main menu loop
        do {
            // Display main menu options
        	System.out.println("");
        	System.out.println("--------------------");
            System.out.println("Do you want to:   ");
            System.out.println("[1] Edit bookings.  ");
            System.out.println("[2] Cancel bookings.");
            System.out.println("[3] Exit program.   ");
        	System.out.println("--------------------");
            
            // Get user choice
            choice = Choice.getUserChoice(input, 3);
            switch (choice) {
            case 1:
                // Edit bookings
                System.out.println("\nEnter Booking ID to search for:");
                String bookingIDInput = input.nextLine();
                String matchingBookings = "";
                
                matchingBookings = Booking.viewBookingsAdmin(bookingIDInput);
                if (!matchingBookings.isBlank())                     	
                	Booking.modifyBookings(matchingBookings);
                break;
            case 2:
            	// Cancel bookings
                Booking.cancelBooking();
                break;
            case 3:
            	System.out.println("Thank you for using our application!");
                System.out.println("Exiting program...");
                System.exit(0);                    
                break;
            }
        }while(choice != 3);    
    }
    
    // Method to select movie booking
    public static void selectMovieBooking(String customerName) {
        ArrayList<Movie> movieList = Movie.getMovieList();

        String buyTicketChoice = "";
        int movieChoice = 0;
        String confirmBookingChoice = "";
        Movie selectedMovie = null;

        do {
            do {
            	// Display header for movie selection
            	System.out.println("");
            	System.out.println("==================================================");
                System.out.println(" _____              _____ _             _         ");
                System.out.println("|   | |___ _ _ _   |   __| |_ ___ _ _ _|_|___ ___ ");
                System.out.println("| | | | . | | | |  |__   |   | . | | | | |   | . |");
                System.out.println("|_|___|___|_____|  |_____|_|_|___|_____|_|_|_|_  |");
                System.out.println("                                             |___|");
            	System.out.println("==================================================");
                
                // Display list of available movies
                for (int i = 0; i < movieList.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + movieList.get(i).getName());
                }

                // Get customer's movie choice
                movieChoice = Choice.getUserChoice(input, movieList.size());
                
                // Initialize movie details into selectedMovie
                int movieIndex = movieChoice - 1;
                selectedMovie = movieList.get(movieIndex);

                System.out.println("");
                System.out.println("-------------");
                System.out.println("MOVIE DETAILS");
                System.out.println("-------------");
                System.out.println(selectedMovie);
                System.out.println("\nNote: You can only book tickets for two days in advance.");
                System.out.print("\nDo you want to buy a ticket for this movie? (y / n): ");

                buyTicketChoice = input.nextLine(); //For "Y / y", repeat movie selection process
            } while (!buyTicketChoice.equalsIgnoreCase("y"));

            // Date selection
            System.out.println("");
            System.out.println("----");
            System.out.println("DATE");
            System.out.println("----");
            System.out.println("[1] 13/05/2024 (Thu)");
            System.out.println("[2] 14/05/2024 (Fri)");
            int selectedDate = Choice.getUserChoice(input, 2);
            selectedMovie.setDate(selectedDate);

            // Time selection
            System.out.println("");
            System.out.println("----");
            System.out.println("TIME");
            System.out.println("----");
            System.out.println("[1] Morning (8:00 am)");
            System.out.println("[2] Evening (4:00 pm)");
            int selectedTime = Choice.getUserChoice(input, 2);
            selectedMovie.setTime(selectedTime);

            //Quantity selection
            System.out.println("");
            System.out.println("--------");
            System.out.println("QUANTITY");
            System.out.println("--------");
            System.out.println("Min Ticket Quantity: 1 ticket");
            System.out.println("Max Ticket Quantity: 5 ticket(s)");
            int quantity = Choice.getUserChoice(input, 5);

            // Hall details
            String hallName = "";
            switch (movieChoice) {
                case 1:
                    hallName = "Hall 1";
                    break;
                case 2:
                    hallName = "Hall 2";
                    break;
                case 3:
                    hallName = "Hall 3";
                    break;
                case 4:
                    hallName = "Hall 4";
                    break;
            }

            // Seat details
            // Display hall map to get selected seat details
            System.out.println("");
            System.out.println("----");
            System.out.println("SEAT");
            System.out.println("----");
            String seatName = HallSeatMap.getSeatDetails(
            		hallName,
            		selectedMovie.getDate(),
            		selectedMovie.getTime(),
            		quantity);

            // Calculate total price based on movie price and quantity
            double totalPrice = selectedMovie.getPrice() * quantity;

            // Display booking confirmation details
            System.out.println("");
            System.out.println("===============================================");
            System.out.println("=====        BOOKING CONFIRMATION        ======");
            System.out.println("===============================================");
            System.out.println(":| Movie       |: " + selectedMovie.getName());
            System.out.println(":| Date        |: " + selectedMovie.getDate());
            System.out.println(":| Time        |: " + selectedMovie.getTime());
            System.out.println(":| Hall        |: " + hallName);
            System.out.println(":| Seat(s)     |: " + seatName);
            System.out.println(":| Ticket(s)   |: " + quantity);
            System.out.printf (":| Total Price |: RM %.2f", totalPrice);

            System.out.print("\n\nConfirm booking? (y / n): ");
            confirmBookingChoice = input.nextLine(); //For "Y / y", repeat movie selection process

            if (confirmBookingChoice.equalsIgnoreCase("y")) {
            	// If user confirms booking
				// Create a new booking object
				Booking booking = new Booking(
						customerName, 
						selectedMovie.getName(), 
						selectedMovie.getDate(),
						selectedMovie.getTime(), 
						hallName,quantity,seatName,totalPrice); 
				
				booking.writeToFile();	// Write booking record to file
				
				// Display booking ID
				System.out.println("\nBooking ID: " + booking.getBookingID());
				System.out.println("Booking record added to file.");
			}

        } while (!confirmBookingChoice.equalsIgnoreCase("y")); // Repeat booking process if not confirmed

        // Display thank you message
        System.out.println("Thank you so much for using our application!");
    }
    
	// Method to view bookings (Customer)
	public static void viewBookingsCustomer(String nameInput)
	{     
        List<String> matchingBookings = new ArrayList<>();

        matchingBookings = loadBookingsCustomer(nameInput);
            
            if (matchingBookings.isEmpty()) {
            	// No bookings are found
                System.out.println("No bookings found for " + nameInput);
            } else {
            	// At least one booking is found
                System.out.println("Bookings for " + nameInput + ":");
                for (String booking : matchingBookings) {
                    String[] attributes = booking.split(",");
                    System.out.println("Booking ID      : " 	+ attributes[0].trim());
                    System.out.println("Customer Name   : " 	+ attributes[1].trim());
                    System.out.println("Movie Name      : "		+ attributes[2].trim());
                    System.out.println("Date            : "		+ attributes[3].trim());
                    System.out.println("Time            : "		+ attributes[4].trim());
                    System.out.println("Hall Name       : " 	+ attributes[5].trim());
                    System.out.println("Ticket Quantity : " 	+ attributes[6].trim());
                    System.out.println("Seat Names      : " 	+ attributes[7].trim());
                    System.out.println("Total Price     : RM " 	+ attributes[8].trim());
                    System.out.println();
                }
                
            	System.out.println("====================");
            	System.out.println("Total Bookings : " + matchingBookings.size());
            	System.out.println("====================");         
            }
    }
	
	// Method to view bookings (Admin)
	public static String viewBookingsAdmin(String bookingIDInput)
	{     
		bookingIDInput = bookingIDInput.toUpperCase();
        String matchingBookings = loadBookingsAdmin(bookingIDInput);
            
            if (matchingBookings.isEmpty()) {
            	// No bookings are found
                System.out.println("\nNo bookings found for " + bookingIDInput);
            } else {
            	// Matching booking is found
            	System.out.println("");
            	System.out.println("--------------");
                System.out.println("BOOKING FOUND:"); 
            	System.out.println("--------------");
            	String[] attributes = matchingBookings.split(",");
                System.out.println("Booking ID      : " 	+ attributes[0].trim());
                System.out.println("Customer Name   : " 	+ attributes[1].trim());
                System.out.println("Movie Name      : "		+ attributes[2].trim());
                System.out.println("Date            : "		+ attributes[3].trim());
                System.out.println("Time            : "		+ attributes[4].trim());
                System.out.println("Hall Name       : " 	+ attributes[5].trim());
                System.out.println("Ticket Quantity : " 	+ attributes[6].trim());
                System.out.println("Seat Names      : " 	+ attributes[7].trim());
                System.out.println("Total Price     : RM " 	+ attributes[8].trim());
                                }
            
           return matchingBookings;
    }
	
	// Method to load matching bookings (Customer - with customer name)
	public static List<String> loadBookingsCustomer(String nameInput){
		List<String> matchingBookings = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(bookingFile));
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String customerName = attributes[1].trim(); // Extracting customer name
                
                if (customerName.equals(nameInput)) {
                    matchingBookings.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return matchingBookings;
	}


	// Method to load matching bookings (Admin - with booking ID)
	public static String loadBookingsAdmin(String bookingIDInput){
		String matchingBookings = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(bookingFile));
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String bookingID = attributes[0].trim(); // Extracting customer name
                
                if (bookingID.equals(bookingIDInput)) {
                    matchingBookings = line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return matchingBookings;
	}
	
	// Method to modify bookings
	public static void modifyBookings(String matchingBookings)
	{
		String booking = new String(matchingBookings);
        String[] attributes = booking.split(",");
        
    	String continueEditChoice;
        int editOption = 0;
        String comfirmEditOption;
        
        String hallName = attributes[5];
        String date 	= attributes[3];
        String time 	= attributes[4];
        int newTicketQuantity = Integer.parseInt(attributes[6]);       
        String newMovieSeat = attributes[7];
        
        do {
        	System.out.println("");
        	System.out.println("===============================================");
    		System.out.println("======            Edit Booking            =====");
    		System.out.println("===============================================");
    		System.out.println("Current ticket quantity : " + newTicketQuantity);
    		System.out.println("Current movie seats     : " + newMovieSeat);
    		do {
    			System.out.println
    			("\nWhich information you want to modify?" +
				 "\n1. Ticket Quantity" +
				 "\n2. Movie Seat" );
    			System.out.print("Enter your option: ");
    			editOption = Choice.getUserChoice(input, 2);
   			
   				switch(editOption)
       			{
        			case 1: 
        				System.out.print("Enter the new ticket quantity: "); //modify correct ticket quantity
        				newTicketQuantity = input.nextInt();
        								
       				case 2: 
       					System.out.print("Enter the new movie seat: "); //modify correct movie seat
       					newMovieSeat = HallSeatMap.getSeatDetails(hallName, date, time, newTicketQuantity);
       					break;
       			}
   				System.out.println("Do you still want to modify booking infomation? (y / n): ");
   				continueEditChoice = input.next();
   			} while (continueEditChoice.equalsIgnoreCase("y"));
    		
	   			int oldTicketQuantity = Integer.parseInt(attributes[6]);
	   			double oldTotalPrice = Double.parseDouble(attributes[8]);
	   			double newTotalPrice = (oldTotalPrice/oldTicketQuantity) * newTicketQuantity;
	   			
	   			attributes[6] = String.valueOf(newTicketQuantity);
	   			attributes[7] = String.valueOf(newMovieSeat);
	   			attributes[8] = String.valueOf(newTotalPrice);
	   			//Show the booking after insert the different information
	            System.out.println("");
	            System.out.println("===============================================");
	            System.out.println("=====        BOOKING CONFIRMATION        ======");
	            System.out.println("===============================================");
	            System.out.println("Booking ID      : " 	+ attributes[0].trim());
	            System.out.println("Customer Name   : " 	+ attributes[1].trim());
	            System.out.println("Movie Name      : "		+ attributes[2].trim());
	            System.out.println("Date            : "		+ attributes[3].trim());
	            System.out.println("Time            : "		+ attributes[4].trim());
	            System.out.println("Hall Name       : " 	+ attributes[5].trim());
	            System.out.println("Ticket Quantity : " 	+ attributes[6].trim());
	            System.out.println("Seat Names      : " 	+ attributes[7].trim());
	            System.out.println("Total Price     : RM " 	+ attributes[8].trim());
	            System.out.println();    
	            
	            System.out.println("Comfirm changes? (y / n): ");
	            comfirmEditOption = input.next();
            
	            if (comfirmEditOption.equalsIgnoreCase("y"))
	            {
	                // Delete existing booking and add new booking inside
	            	modifyBookingInFile(attributes[0], String.join(",", attributes));                
	            }
            }while(!comfirmEditOption.equalsIgnoreCase("y"));
        }
	
	// Method to modify bookings inside the file
	private static void modifyBookingInFile(String bookingID, String modifiedBooking) {
        try {
            File inputFile = new File(bookingFile);
            Scanner scanner = new Scanner(inputFile);
            ArrayList<String> lines = new ArrayList<String>();

            // Read all bookings from the file into memory
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] bookingAttributes = line.split(",");
                // Check if the current line represents the booking to be modified
                if (bookingAttributes.length > 0 && bookingAttributes[0].trim().equals(bookingID)) {
                    lines.add(modifiedBooking); // Replace with modified booking
                } else {
                    lines.add(line);
                }
            }
            scanner.close();

            // Write all lines back to the file
            FileWriter writer = new FileWriter(bookingFile);
            for (String line : lines) {
                writer.write(line + System.lineSeparator());
            }
            writer.close();
            
            System.out.println("Booking successfully modified.");
        } catch (IOException e) {
            System.out.println("An error occurred while modifying the booking: " + e.getMessage());
        }
    }

	// Method to cancel bookings
	public static void cancelBooking()
	{
        boolean continueCancel = false;
        String choice;
        do
        {
            System.out.println("\n\n===============================================");
            System.out.println("======          Cancel Booking          =======");
            System.out.println("===============================================");
            System.out.print("Enter the booking ID: ");
            String deleteBooking = input.nextLine();
            deleteBooking = deleteBooking.toUpperCase();
            
            viewBookingsAdmin(deleteBooking);//show the booking details
            ArrayList<String> lines = new ArrayList<>();
            ArrayList<String> idFound = new ArrayList<>();
            
            try
            {
                File readFile = new File(bookingFile); // Create a File object for reading
                Scanner inputFile = new Scanner(readFile); // Create a Scanner for reading the file
                
                String firstLine = inputFile.nextLine();
                lines.add(firstLine);
                
                while(inputFile.hasNextLine())
                {
                    String line = inputFile.nextLine();
                    int comma1 = line.indexOf(",");
                    String temp_bookingID = line.substring(0, comma1);
                    
                    if(!temp_bookingID.equals(deleteBooking))
                    {
                    	lines.add(line); // Add lines that don't match the delete booking to the list
                    	
                    }
                    
                    else
                    {
                        idFound.add(temp_bookingID);// Add the booking ID to the idFound list
                    }
                }
                
                inputFile.close();//close the inputFile
                
                do
                {
    	            System.out.print("Do you want to cancel this booking? (y/n): ");
    	            choice = input.nextLine().toLowerCase();
    	            if(choice.equals("y"))
    	            {
    	            	continueCancel = true;
    	                
    	            }
    	            else if(choice.equals("n"))
    	            {
    	            	continueCancel = false;
    	            }
    	            else
    	            {
    	            	System.out.println("\n!!! Invalid option !!!\n");
    	            }
                }while(!choice.equals("y") && !choice.equals("n"));
                
                if(choice.equals("y"))
                {
                    FileWriter fw = new FileWriter(bookingFile);// Create a FileWriter object
                    PrintWriter outputFile = new PrintWriter(fw);// Create a PrintWriter to write to the file
                    
                    // Write the updated lines back to the file
                    for (String line : lines)
                    {
                        outputFile.println(line);// rewrite the file without the deleted student's information
                    }
                    
                    outputFile.close();//close the outputFile
                    System.out.println("\n--- Booking has been cancel successfully ---");
                }
                else
                {
                    System.out.println("--- Failed to cancel booking ---");
                }
                
                do
                {
    	            System.out.print("Do you want to cancel another booking? (y/n): ");
    	            choice = input.nextLine().toLowerCase();
    	            if(choice.equals("y"))
    	            {
    	            	continueCancel = true;
    	                
    	            }
    	            else if(choice.equals("n"))
    	            {
    	            	continueCancel = false;
    	            	break; // exit the loop if the admin doesn't want to cancel more booking 
    	            }
    	            else
    	            {
    	            	System.out.println("\n!!! Invalid option !!!\n");
    	            }
                }while(!choice.equals("y") && !choice.equals("n"));
            }
            
            catch (IOException exc)
            {
                System.out.println("File error: " + exc.getMessage());
            }
        } while(continueCancel == true);
    }
	
	public void writeToFile() 
	{
        try (FileWriter fw = new FileWriter(bookingFile, true)) {
            	
            fw.write(getBookingID() + "," + getCustomerName() + "," +getMovieName() + "," + getDate() + "," + getTime() + "," + getHall() + "," + getTicketQuantity() + "," + getSeat()+ "," + getPrice() + "\n"); 
            
            // Flush the writer to ensure data is written immediately
            fw.flush();
            
            System.out.println("Booking record written successfully.");
        } catch (IOException e) {
            // Print error message and stack trace
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	public void writeToFile(String addBooking) {
	    try (FileWriter fw = new FileWriter(bookingFile, true)) {
	        fw.append(addBooking).append("\n"); // Append booking record and add a newline character
	        // Flush the writer to ensure data is written immediately
	        fw.flush();
	        
	        System.out.println("Booking record written successfully.");
	    } catch (IOException e) {
	        // Print error message and stack trace
	        System.err.println("Error writing to file: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}