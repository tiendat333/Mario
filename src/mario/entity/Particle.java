/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity;

import java.awt.Graphics;
import mario.Game;
import mario.Handler;
import mario.Id;

/**
 *
 * @author tolet
 */
public class Particle extends Entity{

    private int frame = 0;
    private int frameDelay = 0;
    private int count = 0;
    
    private boolean fading = false;
    
    public Particle(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }
    
    public void render(Graphics g) {
        if (!fading)
            g.drawImage(Game.particle[frame].getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
        else
            g.drawImage(Game.particle[Game.particle.length - frame -1].getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    public void tick() {
        frameDelay++;
        
        if (frameDelay >= 3){
            frame++;
            count++;
            frameDelay = 0;
        }
        
        if (frame >= 5)
            frame = 0;
        
        if (count >= Game.particle.length -1)
            fading = true;
        
        if (count >= 13)
            die();
    }
    
}
