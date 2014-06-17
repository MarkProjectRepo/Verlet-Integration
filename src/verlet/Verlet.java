/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Color;
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
    final int entities = 100;
    double delta;
    double mx = -1, my = -1;
    Random r = new Random();
    ArrayList<Particle> particles = new ArrayList<>();
    ArrayList<Connecter> connectors = new ArrayList<>();
    
    public Verlet(){
        window.init();
        
        window.mainCanvas.addMouseMotionListener(new MouseListen(){
            @Override
            public void mouseMoved(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }
        
        });
        int rows = 0, columns = 0;
        boolean first = false;
        
        int startingX = 300, startingY = 500;
        
        int spacing = 10;
        
        for (int c = startingX; c < entities+startingX; c += spacing){
            for (int i = startingY; i < entities+startingY; i += spacing){
                particles.add(new Particle(i, c, particles.size()));
                if (!first){
                    columns++;
                }
            }
            rows++;
            first = true;
        }
        //rows = window.Height/14;
        //columns = window.Width/13;
        boolean firstRow = true;
        
        for (int i = 0; i <= entities/spacing*9; i += 10){
            for (int c = 0; c < columns-1; c ++){
                    connectors.add(new Connecter(particles.get(i+c), particles.get(i+c+1), i+c, i+c+1));
                    particles.get(i+c).addConnections(connectors.get(connectors.size()-1));
                    particles.get(i+c+1).addConnections(connectors.get(connectors.size()-1));
                    if (firstRow){
                        particles.get(particles.size()-1).setStationary(true);
                    }
            }
            firstRow = false;
        }
        
        for (int c = 0; c < columns; c ++){
            for (int i = c; i <= entities/spacing*9+9; i += 10){
                connectors.add(new Connecter(particles.get(c), particles.get(i), c, i));
                particles.get(i).addConnections(connectors.get(connectors.size()-1));
                particles.get(c).addConnections(connectors.get(connectors.size()-1));
            }
        }
        for (Particle p : particles){
            System.out.println(p.connections.size());
        }
        System.out.println(particles.size());
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
                //System.out.println((int)(1.0 / delta));
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
        for (Connecter c : connectors){
            c.draw(g);
        }
    }
    
    public void update(double delta){
        for (Particle p : particles){
            p.attraction(mx, my);
            p.update(delta);
        }
        for (Connecter c : connectors){
            c.update(particles.get(c.index1).x, particles.get(c.index1).y, particles.get(c.index2).x, particles.get(c.index2).y);
        }
    }
    
    public static void main(String[] args) {
        Verlet verlet = new Verlet();
        verlet.loop();
    }
    
}
