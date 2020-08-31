// Class for the 6 weapon cards

public class Weapon extends Card {
    private String name;
    private char symbol;
    private int xPos;
    private int yPos;

    public Weapon(String weaponName) {
        super(weaponName);
        name = weaponName;
    }

    public Weapon(String weapnName, char symbol, int x, int y){
        name = weapnName;
        this.symbol = symbol;
        xPos = x;
        yPos = y;
    }

    public String getName() {
        return name;
    }
}