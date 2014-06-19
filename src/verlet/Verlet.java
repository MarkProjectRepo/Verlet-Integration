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
import listeners.*;

/**
 *
 * @author Mark
 */
public class Verlet {

    Window window = new Window();
    //final int entities = 100;
    double delta;
    double mx = -1, my = -1;
    Random r = new Random();
    ArrayList<Particle> particles = new ArrayList<>();
    ArrayList<Connecter> connectors = new ArrayList<>();
    boolean pressed = false;
    Particle part = null;
    
    public Verlet(){
        window.init();
        
        window.mainCanvas.addMouseMotionListener(new MouseListen(){
            
            @Override
            public void mouseDragged(MouseEvent e) {
            }
            
            @Override
            public void mouseMoved(MouseEvent e){
                if (part != null){
                    mx = e.getX();
                    my = e.getY();
                }
            }
            
        });
        
        window.mainCanvas.addMouseListener(new MouseClickListen(){
            @Override
            public void mousePressed(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (part == null){
                    for (Particle p : particles){
                        if (e.getX() > p.x-3 && e.getX() < p.x+3 && e.getY() > p.y-3 && e.getY() < p.y+3){
                            p.setColor(Color.blue);
                            part = p;
                        }
                    }
                }else{
                    part.setColor(Color.white);
                    part = null;
                }
            }
        });
        
        int rows = 0, columns = 0;
        boolean first = false;
        
        int startingX = 100, startingY = 0;
        
        int width = 100, height = 10;
        int spacingx = 10, spacingy = 10;
        
        for (int i = 0; i < width*height; i++){
            int column = i % width;
            int row = i/width;
            particles.add(new Particle(startingX+spacingx*column, startingY+spacingy*row, i));
            if (i % width != 0){
                connectors.add(new Connecter(particles.get(i-1), particles.get(i)));
                particles.get(i-1).addConnections(connectors.get(connectors.size()-1));
                particles.get(i).addConnections(connectors.get(connectors.size()-1));
            }
            if (i >= width){
                connectors.add(new Connecter(particles.get(i-width), particles.get(i)));
                particles.get(i-width).addConnections(connectors.get(connectors.size()-1));
                particles.get(i).addConnections(connectors.get(connectors.size()-1));
            }else{
                 particles.get(i).setStationary(true);
            }
        }
        for (Particle p : particles){
            System.out.println(p.connections.size());
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
        for (Connecter c : connectors){
            c.draw(g);
        }
    }
    
    public void update(double delta){
        for (Particle p : particles){
            p.update(delta);
            if (part != null){
                part.attraction(mx, my);
            }
            //p.attraction(mx, my);
        }
    }
    
    public static void main(String[] args) {
        Verlet verlet = new Verlet();
        verlet.loop();
    }
    
}
