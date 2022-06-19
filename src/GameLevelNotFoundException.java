/**
 * Customized exception thrown when the game file is not found. 
 * To be checked thrown and checked before other general exceptions. 
 * @author : Cheah Jia Huei, jiahueic@student.unimelb.edu.au, 1078203.
 *
 */
import java.io.FileNotFoundException;
public class GameLevelNotFoundException extends FileNotFoundException{
    public GameLevelNotFoundException(){
        super("Game level not found exception");
    }
    public GameLevelNotFoundException(String aMessage){
        super(aMessage);
    }
}