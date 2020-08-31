// Controls all output and input of the game

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class GUI extends JFrame implements MouseListener, KeyListener, MouseMotionListener {
    // Collections
    private ArrayList<String> weaponNames = new ArrayList<>();
    private ArrayList<String> roomNames = new ArrayList<>();
    private ArrayList<String> characterNames = new ArrayList<>();
    private ArrayList<Player> players;

    AtomicBoolean mousePressed = new AtomicBoolean(false);
    Cell movedCell = null;
    private Board board;
    // Main Panels
    JPanel playersPanel = new JPanel();
    JPanel infoPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel mainPanel = new JPanel(new BorderLayout());
    JLabel firstDice = new JLabel();
    JLabel secondDice = new JLabel();
    // Cell Information
    private int cellSize = 26; //change this to change the size of the window on screen
    private int cellsWide = 24;
    private int cellsHigh = 25;
    private int width = cellSize * (cellsWide + 2); //+2 leaves space around the boards as a border
    private int height = cellSize * (cellsHigh + 2);
    // dice images
    ImageIcon dice1 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice1.jpg")).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    ImageIcon dice2 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice2.jpg")).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    ImageIcon dice3 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice3.jpg")).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    ImageIcon dice4 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice4.jpg")).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    ImageIcon dice5 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice5.jpg")).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    ImageIcon dice6 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice6.jpg")).getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
    // all cards got from https://www.quizmasters.biz/Pub%20Genius/Cluedo/Cluedo.html
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


    /*
    * Constructor for the GUI
    *
    * @param board, players
    */
    public GUI(Board board, ArrayList<Player> players) {
        this.board = board;
        this.players = players;
        createRooms();
        createCharacters();
        createWeapons();
        initUI();
    }

    /**
     *  Opens all panels
     */
    private void initUI() {
        mainPanel.addMouseMotionListener(this);
        mainPanel.addMouseListener(this);
        addKeyListener(this);
        setTitle("Cluedo Game");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setResizable(true);

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem i1 = new JMenuItem("Instructions");
        JMenuItem i2 = new JMenuItem("Exit");

        menu.add(i1);
        menu.add(i2);
        menuBar.add(menu);

        // Instructions item button
        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d =  makeDialog("Instructions for Cluedo:");
                d.setLayout(new GridLayout(3, 1));
                JLabel label1 = new JLabel("Go into each room to find clues");
                JLabel label2 = new JLabel("To be the first to find the murder circumstances");

                d.add(label1);
                d.add(label2);
                // setsize and visibility of dialog
                setSizeandVisible(d, 200, 200);
            }
        });

        // Exit item button
        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Get images
        firstDice.setIcon(dice1);
        secondDice.setIcon(dice2);
        setJMenuBar(menuBar);

        JPanel dicePanel = new JPanel(new GridBagLayout());

        infoPanel.setLayout(new GridLayout(2, 1));
        playersPanel.setBackground(Color.decode("#0C9036"));
        infoPanel.setBackground(Color.decode("#0C9036"));
        dicePanel.setBackground(Color.decode("#1B562E"));
        mainPanel.setBackground(Color.cyan);
        bottomPanel.setPreferredSize(new Dimension(100, 100));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        gbc = getGBC(gbc, 0, 0, 1, 1, 3, 3);
        gbc.anchor = GridBagConstraints.EAST;
        dicePanel.add(firstDice, gbc);

        gbc = getGBC(gbc, 1, 0, 1, 1, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;
        dicePanel.add(secondDice, gbc);

        gbc = getGBC(gbc, 0, 0, 1, 3, 4, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(new Board(players), gbc);

        gbc = getGBC(gbc, 1, 0, 1, 1, 0.5, 2);
        mainPanel.add(playersPanel, gbc);

        gbc = getGBC(gbc, 1, 2, 1, 1, 0.5, 0.25);
        mainPanel.add(dicePanel, gbc);

        gbc = getGBC(gbc, 0, 3, 2, 1, 0.5, 1);
        mainPanel.add(bottomPanel, gbc);

        gbc = getGBC(gbc, 1, 1, 1, 1, 0.5, 1);
        mainPanel.add(infoPanel, gbc);

        add(mainPanel);
        setVisible(false);
    }

    /*
    * Sets the icon of the dice to be displayed
    *
    * @param first die, second die
    */
    public void setDice(int one, int two) {
        firstDice.setIcon(getDice(one));
        secondDice.setIcon(getDice(two));
    }

    /**
     * Asks for input of the number of people playing
     *
     * @return the number of people playing
     */
    public int getNumPlayers() {
        JDialog d = makeDialog("Number of Players:");
        String[] characterString = {"3", "4", "5", "6"};
        JComboBox<String> num = new JComboBox<>(characterString);
        JButton next = new JButton("Next");
        d.setLayout(new FlowLayout());
        d.add(num);
        d.add(next);

        // If button is clicked a new panel will appear until or the game will begin
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });
        setSizeandVisible(d, 250, 150);
        return Integer.parseInt(num.getSelectedItem().toString());
    }

    /**
     *  Updates information each player's turn or movement
     *
     * @param p
     * @param num
     */
    public void updateInfo(Player p, int num){
        infoPanel.removeAll();
        JLabel label1 = new JLabel("It is " + p.getName() + "'s turn: Player " + p.getPlayerNum());
        JLabel label2 = new JLabel("Number of moves left: " + num);
        infoPanel.add(label1);
        infoPanel.add(label2);
        infoPanel.validate();
        infoPanel.repaint();
    }

    /**
     * One of the setup panels. This panel will pop up secend to ask for the player's
     * name and their character of choice.
     *
     * @param numPlayer
     * @return
     */
    public ArrayList<String> setCharacters(int numPlayer) {
        ArrayList names = new ArrayList();
        JLabel label = new JLabel();
        JDialog d = new JDialog(this, true);
        d.setLayout(new GridLayout(10, 1));
        // Group of character radio buttons
        JTextField name = new JTextField();
        JRadioButton button1 = new JRadioButton("Miss Scarlett");
        JRadioButton button2 = new JRadioButton("Colonel Mustard");
        JRadioButton button3 = new JRadioButton("Mrs. White");
        JRadioButton button4 = new JRadioButton("Mr. Green");
        JRadioButton button5 = new JRadioButton("Mrs. Peacock");
        JRadioButton button6 = new JRadioButton("Professor Plum");
        ButtonGroup bg = new ButtonGroup();
        bg.add(button1);
        bg.add(button2);
        bg.add(button3);
        bg.add(button4);
        bg.add(button5);
        bg.add(button6);

        JButton next = new JButton("Next");

        // If button is clicked a new panel will appear until or the game will begin
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        for (int i = 0; i < numPlayer; i++) {
            label.setText("Enter your name player: " + (i + 1));
            d.add(label);
            d.add(name);
            d.add(button1);
            d.add(button2);
            d.add(button3);
            d.add(button4);
            d.add(button5);
            d.add(button6);
            // Enables a character button if another player is already chosen
            if (button1.isEnabled()) {
                button1.setSelected(true);
            } else if (button2.isEnabled()) {
                button2.setSelected(true);
            } else if (button3.isEnabled()) {
                button3.setSelected(true);
            } else if (button4.isEnabled()) {
                button4.setSelected(true);
            } else if (button5.isEnabled()) {
                button5.setSelected(true);
            } else if (button6.isEnabled()) {
                button6.setSelected(true);
            }
            d.add(next);
            setSizeandVisible(d, 200, 200);
            names.add(name.getText());
            name.setText("");
            if (button1.isSelected()) {
                names.add(button1.getText());
                button1.setEnabled(false);
            } else if (button2.isSelected()) {
                names.add(button2.getText());
                button2.setEnabled(false);
            } else if (button3.isSelected()) {
                names.add(button3.getText());
                button3.setEnabled(false);
            } else if (button4.isSelected()) {
                names.add(button4.getText());
                button4.setEnabled(false);
            } else if (button5.isSelected()) {
                names.add(button5.getText());
                button5.setEnabled(false);
            } else if (button6.isSelected()) {
                names.add(button6.getText());
                button6.setEnabled(false);
            }
        }
        this.setVisible(true);
        return names;
    }

    /**
     * Player information that will be displayed on the right side panel
     *
     * @param players
     */
    public void displayPlayers(ArrayList<Player> players) {
        playersPanel.setLayout(new GridLayout(players.size()+1, 1));
        for (int i = 0; i < players.size(); i++) {
            String text = "Player " + (i + 1) + ": " + players.get(i).getCharacterCard().getName() + ": " +
                    players.get(i).getName();
            playersPanel.add(new JLabel(text));
        }
    }
    
    /*
    * Dialog for the
    */
    public void nextPlayer(Player p){
        bottomPanel.removeAll();
        JDialog d = makeDialog("It is player " + p.getPlayerNum() + "'s turn.");
        d.setLayout(new GridLayout(2, 1));
        JButton button = new JButton("OK");
        d.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });
        setSizeandVisible(d, 100, 100);
    }

    /**
     * Asks for input of which direction to move player
     *
     * @return "U", "D", "L", "R" indicating up, down, left, or right
     */
    public String getMoves(Player player) {
        movedCell = null;
        mousePressed.set(false);

        while(!mousePressed.get()){

        }

        if(movedCell!=null){
            if(movedCell.getSymbol()=='U'){
                return "U";
            }else if(movedCell.getSymbol()=='D'){
                return "D";
            }else if(movedCell.getSymbol()=='L'){
                return "L";
            }else if(movedCell.getSymbol()=='R'){
                return "R";
            }
        }
        return getDirection(movedCell, player);
    }

    /**
     * Will get the direction of where the player has moved using.
     * Returns a string of the direction of where the player has moved
     *
     * @param c
     * @param p
     * @return "L", "R", "U", "D" indicates left, right, up, down
     */
    public String getDirection(Cell c, Player p){
        if(c == null){
            return "";
        }
        int playerX = p.getxPos();
        int playerY = p.getyPos();
        int cellX = c.getxCoord();
        int cellY = c.getyCoord();
        int colDiff = playerX - cellX;
        int rowDiff = playerY - cellY;
        if(colDiff == 1 && rowDiff == 0){
            return "L";
        }else if(colDiff == -1 && rowDiff == 0){
            return "R";
        }else if(colDiff == 0 && rowDiff == 1){
            return "U";
        }else if(colDiff == 0 && rowDiff == -1){
            return "D";
        }
        return "";
    }

    /**
     * Ask for input of if the player wants to make a suggestion or accusation or neither
     *
     * @return "S", "A", "N" indication suggestion, accusation or neither
     */
    public String checkSuggestion() {
        JDialog makeSuggestion = makeDialog("Do you want to make a:");
        makeSuggestion.setLayout(new GridLayout(5, 0));
        // three radio buttons
        JRadioButton suggest = new JRadioButton("Suggestion", true);
        JRadioButton accuse = new JRadioButton("Accusation");
        JRadioButton none = new JRadioButton("Neither");
        JButton next = new JButton("next");
        ButtonGroup bg = new ButtonGroup();
        bg.add(suggest);
        bg.add(accuse);
        bg.add(none);

        makeSuggestion.add(suggest);
        makeSuggestion.add(accuse);
        makeSuggestion.add(none);
        makeSuggestion.add(next);

        // if next is clicked the panel will close and move to the next panel
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeSuggestion.dispose();
            }
        });

        setSizeandVisible(makeSuggestion, 200, 300);

        if (suggest.isSelected()) {
            return "S";
        } else if (accuse.isSelected()) {
            return "A";
        } else if (none.isSelected()) {
            return "N";
        }
        return "";
    }

    /**
     * Asks for input of suggestion or accusation
     *
     * @param suggest (true if suggestion, false if accusation)
     * @return ArrayList of Strings describing the 3 cards they chose
     */
    public ArrayList<String> getMurder(Boolean suggest) {
        ArrayList<String> murder = new ArrayList<>();
        // Three groups of buttons to chose from
        JLabel label1 = new JLabel("Pick a Character:");
        JLabel label2 = new JLabel("Pick a Weapon:");
        JLabel label3 = new JLabel("Pick a Room:");
        JDialog d = new JDialog(this, true);
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel characters = new JPanel(new GridLayout(0, 1));
        JPanel weapons = new JPanel(new GridLayout(0, 1));
        d.setLayout(new GridBagLayout());

        characters.add(label1);
        ButtonGroup bg = new ButtonGroup();
        // iterates through all characters and creates a button
        for(String s: characterNames){
            JRadioButton button = new JRadioButton(s, true);
            button.setActionCommand(s);
            bg.add(button);
            characters.add(button);
        }
        gbc = getGBC(gbc, 0, 0, 1, 1, 1, 2);
        d.add(characters, gbc);
        weapons.add(label2);
        ButtonGroup bg2 = new ButtonGroup();
        // iterates through all weapons and creates a button
        for(String s: weaponNames){
            JRadioButton button = new JRadioButton(s, true);
            button.setActionCommand(s);
            bg2.add(button);
            weapons.add(button);
        }
        gbc = getGBC(gbc, 1, 0, 1, 1, 1, 2);
        d.add(weapons, gbc);
        ButtonGroup bg3 = new ButtonGroup();

        // Will only create room buttons if player is making an accusation
        if (!suggest) {
            JPanel rooms = new JPanel(new GridLayout(0, 1));
            rooms.add(label3);
            bg3 = new ButtonGroup();
            // iterates through all rooms and creates a button
            for(String s: roomNames){
                JRadioButton button = new JRadioButton(s, true);
                button.setActionCommand(s);
                bg3.add(button);
                rooms.add(button);
            }
            gbc = getGBC(gbc, 2, 0, 1, 1, 1, 2);
            d.add(rooms, gbc);
        }

        JButton next = new JButton("Next");
        gbc = getGBC(gbc, 1, 1, 1, 1, 1, 1);
        next.setSize(50, 25);
        d.add(next, gbc);

        // dispose of the panel when the next button is clicked
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        setSizeandVisible(d, 600, 600);

        String character = bg.getSelection().getActionCommand();
        String weapon = bg2.getSelection().getActionCommand();
        murder.add(character);
        murder.add(weapon);
        // room will be added to ArrayList if making an accusation so no "null" variable is added
        if (!suggest) {
            String room = bg3.getSelection().getActionCommand();
            murder.add(room);
        }
        return murder;
    }

    /**
     * Create and store the weapon names
     */
    public void createWeapons() {
        weaponNames.add("Candlestick");
        weaponNames.add("Dagger");
        weaponNames.add("Lead Pipe");
        weaponNames.add("Revolver");
        weaponNames.add("Rope");
        weaponNames.add("Spanner");
    }

    /**
     * Create and store the character names
     */
    public void createCharacters() {
        characterNames.add("Miss Scarlett");
        characterNames.add("Colonel Mustard");
        characterNames.add("Mrs. White");
        characterNames.add("Mr. Green");
        characterNames.add("Mrs. Peacock");
        characterNames.add("Professor Plum");
    }

    /**
     * Create and store the room names
     */
    public void createRooms() {
        roomNames.add("Dining Room");
        roomNames.add("Kitchen");
        roomNames.add("Ball Room");
        roomNames.add("Conservatory");
        roomNames.add("Billiard Room");
        roomNames.add("Library");
        roomNames.add("Study");
        roomNames.add("Hall");
        roomNames.add("Lounge");
    }

    /**
     * Asks for input of weapon and character
     *
     * @param room
     * @return ArrayList of Strings describing the 2 cards they chose
     */
    public ArrayList<String> makeSuggesstion(String room) {
        ArrayList<String> murder = getMurder(true);
        return murder;
    }

    /**
     * Asks for input of accusation
     *
     * @return Suggestion object containing the 3 cards they chose
     */
    public Suggestion makeAccusation() {
        ArrayList<String> murder = getMurder(false);
        Character sugChar = new Character(murder.get(0));
        Weapon sugWeapon = new Weapon(murder.get(1));
        Room sugRoom = new Room(murder.get(2));
        Suggestion sug = new Suggestion(sugRoom, sugChar, sugWeapon);
        return sug;
    }

    /**
     *  A panel to indicate that the player has made a wrong accusation
     */
    public void wrongAccusation() {
        JDialog d = makeDialog("Your accusation was wrong. You have lost!");
        d.setLayout(new GridLayout(2, 1));
        JButton ok = new JButton("OK");

        d.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        setSizeandVisible(d, 300, 100);
    }

    /**
     *  A panel to indicate that the player has made a correct accusation
     */
    public void correctAccusation() {
        JDialog d = makeDialog("Your accusation was right. You have WON!");
        d.setLayout(new GridLayout(2, 1));

        JButton ok = new JButton("OK");
        d.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        setSizeandVisible(d, 300, 100);
    }

    /**
     * Print the pictures that represent the current player's hand on the bottom panel
     *
     * @param hand
     */
    public void showHand(ArrayList<Card> hand) {
        bottomPanel.removeAll(); // clear bottom panel of all pictures
        // Prints current players hand
        for (Card c : hand) {
            ImageIcon icon = getCard(c.getName());
            if (icon != null) {
                JLabel label = new JLabel();
                label.setIcon(icon);
                bottomPanel.add(label);
            }
        }
        bottomPanel.validate();
        bottomPanel.repaint();
    }

    /**
     * Gets the image that represents the card
     *
     * @param name
     * @return
     */
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
     * Gets the image that represents a side of the dice
     *
     * @param num
     * @return
     */
    public ImageIcon getDice(int num) {
        switch (num) {
            case 1:
                return dice1;
            case 2:
                return dice2;
            case 3:
                return dice3;
            case 4:
                return dice4;
            case 5:
                return dice5;
            case 6:
                return dice6;
        }
        return null;
    }

    /**
     * Asks for input of if whether they want to make an accusation
     *
     * @return true if they want to make an accusation, otherwise false
     */
    public boolean shouldMakeAccusation() {
        ArrayList<String> answer = new ArrayList<>();
        JDialog d = makeDialog("Do you want to make an Accusation?");
        d.setLayout(new GridLayout(3, 1));
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        d.add(yes);
        d.add(no);

        // will dispose of the panel if any button is clicked
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.add("Y");
                d.dispose();
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.add("N");
                d.dispose();
            }
        });

        setSizeandVisible(d, 300, 200);
        if(answer.isEmpty()){
            answer.add("Y");
        }

        if (answer.get(0).equalsIgnoreCase("Y")) {
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
        ArrayList<String> answer = new ArrayList<>();
        JDialog d = makeDialog("You are in a room!!!");
        d.setLayout(new GridLayout(4, 1));

        JLabel question = new JLabel("Do you want to use the rest of your moves?");
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        d.add(question);
        d.add(yes);
        d.add(no);

        // will dispose of the panel if any button is clicked
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.add("Y");
                d.dispose();
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.add("N");
                d.dispose();
            }
        });

        setSizeandVisible(d, 300, 200);
        if(answer.isEmpty()){
            answer.add("Y");
        }

        if (answer.get(0).equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Will display the card made in a suggestion from the current player
     * that another player has in their hand
     *
     * @param withCard
     * @param toShow
     */
    public void displayCard(Player withCard, Card toShow) {
        JDialog d = makeDialog("Player " + withCard.getSymbol() + " showed you: ");
        d.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel icon = new JLabel();
        JButton button = new JButton("OK");
        icon.setIcon(getCard(toShow.getName()));
        gbc = getGBC(gbc, 0, 1, 1, 1, 1,  1);
        d.add(icon, gbc);
        gbc = getGBC(gbc, 0, 2, 1, 1, 1, 1);
        d.add(button, gbc);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        setSizeandVisible(d, 200, 500);
    }

    /**
     * Asks for input of which card to show and then display that card
     *
     * @param choosing
     * @param chooseFrom
     */
    public void chooseCard(Player choosing, ArrayList<Card> chooseFrom) {
        JDialog d = makeDialog("Player " + choosing.getSymbol() + ", choose a card to show:");
        d.setLayout(new GridLayout(0, 1));
        ButtonGroup bg = new ButtonGroup();

        for (Card c : chooseFrom) {
            JRadioButton button = new JRadioButton(c.getName(), true);
            button.setActionCommand(c.getName());
            bg.add(button);
            d.add(button);
        }

        JButton next = new JButton("next");
        d.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });
        setSizeandVisible(d, 300, 150);
        displayCard(choosing, new Card(bg.getSelection().getActionCommand()));

    }

    /**
     * Displayed when a player has won
     *
     * @param player
     */
    public void displayWinningPlayer(char player) {
        JDialog d = makeDialog("CONGRATS Player " + player);
        d.setLayout(new GridLayout(3, 1));
        JLabel label = new JLabel("You have won the game!!!");

        JButton ok = new JButton("OK");
        d.add(label);
        d.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        setSizeandVisible(d, 300, 150);
    }

    /**
     * Create a panel if no other player has a card made in a suggestion
     */
    public void noCardShown() {
        JDialog d = makeDialog("No one has those cards");
        d.setLayout(new GridLayout(2, 1));
        JButton ok = new JButton("OK");
        d.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        setSizeandVisible(d, 100, 100);
    }

    /*
    * Creates a group of buttons from a list of strings for a dialog
    *
    * @param dialog, AL<String>
    *
    * @return ButtonGroup
    */
    public ButtonGroup addButtonGroup(JDialog d, ArrayList<String> list) {
        ButtonGroup group = new ButtonGroup();
        for (String s : list) {
            JRadioButton button = new JRadioButton(s, true);
            button.setActionCommand(s);
            group.add(button);
            d.add(button);
        }
        return group;
    }

    /*
    * Makes a new JDialog from a string
    *
    * @param text
    *
    * @return JDialog
    */
    public JDialog makeDialog(String text) {
        JDialog d = new JDialog(this, true);
        JLabel label = new JLabel(text);
        d.add(label);
        return d;
    }

    /*
    * Sets the size of a dialog box and makes it visible
    *
    * @param dialog, width, height
    */
    public void setSizeandVisible(JDialog d, int x, int y) {
        d.setSize(x, y);
        d.setVisible(true);
    }

    /*
    * 
    */
    public GridBagConstraints getGBC(GridBagConstraints gbc, int x, int y, int width, int height, double xweight, double yweight) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = xweight;
        gbc.weighty = yweight;
        return gbc;
    }
    
    /*
    * 
    */
    public void toolTip(String player){
        mainPanel.setToolTipText(player);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Cell c = board.getCellFromPixel(e.getX(), e.getY());
        if(c!=null) {
            movedCell = new Cell(c.getxCoord(), c.getyCoord(), '-');
            mousePressed.set(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            movedCell = new Cell(1, 1, 'U');
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            movedCell = new Cell(1, 1, 'D');
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movedCell = new Cell(1, 1, 'R');
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movedCell = new Cell(1, 1, 'L');
        }
        mousePressed.set(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Boolean onPlayer = false;
        String player = null;
        Cell cell = board.getCellFromPixel(e.getX(), e.getY());
        if(cell!=null) {
            for(Player p: CluedoGame.players){
                if(p.getxPos()==cell.getxCoord() && p.getyPos()==cell.getyCoord()){
                    player = p.getCharacterCard().getName();
                    board.setHover(true);
                    onPlayer = true;
                }
            }
            for(Cell c: Board.boardWeapons){
                if(c.getxCoord()==cell.getxCoord()&&c.getyCoord()==cell.getyCoord()){
                    player = CluedoGame.weaponNames.get(c.getSymbol());
                    board.setHover(true);
                    onPlayer = true;
                }
            }
        }
        if(!onPlayer){
            board.setHover(false);
        }
        toolTip(player);
    }
}
