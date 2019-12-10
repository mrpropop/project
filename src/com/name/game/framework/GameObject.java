package com.name.game.framework;

import com.name.game.Constants;
import com.name.game.Utilities;
import com.name.game.window.Handler;

import java.awt.*;


public abstract class GameObject {

    protected Handler handler;
    protected Identifier identifier;

    protected boolean falling = true;
    protected boolean jumping = false;

    protected float x,y;
    protected int width, height;
    protected float velX, velY;
    protected float accX, accY;
    protected float mass;
    protected float gravity;
    protected byte collision_Mode;


    // We need these functions in every class that extends com.name.game.framework.GameObject
    public abstract void tick();
    public abstract void render(Graphics g);

    public GameObject(float x, float y, int width, int height, Identifier identifier, Handler handler){
        this(x, y, width, height, identifier, handler,1, Constants.COLLISION_MODE_SOLID);
    }

    public GameObject(float x, float y, int width, int height, Identifier identifier, Handler handler, float mass, byte CollisionMode){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.identifier = identifier;
        this.handler = handler;
        this.mass = mass;
        this.collision_Mode = CollisionMode;
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



    public float getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }


    public float getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
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


    public float getVelX(){
        return velX;
    }

    public void setVelX(int velX){
        this.velX = velX;
    }

    public float getVelY(){
        return velY;
    }

    public void setVelY(int velY){
        this.velY = velY;
    }


    public Identifier getIdentifier(){
        return identifier;
    }


