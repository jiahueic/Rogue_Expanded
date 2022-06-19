/**
 * Store attributes for monster and methods to modify them.
 * Damage attribute is the damage of monster towards player.
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
//needs a method to check whether the player is within 2 cells away from monster
//need to make monster disappear from map if player wins
public class Monster extends Unit {
    private int damage;
    private boolean hasDisapperead;
    private int prevPosX;
    private int prevPosY;
    Monster(){
        this(0,0,"anonymous",0,0);
    }
    Monster(int posX, int posY, String name,int currentHealth, int damage){
        super(posX,posY,name,currentHealth);
        this.damage=damage;
        setCurrentHealth(currentHealth);
        setMaxHealth(currentHealth);
        int[]monsterPrevPos= new int[2];
        monsterPrevPos[0]=posX;
        monsterPrevPos[1]=posY;
        setPrevPosX(posX);
        setPrevPosY(posY);

    }
    public void setDamage(int damage){
        this.damage=damage;
    }

    public void attackPlayer(Player player){
        // player's health level before attack
        int iniHealth=player.getCurrentHealth();
        // player's health after attack by monster
        int attHealth= iniHealth-getDamage();
        player.setCurrentHealth(attHealth);
    }
    // Used when player defeats the monster
    public void monsterDisappear(Map map, Player player){
        map.setTerrain(getPosX(),getPosY(),map.getBackground());
        this.hasDisapperead=true;
        // if this method is executed, return true
    }
    public boolean getHasDisappeared(){
        return this.hasDisapperead;
    }
    //getSymbol() is inherited from Entity class
    public void draw(Map map){
        int monsPosX=getPosX();
        int monsPosY=getPosY();
        String[][]terrain=map.getTerrain();
        boolean isBackground=terrain[monsPosY][monsPosX].equals(map.getBackground());
        if(isBackground){
            map.setTerrain(monsPosX,monsPosY,getSymbol());
        }
    }
    public int getDamage(){
        return this.damage;
    }
    //All monster within the monsters arraylist in the map class need to be checked if they are within
    // 2 units distance of the player
    // This is render after the next command
    public int computePosXDiff(Player player){
        return getPosX()-player.getPosX();
    }
    public int computePosYDiff(Player player){
        return getPosY()-player.getPosY();
    }
    public void setPrevPosX(int posX){
        this.prevPosX=posX;
    }
    public void setPrevPosY(int posY){
        this.prevPosY=posY;
    }
    public int getPrevPosX(){
        return this.prevPosX;
    }
    public int getPrevPosY(){
        return this.prevPosY;
    }

    // cell distance between player(after movement) and monster
    public boolean checkTwoUnits(Player player){
        final int UPPER_BOUNDARY=2;
        final int LOWER_BOUNDARY=0;
        final int SQUARE=2;
        int absPosXDiff=Math.abs(computePosXDiff(player));
        int absPosYDiff=Math.abs(computePosYDiff(player));
        boolean firstStatement=absPosXDiff>=LOWER_BOUNDARY && absPosXDiff<=UPPER_BOUNDARY;
        boolean secondStatement=absPosYDiff>=LOWER_BOUNDARY && absPosYDiff<=UPPER_BOUNDARY;
        return firstStatement && secondStatement;

    }
    // draw monster movement
    // this should be rendered in the next round of player movements
    public void drawMonsterMovement(Map map){
        moved(map,getPrevPosX(),getPrevPosY());
        // if the area is traversable, draw the monster on the map
        draw(map);
        // this is to render the monster on the terrain cell, but not print the map
    }
    // This method is only executed if checkTwoUnits is true
    // need to return monsterprevpos such that monster can move from old to new pos
    public void monsterMovement(Player player,Map map){
        // if the x position of monster is greater than the player (positive difference), then the monster moves to the West
        // if the x position of monster is smaller than the player (negative difference), then the monster moves to the East
        // if the y position of monster is greater than the player (positive difference), then the monster moves to the North
        // if the y position of monster is smaller than the player (negative difference), then the monster moves to the South
        final int ZERO=0;
        int posXDiff=computePosXDiff(player);
        int posYDiff=computePosYDiff(player);
        int[] monsterPrevPos;
        boolean isTraversable;
        boolean isInBoundary;
        if(posXDiff>=ZERO){
            if (posXDiff==ZERO){
                monsterNorthAndSouth(player,map,posYDiff,ZERO);
            }
            else{
                // save a copy of the old position firsr before setting the new position

                setPrevPosX(getPosX());
                setPrevPosY(getPosY());
                //set the monster to a new direction
                setWest(map);
                isTraversable=map.checkTraversable(map.getTerrain(),getPosX(),getPosY());
                isInBoundary=map.checkMapBoundaries(getPosX(),getPosY());
                if(!(isInBoundary&&isTraversable)){
                    //move back to original position by moving East
                    setEast(map);
                    //try moving to North or South
                    monsterNorthAndSouth(player,map,posYDiff,ZERO);
                }
            }
        }
        else if(posXDiff<ZERO){
            setPrevPosX(getPosX());
            setPrevPosY(getPosY());
            setEast(map);
            isTraversable=map.checkTraversable(map.getTerrain(),getPosX(),getPosY());
            isInBoundary=map.checkMapBoundaries(getPosX(),getPosY());
            if(!(isInBoundary&&isTraversable)){
                //move back to original position by moving West
                setWest(map);
                //try moving to North or South
                monsterNorthAndSouth(player,map,posYDiff,ZERO);
            }
        }
    }

    public void monsterNorthAndSouth(Player player, Map map, int posYDiff, final int ZERO){
        final int POSX_INDEX=0;
        final int POSY_INDEX=1;
        boolean isTraversable;
        boolean isInBoundary;
        int[] monsterPrevPos;
        if(posYDiff>=ZERO){
            setPrevPosX(getPosX());
            setPrevPosY(getPosY());
            setNorth(map);
            isTraversable=map.checkTraversable(map.getTerrain(),getPosX(),getPosY());
            isInBoundary=map.checkMapBoundaries(getPosX(),getPosY());
            if(!(isInBoundary&&isTraversable)){
                //move back to original position by moving South
                setSouth(map);
            }
        }
        else if(posYDiff<ZERO){
            setPrevPosX(getPosX());
            setPrevPosY(getPosY());
            setSouth(map);
            isTraversable=map.checkTraversable(map.getTerrain(),getPosX(),getPosY());
            isInBoundary=map.checkMapBoundaries(getPosX(),getPosY());
            if(!(isInBoundary&&isTraversable)){
                //move back to original position by moving North
                setNorth(map);
            }
        }
    }






}
