import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PetStore petStore = new PetStore();

    private static void printOptions() {
        System.out.println("What would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        System.out.println("3) Update an existing pet");
        System.out.println("4) Remove an existing pet");
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
            case 3:
                updatePet();
                break;
            case 4:
                removePet();
                break;
            case 5:
                searchPetsByName();
                break;
            case 6:
                searchPetsByAge();
                break;
            case 7:
                saveAndQuit();
                break;
            default:
                System.out.println("Invalid option.");
                break;
        }

    }

    private static void updatePet() {
        petStore.printPets();
        System.out.println("Enter the ID of the pet you want to update: ");
        String input = scanner.nextLine();
        int id = Integer.parseInt(input);
        Pet pet = petStore.getPet(id);
        if (pet == null) {
            System.out.println("Pet with ID " + id + " not found.");
        } else {
            System.out.println("Enter new name and new age (name, age): ");
            input = scanner.nextLine();
            String[] split = input.split(",");
            byte age = Byte.parseByte(split[1].trim());
            String name = split[0];
            pet.setName(name);
            pet.setAge(age);
        }

    }

    private static void removePet() {
        petStore.printPets();
        System.out.println("Enter the ID of the pet you want to remove: ");
        String input = scanner.nextLine();
        int id = Integer.parseInt(input);
        petStore.removePet(id);
        System.out.println("Removed pet with ID " + id);
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
            try {
                String[] split = input.split(",");
                if (split.length != 2) {
                    throw new Exception("Formatting error.");
                }
                byte age = Byte.parseByte(split[1].trim());
                if (age < 1 || age > 20) {
                    throw new Exception("Age must be between 1 and 20.");
                }
                String name = split[0];
                Pet newPet = new Pet(name, age);
                petStore.addPet(newPet);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse age.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void saveAndQuit() {
        FileOutputStream file;
        ObjectOutputStream out;
        try {
            file = new FileOutputStream("db");
            out = new ObjectOutputStream(file);
        } catch (IOException e) {
            System.out.println("Unable to create file db. Reason:");
            System.out.println(e.getMessage());
            return;
        }
        for (Pet pet : petStore.getPets()) {
            try {
                out.writeObject(pet);
            } catch (Exception e) {
                System.out.println("Unable to save pet " + pet.getID() + " " + pet.getName()); //I wish Java had nice string interpolation. :|
                System.out.println(e.getMessage());
            }
        }
        try {
            out.close();
            file.close();
        } catch (IOException e) {
            System.out.println("Unable to save file db");
            System.out.println(e.getMessage());
            return;
        }
        System.exit(0);
    }

    public static void loadData() {
        try {
            FileInputStream file = new FileInputStream("db");
            ObjectInputStream in = new ObjectInputStream(file);
            boolean reading = true;
            while (reading) {
                try {
                    Pet pet = (Pet) in.readObject();
                    petStore.addPet(pet);
                } catch (EOFException e) {
                    reading = false;
                    in.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        loadData();
        scanner.useDelimiter(System.lineSeparator());
        //noinspection InfiniteLoopStatement
        while (true) {
            printOptions();
            acceptOption();
        }
    }
}