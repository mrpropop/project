package com.name.game.window;

import com.name.game.Constants;
import com.name.game.Settings;
import com.name.game.framework.GameObject;
import com.name.game.framework.Identifier;
import com.name.game.framework.KeyInput;
import com.name.game.objects.Block;
import com.name.game.objects.Player;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.awt.*;
import java.awt.image.BufferStrategy;




public class Game extends Canvas implements Runnable {

   public static enum State {
        MENU, GAME, SHOP, HELP, END;
    }

    public static int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
    public String title = "Game 1";


    //Instances
    private Windows window;
    private Handler handler;
    private Camera camera;
    private HUD hud;
    private Menu menu;

    private Thread thread;
    private boolean isRunning = false;
    private boolean paused = false;
    public State gameState = State.MENU;



    public void init(){
        camera = new Camera(0, 0);
        handler = new Handler();
        menu = new Menu(this);
        hud = new HUD();
        addListeners();
    }

    public void reset(){
        handler.getObjects().clear();
        hud.reset();
    }

    public void prepareGame(){
        generateMap();
        spawnObjects();
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


    public Game(){
        window = new Windows(WIDTH, HEIGHT, title,this);
        init();
    }



    @Override
    public void run() {
        this.requestFocus(); // So that I don't need to click on the screen to have a keyboard control input, it just happens automatically
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = Math.pow(10, 9)/amountOfTicks;
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
                if (Settings.limitFPSToTicks) {
                    frames++;
                    render();
                }
            }
            if(!Settings.limitFPSToTicks){
                frames++;
                render();
            }


            if(System.currentTimeMillis() - timer > 1000){
                timer +=1000;
                System.out.println("FPS " + frames + " Ticks " + updates);
                updates = 0;
                frames = 0;
            }
        }
    }

    // updates the game (Movement, shooting system, and updating coordinates)
    private void tick() {
        if (gameState == State.GAME) {
            if(!paused){
                handler.tick();
                hud.tick();
                camera.tick(getPlayer());
                //if the game is over
                if (hud.getCurrentHealth() <= 0) {
                    handler.getObjects().clear();
                    gameState = State.END;
                }
            }
        } else if (gameState == State.MENU || gameState == State.HELP || gameState == State.END) {
            menu.tick();
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

            if (paused) {
                g.setColor(Color.WHITE);
                g.drawString("Paused!", 100, 100);
            }

            if (gameState == State.GAME) {
                //Camera moves to the right, things are not shifted
                g2d.translate(camera.getX(), camera.getY());
                hud.render(g);
                handler.render(g);
                g2d.translate(-camera.getX(), -camera.getY());
            } else if (gameState == State.SHOP) {

            } else if (gameState == State.MENU || gameState == State.HELP || gameState == State.END) {
                menu.render(g);
            }

            bs.show(); //will cause the buffer that you just drew to become the current buffer for the jframe
            g.dispose();

        }while(bs.contentsLost()); // All buffers that have been lost have to be redrawn when they are restored.

    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void addListeners() {
        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new Menu(this));
    }

    public void generateMap() {

    }

    public void spawnObjects(){
        Player player = new Player(400,400,32,32,Identifier.PLAYER, handler);
        handler.addObject(player);
        spawnBlocksWithRandomVelocities();
    }

    public void spawnBlocksWithRandomVelocities(){
        for(int i = 0; i< 3; i++){
            Block block = new Block((int) (Math.random()*Game.WIDTH),(int) (Math.random()*Game.HEIGHT), 25, 25,Identifier.BLOCK,handler, (int) Math.round(Math.random() * 10) + 1, (byte) Constants.COLLISION_MODE_SOLID);
            //com.name.game.objects.Block  block = new com.name.game.objects.Block(0,0, 25, 25,ID.BLOCK,handler, (int) Math.round(Math.random() * 10) + 1, (byte) com.name.game.Constants.COLLISION_MODE_SOLID);
            handler.addObject(block);
        }
    }


    private GameObject getPlayer() {
        for (int i = 0; i < handler.getObjects().size(); i++) {
            GameObject gameObject = handler.getObjects().get(i);
            if (gameObject.getIdentifier().equals(Identifier.PLAYER)) {
                return gameObject;
            }
        }
        return null;
    }

    public HUD getHUD(){
        return hud;
    }

    public boolean isPaused(){
        return paused;
    }

    public void setPaused(boolean paused){
        System.out.println("hehehehe");
        this.paused = paused;
    }

    public Handler getHandler(){
        return handler;
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

}
