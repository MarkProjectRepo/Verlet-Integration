/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Color;
import java.awt.Graphics;


public class Connecter {
    public Particle p1, p2;
    public int index1, index2;
    final double maxLength = 10;
    
    public Connecter(Particle p1, Particle p2){
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public double length(){
        double dx = this.p1.x - this.p2.x;
        double dy = this.p1.y - this.p2.y;
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public void correct(){
        double diffX = p1.x - p2.x;
        double diffY = p1.y - p2.y;

        double d = Math.sqrt(diffX * diffX + diffY * diffY);

        double difference = (maxLength - d) / d;

        double translateX = diffX * 0.4 * difference;
        double translateY = diffY * 0.4 * difference;
        if (!p1.stationary){
            p1.x += translateX;
            p1.y += translateY;
        }
        if (!p2.stationary){
            p2.x -= translateX;
            p2.y -= translateY;
        }
    }
    
    public void draw(Graphics g){
        g.setColor(Color.red);
        //g.setColor(Color.getHSBColor((float)length(), (float)length()/10, 1));
        g.drawLine((int) p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
    }
}
