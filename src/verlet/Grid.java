/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.util.ArrayList;

/**
 *
 * @author matra4214
 */
public class Grid {
    public ArrayList<Particle> particles = new ArrayList();
    public ArrayList<Connecter> connectors = new ArrayList();
    
    public Grid(double startingX, double startingY, int width, int height, double spacing){
        for (int i = 0; i < width*height; i++){
            int column = i % width;
            int row = i/width;
            particles.add(new Particle(startingX+spacing*column, startingY+spacing*row, i));
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
    }
}
