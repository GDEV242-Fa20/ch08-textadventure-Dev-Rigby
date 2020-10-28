import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previous;
    private ArrayList<Room> path;
    private Player player;
    /**
     * Create the game and initialise its internal map.
     * Also creates an instance of player that can carry 5 items
     * Also initialises the path arraylist that tracks the taken path
     */
    public Game() 
    {
        path = new ArrayList<Room>();
        createRooms();
        parser = new Parser();
        player = new Player(5);
    }

    /**
     * Create all the rooms and link their exits together.
     * this is a sketch of the map
     *      ___     ___
     * ____|___|___|___|
     * |___|___|___|___|
     * |___|___|___|___|
     * |___|___|___|___|
     * |___|___|
     * 
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, security, janitorsCloset,
            storageOne, storageTwo, storageThree, gym, cafeteria, library, 
            hallwayOne, hallwayTwo, hallwayThree, hallwayFour, lockerRoom;
        Item paper = new Item(0, "A well written essay.");
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        security = new Room("in the security room");
        janitorsCloset = new Room("in the janitor's closet");
        storageOne = new Room("in the security storage room");
        storageTwo = new Room("in the library storage room");
        storageThree = new Room("in the gym storage room");
        gym = new Room("in the gym");
        cafeteria = new Room("in the cafeteria");
        library = new Room("in the library");
        hallwayOne = new Room("in a long hallway");
        hallwayTwo = new Room("in a long hallway");
        hallwayThree = new Room("in a long hallway");
        hallwayFour= new Room("in a long hallway");
        lockerRoom = new Room("in the locker room");
                
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("south", hallwayOne);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("west", hallwayOne);

        office.setExit("west", lab);
        
        security.setExit("west", hallwayFour);
        security.setExit("north", storageOne);
        
        janitorsCloset.setExit("north", hallwayFour);
        
        storageOne.setExit("south", security);
        
        storageTwo.setExit("east", library);
        
        storageThree.setExit("south", gym);
        
        gym.setExit("north", storageThree);
        gym.setExit("east", hallwayTwo);
        gym.setExit("south", lockerRoom);
        
        cafeteria.setExit("west", hallwayTwo);
        
        library.setExit("north", hallwayThree);
        library.setExit("west", storageTwo);
        
        hallwayOne.setExit("north", pub);
        hallwayOne.setExit("east", lab);
        hallwayOne.setExit("south", hallwayTwo);
        
        hallwayTwo.setExit("north", hallwayOne);
        hallwayTwo.setExit("west", gym);
        hallwayTwo.setExit("south", hallwayThree);
        hallwayTwo.setExit("east", cafeteria);
        
        hallwayThree.setExit("north", hallwayTwo);
        hallwayThree.setExit("east", hallwayFour);
        hallwayThree.setExit("south", library);
        
        hallwayFour.setExit("west", hallwayThree);
        hallwayFour.setExit("south", janitorsCloset);
        hallwayFour.setExit("east", security);
        
        lockerRoom.setExit("north", gym);
        
        //add item to library
        library.addItem(paper);
        
        //tracking for the previous room and the path taken
        previous = outside;
        path.add(outside);
        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                look();
                break;
                
            case EAT:
                eat();
                break;
                
            case BACK:
                goBack(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previous = currentRoom;
            currentRoom = nextRoom;
            path.add(currentRoom);
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    /**
     * Look around the room and get the long description
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * eat food
     */
    private void eat()
    {
        System.out.println("You have eaten now and you are not hungary any more.");
    }
    /**
     * go back rooms either one or multiple
     */
    private void goBack(Command command)
    {
        if(command.hasSecondWord()) {
            int rooms = Integer.parseInt(command.getSecondWord());
            if(rooms < path.size())
            {
                currentRoom = path.get(path.size()-1-rooms);
                for(int i = 0; i <= rooms; i++)
                {
                    path.add(path.get(path.size()-i));
                }
                path.add(currentRoom);
            }
            else
                System.out.println("You haven't traveled that far.");
        }
        else
        {
            Room temp = currentRoom;
            path.add(previous);
            currentRoom = previous;
            previous = temp;
        }
    }
    public static void main(String[] args)
    {
        Game game = new Game();
        game.play();
    }
}
