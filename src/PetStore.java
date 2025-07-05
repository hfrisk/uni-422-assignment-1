import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Predicate;

public class PetStore {
    private static final String ROW_BREAK = "+----------------------+";

    private final ArrayList<Pet> pets = new ArrayList<>();

    private void printHeader() {
        System.out.println(ROW_BREAK);
        System.out.println("| ID | NAME | AGE |");
        System.out.println(ROW_BREAK);
    }

    private void printPet(Pet pet) {
        final String FORMAT_STRING = "| %d | %s | %d |";
        String formatted = String.format(FORMAT_STRING, pet.getID(), pet.getName(), pet.getAge());
        System.out.println(formatted);
    }

    public void printPets(ArrayList<Pet> pets) {
        printHeader();
        for (Pet pet : pets) {
            printPet(pet);
        }
        System.out.println(ROW_BREAK);
    }

    public ArrayList<Pet> searchPets(Predicate<Pet> predicate) {
        //return this.pets.clone().removeIf(predicate.negate()); can't do this in java because .removeIf is an in-place method that returns true :(
        ArrayList<Pet> result = (ArrayList<Pet>) this.pets.clone();
        result.removeIf(predicate.negate());
        return result;
    }

    public ArrayList<Pet> searchPets(String name) {
        return searchPets(pet -> pet.getName().equalsIgnoreCase(name));
    }

    public ArrayList<Pet> searchPets(byte age) {
        return searchPets(pet -> pet.getAge() == age);
    }

    public void printPets() {
        this.printPets(this.pets);
    }

    public void addPet(Pet pet) {
        this.pets.add(pet);
    }

    public PetStore() {
    }

}
