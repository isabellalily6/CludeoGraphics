import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Tests {
    @Test public void test_01() {
        CluedoGame cg = new CluedoGame();
        String expected =
                "XXXXXXXXX3XXXX4XXXXXXXXX" +
                "XKKKKX____XXXX____XCCCCX" +
                "XKKKKX__XXXBBXXX__XCCCCX" +
                "XKKKKX__XBDBBBBX__XCCCCX" +
                "XKKKKX__XBBBBBBX__CCCCCX" +
                "XRKKKX__BBBBBBBB___XXXXX" +
                "XXXXKX__XBBBBBBX_______5" +
                "X_______XBXXXXBX_______X" +
                "X_________________XXXXXX" +
                "XXXXX_____________IIIIIX" +
                "XDDDXXXX__XXXXX___XIIIIX" +
                "XDDDDDDX__XXXXX___XIIIIX" +
                "XDDDDDDD__XXXXX___XXXXIX" +
                "XDDDDDDX__XXXXX________X" +
                "XSDDDDDX__XXXXX___XXLXXX" +
                "XXXXXXDX__XXXXX__XXLLLLX" +
                "X_________XXXXX__LLLLLLX" +
                "2________________XXLLLLX" +
                "X________XXHHXX___XXXXXX" +
                "XXXXXOX__XHHHHX________6" +
                "XOOOOOX__XHHHHH________X" +
                "XOOOOOX__XHHHHX__XSXXXXX" +
                "XOOOOOX__XHHHHX__XSSSSSX" +
                "XOOOOCX__XHHH&X__XSSSSLX" +
                "XXXXXXX1XXXXXXXXXXXXXXXX";
        assertEquals(expected, cg.getActiveBoard().toString());
    }
    @Test public void test_02() {
        CluedoGame cg = new CluedoGame();
        String expected =
                "XXXXXXXXX3XXXX4XXXXXXXXX" +
                        "XKKKKX____XXXX____XCCCCX" +
                        "XKKKKX__XXXBBXXX__XCCCCX" +
                        "XKKKKX__XBDBBBBX__XCCCCX" +
                        "XKKKKX__XBBBBBBX__CCCCCX" +
                        "XRKKKX__BBBBBBBB___XXXXX" +
                        "XXXXKX__XBBBBBBX_______5" +
                        "X_______XBXXXXBX_______X" +
                        "X_________________XXXXXX" +
                        "XXXXX_____________IIIIIX" +
                        "XDDDXXXX__XXXXX___XIIIIX" +
                        "XDDDDDDX__XXXXX___XIIIIX" +
                        "XDDDDDDD__XXXXX___XXXXIX" +
                        "XDDDDDDX__XXXXX________X" +
                        "XSDDDDDX__XXXXX___XXLXXX" +
                        "XXXXXXDX__XXXXX__XXLLLLX" +
                        "X_________XXXXX__LLLLLLX" +
                        "2________________XXLLLLX" +
                        "X________XXHHXX___XXXXXX" +
                        "XXXXXOX__XHHHHX________6" +
                        "XOOOOOX__XHHHHH________X" +
                        "XOOOOOX__XHHHHX__XSXXXXX" +
                        "XOOOOOX__XHHHHX__XSSSSSX" +
                        "XOOOOCX__XHHH&X__XSSSSLX" +
                        "XXXXXXX1XXXXXXXXXXXXXXXX";
        assertEquals(expected, cg.getActiveBoard().toString());
    }
}
