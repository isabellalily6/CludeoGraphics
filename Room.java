// Class for the 9 room cards

public class Room extends Card {
    private String name;

    public Room(String roomName) {
        super(roomName);
        name = roomName;
    }

    public String getName() {
        return name;
    }

}