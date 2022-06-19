/**
 * General class to be inherited by Player and Monster.
 * Stores basic attributes of name, currentHealth and maxHealth.
 * Generates symbol based on name of Unit. 
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */

public abstract class Unit extends Entity{
    private String name;
    private int currentHealth;
    private int maxHealth;
    private String symbol;
    public Unit(int posX, int posY, String name, int currentHealth){
        super(posX, posY);
        setName(name);
        setCurrentHealth(currentHealth);
        setMaxHealth(currentHealth);
        this.symbol=generateSymbol();
    }

    public String getName(){
        return this.name;
    }
    public int getCurrentHealth(){
        return this.currentHealth;
    }
    public void setCurrentHealth(int currentHealth){
        this.currentHealth=currentHealth;
    }
    public int getMaxHealth(){
        return this.maxHealth;
    }
    //This method will be overriden in Player to get uppercase
    public String generateSymbol(){
        char firstCharacter=getName().charAt(0);
        String lowerCaseName=String.valueOf(firstCharacter);
        return lowerCaseName.toLowerCase();
    }
    //when moving, always replace the previous position with background symbol
    // helper function for moveNorth(), moveSouth(), moveEast(), moveWest()

    public void moved(Map map){
        map.setTerrain(getPosX(),getPosY(),map.getBackground());

    }
    // Overloading of moved method
    public void moved(Map map, int posX, int posY){
        map.setTerrain(posX,posY,map.getBackground());
    }



    //Checks for "w" movement, which is North for player
    // Originally, this was coded in World, but is now encapsulated in Unit such that
    // these movement commands can be used for default and loaded game world
    // setNorth is helper function for moveNorth
    public void setNorth(Map map){
        int newPosY=getPosY()-1;
        setPosY(newPosY);
    }
    // used for default game world
    public void moveNorth(Map map){
        moved(map);
        setNorth(map);
    }
    //Checks for "a" movement, which is West for player
    // serWest is helper function for moveWest
    public void setWest(Map map){
        int newPosX=getPosX()-1;
        setPosX(newPosX);
    }
    // used in default game world
    public void moveWest(Map map){
        moved(map);
        setWest(map);
    }
    //Checks for "s" movement, which is South for player
    // setSouth is helper function for moveSouth
    public void setSouth(Map map){
        int newPosY=getPosY()+1;
        setPosY(newPosY);
    }
    //used in default game world
    public void moveSouth(Map map){
        moved(map);
        setSouth(map);
    }

    // setEast is helper function for moveEast
    public void setEast(Map map){
        int newPosX=getPosX()+1;
        setPosX(newPosX);
    }
    public void moveEast(Map map){
        moved(map);
        setEast(map);
    }
    public void setMaxHealth(int maxHealth){
        this.maxHealth=maxHealth;
    }
    public void setName(String name){
        this.name=name;
        String newSymbol=generateSymbol();
        setSymbol(newSymbol);
    }

    public String getSymbol(){
        return this.symbol;
    }
    public void setSymbol(String symbol){
        this.symbol=symbol;
    }
}