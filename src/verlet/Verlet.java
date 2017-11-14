/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import java.io.*;
import listeners.*;

/**
 *
 * @author Mark
 */


class ImageDestructor{
    
    BufferedImage imgs[];
    
    public ImageDestructor(int rows, int cols) throws IOException{
        File fimage = new File("Canada.png");
        
        FileInputStream fis = new FileInputStream(fimage);
        
        BufferedImage image = ImageIO.read(fis);
        
        int chunks = rows * cols;

        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        
        imgs = new BufferedImage[chunks]; //Image array to hold image chunks
        
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        
    }
    
    
}
    

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
    Grid grid = new Grid(100, 0, 150, 150, 5);
    
    
    
    
    public Verlet(){
        
        window.init();
        
        window.mainCanvas.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (part != null){
                    if (e.getKeyCode() == KeyEvent.VK_SPACE){
                        part.stationary = !part.stationary;
                    }
                    
                }
                if (e.getKeyCode() == KeyEvent.VK_C){
                    for (Particle p : grid.getParticles()) {
                        p.stationary = false;
                    }
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
                    for (Particle p : grid.getParticles()){
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
        grid.getParticles().stream().forEach((p) -> {
            p.draw(g);
        });
        grid.getConnecters().stream().forEach((c) -> {
            c.draw(g);
        });
    }
    
    public void update(double delta){
        grid.getParticles().stream().map((p) -> {
            p.update(delta);
            return p;
        }).filter((_item) -> (part != null)).forEach((_item) -> {
            part.attraction(mx, my);
        });
    }
    
    public static void main(String[] args) {
        Verlet verlet = new Verlet();
        verlet.loop();
    }
    
}
