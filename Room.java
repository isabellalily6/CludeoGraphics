// Class for the 9 room cards

public class Room extends Card {
    private String name;

    /*
    * Constructor for the room card
    * 
    * @param roomName
    */
    public Room(String roomName) {
        super(roomName);
        name = roomName;
    }

    /*
    * Gets the name of the room
    * 
    * @return String
    */
    public String getName() {
        return name;
    }

}
