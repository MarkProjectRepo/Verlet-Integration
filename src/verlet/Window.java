/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package verlet;

import java.awt.Canvas;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import javax.swing.*;


public class Window {
    private final Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    public final int Width = dim.width, Height = dim.height;
    public boolean clicked = false;
    Canvas mainCanvas = new Canvas();
    JFrame frame  = new JFrame("Colliding Spheres in Space");
    
    Panel menuPanel = new Panel();
    
    
    
    BufferStrategy bufferStrategy; 
    
    JPanel panel;
    
    public void init(){
        
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        panel = (JPanel) frame.getContentPane();
        panel.setSize(Height, Width);
        
        menuPanel.setSize(Width,20);
        
        
        mainCanvas.setSize(Width, Height);
        
        menuPanel.setVisible(false);
        
        
        menuPanel.setBackground(Color.white);
        
        panel.add(menuPanel);
        
        panel.add(mainCanvas);
        panel.setBackground(Color.black);
        
        frame.getContentPane().add(mainCanvas);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        mainCanvas.createBufferStrategy(2);
        
        mainCanvas.requestFocus();
        
        bufferStrategy = mainCanvas.getBufferStrategy();
    }
    
    public void addToMenuPanel(Component c){
        this.menuPanel.add(c);
    }
    
    public void addToMainPanel(Component c){
        this.panel.add(c);
    }
    
    public void menuPanelVisible(Boolean visible){
        this.menuPanel.setVisible(visible);
    }
}

