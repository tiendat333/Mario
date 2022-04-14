/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity.mob;

import java.awt.Graphics;
import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;

/**
 *
 * @author tolet
 */
public class Plant extends Entity{

    private int wait;
    private int pixelsTravelled;
    
    private boolean moving;
    private boolean insidePipe;
    
    private int frame = 0;
    private int frameDelay = 0;
    
    public Plant(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        
        moving = false;
        insidePipe = true;
    }

    public void render(Graphics g) {
        g.drawImage(Game.plant[frame].getBufferedImage(), x, y, width, height, null);
    
    }

    public void tick() {
        y += velY;
        
        if (!moving)
            wait++;
        
        if (wait >= 180){
            if (insidePipe)
                insidePipe = false;
            else insidePipe = true;
            
            moving = true;
            wait = 0;
        }
        
        if (moving){
            if (insidePipe)
                setVelY(-3);
            else
                setVelY(3);
            
            pixelsTravelled+= velY;
            
            if (pixelsTravelled >= getHeight() || pixelsTravelled <= -getHeight()){
                pixelsTravelled = 0;
                moving = false;
                
                setVelY(0);
            }
        }
        
        frameDelay++;
        if (frameDelay >= 30){
            frame++;
            if (frame > 1){
                frame = 0;
            }
            frameDelay = 0;
        }
    }
    
}
