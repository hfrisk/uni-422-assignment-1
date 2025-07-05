import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PetStore petStore = new PetStore();

    private static void printOptions() {
        System.out.println("What would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        /*System.out.println("3) Update an existing pet");
        System.out.println("4) Remove an existing pet");
         */
        System.out.println("5) Search pets by name");
        System.out.println("6) Search pets by age");
        System.out.println("7) Exit Program");
    }

    private static void acceptOption() {
        System.out.print("Your choice: ");
        String input = scanner.nextLine();
        byte option = Byte.parseByte(input);
        switch (option) {
            case 1:
                petStore.printPets();
                break;
            case 2:
                addPet();
                break;
                /*
            case 3:
                updatePet();
                break;
            case 4:
                removePet();
                break;

                 */
            case 5:
                searchPetsByName();
                break;
            case 6:
                searchPetsByAge();
                break;
            case 7:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }

    }

    private static void searchPetsByName() {
        System.out.print("Enter a name to search: ");
        String name = scanner.nextLine();
        ArrayList<Pet> results = petStore.searchPets(name);
        petStore.printPets(results);
    }

    private static void searchPetsByAge() {
        System.out.print("Enter age to search: ");
        String input = scanner.nextLine();
        byte age = Byte.parseByte(input);
        ArrayList<Pet> results = petStore.searchPets(age);
        petStore.printPets(results);
    }

    private static void addPet() {
        while (true) {
            System.out.print("add pet (name, age): ");
            String input = scanner.nextLine();
            if (input.equals("done")) {
                return;
            }
            String[] split = input.split(",");
            byte age = Byte.parseByte(split[1].trim());
            String name = split[0];
            Pet newPet = new Pet(name, age);
            petStore.addPet(newPet);
        }
    }

    public static void main(String[] args) {
        scanner.useDelimiter(System.lineSeparator());
        //noinspection InfiniteLoopStatement
        while (true) {
            printOptions();
            acceptOption();
        }
    }
}