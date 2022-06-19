/**
 * Store attributes for all entites, player, monster and item.
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
public abstract class Entity {
    private int posX;
    private int posY;
    private String symbol;
    //Overloading of Entity constructor
    // The first constructor is used for Player and Monster
    public Entity(int posX,int posY){
        setPosX(posX);
        setPosY(posY);
    }
    //The second constructor is used for Entity
    public Entity(int posX, int posY, String symbol){
        setPosX(posX);
        setPosY(posY);
        this.symbol=symbol;
    }
    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public void setPosX(int posX){
        this.posX=posX;
    }
    public void setPosY(int posY){
        this.posY=posY;
    }
    public  String getSymbol(){
        return this.symbol;
    }
    //draw the position of entity on the map
    //to be used every time after a valid movement is made for monster and player
    //used at the start of updated game for item class
    public abstract void draw(Map map);
}