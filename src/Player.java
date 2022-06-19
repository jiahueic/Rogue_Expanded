/**
 * Stores attributes for player and methods to modify them.
 * @author: Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
public class Player extends Unit{
    final static int INI_LEVEL=1;
    final static int INI_DAMAGE=INI_LEVEL+1;
    final static int INI_HEALTH=17+INI_LEVEL*3;
    private int level;
    private int damage;
    private boolean isHomeReturned;
    Player(){
        this(0,0,"anonymous",0,0);
    }
    // the formula for health is 17+level*3
    // since referenced final int cannot be passed into super constructor
    // therefore 20 is used
    Player(int posX, int posY,String name,int currentHealth, int damage){
        super(posX,posY,name,20);
        this.damage=INI_DAMAGE;
        setLevel(INI_LEVEL);
    }
    //this method is used in the loaded game world
    // if true, the monster should not move
    public void setIsHomeReturned(boolean isHomeReturned){
        this.isHomeReturned=isHomeReturned;
    }
    public boolean getIsHomeReturned(){
        return isHomeReturned;
    }
    public void attackMonster(Monster monster){
        int iniHealth=monster.getCurrentHealth();
        int attHealth=iniHealth-getDamage();
        monster.setCurrentHealth(attHealth);
    }
    public void setLevel(int level){
        this.level=level;
        setDamage();
        int newHealth=computeMaxHealth();
        setMaxHealth(newHealth);
        setCurrentHealth(newHealth);
    }
    public int getLevel(){
        return this.level;
    }

    public int computeMaxHealth(){
        return 17+getLevel()*3;
    }
    public int getDamage(){
        return this.damage;
    }
    public void setDamage(){
        this.damage=getLevel()+1;
    }
    @Override
    public String generateSymbol(){
        return super.generateSymbol().toUpperCase();
    }
    //Update position of player on the map
    public void draw (Map map){
        map.setTerrain(getPosX(),getPosY(),getSymbol());
    }


}
