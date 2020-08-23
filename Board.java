// The board is a 2D Array of cells. The game board that holds and displays information about everything on the board. 

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board  extends JPanel {
    private int WIDTH = 24;
    private int HEIGHT = 25;
    private Cell[][] board = new Cell[WIDTH][HEIGHT]; // storing each cell in position
    private Cell[][] playerBoard = new Cell[WIDTH][HEIGHT]; // storing each cell in position
    private AtomicBoolean hover = new AtomicBoolean(false);
    private ArrayList<Cell> boardWeapons = new ArrayList<>();

    private ArrayList<Player> players;
    Graphics2D g2 = null;
    private int cellSize = 18; //change this to change the size of the window on screen
    private int cellsWide = 24;
    private int cellsHigh = 25;
    private int width = cellSize*(cellsWide+2); //+2 leaves space around the board as a border
    private int height = cellSize*(cellsHigh+2);

    public Board(ArrayList<Player> players) {
        makeBoard();
        makeBoardWeapons();
        this.players = players;
        setBackground(Color.pink);
        //addMouseMotionListener(this);
    }

    /**
     * Display the board
     *
     * @param players
     */
    public void drawBoard(ArrayList<Player> players) {
        Cell[][] tempBoard = getPlayerBoard(players);
        for (int y = 0; y < HEIGHT; y++) {
            System.out.println();
            for (int x = 0; x < WIDTH; x++) {
                //print the symbol indicating what is in the cell with a space in between
                System.out.print(tempBoard[x][y].getSymbol());
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public void setHover(Boolean bool) {
        hover.set(bool);
    }

    public void paint(Graphics g) {
        g2 = (Graphics2D) g;
        for(int c = 0; c < cellsWide; c++) { //fill in cells
            for(int r = 0; r < cellsHigh; r++) {
                char cellSymbol = getCell(c, r).getSymbol();
                if(cellSymbol == 'X') { //wall
                    g2.setColor(Color.black);
                } else if(cellSymbol == '_') { //floor
                    g2.setColor(Color.cyan);
                } else { //room
                    g2.setColor(Color.pink);
                }
                g2.fillRect(cellSize*(c+1), cellSize*(r+1), cellSize, cellSize);
            }
        }

        for(int i = 1; i < cellsWide+2; i++) { //vertical cell lines
            g2.drawLine(cellSize*i, cellSize, cellSize*i, height-cellSize);
        }

        for(int i = 1; i < cellsHigh+2; i++) { //horizontal cell lines
            g2.drawLine(cellSize, cellSize*i, width-cellSize, cellSize*i);
        }

        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int xPos = player.getxPos();
            int yPos = player.getyPos();
            
            //set the colour of the player
            if(player.getCharacterCard().getName() == "Miss Scarlett") {
                g2.setColor(new Color(255, 36, 0));
            } else if(player.getCharacterCard().getName() == "Colonel Mustard") {
                g2.setColor(new Color(255, 219, 88));
            } else if(player.getCharacterCard().getName() == "Mrs. White") {
                g2.setColor(new Color(255, 255, 255));
            } else if(player.getCharacterCard().getName() == "Mr. Green") {
                g2.setColor(new Color(56, 118, 29));
            } else if(player.getCharacterCard().getName() == "Mrs. Peacock") {
                g2.setColor(new Color(12, 12, 223));
            } else if(player.getCharacterCard().getName() == "Professor Plum") {
                g2.setColor(new Color(221, 160, 221));
            }

            //draw the player
            g2.fillOval(cellSize*(xPos+1)+cellSize/8, cellSize*(yPos+1)+cellSize/8, cellSize*3/4, cellSize*3/4);
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.black);
            g2.drawOval(cellSize*(xPos+1)+cellSize/8, cellSize*(yPos+1)+cellSize/8, cellSize*3/4, cellSize*3/4);
            
            for(Cell cell : boardWeapons){
                g2.setColor(Color.black);
                g2.drawString(String.valueOf(cell.getSymbol()), cellSize*(cell.getxCoord()+1)+cellSize/8, cellSize*(cell.getyCoord()+2)+cellSize/8);
            }
        }
    }
    /**
     * Creating Cells in each room for a weapon to go into
     */
    public void makeBoardWeapons() {
        boardWeapons.add(new Cell(1, 5, 'R', 'K'));
        boardWeapons.add(new Cell(10, 3, '+', 'B'));
        boardWeapons.add(new Cell(1, 14, 's', 'D'));
        boardWeapons.add(new Cell(5, 23, '|', 'O'));
        boardWeapons.add(new Cell(13, 23, '&', 'H'));
        boardWeapons.add(new Cell(22, 23, 'L', 'S'));
        boardWeapons.add(new Cell(22, 16, ' ', 'L'));
        boardWeapons.add(new Cell(22, 10, ' ', 'I'));
        boardWeapons.add(new Cell(22, 1, ' ', 'C'));
    }

    public ArrayList<Cell> getBoardWeapons(){
        return boardWeapons;
    }
    
    /**
     * Gets a Cell based on x and y positions of a mouse click
     *
     * @param x, y
     * @return null if out of bounds, otherwise the Cell being clicked on
     */
    public Cell getCellFromPixel(int x, int y) {
    	//return null if out of bounds
    	if(x < cellSize || x > width - cellSize || y < cellSize || y > (height - cellSize)) {
    		return null;
    	}

        //positions from the top left corner of the board, not the screen
        x -=  cellSize;
        y -= cellSize;

        // calculates the row based on the off set
        int row = y/cellSize;

    	//return the cell
        try {
            return board[x / cellSize][row];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }



    public void updateBoard(ArrayList<Player> players){
        playerBoard = getPlayerBoard(players);
    }
    /**
     * Adds all players the player on the board in their current position
     *
     * @param players
     * @return 2D array of cells including where the players are
     */
    public Cell[][] getPlayerBoard(ArrayList<Player> players) {
        //store the current board in a new 2D array
        Cell[][] tempBoard = new Cell[WIDTH][HEIGHT];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                tempBoard[x][y] = board[x][y];
            }
        }

        //add the players to the new board
        for (Player player : players) {
            Cell c = new Cell(player.getxPos(), player.getyPos(), player.getSymbol());
            tempBoard[player.getxPos()][player.getyPos()] = c;
        }
        return tempBoard;
    }

    /**
     * Returns the cell at the x and y co-ordinates passed in
     *
     * @param x
     * @param y
     * @return the Cell at the co-ordinates
     */
    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Returns the top left position in a room
     *
     * @param letter  indicating the room
     * @param players
     * @return x and y value of top left cell in the relevant room
     */
    public List<Integer> getRoom(char letter, ArrayList<Player> players) {
        Cell[][] tempBoard = getPlayerBoard(players);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (tempBoard[x][y].getSymbol() == letter) {
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(x);
                    list.add(y);
                    return list;
                }
            }
        }
        return null;
    }

    /**
     * Design and store the board layout
     */
    public void makeBoard() {
        String boardLayout =
                "XXXXXXXXXXXXXXXXXXXXXXXX" +
                        "XKKKKX____XXXX____XCCCCX" +
                        "XKKKKX__XXXBBXXX__XCCCCX" +
                        "XKKKKX__XBBBBBBX__XCCCCX" +
                        "XKKKKX__XBBBBBBX__CCCCCX" +
                        "XKKKKX__BBBBBBBB___XXXXX" +
                        "XXXXKX__XBBBBBBX_______X" +
                        "X_______XBXXXXBX_______X" +
                        "X_________________XXXXXX" +
                        "XXXXX_____________IIIIIX" +
                        "XDDDXXXX__XXXXX___XIIIIX" +
                        "XDDDDDDX__XXXXX___XIIIIX" +
                        "XDDDDDDD__XXXXX___XXXXIX" +
                        "XDDDDDDX__XXXXX________X" +
                        "XDDDDDDX__XXXXX___XXLXXX" +
                        "XXXXXXDX__XXXXX__XXLLLLX" +
                        "X_________XXXXX__LLLLLLX" +
                        "X________________XXLLLLX" +
                        "X________XXHHXX___XXXXXX" +
                        "XXXXXOX__XHHHHX________X" +
                        "XOOOOOX__XHHHHH________X" +
                        "XOOOOOX__XHHHHX__XSXXXXX" +
                        "XOOOOOX__XHHHHX__XSSSSSX" +
                        "XOOOOOX__XHHHHX__XSSSSSX" +
                        "XXXXXXXXXXXXXXXXXXXXXXXX";
        int count = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                char symbol = boardLayout.charAt(count);
                Cell newCell = new Cell(x, y, symbol);
                board[x][y] = newCell;
                count += 1;
            }
        }

    }


}
