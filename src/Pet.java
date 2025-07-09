import java.io.Serializable;

public class Pet implements Serializable {
    private static int highestID = 0;
    private final int ID;
    private byte age; //No need for an int. I doubt anyone's keeping a Greenland shark around as a pet.
    private String name;

    public byte getAge() {
        return this.age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public int getID() {
        return this.ID;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pet(String petName, byte petAge) {
        this.age = petAge;
        this.name = petName;
        this.ID = highestID;
        highestID += 1;
    }

}
