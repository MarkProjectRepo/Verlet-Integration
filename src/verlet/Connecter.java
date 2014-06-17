/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Color;
import java.awt.Graphics;


public class Connecter {
    public double x1, y1, x2, y2;
    public Particle p1, p2;
    public int index1, index2;
    final double maxLength = 15;
    
    public Connecter(Particle p1, Particle p2, int index1, int index2){
        this.p1 = p1;
        this.p2 = p2;
        
        this.x1 = p1.x;
        this.x2 = p2.x;
        
        this.y1 = p1.y;
        this.y2 = p2.y;
        
        this.index1 = index1;
        this.index2 = index2;
    }
    
    public void update(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public double length(){
        double dx = this.x1 - this.x2;
        double dy = this.y1 - this.y2;
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public void draw(Graphics g){
        g.setColor(Color.gray);
        //g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
}
