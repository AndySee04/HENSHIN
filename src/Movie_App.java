package Movie_App;

import java.util.Scanner;

public class Movie_App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        do {            
            System.out.println("=====================================================================");
            System.out.println("");
            System.out.println(" ('-. .-.   ('-.       .-') _   .-')    ('-. .-.              .-') _  ");
            System.out.println("( OO )  / _(  OO)     ( OO ) ) ( OO ). ( OO )  /             ( OO ) ) ");
            System.out.println(",--. ,--.(,------.,--./ ,--,' (_)---\\_),--. ,--.  ,-.-') ,--./ ,--,'  ");
            System.out.println("|  | |  | |  .---'|   \\ |  |\\ /    _ | |  | |  |  |  |OO)|   \\ |  |\\  ");
            System.out.println("|   .|  | |  |    |    \\|  | )\\  :` `. |   .|  |  |  |  \\|    \\|  | ) ");
            System.out.println("|       |(|  '--. |  .     |/  '..`''.)|       |  |  |(_/|  .     |/  ");
            System.out.println("|  .-.  | |  .--' |  |\\    |  .-._)   \\|  .-.  | ,|  |_.'|  |\\    |   ");
            System.out.println("|  | |  | |  `---.|  | \\   |  \\       /|  | |  |(_|  |   |  | \\   |   ");
            System.out.println("`--' `--' `------'`--'  `--'   `-----' `--' `--'  `--'   `--'  `--'   ");
            System.out.println("");

            System.out.println("=====================================================================");
            System.out.println("\t  Welcome to HENSHIN Movie Booking Management App!");
            System.out.println("=====================================================================");
            System.out.println("1. User Registration");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            choice = Choice.getUserChoice(input, 3);
            switch (choice) {
                case 1:
                    Account.RegisterUser();
                    break;
                case 2:
                    Account.LoginUser();
                    break;
            }
        } while (choice != 3);
    }
}