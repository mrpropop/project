import java.awt.*;
import java.util.ArrayList;
import java.util.List;
//changed
public class Handler {

    /**
     * This handler class is going to maintain or update and render all objects in
     * our room
     *
     * We have different objects.. Since we are going to have more than one object
     * in the game, we need to process and handle each one of those objects.
     *
     * So this class is going to loop through all of our objects in the game, and
     * individually update them, and render them to the screen
     *
     */

    List<GameObject> objects;

    public int playerSpeed = 10;

    public Handler(){
        this.objects = new ArrayList<GameObject>();
    }


    public void tick(){
        for(int i = 0; i< objects.size(); i++){
            objects.get(i).tick();
        }
    }

    public void render(Graphics g){
        for(int i = 0; i< objects.size(); i++){
            objects.get(i).render(g);
        }
    }

    public void addObject(GameObject object){
        this.objects.add(object);
    }

    public void removeObject(GameObject object){
        this.objects.remove(object);
    }

}
