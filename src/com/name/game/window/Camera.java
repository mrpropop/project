package com.name.game.window;

import com.name.game.Settings;
import com.name.game.framework.GameObject;
import com.name.game.framework.Identifier;
import com.name.game.window.Game;

//changed
public class Camera {

    float x, y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject gameObject) {
        if(gameObject == null){
            System.err.println("There is no player in the map to be followed by the camera..");
            return;
        }
        // x = -gameObject.getX() + com.name.game.window.Game.WIDTH / 2; // tweening algorithm
        if(Settings.horizontalCameraEnabled) {
            int targetX = (int) (-gameObject.getX() + Game.WIDTH / 2);
            x += (targetX - x) * 0.1;
        }

        if(Settings.verticalCameraEnabled) {
            int targetY = (int) (-gameObject.getY() + (int) Game.HEIGHT - 200);
            y += (targetY - 2 * y) * 0.1;
        }

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
