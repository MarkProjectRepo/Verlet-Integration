/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import listeners.MouseListen;

/**
 *
 * @author Mark
 */
public class Verlet {

    Window window = new Window();
    final int entities = 5000;
    double delta;
    double mx = -1, my = -1;
    
    ArrayList<Particle> particles = new ArrayList<>();
    
    public Verlet(){
        window.init();
        
        window.mainCanvas.addMouseMotionListener(new MouseListen(){
            @Override
            public void mouseMoved(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }
        
        });
        Random r = new Random();
        for (int i = 0; i < entities; i++){
            particles.add(new Particle(r.nextInt(window.Height), r.nextInt(window.Width)));
        }
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
            p.attraction(mx, my);
            p.update(delta);
        }
    }
    
    public static void main(String[] args) {
        Verlet verlet = new Verlet();
        verlet.loop();
    }
    
}
