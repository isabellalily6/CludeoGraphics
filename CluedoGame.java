// This class oversees the entire game. It contains the main methods in the game like run() and playersTurn().

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CluedoGame {
    // initialise the variables
    private int numPlayers;
    private Set<Card> cards = new HashSet<>();
    private Suggestion murder; //solution
    static public java.util.ArrayList<Player> players = new ArrayList<>();
    private java.util.ArrayList<Player> currentPlayers = new ArrayList<>();
    private Map<String, String> roomWeapons = new HashMap<>();
    private Board board;
    private Map<java.lang.Character, String> roomNames = new HashMap<>();
    static public Map<java.lang.Character, String> weaponNames = new HashMap<>();
    private Map<java.lang.Character, Cell> leftRoomLocations = new HashMap<>();
    private GUI ui;
    private Boolean gameOver = false;
    private int diceNum = 0;

     /**
     * Make an instance of Cluedo game and start the game
     *
     *
     */
    public CluedoGame() {
        makePlayers();
        board = new Board(players);
        this.ui = new GUI(board, players);
        getPlayers();
    }
    
        
    /**
     * Make an instance of Cluedo game with the number of players passed as an integer
     *
     *@param num
     */
    public CluedoGame(int num) {
        if(num< 3 || num > 6) {

        }else {
            makePlayers();
            board = new Board(players);
            for (int i = 0; i < num; i++) {
                currentPlayers.add(players.get(i));
            }
        }
    }
    
    /**
     * Uses methods below to create everything needed to play the game (Cards, players etc)
     * then starts running/playing the game. Will continuously loop through the while loop
     * until game finishes.
     */
   public void run() {
        setUp();

        // draw the board
        board.repaint();
        Player winningPlayer = null;
        while (!gameOver) {  // loops through stages 2-4 from section 2.6 'User Interface' from handout
            for (Player player : currentPlayers) {
                if (!player.getHasLost()) {
                    ui.nextPlayer(player);
                    ui.showHand(player.getHand());
                    playersTurn(player);
                }
                if (gameOver) {
                    winningPlayer = player;
                    break;
                }
            }
        }
        if (checkGameOver() == null) {
            gameOver(winningPlayer);
        } else {
            gameOver(checkGameOver());
        }
    } 
    
    public void setUp(){
        // set up the game
        createCards();
        createRooms();
        createWeapons();
        addWeaponsToRooms();
        decideMurder();
        dealCards();
    }

    public void getPlayers(){
        numPlayers = ui.getNumPlayers();
        // error checking range
        while (numPlayers < 3 || numPlayers > 6) {
            numPlayers = ui.getNumPlayers();
        }

        ArrayList list = ui.setCharacters(numPlayers);

        for(int i = 0; i < numPlayers*2; i+=2){
            for(Player p: players){
                if(p.getCharacterCard().getName().equals(list.get(i+1).toString())){
                    currentPlayers.add(p);
                    p.setPlayerNum(i/2+1);
                    p.setName(list.get(i).toString());
                }
            }
        }
        ui.displayPlayers(currentPlayers);
    }

    /**
     * Runs through a players turn including; dice roll, moves, suggestion etc
     * Will be used every time it's a new players turn
     *
     * @param player
     */
    public void playersTurn(Player player) {
        boolean asked = false;
        Set<Cell> spacesUsed = new HashSet<Cell>();
        int diceNum = rollDice();
        while (diceNum > 0) {     //continue until no more moves left
            ui.updateInfo(player, diceNum);
            spacesUsed.add(board.getCell(player.getxPos(), player.getyPos()));
            if (roomNames.containsKey(board.getCell(player.getxPos(), player.getyPos()).getSymbol()) && !asked) {
                // player is in a room
                asked = true;
                if (!ui.continueMove()) {
                    //player doesn't want to use the rest of their move.
                    diceNum = 0;
                }
            }
            if (diceNum > 0) {
                //ui.displayMovesLeft(diceNum);
                Boolean validMove = player.move(ui.getMoves(player), board.getPlayerBoard(players), spacesUsed);
                board.updateBoard(players);
                // error checking
                while (!validMove) {
                    validMove = player.move(ui.getMoves(player), board.getPlayerBoard(players), spacesUsed);
                }
                ui.repaint();
                diceNum -= 1;
            }
        }
        ui.updateInfo(player, diceNum);
        // if they are inside when they have no more moves they can make a suggestion
        if (roomNames.containsKey(board.getCell(player.getxPos(), player.getyPos()).getSymbol())) {
            String userInput = ui.checkSuggestion();
            // error checking
            while (!validSuggestion(userInput)) {
                userInput = ui.checkSuggestion();
            }
            if (userInput.equalsIgnoreCase("S")) {
                suggestion(player, roomNames.get(board.getCell(player.getxPos(), player.getyPos()).getSymbol()));
                // ask if want to make accusation or not
                if (ui.shouldMakeAccusation()) {
                    userInput = "A";
                }
            }
            if (userInput.equalsIgnoreCase("A")) {
                accusation(player);
            }
        }
        ui.repaint();
    }

    /**
     * Check if the player entered a valid input when asked about making a suggestion or accusation
     *
     * @return true if valid, otherwise false
     */
    public boolean validSuggestion(String input) {
        if (input.equalsIgnoreCase("S") || input.equalsIgnoreCase("A") ||
                input.equalsIgnoreCase("N")) {
            return true;
        }
        return false;
    }

    /**
     * Display that the game is over and who won the game
     *
     * @param winningPlayer
     */
    public void gameOver(Player winningPlayer) {
        if (winningPlayer != null) {
            ui.displayWinningPlayer(winningPlayer.getSymbol());
        } else {
            ui.displayWinningPlayer(' ');
        }
    }

    public void makeLeftRooms(){
        for(java.lang.Character s : roomNames.keySet()){
            // add the co-ordes of left corner of room
        }
    }
    /**
     * Create the character, weapon and room cards
     */
    public void createCards() {
        //create all the cards and add to list
        // Character cards
        cards.add(new Character("Mrs. White"));
        cards.add(new Character("Mr. Green"));
        cards.add(new Character("Mrs. Peacock"));
        cards.add(new Character("Professor Plum"));
        cards.add(new Character("Miss Scarlett"));
        cards.add(new Character("Colonel Mustard"));

        // Weapon Cards
        cards.add(new Weapon("Candlestick"));
        cards.add(new Weapon("Dagger"));
        cards.add(new Weapon("Lead Pipe"));
        cards.add(new Weapon("Revolver"));
        cards.add(new Weapon("Rope"));
        cards.add(new Weapon("Spanner"));

        // Room Cards
        cards.add(new Room("Kitchen"));
        cards.add(new Room("Ball Room"));
        cards.add(new Room("Conservatory"));
        cards.add(new Room("Billiard Room"));
        cards.add(new Room("Library"));
        cards.add(new Room("Study"));
        cards.add(new Room("Hall"));
        cards.add(new Room("Lounge"));
        cards.add(new Room("Dining Room"));
    }

    /**
     * Create weapons for displaying on the board, the key will be used to display
     */
    public void createWeapons() {
        weaponNames.put('R', "Revolver");
        weaponNames.put('S', "Spanner");
        weaponNames.put('C', "Candlestick");
        weaponNames.put('D', "Dagger");
        weaponNames.put('L', "Lead Pipe");
        weaponNames.put('&', "Rope");

    }

    /**
     * Add a weapon into a room, there can be no two weapon in the same room
     */
    public void addWeaponsToRooms() {
        ArrayList<Weapon> weapons = new ArrayList<>();
        // Find weapons
        for (Card card : cards) {
            if (card instanceof Weapon) {
                weapons.add((Weapon) card);
            }
        }
        // Put weapon into a empty room
        for (Card card : cards) {
            if (card instanceof Room) {
                if (!weapons.isEmpty()) {
                    Weapon weapon = weapons.get(0);
                    roomWeapons.put(((Room) card).getName(), weapon.getName());
                    weapons.remove(0);
                }
            }
        }
    }

    /**
     * Create rooms for displaying on the board, the key will be used to display
     */
    public void createRooms() {
        roomNames.put('D', "Dining Room");
        roomNames.put('K', "Kitchen");
        roomNames.put('B', "Ball Room");
        roomNames.put('C', "Conservatory");
        roomNames.put('I', "Billiard Room");
        roomNames.put('L', "Library");
        roomNames.put('S', "Study");
        roomNames.put('H', "Hall");
        roomNames.put('O', "Lounge");
    }

    /**
     * Create player for displaying on the board, the symbol will be used to display
     * each player is a character
     */
    public void makePlayers() {
        players.add(new Player(new Character("Miss Scarlett"), 7, 24, '1'));
        players.add(new Player(new Character("Colonel Mustard"), 0, 17, '2'));
        players.add(new Player(new Character("Mrs. White"), 9, 0, '3'));
        players.add(new Player(new Character("Mr. Green"), 14, 0, '4'));
        players.add(new Player(new Character("Mrs. Peacock"), 23, 6, '5'));
        players.add(new Player(new Character("Professor Plum"), 23, 19, '6'));
    }

    /**
     * Creates a Suggestion object that contains the answer to the game created
     * once per game
     */
    public void decideMurder() {
        // shuffle cards to find random answer/murder
        ArrayList<Card> shuffledCards = new ArrayList<>();
        for (Card c : cards) {
            shuffledCards.add(c);
        }
        Collections.shuffle(shuffledCards);

        // randomly get a weapon card
        int randInt = (int) (Math.random() * shuffledCards.size());
        while (!(shuffledCards.get(randInt) instanceof Weapon)) {
            randInt = (int) (Math.random() * shuffledCards.size());
        }
        Weapon weapon = (Weapon) shuffledCards.get(randInt);
        cards.remove(weapon);

        // randomly get a character card
        randInt = (int) (Math.random() * shuffledCards.size());
        while (!(shuffledCards.get(randInt) instanceof Character)) {
            randInt = (int) (Math.random() * shuffledCards.size());
        }
        Character character = (Character) shuffledCards.get(randInt);
        cards.remove(character);

        // randomly get a room card
        randInt = (int) (Math.random() * shuffledCards.size());
        while (!(shuffledCards.get(randInt) instanceof Room)) {
            randInt = (int) (Math.random() * shuffledCards.size());
        }
        Room room = (Room) shuffledCards.get(randInt);
        cards.remove(room);

        Iterator<Card> index = cards.iterator();
        Card chosenCard = index.next();

        // searches for a weapon card and removes it from the stack of cards
        while (!(chosenCard instanceof Weapon)) {
            chosenCard = index.next();
        }

        index = cards.iterator();
        // searches for a room card and removes it from the stack of cards
        while (!(chosenCard instanceof Room)) {
            chosenCard = index.next();
        }

        index = cards.iterator();
        // searches for a character card and removes it from the stack of cards
        while (!(chosenCard instanceof Character)) {
            chosenCard = index.next();
        }

        murder = new Suggestion(room, character, weapon);
    }

    /**
     * Find two random integers between 1 and 6. Then add together to create number of moves
     *
     * @return
     */
    public int rollDice() {
        int one = (int) ((Math.random() * 5 + 1));
        int two = (int) ((Math.random() * 5 + 1));
        ui.setDice(one, two);
        return one + two;
    }

    /**
     * Evenly hands out the rest of the cards into the players hands. If odd amount
     * some players will have one more card.
     */
    public void dealCards() {
        Iterator<Card> index = cards.iterator();
        while (index.hasNext()) {
            for (Player player : currentPlayers) {
                if (index.hasNext()) {
                    Card card = index.next();
                    player.addToHand(card);
                }
            }
        }
        cards.clear();
    }

    /**
     * If weapon from a different room is used in a suggestion then move it to the room that
     * the suggestion was made in, and if that room already has a weapon in it then swap rooms
     * with the weapon
     *
     * @param room
     * @param weapon
     */
    public void moveWeapon(String room, String weapon) {
        //String oldRoom = null; // name of room weapon needed is in currently
        java.lang.Character oldCharRoom = ' '; // name of room weapon needed is in currently
        java.lang.Character newRoom = ' ';
        for (Map.Entry<java.lang.Character, String> entry : weaponNames.entrySet()) {
            if (entry.getValue().equals(weapon)) {
                oldCharRoom = entry.getKey();
            }
        }
        for(Cell c: board.getBoardWeapons()){
            if(c.getSymbol()==oldCharRoom){
                oldCharRoom=c.getRoomSymbol();
            }
        }
        for (Map.Entry<java.lang.Character, String> entry : roomNames.entrySet()) {
            if (entry.getValue().equals(room)) {
                newRoom = entry.getKey();
            }
        }

        Cell oldCell = null;
        Cell newCell = null;
        for(Cell c: board.getBoardWeapons()){
            if(c.getRoomSymbol()==oldCharRoom){
                oldCell = new Cell(c.getxCoord(), c.getyCoord(), c.getSymbol(), c.getRoomSymbol());
            }
            if(c.getRoomSymbol()==newRoom){
                newCell = new Cell(c.getxCoord(), c.getyCoord(), c.getSymbol(), c.getRoomSymbol());
            }
        }
        ArrayList<Cell> list = new ArrayList<>();
        for(Cell c: Board.boardWeapons){
            if(c.getxCoord()==oldCell.getxCoord()&&c.getyCoord()==oldCell.getyCoord()){
                c.changeCell(newCell);
            }
            if(c.getxCoord()==newCell.getxCoord()&&c.getyCoord()==newCell.getyCoord()){
                c.changeCell(oldCell);
            }
            list.add(c);
        }
        ui.repaint();
    }

    /**
     * Move a player to the first available top left cell of a specified room
     *
     * @param room
     * @param player
     */
    public void movePlayer(String room, String player) {
        char letter = ' ';
        // Get room symbol
        for (Map.Entry<java.lang.Character, String> entry : roomNames.entrySet()) {
            if (entry.getValue().equals(room)) {
                letter = entry.getKey();
                break;
            }
        }
        List<Integer> playerCoords = board.getRoom(letter, players);
        for (Player p : players) {
            if (p.getCharacterCard().getName().equals(player)) {
                if (board.getCell(p.getxPos(), p.getyPos()).getSymbol() != letter) {
                    p.setCoords(playerCoords.get(0), playerCoords.get(1));
                }
            }
        }
    }

    /**
     * This class gets the players suggestion an controls the players suggestion.
     *
     * @param player
     * @param room
     */
    public void suggestion(Player player, String room) {
        ArrayList<String> guess = ui.makeSuggesstion(room);

        // move the weapon and player into the room
        moveWeapon(room, guess.get(1));
        movePlayer(room, guess.get(0));

        // draw the new board
        ui.repaint();

        guess.add(room);
        boolean shownCard = false;
        int num = 0;
        
        if ((player.getPlayerNum()) == numPlayers) { // if last player go back to the beginning
            num = 0;
        } else {
            num = player.getPlayerNum();
        }
        // loop clockwise around other players and check their hands for any cards in suggestion
        for (int i = num; i != (int) (player.getPlayerNum() - 1); i++) {
            if (i == numPlayers) {
                i = 0;
            }
            Player p = currentPlayers.get(i);
            ArrayList<Card> matches = p.checkHand(guess);

            if (matches.size() == 1) {
                ui.displayCard(p, matches.get(0));
                shownCard = true;
                break;
            } else if (matches.size() > 1) { // make the user choose which card to show to the current player
                ui.chooseCard(p, matches);
                shownCard = true;
                break;
            }
        }
        if (!shownCard) {
            ui.noCardShown();
        }
    }

    /**
     * This class get the players accusation controls the players accusation.
     *
     * @param player
     */
    public void accusation(Player player) {
        Suggestion sug = ui.makeAccusation();
        // check whether the accusation matches the murder circumstances
        if (!sug.equals(murder)) {
            ui.wrongAccusation();
            player.setHasLost();
            // check if the game is over
            if (checkGameOver() != null) {
                gameOver = true;
            }
        } else {
            ui.correctAccusation();
            // set all the other players to have lost
            for (Player p : currentPlayers) {
                if (!p.equals(player)) {
                    p.setHasLost();
                }
            }
            gameOver = true;
        }
    }

    /**
     * Returns the player which has won the game if the game is over
     *
     * @return either null is the game isn't over yet, or the winning player
     */
    public Player checkGameOver() {
        int count = 0;
        Player winningPlayer = null;
        for (Player player : currentPlayers) {
            if (player.getHasLost()) {
                count += 1;
            } else {
                winningPlayer = player;
            }
        }
        if (count == numPlayers - 1) {
            return winningPlayer;
        }
        return null;
    }
    
    /**
     * Get current board
     * 
     * @return the board
     */
    public Board getActiveBoard(){
        if(board == null){
            return new Board(players);
        }
        return board;
    }

        /**
     * Move players based an array of moves and the dice numbers rolled to test movement
     * 
     * @param moves
     * @param num
     */
    public void playerMove(ArrayList<String> moves, ArrayList<Integer> num) {
        // initialise the variables
        int count = 0; // counts the move
        int dice = 0; // counts the dice
        
        // if there is no board there are no moves to make
        if(board == null){
            count = moves.size();
        }
        
        // while there are still moves
        while(count != moves.size()){
            for(Player player: currentPlayers){
                // get the number of moves this player will move
                int number = num.get(dice);
                if(!player.getHasLost()){
                    // while they still have moves left move the player
                    while(number>0) {
                        Set<Cell> set = new HashSet<>();
                        set.add(board.getCell(player.getxPos(), player.getyPos()));
                        Boolean valid = player.move(moves.get(count), board.getPlayerBoard(players), set);
                        // if the move is valid, count is as a move and decrease the dice number
                        if(valid){
                            number-=1;
                        }
                        count += 1;
                    }
                }
                dice += 1;
            }
        }
    }

    /**
     * Creates a new instance of the game Cluedo and runs the game
     *
     * @param args
     */
    public static void main(String[] args) {
        CluedoGame game = new CluedoGame();
        game.run();
    }
}
