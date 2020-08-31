// Class for the 6 weapon cards

public class Weapon extends Card {
    private String name;
    private char symbol;
    private int xPos;
    private int yPos;

    /*
    * Constructor for a weapon
    *
    * @param weaponName
    */
    public Weapon(String weaponName) {
        super(weaponName);
        name = weaponName;
    }

    /*
    * Constructor for a weapon with symbol and position
    *
    * @param weaponName, symbol, x, y
    */
    public Weapon(String weapnName, char symbol, int x, int y){
        name = weapnName;
        this.symbol = symbol;
        xPos = x;
        yPos = y;
    }

    /*
    * Gets the name of the weapon
    *
    * @return String
    */
    public String getName() {
        return name;
    }
}
