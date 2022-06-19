/**
 * Store attributes for Item. 
 * Checks whether item is picked up and prints relative commands.
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
public class Item extends Entity {
    private boolean isPickedUp;
    public Item(int posX, int posY, String symbol){
        super(posX,posY,symbol);
    }
    public void setIsPickedUp(boolean isPickedUp){
        this.isPickedUp=isPickedUp;
    }
    public boolean getIsPickedUp(){
        return this.isPickedUp;
    }
    // returns isWorldComplete boolean
    public void printItemCommand(){
        String itemSymbol=super.getSymbol();
        if(itemSymbol.equals("+")){
            System.out.println("Healed!");
        }
        else if(itemSymbol.equals("^")){
            System.out.println("Attack up!");
        }
        else if(itemSymbol.equals("@")){
            System.out.println("World complete! (You leveled up!)");
            System.out.println();
            System.out.println("(Press enter key to return to main menu)");
        }
    }
    //item disappear from map and symbol is replaced by player
    // need to remove item from item arraylist as well
    public void itemPicked(Map map, Player player){
        map.setTerrain(getPosX(),getPosY(),player.getSymbol());

    }
    //update the render of item on the map
    public void draw(Map map){
        map.setTerrain(getPosX(),getPosY(),getSymbol());
    }
}