import java.util.ArrayList;
import java.util.NoSuchElementException;
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
        pets.forEach(this::printPet);
        System.out.println(ROW_BREAK);
    }

    public ArrayList<Pet> searchPets(Predicate<Pet> predicate) {
        //return this.pets.clone().removeIf(predicate.negate()); can't do this fun one-liner :(
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

    public Pet getPet(int id) throws IndexOutOfBoundsException, NoSuchElementException  {
        if (id < 0 || id > 4) {
            throw new IndexOutOfBoundsException("Pet ID must be between 0 and 4");
        }
        return this.pets.stream().filter(pet -> pet.getID() == id).findFirst().orElseThrow(() -> new NoSuchElementException("Pet not found."));
    }

    public ArrayList<Pet> getPets() {
        return this.pets;
    }

    public void printPets() {
        this.printPets(this.pets);
    }

    public int getSize() {
        return this.pets.size();
    }

    public void addPet(Pet pet) throws Exception {
        if (this.getSize() >= 5) {
            throw new Exception("Database is full");
        }
        this.pets.add(pet);
    }

    public void removePet(Pet pet) {
        this.pets.removeIf(pet1 -> pet1 == pet);
    }

    public void removePet(int id) {
        this.pets.removeIf(pet -> pet.getID() == id);
    }

    public PetStore() {
    }

}
