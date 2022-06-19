/**
 * Store 2D array for map for which the dimensions are set
 * when "start" or "start <gamefilename> is called in World class"
 * Checks whether the area in the 2D array is traversable.
 * setTerrain is used as helper function for draw(render) in Monster, Player and Item. 
 * Stores arraylist for monsters and items. 
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
import java.util.ArrayList;
public class Map{
    final static String BACKGROUND_SYMBOL=".";
    private String[][] terrain;
    private int width;
    private int height;
    private String background;
    private ArrayList<Monster>monsters=new ArrayList<Monster>();
    private ArrayList<Item>items=new ArrayList<Item>();
    private Monster combatMonster;

    //this constructor is for the default game world
    public Map(int width, int height){
        this.height=height;
        this.width=width;
        this.background=BACKGROUND_SYMBOL;
    }

    public boolean searchWarpStone(){
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if(terrain[i][j].equals("@")){
                    return false;
                }
            }
        }
        return true;
    }

    //This is to check whether the terrain is traversable for the loaded game file version
    public boolean checkTraversable(String[][]terrain, int posX, int posY){
        String currentBlock=terrain[posY][posX];
        if(currentBlock.equals("#")){
            return false;
        }
        else if(currentBlock.equals("~")){
            return false;
        }
        return true;
    }
    public void setCombatMonster(Monster monster){
        this.combatMonster=monster;
    }
    public Monster getCombatMonster(){
        return this.combatMonster;
    }
    public boolean checkMapBoundaries(int posX,int posY){
        if(posX>=0 && posX<width){
            if(posY>=0 && posY<height){
                return true;
            }
            return false;
        }
        return false;
    }
    //need to think how to deal with monster and item at the same position
    //maybe set a boolean double array gotItem in item class
    public void drawMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(terrain[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    public String[][] getTerrain(){
        return this.terrain;
    }
    public String getTerrainCell(int posX, int posY){
        return terrain[posY][posX];
    }
    public void setTerrain(int posX, int posY, String symbol){
        terrain[posY][posX]=symbol;
    }
    public String getBackground(){
        return this.background;
    }
    //in updated games, there may be multiple monsters
    public void addMonster(Monster monster){
        monsters.add(monster);
    }

    public ArrayList<Monster> getMonsterArray(){
        return monsters;
    }
    // this is to reset the width after reading the data from the loaded game file
    public void setWidth(int width){
        this.width=width;
    }
    // this is to reset the height after reading data from the loaded game file
    public void setHeight(int height){
        this.height=height;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    //this method is only used after start <gamefile> or start command is used
    public void setArraySize(int height,int width){
        terrain=new String[height][width];
    }
    //setArraySize method should be used with setWidth and setHeight method

    //there may be multiple items in the loaded game world
    public void addItem(Item item){
        items.add(item);
    }

    public ArrayList<Item> getItemArray(){
        return this.items;
    }
    // fillTerrain method is only used in default game world
    public void fillTerrain(){
        //position y
        for(int i=0;i<height;i++){
            //position x
            for(int j=0;j<width;j++){
                setTerrain(j,i,BACKGROUND_SYMBOL);
            }
        }
    }
}