import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 *  This class is part of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.
 * 
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that 
 *  the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (Jan 2003)
 */
public class GameEngine {
    private Parser parser;
    private UserInterface gui;
    private Player player;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine() {
        parser = new Parser();
        createRooms();
    }

    public void setGUI(UserInterface userInterface) {
        gui = userInterface;
        printWelcome();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        gui.print("\n");
        gui.println("Welcome to the Heart Attack!");
        gui.println("Heart Attack is about a young man who gets his heart broken...\n");
        gui.println("In order to recover and to feel better, he has to find all the missing pieces of his heart.");
        gui.println("Type 'help' if you need help.");
        gui.print("\n");
        gui.println(player.getCurrentRoom().getLongDescription());
        gui.showImage(player.getCurrentRoom().getImageName());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room vacuum,anger,hatred,vengeance,lackOfConfidence,susceptibility,despair,sadness,regret,indifference,deliverance;

        // create the rooms
        vacuum = new Room("in the vaccum phase","vacuum.jpg");
        anger = new Room("in the anger phase", "anger.jpg");
        hatred =new Room("in the hatred phase","hatred.jpg");
        vengeance=new Room("in the vengeance phase","vengeance.jpeg");
        lackOfConfidence=new Room("in the lack of confidence phase","confidence.jpg");
        susceptibility= new Room("in the susceptibility phase", "susceptibility.jpg");
        despair = new Room("in the despair phase", "despair.jpg");
        sadness = new Room("in the sadness phase","sadness.jpg");
        regret = new Room("in the regret phase","regret.jpg");
        indifference = new Room("in the indifference phase" , "indifference.jpg");
        deliverance= new Room("congratz you are now recovered !!!! ","deliverance.jpg");
        // initialise room exits
        vacuum.setExit("up", anger);
        vacuum.addItem("Motivation","It helps you to move on",1);
        anger.setExit("west",hatred);
        anger.addItem("Control","You need it to control your feelings",2);
        anger.addItem("Cold","You need to be cold to better control your feelings",2);
        hatred.setExit("north",vengeance);
        hatred.setExit("east",anger);
        vengeance.setExit("up",susceptibility);
        vengeance.setExit("south",hatred);
        susceptibility.setExit("south",lackOfConfidence);
        susceptibility.setExit("down",vengeance);
        susceptibility.addItem("Reflexion","It's the only way to get to know yourself",2);
        lackOfConfidence.setExit("up",despair);
        lackOfConfidence.setExit("north",susceptibility);
        despair.setExit("east",sadness);
        despair.setExit("down",lackOfConfidence);
        despair.addItem("Patience","You need time to get over some bad feelings",4);
        despair.addItem("Friends","You need less time to get over some bad feelings if you have good friends",2);
        sadness.setExit("up",regret);
        sadness.setExit("west",despair);
        regret.setExit("up",indifference);
        regret.setExit("down",sadness);
        regret.addItem("Confidence","If you don't trust yourself why should anyone else?",4);
        indifference.setExit("up",deliverance);
        indifference.setExit("down",regret);
        indifference.addItem("Hope","Because hope is the only way to go forward in life :)",5);
        
        player = new Player("Chafik",200,vacuum);  // start game outside
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    public void interpretCommand(String commandLine)  {
        gui.println(commandLine);
        Command command = parser.getCommand(commandLine);

        if(command.isUnknown()) {
            gui.println("I don't know what you mean...");
            return;
        }
        
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if (commandWord.equals("look")) {
        	look();
        }
        else if (commandWord.equals("eat")) {
        	eat(command);
        }
        else if (commandWord.equals("back")) {
        	back();
        }
        else if (commandWord.equals("take")) {
        	take(command);
        }
        else if (commandWord.equals("drop")) {
        	drop(command);
        }
        else if (commandWord.equals("items")) {
        	inventory();
        }
        else if (commandWord.equals("test")) {
        	testFile(command);
        }
        else if (commandWord.equals("beamer")) {
        	beamer(command);
        }
        else if (commandWord.equals("quit")) {
            if(command.hasSecondWord())
                gui.println("Quit what?");
            else
                endGame();
        }

        gui.println("");
    }
    
    /**
     * Prints the current location information - Question 7.5 : 
     */
    private void printLocationInfo() {
    	gui.println(player.getCurrentRoom().getLongDescription());
        if(player.getCurrentRoom().getImageName() != null)
            gui.showImage(player.getCurrentRoom().getImageName());
    }
    

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() {
        gui.println("You are lost. You are alone. You wander.\n");
        gui.print("Your command words are: " + parser.showCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        
        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null)
            gui.println("There is no door!");
        else {
        	player.addPreviousRoom(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }

    private void endGame() {
        gui.println("Thank you for playing.  Good bye.");
        gui.enable(false);
    }

    /** 
     * Execute commands from a text file. Question 7.28.1
     * Execution : test fileName
     */
    private void testFile(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the name of the test file...
            gui.println("Test what?");
            return;
        }
        
        String fileName = command.getSecondWord();
		
		//lecture du fichier texte	
		try{
			String word1, word2;
			boolean finished = false, loop;
			BufferedReader br=new BufferedReader(new FileReader(new File(fileName)));
			String ligne;
			Scanner tokenizer;
			while((ligne = br.readLine()) != null) {
				loop = true;
				word1 = null;
				word2 = null;
				System.out.println(ligne);
				while(!finished && loop) {
					loop = false;
					tokenizer = new Scanner(ligne);
			        if(tokenizer.hasNext()) {
			            word1 = tokenizer.next();      // get first word
			            if(tokenizer.hasNext()) {
			                word2 = tokenizer.next();      // get second word
			                // note: we just ignore the rest of the input line.
			            }
			        }
			        if(word1.equals("quit")) endGame();
			        interpretCommand(word1+" "+word2);		        					        
				}
			}
			br.close(); 
		}		
		catch (Exception e){
			gui.println(e.toString());
		}
        
    }
    
    /**
     * Charges and fires the beamer (A tool that takes the player to the room where it was charged)
     * @param command To verify the second word of the command
     */
    public void beamer(Command command) {
    	if(player.findItem("Beamer") != null && command.hasSecondWord()) {
    		if(command.getSecondWord().equals("charge") && player.findItem("Beamer").getDescription().equals("Charged")) {
    			player.setBeamer(player.getCurrentRoom());
    			gui.println("The beamer has been charged in this room.");
    		}
    		else if(player.getBeamer() == null)
    			System.out.println("The beamer is not charged ...");
    		else if(player.findItem("Beamer") != null && command.getSecondWord().equals("fire")) {
    			if(player.findItem("Beamer").getDescription().equals("Charged")) {
    				player.setCurrentRoom(player.getBeamer());
    				player.setBeamer(null);
    				player.removeItem(player.findItem("Beamer"));
    				player.addItem("Beamer", "Not charged", 100);
    				gui.println("Player transported to the beamer room !");
    				printLocationInfo();
    			}
    			else
    				gui.println("Sorry. The beamer is not charged ...");
    		}
    		else gui.println("A beamer can only be charged or fired ...");
    	}
    	else gui.println("We're gonna need a second word for the beamer command ...");
    }
    
    /**
     * Prints the description and the total weight of all the items carried by the player.
     */
    public void inventory() {
    	gui.println(player.getCarriedItemsList());
    }

    /**
     * Removes an item of the list of items carried by the player.
     * The item is added to the current room.
     * Question 7.30
     */
    public void drop(Command command) {
    	if(command.hasSecondWord()) {
    		if(player.findItem(command.getSecondWord()) != null) {
    			player.getCurrentRoom().addItem(player.findItem(command.getSecondWord()));
    			player.removeItem(player.findItem(command.getSecondWord()));
    			gui.println("Item "+command.getSecondWord()+" dropped.");
    		}
    		else {
    			gui.println("Item '"+command.getSecondWord()+" doesn't exist.");  			
    		}
    	}
    	else
    		gui.println("Drop what?");
    }

    /**
     * Adds an item to the list of items carried by the player.
     * The item is removed from the current room.
     * Question 7.30
     */
    public void take(Command command) {
    	if(command.hasSecondWord()) {
    		if(player.getCurrentRoom().findItem(command.getSecondWord()) != null) {
    			if(player.getMaximumWeight() >= player.getCarriedItemsWeight() +
    					player.getCurrentRoom().findItem(command.getSecondWord()).getWeight()) {
    				player.addItem(player.getCurrentRoom().findItem(command.getSecondWord()));
    				player.getCurrentRoom().removeItem(player.getCurrentRoom().findItem(command.getSecondWord()));
    				gui.println("Item "+command.getSecondWord()+" taken.");
    			}
    			else 
    				gui.println("You can't take the item "+command.getSecondWord()+"...");
    		}
    		else {
    			gui.println("Item '"+command.getSecondWord()+"' doesn't exist.");  			
    		}
    	}
    	else
    		gui.println("Take what?");
    }

    /**
     * back command, it takes the player to the last room he has visited.
     * This command can be used until the player reaches the start point.
     * Question 7.23 & 7.26
     */
    private void back() {
    	if(!player.emptyPreviousRooms()) {
    		player.setCurrentRoom(player.getLastRoom());
    		player.removePreviousRoom();
    		printLocationInfo();
    	}
    	else 
    		gui.println("No last room visited");
    }

    /**
     * eat command, it prints a message that says that the player has eaten.
     * Question 7.15
     */
    private void eat(Command command) {
    	if(command.hasSecondWord()) {
    		if(command.getSecondWord().equals("cookie") && player.getCurrentRoom().findItem("Cookie") != null) {
    			player.setMaximumWeight(player.getMaximumWeight()+50);
    			player.getCurrentRoom().removeItem(player.getCurrentRoom().findItem("Cookie"));
    			gui.println("You have eaten a Magic Cookie ! Your maximum carrying weight is increased now!");
    			gui.println("New Maximum weight : "+player.getMaximumWeight());
    		}
    		else if (command.getSecondWord().equals("cookie") && player.findItem("Cookie") != null) {
    			player.setMaximumWeight(player.getMaximumWeight()+50);
    			player.removeItem(player.findItem("Cookie"));
    			gui.println("You have eaten a Magic Cookie ! Your maximum carrying weight is increased now!");
    			gui.println("New Maximum weight : "+player.getMaximumWeight());
    		}
    		else {
    			gui.println("Eat what ?");  			
    		}
    	}
    	else
    		gui.println("You have eaten now and you are not hungry any more.");
    }

    /**
     * look command, shows the long description of the current room.
     * Question 7.14
     */
    private void look() {
    	gui.println(player.getCurrentRoom().getLongDescription());
    }
    
}
