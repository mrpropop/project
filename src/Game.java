import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
    public String title = "Game 1";


    private Handler handler;
    private HUD hud;

    private Thread thread;
    private boolean isRunning = false;


    public void init(){
        handler = new Handler();
        hud = new HUD();

      for(int i = 0; i< 2; i++){
          Block  block = new Block(300,300, 25, 25,ID.BLOCK,handler);
          handler.addObject(block);
      }
    }

    public Game(){
        init();
        new Windows(WIDTH, HEIGHT, title,this);
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
            if(delta >=1){
                tick();
                updates++;
                delta--;
            }
            render();
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
    }

    private void render() {

        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        do{
            Graphics g = bs.getDrawGraphics();
            //meat and bones of rendering
            drawBackground(g);
            hud.render(g);
            handler.render(g);
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
