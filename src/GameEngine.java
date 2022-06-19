// TODO: Copy code from your Assignment 1 as starter code.
/**
 * GameEngine runs the maingameloop
 * integrates other Player, Monster, and World class into the system.
 * When start command is provided without a game file name, the default world is loaded.
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
public class GameEngine {
    //parameters of the default world
    final static int PLAYER_START_POSITION_X=1;
    final static int PLAYER_START_POSITION_Y=1;
    final static int MONSTER_START_POSITION_X=4;
    final static int MONSTER_START_POSITION_Y=2;
    final static int MAP_HEIGHT=4;
    final static int MAP_WIDTH=6;
    public static void main(String[] args) throws GameLevelNotFoundException {
        Player player=new Player();
        World world=new World();
        Monster monster=new Monster();
        Scanner keyboard= new Scanner(System.in);
        //set the width and height of map to that of default game world
        //if game file is loaded, the width and height can be reset
        Map map=new Map(MAP_WIDTH,MAP_HEIGHT);

        // TODO: Some starter code has been provided below.
        // Edit this method as you find appropriate.

        // Creates an instance of the game engine.
        GameEngine gameEngine = new GameEngine();

        // Runs the main game loop.
        gameEngine.runGameLoop(player,world,keyboard,map,gameEngine);

    }


    /*
     *  Logic for running the main game loop.
     */
    private void runGameLoop(Player player, World world, Scanner keyboard,Map map,GameEngine gameEngine) throws GameLevelNotFoundException {
        ArrayList<Monster> defaultMonsters=map.getMonsterArray();
        Monster monster;
        if(defaultMonsters.size()>0){
            monster=defaultMonsters.get(0);
        }
        else{
            monster=new Monster();
        }
        displayTitleText(player,map,monster);
        simpleCommandLoop(player,monster, world, keyboard, map, gameEngine);

    }
    public void simpleCommandLoop(Player player, Monster monster, World world, Scanner keyboard, Map map, GameEngine gameEngine)throws GameLevelNotFoundException{
        String myEntry;
        System.out.print("> ");
        myEntry=keyboard.nextLine();
        checkCommand(myEntry,player,monster,world,keyboard,map,gameEngine);
        boolean isExit=myEntry.equals("exit");
        while(! isExit){
            System.out.print("> ");
            //used keyboard.next() in original version
            myEntry=keyboard.nextLine();
            checkCommand(myEntry,player,monster,world,keyboard,map,gameEngine);
            isExit=myEntry.equals("exit");
            if(isExit){
                System.exit(1);
            }
        }
    }
    // used when "monster" command is called
    public Monster createMonster(Scanner keyboard, Map map,Player player, Monster monster){
        System.out.print("Monster name: ");
        String monsName=keyboard.next();
        System.out.print("Monster health: ");
        int health=keyboard.nextInt();
        System.out.print("Monster damage: ");
        int damage=keyboard.nextInt();
        map.addMonster(new Monster(MONSTER_START_POSITION_X,MONSTER_START_POSITION_Y,monsName,health,damage));
        System.out.print("Monster '");
        monster.setName(monsName);
        monster.setPosX(MONSTER_START_POSITION_X);
        monster.setPosY(MONSTER_START_POSITION_Y);
        monster.setCurrentHealth(health);
        monster.setMaxHealth(health);
        monster.setDamage(damage);
        System.out.print(monster.getName());
        System.out.print("' created.");
        System.out.println();
        System.out.println();
        System.out.println("(Press enter key to return to main menu)");
        String enterkey=keyboard.nextLine();
        checkEnter(enterkey,player,monster,keyboard,map);
        return monster;
    }

    //used when "command" is called
    public void printCommand(){
        System.out.println("help");
        System.out.println("player");
        System.out.println("monster");
        System.out.println("start");
        System.out.println("exit\n");
    }

    // used when "player" is called and player is anonymous
    // meaning no player has been created yet
    public void createPlayer(Scanner keyboard, Player player,Monster monster,Map map){
        System.out.println("What is your character's name?");
        String name=keyboard.nextLine();
        player.setName(name);
        System.out.print("Player '");
        System.out.print(player.getName());
        System.out.print("' created.\n");
        System.out.println();
        player.setPosX(PLAYER_START_POSITION_X);
        player.setPosY(PLAYER_START_POSITION_Y);
        System.out.println("(Press enter key to return to main menu)");
        String enterkey=keyboard.nextLine();
        checkEnter(enterkey,player,monster,keyboard,map);
    }

    public void printPlayerInfo(Scanner keyboard, Player player, Monster monster, Map map){
        System.out.println(player.getName()+ " (Lv. "+player.getLevel()+")");
        System.out.println("Damage: "+player.getDamage());
        System.out.println("Health: "+player.getCurrentHealth()+"/"+player.getMaxHealth());
        System.out.println();
        System.out.println("(Press enter key to return to main menu)");
        String enterkey=keyboard.nextLine();
        checkEnter(enterkey,player,monster,keyboard,map);
    }

    public void printHelp(){
        System.out.println("Type 'commands' to list all available commands");
        System.out.println("Type 'start' to start a new game");
        System.out.println("Create a character, battle monsters, and find treasure!\n");
    }

    /*
     *  Displays the title text.
     */
    private void displayTitleText(Player player,Map map, Monster monster) {

        String titleText = " ____                        \n" +
                "|  _ \\ ___   __ _ _   _  ___ \n" +
                "| |_) / _ \\ / _` | | | |/ _ \\\n" +
                "|  _ < (_) | (_| | |_| |  __/\n" +
                "|_| \\_\\___/ \\__, |\\__,_|\\___|\n" +
                "COMP90041   |___/ Assignment ";

        System.out.println(titleText);
        System.out.println();
        if(player.getName().equals("anonymous")){
            System.out.print("Player: [None]");
        }
        else {
            System.out.print("Player: "+player.getName()+" "+player.getCurrentHealth()+"/"+player.getMaxHealth());
        }

        //either there are no monsters in the array (default game world)
        // or the monster has disappeared
        String monsterName=monster.getName();
        if(monsterName.equals("anonymous")){
            System.out.print("  | Monster: [None]");
        }
        else {
            System.out.print("  | Monster: "+monster.getName()+" "+ monster.getCurrentHealth()+"/"+monster.getMaxHealth());
        }
        System.out.println();
        System.out.println();
        System.out.println("Please enter a command to continue.");
        System.out.println("Type 'help' to learn how to get started.\n");

    }
    private void checkCommand(String myEntry, Player player, Monster monster, World world, Scanner keyboard, Map map, GameEngine gameEngine)throws GameLevelNotFoundException{
        // start <gamefilename>
        String gameFileName;
        //This should be the total number of words in the command line if we
        // want to start a loaded game world
        if (myEntry.contains("start ")){
            if(player.getName().equals("anonymous")){
                playerNotFound(keyboard,player,monster,map);
            }
            else{
                gameFileName=myEntry.substring(6)+".dat";
                readingGameFile(keyboard,gameFileName,player, map,gameEngine);
                File tempFile=new File(gameFileName);
                if(tempFile.isFile()){
                    runGameWorldStartCommand(player,world,keyboard,map,gameEngine,gameFileName);
                }
            }
        }
        if(myEntry.equals("help")){
            printHelp();

        }
        else if(myEntry.equals("commands")){
            printCommand();
        }
        else if(myEntry.equals("player")){
            if(player.getName().equals("anonymous")){
                createPlayer(keyboard,player,monster,map);
            }
            else{
                printPlayerInfo(keyboard,player,monster,map);
            }

        }
        else if(myEntry.equals("monster")){
            monster=createMonster(keyboard,map,player,monster);

        }


        else if(myEntry.equals("exit")){
            System.out.println("Thank you for playing Rogue!");
        }

        else if(myEntry.equals("start")){
            if(player.getName().equals("anonymous")){
                playerNotFound(keyboard,player,monster,map);
            }
            int monsArraySize=map.getMonsterArray().size();
            if(monsArraySize==0){
                System.out.println("No monster found, please create a monster with 'monster' first.");
                System.out.println();
                System.out.println("(Press enter key to return to main menu)");
                String enterkey=keyboard.nextLine();
                checkEnter(enterkey,player,monster,keyboard,map);
            }
            //healing the player and monster to full health when a new game is triggered
            else if(player.getCurrentHealth()!=player.getMaxHealth() || monster.getCurrentHealth()!=monster.getMaxHealth()){
                player.setMaxHealth(player.computeMaxHealth());
                monster.setCurrentHealth(monster.getMaxHealth());
                // also, restore player and monster to their original position
                player.setPosX(PLAYER_START_POSITION_X);
                player.setPosY(PLAYER_START_POSITION_Y);
                monster.setPosX(MONSTER_START_POSITION_X);
                monster.setPosY(MONSTER_START_POSITION_Y);
                runStartCommand(player,monster,world,keyboard,map,gameEngine);
            }
            else{
                map.setArraySize(MAP_HEIGHT,MAP_WIDTH);
                map.fillTerrain();
                player.draw(map);
                //need to get the monster from the arraylist first before drawing
                ArrayList<Monster>monsters=map.getMonsterArray();
                if(monsters.size()>0){
                    monster=monsters.get(0);
                    monster.draw(map);
                }
                runStartCommand(player,monster,world,keyboard,map,gameEngine);

            }
        }
        else if(myEntry.equals("save")){
            if(!player.getName().equals("anonymous")){
                String p=player.getName()+" "+player.getLevel();
                createPlayerFile(p);
            }
            else{
                System.out.println("No player data to save.");
                System.out.println();
            }
        }
        else if(myEntry.equals("load")){
            loadPlayerFile(player);
            if(!player.getName().equals("anonymous")){
                printPlayerInfo(keyboard,player,monster,map);
            }
        }

    }
    public void loadPlayerFile(Player player){
        Scanner inputStream=null;
        String playerData;
        String playerName;
        int playerLevel;
        // players loaded from the saved file will be healed to full health
        int newHealth;
        try{
            inputStream=new Scanner(new FileInputStream("player.dat"));
            if(inputStream.hasNextLine()){
                playerData=inputStream.nextLine();
                String[] playerField=playerData.split(" ");
                playerName=playerField[0];
                player.setName(playerName);
                playerLevel=Integer.parseInt(playerField[1]);
                player.setLevel(playerLevel);
                newHealth=player.computeMaxHealth();
                player.setCurrentHealth(newHealth);
                player.setMaxHealth(newHealth);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("No player data found.");
            System.out.println();
        }
    }
    //createPlayerFile is used when the "save" command is called
    // the condition of player created first via "player" command must be fulfilled
    // String p is "[player name] [Level]"
    public void createPlayerFile(String p){
        PrintWriter outputStream=null;
        try{
            outputStream=new PrintWriter(new FileOutputStream("player.dat"));
        }
        catch(FileNotFoundException e){
            System.out.println("No player data to save.");
            System.out.println();

        }
        finally{
            outputStream.println(p);
            outputStream.close();
            System.out.println("Player data saved.");
            System.out.println();
        }
    }
    // screen display if player is not found
    // used as prerequisite requirement for both default game world and loaded game world
    public void playerNotFound(Scanner keyboard,Player player, Monster monster, Map map){
        System.out.println("No player found, please create a player with 'player' first.");
        System.out.println();
        System.out.println("(Press enter key to return to main menu)");
        String enterkey=keyboard.nextLine();
        checkEnter(enterkey,player,monster,keyboard,map);
    }
    public void checkEnter(String myEntry,Player player, Monster monster, Scanner keyboard, Map map){
        /** if(myEntry.equals("")){
         displayTitleText(player,map,monster);
         } **/
        //for some reason, Test 9 can't detect enter
        displayTitleText(player,map,monster);

    }

    // home is invalid before the start command, therefore we need to create a separate function outside checkCommand
    public boolean checkHome(String myEntry, Player player, Monster monster, World world, Scanner keyboard, Map map){
        if(myEntry.equals("home")){
            System.out.println("Returning home...");
            System.out.println();
            System.out.println("(Press enter key to return to main menu)");
            String enterkey=keyboard.nextLine();
            checkEnter(enterkey,player,monster,keyboard,map);
            return true;
        }
        return false;
    }

    // This method is executed after the start command is executed
    // player is allowed to enter movement command continuosly until the player meets the monster
    public void runStartCommand(Player player, Monster monster, World world, Scanner keyboard, Map map,GameEngine gameEngine)throws GameLevelNotFoundException{
        map.drawMap();
        boolean secondExitCheck=false;
        String move;
        while(!world.checkCombat(player,monster)&& !secondExitCheck){
            // Start: can be copied into another function and be reused for the loaded game world
            move=commonStartCode(player,monster,world,keyboard,gameEngine,map);
            secondExitCheck=move.equals("exit");
            // End
            world.movement(move,player,monster,map);
        }
        if(world.checkCombat(player,monster)){
            world.runBattleLoop(player,monster,map);
            String enterkey=keyboard.nextLine();
            checkEnter(enterkey,player,monster,keyboard,map);
        }
        else if(secondExitCheck){
            System.exit(0);
        }
    }
    public String commonStartCode(Player player, Monster monster, World world, Scanner keyboard, GameEngine gameEngine, Map map)throws GameLevelNotFoundException{
        System.out.print("> ");
        // orginally next in default game world, but need to detect empty spaces so change to nextLine
        while(keyboard.hasNextLine()){
            String move=keyboard.nextLine();
            boolean isHomeReturned=checkHome(move,player,monster,world,keyboard,map);
            if(isHomeReturned){
                player.setIsHomeReturned(isHomeReturned);
            }
            checkCommand(move,player,monster,world,keyboard,map,gameEngine);
            return move;
        }
        return "exit";
    }
    public void runGameWorldStartCommand(Player player, World world, Scanner keyboard, Map map,GameEngine gameEngine, String filename)throws GameLevelNotFoundException{
        map.drawMap();
        boolean secondExitCheck=false;
        boolean hasPlayerWon=true;
        String move;
        boolean isWorldComplete=false;
        Monster monster=new Monster();

        // we need to continue the game until warp stone is collected
        while(!isWorldComplete){
            outerloop:
            do{
                move=commonStartCode(player,monster,world,keyboard,gameEngine,map);
                secondExitCheck=move.equals("exit");
                if(secondExitCheck){
                    System.exit(0);
                }
                else if(!(move.equals("help")||move.equals("commands")||move.equals("player")||move.equals("save")||move.equals("load"))){
                    isWorldComplete=world.gameFileMovement(move,map,player);
                    if(isWorldComplete){
                        break outerloop;
                    }
                }
                //Testing
            }while(!world.allMonstersIsCombat(map,player) && !secondExitCheck);

            while(world.allMonstersIsCombat(map,player) && hasPlayerWon){
                // get the monster from combatMonster attribute in Map
                Monster battleMonster=map.getCombatMonster();
                hasPlayerWon=world.runGameFileBattleLoop(player,battleMonster,map,gameEngine,keyboard,world);

            }
        }
        String enterkey=keyboard.nextLine();
        checkEnter(enterkey,player,monster,keyboard,map);

    }


    public void readingGameFile(Scanner keyboard, String filename,Player player, Map map, GameEngine gameEngine)throws GameLevelNotFoundException {
        Scanner inputStream=null;
        //FileNotFoundException is used to catch the "general" exception
        // to be handled when FileInputStream is called
        try {
            File tempFile=new File(filename);
            if (!tempFile.isFile()) {
                throw new GameLevelNotFoundException("Map not found.");
            }
            inputStream = new Scanner(new FileInputStream(filename));
            int count = 0;
            final  int ENTITY_TYPE = 0;
            String[] terrainRow;
            String[] entityData;
            final int POSX_INDEX = 1;
            final int POSY_INDEX = 2;
            final int MONS_NAME_INDEX = 3;
            final int MONS_HEALTH_INDEX = 4;
            final int MONSATTACK_INDEX = 5;
            final int ITEM_SYMBOL;
            int posX;
            int posY;
            String monsName;
            int monsHealth;
            int monsAttack;
            String itemSymbol;
            String line;
            //In the game file, the first row (row 0) is the width and height of the map
            // note that there are no spacing when the terrain is loaded in the game file
            // there is spacing when player, monster and item command are loaded
            while (inputStream.hasNextLine()) {
                line = inputStream.nextLine();
                if (count == 0) {
                    String[] mapDimension = line.split(" ");
                    final int WIDTH_POS = 0;
                    final int HEIGHT_POS = 1;
                    int width = Integer.parseInt(mapDimension[WIDTH_POS]);
                    int height = Integer.parseInt(mapDimension[HEIGHT_POS]);
                    map.setArraySize(height, width);
                    map.setWidth(width);
                    map.setHeight(height);
                }
                if (count >= 1 && count <= map.getHeight()) {
                    terrainRow = line.split("");
                    for (int i = 0; i < terrainRow.length; i++) {
                        map.setTerrain(i, count - 1, terrainRow[i]);
                    }
                }
                else {
                    entityData = line.split(" ");
                    if (entityData[ENTITY_TYPE].equals("player")) {
                        posX = Integer.parseInt(entityData[POSX_INDEX]);
                        posY = Integer.parseInt(entityData[POSY_INDEX]);
                        player.setPosX(posX);
                        player.setPosY(posY);
                        player.draw(map);
                    } else if (entityData[ENTITY_TYPE].equals("monster")) {
                        posX = Integer.parseInt(entityData[POSX_INDEX]);
                        posY = Integer.parseInt(entityData[POSY_INDEX]);
                        monsName = entityData[MONS_NAME_INDEX];
                        monsHealth = Integer.parseInt(entityData[MONS_HEALTH_INDEX]);
                        monsAttack = Integer.parseInt(entityData[MONSATTACK_INDEX]);
                        map.addMonster(new Monster(posX, posY, monsName, monsHealth, monsAttack));
                        //need to draw all monster position on map
                        gameEngine.drawAllMonsters(map);
                    } else if (entityData[ENTITY_TYPE].equals("item")) {
                        posX = Integer.parseInt(entityData[POSX_INDEX]);
                        posY = Integer.parseInt(entityData[POSY_INDEX]);
                        itemSymbol = entityData[MONS_NAME_INDEX];
                        map.addItem(new Item(posX, posY, itemSymbol));
                        //need to draw all item position on map
                        gameEngine.drawAllItems(map);
                    }
                }
                count++;
            }
            inputStream.close();
        }
        catch(GameLevelNotFoundException e){
            System.out.println(e.getMessage());
            System.out.println();
            System.out.println("(Press enter key to return to main menu)");
            String enterkey=keyboard.nextLine();
            // create a dummy monster variable
            Monster dummyMonster=new Monster();
            checkEnter(enterkey,player,dummyMonster,keyboard,map);

        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    // this method is only used as a helper function when reading the game file
    public void drawAllMonsters(Map map){
        ArrayList<Monster>monsters=map.getMonsterArray();
        for(Monster monster:monsters){
            monster.draw(map);
        }
    }
    public void drawAllItems(Map map){
        ArrayList<Item>items=map.getItemArray();
        for(Item item:items){
            item.draw(map);
        }
    }
    static void createOriginal(String s, String filename){
        try{
            PrintWriter p= new PrintWriter(new FileOutputStream(filename));
            p.print(s);
            p.close();
        }
        catch(Exception e){
            e.getMessage();
            System.exit(1);
        }
    }
}


