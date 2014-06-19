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
    final int maxDistance = 10;
    public Color c;
    public boolean focused = false;
    Window window = new Window();
    ArrayList<Connecter> connections = new ArrayList<Connecter>();
    
    public Particle(double x, double y, int identifier){
        this.x = this.ox = x;
        this.y = this.oy = y;
        this.identifier = identifier;
        c = Color.white;
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
            this.y += vy+0.1;
            for (Connecter c : connections){
                c.correct();
            }
            
            if (this.y > window.Height){
                y = window.Height;
                vy = -vy;
            }
            if (this.x < 0){
                x = 0;
                vx = -vx;
            }
            if (this.x > window.Width){
                x = window.Height;
                vx = -vx;
            }
            
            
        }
    }
    
    public void setColor(Color c){
        this.c = c;
    }
    
    public void draw(Graphics g){
        g.setColor(c);
        if (focused){
            g.drawOval((int)x, (int)y, 5, 5);
        }
        g.drawLine((int)x, (int)y, (int)x, (int)y);
    }
}
