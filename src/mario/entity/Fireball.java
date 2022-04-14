/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.entity;

import java.awt.Graphics;
import java.util.Random;
import mario.Game;
import mario.Handler;
import mario.Id;
import mario.tile.Tile;

/**
 *
 * @author tolet
 */
public class Fireball extends Entity{

    private int frame = 0;
    private int frame2 = 0;
    private int frame3 = 0;
    private int frame4 = 0;
    private int frame5 = 0;
    private int frameDelay = 0;
    private int frameDelay2 = 0;
    private int frameDelayH = 0;
    
    private boolean xy = false;
    
    private Random random;
    
    public Fireball(int x, int y, int width, int height, Id id, Handler handler, int facing, int type) {
        super(x, y, width, height, id, handler);
        this.type = type;
        
        random = new Random();
        
        if (type == 0)
            switch(facing){
                case 0:
                    setVelX(-4);
                    break;
                case 1:
                    setVelX(4);
                    break;
            }
        else if (type == 1){
            int dir = random.nextInt(10);
            switch(facing){
                case 0:
                    setVelX(-dir-4);
                    break;
                case 1:
                    setVelX(dir+4);
                    break;
            }
        }
            
    }

    public void render(Graphics g) {
        if (type == 0)
            g.drawImage(Game.fireBall.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
        else if (type < 4)
            g.drawImage(Game.darkBall.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
        
        
    }
    
    public void tick() {
        x += velX;
        y += velY;
        
        if (getY() > 64*13 && type == 1 ){
            jumping = true;
            falling = false;
            gravity = 16.0;
        }
        
        for(int i = 0; i < handler.tile.size(); i++){
            Tile t = handler.tile.get(i);
            
            if (t.isSolid()){
                if (getBoundsTop().intersects(t.getBounds()) && type < 2){
                    setVelY(0);
                    if(jumping){
                        jumping = false;
                        falling = true;
                        gravity = 6.0;
                    }
                }
                
                if (getBoundsLeft().intersects(t.getBounds()) && type < 2)
                    if (type == 0)
                        die();
                    else{
                        setVelX(3);
                    }
               
                if (getBoundsRight().intersects(t.getBounds()) && type < 2)
                    if (type == 0)
                        die();
                    else{
                        setVelX(-3);
                    }
               
                if (getBoundsBottom().intersects(t.getBounds()) && type < 2){
                    jumping = true;
                    falling = false;
                    if (type == 0)
                        gravity = 4.0;
                    else 
                        gravity = 14.0;
                   
                }else if (!falling && !jumping && type < 2){
                    falling = true;
                    gravity = 1.0;
                }
            }
        }
        
        for(int i = 0; i < handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            
            if (e.getId() == Id.goomba || e.getId() == Id.plant || e.getId() == Id.koopa){
                if (type == 0 && getBounds().intersects(e.getBounds())){
                    die();
                    
                    e.die();                    
                }
            }else if (e.getId() == Id.player && type == 2){
                if (!xy){
                        if (getX() >= e.getX() -4 && getX() <= e.getX() +4)
                            setVelX(0);
                        else if (e.getX() < getX())
                            setVelX(-2);
                        else if (e.getX() > getX())
                            setVelX(2);
                        
                        if (getY() >= e.getY() -4 && getY() <= e.getY() +4)
                            setVelY(0);
                        else if (e.getY() < getY())
                            setVelY(-2);
                        else if (e.getY() > getY())
                            setVelY(2);
                        
                    frameDelay2++;
                    if (frameDelay2 >= 60){
                        frame4++;
                        if (frame4 > 2){
                            xy = true;
                        frame4 = 0;
                        }
                        frameDelay2 = 0;
                    }
                }
            }
        }
        
        if(jumping  && type < 2){
            gravity-= 0.3;
            setVelY((int)-gravity);
            if(gravity<= 0.5){
                jumping = false;
                falling = true;
            }
        }
        
        if(falling  && type < 2){
            gravity += 0.3;
            setVelY((int)gravity);
        }
        
        if (type == 4){
            
            frameDelayH++;
            if (frameDelayH >= 20){
                frame5++;
                if (frame5 > 0){
                    type = 3;
                    frame5 = 0;
                    frameDelayH = 0;
                }
            }
        }
        
        frameDelay++;
        if (frameDelay >= 60){
            frame++;
            frame2++;
            frame3++;
            frame4++;
            if (frame > 3 && type == 1){
                die();
                frame = 0;
            }
            if (frame3 > 9 && type == 2){
                die();
                frame3 = 0;
            }
            if (frame2 > 0 && type == 3){
                type = 2;
                frame2 = 0;
            }
            
            frameDelay = 0;
        }
        
    }
    
}
