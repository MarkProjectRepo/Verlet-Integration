
package verlet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import listeners.MouseClickListen;
import listeners.MouseListen;

/**
 *
 * @author Mark
 */
public class Screensaver {
    
    
    Window window = new Window();
    final int entities = 50000;
    double delta;
    double mx = -1, my = -1;
    Random r = new Random();
    ArrayList<Particle> particles = new ArrayList<>();
    
    
    
    public Screensaver(){
        window = new Window();
        for (int i = 0; i < entities; i++){
            particles.add(new Particle(r.nextInt(window.Width), r.nextInt(window.Height), i));
        }
        
        window.init();
        
        window.mainCanvas.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        
        });
        
        window.mainCanvas.addMouseMotionListener(new MouseListen(){
            
            @Override
            public void mouseDragged(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }
            
            @Override
            public void mouseMoved(MouseEvent e){
                mx = e.getX();
                my = e.getY(); 
            }
            
        });

    }
    
    public void loop(){
        Graphics g = window.bufferStrategy.getDrawGraphics();
        
        long time1 = System.nanoTime();
        long time2;
        
        long optimalFPS = 60;
        long sToNs = 1000000000;
        long interval = sToNs / optimalFPS;
        while (true){
            
            time2 = System.nanoTime();
            
            if (time2 - time1 >= interval) {
                delta = (time2 - time1) / (double) sToNs;
                System.out.println((int)(1.0 / delta));
                update(delta);
                time1 = time2;
            }
        
            g.clearRect(0, 0, window.Width, window.Height);
            paint(g);
            window.bufferStrategy.show();
        }
    }
    
    public void paint(Graphics g){
        for (Particle p : particles){
            p.draw(g);
        }
    }
    
    public void update(double delta){
        for (Particle p : particles){
            p.update(delta);
            p.attraction(mx, my);
        }
    }
    
    public static void main(String[] args){
        Screensaver saver = new Screensaver();
        saver.loop();
    }
}
