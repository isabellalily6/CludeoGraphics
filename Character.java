// Class for the 6 character cards

public class Character extends Card {
    private String name;

    public Character(String charName) {
        super(charName);
        name = charName;
    }

    public String getName() {
        return name;
    }
}
