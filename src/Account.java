package Movie_App;

import java.io.*;
import java.util.Scanner;

public class Account {
    private String username;
    private String gender;
    private String email;
    private int age;
    private String password;
    
	private static final Scanner input = new Scanner(System.in);
	private static final String userFile = "User Info.txt";

    // Getters for Account
    public String getName() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
    
    public String getPassword() {
        return password;
    }
    
    // Constructor for Account
    public Account(String username, String gender, String email, int age, String password) {
        this.username = username;
        this.gender = gender;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public void saveToFile() {
        try {
            FileWriter writer = new FileWriter(userFile, true);
            writer.write("customer" + "," + username + "," + age + "," + email + "," + gender + "," + password + "\n");
            writer.close();
            System.out.println("Account information successfully saved.");
        } catch (IOException e) {
            System.err.println("Error: Unable to save account information to file.");
            e.printStackTrace();
        }
    }
    
    public static void RegisterUser() {
        String name = null;
        int age = 0;
        String gender = null;
        String email = null;
        String password = null;
        String comfirmRegChoice = null;

        do {
        	System.out.println("");
            System.out.println("=================");
            System.out.println("USER REGISTRATION");
            System.out.println("=================");

            // Enter name
            do {
                System.out.println("\nEnter your name : ");
                name = input.nextLine();

                // Check if the name contains any special characters excluding spaces
                if (!name.matches("[a-zA-Z\\s]+")) {
                    System.out.println("Invalid input! Name cannot contain numbers or special characters (excluding spaces).");
                }
            } while (!name.matches("[a-zA-Z\\s]+"));

            // Enter age
            do {
                System.out.println("\nEnter your age : ");
                while (!input.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    System.out.println("\nEnter your age: ");
                    input.next(); // Consume invalid input
                }
                age = input.nextInt();
                input.nextLine();

                if (age < 0 || age > 100) {
                    System.out.println("Invalid input! Please enter a number between 0 and 100.");
                }
            } while (age < 0 || age > 100);

            // Enter gender
            do {
                System.out.println("\nEnter your gender (M/F) : ");
                gender = input.nextLine().toUpperCase();

                if (!gender.equals("M") && !gender.equals("F"))
                    System.out.println("Invalid input! Please try again !");
            } while (!gender.equals("M") && !gender.equals("F"));
            
            // Enter email
            System.out.println("\nEnter your email address : ");
            email = input.nextLine();
            boolean validEmail = false;
            do {	
                int atpos1 = 0, atpos2 = 0, dotpos1 = 0, dotpos2 = 0;

                // Find the position of "@" symbol
                atpos1 = email.indexOf("@");
                if (atpos1 == -1)
                    System.out.println("No '@' symbol found.");
                else {
                    atpos2 = email.lastIndexOf("@");
                    if (atpos1 == atpos2) {
                    	// Only one "@" symbol found
                    } else
                        System.out.println("More than one '@' symbol.");
                }

                // Find the position of "." symbol
                dotpos1 = email.indexOf(".");
                if (dotpos1 == -1)
                    System.out.println("No '.' symbol found.");
                else {
                    dotpos2 = email.lastIndexOf(".");
                    if (dotpos1 == dotpos2) {
                        // Only one "." symbol found
                    } else
                        System.out.println("More than one '.' symbol.");
                }

                // Check if both "@" and "." are present only once
                if (atpos1 == atpos2 && dotpos1 == dotpos2) {
                    validEmail = true;
                } else {
                    System.out.println("Invalid email format! Please enter a valid email: ");
                    email = input.nextLine();
                }
            } while (!validEmail);

            // Enter password
            boolean containsSpecialChar = false;
            boolean containsUppercase = false;
            boolean containsDigit = false;
            boolean validPassword = false;
            do {
                containsSpecialChar = false;
                containsUppercase = false;
                containsDigit = false;
                validPassword = false;

                System.out.println("\nEnter your password : ");
                password = input.nextLine();

                for (char ch : password.toCharArray()) {
                    if (!containsSpecialChar && !Character.isLetterOrDigit(ch)) {
                        containsSpecialChar = true;
                    } else if (!containsUppercase && Character.isUpperCase(ch)) {
                        containsUppercase = true;
                    } else if (!containsDigit && Character.isDigit(ch)) {
                        containsDigit = true;
                    }
                    // Break the loop if all conditions are satisfied
                    if (containsSpecialChar && containsUppercase && containsDigit)
                        validPassword = true;
                }
                // Check if all required characters are present
                if (!containsSpecialChar || !containsUppercase || !containsDigit) {
                    System.out.println(
                            "Password must contain at least one special character, one Uppercase letter, and one digit.");
                }
            } while (validPassword != true);
            
            Account acc = new Account(name, gender, email, age, password);

            System.out.println("\nRegistration Confirmation:");
            System.out.println("Name     : " + acc.getName());
            System.out.println("Age      : " + acc.getAge());
            System.out.println("Gender   : " + acc.getGender());
            System.out.println("Email    : " + acc.getEmail());
            System.out.println("Password : " + acc.getPassword());
            System.out.println("Are you sure all details are correct? (Y / N): ");
            comfirmRegChoice = input.next();

            if (comfirmRegChoice.equalsIgnoreCase("Y")) {
                // Simulating registration process
                System.out.println("Persona Profile registered was successfully!");
                acc.saveToFile();
            }
        } while (!comfirmRegChoice.equalsIgnoreCase("Y"));
    }
    
    public static void LoginUser() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(userFile));
        } catch (FileNotFoundException e) {
            System.out.println("\"User Info.txt\" cannot be accessed.");
            e.printStackTrace();
        }

        char tryAgainChoice = 0;
        char backToMainChoice;
        String email = null;
        String password = null;

        do {
            input.nextLine(); // Consume the newline character
        	System.out.println("");
            System.out.println("==========");
            System.out.println("USER LOGIN");
            System.out.println("==========");

            System.out.println("Enter email:");
            email = input.nextLine();

            System.out.println("\nEnter password:");
            password = input.nextLine();
            boolean loginSuccessful = checkCredentials(email, password);

            if (loginSuccessful) {
                System.out.println("\nLogin successful!");
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        String[] attributes = line.split(",");
                        String checkUserIdendity = attributes[0].trim();
                        String name = attributes[1].trim();

                        if (line.contains(email) && line.contains(password)) {
                            if (checkUserIdendity.equals("customer"))
                            	Booking.loginCustomer(name);
                            else
                            	Booking.loginAdmin(name);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("\nWrong email or password!");
                System.out.print("Try again? (Y/N): ");
                tryAgainChoice = input.next().charAt(0);
                input.nextLine(); // Consume the newline character

                if (tryAgainChoice != 'Y' && tryAgainChoice != 'y') {
                    System.out.print("Back to main page? (Y/N): ");
                    backToMainChoice = input.next().charAt(0);
                    input.nextLine(); // Consume the newline character

                    if (backToMainChoice == 'Y' || backToMainChoice == 'y') {
                        // Return to main page
                        break;
                    } else {
                        System.out.println("Exiting program. Goodbye!");
                        System.exit(0);
                    }
                }
            }
        } while (tryAgainChoice == 'Y' || tryAgainChoice == 'y');
        // Code to return to the main page or exit program can be added here
        System.out.println("Back to main page.");
    }
    
    // Method to check user credentials
    private static boolean checkCredentials(String email, String password) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(userFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(",");
                String storedEmail = attributes[3].trim();
                String storedPassword = attributes[5].trim();
                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}