    public Rectangle getBoundsBottom() {
        return new Rectangle((int) x + width / 4, (int) y + height / 2, width / 2, height / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) x + width / 4, (int) y, width / 2, height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) x + width - 5, (int) y + 5, 5, height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, 5, height - 10);
    }


    public boolean collision() {
        int Max = 0;
        for(int i = 0; i< handler.getObjects().size(); i++) {
            if(this.equals(handler.getObjects().get(i))){
                Max = i;
                break;
            }
        }
        boolean collided = false;
        for(int i = 0; i < Max; i++) {
            GameObject gameObject = handler.getObjects().get(i);
            if(gameObject != null && this != null && this != gameObject){
                if (this.getBounds().intersects(gameObject.getBounds())) {
                    collided = true;
                    if(collided) {
                        double[] Old_X_vels = new double[] {velX, gameObject.velX};
                        double[] Old_Y_vels = new double[] {velY, gameObject.velY};
                        double[] X_vels = calculateNewVelocities(mass, velX, gameObject.mass, gameObject.velX);
                        double[] Y_vels = calculateNewVelocities(mass, velY, gameObject.mass, gameObject.velY);
                        velX = (float) X_vels[0];
                        gameObject.velX = (float) X_vels[1];
                        velY = (float) Y_vels[0];
                        gameObject.velY = (float) Y_vels[1];
                        System.out.println("Bounce happened: What changed:");
                        System.out.println("x1: (" + Old_X_vels[0] + "->" + X_vels[0]+")");
                        System.out.println("x2: (" + Old_X_vels[1] + "->" + X_vels[1]+")");
                        System.out.println("y1: (" + Old_Y_vels[0] + "->" + Y_vels[0]+")");
                        System.out.println("y2: (" + Old_Y_vels[1] + "->" + Y_vels[1]+")");
                        System.out.println("Masses: "  + mass + " " + gameObject.mass + ".");
                        System.out.println("--------------");
                        adjustPositions(this, gameObject);
                    }
                }
            }
        }
        return collided;
    }

    public static void adjustPositions(GameObject object, GameObject gameObject){
        float object_getCenterX = object.x + object.width/2.0f;
        float gameObject_getCenterX = gameObject.x + gameObject.width/2.0f;
        float object_getCenterY = object.y + object.height/2.0f;
        float gameObject_getCenterY = gameObject.y + gameObject.height/2.0f;

        float center_CenterX =  (object_getCenterX+gameObject_getCenterX)/2.0f;
        float center_CenterY =  (object_getCenterY+gameObject_getCenterY)/2.0f;

        float SafetyFactor = 1.01f;

        float MoveX = Math.abs(object_getCenterX - center_CenterX); // supposed to be 10 with object width 20, gameobject = 4, object = 1;
        float MoveDistancePerMassX = 0;                                                         //but lets say its 6
        float MoveDistancePerMassY = 0;                                                         //but lets say its 6
        if(MoveX < (object.width + gameObject.width)/4.0f){
            float MoveDistanceX = 2 * ((object.width + gameObject.width)/4.0f - MoveX);
            MoveDistancePerMassX = MoveDistanceX / (object.mass + gameObject.mass);
        }
        float MoveY = Math.abs(object_getCenterY - center_CenterY);
        if(MoveY < (object.height + gameObject.height)/4.0f){
            float MoveDistanceY = 2 * ((object.height + gameObject.height)/4.0f - MoveY);
            MoveDistancePerMassY = MoveDistanceY / (object.mass + gameObject.mass);
        }


        if((object_getCenterX - center_CenterX) < (object_getCenterY - center_CenterY)) {
            if (Math.abs(object_getCenterX - center_CenterX) < Math.round(object.width / 2.0D)) {
                if (object_getCenterX > center_CenterX) {
                    object.x += SafetyFactor * MoveDistancePerMassX * gameObject.mass;
                } else {
                    object.x -= SafetyFactor * MoveDistancePerMassX * gameObject.mass;
                }
            }
            if (Math.abs(gameObject_getCenterX - center_CenterX) < Math.round(gameObject.width / 2.0)) {
                if (gameObject_getCenterX > center_CenterX) {
                    gameObject.x += SafetyFactor * MoveDistancePerMassX * object.mass;
                } else {
                    gameObject.x -= SafetyFactor * MoveDistancePerMassX * object.mass;
                }
            }
        }else {
            if (Math.abs(object_getCenterY - center_CenterY) < Math.round(object.height / 2.0D)) {
                if (object_getCenterY > center_CenterY) {
                    object.y += SafetyFactor * MoveDistancePerMassY * gameObject.mass;
                } else {
                    object.y -= SafetyFactor * MoveDistancePerMassY * gameObject.mass;
                }
            }

            if (Math.abs(gameObject_getCenterY - center_CenterY) < Math.round(gameObject.height / 2.0D)) {
                if (gameObject_getCenterY > center_CenterY) {
                    gameObject.y += SafetyFactor * MoveDistancePerMassY * object.mass;
                } else {
                    gameObject.y -= SafetyFactor * MoveDistancePerMassY * object.mass;
                }
            }
        }



    }

    public static double[] calculateNewVelocities(GameObject object1, GameObject object2, boolean X){
        if(object1.collision_Mode != Constants.COLLISION_MODE_SOLID || object2.collision_Mode != Constants.COLLISION_MODE_SOLID ){
            return null;
        }
        if(X) {
            return calculateNewVelocities(object1.mass, object1.velX, object2.mass, object2.velX);
        }else{
            return calculateNewVelocities(object1.mass, object1.velY, object2.mass, object2.velY);
        }
    }


    public static double[] calculateNewVelocities ( double m1, double v1, double m2, double v2){
        double Tb = m1 * v1 + m2 * v2;
        double Kb = 0.5D * m1 * Math.pow(v1, 2) + 0.5D * m2 * Math.pow(v2, 2);
        double a = Math.pow(m1, 2) + m1 * m2;
        double b = (-2.0D * Tb * m1);
        double c = (Math.pow(Tb, 2) - 2 * m2 * Kb);
        double d = Math.pow(b, 2) - 4.0D * a * c;
        double result1_1 = (-1.0D * b + Math.sqrt(d)) / ((double) 2.0 * a);
        double result2_1 = (Tb - m1 * result1_1) / ((double) m2);

        double result1_2 = (-1.0D * b - Math.sqrt(d)) / ((double) 2.0 * a);
        double result2_2 = (Tb - m1 * result1_2) / ((double) m2);

        if (Utilities.equalsWithinEpsilon(result1_1, v1) && Utilities.equalsWithinEpsilon(result2_1, v2)) {
            return new double[]{result1_2, result2_2};
        } else {
            return new double[]{result1_1, result2_1};
        }
    }

}
