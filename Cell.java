// A Cell is a single position on the board.

public class Cell {
    private int xCoord;
    private int yCoord;
    private char symbol; //Represents what the cell is (wall, room, etc.)
    private char roomSymbol; //Represents what object this room is in

    /**
     * Constructor for cells on the board
     *
     * @param x, y, symbol
     */
    public Cell(int x, int y, char symbol) {
        xCoord = x;
        yCoord = y;
        this.symbol = symbol;
    }
    
    /**
     * Consturctor for cells with weapons on the board to show what room they are in
     */
    public Cell(int x, int y, char symbol, char roomSymbol) {
        xCoord = x;
        yCoord = y;
        this.symbol = symbol;
        this.roomSymbol = roomSymbol;
    }

    /**
     * Get the x-coord of the cell
     *
     * @return the x-coord of the current cell
     */
    public int getxCoord() {
        return xCoord;
    }

    /**
     * Get the y-coord of the cell
     *
     * @return the y-coord of the current cell
     */
    public int getyCoord() {
        return yCoord;
    }

    /**
     * Check whether the cell is a wall
     *
     * @return true if the cell is a wall, otherwise false
     */
    public Boolean isWall() {
        if (symbol == 'X') {
            return true;
        }
        return false;
    }
    
    /**
     * Change the symbol of the cell
     */
    public void changeCell(Cell c){
        this.symbol = c.symbol;
    }

    /**
     * Get the symbol of the cell
     *
     * @return the symbol of the cell
     */
    public char getSymbol() {
        return symbol;
    }
    
    /**
     * @return the symbol for the room the cell is in
     */
    public char getRoomSymbol(){
        return roomSymbol;
    }

    /**
     * Changes the symbol for the weapon that is in the cell
     *
     * @param symbol
     */
    public void changeWeapon(char symbol){
        this.symbol = symbol;
    }

}
