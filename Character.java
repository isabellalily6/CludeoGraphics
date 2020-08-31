// Class for the 6 character cards

public class Character extends Card {
    private String name;
    
    /**
    * Constructor for a character card
    *
    * @param name
    */
    public Character(String charName) {
        super(charName);
        name = charName;
    }

    /**
    * @return name
    */
    public String getName() {
        return name;
    }
}
