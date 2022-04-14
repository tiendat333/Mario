/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity.powerup;

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
public class PowerStar extends Entity{
    
    private Random random;
    

    public PowerStar(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        
        random = new Random();
        
        int dir = random.nextInt(2);
        
        switch(dir){
            case 0:
                setVelX(-4);
                break;
            case 1:
                setVelX(4);
                break;             
        }
        falling = true;
        gravity = 0.15;
    }

    public void render(Graphics g) {
        g.drawImage(Game.star.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
                
    }

    public void tick() {
        x += velX;
        y += velY;
        for (int i = 0; i < handler.tile.size(); i++){
            Tile t = handler.tile.get(i);
            
            if (t.isSolid()){
                if (getBoundsBottom().intersects(t.getBounds())){
                    jumping = true;
                    falling = false;
                    gravity = 8.0;
                }
                
                if (getBoundsLeft().intersects(t.getBounds())){
                    setVelX(4);
                }
                
                if (getBoundsRight().intersects(t.getBounds())){
                    setVelX(-4);
                }
            }
        }
        if(jumping){
            gravity-= 0.15;
            setVelY((int)-gravity);
            if(gravity<= 0.6){
                jumping = false;
                falling = true;
            }
        }
        
        if(falling){
            gravity += 0.15;
            setVelY((int)gravity);
        }
    }
    
}
