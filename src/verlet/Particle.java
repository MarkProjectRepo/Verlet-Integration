/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Particle {
    public double x, y, ox, oy;
    public double vx, vy;
    public int identifier;
    public boolean stationary = false;
    
    ArrayList<Connecter> connections = new ArrayList<Connecter>();
    
    Window window = new Window();
    
    public Particle(double x, double y, int identifier){
        this.x = this.ox = x;
        this.y = this.oy = y;
        this.identifier = identifier;
    }
    
    public void attraction(double x, double y){
        if (x != -1 && !this.stationary){
            double dx = x - this.x;
            double dy = y - this.y;
            double distance = Math.sqrt(dx * dx + dy * dy);
            this.x += (dx / distance);
            this.y += (dy / distance);
        }
    }
    
    public void setStationary(boolean state){
        this.stationary = state;
    }
    
    public void addConnections(Connecter c){
        this.connections.add(c);
    }
    
    public void update(double delta){
        if (!this.stationary){
            
            this.vx = this.x - this.ox;
            this.vy = this.y - this.oy;
            
            
            this.ox = this.x;
            this.oy = this.y;
            this.x += vx;
            this.y += vy;
            
            /*for (Connecter c : connections){
                if (c.length() <= c.maxLength){
                    

                }
            }*/
        }
    }
    
    public void draw(Graphics g){
        g.setColor(Color.white);
        //g.drawOval((int)x, (int)y, 5, 5);
        g.drawLine((int)x, (int)y, (int)x, (int)y);
    }
}
