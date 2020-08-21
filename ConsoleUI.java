// Controls all output and input of the game

import jdk.nashorn.internal.scripts.JD;
import sun.misc.JavaLangAccess;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.management.remote.rmi.RMIConnectionImpl;
import javax.swing.*;

public class ConsoleUI extends JFrame implements MouseListener {
    private Map<String, String> weaponNames = new HashMap<>();
    private Map<String, String> roomNames = new HashMap<>();
    private Map<String, String> characterNames = new HashMap<>();
    static Scanner input = new Scanner(System.in);
    AtomicBoolean mousePressed = new AtomicBoolean(false);
    Cell movedCell = null;
    CluedoGame game;
    private Board board;
    private ArrayList<Player> players;
    JPanel playersPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel mainPanel = new JPanel(new BorderLayout());
    boolean buttonPressed = false;
    JLabel firstDice = new JLabel();
    JLabel secondDice = new JLabel();

    private int cellSize = 26; //change this to change the size of the window on screen
    private int cellsWide = 24;
    private int cellsHigh = 25;
    private int width = cellSize*(cellsWide+2); //+2 leaves space around the boards as a border
    private int height = cellSize*(cellsHigh+2);

    // dice images
    ImageIcon dice1 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice1.jpg")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    ImageIcon dice2 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice2.jpg")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    ImageIcon dice3 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice3.jpg")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    ImageIcon dice4 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice4.jpg")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    ImageIcon dice5 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice5.jpg")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
    ImageIcon dice6 = new ImageIcon(new ImageIcon(getClass().getResource("Pictures/dice6.jpg")).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
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


    firstDice.setIcon(dice1);
    secondDice.setIcon(dice2);
        setJMenuBar(menuBar);



//this.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.cyan);

        bottomPanel.setPreferredSize(new Dimension(100, 100));



        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));



        JPanel dicePanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 3;
        gbc.weighty = 3;
        gbc.anchor = GridBagConstraints.EAST;
        dicePanel.add(firstDice, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        dicePanel.add(secondDice, gbc);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        buttonPanel.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 1;
        //playersPanel.add(b);

        buttonPanel.setBackground(Color.decode("#1B562E"));
        playersPanel.setBackground(Color.decode("#0C9036"));
        dicePanel.setBackground(Color.decode("#1B562E"));
        //bottomPanel.setBackground(Color.green);
      /*  mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(rightPanel, BorderLayout.EAST);*/
    	setVisible(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 4;
        gbc.weighty = 4;
        gbc.gridheight = 3;
        gbc.gridwidth = 1;

        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new Board(players), gbc);
        //mainPanel.add(buttonPanel, gbc);
        //mainPanel.add(playersPanel, gbc);

        gbc.weightx = 1;
        gbc.weighty = 4;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1 ;
        mainPanel.add(playersPanel, gbc);


        gbc.weighty = 0.5;
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

    public void setDice(int one, int two){
        firstDice.setIcon(getDice(one));
        secondDice.setIcon(getDice(two));
    }
    /**
     * Asks for input of the number of people playing
     *
     * @return the number of people playing
     */
    public int getNumPlayers() {
            JDialog d = new JDialog(this);
            d.setModal(true);
            JLabel label = new JLabel("Num of Players:");
            String[] characterString= {"3", "4", "5", "6"};
            JComboBox<String> num = new JComboBox<>(characterString);
            JButton next = new JButton("Next");
            d.setLayout(new FlowLayout());
            d.add(label);
            d.add(num);
            d.add(next);

             d.setSize(200, 200);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.setModal(false);

                d.dispose();

            }
        });
        d.setVisible(true);
        return Integer.parseInt(num.getSelectedItem().toString());

    }


    public ArrayList<String> setCharacters(int numPlayer){
        ArrayList names = new ArrayList();
        buttonPressed = false;
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
            ButtonGroup bg=new ButtonGroup();
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
                    buttonPressed = true;
                    d.dispose();
                }
            });

        for(int i = 0; i < numPlayer; i++) {
            label.setText("Enter your name player: "+ (i+1));
            d.add(label);
            d.add(name);
            d.add(button1);
            d.add(button2);
            d.add(button3);
            d.add(button4);
            d.add(button5);
            d.add(button6);
            if(button1.isEnabled()){
                button1.setSelected(true);
            }else if(button2.isEnabled()){
                button2.setSelected(true);
            }else if(button3.isEnabled()){
                button3.setSelected(true);
            }else if(button4.isEnabled()){
                button4.setSelected(true);
            }else if(button5.isEnabled()){
                button5.setSelected(true);
            }else if(button6.isEnabled()){
                button6.setSelected(true);
            }


            d.add(next);
            d.setSize(200, 200);
            d.setVisible(true);
        }
        setVisible(true);
        return names;
    }


    public void displayPlayers(ArrayList<Player> players){

        playersPanel.setLayout(new GridLayout(players.size() + 6, 1));
        for(int i = 0; i < players.size(); i++){
            String text = "Player " + (i+1) + ": " + players.get(i).getName();
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
    public Cell getMoves() {
        mousePressed.set(false);
        System.out.println("Do you want to move UDLR? ");

        while(!mousePressed.get()){
            //System.out.println(mousePressed);

        }

        return movedCell;

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
        final String[] text = {""};
        System.out.println("Do you want to make a suggestion (S), an Accusation (A) or neither (N)? ");
        System.out.println("Type S for suggestion, A for accusation and N for neither");
        JDialog makeSuggestion = new JDialog(this, true);
        makeSuggestion.setSize(150,200);
        makeSuggestion.setLayout(new GridLayout(5, 1));
        // three radio buttons
        // one next button
        JLabel question = new JLabel("Do you want to make a:");
        JRadioButton suggest = new JRadioButton("Suggestion", true);
        JRadioButton accuse = new JRadioButton("Accusation");
        JRadioButton none = new JRadioButton("Neither");
        JButton next = new JButton("next");
        ButtonGroup bg=new ButtonGroup();
        bg.add(suggest);
        bg.add(accuse);
        bg.add(none);

        makeSuggestion.add(question);
        makeSuggestion.add(suggest);
        makeSuggestion.add(accuse);
        makeSuggestion.add(none);
        makeSuggestion.add(next);


        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(suggest.isSelected()){
                    text[0] = "S";
                }else if(accuse.isSelected()){
                    text[0] = "A";
                }else if(none.isSelected()){
                    text[0] = "N";
                }
                makeSuggestion.dispose();
            }
        });


        makeSuggestion.setVisible(true);

        System.out.println(text[0]);

        return text[0];
    }

    /**
     * Asks for input of suggestion or accusation
     *
     * @param suggest (true if suggestion, false if accusation)
     * @return ArrayList of Strings describing the 3 cards they chose
     */
   public ArrayList<String> getMurder(Boolean suggest) {
        ArrayList<String> murder = new ArrayList<>();
        ButtonGroup bg=new ButtonGroup();
        ButtonGroup bg2 = new ButtonGroup();
        ButtonGroup bg3 = new ButtonGroup();

        JLabel label1 = new JLabel("Pick a Character:");
        JLabel label2 = new JLabel("Pick a Weapon:");
        JLabel label3 = new JLabel("Pick a Room");
        JDialog d = new JDialog(this, true);
        if(suggest){
            d.setLayout(new GridLayout(15, 1));
        }else{
            d.setLayout(new GridLayout(25, 1));
        }
        JRadioButton button1 = new JRadioButton("Miss Scarlett", true);
        button1.setActionCommand("Miss Scarlett");
        JRadioButton button2 = new JRadioButton("Colonel Mustard");
       button2.setActionCommand("Colonel Mustard");
        JRadioButton button3 = new JRadioButton("Mrs. White");
       button3.setActionCommand("Mrs. White");
        JRadioButton button4 = new JRadioButton("Mr. Green");
       button4.setActionCommand("Mr. Green");
        JRadioButton button5 = new JRadioButton("Mrs. Peacock");
       button5.setActionCommand("Mrs. Peacock");
        JRadioButton button6 = new JRadioButton("Professor Plum");
       button6.setActionCommand("Professor Plum");
        bg.add(button1);
        bg.add(button2);
        bg.add(button3);
        bg.add(button4);
        bg.add(button5);
        bg.add(button6);
        // Radio buttons here. Grid Layout
        JRadioButton weaponButt1 = new JRadioButton("Candlestick", true);
       weaponButt1.setActionCommand("Candlestick");
        JRadioButton weaponButt2 = new JRadioButton("Dagger");
       weaponButt2.setActionCommand("Dagger");
        JRadioButton weaponButt3 = new JRadioButton("LeadPipe");
       weaponButt3.setActionCommand("LeadPipe");
        JRadioButton weaponButt4 = new JRadioButton("Revolver");
       weaponButt4.setActionCommand("Revolver");
        JRadioButton weaponButt5 = new JRadioButton("Rope");
       weaponButt5.setActionCommand("Rope");
        JRadioButton weaponButt6 = new JRadioButton("Spanner");
       weaponButt6.setActionCommand("Spanner");
        bg2.add(weaponButt1);
        bg2.add(weaponButt2);
        bg2.add(weaponButt3);
        bg2.add(weaponButt4);
        bg2.add(weaponButt5);
        bg2.add(weaponButt6);
        JRadioButton roomButt1 = new JRadioButton("Ballroom", true);
        roomButt1.setActionCommand("Ballroom");
        JRadioButton roomButt2 = new JRadioButton("Billiard Room");
       roomButt2.setActionCommand("Billiard Room");
        JRadioButton roomButt3 = new JRadioButton("Conservatory");
       roomButt3.setActionCommand("Conservatory");
        JRadioButton roomButt4 = new JRadioButton("Dining Room");
       roomButt4.setActionCommand("Dining Room");
        JRadioButton roomButt5 = new JRadioButton("Hall");
       roomButt5.setActionCommand("Hall");
        JRadioButton roomButt6 = new JRadioButton("Kitchen");
       roomButt6.setActionCommand("Kitchen");
        JRadioButton roomButt7 = new JRadioButton("Library");
       roomButt7.setActionCommand("Library");
        JRadioButton roomButt8 = new JRadioButton("Lounge");
       roomButt8.setActionCommand("Lounge");
        JRadioButton roomButt9 = new JRadioButton("Study");
       roomButt9.setActionCommand("Study");
        bg3.add(roomButt1);
        bg3.add(roomButt2);
        bg3.add(roomButt3);
        bg3.add(roomButt4);
        bg3.add(roomButt5);
        bg3.add(roomButt6);
        bg3.add(roomButt7);
        bg3.add(roomButt8);
        bg3.add(roomButt9);

        d.add(label1);
        d.add(button1);
        d.add(button2);
        d.add(button3);
        d.add(button4);
        d.add(button5);
        d.add(button6);
        d.add(label2);
        d.add(weaponButt1);
        d.add(weaponButt2);
        d.add(weaponButt3);
        d.add(weaponButt4);
        d.add(weaponButt5);
        d.add(weaponButt6);
        if(!suggest){
            d.add(label3);
            d.add(roomButt1);
            d.add(roomButt2);
            d.add(roomButt3);
            d.add(roomButt4);
            d.add(roomButt5);
            d.add(roomButt6);
            d.add(roomButt7);
            d.add(roomButt8);
            d.add(roomButt9);
        }
        JButton next = new JButton("Next");
        d.add(next);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(bg.getSelection().getActionCommand());
                String character = bg.getSelection().getActionCommand();
                String weapon = bg2.getSelection().toString();
                murder.add(character);
                murder.add(weapon);        
                if(!suggest){
                    String room = bg3.getSelection().toString();
                    murder.add(room);
                }
                buttonPressed = true;
                d.dispose();            
            }
        });

        d.setSize(100, 600);
        d.setVisible(true);
        System.out.println(murder.get(0));
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
        JDialog d = new JDialog(this, true);
        d.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel("Your accusation was wrong. You have lost!");
        JButton ok = new JButton("OK");

        d.add(label);
        d.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        d.setSize(100, 100);
        d.setVisible(true);
    }

    public void correctAccusation() {
        JDialog d = new JDialog(this, true);
        d.setLayout(new GridLayout(2, 1));
        JLabel label = new JLabel("Your accusation was right. You have WON!");
        JButton ok = new JButton("OK");

        d.add(label);
        d.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        d.setSize(100, 100);
        d.setVisible(true);
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
        bottomPanel.setVisible(true);
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

    public ImageIcon getDice(int num){
        switch (num){
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
        JDialog d = new JDialog(this, true);
        d.setLayout(new GridLayout(3, 1));
        JLabel label = new JLabel("Do you want to make an Accusation?");
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        d.add(label);
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

        d.setSize(100, 100);
        d.setVisible(true);


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
        JDialog d = new JDialog(this, true);
        d.setLayout(new GridLayout(4, 1));
        JLabel label = new JLabel("You are in a room!!!");
        JLabel question = new JLabel("Do you want to use the rest of your moves? Y or N");
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");

        d.add(label);
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

        d.setSize(100, 100);
        d.setVisible(true);


        if (answer.get(0).equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public void displayCard(Player withCard, Card toShow) {
        JDialog d = new JDialog(this, true);
        d.setLayout(new GridLayout(2, 1));
        JLabel title = new JLabel("Player " + withCard.getSymbol() + " showed you: ");
        JLabel icon = new JLabel();
        icon.setIcon(getCard(toShow.getName()));

        d.add(title);
        d.add(icon);
        d.setSize(200, 500);
        d.setVisible(true);
    }

    /**
     * Asks for input of which card to show and then display that card
     *
     * @param choosing
     * @param chooseFrom
     */
    public void chooseCard(Player choosing, ArrayList<Card> chooseFrom) {
        JDialog d = new JDialog(this, true);

        JLabel title = new JLabel("Player " + choosing.getSymbol() + ", choose a card to show:");
        d.add(title);
        ButtonGroup bg = new ButtonGroup();

        for(Card c: chooseFrom){
            JRadioButton button = new JRadioButton(c.getName(), true);
            button.setActionCommand(c.getName());
            bg.add(button);
            d.add(button);
        }

        JButton next = new JButton("next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayCard(choosing, new Card(bg.getSelection().getActionCommand()));
                d.dispose();
            }
        });

        d.setSize(100, 100);
        d.setVisible(true);
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
        JDialog d = new JDialog();
        JLabel label = new JLabel("No one has those cards");
        JButton ok = new JButton("OK");

        d.add(label);
        d.add(ok);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        d.setSize(100, 100);
        d.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("clicked");
/*        Cell c = board.getCellFromPixel(e.getX(), e.getY());
        movedCell = c;
        mousePressed = true;*/
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Cell c = board.getCellFromPixel(e.getX(), e.getY());
        movedCell = c;
        mousePressed.set(true);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
