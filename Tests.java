import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Tests {
    @Test
    public void test_01() {
        CluedoGame cg = new CluedoGame();
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
        moves.add("U");
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

}
