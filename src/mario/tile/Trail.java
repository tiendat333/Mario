/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.tile;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import mario.Handler;
import mario.Id;

/**
 *
 * @author tolet
 */
public class Trail extends Tile{

    private float alpha = 1.0f;
    
    private BufferedImage image;
    
    public Trail(int x, int y, int width, int height, boolean solid, Id id, Handler handler, BufferedImage image) {
        super(x, y, width, height, solid, id, handler);
        
        this.image = image;
    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        
        g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    }

    public void tick() {
        alpha -= 0.05;
        
        if (alpha < 0.05) 
            die();
    }
    
}
