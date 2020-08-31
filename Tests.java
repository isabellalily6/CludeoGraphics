import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class Tests {
    @Test
    public void test_01() {
        CluedoGame cg = new CluedoGame(3);
        String expected =
                "XXXXXXXXX3XXXX4XXXXXXXXX\n" +
                        "XKKKKX____XXXX____XCCCCX\n" +
                        "XKKKKX__XXXBBXXX__XCCCCX\n" +
                        "XKKKKX__XBDBBBBX__XCCCCX\n" +
                        "XKKKKX__XBBBBBBX__CCCCCX\n" +
                        "XRKKKX__BBBBBBBB___XXXXX\n" +
                        "XXXXKX__XBBBBBBX_______5\n" +
                        "X_______XBXXXXBX_______X\n" +
                        "X_________________XXXXXX\n" +
                        "XXXXX_____________IIIIIX\n" +
                        "XDDDXXXX__XXXXX___XIIIIX\n" +
                        "XDDDDDDX__XXXXX___XIIIIX\n" +
                        "XDDDDDDD__XXXXX___XXXXIX\n" +
                        "XDDDDDDX__XXXXX________X\n" +
                        "XSDDDDDX__XXXXX___XXLXXX\n" +
                        "XXXXXXDX__XXXXX__XXLLLLX\n" +
                        "X_________XXXXX__LLLLLLX\n" +
                        "2________________XXLLLLX\n" +
                        "X________XXHHXX___XXXXXX\n" +
                        "XXXXXOX__XHHHHX________6\n" +
                        "XOOOOOX__XHHHHH________X\n" +
                        "XOOOOOX__XHHHHX__XSXXXXX\n" +
                        "XOOOOOX__XHHHHX__XSSSSSX\n" +
                        "XOOOOCX__XHHH&X__XSSSSLX\n" +
                        "XXXXXXX1XXXXXXXXXXXXXXXX\n";

        assertEquals(expected, cg.getActiveBoard().toString());
    }
    @Test public void test_02() {
        // Create a new game with the number of players passed as a parameter
        CluedoGame cg = new CluedoGame(3);

        // Create an array list for the players to move
        ArrayList<String> moves = new ArrayList<>();
        moves.add("U");
        moves.add("R");
        moves.add("D");

        // Create an array list for the number each dice roll will be
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(1);
        dice.add(1);
        dice.add(1);

        // move the players
        cg.playerMove(moves, dice);

        String expected =
                "XXXXXXXXXXXXXX4XXXXXXXXX\n" +
                "XKKKKX___3XXXX____XCCCCX\n" +
                "XKKKKX__XXXBBXXX__XCCCCX\n" +
                "XKKKKX__XBDBBBBX__XCCCCX\n" +
                "XKKKKX__XBBBBBBX__CCCCCX\n" +
                "XRKKKX__BBBBBBBB___XXXXX\n" +
                "XXXXKX__XBBBBBBX_______5\n" +
                "X_______XBXXXXBX_______X\n" +
                "X_________________XXXXXX\n" +
                "XXXXX_____________IIIIIX\n" +
                "XDDDXXXX__XXXXX___XIIIIX\n" +
                "XDDDDDDX__XXXXX___XIIIIX\n" +
                "XDDDDDDD__XXXXX___XXXXIX\n" +
                "XDDDDDDX__XXXXX________X\n" +
                "XSDDDDDX__XXXXX___XXLXXX\n" +
                "XXXXXXDX__XXXXX__XXLLLLX\n" +
                "X_________XXXXX__LLLLLLX\n" +
                "X2_______________XXLLLLX\n" +
                "X________XXHHXX___XXXXXX\n" +
                "XXXXXOX__XHHHHX________6\n" +
                "XOOOOOX__XHHHHH________X\n" +
                "XOOOOOX__XHHHHX__XSXXXXX\n" +
                "XOOOOOX__XHHHHX__XSSSSSX\n" +
                "XOOOOCX1_XHHH&X__XSSSSLX\n" +
                "XXXXXXXXXXXXXXXXXXXXXXXX\n";

        assertEquals(expected, cg.getActiveBoard().toString());
    }

    @Test public void test_03() {
        // Create a new game with the number of players passed as a parameter
        CluedoGame cg = new CluedoGame(3);

        // Create an array list for the players to move
        ArrayList<String> moves = new ArrayList<>();
        // player 1 moves
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("L");
        //player 2 moves
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        // player 3 moves
        moves.add("D");
        moves.add("L");
        moves.add("L");
        moves.add("D");

        // Create an array list for the number each dice roll will be
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(7);
        dice.add(5);
        dice.add(4);

        // move the players
        cg.playerMove(moves, dice);

        String expected =
                "XXXXXXXXXXXXXX4XXXXXXXXX\n" +
                        "XKKKKX____XXXX____XCCCCX\n" +
                        "XKKKKX_3XXXBBXXX__XCCCCX\n" +
                        "XKKKKX__XBDBBBBX__XCCCCX\n" +
                        "XKKKKX__XBBBBBBX__CCCCCX\n" +
                        "XRKKKX__BBBBBBBB___XXXXX\n" +
                        "XXXXKX__XBBBBBBX_______5\n" +
                        "X_______XBXXXXBX_______X\n" +
                        "X_________________XXXXXX\n" +
                        "XXXXX_____________IIIIIX\n" +
                        "XDDDXXXX__XXXXX___XIIIIX\n" +
                        "XDDDDDDX__XXXXX___XIIIIX\n" +
                        "XDDDDDDD__XXXXX___XXXXIX\n" +
                        "XDDDDDDX__XXXXX________X\n" +
                        "XSDDDDDX__XXXXX___XXLXXX\n" +
                        "XXXXXXDX__XXXXX__XXLLLLX\n" +
                        "X_________XXXXX__LLLLLLX\n" +
                        "X____2___________XXLLLLX\n" +
                        "X_____1__XXHHXX___XXXXXX\n" +
                        "XXXXXOX__XHHHHX________6\n" +
                        "XOOOOOX__XHHHHH________X\n" +
                        "XOOOOOX__XHHHHX__XSXXXXX\n" +
                        "XOOOOOX__XHHHHX__XSSSSSX\n" +
                        "XOOOOCX__XHHH&X__XSSSSLX\n" +
                "XXXXXXXXXXXXXXXXXXXXXXXX\n";

        assertEquals(expected, cg.getActiveBoard().toString());
    }

    @Test public void test_04() {
        // Create a new game with the number of players passed as a parameter
        CluedoGame cg = new CluedoGame(3);

        // Create an array list for the players to move
        ArrayList<String> moves = new ArrayList<>();
        // player 1 moves
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("L");
        moves.add("L");
        moves.add("D");
        //player 2 moves
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        // player 3 moves
        moves.add("D");
        moves.add("L");
        moves.add("L");
        moves.add("D");

        // Create an array list for the number each dice roll will be
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(9);
        dice.add(5);
        dice.add(4);

        Suggestion sug = new Suggestion(new Room("Lounge"), new Character("Mr. Green"), new Weapon("Revolver"));

        // move the players
        cg.playerMove(moves, dice);

        String expected =
                "XXXXXXXXXXXXXX4XXXXXXXXX\n" +
                        "XKKKKX____XXXX____XCCCCX\n" +
                        "XKKKKX_3XXXBBXXX__XCCCCX\n" +
                        "XKKKKX__XBDBBBBX__XCCCCX\n" +
                        "XKKKKX__XBBBBBBX__CCCCCX\n" +
                        "XRKKKX__BBBBBBBB___XXXXX\n" +
                        "XXXXKX__XBBBBBBX_______5\n" +
                        "X_______XBXXXXBX_______X\n" +
                        "X_________________XXXXXX\n" +
                        "XXXXX_____________IIIIIX\n" +
                        "XDDDXXXX__XXXXX___XIIIIX\n" +
                        "XDDDDDDX__XXXXX___XIIIIX\n" +
                        "XDDDDDDD__XXXXX___XXXXIX\n" +
                        "XDDDDDDX__XXXXX________X\n" +
                        "XSDDDDDX__XXXXX___XXLXXX\n" +
                        "XXXXXXDX__XXXXX__XXLLLLX\n" +
                        "X_________XXXXX__LLLLLLX\n" +
                        "X____2___________XXLLLLX\n" +
                        "X________XXHHXX___XXXXXX\n" +
                        "XXXXX1X__XHHHHX________6\n" +
                        "XOOOOOX__XHHHHH________X\n" +
                        "XOOOOOX__XHHHHX__XSXXXXX\n" +
                        "XOOOOOX__XHHHHX__XSSSSSX\n" +
                        "XOOOOCX__XHHH&X__XSSSSLX\n" +
                        "XXXXXXXXXXXXXXXXXXXXXXXX\n";

        String sugExpected = "Suggestion{character=Mr. Green, room=Lounge, weapon=Revolver}";

        assertEquals(expected, cg.getActiveBoard().toString());
        assertEquals(sugExpected, sug.toString());
    }

    @Test public void test_05() {
        // Create a new game with the number of players passed as a parameter
        CluedoGame cg = new CluedoGame(3);

        // Create an array list for the players to move
        ArrayList<String> moves = new ArrayList<>();
        // player 1 moves
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("L");
        moves.add("L");
        moves.add("D");
        //player 2 moves
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("U");
        moves.add("R");
        moves.add("U");
        // player 3 moves
        moves.add("D");
        moves.add("L");
        moves.add("L");
        moves.add("D");

        // Create an array list for the number each dice roll will be
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(9);
        dice.add(8);
        dice.add(4);

        ArrayList<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(new Suggestion(new Room("Lounge"), new Character("Mr. Green"), new Weapon("Revolver")));
        suggestions.add(new Suggestion(new Room("Lounge"), new Character("Miss Scarlett"), new Weapon("Revolver")));

        // move the players
        cg.playerMove(moves, dice);

        String expected =
                "XXXXXXXXXXXXXX4XXXXXXXXX\n" +
                        "XKKKKX____XXXX____XCCCCX\n" +
                        "XKKKKX_3XXXBBXXX__XCCCCX\n" +
                        "XKKKKX__XBDBBBBX__XCCCCX\n" +
                        "XKKKKX__XBBBBBBX__CCCCCX\n" +
                        "XRKKKX__BBBBBBBB___XXXXX\n" +
                        "XXXXKX__XBBBBBBX_______5\n" +
                        "X_______XBXXXXBX_______X\n" +
                        "X_________________XXXXXX\n" +
                        "XXXXX_____________IIIIIX\n" +
                        "XDDDXXXX__XXXXX___XIIIIX\n" +
                        "XDDDDDDX__XXXXX___XIIIIX\n" +
                        "XDDDDDDD__XXXXX___XXXXIX\n" +
                        "XDDDDDDX__XXXXX________X\n" +
                        "XSDDDDDX__XXXXX___XXLXXX\n" +
                        "XXXXXX2X__XXXXX__XXLLLLX\n" +
                        "X_________XXXXX__LLLLLLX\n" +
                        "X________________XXLLLLX\n" +
                        "X________XXHHXX___XXXXXX\n" +
                        "XXXXX1X__XHHHHX________6\n" +
                        "XOOOOOX__XHHHHH________X\n" +
                        "XOOOOOX__XHHHHX__XSXXXXX\n" +
                        "XOOOOOX__XHHHHX__XSSSSSX\n" +
                        "XOOOOCX__XHHH&X__XSSSSLX\n" +
                        "XXXXXXXXXXXXXXXXXXXXXXXX\n";

        String sug1Expected = "Suggestion{character=Mr. Green, room=Lounge, weapon=Revolver}";
        String sug2Expected = "Suggestion{character=Miss Scarlett, room=Dining Room, weapon=Revolver}";

        assertEquals(expected, cg.getActiveBoard().toString());
        assertEquals(sug1Expected, suggestions.get(0).toString());
        assertFalse(sug2Expected, suggestions.get(1).toString());
    }

    @Test public void test_06() {
        // Create a new game with the number of players passed as a parameter
        CluedoGame cg = new CluedoGame(6);

        // Create an array list for the players to move
        ArrayList<String> moves = new ArrayList<>();
        // player 1 moves
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("U");
        moves.add("L");
        //player 2 moves
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        moves.add("R");
        // player 3 moves
        moves.add("D");
        moves.add("L");
        moves.add("L");
        moves.add("D");
        // player 4 moves
        moves.add("D");
        moves.add("R");
        // player 5 moves
        moves.add("L");
        moves.add("L");
        moves.add("L");
        moves.add("L");
        moves.add("L");
        moves.add("L");
        // player 6 moves
        moves.add("L");
        moves.add("L");
        moves.add("L");

        // Create an array list for the number each dice roll will be
        ArrayList<Integer> dice = new ArrayList<>();
        dice.add(7);
        dice.add(5);
        dice.add(4);
        dice.add(2);
        dice.add(6);
        dice.add(3);

        // move the players
        cg.playerMove(moves, dice);

        String expected =
                "XXXXXXXXXXXXXXXXXXXXXXXX\n" +
                "XKKKKX____XXXX_4__XCCCCX\n" +
                "XKKKKX_3XXXBBXXX__XCCCCX\n" +
                "XKKKKX__XBDBBBBX__XCCCCX\n" +
                "XKKKKX__XBBBBBBX__CCCCCX\n" +
                "XRKKKX__BBBBBBBB___XXXXX\n" +
                "XXXXKX__XBBBBBBX_5_____X\n" +
                "X_______XBXXXXBX_______X\n" +
                "X_________________XXXXXX\n" +
                "XXXXX_____________IIIIIX\n" +
                "XDDDXXXX__XXXXX___XIIIIX\n" +
                "XDDDDDDX__XXXXX___XIIIIX\n" +
                "XDDDDDDD__XXXXX___XXXXIX\n" +
                "XDDDDDDX__XXXXX________X\n" +
                "XSDDDDDX__XXXXX___XXLXXX\n" +
                "XXXXXXDX__XXXXX__XXLLLLX\n" +
                "X_________XXXXX__LLLLLLX\n" +
                "X____2___________XXLLLLX\n" +
                "X_____1__XXHHXX___XXXXXX\n" +
                "XXXXXOX__XHHHHX_____6__X\n" +
                "XOOOOOX__XHHHHH________X\n" +
                "XOOOOOX__XHHHHX__XSXXXXX\n" +
                "XOOOOOX__XHHHHX__XSSSSSX\n" +
                "XOOOOCX__XHHH&X__XSSSSLX\n" +
                "XXXXXXXXXXXXXXXXXXXXXXXX\n";

        assertEquals(expected, cg.getActiveBoard().toString());
    }

    private void assertFalse(String expected, String toString) {
        for(int i = 0; i < expected.length(); i++){
            if(expected.charAt(i) != toString.charAt(i)){
                return;
            }
        }
        fail("Failed to catch false assertion");
    }
}