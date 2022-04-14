/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity.mob;

import java.awt.Graphics;
import java.util.Random;
import mario.Game;
import mario.Handler;
import mario.Id;
import mario.entity.Entity;
import mario.tile.Tile;

/**
 *
 * @author tolet
 */
public class Goomba extends Entity{

    private int frame = 0;
    private int frameDelay = 0;
    public Random random = new Random();
    
    public Goomba(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        
        int dir = random.nextInt(2);
        
        switch(dir){
            case 0:
                setVelX(-2);
                break;
            case 1:
                setVelX(2);
                break;             
        }
    }

    public void render(Graphics g) {
         g.drawImage(Game.goomba[frame].getBufferedImage(), x, y, width, height, null);
        
    }

    public void tick() {
        x += velX;
        y += velY;
        
        for(Tile t:handler.tile){
            if(t.solid) {
                
                if(getBoundsBottom().intersects(t.getBounds())){
                    setVelY(0);
                    if(falling){
                        
                        falling = false;
                    }
                }else{
                    if(!falling){
                        gravity = 0.0;
                        falling = true;
                    } 
                }
                if(getBoundsLeft().intersects(t.getBounds())){
                    setVelX(2);
                    
                }
                if(getBoundsRight().intersects(t.getBounds())){
                    setVelX(-2);
                }
            }                       
        }
        
        if(falling){
            gravity += 0.1;
            setVelY((int)gravity);
        }
        
        if(velX != 0){
            frameDelay++;
            if (frameDelay >= 7){
                frame++;
                if (frame >= 2){
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    
    }
}
