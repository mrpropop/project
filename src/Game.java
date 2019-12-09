import com.sun.corba.se.spi.ior.ObjectId;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

//changed
public class Game extends Canvas implements Runnable {

    public static int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
    public String title = "Game 1";


    //Instances
    private Windows window;
    private Handler handler;
    private Camera camera;
    private HUD hud;

    private Thread thread;
    private boolean isRunning = false;


    private void addListeners() {
        this.addKeyListener(new KeyInput(handler, this));
    }

    public void generateMap() {

    }

    public void spawnObjects(){
        Block  block = new Block(300,300, 25, 25,ID.BLOCK,handler);
        handler.addObject(block);
        Player player = new Player(400,400,32,32,ID.PLAYER, handler);
        handler.addObject(player);
    }

    public void init(){
        camera = new Camera(0, 0);
        handler = new Handler();
        hud = new HUD();
        generateMap();
        addListeners();
        //spawnObjects();
        SpawnBlocksWithRandomVelocities(handler);
    }



    public static void SpawnBlocksWithRandomVelocities(Handler handler){
        for(int i = 0; i< 10; i++){
            Block  block = new Block((int) (Math.random()*Game.WIDTH),(int) (Math.random()*Game.HEIGHT), 25, 25,ID.BLOCK,handler, (int) Math.round(Math.random() * 10) + 1, (byte) Constants.COLLISION_MODE_SOLID);
            //Block  block = new Block(0,0, 25, 25,ID.BLOCK,handler, (int) Math.round(Math.random() * 10) + 1, (byte) Constants.COLLISION_MODE_SOLID);
            handler.addObject(block);
        }
    }

    public Game(){
        window = new Windows(WIDTH, HEIGHT, title,this);
        init();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >=1) {
                tick();
                updates++;
                delta--;
                if (Settings.LimitFPSToTicks) {
                    render();
                }
            }
            if(!Settings.LimitFPSToTicks){
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer +=1000;
                System.out.println("FPS " + frames + " Ticks " + updates);
                updates = 0;
                frames = 0;
            }
        }
    }

    private void tick() {
        if(handler != null){
            handler.tick();
        }
        if(hud != null){
            hud.tick();
        }

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject gameObject = handler.objects.get(i);
            if (gameObject.getId().equals(ID.PLAYER)) {
                camera.tick(gameObject);
            }
        }
    }

    private void render() {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        do{
            Graphics g = bs.getDrawGraphics();
            Graphics2D g2d = (Graphics2D) g;
            //meat and bones of rendering
            drawBackground(g);
            // Camera moves to the right, things are not shifted
            g2d.translate(camera.getX(), camera.getY());
            hud.render(g);
            handler.render(g);
            g2d.translate(-camera.getX(), -camera.getY());
            //////////////////
            bs.show(); //will cause the buffer that you just drew to become the current buffer for the jframe
            g.dispose();
        }while(bs.contentsLost()); // All buffers that have been lost have to be redrawn when they are restored.

    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public synchronized void start(){
        if(isRunning){
            return;
        }
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public synchronized void stop(){
        if(!isRunning){
            return;
        }
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

}
