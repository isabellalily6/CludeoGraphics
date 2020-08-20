// Controls all output and input of the game

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.management.remote.rmi.RMIConnectionImpl;
import javax.swing.*;

public class ConsoleUI extends JFrame{
    private Map<String, String> weaponNames = new HashMap<>();
    private Map<String, String> roomNames = new HashMap<>();
    private Map<String, String> characterNames = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    Graphics2D g2 = null;
    private Board board;
    private ArrayList<Player> players;
    JPanel boardPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel mainPanel = new JPanel(new BorderLayout());
    private boolean buttonPressed = false;

    private int cellSize = 26; //change this to change the size of the window on screen
    private int cellsWide = 24;
    private int cellsHigh = 25;
    private int width = cellSize*(cellsWide+2); //+2 leaves space around the boards as a border
    private int height = cellSize*(cellsHigh+2);
    
    // dice images
    ImageIcon dice1 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice1.jpg")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    ImageIcon dice2 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice1.jpg")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    ImageIcon dice3 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice3.jpg")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    ImageIcon dice4 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice4.jpg")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    ImageIcon dice5 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice5.jpg")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    ImageIcon dice6 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice6.jpg")).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    // character card images
    ImageIcon scarlet = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/scarlet.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon white = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/white.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon peacock = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/peacock.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon mustard = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/mustard.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon green = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/green.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon plum = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/plum.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    // weapon card images
    ImageIcon candleStick = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/CandleStick.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon dagger = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dagger.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon leadPipe = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/LeadPipe.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon revolver = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Revolver.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon rope = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Rope.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon spanner = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Spanner.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    // room card images
    ImageIcon ballRoom = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/BallRoom.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon billiardRoom = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/BilliardRoom.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon conservatory = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Conservatory.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon diningRoom = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/diningRoom.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon hall = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Hall.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon kitchen = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Kitchen.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon library = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Library.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon lounge = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Lounge.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));
    ImageIcon study = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/Study.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_DEFAULT));


    public ConsoleUI(Board board, ArrayList<Player> players) {
    	this.board = board;
    	this.players = players;
        createRooms();
        createCharacters();
        createWeapons();
        initUI();
    }
    
    private void initUI() {
        setTitle("Cluedo Game");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JMenuBar menuBar=new JMenuBar();
        JMenu menu =new JMenu("Menu");
        JMenuItem i1 =new JMenuItem("Instructions");
        JMenuItem i2=new JMenuItem("Exit");

        menu.add(i1);
        menu.add(i2);
        menuBar.add(menu);


        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog();

                // create a label
                JLabel l = new JLabel("Instructions for Cluedo:");

                d.add(l);
                // setsize of dialog
                d.setSize(200, 200);

                // set visibility of dialog
                d.setVisible(true);
            }
        });

        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



        setJMenuBar(menuBar);



