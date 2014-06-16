/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Graphics;


public class Connecter {
    public double x1, y1, x2, y2;
    
    public Connecter(double x1,double x2,double y1,double y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    
    public void update(Particle p1, Particle p2){
        this.x1 = p1.x;
    }
    
    public void draw(Graphics g){
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
}
