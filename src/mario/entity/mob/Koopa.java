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
import states.KoopaState;

/**
 *
 * @author tolet
 */
public class Koopa extends Entity{

    private int frame = 0;
    private int frameDelay = 0;
    public Random random = new Random();
    
    public int shellCount;
    
    public Koopa(int x, int y, int width, int height, Id id, Handler handler) {
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
        
        koopaState = KoopaState.WALKING;
    }

    public void render(Graphics g) {
        if (koopaState == KoopaState.WALKING){
            if ( facing == 0){
                g.drawImage(Game.koopa[frame].getBufferedImage(), x, y, width, height, null);
            }else if ( facing == 1){
                g.drawImage(Game.koopa[frame+2].getBufferedImage(), x, y, width, height, null);
            }
        }else 
            g.drawImage(Game.koopa[4].getBufferedImage(), x, y, width, height, null);
  
    }

    public void tick() {
        x += velX;
        y += velY;
        
        if (koopaState == KoopaState.SHELL){
            setVelX(0);
            
            shellCount++;
            
            if (shellCount >=300){
                shellCount = 0;
                koopaState = KoopaState.WALKING;
            }
            
            if (koopaState == KoopaState.WALKING || koopaState == KoopaState.SPINNING){
                shellCount = 0;
                
                if (velX == 0){
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
            } 
                
        }
        
        
        
        
        
        for(int i = 0; i < handler.tile.size(); i++){
            Tile t = handler.tile.get(i);
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
                    if (koopaState == KoopaState.SPINNING)
                        setVelX(10);
                    else 
                        setVelX(2);
                }
                if(getBoundsRight().intersects(t.getBounds())){
                    if (koopaState == KoopaState.SPINNING)
                        setVelX(-10);
                    else 
                        setVelX(-2);
                }
            }                       
        }
        
        for (int i = 0; i < handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            if (e.getId() == Id.goomba)
                if (getBounds().intersects(e.getBounds()) && koopaState == KoopaState.SPINNING){
                    e.die();
                    Game.scores = Game.scores + 200;
                }
                    
        }   
        
        if(falling){
            gravity += 0.1;
            setVelY((int)gravity);
        }
        
        if (velX < 0)
            facing = 0;
        else if (velX > 0)
            facing = 1;
        
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