//this.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.cyan);

        bottomPanel.setPreferredSize(new Dimension(100, 100));


        JButton b = new JButton("button1");
        JButton b2 = new JButton("button2");
        //rightPanel.add(b2);
        //bottomPanel.add(b);

        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JButton suggestButton = new JButton("Suggest");
        JButton accuseButton = new JButton("Accuse");
        JButton rollButton = new JButton("Roll Dice");

        rightPanel.setBackground(Color.lightGray);

        JPanel dicePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel playersPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(suggestButton);
        buttonPanel.add(accuseButton);
        dicePanel.add(rollButton);
        //playersPanel.add(b);

        buttonPanel.setBackground(Color.darkGray);
        playersPanel.setBackground(Color.red);
        dicePanel.setBackground(Color.blue);
        //bottomPanel.setBackground(Color.green);
      /*  mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel, BorderLayout.EAST);*/
    	setVisible(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 4;
        gbc.weighty = 4;
        gbc.gridheight = 3;

        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new Board(players), gbc);
        //mainPanel.add(buttonPanel, gbc);
        //mainPanel.add(playersPanel, gbc);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1 ;
        mainPanel.add(playersPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(buttonPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(dicePanel, gbc);

        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
//       gbc.anchor = GridBagConstraints.PAGE_END;
   //     gbc.anchor = GridBagConstraints.LAST_LINE_START;
        mainPanel.add(bottomPanel, gbc);


        add(mainPanel);
    }

    /**
     * Asks for input of the number of people playing
     *
     * @return the number of people playing
     */
    public int getNumPlayers() {
        JDialog d = new JDialog();
        JLabel label = new JLabel("Num of cells:");
        String[] characterString= {"3", "4", "5", "6"};
        JComboBox<String> num = new JComboBox<>(characterString);
        JButton next = new JButton("Next");
        d.setLayout(new FlowLayout());
        d.add(label);
        d.add(num);
        d.add(next);
        d.setSize(200, 200);
        d.setVisible(true);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonPressed = true;
                d.dispose();
            }
        });
        while(!buttonPressed){

        }
        return Integer.parseInt(num.getSelectedItem().toString());

    }

    /**
     * Asks for input of which direction to move player
     *
     * @return "U", "D", "L", "R" indicating up, down, left, or right
     */
    public String getMoves() {
        System.out.println("Do you want to move UDLR? ");
        String move = input.next();
        return move;
    }

    public void displayPlayersTurn(char player) {
        System.out.println("\nIt is player " + player + "'s turn");
    }

    public void displayDiceRoll(int number) {
        System.out.println("\nThe dice rolled: " + number);
    }

    public void displayMovesLeft(int movesLeft) {
        System.out.println("\nYou have " + movesLeft + " moves");
    }

    public void invalidInput() {
        System.out.println("Invalid input, try again: ");
    }

    /**
     * Ask for input of if the player wants to make a suggestion or accusation or neither
     *
     * @return "S", "A", "N" indication suggestion, accusation or neither
     */
    public String checkSuggestion() {
        System.out.println("Do you want to make a suggestion (S), an Accusation (A) or neither (N)? ");
        System.out.println("Type S for suggestion, A for accusation and N for neither");
        String userInput = input.next();
        return userInput;
    }

    /**
     * Asks for input of suggestion or accusation
     *
     * @param suggest (true if suggestion, false if accusation)
     * @return ArrayList of Strings describing the 3 cards they chose
     */
    public ArrayList<String> getMurder(Boolean suggest) {
        ArrayList<String> murder = new ArrayList<>();
        String room = null;
        String character = null;
        String weapon = null;

        if (!suggest) { //if accusation then ask user for a room, if it's a suggestion then we do not need to ask
            //prints out all room options
            for (String c : roomNames.keySet()) {
                System.out.println("Type " + c + " for " + roomNames.get(c));
            }

            // asks user for room
            System.out.println("Pick a room card: ");
            String roomInput = input.next();
            room = roomNames.get(roomInput);

            // error check here
            while (room == null) {
                invalidInput();
                System.out.println("Pick a room card: ");
                roomInput = input.next();
                room = roomNames.get(roomInput);
            }
            murder.add(room);
        }

        //prints out all character options
        for (String c : characterNames.keySet()) {
            System.out.println("Type " + c + " for " + characterNames.get(c));
        }

        // asks user for character
        System.out.println("Pick a character card: ");
        String charInput = input.next();
        character = characterNames.get(charInput);

        // error check here
        while (character == null) {
            invalidInput();
            System.out.println("Pick a character card: ");
            charInput = input.next();
            character = characterNames.get(charInput);
        }
        murder.add(character);

        //prints out all weapon options
        for (String c : weaponNames.keySet()) {
            System.out.println("Type " + c + " for " + weaponNames.get(c));
        }

        // asks user for weapon
        System.out.println("Pick a weapon card: ");
        String weaponInput = input.next();
        weapon = weaponNames.get(weaponInput);

        // error check here
        while (weapon == null) {
            invalidInput();
            System.out.println("Pick a weapon card: ");
            weaponInput = input.next();
            weapon = weaponNames.get(weaponInput);
        }
        murder.add(weapon);

        return murder;
    }

    /**
     * Create and store the weapon names
     */
    public void createWeapons() {
        weaponNames.put("1", "Candlestick");
        weaponNames.put("2", "Dagger");
        weaponNames.put("3", "Lead Pipe");
        weaponNames.put("4", "Revolver");
        weaponNames.put("5", "Rope");
        weaponNames.put("6", "Spanner");
    }

    /**
     * Create and store the character names
     */
    public void createCharacters() {
        characterNames.put("1", "Miss Scarlett");
        characterNames.put("2", "Colonel Mustard");
        characterNames.put("3", "Mrs. White");
        characterNames.put("4", "Mr. Green");
        characterNames.put("5", "Mrs. Peacock");
        characterNames.put("6", "Professor Plum");
    }

    /**
     * Create and store the room names
     */
    public void createRooms() {
        roomNames.put("1", "Dining Room");
        roomNames.put("2", "Kitchen");
        roomNames.put("3", "Ball Room");
        roomNames.put("4", "Conservatory");
        roomNames.put("5", "Billiard Room");
        roomNames.put("6", "Library");
        roomNames.put("7", "Study");
        roomNames.put("8", "Hall");
        roomNames.put("9", "Lounge");
    }

    /**
     * Asks for input of weapon and character
     *
     * @param room
     * @return ArrayList of Strings describing the 2 cards they chose
     */
    public ArrayList<String> makeSuggesstion(String room) {
        System.out.println("Pick a card from Character and Weapon types to make a suggestion");
        ArrayList<String> murder = getMurder(true);
        System.out.println("You suggestion is; Room: " + room + "  Character: " + murder.get(0) + "  Weapon: " + murder.get(1));
        return murder;
    }

    /**
     * Asks for input of accusation
     *
     * @return Suggestion object containing the 3 cards they chose
     */
    public Suggestion makeAccusation() {
        System.out.println("Pick a card from each type to make a accusation");
        ArrayList<String> murder = getMurder(false);
        Room sugRoom = new Room(murder.get(0));
        Character sugChar = new Character(murder.get(1));
        Weapon sugWeapon = new Weapon(murder.get(2));
        Suggestion sug = new Suggestion(sugRoom, sugChar, sugWeapon);
        System.out.println("Your accusation is; Room: " + sug.getRoom().getName() + "  Character: " + sug.getCharacter().getName() + "  Weapon: " + sug.getWeapon().getName());
        return sug;
    }

    public void wrongAccusation() {
        System.out.println("Your accusation was wrong. You have lost!");
    }

    public void correctAccusation() {
        System.out.println("Your accusation was right. You have WON!");
    }



    /**
     * Displays the hand
     *
     * @param hand
     */
    public void viewHand(ArrayList<Card> hand) {
        int i = 1;
        for (Card c : hand) {
            System.out.println(i + ". " + c.getName());


            i++;
        }
    }

    public void showHand(ArrayList<Card> hand) {
        bottomPanel.removeAll();
        for (Card c : hand) {
            ImageIcon icon = getCard(c.getName());
            if(icon != null) {
                JLabel label = new JLabel();
                label.setIcon(icon);
                bottomPanel.add(label);
            }
        }
    }


    public ImageIcon getCard(String name) {
        switch (name) {
            case "Candlestick":
                return candleStick;
            case "Dagger":
                return dagger;
            case "Lead Pipe":
                return leadPipe;
            case "Revolver":
                return revolver;
            case "Rope":
                return rope;
            case "Spanner":
                return spanner;
            case "Miss Scarlett":
                return scarlet;
            case "Colonel Mustard":
                return mustard;
            case "Mrs. White":
                return white;
            case "Mr. Green":
                return green;
            case "Mrs. Peacock":
                return peacock;
            case "Professor Plum":
                return plum;
            case "Dining Room":
                return diningRoom;
            case "Kitchen":
                return kitchen;
            case "Ball Room":
                return ballRoom;
            case "Conservatory":
                return conservatory;
            case "Billiard Room":
                return billiardRoom;
            case "Library":
                return library;
            case "Study":
                return study;
            case "Hall":
                return hall;
            case "Lounge":
                return lounge;
        }
        return null;
    }

    /**
     * Asks for input of if whether they want to make an accusation
     *
     * @return true if they want to make an accusation, otherwise false
     */
    public boolean shouldMakeAccusation() {
        System.out.println("Do you want to make an Accusation? ");
        System.out.println("Enter Y or N");
        String userInput = input.next();
        while(!userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N")){
            invalidInput();
            System.out.println("Enter Y or N");
            userInput = input.next();
        }

        if (userInput.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Asks for input of whether they want to continue to move after entering a room
     *
     * @return true if they want to keep moving, otherwise false
     */
    public boolean continueMove() {
        System.out.println("You are in a room!!!");
        System.out.println("Do you want to use the rest of your moves? Y or N");
        String userInput = input.next();
        while (!userInput.equalsIgnoreCase("Y") && !userInput.equalsIgnoreCase("N")) {
            invalidInput();
            System.out.println("Do you want to use the rest of your moves? Y or N");
            userInput = input.next();
        }
        if (userInput.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public void displayCard(Player withCard, Card toShow) {
        System.out.println("\nPlayer " + withCard.getSymbol() + " showed you " + toShow.getName() + ".");
    }

    /**
     * Asks for input of which card to show and then display that card
     *
     * @param choosing
     * @param chooseFrom
     */
    public void chooseCard(Player choosing, ArrayList<Card> chooseFrom) {
        System.out.println("\nPlayer " + choosing.getSymbol() + ", choose a card to show:");
        viewHand(chooseFrom);
        System.out.print("Number: ");
        String choose = input.next();

        while (!choose.matches("\\d+")) {
            invalidInput();
            System.out.print("Number: ");
            choose = input.nextLine();
        }

        int chosen = Integer.parseInt(choose) - 1;
        // error checking
        while (chosen < 0 || chosen >= chooseFrom.size()) {   // if out of bounds
            invalidInput();
            System.out.print("Number: ");
            choose = input.nextLine();
            while (!choose.matches("\\d+")) {
                invalidInput();
                System.out.print("Number: ");
                choose = input.nextLine();
            }
            chosen = Integer.parseInt(choose) - 1;
        }
        System.out.println("\nPlayer " + choosing.getSymbol() + " showed you " + chooseFrom.get(chosen).getName() + ".");
    }

    public void drawWeapons(Map<String, String> map) {
        for (String room : map.keySet()) {
            System.out.println("Room: " + room + " contains Weapon: " + map.get(room));
        }
    }

    public void displayWinningPlayer(char player) {
        if (player == ' ') {
            System.out.println("You all lost the game");
        } else {
            System.out.println("CONGRATS Player " + player + "\nYou have won the game!!!");
        }
    }

    public void noCardShown() {
        System.out.println("\nNobody has those cards.");
    }
}
