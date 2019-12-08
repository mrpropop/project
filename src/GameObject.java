import java.awt.*;

public abstract class GameObject {

   protected Handler handler;
   protected ID id;

    protected float x,y;
    protected int width, height;
    protected float velX, velY;
    protected float accX, accY;


    // We need these functions in every class that extends GameObject
    public abstract void tick();
    public abstract void render(Graphics g);
    
    public GameObject(float x, float y, int width, int height, ID id, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    }

    /*
     * Basically, we are going to be using rectangles to handle all the collisions
     *
     * Because within the library, there is a method called intersects which handles
     * if two rectangles hit each other, it then will print true
     *
     */
    protected Rectangle getBounds(){
        return new Rectangle((int)Math.round(x), (int)Math.round(y), width, height);
    }



    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }



    public void collision() {
        for(int i = 0; i< handler.objects.size(); i++) {
            GameObject gameObject = handler.objects.get(i);
            if(gameObject != null && this != gameObject){
                if (this.getBounds().intersects(gameObject.getBounds())){
                    System.out.println("An intersect");
                    if(gameObject.id == ID.BLOCK){
                        Block block = (Block) gameObject;
                        block.color = Color.red;
                    }
                }else{
                    if(gameObject.id == ID.BLOCK){
                        Block block = (Block) gameObject;
                        block.color = block.original;
                    }
                }
            }
        }
    }





}
