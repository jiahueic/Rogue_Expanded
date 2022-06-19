/**
 * Produce the battle world map, checks whether movements are valid and runs the combat loop.
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
import java.util.ArrayList;
import java.util.Scanner;
public class World {

    //Checks and updates movements in the default game world
    public void movement(String move, Player player, Monster monster,Map map){

        if(move.equals("w")){
            player.moveNorth(map);
            if(map.checkMapBoundaries(player.getPosX(),player.getPosY())){
                player.draw(map);
                if(!checkCombat(player,monster)){
                    map.drawMap();
                }
            }
            //if movement is out of boundary
            else{
                player.moveSouth(map);
                player.draw(map);
                map.drawMap();
            }
        }
        else if(move.equals("a")){
            player.moveWest(map);
            if(map.checkMapBoundaries(player.getPosX(),player.getPosY())){
                player.draw(map);
                if(!checkCombat(player,monster)){
                    map.drawMap();
                }
            }
            else{
                player.moveEast(map);
                player.draw(map);
                map.drawMap();
            }

        }
        else if(move.equals("s")){
            player.moveSouth(map);
            if(map.checkMapBoundaries(player.getPosX(),player.getPosY())){
                player.draw(map);
                if(!checkCombat(player,monster)){
                    map.drawMap();
                }
            }
            else{
                player.moveNorth(map);
                player.draw(map);
                map.drawMap();
            }
        }
        else if(move.equals("d")){
            player.moveEast(map);
            if(map.checkMapBoundaries(player.getPosX(),player.getPosY())){
                player.draw(map);
                if(!checkCombat(player,monster)){
                    map.drawMap();
                }
            }
            else{
                player.moveWest(map);
                player.draw(map);
                map.drawMap();
            }
        }
    }

    //checks if the x and y coordinates of player and monster are the same
    public boolean checkCombat(Player player, Monster monster){
        return player.getPosX()==monster.getPosX() &&
                player.getPosY()==monster.getPosY();
    }
    public void runBattleLoop(Player player, Monster monster, Map map){
        // the loop stops when either the monster or player is defeated (i.e.their current health reaches 0 or less)
        battleLoop(player,monster,map);
        System.out.println();
        System.out.println();
        System.out.println("(Press enter key to return to main menu)");
    }
    //battleLoop is code to be reused when battle loop is run in the default game world and
    // loaded game world respectively
    public void battleLoop(Player player, Monster monster,Map map){
        System.out.println(player.getName()+" encountered a "+monster.getName()+"!");
        while(player.getCurrentHealth()>0 && monster.getCurrentHealth()>0){
            System.out.println();
            System.out.print(player.getName()+" "+player.getCurrentHealth()+"/");
            System.out.print(player.getMaxHealth()+" | "+monster.getName()+" "+monster.getCurrentHealth()+"/"+monster.getMaxHealth());
            player.attackMonster(monster);
            System.out.println();
            System.out.println(player.getName()+" attacks "+monster.getName()+" for "+player.getDamage()+" damage.");
            if(monster.getCurrentHealth()>0){
                monster.attackPlayer(player);
                System.out.println(monster.getName()+" attacks "+player.getName()+" for "+monster.getDamage()+" damage.");
            }
            else if(monster.getCurrentHealth()<0){
                System.out.println(player.getName()+" wins!");
                System.out.println();
            }

        }
        if(player.getCurrentHealth()<0){
            System.out.print(monster.getName()+" wins!");
        }
    }
    public boolean runGameFileBattleLoop(Player player, Monster monster, Map map, GameEngine gameEngine, Scanner keyboard, World world)throws GameLevelNotFoundException{
        // need to return whether the player has won
        boolean hasPlayerWon=false;
        battleLoop(player,monster,map);
        if(player.getCurrentHealth()>0){
            hasPlayerWon=true;
            monster.monsterDisappear(map,player);
            ArrayList<Item>items=map.getItemArray();
            Item attackItem=items.get(1);

            for(Item item:items){
                if(!item.getIsPickedUp()){
                    item.draw(map);
                }
            }
            // checks if more than one monster occurs in the same cell
            map.drawMap();
             /*if(allMonstersIsCombat(map,player)){
			// get the monster from combatMonster attribute in Map
				Monster battleMonster=map.getCombatMonster();
				hasPlayerWon=runGameFileBattleLoop(player,battleMonster,map,gameEngine,keyboard,world);
			}*/
        }
        else if(monster.getCurrentHealth()>0){
            hasPlayerWon=false;
            //System.out.print(monster.getName()+" wins!");
            System.out.println();
            System.out.println();
            System.out.println("(Press enter key to return to main menu)");
            String enterkey=keyboard.nextLine();
            // create a dummy Monster object such that it displays monster none on the screen
            Monster dummyMonster=new Monster();

            gameEngine.checkEnter(enterkey,player,dummyMonster,keyboard,map);


        }
        return hasPlayerWon;
    }

    public void drawAllMonster(Map map){
        ArrayList<Monster>monsters=map.getMonsterArray();
        for(Monster monster:monsters){
            if(monster.getHasDisappeared()){
                continue;
            }
            else{
                monster.drawMonsterMovement(map);
                //Testing
            }
        }
    }
    public void allMonstersCheck(Map map,Player player){
        //loop through all monsters in the arrayList in map
        ArrayList<Monster>monsters=map.getMonsterArray();
        //check if each monster is within two units of the player
        for(Monster monster:monsters){
            // skips the iteration if the monster has disapperared
            if(monster.getHasDisappeared()){
                continue;
            }
            if(monster.checkTwoUnits(player)){
                monster.monsterMovement(player,map);
            }
        }
    }
    // because the monster posiiton field is already updated in the same round as player
    // to check for combat, we need to compare the current position of the player and the
    // previous position of the monsters
    public boolean gameFileCheckCombat(Player player, Monster monster,Map map){
        int prevPosX=monster.getPrevPosX();
        int prevPosY=monster.getPrevPosY();
        return player.getPosX()== prevPosX && player.getPosY()==prevPosY;
    }
    public boolean allMonstersIsCombat(Map map, Player player){
        ArrayList<Monster>monsters=map.getMonsterArray();
        boolean isCombat;
        for(Monster monster:monsters){
            isCombat=gameFileCheckCombat(player,monster, map);
            if(monster.getHasDisappeared()){
                continue;
            }
            if (isCombat){
                map.setCombatMonster(monster);
                return true;
            }
        }
        return false;
    }

    // This method is used in the loaded game world
    public int[] getPlayerPrevPos(Player player){
        int[] playerPrevPos=new int[2];
        int posX=player.getPosX();
        int posY=player.getPosY();
        playerPrevPos[0]=posX;
        playerPrevPos[1]=posY;
        return playerPrevPos;
    }

    public boolean gameFileMovement(String move,Map map,Player player){
        //need to check if monster is within 2 cells of player after movement of player is valid
        // and rendered
        // method called allMonsterCheck
        final int POSX_INDEX=0;
        final int POSY_INDEX=1;
        boolean isTraversable;
        boolean isInBoundary;
        boolean isWorldComplete=false;
        if(move.equals("w")){
            int[]playerPrevPos=getPlayerPrevPos(player);
            player.setNorth(map);
            isTraversable=map.checkTraversable(map.getTerrain(),player.getPosX(),player.getPosY());
            isInBoundary=map.checkMapBoundaries(player.getPosX(),player.getPosY());
            if(isTraversable && isInBoundary){
                player.moved(map,playerPrevPos[POSX_INDEX],playerPrevPos[POSY_INDEX]);
                drawAllMonster(map);
                player.draw(map);
                allMonstersCheck(map,player);
                // when checking, new position is already set
                // so to to access prevPos
                if(!allMonstersIsCombat(map,player)){
                    loopAllItems(player,map);
                    isWorldComplete=map.searchWarpStone();
                    if(!isWorldComplete){
                        map.drawMap();
                    }
                }
            }
            //if movement is out of boundary
            else{
                player.setSouth(map);
                player.draw(map);
                // render all monsters on terrain cell from the previous round
                drawAllMonster(map);
                map.drawMap();
            }
        }
        else if(move.equals("a")){
            int[]playerPrevPos=getPlayerPrevPos(player);
            player.setWest(map);
            isTraversable=map.checkTraversable(map.getTerrain(),player.getPosX(),player.getPosY());
            isInBoundary=map.checkMapBoundaries(player.getPosX(),player.getPosY());
            if(isTraversable&&isInBoundary){
                player.moved(map,playerPrevPos[POSX_INDEX],playerPrevPos[POSY_INDEX]);
                drawAllMonster(map);
                player.draw(map);
                allMonstersCheck(map,player);
                if(!allMonstersIsCombat(map,player)){
                    loopAllItems(player,map);
                    isWorldComplete=map.searchWarpStone();
                    if(!isWorldComplete){
                        map.drawMap();
                    }
                }
            }
            else{
                player.setEast(map);
                player.draw(map);
                // render all monsters on terrain cell from the previous round
                drawAllMonster(map);
                // only print map if no monsters and players are in combat mode
                if(!allMonstersIsCombat(map,player)){
                    map.drawMap();
                }
            }

        }
        else if(move.equals("s")){
            int[]playerPrevPos=getPlayerPrevPos(player);
            player.setSouth(map);
            isTraversable=map.checkTraversable(map.getTerrain(),player.getPosX(),player.getPosY());
            isInBoundary=map.checkMapBoundaries(player.getPosX(),player.getPosY());
            if(isTraversable &&isInBoundary){
                player.moved(map,playerPrevPos[POSX_INDEX],playerPrevPos[POSY_INDEX]);
                drawAllMonster(map);
                player.draw(map);
                allMonstersCheck(map,player);
                if(!allMonstersIsCombat(map,player)){
                    loopAllItems(player,map);
                    isWorldComplete=map.searchWarpStone();
                    if(!isWorldComplete){
                        map.drawMap();
                    }
                }
            }
            else{
                player.setNorth(map);
                player.draw(map);
                drawAllMonster(map);
                map.drawMap();
            }
        }
        else if(move.equals("d")){
            int[]playerPrevPos=getPlayerPrevPos(player);
            player.setEast(map);
            isTraversable=map.checkTraversable(map.getTerrain(),player.getPosX(),player.getPosY());
            isInBoundary=map.checkMapBoundaries(player.getPosX(),player.getPosY());
            if(isTraversable&&isInBoundary){
                player.moved(map,playerPrevPos[POSX_INDEX],playerPrevPos[POSY_INDEX]);
                drawAllMonster(map);
                player.draw(map);
                allMonstersCheck(map,player);
                if(!allMonstersIsCombat(map,player)){
                    loopAllItems(player,map);
                    isWorldComplete=map.searchWarpStone();
                    if(!isWorldComplete){
                        map.drawMap();
                    }
                }
            }
            else{
                player.setWest(map);
                player.draw(map);
                // render all monsters on terrain cell from the previous round
                drawAllMonster(map);
                map.drawMap();
            }
        }
        // all other invalid commands other than home
        else if(!player.getIsHomeReturned()){
            drawAllMonster(map);
            // when the user runs an invalid command, the monster will still move
            allMonstersCheck(map,player);
            // checks if there is combat
            // print out the map
            if(!allMonstersIsCombat(map,player)){
                map.drawMap();
            }
        }
        if(isWorldComplete){
            return true;
        }
        return false;
    }
    //helper function for itemIsPickedUp
    public boolean checkItemPickUp(Item item,Player player){
        return item.getPosX()==player.getPosX() && item.getPosY()==player.getPosY();
    }
    //helper function for loop all items
    public void itemIsPickedUp(Item item, Player player, Map map){
        if(checkItemPickUp(item,player)){
            item.setIsPickedUp(true);
            item.itemPicked(map,player);
            item.printItemCommand();
        }
        // do nothing if thre are no items that the player encounter
    }
    // returns true if worldIsCompleted
    // returns false if worldNotYetCompleted
    public void loopAllItems( Player player, Map map){
        ArrayList<Item>items=map.getItemArray();
        if(items.size()>0){
            for(Item myItem:items){
                itemIsPickedUp(myItem,player,map);

            }
        }
    }

}

