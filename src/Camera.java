
//changed
public class Camera {

    float x, y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject gameObject) {
        // x = -gameObject.getX() + Game.WObjectIdTH / 2; // tweening algorithm
        int targetX = (int) (-gameObject.x + Game.WIDTH / 2);
        //	int targetY = (int) (-gameObject.getY() + (int) Game.HEIGHT - 200);
        x += (targetX - x) * 0.1;
        //	y += (targetY - 2 * y) * 0.1;

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
