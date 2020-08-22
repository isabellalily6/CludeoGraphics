// Controls all output and input of the game

import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.*;

public class ConsoleUI extends JFrame implements MouseListener, KeyListener, MouseMotionListener {
    private ArrayList<String> weaponNames = new ArrayList<>();
    private ArrayList<String> roomNames = new ArrayList<>();
    private ArrayList<String> characterNames = new ArrayList<>();

    AtomicBoolean mousePressed = new AtomicBoolean(false);
    Cell movedCell = null;
    CluedoGame game;
    private Board board;
    private ArrayList<Player> players;
    JPanel playersPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel mainPanel = new JPanel(new BorderLayout());
    JLabel firstDice = new JLabel();
    JLabel secondDice = new JLabel();

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


    public ConsoleUI(Board board, ArrayList<Player> players, CluedoGame game) {
        this.board = board;
        this.players = players;
        this.game = game;
        createRooms();
        createCharacters();
        createWeapons();
        initUI();
    }

    private void initUI() {
        addMouseListener(this);
        addKeyListener(this);
        setTitle("Cluedo Game");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setResizable(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem i1 = new JMenuItem("Instructions");
        JMenuItem i2 = new JMenuItem("Exit");

        menu.add(i1);
        menu.add(i2);
        menuBar.add(menu);


        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d =  makeDialog("Instructions for Cluedo:");
                // setsize and visibility of dialog
                setSizeandVisible(d, 200, 200);

            }
        });

        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        firstDice.setIcon(dice1);
        secondDice.setIcon(dice2);
        setJMenuBar(menuBar);

        JPanel dicePanel = new JPanel(new GridBagLayout());

        playersPanel.setBackground(Color.decode("#0C9036"));
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

        gbc = getGBC(gbc, 0, 0, 1, 3, 4, 4);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(new Board(players), gbc);

        gbc = getGBC(gbc, 1, 0, 1, 1, 0.5, 5);
        mainPanel.add(playersPanel, gbc);

        gbc = getGBC(gbc, 1, 2, 1, 1, 0.5, 0.25);
        mainPanel.add(dicePanel, gbc);

        gbc = getGBC(gbc, 0, 3, 2, 1, 0.5, 1);
        mainPanel.add(bottomPanel, gbc);

        add(mainPanel);
        setVisible(false);
    }

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


        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });
        setSizeandVisible(d, 250, 150);

        return Integer.parseInt(num.getSelectedItem().toString());

    }


    public ArrayList<String> setCharacters(int numPlayer) {
        ArrayList names = new ArrayList();
        JLabel label = new JLabel();
        JDialog d = new JDialog(this, true);
        d.setLayout(new GridLayout(10, 1));
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
                System.out.println("ye");
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


    public void displayPlayers(ArrayList<Player> players) {
        playersPanel.setLayout(new GridLayout(players.size() + 6, 1));
        for (int i = 0; i < players.size(); i++) {
            String text = "Player " + (i + 1) + ": " + players.get(i).getCharacterCard().getName() + ": " +
                    players.get(i).getName();
            playersPanel.add(new JLabel(text));
        }
        playersPanel.add(new JLabel("Revolver = R"));
        playersPanel.add(new JLabel("Dagger = -"));
        playersPanel.add(new JLabel("Spanner = S"));
        playersPanel.add(new JLabel("CandleStick = |"));
        playersPanel.add(new JLabel("Rope = &"));
        playersPanel.add(new JLabel("Lead Pipe = L"));
    }

    /**
     * Asks for input of which direction to move player
     *
     * @return "U", "D", "L", "R" indicating up, down, left, or right
     */
    public String getMoves(Player player) {
        movedCell = null;
        mousePressed.set(false);
        System.out.println("Do you want to move UDLR? ");

        while (!mousePressed.get()) {
            //System.out.println(mousePressed);
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

        JLabel label1 = new JLabel("Pick a Character:");
        JLabel label2 = new JLabel("Pick a Weapon:");
        JLabel label3 = new JLabel("Pick a Room");
        JDialog d = new JDialog(this, true);
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel characters = new JPanel(new GridLayout(0, 1));
        JPanel weapons = new JPanel(new GridLayout(0, 1));
        d.setLayout(new GridBagLayout());

        characters.add(label1);
        ButtonGroup bg = new ButtonGroup();
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
        for(String s: weaponNames){
            JRadioButton button = new JRadioButton(s, true);
            button.setActionCommand(s);
            bg2.add(button);
            weapons.add(button);
        }
        gbc = getGBC(gbc, 1, 0, 1, 1, 1, 2);
        d.add(weapons, gbc);
        ButtonGroup bg3 = new ButtonGroup();

        if (!suggest) {
            JPanel rooms = new JPanel(new GridLayout(0, 1));
            rooms.add(label3);
            bg3 = new ButtonGroup();
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


    public void showHand(ArrayList<Card> hand) {
        bottomPanel.removeAll();
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

        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.add("Y");
                System.out.println(answer.get(0));
                d.dispose();
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.add("N");
                System.out.println(answer.get(0));
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
        JDialog d = makeDialog("No one has those cards");
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


    public JDialog makeDialog(String text) {
        JDialog d = new JDialog(this, true);
        JLabel label = new JLabel(text);
        d.add(label);
        return d;
    }

    public void setSizeandVisible(JDialog d, int x, int y) {
        d.setSize(x, y);
        d.setVisible(true);
    }

    public GridBagConstraints getGBC(GridBagConstraints gbc, int x, int y, int width, int height, double xweight, double yweight) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = xweight;
        gbc.weighty = yweight;
        return gbc;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Cell c = board.getCellFromPixel(e.getX(), e.getY());
        movedCell = new Cell(c.getxCoord(), c.getyCoord(), '-');
        mousePressed.set(true);
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
        System.out.println(e.getY() + "entered" + e.getX());
    }
}
