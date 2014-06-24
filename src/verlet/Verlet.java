/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
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
    double spacing = 5;
    ArrayList<Grid> grid = new ArrayList<>();
    
    
    
    
    public Verlet(){
        
        grid.add(new Grid(100, 0, 50, 50, 5));
        
        window.init();
        
        window.mainCanvas.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (part != null){
                    if (e.getKeyCode() == KeyEvent.VK_SPACE){
                        part.stationary = !part.stationary;
                    }
                    
                }
                if (e.getKeyCode() == KeyEvent.VK_C){
                    grid1.particles.stream().forEach((Particle p) -> {
                        p.stationary = false;
                    });
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            
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
        
        window.mainCanvas.addMouseListener(new MouseClickListen(){
            
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (part == null){
                    for (Particle p : grid1.particles){
                        if (e.getX() > p.x-10 && e.getX() < p.x+10 && e.getY() > p.y-10 && e.getY() < p.y+10){
                            p.setColor(Color.blue);
                            part = p;
                            
                        }
                    }
                }else{
                    part.setColor(Color.white);
                    part = null;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e){
                part = null;
            }
        });
        
        
        //this.createGrid(0, 0, 10, 10, 5, 5);
        
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
        for (Particle p : grid1.particles){
            p.draw(g);
        }
        for (Connecter c : grid1.connectors){
            c.draw(g);
        }
    }
    
    public void update(double delta){
        for (Particle p : grid1.particles){
            p.update(delta);
            if (part != null){
                part.attraction(mx, my);
            }
        }
    }
    
    public static void main(String[] args) {
        Verlet verlet = new Verlet();
        verlet.loop();
    }
    
}
