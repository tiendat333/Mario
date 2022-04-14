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
public class Beam extends Entity{

    private int frame = 0;
    private int frameDie = 0;
    private int frameDelay = 0;
    private int frameDelayDie = 0;
    public Random random = new Random();
    
    public Beam(int x, int y, int width, int height, Id id, Handler handler, int type) {
        super(x, y, width, height, id, handler);
        this.type = type;
        
        int dir = random.nextInt(2);
        
    }

    public void render(Graphics g) {
            
        g.drawImage(Game.beam[type].getBufferedImage(), x, y, width, height, null);
    }

    public void tick() {
        x += velX;
        y += velY;
        
        frameDelay++;
        if (frameDelay >= 50){
            frame++;
            if (frame > 1){
                if ( type <=2)
                    die();
                frame = 0;
            }
            frameDelay = 0;
        }
        
        
        
        for(int i = 0; i < handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            
            if (e.getId() == Id.player){
                if (getBounds().intersects(e.getBounds()))
                    frameDelayDie++;
                    if (frameDelayDie >= 10){
                        frameDie++;
                        if (frameDie > 1){
                            //e.die();
                        }
                        frameDelayDie = 0;
                    }
            }    
        }    
    }
}
