/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.graphic.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import mario.Game;

/**
 *
 * @author tolet
 */
public class Button {
    public int x,y;
    public int width, height;
    
    public String label;

    public Button(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }
    
    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Century Gothic", Font.BOLD, 50));
        g.drawRect(getX(), getY(), getWidth(), getHeight());
        
        FontMetrics fm = g.getFontMetrics();
        int stringX = (getWidth() - fm.stringWidth(getLabel())) / 2;
        int stringY = fm.getAscent() + (getHeight() - (fm.getAscent() + fm.getDescent())) / 2;
        g.drawString(getLabel(), getX() + stringX, getY() + stringY);
    }
    
    public void triggerEvent(){
        if (!Game.playing){
            if (getLabel().toLowerCase().contains("start"))
                Game.playing = true;
            else if (getLabel().toLowerCase().contains("leader")){
                LeaderBoard top = new LeaderBoard();
                top.setVisible(true);
            }else if (getLabel().toLowerCase().contains("control")){
                Control ctl = new Control();
                ctl.setVisible(true);
            }else if (getLabel().toLowerCase().contains("exit"))
                System.exit(0);
        }
        
        
        
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
}
