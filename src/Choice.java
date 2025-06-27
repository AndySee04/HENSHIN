package Movie_App;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Choice {
	// Method to handle choices with range
	public static int getUserChoice(Scanner scanner, int maxChoice) {
        int inputChoice = 0;
        int minChoice = 1;
        
        String sign = " - ";	
        if (maxChoice == 2)
        	sign = " / ";
        
        do {
            try {
                System.out.print("Choose (" + minChoice + sign + maxChoice + "): ");
                inputChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (inputChoice < minChoice || inputChoice > maxChoice) {
                    System.out.println("\nInvalid number!");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input choice!");
                scanner.nextLine(); // Consume invalid input
                inputChoice = -1; // Reset inputChoice so loop will continue
            }
        } while (inputChoice < minChoice || inputChoice > maxChoice);
        return inputChoice;
    }
}
